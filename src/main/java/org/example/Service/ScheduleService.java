package org.example.Service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.util.EmbedMessage;
import org.example.util.PropertiesLoad;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

public class ScheduleService {
    private static String DIR_ID; // Google Drive Log가 저장되는 폴더의 고유 ID

    private static Drive SERVICE;

    public ScheduleService () {
        DIR_ID = new PropertiesLoad().getValue("dir_id");
        try {
            SERVICE = new DriveQuickStartService().startGoogleDrive();
        } catch (Exception e) {
            SERVICE = null;
        }
    }

    /** Log File의 목록을 출력하는 메서드 */
    public void getLogFileList (MessageReceivedEvent event) {
        try {
            String print_str = "";

            // Google Drive Start
            String parents = "parents = '" + DIR_ID + "'";

            FileList result = SERVICE.files().list()
                    .setQ(parents)  // 폴더의 고유 ID
                    .setPageSize(10)
                    .execute();
            List<File> files = result.getFiles();
            if (files == null || files.isEmpty()) {
                System.out.println("No files found.");
            } else {
                for (File file : files) {
                    print_str += "----------------------------\n" +
                            ":page_facing_up: " + file.getName() + "\n" +
                            ":id: `" + file.getId() + "`\n";
                }
            }

            EmbedBuilder eb = new EmbedMessage().getEmbed(
                    ":file_folder: Log 파일 목록",
                    print_str
            );

            // Discord 메시지 전송
            event.getChannel().sendMessageEmbeds(eb.build()).queue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Log File을 읽고 출력하는 메서드 */
    public void getLogFileOpen (MessageReceivedEvent event, String file_id) {
        EmbedBuilder eb = null;

        try {
            // Google Drive Start
            String parents = "parents = '" + DIR_ID + "'";

            System.out.println(SERVICE.files().get(file_id).);

            OutputStream outputStream = new ByteArrayOutputStream();
            SERVICE.files().get(file_id)
                    .executeMediaAndDownloadTo(outputStream);

            Scanner sc = new Scanner((Readable) outputStream);
//            Scanner sc = null;
            String temp = "", content = "";

            // 파일 읽기
            while(sc.hasNextLine()) {
                temp += sc.nextLine() + "\n";
            }

            // 파일의 내용이 공백인지 확인
            if (null == temp || temp.equals(""))
                content = ":u7121: 파일의 내용이 없습니다. :u7121:";
            else
                content = "```" + temp + "```";

            eb = new EmbedMessage().getEmbed(
                    ":page_facing_up: `" + file_id + "`의 로그",
                    content
            );
        } catch (Exception e) {
            eb = new EmbedMessage().getErrorEmbed(
                    "`" + file_id + "` 파일이 없습니다.\n파일 이름을 확인하고 다시 시도하십시오."
            );
            e.printStackTrace();
        }

        // Discord 메시지 전송
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }
}

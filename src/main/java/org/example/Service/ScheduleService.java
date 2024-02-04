package org.example.Service;

import com.google.api.client.http.HttpResponseException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.util.EmbedMessage;
import org.example.util.PropertiesLoad;
import org.json.JSONObject;

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

            // parents 변수 : 검색할 파일의 폴더 지정
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
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            String content = "";

            // Google Drive에서 파일 Blob 가져오기
            try {
                SERVICE.files().get(file_id)
                        .executeMediaAndDownloadTo(outputStream);

                content = "```" + new String(outputStream.toByteArray(),"UTF-8") + "```";
            } catch (HttpResponseException e) {
                // 파일의 내용이 비어있거나 파일이 없을때 Error Code를 이용하여 구분
                JSONObject ejson = (JSONObject) new JSONObject(e.getContent()).get("error");

                System.out.println(ejson);
                String code = "" + ejson.get("code");

                switch (code) {
                    case "404":
                        content = "`" + file_id + "` 파일이 없습니다.\n파일 ID를 확인하고 다시 시도하십시오.";
                        break;
                    case "416":
                        content = ":u7121: 파일의 내용이 없습니다. :u7121:";
                        break;
                    default:
                        break;
                }
            }

            eb = new EmbedMessage().getEmbed(
                    ":page_facing_up: `" + file_id + "`의 로그",
                    content
            );
        } catch (Exception e) {
            eb = new EmbedMessage().getErrorEmbed(
                    "Error"
            );
            e.printStackTrace();
        }

        // Discord 메시지 전송
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }
}

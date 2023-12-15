package org.example.Service;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.util.EmbedMessage;

import java.io.File;
import java.util.Scanner;

public class ScheduleService {
    private static String LOG_DIR_PATH = "./test_log/";

    public void getLogFileList (MessageReceivedEvent event) {
        File dir = new File(LOG_DIR_PATH);

        String print_str = "";

        // 파일 목록을 출력을 위한 for문
        for (String file_name : dir.list()) {
            print_str += ":page_facing_up: `" + file_name + "`\n";
        }

        EmbedBuilder eb = new EmbedMessage().getEmbed(
                ":file_folder: Log 파일 목록",
                print_str
        );

        // Discord 메시지 전송
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    public void getLogFileOpen (MessageReceivedEvent event, String file_name) {
        File file = new File(LOG_DIR_PATH + file_name);
        EmbedBuilder eb = null;

        try {
            Scanner sc = new Scanner(file);
            String temp = "";

            while(sc.hasNextLine()) {
                temp += sc.nextLine() + "\n";
            }

            eb = new EmbedMessage().getEmbed(
                    ":page_facing_up: `" + file_name + "`의 로그",
                    "```" + temp + "```"
            );
        } catch (Exception e) {
            eb = new EmbedMessage().getErorrEmbed(
                    "`" + file_name + "` 파일이 없습니다.\n파일 이름을 확인하고 다시 시도하십시오."
            );
            e.printStackTrace();
        }

        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }
}

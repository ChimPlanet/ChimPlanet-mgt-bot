package org.example.Service;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.File;

public class ScheduleService {
    private static String LOG_DIR_PATH = "./test_log/";

    public void getLogFileList (MessageReceivedEvent event) {
        File dir = new File(LOG_DIR_PATH);
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(":file_folder: Log 파일 목록", null);   // Embed 형식 메시지의 Title
        eb.setColor(Color.green);   // Embed 형식 메시지의 왼쪽 줄 생상

        String print_str = "";

        // 파일 목록을 출력을 위한 for문
        for (String file_name : dir.list()) {
            print_str += ":page_facing_up: `" + file_name + "`\n";
        }

        eb.setDescription(print_str);   // String 형식으로 저장된 파일 목록을 출력

        // Discord 메시지 전송
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    public void getLogFileOpne (MessageReceivedEvent event, String file_name) {
        event.getChannel().sendMessage("pong!").queue();
    }
}

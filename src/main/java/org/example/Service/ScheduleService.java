package org.example.Service;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.util.EmbedMessage;

import java.awt.*;
import java.io.File;

public class ScheduleService {
    private static String LOG_DIR_PATH = "./test_log/";

    public void getLogFileList (MessageReceivedEvent event) {
        File dir = new File(LOG_DIR_PATH);

        String print_str = "";

        // 파일 목록을 출력을 위한 for문
        for (String file_name : dir.list()) {
            print_str += ":page_facing_up: `" + file_name + "`\n";
        }

        EmbedBuilder eb = new EmbedMessage().getEmbed(":file_folder: Log 파일 목록", print_str);

        // Discord 메시지 전송
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    public void getLogFileOpne (MessageReceivedEvent event, String file_name) {
        event.getChannel().sendMessage("pong!").queue();
    }
}

package org.example.Controller;

import com.google.api.services.drive.Drive;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.Service.ScheduleService;
import org.example.util.PropertiesLoad;

public class JDAController extends ListenerAdapter {

    private static String LOG_CHANNEL_ID;  // 스케줄러의 작동 현황을 알리는 로그 채널 ID

    public JDAController () {
        LOG_CHANNEL_ID = new PropertiesLoad().getValue("log_channel_id");
    }

    @Override
    public void onReady(ReadyEvent event) {
        event.getJDA().getTextChannelById(LOG_CHANNEL_ID).sendMessage("실행").queue();
        super.onReady(event);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // System.out.println(event.getMessage().getContentDisplay());
        String messge = event.getMessage().getContentRaw();

        // Discord bot의 채팅이면 걸러냄 & 명령어 구문인지 확인
        if (event.getAuthor().isBot() || messge.charAt(0) != '!') return ;

        // 명령어 인식을 위한 " "으로 단어 분류
        String[] messge_split = messge.split(" ");

        // 명령어
        switch (messge_split[0]) {
            case "!스케줄러":
                ScheduleService scheduleService = new ScheduleService();
                // ! 스케줄러 <보조 명령>
                switch (messge_split[1]) {
                    case "로그":
                        // !스케줄러 로그 <명령어, 파일 이름>
                        switch (messge_split[2]) {
                            case "목록":
                                scheduleService.getLogFileList(event);
                                break;
                            default:
                                scheduleService.getLogFileOpen(event, messge_split[2]);
                                break;
                        }
                        break;
                }
                break;
        }
    }

}

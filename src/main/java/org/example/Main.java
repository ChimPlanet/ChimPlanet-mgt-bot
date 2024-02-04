package org.example;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.Controller.JDAController;
import org.example.Service.DriveQuickStartService;
import org.example.util.PropertiesLoad;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // JDA 기본 세팅 및 실행
        JDA jda = JDABuilder.createDefault(new PropertiesLoad().getValue("bot_token"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.watching("우왁굳의 게임방송"))
                .build();

        jda.addEventListener(new JDAController());
    }

}
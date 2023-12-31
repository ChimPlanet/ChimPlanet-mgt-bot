package org.example.util;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class EmbedMessage {
    /** 일반 Embed Message 형식 */
    public EmbedBuilder getEmbed (String title, String content) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(title, null);   // Embed 형식 메시지의 Title
        eb.setColor(Color.green);   // Embed 형식 메시지의 왼쪽 줄 색상
        eb.setDescription(content);   // String 형식으로 저장된 파일 목록을 출력

        return eb;
    }

    /** Error Embed Message 형식 */
    public EmbedBuilder getErrorEmbed (String content) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(":octagonal_sign: Error", null);    // Embed 형식 메시지의 Title
        eb.setColor(Color.red); // Embed 형식 메시지의 왼쪽 줄 색상
        eb.setDescription(content); // String 형식으로 저장된 파일 목록을 출력

        return eb;
    }
}

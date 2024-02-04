package org.example.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class PropertiesLoad {
    private static String FILE_PATH = "./src/main/resources/application.properties";

    /** 일반 정보 가져오기 */
    public String getValue(String name) {
        try {
            Properties prop = new Properties();

            prop.load(new FileInputStream(FILE_PATH));

            return prop.getProperty(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** URL 관련 정보 가져오기 */
    public String getUrlValue(String name) {
        try {
            Properties prop = new Properties();

            prop.load(new FileInputStream(FILE_PATH));

            return prop.getProperty("server_url") + prop.getProperty(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package org.example.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class PropertiesLoad {
    private static String FILE_PATH = "./src/main/resources/application.properties";

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
}

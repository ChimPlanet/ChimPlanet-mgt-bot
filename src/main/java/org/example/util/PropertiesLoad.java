package org.example.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class PropertiesLoad {
    public String PropertiesLoad(String name) {
        try {
            Properties prop = new Properties();

            prop.load(new FileInputStream("./src/main/resources/application.properties"));

            return prop.getProperty(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

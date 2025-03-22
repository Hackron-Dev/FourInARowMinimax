package org.WebAi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private Properties properties;

    public ConfigLoader() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src\\main\\resources\\config.properties");
properties.load(fis);

            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return properties.getProperty("username");
    }

    public String getEmail() {
        return properties.getProperty("email");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }
}

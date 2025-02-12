package org.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    static {
        String propertiesFileName = System.getProperty("config.file", "test-config.properties"); // Default to main properties file
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (input == null) {
                throw new IOException("Unable to find properties file: " + propertiesFileName);
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("App.addr");
    }
    public static String getPath() {
        return properties.getProperty("path");
    }
    public static String getProtocol() {
        return properties.getProperty("App.protocol");
    }
    public static String getPort() {
        return properties.getProperty("App.port");
    }
    public static String getScheme() {
        return properties.getProperty("App.scheme");
    }
}
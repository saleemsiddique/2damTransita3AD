package com.es.iesmz.transita3.Transita.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {
    static String filePath = "C:\\Clase\\2dam\\desarrolloMultimedia\\seminario6validacion\\2damTransita3AD\\AD\\Transita\\src\\main\\resources\\application.properties";
    public static String ddlAuto;
    public static String port;
    public static String url;
    public static String username;
    public static String password;
    public static String jwtSecret;
    public static String jwtExpirationMs;
    public static String ORSToken;
    public static void readConfigFile() {
        try {
            Properties properties = new Properties();

            // Load the properties from the file
            FileInputStream fileIn = new FileInputStream(filePath);
            properties.load(fileIn);
            fileIn.close();

            // Get values using keys
            ddlAuto = properties.getProperty("spring.jpa.hibernate.ddl-auto");
            port = properties.getProperty("server.port");
            url = properties.getProperty("spring.datasource.url");
            username = properties.getProperty("spring.datasource.username");
            password = properties.getProperty("spring.datasource.password");
            jwtSecret = properties.getProperty("bezkoder.app.jwtSecret");
            jwtExpirationMs = properties.getProperty("bezkoder.app.jwtExpirationMs");
            ORSToken = properties.getProperty("ors.token");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

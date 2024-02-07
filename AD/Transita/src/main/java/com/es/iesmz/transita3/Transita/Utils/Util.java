package com.es.iesmz.transita3.Transita.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {
    static String filePath = "./src/main/resources/application.properties";
    public static String ddlAuto;
    public static String port;
    public static String url;
    public static String username;
    public static String password;
    public static String jwtSecret;
    public static String jwtExpirationMs;
    public static String ORSToken;
    public static String FTPHost;
    public static String FTPPort;
    public static String ftpUsername;
    public static String ftpPassword;
    public static String ftpRemoteDirectory;
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
            FTPHost = properties.getProperty("spring.ftp.host");
            FTPPort = properties.getProperty("spring.ftp.port");
            ftpUsername = properties.getProperty("ftp.username");
            ftpPassword = properties.getProperty("ftp.password");
            ftpRemoteDirectory = properties.getProperty("ftp.remoteDirectory");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

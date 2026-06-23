package com.lightit.demo.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try (InputStream input = DatabaseConnection.class.getResourceAsStream("/application.properties")) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                URL = prop.getProperty("db.url", "jdbc:mysql://localhost:3306/light-it?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
                USER = prop.getProperty("db.user", "root");
                PASSWORD = prop.getProperty("db.password", "");
            } else {
                // Fallback jika file properties tidak ditemukan
                URL = "jdbc:mysql://localhost:3306/light-it?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                USER = "root";
                PASSWORD = "";
            }
        } catch (Exception e) {
            URL = "jdbc:mysql://localhost:3306/light-it?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            USER = "root";
            PASSWORD = "";
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL JDBC tidak ditemukan! Pastikan library mysql-connector-j sudah terpasang.");
            return null;
        } catch (SQLException e) {
            System.err.println("Koneksi ke Database gagal! Pastikan MySQL di XAMPP/Laragon sudah di-Start.");
            return null;
        }
    }
}
package org.example.almasenesmorelos1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConexionDBTest {

    private static final String WALLET_PATH = "C:/Users/jenif/AlmacenesMorelos1/src/main/resources/org/example/almasenesmorelos1/Wallet_Integradora";
    private static final String DB_ALIAS = "integradora_high";
    private static final String USER = "ADMIN";
    private static final String PASSWORD = "Dua.Lipa.2005";

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:oracle:thin:@" + DB_ALIAS + "?TNS_ADMIN=" + WALLET_PATH;

        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);

        try (Connection conn = DriverManager.getConnection(jdbcUrl, props);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SYSDATE FROM DUAL")) {

            if (rs.next()) {
                System.out.println("✅ Conexión exitosa. Fecha actual de la BD: " + rs.getString(1));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error durante la conexión o consulta:");
            e.printStackTrace();
        }
    }
}

package org.example.almasenesmorelos1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String UBICACION_WALLET =
            "C:/Users/jenif/AlmacenesMorelos1/src/main/Wallet_Integradora";

    private static final String JDBC_URL =
            "jdbc:oracle:thin:@integradora_high?TNS_ADMIN=" + UBICACION_WALLET;

    private static final String USUARIO = "ADMIN";
    private static final String PASSWORD = "Dua.Lipa.2005";

    public static Connection getConexion() {
        try {
            return DriverManager.getConnection(JDBC_URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos:");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conn = getConexion();
        if (conn != null) {
            System.out.println("✅ Conexión exitosa");
        }
    }
}

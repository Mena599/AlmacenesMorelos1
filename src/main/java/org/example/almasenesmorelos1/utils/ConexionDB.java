package org.example.almasenesmorelos1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    // Ruta del wallet
    private static final String UBICACION_WALLET = "C:\\Users\\jenif\\AlmacenesMorelos1\\src\\main";

    // URL del JDBC con alias TNS
    private static final String JDBC_URL = "jdbc:oracle:thin:@integradora_high";

    // Usuario y contraseña
    private static final String USER = "Admin";
    private static final String PASS = "Dua.Lipa.2005";

    public static Connection getConnection() throws SQLException {
        System.setProperty("oracle.net.tns_admin", UBICACION_WALLET);
        return DriverManager.getConnection(JDBC_URL, USER, PASS);
    }
}
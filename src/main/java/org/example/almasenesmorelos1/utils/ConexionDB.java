package org.example.almasenesmorelos1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static javafx.application.Application.launch;

public class ConexionDB {
    private static Connection conexion;

    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                Class.forName("oracle.jdbc.OracleDriver");
                String ruta = "/home/menaisrael599/IdeaProjects/AlmacenesMorelos1/src/main/resources/org/example/almasenesmorelos1/Wallet_Integradora";
                String alias = "integradora_high";
                String url = "jdbc:oracle:thin:@" + alias + "?tns_ADMIN=" + ruta;

                Properties props = new Properties();
                props.setProperty("user", "ADMIN");
                props.setProperty("password", "Dua.Lipa.2005");

                conexion = DriverManager.getConnection(url, props);
                System.out.println("✅ Conexión exitosa a la base de datos Oracle");
            } catch (Exception e) {
                System.out.println("❌ Error al conectar a la base de datos: ");
                e.printStackTrace();
            }
        }
        return conexion;
    }

    // Método para compatibilidad con código que use getConnection()
    public static Connection getConnection() throws SQLException {
        return getConexion();
    }

    // Método main para probar conexión desde consola o IDE
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                System.out.println("🔍 Prueba de conexión completada correctamente");
                conn.close();
            } else {
                System.out.println("❌ No se pudo establecer conexión");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


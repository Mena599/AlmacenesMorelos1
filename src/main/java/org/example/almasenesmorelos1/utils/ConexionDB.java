package org.example.almasenesmorelos1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;

public class ConexionDB {
    private static Connection conexion; // <- Faltaba

    // Ajusta aquí SOLO si cambian tus datos
    private static final String USER = "ADMIN";
    private static final String PASS = "Dua.Lipa.2005";
    private static final String WALLET_PATH = "C:/Users/jenif/AlmacenesMorelos1/src/main/resources/org/example/almasenesmorelos1/Wallet_Integradora";
    private static final String DB_ALIAS = "integradora_high";

    /**
     * Conexión Oracle con Wallet usando alias TNS (tnsnames.ora).
     * Usa TNS_ADMIN en las Properties (recomendado por Oracle).
     */
    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                // Driver moderno
                Class.forName("oracle.jdbc.OracleDriver");

                // URL corta: usa alias del tnsnames.ora
                String url = "jdbc:oracle:thin:@" + DB_ALIAS;

                // Credenciales y TNS_ADMIN en properties
                Properties props = new Properties();
                props.setProperty("user", USER);
                props.setProperty("password", PASS);
                props.setProperty("TNS_ADMIN", WALLET_PATH);

                conexion = DriverManager.getConnection(url, props);
                System.out.println("✅ Conexión exitosa a la base de datos Oracle");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ Driver JDBC de Oracle no encontrado: " + e.getMessage());
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
        return conexion;
    }

    // Alias por compatibilidad con código viejo
    public static Connection getConnection() throws SQLException {
        return getConexion();
    }

}
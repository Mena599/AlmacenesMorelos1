package org.example.almasenesmorelos1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConexionDB {

    // === CONFIGURA ESTOS VALORES (coinciden con lo que ya usas) ===
    private static final String WALLET_DIR = "/home/menaisrael599/IdeaProjects/AlmacenesMorelos1/src/main/resources/org/example/almasenesmorelos1/Wallet_Integradora";
    private static final String TNS_ALIAS  = "integradora_high"; // Debe existir en tnsnames.ora del wallet
    private static final String USER       = "ADMIN";
    private static final String PASS       = "Dua.Lipa.2005";

    private static volatile Connection singleton;

    private ConexionDB() {}

    /** Obtén SIEMPRE la conexión con este método. Autocommit=TRUE. */
    public static Connection getConnection() throws SQLException {
        try {
            if (singleton == null || singleton.isClosed()) {
                // Carga del driver (por si tu runtime lo necesita)
                try { Class.forName("oracle.jdbc.OracleDriver"); } catch (ClassNotFoundException ignored) {}

                // URL formato Wallet: jdbc:oracle:thin:@<alias>?TNS_ADMIN=<carpeta_wallet>
                String url = "jdbc:oracle:thin:@" + TNS_ALIAS + "?TNS_ADMIN=" + WALLET_DIR;

                Properties props = new Properties();
                props.setProperty("user", USER);
                props.setProperty("password", PASS);

                // Sugerido por Oracle para wallet (no es obligatorio si usas el query param):
                props.setProperty("oracle.net.tns_admin", WALLET_DIR);

                // Conecta
                singleton = DriverManager.getConnection(url, props);
                singleton.setAutoCommit(true); // <- importante para que se guarde lo que insertas
                System.out.println("✅ Conexión Oracle OK (" + TNS_ALIAS + ")");
            }
            return singleton;
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a Oracle: " + e.getMessage());
            throw e;
        }
    }

    /** Cierra la conexión si quieres apagar la app ordenadamente. */
    public static void closeQuietly() {
        try { if (singleton != null && !singleton.isClosed()) singleton.close(); }
        catch (SQLException ignore) {}
    }

    // Prueba rápida
    public static void main(String[] args) {
        try (Connection c = getConnection()) {
            System.out.println("🔍 Prueba de conexión lista");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


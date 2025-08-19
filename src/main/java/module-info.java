module org.example.almasenesmorelos1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;
    requires jbcrypt;

    // Controllers y vistas principales
    opens org.example.almasenesmorelos1 to javafx.fxml;
    exports org.example.almasenesmorelos1;

    // DAOs y utils (como ya los tenías)
    exports org.example.almasenesmorelos1.daos;
    opens org.example.almasenesmorelos1.daos to javafx.fxml;

    exports org.example.almasenesmorelos1.utils;
    opens org.example.almasenesmorelos1.utils to javafx.fxml;

    // ✅ NUEVO: DataStore y modelos (para que otras clases los vean)
    exports org.example.almasenesmorelos1.data;
    exports org.example.almasenesmorelos1.model;

    // (Opcional) abrirlos a FXML si algún día cargas controllers/objetos desde esos paquetes vía FXML
    opens org.example.almasenesmorelos1.data to javafx.fxml;
    opens org.example.almasenesmorelos1.model to javafx.fxml;
}

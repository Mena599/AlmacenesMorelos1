module org.example.almasenesmorelos1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;
    requires jbcrypt;


    opens org.example.almasenesmorelos1 to javafx.fxml;
    exports org.example.almasenesmorelos1;
    exports org.example.almasenesmorelos1.daos;
    opens org.example.almasenesmorelos1.daos to javafx.fxml;
    exports org.example.almasenesmorelos1.utils;
    opens utils to javafx.fxml;
}
module org.example.almasenesmorelos1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.almasenesmorelos1 to javafx.fxml;
    exports org.example.almasenesmorelos1;
}
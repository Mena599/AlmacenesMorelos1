module org.example.almasenesmorelos1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;

    opens org.example.almasenesmorelos1 to javafx.fxml;
    opens org.example.almasenesmorelos1.models to javafx.base;
    opens org.example.almasenesmorelos1.utils to javafx.fxml;

    exports org.example.almasenesmorelos1;
}

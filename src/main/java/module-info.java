module com.example.game00 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.game00 to javafx.fxml;
    exports com.example.game00;
    exports com.example.game00.controller;
    opens com.example.game00.controller to javafx.fxml;
}
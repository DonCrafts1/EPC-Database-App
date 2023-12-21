module com.epc.environmentapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires javafx.base;
    requires mysql.connector.java;
    requires java.desktop;
    requires org.controlsfx.controls;
    requires jasperreports;
    opens com.epc.environmentapp to javafx.fxml;
    exports com.epc.environmentapp;
}

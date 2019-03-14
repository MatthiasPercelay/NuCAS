module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports com.uca.nucas.gui;
    opens com.uca.nucas.gui to javafx.fxml;
}
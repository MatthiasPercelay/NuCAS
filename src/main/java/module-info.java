module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires afterburner.fx;
    opens com.uca.nucas.gui to javafx.fxml;
    exports com.uca.nucas.gui to javafx.graphics;

}
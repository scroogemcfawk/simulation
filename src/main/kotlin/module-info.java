module lab1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens flight to javafx.fxml;
    exports flight;
}
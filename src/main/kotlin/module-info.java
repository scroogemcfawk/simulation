module labs {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens flight to javafx.fxml;
    exports flight;
    exports currency;
    exports cellular_automaton.oned;
}
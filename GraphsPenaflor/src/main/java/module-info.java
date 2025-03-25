module com.example.graphspenaflor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.graphspenaflor to javafx.fxml;
    exports com.example.graphspenaflor;
}
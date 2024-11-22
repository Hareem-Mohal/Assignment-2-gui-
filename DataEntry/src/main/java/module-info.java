module com.example.dataentry {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dataentry to javafx.fxml;
    exports com.example.dataentry;
}
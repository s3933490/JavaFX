module com.example.socialmediafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.socialmediafx to javafx.fxml;

    exports com.example.socialmediafx;
}

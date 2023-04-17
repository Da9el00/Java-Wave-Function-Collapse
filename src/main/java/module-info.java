module com.example.wavefunctioncollapse {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.wavefunctioncollapse to javafx.fxml;
    exports com.example.wavefunctioncollapse;
}
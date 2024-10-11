module com.example.taliorproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires com.dlsc.formsfx;

    opens com.example.taliorproject to javafx.fxml;
    exports com.example.taliorproject;

    requires jdk.jdi;
    requires java.mail;
    requires activation;
    requires java.desktop;

    opens com.example.taliorproject.CustomerEnrollment to javafx.fxml;
    exports com.example.taliorproject.CustomerEnrollment;

    opens com.example.taliorproject.WorkersConsolee to javafx.fxml;
    exports com.example.taliorproject.WorkersConsolee;

    opens com.example.taliorproject.Measurement to javafx.fxml;
    exports com.example.taliorproject.Measurement;

    opens com.example.taliorproject.GetReadyProducts to javafx.fxml;
    exports com.example.taliorproject.GetReadyProducts;

    opens com.example.taliorproject.AllWorkers to javafx.fxml;
    exports com.example.taliorproject.AllWorkers;

    opens com.example.taliorproject.MeasurementsExplorer to javafx.fxml;
    exports com.example.taliorproject.MeasurementsExplorer;

    opens com.example.taliorproject.OrderDeliveryPanel to javafx.fxml;
    exports com.example.taliorproject.OrderDeliveryPanel;

    opens com.example.taliorproject.AdminPanel to javafx.fxml;
    exports com.example.taliorproject.AdminPanel;

//    opens com.example.taliorproject.email to javafx.fxml;
//    exports com.example.taliorproject.;



}
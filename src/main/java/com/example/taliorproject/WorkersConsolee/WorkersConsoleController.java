package com.example.taliorproject.WorkersConsolee;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import com.example.taliorproject.CustomerEnrollment.MySQLConnectioinClass;

public class WorkersConsoleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField address;

    @FXML
    private ListView<String> lblA;

    @FXML
    private TextField mobile;

    @FXML
    private TextField splz;

    @FXML
    private TextField wname;

    @FXML
    void doAdd(MouseEvent event)
    {
        if(event.getClickCount()==2)
        {
            String selItm=lblA.getSelectionModel().getSelectedItem();
            String currText=splz.getText();

            if (currText.isEmpty())
            {
                splz.setText(selItm);
            }
            else
            {
                splz.setText(currText + ", " + selItm);
            }
        }
    }

    @FXML
    void doNew(ActionEvent event) {
        wname.setText("");
        address.setText("");
        mobile.setText("");
        splz.setText("");
    }

    PreparedStatement stmt;
    @FXML
    void doSave(ActionEvent event)
    {
        try
        {
            stmt=con.prepareStatement("insert into workers values(?,?,?,?)");
            stmt.setString(1,wname.getText());
            stmt.setString(2,address.getText());
            stmt.setString(3,mobile.getText());
            stmt.setString(4,splz.getText());

            stmt.executeUpdate();
            System.out.println("Record Saved Successsssfulllyyy");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    Connection con;
    @FXML
    void initialize() {
        con= MySQLConnectioinClass.doConnect();
        if(con==null)
            System.out.println("Connction Did not Established");
        else
            System.out.println("Connection Doneeee");

        String dress[]={"Pent","Shirt","Coat"};
        lblA.getItems().addAll(dress);


    }

}

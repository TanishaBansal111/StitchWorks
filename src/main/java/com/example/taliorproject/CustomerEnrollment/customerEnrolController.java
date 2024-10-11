package com.example.taliorproject.CustomerEnrollment;


import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import static java.util.Arrays.asList;

public class customerEnrolController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField address;

    @FXML
    private TextField city;

    @FXML
    private TextField cname;

    @FXML
    private DatePicker dob;

    @FXML
    private TextField email;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField mobile;

    PreparedStatement stmt;

    @FXML
    void doClear(ActionEvent event)
    {
        mobile.setText("");
        cname.setText("");
        email.setText("");
        address.setText("");
        city.setText("");
        gender.getSelectionModel().select(0);
        dob.getEditor().setText("");
    }

    @FXML
    void doEdit(ActionEvent event) {
        try {
            stmt=con.prepareStatement("update custenroll set cname=?,email=?,address=?,city=?,gender=?,dob=? where mobile=?");
            stmt.setString(1,cname.getText());
            stmt.setString(2,email.getText());
            stmt.setString(3,address.getText());
            stmt.setString(4,city.getText());
//            stmt.setString(5,gender.getEditor().getText());
            stmt.setString(5,gender.getSelectionModel().getSelectedItem());

            LocalDate local=dob.getValue();
            java.sql.Date date=java.sql.Date.valueOf(local);
            stmt.setDate(6, date);
            stmt.setString(7,mobile.getText());
            stmt.executeUpdate();
            System.out.println("Record Update Successsssfulllyyy");;


        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }


    }

    @FXML
    void doEnroll(ActionEvent event)
    {
        try {
//            mobile,cname ,email,address,city,gender,dob,doenrolldate
            stmt=con.prepareStatement("insert into custenroll values(?,?,?,?,?,?,?,current_date())");
            stmt.setString(1,mobile.getText());
//          stmt.setInt(2,Integer.parseInt(age.getSelectionModel().getSelectedItem()));
            stmt.setString(2,cname.getText());
            stmt.setString(3,email.getText());
            stmt.setString(4,address.getText());
            stmt.setString(5,city.getText());
            stmt.setString(6,gender.getSelectionModel().getSelectedItem());
            LocalDate local=dob.getValue();
            java.sql.Date date=java.sql.Date.valueOf(local);
            stmt.setDate(7, date);

            stmt.executeUpdate();
            System.out.println("Record Saved Successsssfulllyyy");

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    @FXML
    void doFetch(ActionEvent event)
    {
        try {
            stmt = con.prepareStatement("select * from custenroll where mobile=?");
            stmt.setString(1,mobile.getText());
            ResultSet records = stmt.executeQuery();
            while (records.next()) {
                String nme = records.getString("cname");//col name
                String eml = records.getString("email");
                String add = records.getString("address");
                String cityy = records.getString("city");
                String genne = records.getString("gender");
                Date dt = records.getDate("dob");

                cname.setText(nme);
                address.setText(add);
                email.setText(eml);
                city.setText(cityy);
//                gender.getSelectionModel().getSelectedItem(genne);  wrong
//                gender.getEditor().setText(genne);  wrong
                gender.setValue(genne);

                dob.setValue(dt.toLocalDate());
            }
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
    }

    Connection con;
    @FXML
    void initialize()
    {

        con= MySQLConnectioinClass.doConnect();
        if(con==null)
            System.out.println("Connction Did not Established");
        else
            System.out.println("Connection Doneeee");

        String gene[]={"Select","Male","Female","Others"};
        gender.getItems().addAll(gene);
        gender.getSelectionModel().select(0);

    }
}
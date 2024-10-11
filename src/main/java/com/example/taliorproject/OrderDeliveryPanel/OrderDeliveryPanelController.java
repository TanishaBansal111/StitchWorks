package com.example.taliorproject.OrderDeliveryPanel;

import com.example.taliorproject.CustomerEnrollment.MySQLConnectioinClass;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import com.example.taliorproject.emails.emailController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class OrderDeliveryPanelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> lblOrid;

    @FXML
    private ListView<String> lblStatus;

    @FXML
    private ListView<String> lblbill;

    @FXML
    private ListView<String> lblitem;

    @FXML
    private Label totbill;

    @FXML
    private TextField txtmob;

    void showMyMsg1(String msg)
    {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Its Header");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    @FXML
    void dodelivered(ActionEvent event)
    {
        float totalBill = 0.0f;

        try{
            stmt = con.prepareStatement("select * from measurements where mobile=? and status=2");
            stmt.setString(1, txtmob.getText());
            ResultSet rec = stmt.executeQuery();
            while(rec.next())
            {


                Float bl=rec.getFloat("bill");
                totalBill+=bl;

            }
            totbill.setText(String.valueOf(totalBill));
            for(String Soid:lblOrid.getItems())
            {
                int oid=Integer.parseInt(Soid);

                stmt=con.prepareStatement("update measurements set status=3 where orderid=? and status=2");

                stmt.setInt(1,oid);
                stmt.executeUpdate();

                System.out.println("delivered Successsssfulllyyy");;
                showMyMsg1("Order delivered successfully and email is sent to registered email of customer");


                stmt1=con.prepareStatement("select distinct email from custenroll where mobile=?");
                stmt1.setString(1,txtmob.getText());
                ResultSet rs1 = stmt1.executeQuery();
                if(rs1.next()) {
                    String emaill = rs1.getString("email");
                    String emailBody = "Dear Customer,\n\n" +
                            "Your order has been delivered successfully. The total bill is: ₹" + totalBill + ".\n\n" +
                            "Thank you for choosing our service!\n\nBest regards,\nStitch Works";
                    try {
                        emailController.SendMail(emaill, "Order Delivered", emailBody);
                    } catch (Exception e) {
                        System.out.println("Error sending email: " + e.getMessage());
                    }
                }
                else {
                    System.out.println("No email found for this mobile number.");
                }
                System.out.println("Email Sent Successsssfulllyyy");;


            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }

    }

    PreparedStatement stmt;
    PreparedStatement stmt1;
    @FXML
    void dofindorders(ActionEvent event) {
        lblbill.getItems().clear();
        lblitem.getItems().clear();
        lblOrid.getItems().clear();
        lblStatus.getItems().clear();
        String mob=txtmob.getText();
        try {
            stmt = con.prepareStatement("select * from measurements where mobile=? and status!=3");
            stmt.setString(1,mob);
            ResultSet rec = stmt.executeQuery();
            while (rec.next())
            {
                int st=rec.getInt("status");
                lblStatus.getItems().add(String.valueOf(st));
                int oid=rec.getInt("orderid");
                lblOrid.getItems().add(String.valueOf(oid));
                Float bl=rec.getFloat("bill");
                lblbill.getItems().add(String.valueOf(bl));
                String drs=rec.getString("dress");
                lblitem.getItems().add(drs);
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    Connection con;
    @FXML
    void initialize() {

        con=MySQLConnectioinClass.doConnect();
        if(con==null)
            System.out.println("Connction Did not Established");
        else
            System.out.println("Connection Doneeee");
    }

}

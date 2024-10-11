package com.example.taliorproject.Measurement;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import com.example.taliorproject.CustomerEnrollment.MySQLConnectioinClass;
import com.example.taliorproject.emails.emailController;
import javafx.stage.Stage;

import javax.swing.*;

public class MeasurementController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField bill;

    @FXML
    private DatePicker dodel;

    @FXML
    private ComboBox<String> dress;

    @FXML
    private TextArea measurement;

    @FXML
    private TextField mobile;

    @FXML
    private ImageView pic;

    @FXML
    private TextField price;

    @FXML
    private ComboBox<String> qty;

    @FXML
    private ComboBox<String> status;

    @FXML
    private ComboBox<String> worker;

    @FXML
    void doClose(ActionEvent event)
    {
        // Get the source Node (e.g., a Button)
        Node source = (Node) event.getSource();

        // Get the Stage from the Node
        Stage stage = (Stage) source.getScene().getWindow();

        // Close the Stage (window)
        stage.close();


    }
    String filePath;

    @FXML
    void doNew(ActionEvent event) {
        mobile.setText("");
        dress.getSelectionModel().clearSelection();
        dodel.getEditor().clear();
        qty.getEditor().setText("");
        price.setText("");
        bill.setText("");
        measurement.setText("");
        worker.getSelectionModel().clearSelection();
        status.getSelectionModel().clearSelection();

//        try {
//            if (!filePath.equals("tailor-measuring.jpg")) {
//                filePath = "tailor-measuring.jpg";
//                pic.setImage(new Image(new FileInputStream(filePath)));
//            }
//        }
//        catch(FileNotFoundException ex)
//        {
//            ex.printStackTrace();
//        }

        pic.setImage(null);

    }
    void showMyMsg(String msg)
    {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Its Header");
        alert.setContentText(msg);

        alert.showAndWait();
    }


    void showMyMsg1(String msg)
    {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Its Header");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    PreparedStatement stmt;
    PreparedStatement stmt1;

    @FXML
    void doSave(ActionEvent event) {
        try {
            stmt=con.prepareStatement("insert into measurements (mobile, dress, pic, dodel, qty, bill, measurement, worker, status, doorder) values(?,?,?,?,?,?,?,?,?,current_date())");
            stmt.setString(1,mobile.getText());
            stmt.setString(2,dress.getSelectionModel().getSelectedItem());
            stmt.setString(3,filePath);

            LocalDate local=dodel.getValue();
            java.sql.Date date=java.sql.Date.valueOf(local);
            stmt.setDate(4, date);

            stmt.setInt(5,Integer.parseInt(qty.getSelectionModel().getSelectedItem()));
            stmt.setFloat(6,Float.parseFloat(bill.getText()));
            stmt.setString(7,measurement.getText());

            stmt.setString(8,worker.getSelectionModel().getSelectedItem());
            stmt.setString(9,status.getSelectionModel().getSelectedItem());
            stmt.executeUpdate();
           System.out.println("Record Saved Successsssfulllyyy");;
            showMyMsg1("Order recieved successfully and email is sent to registered email of customer");


            stmt1=con.prepareStatement("select distinct email from custenroll where mobile=?");
            stmt1.setString(1,mobile.getText());
            ResultSet rs1 = stmt1.executeQuery();
            if(rs1.next()) {
                String emaill = rs1.getString("email");
                try {
                    emailController.SendMail(emaill, "Order Confirmation", "Your order has been received successfully!");
                } catch (Exception e) {
                    System.out.println("Error sending email: " + e.getMessage());
                }
            }
            else {
                System.out.println("No email found for this mobile number.");
            }
            System.out.println("Email Sent Successsssfulllyyy");;

        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }


    }

//    @FXML
//    void doSearch(ActionEvent event) {
//        try{
//            stmt = con.prepareStatement("select * from measurements where mobile=?");
//            stmt.setString(1,mobile.getText());
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                String dressValue = rs.getString("dress");
//                String picValue = rs.getString("pic");
//                Date deliveryDateValue = rs.getDate("dodel");
//                int quantityValue = rs.getInt("qty");
//                float billAmountValue = rs.getFloat("bill");
//                String measurementDetailsValue = rs.getString("measurement");
//                String workerNameValue = rs.getString("worker");
//                String statusValue = rs.getString("status");
//
//                dress.getSelectionModel().select(dressValue);
//                filePath = picValue;
//
//                if (picValue != null && !picValue.isEmpty()) {
//                    File file = new File(picValue);
//                    if (file.exists()) {
//                        Image image = new Image(file.toURI().toString());
//                        pic.setImage(image);
//                    } else {
//                        System.out.println("Image file does not exist at the given path: " + picValue);
//                    }
//                }
//                dodel.setValue(deliveryDateValue.toLocalDate());
//                qty.getSelectionModel().select(String.valueOf(quantityValue));
//                bill.setText(String.valueOf(billAmountValue));
//                price.setText(String.valueOf(billAmountValue/quantityValue));
//                measurement.setText(measurementDetailsValue);
//                worker.getSelectionModel().select(workerNameValue);
//                status.getSelectionModel().select(statusValue);
//            }
//        }
//        catch(Exception exp)
//        {
//            exp.printStackTrace();
//        }
//
//    }


    @FXML
    void doUpdate(ActionEvent event)
    {
        try{
            stmt=con.prepareStatement("update measurements SET mobile=?, dress=?, pic=?, dodel=?, qty=?, bill=?, measurement=?, worker=? WHERE orderid=?");
            stmt.setString(1, mobile.getText());
            stmt.setString(2, dress.getSelectionModel().getSelectedItem());
            stmt.setString(3, filePath);

            LocalDate local = dodel.getValue();
            java.sql.Date date = java.sql.Date.valueOf(local);
            stmt.setDate(4, date);

            stmt.setInt(5, Integer.parseInt(qty.getSelectionModel().getSelectedItem()));
            stmt.setFloat(6, Float.parseFloat(bill.getText()));
            stmt.setString(7, measurement.getText());

            stmt.setString(8, worker.getSelectionModel().getSelectedItem());
            stmt.setString(9, status.getSelectionModel().getSelectedItem());

            TextInputDialog dialog=new TextInputDialog("");
            dialog.setTitle("Input Order id");
            dialog.setContentText("Please enter Order id");

            //Traditional way to get the response value.
            Optional<String> result=dialog.showAndWait();

            if(result.isPresent())
            {
                if(result.get().equals("")) {
                    showMyMsg("Please enter order id");
                }

                else
                {
                    stmt.setString(10, result.get());
                }
            }
            else
            {
                showMyMsg("No order id");
            }


            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Record updated successfully");
            } else {
                System.out.println("No records were updated");
            }


        }
        catch(SQLException | NumberFormatException exp)
        {
            exp.printStackTrace();
        }
    }



    @FXML
    void doUpload(ActionEvent event)
    {
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Select Design Pic:");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("*.*", "*.*")
        );
        File file=chooser.showOpenDialog(null);
        filePath=file.getAbsolutePath();

        try {
            pic.setImage(new Image(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
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

        dress.getItems().addAll("Pent","Shirt","Coat");

        for(int i=1;i<=100;i++)
        {
            qty.getItems().addAll(String.valueOf(i));
        }
        for(int i=1;i<=3;i++)
        {
            status.getItems().addAll(String.valueOf(i));
        }

        dress.setOnAction(event->populateWorkers());
        price.textProperty().addListener((observable,oldValue,newValue)->updateTotalBill());
        qty.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->updateTotalBill());


    }
    private void populateWorkers() {
        worker.getItems().clear();
        String selectedDress = dress.getSelectionModel().getSelectedItem();
        if (selectedDress != null)
        {
            String query = "select wname from workers where splz like ?";
            try (PreparedStatement pst = con.prepareStatement(query))
            {
                pst.setString(1, "%" + selectedDress + "%");
                try (ResultSet records = pst.executeQuery())
                {
                    while (records.next()) {
                        worker.getItems().add(records.getString("wname"));
                    }
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateTotalBill()
    {
        try {
            int quantity = Integer.parseInt(qty.getValue());
            double pricePerUnit = Double.parseDouble(price.getText());
            double totalBill = quantity * pricePerUnit;
            bill.setText(String.format("%.2f", totalBill));
        }
        catch (NumberFormatException e)
        {
            bill.setText("");
        }

    }
}








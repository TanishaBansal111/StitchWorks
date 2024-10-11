package com.example.taliorproject.AdminPanel;
import com.example.taliorproject.CustomerEnrollment.MySQLConnectioinClass;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.taliorproject.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AdminPanelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField dopass;

    @FXML
    private Button custenroll;

    @FXML
    private Button measr;

    @FXML
    private Button measrexpl;

    @FXML
    private Button oderdelivery;

    @FXML
    private Button rdyprod;

    @FXML
    private Button workconsle;

    @FXML
    private Button worklist;


    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;

    @FXML
    private PieChart pichartt;

    @FXML
    private PieChart pieChart;

    @FXML
    void dorefresh(MouseEvent event) {
        dorecc();
        dopi();

    }


    @FXML
    void dounlock(ActionEvent event)
    {
        String password="Tanisha";
        String getpass=dopass.getText();
        if(password.equals(getpass))
        {
            custenroll.setDisable(false);
            measr.setDisable(false);
            measrexpl.setDisable(false);
            oderdelivery.setDisable(false);
            rdyprod.setDisable(false);
            workconsle.setDisable(false);
            worklist.setDisable(false);

        }
    }

    @FXML
    void opencustenroll(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CustomerEnrollmentt/CustomerEnrollView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    @FXML
    void opendel(ActionEvent event)
    {
        try{
            Stage stage=new Stage();
       FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("OrderDeliveryPanell/OrderDeliveryPanel.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void openlist(ActionEvent event)
    {
        try{
            Stage stage=new Stage();
       FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AllWorkerss/AllWorkers.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    void openmeasexp(ActionEvent event) {
        try{
            Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MeasurementsExplorerr/MeasurementsExplorer.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    void openmeasu(ActionEvent event) {
        try{
            Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Measurementt/MeasurementView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    void openready(ActionEvent event) {
        try{
            Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GetReadyProductss/GetReadyProductsView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    void openworker(ActionEvent event) {
        try{
            Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("WorkersConsole/WorkersConsoleView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    void onclickonimage(MouseEvent event)
    {
        dopass.setText("");
        custenroll.setDisable(true);
        measr.setDisable(true);
        measrexpl.setDisable(true);
        oderdelivery.setDisable(true);
        rdyprod.setDisable(true);
        workconsle.setDisable(true);
        worklist.setDisable(true);
    }

    Connection con;
    @FXML
    void initialize() {
        con=MySQLConnectioinClass.doConnect();
        if(con==null)
            System.out.println("Connction Did not Established");
        else
            System.out.println("Connection Doneeee");

        dorecc();
        dopi();

    }
    PreparedStatement stmt;
    void dopi()
    {
        try
        {
            stmt=con.prepareStatement("select dress,Count(*) as count from measurements group by dress");
            ResultSet rec= stmt.executeQuery();
            ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
            while(rec.next())
            {
                String dr=rec.getString("dress");
                int cnt=rec.getInt("count");
                data.add(new PieChart.Data(dr, cnt));
            }
            pichartt.setData(data);

        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void dorecc()
    {
        try{
            stmt=con.prepareStatement("select status,Count(*) as count from measurements group by status");
            ResultSet rec= stmt.executeQuery();
            while(rec.next())
            {
                int stat=rec.getInt("status");
                int cnt=rec.getInt("count");
                if(stat==1)
                {
                    lbl1.setText(String.valueOf(cnt));
                }
                else if(stat==2)
                {
                    lbl2.setText(String.valueOf(cnt));
                }
                else if(stat==3)
                {
                    lbl3.setText(String.valueOf(cnt));
                }
            }

        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

}

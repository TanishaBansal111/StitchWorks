package com.example.taliorproject.GetReadyProducts;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import com.example.taliorproject.CustomerEnrollment.MySQLConnectioinClass;

public class GetReadyProductsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> lblDod;

    @FXML
    private ListView<String> lblDr;

    @FXML
    private ListView<String> lblOr;

    @FXML
    private ComboBox<String> workernme;

    @FXML
    void doAll(ActionEvent event)
    {
        try{
            for(String Soid:lblOr.getItems())
            {
                int oid=Integer.parseInt(Soid);
                stmt=con.prepareStatement("update measurements set status=2 where orderid=?");
                stmt.setInt(1,oid);
                stmt.executeUpdate();
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }

    }

    @FXML
    void doRemove(MouseEvent event)
    {
        if(event.getClickCount()==2)
        {
            try{
                stmt=con.prepareStatement("update measurements set status=2 where orderid=?");
                stmt.setInt(1,Integer.parseInt(lblOr.getSelectionModel().getSelectedItem()));
                stmt.executeUpdate();
                int index=lblOr.getSelectionModel().getSelectedIndex();
                lblOr.getItems().remove(index);
                lblDod.getItems().remove(index);
                lblDr.getItems().remove(index);
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
        }


    }
    PreparedStatement stmt;
    @FXML
    void doFill(ActionEvent event)
    {
        lblOr.getItems().clear();
        lblDod.getItems().clear();
        lblDr.getItems().clear();
        try
        {
            stmt=con.prepareStatement("select * from measurements where status=1 and worker=?");
            stmt.setString(1,workernme.getSelectionModel().getSelectedItem());
            ResultSet records=stmt.executeQuery();
            while(records.next())
            {
                int oid=records.getInt("orderid");
                lblOr.getItems().add(String.valueOf(oid));
                String drs=records.getString("dress");
                lblDr.getItems().add(drs);
                Date dt=records.getDate("dodel");
                lblDod.getItems().add(String.valueOf(dt));
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
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

        fillworkers();
    }

    PreparedStatement pst;
    private void fillworkers()
    {
        try
        {
            pst=con.prepareStatement("select distinct worker from measurements where status=1");
            ResultSet records=pst.executeQuery();
            while(records.next())
            {
                workernme.getItems().add(records.getString("worker"));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

}

package com.example.taliorproject.AllWorkers;


import com.example.taliorproject.CustomerEnrollment.MySQLConnectioinClass;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AllWorkersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<WorkersBean> tblView;

    @FXML
    private ComboBox<String> splzz;


    @FXML
    void onact(ActionEvent event)
    {
        tblView.getColumns().clear();
        tblView.getItems().clear();
        TableColumn<WorkersBean,String> nme=new TableColumn<WorkersBean,String>("Workers Name");
        nme.setCellValueFactory(new PropertyValueFactory<>("wname"));
        nme.setMinWidth(100);

        TableColumn<WorkersBean,String> adr=new TableColumn<WorkersBean,String>("Address");
        adr.setCellValueFactory(new PropertyValueFactory<>("address"));
        adr.setMinWidth(100);

        TableColumn<WorkersBean,String> mob=new TableColumn<WorkersBean,String>("Mobile");
        mob.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mob.setMinWidth(100);

        TableColumn<WorkersBean,String> sp=new TableColumn<WorkersBean,String>("Specialization");
        sp.setCellValueFactory(new PropertyValueFactory<>("splz"));
        sp.setMinWidth(110);

        tblView.getColumns().addAll(nme,adr,mob,sp);
        tblView.setItems(onsel());


    }


    ObservableList<WorkersBean> onsel()
    {

        String itm=splzz.getSelectionModel().getSelectedItem();
        ObservableList<WorkersBean> ary= FXCollections.observableArrayList();
        String query="select * from workers where splz like ? ";
        try(PreparedStatement stmt=con.prepareStatement(query))
        {
            stmt.setString(1,'%'+itm+'%');
            ResultSet rec=stmt.executeQuery();
            while(rec.next())
            {
                String wnme = rec.getString("wname");
                String addr = rec.getString("address");
                String mob = rec.getString("mobile");
                String spll = rec.getString("splz");

                ary.add(new WorkersBean(wnme,addr,mob,spll));

            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return ary;
    }


    Connection con;
    @FXML
    void initialize() {
        con=MySQLConnectioinClass.doConnect();
        if(con==null)
            System.out.println("Connction Did not Established");
        else
            System.out.println("Connection Doneeee");

        splzz.getItems().addAll("Pent","Shirt","Coat");



    }

}
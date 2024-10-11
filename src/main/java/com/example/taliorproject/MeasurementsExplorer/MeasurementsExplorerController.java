package com.example.taliorproject.MeasurementsExplorer;


import com.example.taliorproject.MeasurementsExplorer.MeasBean;
import com.example.taliorproject.CustomerEnrollment.MySQLConnectioinClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class MeasurementsExplorerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> combostatus;

    @FXML
    private ComboBox<String> comboworker;

    @FXML
    private TableView<MeasBean> tblview;

    @FXML
    private TextField txtmob;

    @FXML
    void doShow(ActionEvent event)
    {
        tblview.getColumns().clear();
        tblview.getItems().clear();
        String mode = combostatus.getSelectionModel().getSelectedItem();
        String worker = comboworker.getSelectionModel().getSelectedItem();

        int stat = 0;
        if (mode != null) {
            if (mode.equals("In Process")) {
                stat = 1;
            } else if (mode.equals("Received")) {
                stat = 2;
            } else if (mode.equals("Delivered")) {
                stat = 3;
            }
        }


        TableColumn<MeasBean,String> ordid=new TableColumn<MeasBean,String>("Order Id");
        ordid.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        ordid.setMinWidth(100);

        TableColumn<MeasBean,String> mob=new TableColumn<MeasBean,String>("Mobile");
        mob.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mob.setMinWidth(100);

        TableColumn<MeasBean,String> dres=new TableColumn<MeasBean,String>("Dress");
        dres.setCellValueFactory(new PropertyValueFactory<>("dress"));
        dres.setMinWidth(100);

        TableColumn<MeasBean,String> picc=new TableColumn<MeasBean,String>("Pic");
        picc.setCellValueFactory(new PropertyValueFactory<>("pic"));
        picc.setMinWidth(120);

        TableColumn<MeasBean,String> dtd=new TableColumn<MeasBean,String>("Delivery Date");
        dtd.setCellValueFactory(new PropertyValueFactory<>("dodel"));
        dtd.setMinWidth(100);

        TableColumn<MeasBean,String> qty=new TableColumn<MeasBean,String>("Quantity");
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        qty.setMinWidth(100);

        TableColumn<MeasBean,String> bl=new TableColumn<MeasBean,String>("Bill");
        bl.setCellValueFactory(new PropertyValueFactory<>("bill"));
        bl.setMinWidth(100);

        TableColumn<MeasBean,String> meas=new TableColumn<MeasBean,String>("Measurement");
        meas.setCellValueFactory(new PropertyValueFactory<>("measurement"));
        meas.setMinWidth(150);

        TableColumn<MeasBean,String> wor=new TableColumn<MeasBean,String>("Worker");
        wor.setCellValueFactory(new PropertyValueFactory<>("worker"));
        wor.setMinWidth(100);

        TableColumn<MeasBean,String> statt=new TableColumn<MeasBean,String>("Status");
        statt.setCellValueFactory(new PropertyValueFactory<>("status"));
        statt.setMinWidth(100);

        TableColumn<MeasBean,String> dtr=new TableColumn<MeasBean,String>("Order Date");
        dtr.setCellValueFactory(new PropertyValueFactory<>("doorder"));
        dtr.setMinWidth(100);

        tblview.getColumns().addAll(ordid, mob, dres, picc, dtd, qty, bl, meas,wor,statt, dtr);


        if (worker != null && !worker.isEmpty())
        {
            tblview.setItems(onornwor(stat, worker));
        }
        else
        {
            tblview.setItems(onorder(stat));
        }

    }
    ObservableList<MeasBean> onorder(int stat)
    {
        ObservableList<MeasBean> ary= FXCollections.observableArrayList();
        String query="select * from measurements where status = ? ";
        try(PreparedStatement stmt=con.prepareStatement(query))
        {
            stmt.setInt(1,stat);
            ResultSet rec=stmt.executeQuery();
            while(rec.next())
            {
                int orid=rec.getInt("orderid");
                String mob = rec.getString("mobile");
                String dres = rec.getString("dress");
                String picc= rec.getString("pic");
                Date dtd=rec.getDate("dodel");
                int qty=rec.getInt("qty");
                float bl=rec.getFloat("bill");
                String meas = rec.getString("measurement");
                String worker=rec.getString("worker");
                Date dtr=rec.getDate("doorder");
                ary.add(new MeasBean(orid,mob,dres,picc,dtd,qty,bl,meas,worker,stat,dtr));
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return ary;
    }

    ObservableList<MeasBean> onornwor(int stat,String worker)
    {
        ObservableList<MeasBean> ary= FXCollections.observableArrayList();
        String query="select * from measurements where status = ? and worker =? ";
        try(PreparedStatement stmt=con.prepareStatement(query))
        {
            stmt.setInt(1,stat);
            stmt.setString(2,worker);

            ResultSet rec=stmt.executeQuery();
            while(rec.next())
            {
                int orid=rec.getInt("orderid");
                String mob = rec.getString("mobile");
                String dres = rec.getString("dress");
                String picc= rec.getString("pic");
                Date dtd=rec.getDate("dodel");
                int qty=rec.getInt("qty");
                float bl=rec.getFloat("bill");
                String meas = rec.getString("measurement");
                Date dtr=rec.getDate("doorder");
                ary.add(new MeasBean(orid,mob,dres,picc,dtd,qty,bl,meas,worker,stat,dtr));
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return ary;
    }


    @FXML
    void doexport(ActionEvent event) {
        try{
            writeExcel();
            System.out.println("Exported");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }
    public void writeExcel() throws Exception
    {
        Writer writer=null;
        try
        {
            File file=new File("tailor.csv");
            writer=new BufferedWriter(new FileWriter(file));

            String text="Order ID,Mobile,Dress,Pic,Date of Delivery,Quantity,Bill,Measurement,Worker,Status,Date of Order\n";
            writer.write(text);

            for (MeasBean p : tblview.getItems())
            {
                text=p.getOrderid()+","+p.getMobile()+","+p.getDress()+","+p.getPic()+","+p.getDodel()+","+p.getQty()+","+p.getBill()+","+p.getMeasurement()+","+p.getWorker()+","+p.getStatus()+","+p.getDoorder()+"\n";
                writer.write(text);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            writer.flush();
            writer.close();
        }

    }

    @FXML
    void dofetch(ActionEvent event)
    {
        tblview.getColumns().clear();
        tblview.getItems().clear();

        TableColumn<MeasBean,String> ordid=new TableColumn<MeasBean,String>("Order Id");
        ordid.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        ordid.setMinWidth(100);

        TableColumn<MeasBean,String> mob=new TableColumn<MeasBean,String>("Mobile");
        mob.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mob.setMinWidth(100);

        TableColumn<MeasBean,String> dres=new TableColumn<MeasBean,String>("Dress");
        dres.setCellValueFactory(new PropertyValueFactory<>("dress"));
        dres.setMinWidth(100);

        TableColumn<MeasBean,String> picc=new TableColumn<MeasBean,String>("Pic");
        picc.setCellValueFactory(new PropertyValueFactory<>("pic"));
        picc.setMinWidth(120);

        TableColumn<MeasBean,String> dtd=new TableColumn<MeasBean,String>("Delivery Date");
        dtd.setCellValueFactory(new PropertyValueFactory<>("dodel"));
        dtd.setMinWidth(100);

        TableColumn<MeasBean,String> qty=new TableColumn<MeasBean,String>("Quantity");
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        qty.setMinWidth(100);

        TableColumn<MeasBean,String> bl=new TableColumn<MeasBean,String>("Bill");
        bl.setCellValueFactory(new PropertyValueFactory<>("bill"));
        bl.setMinWidth(100);

        TableColumn<MeasBean,String> meas=new TableColumn<MeasBean,String>("Measurement");
        meas.setCellValueFactory(new PropertyValueFactory<>("measurement"));
        meas.setMinWidth(150);

        TableColumn<MeasBean,String> wor=new TableColumn<MeasBean,String>("Worker");
        wor.setCellValueFactory(new PropertyValueFactory<>("worker"));
        wor.setMinWidth(100);

        TableColumn<MeasBean,String> st=new TableColumn<MeasBean,String>("Status");
        st.setCellValueFactory(new PropertyValueFactory<>("status"));
        st.setMinWidth(100);

        TableColumn<MeasBean,String> dtr=new TableColumn<MeasBean,String>("Order Date");
        dtr.setCellValueFactory(new PropertyValueFactory<>("doorder"));
        dtr.setMinWidth(100);

        tblview.getColumns().addAll(ordid, mob, dres, picc, dtd, qty, bl, meas,wor,st, dtr);
        tblview.setItems(onmobile());
    }

    ObservableList<MeasBean> onmobile()
    {
        String mob=txtmob.getText();
        ObservableList<MeasBean> ary= FXCollections.observableArrayList();
        String query="select * from measurements where mobile = ? ";
        try(PreparedStatement stmt=con.prepareStatement(query))
        {
            stmt.setString(1,mob);
            ResultSet rec=stmt.executeQuery();
            while(rec.next())
            {
                int orid=rec.getInt("orderid");
                String dres = rec.getString("dress");
                String picc= rec.getString("pic");
                Date dtd=rec.getDate("dodel");
                int qty=rec.getInt("qty");
                float bl=rec.getFloat("bill");
                String meas = rec.getString("measurement");
                String worker=rec.getString("worker");
                int st=rec.getInt("status");
                Date dtr=rec.getDate("doorder");
                ary.add(new MeasBean(orid,mob,dres,picc,dtd,qty,bl,meas,worker,st,dtr));
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
    void initialize()
    {
        con=MySQLConnectioinClass.doConnect();
        if(con==null)
            System.out.println("Connction Did not Established");
        else
            System.out.println("Connection Doneeee");

        String combo[]={"In Process","Received","Delivered"};
        combostatus.getItems().addAll(combo);

        addworkers();

    }
    void addworkers()
    {
        try(PreparedStatement stmt=con.prepareStatement("select distinct worker from measurements")){
            ResultSet rec= stmt.executeQuery();
            while(rec.next())
            {
                comboworker.getItems().add(rec.getString("worker"));
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

}

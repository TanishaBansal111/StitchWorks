package com.example.taliorproject.MeasurementsExplorer;

import java.sql.Date;

public class MeasBean {
    int orderid;
    String mobile;
    String dress;
    String pic;
    Date dodel;
    int qty;
    float bill;
    String measurement;
    String worker;
    int status;
    Date doorder;

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDress() {
        return dress;
    }

    public void setDress(String dress) {
        this.dress = dress;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Date getDodel() {
        return dodel;
    }

    public void setDodel(Date dodel) {
        this.dodel = dodel;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getBill() {
        return bill;
    }

    public void setBill(float bill) {
        this.bill = bill;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDoorder() {
        return doorder;
    }

    public void setDoorder(Date doorder) {
        this.doorder = doorder;
    }

    public MeasBean() {
    }

    public MeasBean(int orderid, String mobile, String dress, String pic, Date dodel, int qty, float bill, String measurement, String worker, int status, Date doorder) {
        this.orderid = orderid;
        this.mobile = mobile;
        this.dress = dress;
        this.pic = pic;
        this.dodel = dodel;
        this.qty = qty;
        this.bill = bill;
        this.measurement = measurement;
        this.worker = worker;
        this.status = status;
        this.doorder = doorder;
    }

}

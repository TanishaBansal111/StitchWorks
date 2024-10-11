package com.example.taliorproject.AllWorkers;

public class WorkersBean {
    String wname;
    String address;
    String mobile;
    String splz;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSplz() {
        return splz;
    }

    public void setSplz(String splz) {
        this.splz = splz;
    }

    public WorkersBean(String wname, String address, String mobile, String splz) {
        this.wname = wname;
        this.address = address;
        this.mobile = mobile;
        this.splz = splz;
    }

    public WorkersBean() {
    }
}
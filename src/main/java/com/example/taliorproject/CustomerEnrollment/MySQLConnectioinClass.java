package com.example.taliorproject.CustomerEnrollment;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnectioinClass {
    public static Connection doConnect()
    {
        Connection con = null;

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/javaproject", "root", "T#9758@qlph");
        }

        catch (Exception exp)
        {
            exp.printStackTrace();
        }

        return con;
    }

    public static void main(String ary[]) {
        if (doConnect() == null)
            System.out.println("Sorryyyy");
        else
            System.out.println("Badhaiiii");
    }

}

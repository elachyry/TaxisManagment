package com.gestion_des_taxis.gestion_des_taxis.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    public static Connection con = null;

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Gestion_taxis", "root", "");
        } catch (SQLException ex) {
        }
    }

    public static Connection GetConnection() {
        return con;
    }

}

package com.gestion_des_taxis.gestion_des_taxis.Models;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

public class Admin {

    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String Telephone;
    private String email;
    private String Username;
    private String password;
    private String cin;

    private String query = null;
    private Connection con = null;
    private PreparedStatement preStatment = null;
    private ResultSet resultSet = null;

    public Admin() {
        super();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public Admin(int id, String cin, String nom, String prenom, String telephone, String email, String adresse, String username, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        Telephone = telephone;
        this.email = email;
        Username = username;
        this.password = password;
        this.cin = cin;
    }

    public void addAdmin(String cin, String nom, String prenom, String telephone , String email, String adresse, String username,
                         String password) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "INSERT INTO `admin`(`CNE`, `nom`, `prenom`, `adresse`, `telephone`, `email`, `username`, `password`)" +
                    " VALUES (?,?,?,?,?,?,?,?)";
            preStatment = con.prepareStatement(query);
            preStatment.setString(1, cin);
            preStatment.setString(2, nom);
            preStatment.setString(3, prenom);
            preStatment.setString(4, adresse);
            preStatment.setString(5, telephone);
            preStatment.setString(6, email);
            preStatment.setString(7, username);
            preStatment.setString(8, Tools.encryptPassword(password));
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editAdmin(int id, String cin, String nom, String prenom, String telephone , String email, String adresse) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `admin` SET `cin`=?, `nom`=?,"
                    + "`prenom`=?,`adresse`=?,"
                    + "`telephone`=?,`email`=?"
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.setString(1, cin);
            preStatment.setString(2, nom);
            preStatment.setString(3, prenom);
            preStatment.setString(4, adresse);
            preStatment.setString(5, telephone);
            preStatment.setString(6, email);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void searchAdmin(TextField searchFieldAdmins, ObservableList<Admin> adminsList, TableView<Admin> adminsTable) {
        FilteredList<Admin> filteredDataAdmins = new FilteredList<>(adminsList, e -> true);
        searchFieldAdmins.setOnKeyReleased(e -> {
            searchFieldAdmins.textProperty().addListener((observableValue, OldValue, newValue) -> {
                filteredDataAdmins.setPredicate((Predicate<? super Admin>) admin -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String LowerCaseFilter = newValue.toLowerCase();
                    if (admin.getNom().toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    } else if (admin.getPrenom().toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    } else if (admin.getEmail().toString().toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    }else if (admin.getTelephone().toString().contains(LowerCaseFilter)) {
                        return true;
                    }else if (admin.getCin().toString().contains(LowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Admin> sortedListAdmins = new SortedList<>(filteredDataAdmins);
            sortedListAdmins.comparatorProperty().bind(adminsTable.comparatorProperty());
            adminsTable.setItems(sortedListAdmins);
        });
    }

    public void exportAdmin() {
        try {
            query = "SELECT * FROM admin";
            con = DataBaseConnection.GetConnection();
            preStatment = con.prepareStatement(query);
            resultSet = preStatment.executeQuery();

            XSSFWorkbook XFWB = new XSSFWorkbook();
            XSSFSheet XFSheet = XFWB.createSheet("Admins List");
            XSSFRow HeaderRow = XFSheet.createRow(0);
            HeaderRow.createCell(0).setCellValue("Id");
            HeaderRow.createCell(1).setCellValue("CIN");
            HeaderRow.createCell(2).setCellValue("Nom");
            HeaderRow.createCell(3).setCellValue("Prénom");
            HeaderRow.createCell(4).setCellValue("Adresse");
            HeaderRow.createCell(5).setCellValue("Téléphone");
            HeaderRow.createCell(6).setCellValue("E-mail");
            HeaderRow.createCell(7).setCellValue("Username");
            HeaderRow.createCell(8).setCellValue("Password");
            HeaderRow.createCell(9).setCellValue("Deleted At");


            int RowNum = 1;
            while (resultSet.next()) {
                XSSFRow Row = XFSheet.createRow(RowNum);
                Row.createCell(0).setCellValue(resultSet.getInt(1));
                Row.createCell(1).setCellValue(resultSet.getString(2));
                Row.createCell(2).setCellValue(resultSet.getString(3));
                Row.createCell(3).setCellValue(resultSet.getString(4));
                Row.createCell(4).setCellValue(resultSet.getString(5));
                Row.createCell(5).setCellValue(resultSet.getString(6));
                Row.createCell(6).setCellValue(resultSet.getString(7));
                Row.createCell(7).setCellValue(resultSet.getString(8));
                Row.createCell(8).setCellValue(resultSet.getString(9));
                Row.createCell(8).setCellValue(resultSet.getString(10));
                RowNum++;
            }
            try (FileOutputStream FileOutStr = new FileOutputStream("Exported/Admins.xlsx")) {
                XFWB.write(FileOutStr);
                System.out.println("Okay");
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void resetpassword(int id) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `admin` SET `password`=?"
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            String password = Tools.generatePassword();
            preStatment.setString(1, Tools.encryptPassword(password));
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void hardDeleteAdmin(int id) {
        try {
            query = "DELETE FROM `admin` WHERE id  = " + id;
            con = DataBaseConnection.GetConnection();
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
        }
    }

    public void softDelete(int id)
    {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `admin` SET `soft_delete`= CURRENT_TIMESTAMP "
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void restorAdmin(int id)
    {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `admin` SET `soft_delete`= null "
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}

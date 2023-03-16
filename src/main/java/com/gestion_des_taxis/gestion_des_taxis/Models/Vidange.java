package com.gestion_des_taxis.gestion_des_taxis.Models;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.function.Predicate;

public class Vidange {

    private int id;
    private String immatriculation;
    private Date dateVidange;
    private String killometrage;
    private String killometrageNextVidange;
    private String typeHuile;
    private String quantityHuile;
    private double litrePrix;
    private double prixHT;
    private double prixTTC;

    private String query = null;
    private Connection con = null;
    private PreparedStatement preStatment = null;
    private ResultSet resultSet = null;

    public Vidange() {
    }

    public Vidange(int id, String immatriculation, Date dateVidange, String killometrage, String killometrageNextVidange, String typeHuile, String quantityHuile, double litrePrix, double prixHT, double prixTTC) {
        this.id = id;
        this.immatriculation = immatriculation;
        this.dateVidange = dateVidange;
        this.killometrage = killometrage;
        this.killometrageNextVidange = killometrageNextVidange;
        this.typeHuile = typeHuile;
        this.quantityHuile = quantityHuile;
        this.litrePrix = litrePrix;
        this.prixHT = prixHT;
        this.prixTTC = prixTTC;
    }

    public double getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public Date getDateVidange() {
        return dateVidange;
    }

    public void setDateVidange(Date dateVidange) {
        this.dateVidange = dateVidange;
    }

    public String getKillometrage() {
        return killometrage;
    }

    public void setKillometrage(String killometrage) {
        this.killometrage = killometrage;
    }

    public String getKillometrageNextVidange() {
        return killometrageNextVidange;
    }

    public void setKillometrageNextVidange(String killometrageNextVidange) {
        this.killometrageNextVidange = killometrageNextVidange;
    }

    public String getTypeHuile() {
        return typeHuile;
    }

    public void setTypeHuile(String typeHuile) {
        this.typeHuile = typeHuile;
    }

    public String getQuantityHuile() {
        return quantityHuile;
    }

    public void setQuantityHuile(String quantityHuile) {
        this.quantityHuile = quantityHuile;
    }

    public double getLitrePrix() {
        return litrePrix;
    }

    public void setLitrePrix(double litrePrix) {
        this.litrePrix = litrePrix;
    }

    public double getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(double prixHT) {
        this.prixHT = prixHT;
    }

    public void addVidange(String matricule, String dateVidange, String killometrage, String nextVidange, String typeHuile, String quantityHuile, double litrePrix, double prixHT, double prixTTC) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "INSERT INTO `vidange`(`immatriculation`, `dateVidange`, `killometrage`, `killometrageNextVidange`, `typeHuile`, `quantityHuile`, `litrePrix`, `prixHT`, `prixTTC`)" +
                    " VALUES (?,?,?,?,?,?,?,?,?)";
            preStatment = con.prepareStatement(query);
            preStatment.setString(1, matricule);
            preStatment.setString(2, dateVidange);
            preStatment.setString(3, killometrage);
            preStatment.setString(4, nextVidange);
            preStatment.setString(5, typeHuile);
            preStatment.setString(6, quantityHuile);
            preStatment.setDouble(7, litrePrix);
            preStatment.setDouble(8, prixHT);
            preStatment.setDouble(9, prixTTC);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editVidange(int id, String matricule, String dateVidange, String killometrage, String nextVidange, String typeHuile, String quantityHuile, double litrePrix, double prixHT, double prixTTC) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `vidange` SET `immatriculation`=?,`dateVidange`=?,`killometrage`=?,`killometrageNextVidange`=?," +
                    "`typeHuile`=?,`quantityHuile`=?,`litrePrix`=?,`prixHT`=?,`prixTTC`=? WHERE id = " + id;
            preStatment = con.prepareStatement(query);
            preStatment.setString(1, matricule);
            preStatment.setString(2, dateVidange);
            preStatment.setString(3, killometrage);
            preStatment.setString(4, nextVidange);
            preStatment.setString(5, typeHuile);
            preStatment.setString(6, quantityHuile);
            preStatment.setDouble(7, litrePrix);
            preStatment.setDouble(8, prixHT);
            preStatment.setDouble(9, prixTTC);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void SearchVidange(TextField searchFieldVidanges, ObservableList<Vidange> vidangesList, TableView<Vidange> vidangesTable) {
        FilteredList<Vidange> filteredDataClients = new FilteredList<>(vidangesList, e -> true);
        searchFieldVidanges.setOnKeyReleased(e -> {
            searchFieldVidanges.textProperty().addListener((observableValue, OldValue, newValue) -> {
                filteredDataClients.setPredicate((Predicate<? super Vidange>) client -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String LowerCaseFilter = newValue.toLowerCase();
                    if (client.immatriculation.toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Vidange> sortedListVidanges = new SortedList<>(filteredDataClients);
            sortedListVidanges.comparatorProperty().bind(vidangesTable.comparatorProperty());
            vidangesTable.setItems(sortedListVidanges);
        });
    }
    public void exportVidanges() {
        try {
            query = "SELECT * FROM vidange";
            con = DataBaseConnection.GetConnection();
            preStatment = con.prepareStatement(query);
            resultSet = preStatment.executeQuery();

            XSSFWorkbook XFWB = new XSSFWorkbook();
            XSSFSheet XFSheet = XFWB.createSheet("Vidanges List");
            XSSFRow HeaderRow = XFSheet.createRow(0);
            HeaderRow.createCell(0).setCellValue("Id");
            HeaderRow.createCell(1).setCellValue("Matricule ");
            HeaderRow.createCell(2).setCellValue("Date Vidange");
            HeaderRow.createCell(3).setCellValue("Killometrage");
            HeaderRow.createCell(4).setCellValue("Killometrage Next Vidange");
            HeaderRow.createCell(5).setCellValue("Type Huile");
            HeaderRow.createCell(6).setCellValue("Quantitée Huile");
            HeaderRow.createCell(7).setCellValue("Litre Prix");
            HeaderRow.createCell(8).setCellValue("Prix HT");
            HeaderRow.createCell(9).setCellValue("Prix TTC");
            HeaderRow.createCell(10).setCellValue("Deleted");


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
                Row.createCell(9).setCellValue(resultSet.getString(10));
                Row.createCell(10).setCellValue(resultSet.getString(11));
                RowNum++;
            }
            try (FileOutputStream FileOutStr = new FileOutputStream("Exported/Vidanges.xlsx")) {
                XFWB.write(FileOutStr);
                System.out.println("Okay");
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void softDelete(int id)
    {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `vidange` SET `soft_delete`= CURRENT_TIMESTAMP "
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void hardDelete(int id)
    {
        try {
            query = "DELETE FROM `vidange` WHERE id  = " + id;
            con = DataBaseConnection.GetConnection();
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
        }
    }

    public void restorVidange(int id)
    {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `vidange` SET `soft_delete`= null "
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

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

public class Reparation {

    private int id;
    private String immatricule;
    private Date dateReparation;
    private String designation;
    private int quantite;
    private double unitPrix;
    private double prixHT;
    private double prixTTC;

    private String query = null;
    private Connection con = null;
    private PreparedStatement preStatment = null;
    private ResultSet resultSet = null;

    public Reparation() {
    }

    public Reparation(int id, String immatricule, Date dateReparation, String designation, int quantite, double unitPrix, double prixHT, double prixTTC) {
        this.id = id;
        this.immatricule = immatricule;
        this.dateReparation = dateReparation;
        this.designation = designation;
        this.quantite = quantite;
        this.unitPrix = unitPrix;
        this.prixHT = prixHT;
        this.prixTTC = prixTTC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImmatricule() {
        return immatricule;
    }

    public void setImmatricule(String immatricule) {
        this.immatricule = immatricule;
    }

    public Date getDateReparation() {
        return dateReparation;
    }

    public void setDateReparation(Date dateReparation) {
        this.dateReparation = dateReparation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getUnitPrix() {
        return unitPrix;
    }

    public void setUnitPrix(double unitPrix) {
        this.unitPrix = unitPrix;
    }

    public double getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(double prixHT) {
        this.prixHT = prixHT;
    }

    public double getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public void addReparation(String matricule, String dateReparation, String designation, int quantite, double unitPrix, double prixHT, double prixTTC) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "INSERT INTO `reparation`(`immatriculation`, `dateReparation`, `designation`, `quantity`, `unitPrix`, `prixUT`, `prixTTC`)" +
                    " VALUES (?,?,?,?,?,?,?)";
            preStatment = con.prepareStatement(query);
            preStatment.setString(1, matricule);
            preStatment.setString(2, dateReparation);
            preStatment.setString(3, designation);
            preStatment.setInt(4, quantite);
            preStatment.setDouble(5, unitPrix);
            preStatment.setDouble(6, prixHT);
            preStatment.setDouble(7, prixTTC);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void editReparation(int id, String matricule, String dateReparation, String designation, int quantite, double unitPrix, double prixHT, double prixTTC) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `reparation` SET `immatriculation`=?, `dateReparation` = ?, `designation`=?, " +
                    "`quantity`=?,`unitPrix`=?,`prixUT`=?,`prixTTC`=? " +
                    "WHERE `id` = " + id;
        preStatment = con.prepareStatement(query);
        preStatment.setString(1, matricule);
        preStatment.setString(2, dateReparation);
        preStatment.setString(3, designation);
        preStatment.setInt(4, quantite);
        preStatment.setDouble(5, unitPrix);
        preStatment.setDouble(6, prixHT);
        preStatment.setDouble(7, prixTTC);
        preStatment.execute();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }

    public void SearchReparation(TextField searchFieldReparations, ObservableList<Reparation> reparationsList, TableView<Reparation> reparationsTable) {
        FilteredList<Reparation> filteredDataClients = new FilteredList<>(reparationsList, e -> true);
        searchFieldReparations.setOnKeyReleased(e -> {
            searchFieldReparations.textProperty().addListener((observableValue, OldValue, newValue) -> {
                filteredDataClients.setPredicate((Predicate<? super Reparation>) client -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String LowerCaseFilter = newValue.toLowerCase();
                    if (client.getImmatricule().toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Reparation> sortedListReparations = new SortedList<>(filteredDataClients);
            sortedListReparations.comparatorProperty().bind(reparationsTable.comparatorProperty());
            reparationsTable.setItems(sortedListReparations);
        });
    }

    public void exportReparatins() {
        try {
            query = "SELECT * FROM reparation";
            con = DataBaseConnection.GetConnection();
            preStatment = con.prepareStatement(query);
            resultSet = preStatment.executeQuery();

            XSSFWorkbook XFWB = new XSSFWorkbook();
            XSSFSheet XFSheet = XFWB.createSheet("Réparation List");
            XSSFRow HeaderRow = XFSheet.createRow(0);
            HeaderRow.createCell(0).setCellValue("Id");
            HeaderRow.createCell(1).setCellValue("Matricule ");
            HeaderRow.createCell(2).setCellValue("Date Reparation\t");
            HeaderRow.createCell(3).setCellValue("designation");
            HeaderRow.createCell(4).setCellValue("Quantitée");
            HeaderRow.createCell(5).setCellValue("Prix Unitére");
            HeaderRow.createCell(6).setCellValue("Prix HT");
            HeaderRow.createCell(7).setCellValue("Prix TTC");
            HeaderRow.createCell(8).setCellValue("Deleted");


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
                RowNum++;
            }
            try (FileOutputStream FileOutStr = new FileOutputStream("Exported/Réparations.xlsx")) {
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
            query = "UPDATE `reparation` SET `soft_delete`= CURRENT_TIMESTAMP "
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
            query = "DELETE FROM `reparation` WHERE id  = " + id;
            con = DataBaseConnection.GetConnection();
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
        }
    }

    public void restorReparation(int id)
    {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `reparation` SET `soft_delete`= null "
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}

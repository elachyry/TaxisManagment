package com.gestion_des_taxis.gestion_des_taxis.Models;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Cursor;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

public class Demande {

    private int id;
    private String immatriculation;
    private String client;
    private String chauffeur;
    private String status;

    private ImageView delete;
    private ImageView icon;

    private String query = null;
    private Connection con = null;
    private PreparedStatement preStatment = null;
    private ResultSet resultSet = null;

    public Demande() {
    }

    public Demande(int id, String immatriculation, String client, String chauffeur, String status,ImageView delete) throws FileNotFoundException {
        this.id = id;
        this.immatriculation = immatriculation;
        this.client = client;
        this.chauffeur = chauffeur;

        this.status = status;
        this.delete = delete;
        FileInputStream input = new FileInputStream("src/main/resources/Images/icons8_Person_32px.png");
        Image image = new Image(input);
        icon = new ImageView(image);

        FileInputStream input2 = new FileInputStream("src/main/resources/Images/trash_128px.png");
        Image image2 = new Image(input2);
        this.delete = new ImageView(image2);
        this.delete.setFitWidth(35);
        this.delete.setFitHeight(35);
        this.delete.setStyle("-fx-effect: dropshadow(gaussian, #FFDE00, 8, 0.4, 0.0, 0.0);");
        this.delete.setCursor(Cursor.TEXT);
        this.delete.setOnMouseClicked((MouseEvent event) -> {
            this.delete.setVisible(!this.delete.isVisible());
            System.out.println("clicked");
        });

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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(String chauffeur) {
        this.chauffeur = chauffeur;
    }





    public String getStatus() {
        return status;
    }

    public void setStatuts(String statuts) {
        this.status = status;
    }


    public void deleteDemande(){}
    public void searchDemande(){}
    public void exportDemandes(String fileName){}

    public  void addDemande(String vtr, String client, String chauffeur, String statuts
    ) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "INSERT INTO `demande`(`voiture`, `client`, `chauffeur`, `status`)" +
                    " VALUES (?,?,?,?)";
            preStatment = con.prepareStatement(query);
            preStatment.setString(1, vtr);
            preStatment.setString(2, client);
            preStatment.setString(3, chauffeur);
            preStatment.setString(4, statuts);

            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void editdemande(int id, String voiture, String client, String chauffeur, String status) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `demande` SET `voiture`=?,"
                    + "`client`=?,`chauffeur`=?,"
                    + "`status`=?"
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.setString(1, voiture);
            preStatment.setString(2, client);
            preStatment.setString(3, chauffeur);
            preStatment.setString(4, status);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    public void searchDemande(TextField searchFieldDemandes, ObservableList<Demande> demandesList, TableView<Demande> demandesTable) {
        FilteredList<Demande> filteredDatademandes = new FilteredList<>(demandesList, e -> true);
        searchFieldDemandes.setOnKeyReleased(e -> {
            searchFieldDemandes.textProperty().addListener((observableValue, OldValue, newValue) -> {
                filteredDatademandes.setPredicate((Predicate<? super Demande>) demande -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String LowerCaseFilter = newValue.toLowerCase();
                    if (demande.getImmatriculation().toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    } else if (demande.getClient().toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    } else if (demande.getChauffeur().toString().toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    }else if (demande.getStatus().toString().contains(LowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Demande> sortedListClients = new SortedList<>(filteredDatademandes);
            sortedListClients.comparatorProperty().bind(demandesTable.comparatorProperty());
            demandesTable.setItems(sortedListClients);
        });
    }
    public void exportDemande() {
        try {
            query = "SELECT * FROM demande";
            con = DataBaseConnection.GetConnection();
            preStatment = con.prepareStatement(query);
            resultSet = preStatment.executeQuery();

            XSSFWorkbook XFWB = new XSSFWorkbook();
            XSSFSheet XFSheet = XFWB.createSheet("Demandes List");
            XSSFRow HeaderRow = XFSheet.createRow(0);
            HeaderRow.createCell(0).setCellValue("Id");
            HeaderRow.createCell(1).setCellValue("voiture");
            HeaderRow.createCell(2).setCellValue("client");
            HeaderRow.createCell(3).setCellValue("chauffeur");
            HeaderRow.createCell(4).setCellValue("status");



            int RowNum = 1;
            while (resultSet.next()) {
                XSSFRow Row = XFSheet.createRow(RowNum);
                Row.createCell(0).setCellValue(resultSet.getInt(1));
                Row.createCell(1).setCellValue(resultSet.getString(2));
                Row.createCell(2).setCellValue(resultSet.getString(3));
                Row.createCell(3).setCellValue(resultSet.getString(4));
                Row.createCell(4).setCellValue(resultSet.getString(5));

                RowNum++;
            }
            try (FileOutputStream FileOutStr = new FileOutputStream("Exported/Demandes.xlsx")) {
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
            query = "UPDATE `demande` SET `soft_delete`= CURRENT_TIMESTAMP "
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void hardDeleteDemande(int id) {
        try {
            query = "DELETE FROM `demande` WHERE id  = " + id;
            con = DataBaseConnection.GetConnection();
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
        }
    }
    public void restorDemande(int id)
    {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `demande` SET `soft_delete`= null "
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

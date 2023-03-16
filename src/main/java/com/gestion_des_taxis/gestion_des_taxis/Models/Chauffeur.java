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

public class Chauffeur {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String Telephone;
    private String email;
    private String Username;
    private String password;
    private String CNE;
    private ImageView delete;
    private ImageView icon;
    private String numPermis;

    private String query = null;
    private Connection con = null;
    private PreparedStatement preStatment = null;
    private ResultSet resultSet = null;

    public Chauffeur() {

    }

    public Chauffeur(String CNE,String numPermis,String nom, String prenom, String adresse, String telephone, String email,
                     String username, String password, int id, ImageView delete) throws FileNotFoundException {
        this.CNE=CNE;
        this.numPermis=numPermis;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        Telephone = telephone;
        this.email = email;
        Username = username;
        this.password = password;
        this.id = id;
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






    public void addChauffeur(String numPermis ,String CNE,String nom, String prenom, String adresse, String telephone, String email,
                             String username, String password) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "INSERT INTO `chauffeur`(`numPermis`,`CNE`,`nom`, `prenom`, `adresse`, `telephone`, `email`, `username`, `password`)" +
                    " VALUES (?,?,?,?,?,?,?,?,?)";
            preStatment = con.prepareStatement(query);
            preStatment.setString(1, numPermis);
            preStatment.setString(2, CNE);
            preStatment.setString(3, nom);
            preStatment.setString(4, prenom);
            preStatment.setString(5, adresse);
            preStatment.setString(6, telephone);
            preStatment.setString(7, email);
            preStatment.setString(8, username);
            preStatment.setString(9, Tools.encryptPassword(password));
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void editChauffeur(int id,String CNE,String numPermis, String nom, String prenom, String adresse, String telephone, String email) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `chauffeur` SET  `CNE`=?,`numPermis`=?,`nom`=?,"
                    + "`prenom`=?,`adresse`=?,"
                    + "`telephone`=?,`email`=?"
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.setString(1,CNE);
            preStatment.setString(2,numPermis);
            preStatment.setString(3, nom);
            preStatment.setString(4, prenom);
            preStatment.setString(5, adresse);
            preStatment.setString(6, telephone);
            preStatment.setString(7, email);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void hardDeletionChauffeur(int id)  {
        try {
            query = "DELETE FROM `chauffeur`  where id = "+ id;
            con = DataBaseConnection.GetConnection();
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        }catch (SQLException ex) {
        }


    }

    public void searchChauffeur(TextField searchFieldChauffeurs, ObservableList<Chauffeur> chauffeursList, TableView<Chauffeur> chauffeursTable) {

        FilteredList<Chauffeur> filteredDataChauffeur = new FilteredList<>(chauffeursList, e -> true);
        searchFieldChauffeurs.setOnKeyReleased(e -> {
            searchFieldChauffeurs.textProperty().addListener((observableValue, OldValue, newValue) -> {
                filteredDataChauffeur.setPredicate((Predicate<? super Chauffeur>) chauffeur -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String LowerCaseFilter = newValue.toLowerCase();
                    if (chauffeur.getNom().toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    } else if (chauffeur.getPrenom().toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    } else if (chauffeur.getEmail().toString().toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    }else if (chauffeur.getTelephone().toString().contains(LowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Chauffeur> sortedListChauffeur = new SortedList<>(filteredDataChauffeur);
            sortedListChauffeur.comparatorProperty().bind(chauffeursTable.comparatorProperty());
            chauffeursTable.setItems(sortedListChauffeur);
        });
    }

    public void exportChauffeur() {
        try {
            query = "SELECT * FROM chauffeur";
            con = DataBaseConnection.GetConnection();
            preStatment = con.prepareStatement(query);
            resultSet = preStatment.executeQuery();

            XSSFWorkbook XFWB = new XSSFWorkbook();
            XSSFSheet XFSheet = XFWB.createSheet("Chauffeurs List");
            XSSFRow HeaderRow = XFSheet.createRow(0);
            HeaderRow.createCell(0).setCellValue("Id");
            HeaderRow.createCell(1).setCellValue("CNE");
            HeaderRow.createCell(2).setCellValue("Numero Permis");
            HeaderRow.createCell(3).setCellValue("Nom");
            HeaderRow.createCell(4).setCellValue("Prénom");
            HeaderRow.createCell(5).setCellValue("Adresse");
            HeaderRow.createCell(6).setCellValue("Téléphone");
            HeaderRow.createCell(7).setCellValue("E-mail");
            HeaderRow.createCell(8).setCellValue("Username");
            HeaderRow.createCell(9).setCellValue("Password");
            HeaderRow.createCell(9).setCellValue("Deleted");


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
                Row.createCell(9).setCellValue(resultSet.getString(11));
                RowNum++;
            }
            try (FileOutputStream FileOutStr = new FileOutputStream("Exported/Chauffeurs.xlsx")) {
                XFWB.write(FileOutStr);
                System.out.println("Okay");
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void softDelete(int id) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `chauffeur` SET `soft_delete`= CURRENT_TIMESTAMP "
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void resetpassword(int id) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `chauffeur` SET `password`=?"
                    + "WHERE `Id`='" + this.id + "'";
            preStatment = con.prepareStatement(query);
            String password = Tools.generatePassword();
            preStatment.setString(1, Tools.encryptPassword(password));
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void restorChauffeur(int id)
    {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `chauffeur` SET `soft_delete`= null "
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return Telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return password;
    }

    public String getCNE() {
        return CNE;
    }

    public String getNumPermis() {
        return numPermis;
    }

    public void setNumPermis(String numPermis) {
        this.numPermis = numPermis;
    }


}

package com.gestion_des_taxis.gestion_des_taxis.Models;

import com.gestion_des_taxis.gestion_des_taxis.Controllers.ClientsController;
import com.gestion_des_taxis.gestion_des_taxis.Models.Personne;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
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

public class Client  {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String Telephone;
    private String email;
    private String Username;
    private String password;
    private String cin;
    private ImageView delete;
    private ImageView icon;

    private String query = null;
    private Connection con = null;
    private PreparedStatement preStatment = null;
    private ResultSet resultSet = null;

    public Client() {

    }

    public Client(String nom, String prenom, String adresse, String telephone, String email,
                    String username, String password, int id, ImageView icon) throws FileNotFoundException {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        Telephone = telephone;
        this.email = email;
        Username = username;
        this.password = password;
        this.id = id;
        this.icon = icon;
        FileInputStream input = new FileInputStream("src/main/resources/Images/icons8_Person_32px.png");
        Image image = new Image(input);
        this.icon = new ImageView(image);
        this.icon.setFitWidth(35);
        this.icon.setFitHeight(35);

//        FileInputStream input2 = new FileInputStream("src/main/resources/Images/trash_128px.png");
//        Image image2 = new Image(input2);
//        this.delete = new ImageView(image2);
//        this.delete.setFitWidth(35);
//        this.delete.setFitHeight(35);
//        this.delete.setStyle("-fx-effect: dropshadow(gaussian, #FFDE00, 8, 0.4, 0.0, 0.0);");
//        this.delete.setCursor(Cursor.TEXT);
//        this.delete.setOnMouseClicked((MouseEvent event) -> {
//            this.delete.setVisible(!this.delete.isVisible());
//            System.out.println("clicked");
//        });
//        this.delete.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                ObservableList<Client> clientsList = ClientsController.clientsList;
//                System.out.println("clicked");
//                for (Client client : clientsList) {
//                    if (client.getDelete() == delete) {
//                        System.out.println("clicked inside if");
//                        ClientsController cc = new ClientsController();
//                        cc.softDelete(client.getId());
//                    }
//                }
//                event.consume();
//            }
//        });
//        action.setCursor(Cursor.HAND);
//        action.setFocusTraversable(false);
//        this.action.setStyle("-fx-background-color : #05071F;\n" +
//                    "-fx-text-fill : #e7e5e5;\n" +
//                    "-fx-border-color:  #FFDE00;\n" +
//                    "-fx-border-radius: 15;");
//        this.action.setOnMouseEntered(mouseEvent -> {
//            this.action.setStyle("-fx-background-color : #FFDE00;\n" +
//                    "-fx-text-fill: black;\n" +
//                    "-fx-cursor: hand;\n" +
//                    "-fx-border-radius: 15;");
//        });
//        this.action.setOnMouseExited(mouseEvent -> {
//            this.action.setStyle("-fx-background-color : #05071F;\n" +
//                    "-fx-text-fill : #e7e5e5;\n" +
//                    "-fx-border-color:  #FFDE00;\n" +
//                    "-fx-border-radius: 15;");
//        });

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

    public String getAdress() {
        return adresse;
    }

    public void setAdress(String adress) {
        this.adresse = adress;
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
    public void addClient(String nom, String prenom, String adresse, String telephone, String email,
                          String username, String password) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "INSERT INTO `client`(`nom`, `prenom`, `adresse`, `telephone`, `email`, `username`, `password`)" +
                    " VALUES (?,?,?,?,?,?,?)";
            preStatment = con.prepareStatement(query);
            preStatment.setString(1, nom);
            preStatment.setString(2, prenom);
            preStatment.setString(3, adresse);
            preStatment.setString(4, telephone);
            preStatment.setString(5, email);
            preStatment.setString(6, username);
            preStatment.setString(7, Tools.encryptPassword(password));
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editClient(int id, String nom, String prenom, String adresse, String telephone, String email) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `client` SET `nom`=?,"
                    + "`prenom`=?,`adresse`=?,"
                    + "`telephone`=?,`email`=?"
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.setString(1, nom);
            preStatment.setString(2, prenom);
            preStatment.setString(3, adresse);
            preStatment.setString(4, telephone);
            preStatment.setString(5, email);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public ImageView getDelete() {
        return delete;
    }

    public void setDelete(ImageView delete) {
        this.delete = delete;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public void hardDeleteClient(int id) {
        try {
            query = "DELETE FROM `client` WHERE id  = " + id;
            con = DataBaseConnection.GetConnection();
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
        }
    }

    public void searchClient(TextField searchFieldClients, ObservableList<Client> clientsList, TableView<Client> clientsTable) {
        FilteredList<Client> filteredDataClients = new FilteredList<>(clientsList, e -> true);
        searchFieldClients.setOnKeyReleased(e -> {
            searchFieldClients.textProperty().addListener((observableValue, OldValue, newValue) -> {
                filteredDataClients.setPredicate((Predicate<? super Client>) client -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String LowerCaseFilter = newValue.toLowerCase();
                    if (client.getNom().toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    } else if (client.getPrenom().toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    } else if (client.getEmail().toString().toLowerCase().contains(LowerCaseFilter)) {
                        return true;
                    }else if (client.getTelephone().toString().contains(LowerCaseFilter)) {
                    return true;
                }
                    return false;
                });
            });
            SortedList<Client> sortedListClients = new SortedList<>(filteredDataClients);
            sortedListClients.comparatorProperty().bind(clientsTable.comparatorProperty());
            clientsTable.setItems(sortedListClients);
        });
    }

    public void exportClient() {
        try {
            query = "SELECT * FROM client";
            con = DataBaseConnection.GetConnection();
            preStatment = con.prepareStatement(query);
            resultSet = preStatment.executeQuery();

            XSSFWorkbook XFWB = new XSSFWorkbook();
            XSSFSheet XFSheet = XFWB.createSheet("CLients List");
            XSSFRow HeaderRow = XFSheet.createRow(0);
            HeaderRow.createCell(0).setCellValue("Id");
            HeaderRow.createCell(1).setCellValue("Nom");
            HeaderRow.createCell(2).setCellValue("Prénom");
            HeaderRow.createCell(3).setCellValue("Adresse");
            HeaderRow.createCell(4).setCellValue("Téléphone");
            HeaderRow.createCell(5).setCellValue("E-mail");
            HeaderRow.createCell(6).setCellValue("Username");
            HeaderRow.createCell(7).setCellValue("Password");
            HeaderRow.createCell(8).setCellValue("Deleted At");


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
            try (FileOutputStream FileOutStr = new FileOutputStream("Exported/Clients.xlsx")) {
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
            query = "UPDATE `client` SET `password`=?"
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            String password = Tools.generatePassword();
            preStatment.setString(1, Tools.encryptPassword(password));
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void softDelete(int id)
    {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `client` SET `soft_delete`= CURRENT_TIMESTAMP "
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void restorClient(int id)
    {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `client` SET `soft_delete`= null "
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}

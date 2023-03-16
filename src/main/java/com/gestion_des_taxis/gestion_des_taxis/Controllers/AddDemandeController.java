package com.gestion_des_taxis.gestion_des_taxis.Controllers;

import animatefx.animation.BounceIn;
import animatefx.animation.Shake;
import com.gestion_des_taxis.gestion_des_taxis.Models.Client;
import com.gestion_des_taxis.gestion_des_taxis.Models.DataBaseConnection;
import com.gestion_des_taxis.gestion_des_taxis.Models.Demande;
import com.gestion_des_taxis.gestion_des_taxis.Models.Tools;
import com.jfoenix.controls.JFXComboBox;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class AddDemandeController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorPane;



    @FXML
    private TextField voitureVal;

    @FXML
    private TextField clientVal;

    @FXML
    private TextField chauffeurVal;

    @FXML
    private TextField statusVal;



    @FXML
    private MFXButton ajouterBtn;

    @FXML
    private MFXButton modifierBtn;

    @FXML
    private JFXComboBox cbchauffeur;

    @FXML
    private JFXComboBox cbclient;

    @FXML
    private JFXComboBox cbvoiture;

    @FXML
    private Label pageTitle;

    @FXML
    private Label errorMessage;

    private String query = null;

    private Connection con = null;

    private PreparedStatement PpeStatment = null;

    private ResultSet resultSet = null;
    private Demande demande = null;

    public static int demandeId;

    ObservableList<String> authorList = FXCollections.observableArrayList();


    @FXML
    private List<String> getData() {



        // Define the data you will be returning, in this case, a List of Strings for the ComboBox
        List<String> options = new ArrayList<>();

        try {
            con = DataBaseConnection.GetConnection();
            String query = "select `immatriculation` From `voiture`";
            PpeStatment = con.prepareStatement(query);
            resultSet  = PpeStatment.executeQuery();

            while (resultSet.next()) {
                options.add(resultSet .getString("immatriculation"));
            }

            PpeStatment.close();
            resultSet.close();

            // Return the List
            return options;

        } catch (SQLException ex) {

            return null;
        }
    }   @FXML
    private List<String> gettClients() {



        // Define the data you will be returning, in this case, a List of Strings for the ComboBox
        List<String> options = new ArrayList<>();

        try {
            con = DataBaseConnection.GetConnection();
            String query = "select `prenom` From `client`";
            PpeStatment = con.prepareStatement(query);
            resultSet  = PpeStatment.executeQuery();

            while (resultSet.next()) {
                options.add(resultSet .getString("prenom"));
            }

            PpeStatment.close();
            resultSet.close();

            // Return the List
            return options;

        } catch (SQLException ex) {

            return null;
        }
    }
    @FXML
    private List<String> gettChauffeurs() {
        // Define the data you will be returning, in this case, a List of Strings for the ComboBox
        List<String> options = new ArrayList<>();

        try {
            con = DataBaseConnection.GetConnection();
            String query = "select `CNE` From `chauffeur`";
            PpeStatment = con.prepareStatement(query);
            resultSet  = PpeStatment.executeQuery();

            while (resultSet.next()) {
                options.add(resultSet .getString("CNE"));
            }

            PpeStatment.close();
            resultSet.close();

            // Return the List
            return options;

        } catch (SQLException ex) {

            return null;
        }
    }


    public void ajouter(MouseEvent event) throws Exception {

       /*ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Option 1",
                        "Option 2",
                        "Option 3"
                );
       cbvoiture = new JFXComboBox(options);



       /* cbvoiture.getItems().add("choice1");
        cbvoiture.getItems().add("choice2");
        cbvoiture.getItems().add("choice3");
        cbvoiture.getItems().add("choice4");

 /*try {


        con = DataBaseConnection.GetConnection();
        query = "select `immatriculation` From `voiture`";

        PpeStatment = con.prepareStatement(query);
        resultSet  = PpeStatment.executeQuery();
        ObservableList data = FXCollections.observableArrayList();
        while ( resultSet.next()) {
            data.add(resultSet.getString(2));
        }
      cbvoiture.setItems(data);
        } catch (SQLException e) {
      throw new RuntimeException(e);
  }*/


        String vtr = cbvoiture.getSelectionModel().getSelectedItem().toString();
        String client = cbclient.getSelectionModel().getSelectedItem().toString();

        String chauffeur = cbchauffeur.getSelectionModel().getSelectedItem().toString();
        String status = statusVal.getText();
            System.out.println(vtr);
        if (vtr.isEmpty() || client.isEmpty() || chauffeur.isEmpty()
               ) {
            if (vtr.isEmpty()) {
               cbvoiture.setStyle("-fx-border-color: red;");
                new Shake(cbvoiture).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le voiture est obligatoire!");
                new BounceIn(errorMessage).play();
            } else {
                cbvoiture.setStyle("-fx-border-color: white;");
            }
            if (client.isEmpty()) {
               cbclient.setStyle("-fx-border-color: red;");
                new Shake(cbclient).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le client est obligatoire!");
                new BounceIn(errorMessage).play();
            } else {
                cbclient.setStyle("-fx-border-color: white;");
            }
            if (chauffeur.isEmpty()) {
                cbchauffeur.setStyle("-fx-border-color: red;");
                new Shake(cbchauffeur).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le chauffeur est obligatoire!");
                new BounceIn(errorMessage).play();
            } else {
                cbchauffeur.setStyle("-fx-border-color: white;");
            }

            if (status.isEmpty()) {
                statusVal.setStyle("-fx-border-color: red;");
                new Shake(statusVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("status est obligatoire!");
                new BounceIn(errorMessage).play();
            } else {
                statusVal.setStyle("-fx-border-color: white;");
            }


            System.out.println("test if");
        } else {
            demande = new Demande();
                System.out.println("test if else fin");
                demande.addDemande(vtr, client, chauffeur, status);


                Tools.alert(stackPane, anchorPane, "La demande a été ajouté avec succès!");

               /* voitureVal.setText(null);
                clientVal.setText(null);
                chauffeurVal.setText(null);*/
                statusVal.setText(null);
    }     }


    public void modifier(MouseEvent mouseEvent) {
        con = DataBaseConnection.GetConnection();

        String voiture = cbvoiture.getSelectionModel().getSelectedItem().toString();
        String client = cbclient.getSelectionModel().getSelectedItem().toString();

        String chauffeur = cbchauffeur.getSelectionModel().getSelectedItem().toString();
        String status = statusVal.getText();


        if (voiture.isEmpty() || client.isEmpty() || chauffeur.isEmpty() || status.isEmpty()
                ) {
            if (voiture.isEmpty()) {
                cbvoiture.setStyle("-fx-border-color: red;");
                new Shake(cbvoiture).play();
                errorMessage.setVisible(true);
                errorMessage.setText("La voiture est obligatoire!");
                new BounceIn(errorMessage).play();
            } else {
               cbvoiture.setStyle("-fx-border-color: white;");
            }
            if (client.isEmpty()) {
                cbclient.setStyle("-fx-border-color: red;");
                new Shake(cbclient).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le client est obligatoire!");
                new BounceIn(errorMessage).play();
            } else {
                clientVal.setStyle("-fx-border-color: white;");
            }
            if (chauffeur.isEmpty()) {
                cbchauffeur.setStyle("-fx-border-color: red;");
                new Shake(cbchauffeur).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le chauffeur est obligatoire!");
                new BounceIn(errorMessage).play();
            } else {
                chauffeurVal.setStyle("-fx-border-color: white;");
            }

            if (status.isEmpty()) {
                statusVal.setStyle("-fx-border-color: red;");
                new Shake(statusVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("status est obligatoire!");
                new BounceIn(errorMessage).play();
            } else {
                statusVal.setStyle("-fx-border-color: white;");
            }

            System.out.println("test if");
        } else {
            System.out.println("test else");
            demande = new Demande();


                demande.editdemande(demandeId, voiture, client, chauffeur, status);


                Tools.alert(stackPane, anchorPane, "La demande a été modifier avec succès!");

        }
    }















































    public void fillFields(int id, String voiture, String client, String chauffeur, String status) {
        demandeId = id;

        cbvoiture.setValue(voiture);
       cbclient.setValue(client);
       cbchauffeur.setValue(chauffeur);
        statusVal.setText(status);

    }


    public void setAjouterBtn(boolean visible) {
        this.ajouterBtn.setVisible(visible);
    }

    public void setModifierBtn(boolean visible) {
        this.modifierBtn.setVisible(visible);
    }

    public void pageTitle(String TitleLabel) {
        this.pageTitle.setText(TitleLabel);
    }

    @FXML
    void anuller(MouseEvent event) {
        Stage stage = (Stage)statusVal.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setVisible(false);
        cbvoiture.setItems(FXCollections.observableArrayList(getData()));
        cbclient.setItems(FXCollections.observableArrayList(gettClients()));
        cbchauffeur.setItems(FXCollections.observableArrayList(gettChauffeurs()));
    }



    }



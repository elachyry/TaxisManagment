package com.gestion_des_taxis.gestion_des_taxis.Controllers;

import com.gestion_des_taxis.gestion_des_taxis.Models.Client;
import com.gestion_des_taxis.gestion_des_taxis.Models.DataBaseConnection;
import com.gestion_des_taxis.gestion_des_taxis.Models.Tools;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.ResourceBundle;

public class AddClientController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField nomVal;

    @FXML
    private TextField prenomVal;

    @FXML
    private TextField adresseVal;

    @FXML
    private TextField telephoneVal;

    @FXML
    private TextField emailVal;

    @FXML
    private MFXButton ajouterBtn;

    @FXML
    private MFXButton modifierBtn;

    @FXML
    private Label pageTitle;

    @FXML
    private Label errorMessage;

    private String query = null;

    private Connection con = null;

    private PreparedStatement PreStatment = null;

    private ResultSet resultSet = null;
    private Client client = null;

    public static int clientId;



    @FXML
    public void ajouter(MouseEvent event) throws Exception {
        con = DataBaseConnection.GetConnection();
        String nom = nomVal.getText();
        String prenom = prenomVal.getText();
        String adresse = adresseVal.getText();
        String telephone = telephoneVal.getText();
        String email = emailVal.getText();
        if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || email.isEmpty() || (!(email.matches("[a-zA-Z0-9_@.]{10,}")))
                || (!(telephone.matches("[0-9+]{9,}")))) {
            if (nom.isEmpty()) {
                nomVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(nomVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le nom est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                nomVal.setStyle("-fx-border-color: white;");
            }
            if (prenom.isEmpty()) {
                prenomVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(prenomVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le prenom est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                prenomVal.setStyle("-fx-border-color: white;");
            }
            if (telephone.isEmpty()) {
                telephoneVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(telephoneVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le numero de téléphone est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                telephoneVal.setStyle("-fx-border-color: white;");
            }

            if (email.isEmpty()) {
                emailVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(emailVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("L'E-mail est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                emailVal.setStyle("-fx-border-color: white;");
            }
            if (!(email.matches("[a-zA-Z0-9_@.]{10,}")) && (!(email.isEmpty()))) {
                emailVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(emailVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("E-mail est incorrect!");
                new animatefx.animation.BounceIn(errorMessage).play();
            }
            if (!(telephone.matches("[0-9+]{9,}")) && (!(telephone.isEmpty()))) {
                telephoneVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(telephoneVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le numero de téléphone est incorrect!");
                new animatefx.animation.BounceIn(errorMessage).play();
            }
            System.out.println("test if");
        } else {
            System.out.println("test else");
            String username = nom + "_" + prenom + "_" + new Random(9);
            String password = Tools.generatePassword();
            client = new Client();
            int telephoneCount = 0;
            int emailCount = 0;
                    ///Test CNE And Moblie if are already exist
                System.out.println("test if isedit");
                Tools T = new Tools();
                telephoneCount = T.testColumn("client", "telephone", telephone.trim().toLowerCase());
                emailCount = T.testColumn("client", "email", email.trim().toLowerCase());
                    if (telephoneCount > 0 || emailCount > 0 ) {
                        System.out.println("test if telephoneCount > 0 || emailCount > 0");
                        if (telephoneCount > 0) {
                            System.out.println("test if telephoneCount > 0");
                            Tools.alert(stackPane, anchorPane, "Ce numéro de téléphone existe déjà!");
                        }
                        if (emailCount > 0) {
                            System.out.println("test if emailCount > 0");
                            Tools.alert(stackPane, anchorPane, "Ce E-mail existe déjà!");
                        }
                    } else {
                        System.out.println("test if else fin");
                        client.addClient(nom, prenom, adresse, telephone, email, username, password);
                    }
                    if (!(telephoneCount > 0 || emailCount > 0)) {
                        Tools.alert(stackPane, anchorPane, "Le client a été ajouté avec succès!");
                        Tools t = new Tools(email, nom, prenom, username, password, "New Account");
                        t.start();
                        nomVal.setText(null);
                        prenomVal.setText(null);
                        emailVal.setText(null);
                        telephoneVal.setText(null);
                        adresseVal.setText(null);
                    }
            }
    }

    public void modifier(MouseEvent mouseEvent) {
        con = DataBaseConnection.GetConnection();
        String nom = nomVal.getText();
        String prenom = prenomVal.getText();
        String adresse = adresseVal.getText();
        String telephone = telephoneVal.getText();
        String email = emailVal.getText();
        if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || email.isEmpty() || (!(email.matches("[a-zA-Z0-9_@.]{10,}")))
                || (!(telephone.matches("[0-9+]{9,}")))) {
            if (nom.isEmpty()) {
                nomVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(nomVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le nom est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                nomVal.setStyle("-fx-border-color: white;");
            }
            if (prenom.isEmpty()) {
                prenomVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(prenomVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le prenom est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                prenomVal.setStyle("-fx-border-color: white;");
            }
            if (telephone.isEmpty()) {
                telephoneVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(telephoneVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le numero de téléphone est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                telephoneVal.setStyle("-fx-border-color: white;");
            }

            if (email.isEmpty()) {
                emailVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(emailVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("L'E-mail est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                emailVal.setStyle("-fx-border-color: white;");
            }
            if (!(email.matches("[a-zA-Z0-9_@.]{10,}")) && (!(email.isEmpty()))) {
                emailVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(emailVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("E-mail est incorrect!");
                new animatefx.animation.BounceIn(errorMessage).play();
            }
            if (!(telephone.matches("[0-9+]{9,}")) && (!(telephone.isEmpty()))) {
                telephoneVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(telephoneVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le numero de téléphone est incorrect!");
                new animatefx.animation.BounceIn(errorMessage).play();
            }
            System.out.println("test if");
        } else {
            System.out.println("test else");
            client = new Client();
            int telephoneCount = 0;
            int emailCount = 0;
            ///Test CNE And Moblie if are already exist
            System.out.println("test if isedit");
            Tools T = new Tools();
            telephoneCount = T.testColumnForEdit("client", "telephone", telephone.trim().toLowerCase(), clientId);
            emailCount = T.testColumnForEdit("client", "email", email.trim().toLowerCase(), clientId);
            if (telephoneCount > 0 || emailCount > 0 ) {
                System.out.println("test if telephoneCount > 0 || emailCount > 0");
                if (telephoneCount > 0) {
                    System.out.println("test if telephoneCount > 0");
                    Tools.alert(stackPane, anchorPane, "Ce numéro de téléphone existe déjà!");
                }
                if (emailCount > 0) {
                    System.out.println("test if emailCount > 0");
                    Tools.alert(stackPane, anchorPane, "Ce E-mail existe déjà!");
                }
            } else {
                System.out.println("test if else fin");
                client.editClient(clientId, nom, prenom, adresse, telephone, email);
            }
            if (!(telephoneCount > 0 || emailCount > 0)) {
                Tools.alert(stackPane, anchorPane, "Le client a été modifier avec succès!");
            }
        }
    }

    public void fillFields(int id, String nom, String prenom, String telephone, String email, String adresse) {
        clientId = id;
        nomVal.setText(nom);
        prenomVal.setText(prenom);
        emailVal.setText(email);
        telephoneVal.setText(telephone);
        adresseVal.setText(adresse);
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
        Stage stage = (Stage) nomVal.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setVisible(false);
    }


}

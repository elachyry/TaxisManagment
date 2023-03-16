package com.gestion_des_taxis.gestion_des_taxis.Controllers;

import com.gestion_des_taxis.gestion_des_taxis.Models.Admin;
import com.gestion_des_taxis.gestion_des_taxis.Models.DataBaseConnection;
import com.gestion_des_taxis.gestion_des_taxis.Models.Tools;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.util.Random;
import java.util.ResourceBundle;

public class AddAdminController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label pageTitle;

    @FXML
    private TextField nomVal;

    @FXML
    private TextField prenomVal;

    @FXML
    private TextField telephoneVal;

    @FXML
    private TextField emailVal;

    @FXML
    private TextField adresseVal;

    @FXML
    private MFXButton ajouterBtn;

    @FXML
    private MFXButton modifierBtn;

    @FXML
    private Label errorMessage;

    @FXML
    private TextField cinVal;

    private Connection con = null;

    private PreparedStatement PreStatment = null;

    private ResultSet resultSet = null;
    private Admin admin = null;

    public static int adminId;

    @FXML
    void ajouter(MouseEvent event) {
        con = DataBaseConnection.GetConnection();
        String cin = cinVal.getText();
        String nom = nomVal.getText();
        String prenom = prenomVal.getText();
        String adresse = adresseVal.getText();
        String telephone = telephoneVal.getText();
        String email = emailVal.getText();
        if (nom.isEmpty() || cin.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || email.isEmpty() || (!(email.matches("[a-zA-Z0-9_@.]{10,}")))
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
            if (cin.isEmpty()) {
                cinVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(cinVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le nom est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                cinVal.setStyle("-fx-border-color: white;");
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
            admin = new Admin();
            int telephoneCount = 0;
            int emailCount = 0;
            int cinCount = 0;
            ///Test CNE And Moblie if are already exist
            Tools T = new Tools();
            telephoneCount = T.testColumn("admin", "telephone", telephone.trim().toLowerCase());
            emailCount = T.testColumn("admin", "email", email.trim().toLowerCase());
            cinCount = T.testColumn("admin", "cin", email.trim().toLowerCase());
            if (telephoneCount > 0 || emailCount > 0 || cinCount > 0) {
                if (telephoneCount > 0) {
                    Tools.alert(stackPane, anchorPane, "Ce numéro de téléphone existe déjà!");
                }
                if (emailCount > 0) {
                    Tools.alert(stackPane, anchorPane, "Ce E-mail existe déjà!");
                }
                if (cinCount > 0) {
                    Tools.alert(stackPane, anchorPane, "Cette CIN existe déjà!");
                }
            } else {

                admin.addAdmin(cin, nom, prenom, adresse, telephone, email, username, password);
            }
            if (!(telephoneCount > 0 || emailCount > 0)) {
                Tools.alert(stackPane, anchorPane, "L'admin a été ajouté avec succès!");
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

    @FXML
    void anuller(MouseEvent event) {
        Stage stage = (Stage) nomVal.getScene().getWindow();
        stage.close();
    }

    @FXML
    void modifier(MouseEvent event) {
        String cin = cinVal.getText();
        String nom = nomVal.getText();
        String prenom = prenomVal.getText();
        String adresse = adresseVal.getText();
        String telephone = telephoneVal.getText();
        String email = emailVal.getText();
        if (nom.isEmpty() || cin.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || email.isEmpty() || (!(email.matches("[a-zA-Z0-9_@.]{10,}")))
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
            if (cin.isEmpty()) {
                cinVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(cinVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le nom est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                cinVal.setStyle("-fx-border-color: white;");
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
        } else {
            String username = nom + "_" + prenom + "_" + new Random(9);
            String password = Tools.generatePassword();
            admin = new Admin();
            int telephoneCount = 0;
            int emailCount = 0;
            int cinCount = 0;
            ///Test CNE And Moblie if are already exist
            Tools T = new Tools();
            telephoneCount = T.testColumnForEdit("admin", "telephone", telephone.trim().toLowerCase(), adminId);
            emailCount = T.testColumnForEdit("admin", "email", email.trim().toLowerCase(), adminId);
            cinCount = T.testColumnForEdit("admin", "cin", email.trim().toLowerCase(), adminId);
            if (telephoneCount > 0 || emailCount > 0 || cinCount > 0) {
                if (telephoneCount > 0) {
                    Tools.alert(stackPane, anchorPane, "Ce numéro de téléphone existe déjà!");
                }
                if (emailCount > 0) {
                    Tools.alert(stackPane, anchorPane, "Ce E-mail existe déjà!");
                }
                if (cinCount > 0) {
                    Tools.alert(stackPane, anchorPane, "Cette CIN existe déjà!");
                }
            } else {
                admin.editAdmin(adminId, cin, nom, prenom, adresse, telephone, email);
            }
            if (!(telephoneCount > 0 || emailCount > 0)) {
                Tools.alert(stackPane, anchorPane, "L'admin a été modifier avec succès!");
            }
        }
    }

    public void fillFields(int id, String cin, String nom, String prenom, String telephone, String email, String adresse) {
        System.out.println(telephone);
        System.out.println(email);
        adminId = id;
        cinVal.setText(cin);
        nomVal.setText(nom);
        prenomVal.setText(prenom);
        emailVal.setText(email);
        telephoneVal.setText(adresse);
        adresseVal.setText(telephone);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setVisible(false);
    }
}

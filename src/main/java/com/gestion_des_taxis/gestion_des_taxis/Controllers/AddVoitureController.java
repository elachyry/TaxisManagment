package com.gestion_des_taxis.gestion_des_taxis.Controllers;

import com.gestion_des_taxis.gestion_des_taxis.Models.DataBaseConnection;
import com.gestion_des_taxis.gestion_des_taxis.Models.Tools;
import com.gestion_des_taxis.gestion_des_taxis.Models.Voiture;
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

public class AddVoitureController implements Initializable {

    @FXML
    private MFXButton ajouterBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField carbuVal;

    @FXML
    private Label errorMessage;

    @FXML
    private TextField etatVal;

    @FXML
    private TextField immaVal;

    @FXML
    private TextField marqueVal;

    @FXML
    private TextField modelVal;

    @FXML
    private MFXButton modifierBtn;

    @FXML
    private Label pageTitle;

    @FXML
    private StackPane stackPane;

    private String query = null;

    private Connection con = null;

    private PreparedStatement PpeStatment = null;

    private ResultSet resultSet = null;
    private Voiture voiture = null;

    public static int voitureId;


    @FXML
    void ajouter(MouseEvent event) throws Exception {
        con = DataBaseConnection.GetConnection();
        String immatriculation = immaVal.getText();
        String model = modelVal.getText();
        String marque = marqueVal.getText();
        String carburant = carbuVal.getText();
        String etat = etatVal.getText();

        System.out.println(immatriculation);
        System.out.println(marque);
        System.out.println(model);
        System.out.println(carburant);
        System.out.println(etat);
        System.out.println(immatriculation.isEmpty() || model.isEmpty() || marque.isEmpty() || carburant.isEmpty() || etat.isEmpty());
        if (immatriculation.isEmpty() || model.isEmpty() || marque.isEmpty() || carburant.isEmpty() || etat.isEmpty()) {
            if (immatriculation.isEmpty()) {
                immaVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(immaVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("L'immatriculation est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                immaVal.setStyle("-fx-border-color: white;");
            }
            if (model.isEmpty()) {
                modelVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(modelVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le model est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                modelVal.setStyle("-fx-border-color: white;");
            }
            if (marque.isEmpty()) {
                marqueVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(marqueVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("La marque est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                marqueVal.setStyle("-fx-border-color: white;");
            }

            if (carburant.isEmpty()) {
                carbuVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(carbuVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le carburant est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            }
        } else {
            carbuVal.setStyle("-fx-border-color: white;");

            System.out.println("test else");
            String username = immatriculation + "_" + marque + "_" + new Random(9);
            //           String password = Tools.generatePassword();
            voiture = new Voiture();
            int immaCount = 0;
            ///Test CNE And Moblie if are already exist
            System.out.println("test if isedit");
            Tools T = new Tools();
            immaCount = T.testColumn("voiture", "immatriculation", immatriculation.trim().toLowerCase());
            if (immaCount > 0) {
                System.out.println("test if immaCount > 0");
                if (immaCount > 0) {
                    System.out.println("test if immaCount > 0");
                    Tools.alert(stackPane, anchorPane, "Cet immatriculation existe déjà!");
                }
            } else {
                System.out.println("test if else fin");
                voiture.addVoiture(immatriculation, marque, model, carburant, etat);
            }
            if (!(immaCount > 0)) {
                Tools.alert(stackPane, anchorPane, "La voiture a été ajouté avec succès!");
                immaVal.setText(null);
                modelVal.setText(null);
                marqueVal.setText(null);
                carbuVal.setText(null);
                etatVal.setText(null);
            }
        }

    }

    public void modifier(MouseEvent mouseEvent) {
        con = DataBaseConnection.GetConnection();
        String immatriculation = immaVal.getText();
        String marque = marqueVal.getText();
        String model = modelVal.getText();
        String carburant = carbuVal.getText();
        String etat = etatVal.getText();
        System.out.println(immatriculation);
        System.out.println(marque);
        System.out.println(model);
        System.out.println(carburant);
        System.out.println(etat);

        if (immatriculation.isEmpty() || model.isEmpty() || marque.isEmpty() || carburant.isEmpty() || etat.isEmpty()) {
            if (immatriculation.isEmpty()) {
                immaVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(immaVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("L'immatriculation est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                immaVal.setStyle("-fx-border-color: white;");
            }
            if (model.isEmpty()) {
                modelVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(modelVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le model est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                modelVal.setStyle("-fx-border-color: white;");
            }
            if (marque.isEmpty()) {
                marqueVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(marqueVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("La marque est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                marqueVal.setStyle("-fx-border-color: white;");
            }

            if (carburant.isEmpty()) {
                carbuVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(carbuVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le carburant est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            }
            } else {
                carbuVal.setStyle("-fx-border-color: white;");

                System.out.println("test else");
                String username = immatriculation + "_" + marque + "_" + new Random(9);
                //           String password = Tools.generatePassword();
                voiture = new Voiture();
                int immaCount = 0;
                System.out.println("test if isedit");
                Tools T = new Tools();
                immaCount = T.testColumnForEdit("voiture", "immatriculation", immatriculation.trim().toLowerCase(), voitureId);
                if (immaCount > 0) {
                    System.out.println("test if immaCount > 0");
                    if (immaCount > 0) {
                        System.out.println("test if immaCount > 0");
                        Tools.alert(stackPane, anchorPane, "Cet immatriculation existe déjà!");
                    }
                } else {
                    System.out.println("test if else fin");
                    voiture.editVoiture(voitureId, immatriculation, model, marque, carburant, etat);
                }
                if (!(immaCount > 0)) {
                    Tools.alert(stackPane, anchorPane, "La voiture a été modifier avec succès!");
                }
            }


    }

    public void fillFields(int id, String immatriculation, String model, String marque, String carburant, String etat) {
        voitureId = id;
        immaVal.setText(immatriculation);
        modelVal.setText(model);
        marqueVal.setText(marque);
        carbuVal.setText(carburant);
        etatVal.setText(etat);
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
        Stage stage = (Stage) immaVal.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setVisible(false);
    }


}

package com.gestion_des_taxis.gestion_des_taxis.Controllers;

import com.gestion_des_taxis.gestion_des_taxis.Models.DataBaseConnection;
import com.gestion_des_taxis.gestion_des_taxis.Models.Reparation;
import com.gestion_des_taxis.gestion_des_taxis.Models.Tools;
import com.gestion_des_taxis.gestion_des_taxis.Models.Vidange;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddVidangeController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label pageTitle;

    @FXML
    private TextField voitureVal;

    @FXML
    private TextField killometrageVid;

    @FXML
    private TextField prixUnitVal;

    @FXML
    private TextField prixTTCVal;

    @FXML
    private MFXButton ajouterBtn;

    @FXML
    private MFXButton modifierBtn;

    @FXML
    private Label errorMessage;

    @FXML
    private ComboBox<String> matriculeVal;

    @FXML
    private MFXDatePicker dateVidangeVal;

    @FXML
    private TextField quantityVal;

    @FXML
    private TextField prixUTVal;

    @FXML
    private TextField nextVidangeVal;

    @FXML
    private ComboBox<String> typeHuileVal;


    private String query = null;

    private Connection con = null;

    private PreparedStatement PreStatment = null;

    private ResultSet resultSet = null;

    private Vidange vidange = null;

    public static int vidangeId;

    ObservableList<String> matriculesList = FXCollections.observableArrayList();
    ObservableList<String> typeHuileList = FXCollections.observableArrayList();
    @FXML
    void ajouter(MouseEvent event) {
        con = DataBaseConnection.GetConnection();
        String matricule = String.valueOf(matriculeVal.getSelectionModel().getSelectedItem());
        String dateVidange = dateVidangeVal.getText();
        String quantity = quantityVal.getText();
        String killometrage = killometrageVid.getText();
        String nextVidange = nextVidangeVal.getText();
        String typeHuile = String.valueOf(typeHuileVal.getSelectionModel().getSelectedItem());
        String prixUnit = prixUnitVal.getText();
        String prixHT = prixUTVal.getText();
        String prixTTC = prixTTCVal.getText();
        if (matriculeVal.getSelectionModel().isEmpty() || typeHuileVal.getSelectionModel().isEmpty() || dateVidange.isEmpty() || quantity.isEmpty() || killometrage.isEmpty() || prixUnit.isEmpty() || prixTTC.isEmpty() || prixHT.isEmpty()) {
            if (matriculeVal.getSelectionModel().isEmpty()) {
                matriculeVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(matriculeVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le matricule est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                matriculeVal.setStyle("-fx-border-color: white;");
            }
            if (typeHuileVal.getSelectionModel().isEmpty()) {
                typeHuileVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(typeHuileVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le type de huile est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                typeHuileVal.setStyle("-fx-border-color: white;");
            }
            if (dateVidange.isEmpty()) {
                dateVidangeVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(dateVidangeVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("La date de vidange est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                dateVidangeVal.setStyle("-fx-border-color: white;");
            }
            if (quantity.isEmpty()) {
                quantityVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(quantityVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("La quantiteé de huile est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                quantityVal.setStyle("-fx-border-color: white;");
            }

            if (killometrage.isEmpty()) {
                killometrageVid.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(killometrageVid).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le killometrage est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                killometrageVid.setStyle("-fx-border-color: white;");
            }
            if (prixUnit.isEmpty()) {
                prixUnitVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(prixUnitVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le prix unitaire est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                prixUnitVal.setStyle("-fx-border-color: white;");
            }

            if (prixHT.isEmpty()) {
                prixUTVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(prixUTVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le prix HT est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                prixUTVal.setStyle("-fx-border-color: white;");
            }

            if (prixTTC.isEmpty()) {
                prixTTCVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(prixTTCVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le prix TTC est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                prixTTCVal.setStyle("-fx-border-color: white;");
            }

            System.out.println("test if");
        } else {
            System.out.println("test else");
            vidange = new Vidange();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = dateVidangeVal.getValue();
            vidange.addVidange(matricule, formatter.format(date), killometrage, nextVidange, typeHuile, quantity,Double.parseDouble(prixUnit), Double.parseDouble(prixHT), Double.parseDouble(prixTTC));

            Tools.alert(stackPane, anchorPane, "La vidange a été ajouté avec succès!");

            matriculeVal.setValue(null);
            killometrageVid.setText("0");
            nextVidangeVal.setText("0");
            typeHuileVal.setValue(null);
            quantityVal.setText("0");
            prixTTCVal.setText("0");
            prixUTVal.setText("0");
            prixUnitVal.setText("0");
            dateVidangeVal.setValue(null);
            voitureVal.setText(null);
        }
    }

    @FXML
    void anuller(MouseEvent event) {
        Stage stage = (Stage) matriculeVal.getScene().getWindow();
        stage.close();
    }

    public void fillFields(int id, String matricule, String dateReparation, String killonetrage, String nextVidange, String typeHuile,String quantite, String unitPrix, String prixHT, String prixTTC) {
        vidangeId = id;
        matriculeVal.setValue(matricule);
        killometrageVid.setText(killonetrage);
        dateVidangeVal.setValue(LocalDate.parse(dateReparation));
        quantityVal.setText(quantite);
        nextVidangeVal.setText(nextVidange);
        typeHuileVal.setValue(typeHuile);
        prixTTCVal.setText(prixTTC);
        prixUnitVal.setText(unitPrix);
        prixUTVal.setText(prixHT);
        con = DataBaseConnection.GetConnection();
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM `voiture` WHERE immatriculation = '" + matricule + "'");
            rs.next();
            voitureVal.setText(rs.getString(3) + " " + rs.getString(4));
        } catch (SQLException ex) {
        }
    }

    @FXML
    void fillVoitureMarque(ActionEvent event) {
        String value = matriculeVal.getValue();
        con = DataBaseConnection.GetConnection();
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM `voiture` WHERE immatriculation = '" + value + "'");
            rs.next();
            voitureVal.setText(rs.getString(3) + " " + rs.getString(4));
        } catch (SQLException ex) {
        }
    }

    public void onlyDigits(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            textField.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }

    public void fillMatriculsCombobox() {
        con = DataBaseConnection.GetConnection();
        Statement St;
        matriculesList.clear();
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM `voiture`");
            while (rs.next()) {
                matriculesList.add(rs.getString(2));
            }
        } catch (SQLException ex) {
        }
        matriculeVal.setItems(null);
        matriculeVal.setItems(matriculesList);
    }

    public void fillTypeHuileCombobox() {
        typeHuileList.add("10W30");
        typeHuileList.add("10W40");
        typeHuileList.add("10W60");
        typeHuileList.add("15W40");
        typeHuileList.add("20W60");
        typeHuileList.add("20W50");
        typeHuileList.add("5W30");
        typeHuileList.add("5W40");
        typeHuileList.add("0W30");
        typeHuileVal.setItems(null);
        typeHuileVal.setItems(typeHuileList);
    }

    public void calculePrix(TextField textField)
    {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!(prixUnitVal.getText().isEmpty() || quantityVal.getText().isEmpty()))
            {
                prixUTVal.setText(String.valueOf((Double.parseDouble(quantityVal.getText()) * Double.parseDouble(prixUnitVal.getText()))/10));
                prixTTCVal.setText(String.valueOf(((Double.parseDouble(quantityVal.getText()) * Double.parseDouble(prixUnitVal.getText()) * 0.2 ) + Double.parseDouble(quantityVal.getText()) * Double.parseDouble(prixUnitVal.getText()))/10));
            }
        });
    }

    @FXML
    void modifier(MouseEvent event) {
        con = DataBaseConnection.GetConnection();
        String matricule = String.valueOf(matriculeVal.getSelectionModel().getSelectedItem());
        String dateVidange = dateVidangeVal.getText();
        String quantity = quantityVal.getText();
        String killometrage = killometrageVid.getText();
        String nextVidange = nextVidangeVal.getText();
        String typeHuile = String.valueOf(typeHuileVal.getSelectionModel().getSelectedItem());
        String prixUnit = prixUnitVal.getText();
        String prixHT = prixUTVal.getText();
        String prixTTC = prixTTCVal.getText();
        if (matriculeVal.getSelectionModel().isEmpty() || typeHuileVal.getSelectionModel().isEmpty() || dateVidange.isEmpty() || quantity.isEmpty() || killometrage.isEmpty() || prixUnit.isEmpty() || prixTTC.isEmpty() || prixHT.isEmpty()) {
            if (matriculeVal.getSelectionModel().isEmpty()) {
                matriculeVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(matriculeVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le matricule est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                matriculeVal.setStyle("-fx-border-color: white;");
            }
            if (typeHuileVal.getSelectionModel().isEmpty()) {
                typeHuileVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(typeHuileVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le type de huile est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                typeHuileVal.setStyle("-fx-border-color: white;");
            }
            if (dateVidange.isEmpty()) {
                dateVidangeVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(dateVidangeVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("La date de vidange est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                dateVidangeVal.setStyle("-fx-border-color: white;");
            }
            if (quantity.isEmpty()) {
                quantityVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(quantityVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("La quantiteé de huile est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                quantityVal.setStyle("-fx-border-color: white;");
            }

            if (killometrage.isEmpty()) {
                killometrageVid.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(killometrageVid).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le killometrage est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                killometrageVid.setStyle("-fx-border-color: white;");
            }
            if (prixUnit.isEmpty()) {
                prixUnitVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(prixUnitVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le prix unitaire est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                prixUnitVal.setStyle("-fx-border-color: white;");
            }

            if (prixHT.isEmpty()) {
                prixUTVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(prixUTVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le prix HT est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                prixUTVal.setStyle("-fx-border-color: white;");
            }

            if (prixTTC.isEmpty()) {
                prixTTCVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(prixTTCVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le prix TTC est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                prixTTCVal.setStyle("-fx-border-color: white;");
            }

            System.out.println("test if");
        } else {
            vidange = new Vidange();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = dateVidangeVal.getValue();
            vidange.editVidange(vidangeId, matricule, formatter.format(date), killometrage, nextVidange, typeHuile, quantity,Double.parseDouble(prixUnit), Double.parseDouble(prixHT), Double.parseDouble(prixTTC));

            Tools.alert(stackPane, anchorPane, "La vidange a été modifié avec succès!");

        }
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
        fillMatriculsCombobox();
        fillTypeHuileCombobox();
        onlyDigits(prixUnitVal);
        onlyDigits(prixTTCVal);
        onlyDigits(prixUTVal);
        onlyDigits(quantityVal);
        onlyDigits(killometrageVid);
        onlyDigits(nextVidangeVal);
        calculePrix(quantityVal);
        calculePrix(prixUnitVal);
        killometrageVid.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!(killometrageVid.getText().isEmpty()))
            {
                nextVidangeVal.setText(String.valueOf((Integer.parseInt(killometrageVid.getText()) + 10000)));
            }
        });
    }
}

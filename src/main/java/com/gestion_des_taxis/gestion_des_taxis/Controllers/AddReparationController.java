package com.gestion_des_taxis.gestion_des_taxis.Controllers;

import com.gestion_des_taxis.gestion_des_taxis.Models.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;

public class AddReparationController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label pageTitle;

    @FXML
    private TextField voitureVal;

    @FXML
    private ComboBox<String> matriculeVal;

    @FXML
    private TextField designationVal;

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
    private MFXDatePicker dateReparationVal;

    @FXML
    private TextField quantityVal;

    @FXML
    private TextField prixUTVal;

    private String query = null;

    private Connection con = null;

    private PreparedStatement PreStatment = null;

    private ResultSet resultSet = null;
    private Reparation reparation = null;

    public static int reparationId;

    ObservableList<String> matriculesList = FXCollections.observableArrayList();

    @FXML
    void ajouter(MouseEvent event) {
        con = DataBaseConnection.GetConnection();
        String matricule = String.valueOf(matriculeVal.getSelectionModel().getSelectedItem());
        String dateReparation = dateReparationVal.getText();
        String quantity = quantityVal.getText();
        String designation = designationVal.getText();
        String prixUnit = prixUnitVal.getText();
        String prixHT = prixUTVal.getText();
        String prixTTC = prixTTCVal.getText();
        if (matriculeVal.getSelectionModel().isEmpty() || dateReparation.isEmpty() || quantity.isEmpty() || designation.isEmpty() || prixUnit.isEmpty() || prixTTC.isEmpty() || prixHT.isEmpty()) {
            if (matriculeVal.getSelectionModel().isEmpty()) {
                matriculeVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(matriculeVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le matricule est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                matriculeVal.setStyle("-fx-border-color: white;");
            }
            if (dateReparation.isEmpty()) {
                dateReparationVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(dateReparationVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("La date de réparation est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                dateReparationVal.setStyle("-fx-border-color: white;");
            }
            if (quantity.isEmpty()) {
                quantityVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(quantityVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("La quantiteé est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                quantityVal.setStyle("-fx-border-color: white;");
            }

            if (designation.isEmpty()) {
                designationVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(designationVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("La designation est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                designationVal.setStyle("-fx-border-color: white;");
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
            reparation = new Reparation();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = dateReparationVal.getValue();
            reparation.addReparation(matricule, formatter.format(date), designation, Integer.parseInt(quantity), Double.parseDouble(prixUnit), Double.parseDouble(prixHT), Double.parseDouble(prixTTC));

            Tools.alert(stackPane, anchorPane, "La réparation a été ajouté avec succès!");

            matriculeVal.setValue(null);
            designationVal.setText(null);
            quantityVal.setText("0");
            prixTTCVal.setText("0");
            prixUTVal.setText("0");
            prixUnitVal.setText("0");
            dateReparationVal.setValue(null);
            voitureVal.setText(null);
        }
    }

    @FXML
    void modifier(MouseEvent event) {
        con = DataBaseConnection.GetConnection();
        String matricule = String.valueOf(matriculeVal.getSelectionModel().getSelectedItem());
        String dateReparation = dateReparationVal.getText();
        String quantity = quantityVal.getText();
        String designation = designationVal.getText();
        String prixUnit = prixUnitVal.getText();
        String prixHT = prixUTVal.getText();
        String prixTTC = prixTTCVal.getText();
        if (matriculeVal.getSelectionModel().isEmpty() || dateReparation.isEmpty() || quantity.isEmpty() || designation.isEmpty() || prixUnit.isEmpty() || prixTTC.isEmpty() || prixHT.isEmpty()) {
            if (matriculeVal.getSelectionModel().isEmpty()) {
                matriculeVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(matriculeVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("Le matricule est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                matriculeVal.setStyle("-fx-border-color: white;");
            }
            if (dateReparation.isEmpty()) {
                dateReparationVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(dateReparationVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("La date de réparation est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                dateReparationVal.setStyle("-fx-border-color: white;");
            }
            if (quantity.isEmpty()) {
                quantityVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(quantityVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("La quantiteé est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                quantityVal.setStyle("-fx-border-color: white;");
            }

            if (designation.isEmpty()) {
                designationVal.setStyle("-fx-border-color: red;");
                new animatefx.animation.Shake(designationVal).play();
                errorMessage.setVisible(true);
                errorMessage.setText("La designation est obligatoire!");
                new animatefx.animation.BounceIn(errorMessage).play();
            } else {
                designationVal.setStyle("-fx-border-color: white;");
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
            reparation = new Reparation();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = dateReparationVal.getValue();
            reparation.editReparation(reparationId, matricule, formatter.format(date), designation, Integer.parseInt(quantity), Double.parseDouble(prixUnit), Double.parseDouble(prixHT), Double.parseDouble(prixTTC));

            Tools.alert(stackPane, anchorPane, "La réparation a été modifié avec succès!");

        }
    }

    public void fillFields(int id, String matricule, String dateReparation, String designation, String quantite, String unitPrix, String prixHT, String prixTTC) {
        reparationId = id;
        matriculeVal.setValue(matricule);
        designationVal.setText(designation);
        dateReparationVal.setValue(LocalDate.parse(dateReparation));
        quantityVal.setText(quantite);
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
        Stage stage = (Stage) matriculeVal.getScene().getWindow();
        stage.close();
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setVisible(false);
        fillMatriculsCombobox();
        onlyDigits(prixUnitVal);
        onlyDigits(prixTTCVal);
        onlyDigits(prixUTVal);
        onlyDigits(quantityVal);
        calculePrix(quantityVal);
        calculePrix(prixUnitVal);
    }
}

package com.gestion_des_taxis.gestion_des_taxis.Controllers;

import com.gestion_des_taxis.gestion_des_taxis.Models.DataBaseConnection;
import com.gestion_des_taxis.gestion_des_taxis.Models.Reparation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ShowMoreReparationController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label matriculeLabel;

    @FXML
    private Label voitureLabel;

    @FXML
    private Label dateReparationLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label designationLabel;

    @FXML
    private Label prixUnitLabel;

    @FXML
    private Label prixHTLabel;

    @FXML
    private Label prixTTCLabel;

    private Connection con = null;

    private PreparedStatement PreStatment = null;

    private ResultSet resultSet = null;
    private Reparation reparation = null;

    public static int reparationId;
    @FXML
    void anuller(MouseEvent event) {
        Stage stage = (Stage) matriculeLabel.getScene().getWindow();
        stage.close();
    }
    public void fillFields(int id, String matricule, String dateReparation, String designation, String quantite, String unitPrix, String prixHT, String prixTTC) {
        reparationId = id;
        matriculeLabel.setText(matricule);
        dateReparationLabel.setText(designation);
        dateReparationLabel.setText(dateReparation);
        quantityLabel.setText(quantite);
        prixTTCLabel.setText(prixTTC);
        prixUnitLabel.setText(unitPrix);
        prixHTLabel.setText(prixHT);
        con = DataBaseConnection.GetConnection();
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM `voiture` WHERE immatriculation = '" + matricule + "'");
            rs.next();
            voitureLabel.setText(rs.getString(3) + " " + rs.getString(4));
        } catch (SQLException ex) {
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

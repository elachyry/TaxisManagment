package com.gestion_des_taxis.gestion_des_taxis.Controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationShapeController implements Initializable {

    @FXML
    private MFXButton dashboardBtn;

    @FXML
    private MFXButton voitureBtn;

    @FXML
    private MFXButton clientBts;

    @FXML
    private MFXButton chauffeursBtn;

    @FXML
    private MFXButton reparationBtn;

    @FXML
    private MFXButton adminBtn;

    @FXML
    private MFXButton demandeBtn;

    @FXML
    private MFXButton profileBtn;

    @FXML
    private MFXButton deconnexionBtn;

    @FXML
    private StackPane pageArea;

    @FXML
    void chauffeurs(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/Views/Chauffeur.fxml"));
        pageArea.getChildren().removeAll();
        pageArea.getChildren().setAll(fxml);
        chauffeursBtn.setStyle("-fx-background-color : #FFDE00;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-cursor: hand;\n" +
                "-fx-border-color:  white;\n" +
                "-fx-border-width: 0px 0px 0px 6px;\n" +
                "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.7) , 10,0,0,1 );  ");
        voitureBtn.setStyle(null);
        dashboardBtn.setStyle(null);
        clientBts.setStyle(null);
        reparationBtn.setStyle(null);
        demandeBtn.setStyle(null);
        profileBtn.setStyle(null);
        deconnexionBtn.setStyle(null);
    }

    @FXML
    void client(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/Views/Clients.fxml"));
        pageArea.getChildren().removeAll();
        pageArea.getChildren().setAll(fxml);
        clientBts.setStyle("-fx-background-color : #FFDE00;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-cursor: hand;\n" +
                "-fx-border-color:  white;\n" +
                "-fx-border-width: 0px 0px 0px 6px;\n" +
                "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.7) , 10,0,0,1 );  ");
        voitureBtn.setStyle(null);
        dashboardBtn.setStyle(null);
        chauffeursBtn.setStyle(null);
        reparationBtn.setStyle(null);
        adminBtn.setStyle(null);
        demandeBtn.setStyle(null);
        profileBtn.setStyle(null);
        deconnexionBtn.setStyle(null);
    }

    @FXML
    void dashboard(MouseEvent event) throws IOException {

    }

    @FXML
    void deconnexion(MouseEvent event) {
        Stage stage1 = (Stage) pageArea.getScene().getWindow();
        stage1.close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/LoginPage.fxml"));
        try {
            loader.load();
            Stage stage = new Stage();
            Parent parent = loader.getRoot();
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
        }
    }

    @FXML
    void demande(MouseEvent event) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/Views/demandes.fxml"));
        pageArea.getChildren().removeAll();
        pageArea.getChildren().setAll(fxml);
        demandeBtn.setStyle("-fx-background-color : #FFDE00;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-cursor: hand;\n" +
                "-fx-border-color:  white;\n" +
                "-fx-border-width: 0px 0px 0px 6px;\n" +
                "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.7) , 10,0,0,1 );  ");
        voitureBtn.setStyle(null);
        dashboardBtn.setStyle(null);
        chauffeursBtn.setStyle(null);
        reparationBtn.setStyle(null);
        clientBts.setStyle(null);
        profileBtn.setStyle(null);
        deconnexionBtn.setStyle(null);

    }

    @FXML
    void profile(MouseEvent event) {

    }

    @FXML
    void reparation(MouseEvent event) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/Views/ReparationsVidanges.fxml"));
        pageArea.getChildren().removeAll();
        pageArea.getChildren().setAll(fxml);

        reparationBtn.setStyle("-fx-background-color : #FFDE00;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-cursor: hand;\n" +
                "-fx-border-color:  white;\n" +
                "-fx-border-width: 0px 0px 0px 6px;\n" +
                "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.7) , 10,0,0,1 );  ");
        voitureBtn.setStyle(null);
        dashboardBtn.setStyle(null);
        chauffeursBtn.setStyle(null);
        clientBts.setStyle(null);
        adminBtn.setStyle(null);
        demandeBtn.setStyle(null);
        profileBtn.setStyle(null);
        deconnexionBtn.setStyle(null);
    }

    @FXML
    void admin(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/Views/Admins.fxml"));
        pageArea.getChildren().removeAll();
        pageArea.getChildren().setAll(fxml);

        adminBtn.setStyle("-fx-background-color : #FFDE00;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-cursor: hand;\n" +
                "-fx-border-color:  white;\n" +
                "-fx-border-width: 0px 0px 0px 6px;\n" +
                "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.7) , 10,0,0,1 );  ");
        voitureBtn.setStyle(null);
        dashboardBtn.setStyle(null);
        chauffeursBtn.setStyle(null);
        clientBts.setStyle(null);
        reparationBtn.setStyle(null);
        demandeBtn.setStyle(null);
        profileBtn.setStyle(null);
        deconnexionBtn.setStyle(null);
    }

    @FXML
    void voiture(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/Views/Voitures.fxml"));
        pageArea.getChildren().removeAll();
        pageArea.getChildren().setAll(fxml);
        clientBts.setStyle("-fx-background-color : #FFDE00;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-cursor: hand;\n" +
                "-fx-border-color:  white;\n" +
                "-fx-border-width: 0px 0px 0px 6px;\n" +
                "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.7) , 10,0,0,1 );  ");
        clientBts.setStyle(null);
        dashboardBtn.setStyle(null);
        chauffeursBtn.setStyle(null);
        reparationBtn.setStyle(null);
        demandeBtn.setStyle(null);
        profileBtn.setStyle(null);
        deconnexionBtn.setStyle(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashboardBtn.setStyle("-fx-background-color : #FFDE00;\n" +
                "-fx-text-fill: black;\n" +
                "-fx-cursor: hand;\n" +
                "-fx-border-color:  white;\n" +
                "-fx-border-width: 0px 0px 0px 6px;\n" +
                "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.7) , 10,0,0,1 );  ");
        voitureBtn.setStyle(null);
        clientBts.setStyle(null);
        chauffeursBtn.setStyle(null);
        reparationBtn.setStyle(null);
        adminBtn.setStyle(null);
        demandeBtn.setStyle(null);
        profileBtn.setStyle(null);
        deconnexionBtn.setStyle(null);
    }
}

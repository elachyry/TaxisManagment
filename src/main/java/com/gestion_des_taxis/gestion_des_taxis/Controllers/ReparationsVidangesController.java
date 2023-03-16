package com.gestion_des_taxis.gestion_des_taxis.Controllers;

import com.gestion_des_taxis.gestion_des_taxis.Models.*;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReparationsVidangesController implements Initializable {


    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MFXButton corbeille;

    @FXML
    private MFXButton reparationPrimaryTable;

    @FXML
    private TextField rechercherTxtReparation;

    @FXML
    private TableView<Reparation> reparationsTable;

    @FXML
    private TableColumn<Reparation, String> idColReparation;

    @FXML
    private TableColumn<Reparation, String> matriculeColReparation;

    @FXML
    private TableColumn<Reparation, String> dateReparationCol;

    @FXML
    private TableColumn<Reparation, String> designationCol;

    @FXML
    private TableColumn<Reparation, String> quantiteCol;

    @FXML
    private TableColumn<Reparation, String> prixttcCol;

    @FXML
    private TableColumn<Reparation, String> actionColReparation;


    @FXML
    private MFXButton corbeille1;

    @FXML
    private MFXButton vidangePrimaryTable;

    @FXML
    private TextField rechercherTxtVidange;

    @FXML
    private TableView<Vidange> vidangesTable;

    @FXML
    private TableColumn<Vidange, String> idColVidange;

    @FXML
    private TableColumn<Vidange, String> matriclueColVidange;

    @FXML
    private TableColumn<Vidange, String> dateVidangeCol;

    @FXML
    private TableColumn<Vidange, String> killometrageCol;

    @FXML
    private TableColumn<Vidange, String> quantiteColVidange;

    @FXML
    private TableColumn<Vidange, String> prixttcColVidange;

    @FXML
    private TableColumn<Vidange, String> actionColVidange;

    @FXML
    private JFXDialog dialog;

    @FXML
    private Label dialogLabel;

    @FXML
    private MFXButton yesButton;

    @FXML
    private MFXButton noButton;

    String query = null;
    Connection con = null;
    PreparedStatement PreStatment = null;
    ResultSet resultSet = null;
    Reparation reparation = null;
    Vidange vidange = null;
    public static ObservableList<Reparation> reparationsList = FXCollections.observableArrayList();
    public static ObservableList<Vidange> vidangesList = FXCollections.observableArrayList();


    @FXML
    void ajouterReparation(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/AddReparation.fxml"));
            DialogPane addClientDialog = loader.load();
            AddReparationController arc = loader.getController();
            arc.setModifierBtn(false);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.setTitle("Ajouter une nouveau réparation");
            dialog.setDialogPane(addClientDialog);
            Optional<ButtonType> Result = dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
        refrechReparationTable("NULL");
    }

    @FXML
    void ajouterVidange(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/AddVidange.fxml"));
            DialogPane addClientDialog = loader.load();
            AddVidangeController avc = loader.getController();
            avc.setModifierBtn(false);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.setTitle("Ajouter une nouveau vidange");
            dialog.setDialogPane(addClientDialog);
            Optional<ButtonType> Result = dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
        refrechVidnageTable("NULL");
    }

    @FXML
    void exporterReparation(MouseEvent event) throws IOException {
        reparation = new Reparation();
        reparation.exportReparatins();
        Tools.alert(stackPane, anchorPane, "Les réparations ont été exportés avec succès!");
        Desktop.getDesktop().open(new File("Exported/Réparations.xlsx"));
    }

    @FXML
    void exporterVidange(MouseEvent event) throws IOException {
        vidange = new Vidange();
        vidange.exportVidanges();
        System.out.println("okay1");
        Tools.alert(stackPane, anchorPane, "Les vidanges ont été exportés avec succès!");
        Desktop.getDesktop().open(new File("Exported/Vidanges.xlsx"));
    }

    @FXML
    void modifier(MouseEvent event) {

    }

    @FXML
    void modifierReparation(MouseEvent event) {
        reparation = reparationsTable.getSelectionModel().getSelectedItem();
        if (reparation != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/AddReparation.fxml"));
                DialogPane AddMemberDialog = loader.load();
                AddReparationController arc = loader.getController();
                arc.fillFields(reparation.getId(), reparation.getImmatricule(), String.valueOf(reparation.getDateReparation()), reparation.getDesignation(),
                        String.valueOf(reparation.getQuantite()), String.valueOf(reparation.getUnitPrix()), String.valueOf(reparation.getPrixHT()), String.valueOf(reparation.getPrixTTC()));

                arc.pageTitle("Modifier une Réparation");
                arc.setAjouterBtn(false);
                //  AMC.setSpinner(false);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Modifier Réparation");
                dialog.initStyle(StageStyle.TRANSPARENT);
                dialog.setDialogPane(AddMemberDialog);
                Optional<ButtonType> Result = dialog.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Tools.alert(stackPane, anchorPane, "Aucune ligne sélectionnée!");
        }
        refrechReparationTable("NULL");
    }

    @FXML
    void modifierVidange(MouseEvent event) {
        vidange = vidangesTable.getSelectionModel().getSelectedItem();
        if (vidange != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/AddVidange.fxml"));
                DialogPane AddMemberDialog = loader.load();
                AddVidangeController avd = loader.getController();
                avd.fillFields(vidange.getId(), vidange.getImmatriculation(), String.valueOf(vidange.getDateVidange()), vidange.getKillometrage(),
                        String.valueOf(vidange.getKillometrageNextVidange()),vidange.getTypeHuile(), String.valueOf(vidange.getQuantityHuile()),
                        String.valueOf(vidange.getLitrePrix()), String.valueOf(vidange.getPrixHT()), String.valueOf(vidange.getPrixTTC()));

                avd.pageTitle("Modifier une Vidange");
                avd.setAjouterBtn(false);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Modifier une Vidange");
                dialog.initStyle(StageStyle.TRANSPARENT);
                dialog.setDialogPane(AddMemberDialog);
                Optional<ButtonType> Result = dialog.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Tools.alert(stackPane, anchorPane, "Aucune ligne sélectionnée!");
        }
        refrechVidnageTable("NULL");
    }

    @FXML
    void poubelleReparation(MouseEvent event) {
        reparationPrimaryTable.setVisible(true);
        corbeille.setVisible(false);
        con = DataBaseConnection.GetConnection();
        refrechReparationTable("NOT NULL");
        idColReparation.setCellValueFactory(new PropertyValueFactory<>("id"));
        matriculeColReparation.setCellValueFactory(new PropertyValueFactory<>("immatricule"));
        dateReparationCol.setCellValueFactory(new PropertyValueFactory<>("dateReparation"));
        designationCol.setCellValueFactory(new PropertyValueFactory<>("designation"));
        quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        prixttcCol.setCellValueFactory(new PropertyValueFactory<>("prixTTC"));
        Callback<TableColumn<Reparation, String>, TableCell<Reparation, String>> cellFoctory = (TableColumn<Reparation, String> param) -> {

            final TableCell<Reparation, String> cell = new TableCell<Reparation, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        MFXButton restor = new MFXButton("");
                        MFXButton delete = new MFXButton("");
                        delete.setPrefSize(35, 35);
                        restor.setPrefSize(35, 35);

                        BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("/Images/restore_100px.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(30,30, true, true, true, false));
                        Background background = new Background(backgroundImage);
                        restor.setBackground(background);

                        BackgroundImage backgroundImage2 = new BackgroundImage( new Image( getClass().getResource("/Images/trash_128px.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(30,30, true, true, true, false));
                        Background background2 = new Background(backgroundImage2);
                        delete.setBackground(background2);

                        delete.setStyle("-fx-effect: dropshadow(gaussian, #FFDE00, 7, 0.3, 0.0, 0.0);");
                        delete.setRippleColor(Color.TRANSPARENT);
                        delete.setOnMouseClicked((MouseEvent event) -> {
                            reparation = reparationsTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (reparation != null) {
                                BoxBlur Blur = new BoxBlur(3, 3, 3);
                                anchorPane.setEffect(Blur);
                                yesButton.setStyle("-fx-background-color : #05071F;\n" +
                                        "-fx-text-fill : #e7e5e5;\n" +
                                        "-fx-border-radius: 5;");
                                yesButton.setOnMouseEntered(mouseEvent -> {
                                    yesButton.setStyle("-fx-background-color : #FFDE00;\n" +
                                            "-fx-text-fill: black;\n" +
                                            "-fx-cursor: hand;");
                                });
                                yesButton.setOnMouseExited(mouseEvent -> {
                                    yesButton.setStyle("-fx-background-color : #05071F;\n" +
                                            "-fx-text-fill : #e7e5e5;\n" +
                                            "-fx-border-color:  #FFDE00;\n" +
                                            "-fx-border-radius: 5;");
                                });
                                noButton.setStyle("-fx-background-color : #05071F;\n" +
                                        "-fx-text-fill : #e7e5e5;\n" +
                                        "-fx-border-radius: 5;");
                                noButton.setOnMouseEntered(mouseEvent -> {
                                    noButton.setStyle("-fx-background-color : #FFDE00;\n" +
                                            "-fx-text-fill: black;\n" +
                                            "-fx-cursor: hand;");
                                });
                                noButton.setOnMouseExited(mouseEvent -> {
                                    noButton.setStyle("-fx-background-color : #05071F;\n" +
                                            "-fx-text-fill : #e7e5e5;\n" +
                                            "-fx-border-color:  #FFDE00;\n" +
                                            "-fx-border-radius: 5;");
                                });
                                yesButton.setPrefSize(140, 40);
                                yesButton.setFont(new Font(17));
                                yesButton.setCursor(Cursor.HAND);
                                noButton.setPrefSize(140, 40);
                                noButton.setFont(new Font(17));
                                noButton.setCursor(Cursor.HAND);
                                dialogLabel.setText("Voulez-vous supprimer définitivement cette réparation?");
                                dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
                                dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
                                dialog.setDialogContainer(stackPane);
                                dialog.show();

                                yesButton.setOnAction((Event) -> {
                                    reparation.hardDelete(reparation.getId());
                                    refrechReparationTable("NOT NULL");
                                    anchorPane.setEffect(null);
                                    dialog.close();
                                });
                                dialog.setOnDialogClosed((JFXDialogEvent Event) -> {
                                    anchorPane.setEffect(null);
                                });
                                noButton.setOnAction((Event) -> {
                                    anchorPane.setEffect(null);
                                    dialog.close();
                                });
                            }

                        });

                        restor.setStyle("-fx-effect: dropshadow(gaussian, #FFDE00, 5, 0.3, 0.0, 0.0);");
                        restor.setRippleColor(Color.TRANSPARENT);
                        restor.setOnMouseClicked((MouseEvent event) -> {
                            reparation = reparationsTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (reparation != null) {
                                reparation.restorReparation(reparation.getId());
                                Tools.alert(stackPane, anchorPane, "La réparation a été restauré avec succès!");
                                refrechReparationTable("NOT NULL");
                            }

                        });
                        HBox managebtn = new HBox(restor, delete);
                        managebtn.setStyle("-fx-alignment:center");
                        managebtn.setSpacing(20);
                        setGraphic(managebtn);

                        setText(null);
                    }
                }
            };
            return cell;
        };
        actionColReparation.setCellFactory(cellFoctory);
        reparationsTable.setItems(reparationsList);
    }

    @FXML
    void poubelleVidange(MouseEvent event) {
        vidangePrimaryTable.setVisible(true);
        corbeille1.setVisible(false);
        con = DataBaseConnection.GetConnection();
        refrechVidnageTable("NOT NULL");
        idColVidange.setCellValueFactory(new PropertyValueFactory<>("id"));
        matriclueColVidange.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
        dateVidangeCol.setCellValueFactory(new PropertyValueFactory<>("dateVidange"));
        killometrageCol.setCellValueFactory(new PropertyValueFactory<>("killometrage"));
        quantiteColVidange.setCellValueFactory(new PropertyValueFactory<>("quantityHuile"));
        prixttcColVidange.setCellValueFactory(new PropertyValueFactory<>("prixTTC"));
        Callback<TableColumn<Vidange, String>, TableCell<Vidange, String>> cellFoctory = (TableColumn<Vidange, String> param) -> {

            final TableCell<Vidange, String> cell = new TableCell<Vidange, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        MFXButton restor = new MFXButton("");
                        MFXButton delete = new MFXButton("");
                        delete.setPrefSize(35, 35);
                        restor.setPrefSize(35, 35);

                        BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("/Images/restore_100px.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(30,30, true, true, true, false));
                        Background background = new Background(backgroundImage);
                        restor.setBackground(background);

                        BackgroundImage backgroundImage2 = new BackgroundImage( new Image( getClass().getResource("/Images/trash_128px.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(30,30, true, true, true, false));
                        Background background2 = new Background(backgroundImage2);
                        delete.setBackground(background2);

                        delete.setStyle("-fx-effect: dropshadow(gaussian, #FFDE00, 7, 0.3, 0.0, 0.0);");
                        delete.setRippleColor(Color.TRANSPARENT);
                        delete.setOnMouseClicked((MouseEvent event) -> {
                            vidange = vidangesTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (vidange != null) {
                                BoxBlur Blur = new BoxBlur(3, 3, 3);
                                anchorPane.setEffect(Blur);
                                yesButton.setStyle("-fx-background-color : #05071F;\n" +
                                        "-fx-text-fill : #e7e5e5;\n" +
                                        "-fx-border-radius: 5;");
                                yesButton.setOnMouseEntered(mouseEvent -> {
                                    yesButton.setStyle("-fx-background-color : #FFDE00;\n" +
                                            "-fx-text-fill: black;\n" +
                                            "-fx-cursor: hand;");
                                });
                                yesButton.setOnMouseExited(mouseEvent -> {
                                    yesButton.setStyle("-fx-background-color : #05071F;\n" +
                                            "-fx-text-fill : #e7e5e5;\n" +
                                            "-fx-border-color:  #FFDE00;\n" +
                                            "-fx-border-radius: 5;");
                                });
                                noButton.setStyle("-fx-background-color : #05071F;\n" +
                                        "-fx-text-fill : #e7e5e5;\n" +
                                        "-fx-border-radius: 5;");
                                noButton.setOnMouseEntered(mouseEvent -> {
                                    noButton.setStyle("-fx-background-color : #FFDE00;\n" +
                                            "-fx-text-fill: black;\n" +
                                            "-fx-cursor: hand;");
                                });
                                noButton.setOnMouseExited(mouseEvent -> {
                                    noButton.setStyle("-fx-background-color : #05071F;\n" +
                                            "-fx-text-fill : #e7e5e5;\n" +
                                            "-fx-border-color:  #FFDE00;\n" +
                                            "-fx-border-radius: 5;");
                                });
                                yesButton.setPrefSize(140, 40);
                                yesButton.setFont(new Font(17));
                                yesButton.setCursor(Cursor.HAND);
                                noButton.setPrefSize(140, 40);
                                noButton.setFont(new Font(17));
                                noButton.setCursor(Cursor.HAND);
                                dialogLabel.setText("Voulez-vous supprimer définitivement cette vidange?");
                                dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
                                dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
                                dialog.setDialogContainer(stackPane);
                                dialog.show();

                                yesButton.setOnAction((Event) -> {
                                    vidange.hardDelete(vidange.getId());
                                    refrechVidnageTable("NOT NULL");
                                    anchorPane.setEffect(null);
                                    dialog.close();
                                });
                                dialog.setOnDialogClosed((JFXDialogEvent Event) -> {
                                    anchorPane.setEffect(null);
                                });
                                noButton.setOnAction((Event) -> {
                                    anchorPane.setEffect(null);
                                    dialog.close();
                                });
                            }

                        });

                        restor.setStyle("-fx-effect: dropshadow(gaussian, #FFDE00, 5, 0.3, 0.0, 0.0);");
                        restor.setRippleColor(Color.TRANSPARENT);
                        restor.setOnMouseClicked((MouseEvent event) -> {
                            vidange =vidangesTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (vidange != null) {
                                vidange.restorVidange(vidange.getId());
                                Tools.alert(stackPane, anchorPane, "La vidange a été restauré avec succès!");
                                refrechVidnageTable("NOT NULL");
                            }

                        });
                        HBox managebtn = new HBox(restor, delete);
                        managebtn.setStyle("-fx-alignment:center");
                        managebtn.setSpacing(20);
                        setGraphic(managebtn);

                        setText(null);
                    }
                }
            };
            return cell;
        };
        actionColVidange.setCellFactory(cellFoctory);
        vidangesTable.setItems(vidangesList);
    }

    @FXML
    void reparationPrimaryTable(MouseEvent event) {
        reparationPrimaryTable.setVisible(false);
        corbeille.setVisible(true);
        fillReparationTable();
    }

    @FXML
    void vidangePrimaryTable(MouseEvent event) {
        vidangePrimaryTable.setVisible(false);
        corbeille1.setVisible(true);
        fillVidangeTable();
    }

    public void fillReparationTable() {
        con = DataBaseConnection.GetConnection();
        refrechReparationTable("NULL");
        idColReparation.setCellValueFactory(new PropertyValueFactory<>("id"));
        matriculeColReparation.setCellValueFactory(new PropertyValueFactory<>("immatricule"));
        dateReparationCol.setCellValueFactory(new PropertyValueFactory<>("dateReparation"));
        designationCol.setCellValueFactory(new PropertyValueFactory<>("designation"));
        quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        prixttcCol.setCellValueFactory(new PropertyValueFactory<>("prixTTC"));
        Callback<TableColumn<Reparation, String>, TableCell<Reparation, String>> cellFoctory = (TableColumn<Reparation, String> param) -> {

            final TableCell<Reparation, String> cell = new TableCell<Reparation, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        MFXButton delete = new MFXButton("");
                        MFXButton showMore = new MFXButton("");

                        delete.setPrefSize(35, 35);
                        showMore.setPrefSize(35, 35);

                        BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("/Images/eye.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(30,30, true, true, true, false));
                        Background background = new Background(backgroundImage);
                        showMore.setBackground(background);

                        BackgroundImage backgroundImage2 = new BackgroundImage( new Image( getClass().getResource("/Images/trash_128px.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(30,30, true, true, true, false));
                        Background background2 = new Background(backgroundImage2);
                        delete.setBackground(background2);
                        delete.setStyle("-fx-effect: dropshadow(gaussian, #FFDE00, 8, 0.4, 0.0, 0.0);");
                        delete.setRippleColor(Color.TRANSPARENT);
                        delete.setOnMouseClicked((MouseEvent event) -> {
                            reparation = reparationsTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (reparation != null) {
                                BoxBlur Blur = new BoxBlur(3, 3, 3);
                                anchorPane.setEffect(Blur);
                                yesButton.setStyle("-fx-background-color : #05071F;\n" +
                                        "-fx-text-fill : #e7e5e5;\n" +
                                        "-fx-border-radius: 5;");
                                yesButton.setOnMouseEntered(mouseEvent -> {
                                    yesButton.setStyle("-fx-background-color : #FFDE00;\n" +
                                            "-fx-text-fill: black;\n" +
                                            "-fx-cursor: hand;");
                                });
                                yesButton.setOnMouseExited(mouseEvent -> {
                                    yesButton.setStyle("-fx-background-color : #05071F;\n" +
                                            "-fx-text-fill : #e7e5e5;\n" +
                                            "-fx-border-color:  #FFDE00;\n" +
                                            "-fx-border-radius: 5;");
                                });
                                noButton.setStyle("-fx-background-color : #05071F;\n" +
                                        "-fx-text-fill : #e7e5e5;\n" +
                                        "-fx-border-radius: 5;");
                                noButton.setOnMouseEntered(mouseEvent -> {
                                    noButton.setStyle("-fx-background-color : #FFDE00;\n" +
                                            "-fx-text-fill: black;\n" +
                                            "-fx-cursor: hand;");
                                });
                                noButton.setOnMouseExited(mouseEvent -> {
                                    noButton.setStyle("-fx-background-color : #05071F;\n" +
                                            "-fx-text-fill : #e7e5e5;\n" +
                                            "-fx-border-color:  #FFDE00;\n" +
                                            "-fx-border-radius: 5;");
                                });
                                yesButton.setPrefSize(140, 40);
                                yesButton.setFont(new javafx.scene.text.Font(17));
                                yesButton.setCursor(javafx.scene.Cursor.HAND);
                                noButton.setPrefSize(140, 40);
                                noButton.setFont(new Font(17));
                                noButton.setCursor(Cursor.HAND);
                                dialogLabel.setText("Voulez-vous déplacer cette réparation vers la corbeille?");
                                dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
                                dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
                                dialog.setDialogContainer(stackPane);
                                dialog.show();

                                yesButton.setOnAction((Event) -> {
                                    reparation.softDelete(reparation.getId());
                                    refrechReparationTable("NULL");
                                    anchorPane.setEffect(null);
                                    dialog.close();
                                });
                                dialog.setOnDialogClosed((JFXDialogEvent Event) -> {
                                    anchorPane.setEffect(null);
                                });
                                noButton.setOnAction((Event) -> {
                                    anchorPane.setEffect(null);
                                    dialog.close();
                                });
                            }

                        });

                        showMore.setStyle("-fx-effect: dropshadow(gaussian, #FFDE00, 8, 0.4, 0.0, 0.0);");
                        showMore.setRippleColor(Color.TRANSPARENT);
                        showMore.setOnMouseClicked((MouseEvent event) -> {
                            reparation = reparationsTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (reparation != null) {
                                reparationShowMore(reparation.getId());
                            }

                        });
                        HBox managebtn = new HBox(showMore, delete);
                        managebtn.setStyle("-fx-alignment:center");
                        managebtn.setSpacing(20);

                        setGraphic(managebtn);

                        setText(null);
                    }
                }
            };
            return cell;
        };
        actionColReparation.setCellFactory(cellFoctory);
        reparationsTable.setItems(reparationsList);
    }

    public void refrechReparationTable(String type) {
        try {
            reparationsList.clear();
            query = "SELECT * FROM `reparation` WHERE soft_delete IS " + type;
            PreStatment = con.prepareStatement(query);
            resultSet = PreStatment.executeQuery();
            while (resultSet.next()) {
                //System.out.println(resultSet.getString(2));
                reparationsList.add(new Reparation(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getDouble(6),
                        resultSet.getDouble(7),
                        resultSet.getDouble(8)
                ));
                reparationsTable.setItems(reparationsList);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void reparationShowMore(int id) {
        if (reparation != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/ShowMoreReparation.fxml"));
                DialogPane AddMemberDialog = loader.load();
                ShowMoreReparationController smrc = loader.getController();
                smrc.fillFields(reparation.getId(), reparation.getImmatricule(), String.valueOf(reparation.getDateReparation()), reparation.getDesignation(),
                        String.valueOf(reparation.getQuantite()), String.valueOf(reparation.getUnitPrix()), String.valueOf(reparation.getPrixHT()), String.valueOf(reparation.getPrixTTC()));

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Réparation  " + reparation.getImmatricule());
                dialog.initStyle(StageStyle.TRANSPARENT);
                dialog.setDialogPane(AddMemberDialog);
                Optional<ButtonType> Result = dialog.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void fillVidangeTable() {
        con = DataBaseConnection.GetConnection();
        refrechVidnageTable("NULL");
        idColVidange.setCellValueFactory(new PropertyValueFactory<>("id"));
        matriclueColVidange.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
        dateVidangeCol.setCellValueFactory(new PropertyValueFactory<>("dateVidange"));
        killometrageCol.setCellValueFactory(new PropertyValueFactory<>("killometrage"));
        quantiteColVidange.setCellValueFactory(new PropertyValueFactory<>("quantityHuile"));
        prixttcColVidange.setCellValueFactory(new PropertyValueFactory<>("prixTTC"));
        Callback<TableColumn<Vidange, String>, TableCell<Vidange, String>> cellFoctory = (TableColumn<Vidange, String> param) -> {

            final TableCell<Vidange, String> cell = new TableCell<Vidange, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        MFXButton delete = new MFXButton("");
                        MFXButton showMore = new MFXButton("");

                        delete.setPrefSize(35, 35);
                        showMore.setPrefSize(35, 35);

                        BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("/Images/eye.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(30,30, true, true, true, false));
                        Background background = new Background(backgroundImage);
                        showMore.setBackground(background);
                        showMore.setStyle("-fx-effect: dropshadow(gaussian, #FFDE00, 8, 0.4, 0.0, 0.0);");
                        showMore.setRippleColor(Color.TRANSPARENT);
                        showMore.setOnMouseClicked((MouseEvent event) -> {
                            vidange = vidangesTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (vidange != null) {
                                vidangeShowMore(vidange.getId());
                            }

                        });

                        BackgroundImage backgroundImage2 = new BackgroundImage( new Image( getClass().getResource("/Images/trash_128px.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(30,30, true, true, true, false));
                        Background background2 = new Background(backgroundImage2);
                        delete.setBackground(background2);
                        delete.setStyle("-fx-effect: dropshadow(gaussian, #FFDE00, 8, 0.4, 0.0, 0.0);");
                        delete.setRippleColor(Color.TRANSPARENT);
                        delete.setOnMouseClicked((MouseEvent event) -> {
                            vidange = vidangesTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (vidange != null) {
                                BoxBlur Blur = new BoxBlur(3, 3, 3);
                                anchorPane.setEffect(Blur);
                                yesButton.setStyle("-fx-background-color : #05071F;\n" +
                                        "-fx-text-fill : #e7e5e5;\n" +
                                        "-fx-border-radius: 5;");
                                yesButton.setOnMouseEntered(mouseEvent -> {
                                    yesButton.setStyle("-fx-background-color : #FFDE00;\n" +
                                            "-fx-text-fill: black;\n" +
                                            "-fx-cursor: hand;");
                                });
                                yesButton.setOnMouseExited(mouseEvent -> {
                                    yesButton.setStyle("-fx-background-color : #05071F;\n" +
                                            "-fx-text-fill : #e7e5e5;\n" +
                                            "-fx-border-color:  #FFDE00;\n" +
                                            "-fx-border-radius: 5;");
                                });
                                noButton.setStyle("-fx-background-color : #05071F;\n" +
                                        "-fx-text-fill : #e7e5e5;\n" +
                                        "-fx-border-radius: 5;");
                                noButton.setOnMouseEntered(mouseEvent -> {
                                    noButton.setStyle("-fx-background-color : #FFDE00;\n" +
                                            "-fx-text-fill: black;\n" +
                                            "-fx-cursor: hand;");
                                });
                                noButton.setOnMouseExited(mouseEvent -> {
                                    noButton.setStyle("-fx-background-color : #05071F;\n" +
                                            "-fx-text-fill : #e7e5e5;\n" +
                                            "-fx-border-color:  #FFDE00;\n" +
                                            "-fx-border-radius: 5;");
                                });
                                yesButton.setPrefSize(140, 40);
                                yesButton.setFont(new javafx.scene.text.Font(17));
                                yesButton.setCursor(javafx.scene.Cursor.HAND);
                                noButton.setPrefSize(140, 40);
                                noButton.setFont(new Font(17));
                                noButton.setCursor(Cursor.HAND);
                                dialogLabel.setText("Voulez-vous déplacer cette vidange vers la corbeille?");
                                dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
                                dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
                                dialog.setDialogContainer(stackPane);
                                dialog.show();

                                yesButton.setOnAction((Event) -> {
                                    vidange.softDelete(vidange.getId());
                                    refrechVidnageTable("NULL");
                                    anchorPane.setEffect(null);
                                    dialog.close();
                                });
                                dialog.setOnDialogClosed((JFXDialogEvent Event) -> {
                                    anchorPane.setEffect(null);
                                });
                                noButton.setOnAction((Event) -> {
                                    anchorPane.setEffect(null);
                                    dialog.close();
                                });
                            }

                        });
                        HBox managebtn = new HBox(showMore, delete);
                        managebtn.setStyle("-fx-alignment:center");
                        managebtn.setSpacing(20);

                        setGraphic(managebtn);

                        setText(null);
                    }
                }
            };
            return cell;
        };
        actionColVidange.setCellFactory(cellFoctory);
        vidangesTable.setItems(vidangesList);
    }

    public void refrechVidnageTable(String type) {
        try {
            vidangesList.clear();
            query = "SELECT * FROM `vidange` WHERE soft_delete IS " + type;
            PreStatment = con.prepareStatement(query);
            resultSet = PreStatment.executeQuery();
            while (resultSet.next()) {
                //System.out.println(resultSet.getString(2));
                vidangesList.add(new Vidange(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getDouble(8),
                        resultSet.getDouble(9),
                        resultSet.getDouble(10)
                ));
                vidangesTable.setItems(vidangesList);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void vidangeShowMore(int id) {
        if (vidange != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/ShowMoreVidange.fxml"));
                DialogPane AddMemberDialog = loader.load();
                ShowMoreVidangeController smvr = loader.getController();
                smvr.fillFields(vidange.getId(), vidange.getImmatriculation(), String.valueOf(vidange.getDateVidange()), vidange.getKillometrage(),
                        String.valueOf(vidange.getKillometrageNextVidange()),vidange.getTypeHuile(), String.valueOf(vidange.getQuantityHuile()),
                        String.valueOf(vidange.getLitrePrix()), String.valueOf(vidange.getPrixHT()), String.valueOf(vidange.getPrixTTC()));

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Vidange  " + vidange.getImmatriculation());
                dialog.initStyle(StageStyle.TRANSPARENT);
                dialog.setDialogPane(AddMemberDialog);
                Optional<ButtonType> Result = dialog.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reparationPrimaryTable.setVisible(false);
        corbeille.setVisible(true);
        vidangePrimaryTable.setVisible(false);
        corbeille1.setVisible(true);
        fillReparationTable();
        fillVidangeTable();
        reparation = new Reparation();
        vidange = new Vidange();
        reparation.SearchReparation(rechercherTxtReparation, reparationsList, reparationsTable);
        vidange.SearchVidange(rechercherTxtVidange, vidangesList, vidangesTable);
    }
}

package com.gestion_des_taxis.gestion_des_taxis.Controllers;

import com.gestion_des_taxis.gestion_des_taxis.Models.Client;
import com.gestion_des_taxis.gestion_des_taxis.Models.DataBaseConnection;
import com.gestion_des_taxis.gestion_des_taxis.Models.Tools;
import com.gestion_des_taxis.gestion_des_taxis.Models.Voiture;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class VoituresController implements Initializable {

    @FXML
    private TableColumn<Voiture, String> actionCol;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MFXButton corbeille;

    @FXML
    private JFXDialog dialog;

    @FXML
    private Label dialogLabel;

    @FXML
    private TableColumn<Voiture, String> etatCol;

    @FXML
    private TableColumn<Voiture, String> iconCol;

    @FXML
    private TableColumn<Voiture, String> idCol;

    @FXML
    private TableColumn<Voiture, String> immaCol;

    @FXML
    private TableColumn<Voiture, String> marqueCol;

    @FXML
    private TableColumn<Voiture, String> modelCol;

    @FXML
    private MFXButton noButton;

    @FXML
    private MFXButton primaryTable;

    @FXML
    private TextField rechercherTxt;

    @FXML
    private StackPane stackPane;

    @FXML
    private TableView<Voiture> voitureTable;

    @FXML
    private MFXButton yesButton;

    String Query = null;
    Connection Con = null;
    PreparedStatement PreStatment = null;
    ResultSet resultSet = null;
    Voiture voiture = null;
    public static ObservableList<Voiture> voituresList = FXCollections.observableArrayList();

    @FXML
    void ajouter(MouseEvent event) {
        try {
            //BoxBlur Blur = new BoxBlur(3, 3, 3);
            //anchorPane.setEffect(Blur);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/AddVoiture.fxml"));
            DialogPane addVoitureDialog = loader.load();
            AddVoitureController acc = loader.getController();
            acc.setModifierBtn(false);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.setTitle("Ajouter une nouvelle voiture");
            dialog.setDialogPane(addVoitureDialog);
            Optional<ButtonType> Result = dialog.showAndWait();

        } catch (IOException e) {
        }
        refrechTable("null");

    }

    @FXML
    void exporter(MouseEvent event) throws IOException{
        voiture = new Voiture();
        voiture.exportVoitures();
        System.out.println("okay1");
        Tools.alert(stackPane, anchorPane, "Les voitures sont été exportés avec succès!");
        Desktop.getDesktop().open(new File("Exported/Voitures.xlsx"));

    }

    @FXML
    void modifier(MouseEvent event) {
        voiture = voitureTable.getSelectionModel().getSelectedItem();
        if (voiture != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/AddVoiture.fxml"));
                DialogPane AddMemberDialog = loader.load();
                AddVoitureController acc = loader.getController();
                acc.fillFields(voiture.getId(), voiture.getImmatriculation(), voiture.getMarque(), voiture.getModel(),voiture.getCarburant(),
                        voiture.getEtat());

                acc.pageTitle("Modifier Voiture");
                acc.setAjouterBtn(false);
                //  AMC.setSpinner(false);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Modifier Voiture");
                dialog.initStyle(StageStyle.TRANSPARENT);
                dialog.setDialogPane(AddMemberDialog);
                Optional<ButtonType> Result = dialog.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Tools.alert(stackPane, anchorPane, "Aucune ligne sélectionnée!");
        }
        refrechTable("NULL");

    }

    @FXML
    void poubelle(MouseEvent event) {

        primaryTable.setVisible(true);
        corbeille.setVisible(false);
        Con = DataBaseConnection.GetConnection();
        refrechTable("NOT NULL");
//        iconCol.setCellValueFactory(new PropertyValueFactory<>("icon"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        marqueCol.setCellValueFactory(new PropertyValueFactory<>("marque"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        immaCol.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
        etatCol.setCellValueFactory(new PropertyValueFactory<>("etat"));
//        actionCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
        Callback<TableColumn<Voiture, String>, TableCell<Voiture, String>> cellFoctory = (TableColumn<Voiture, String> param) -> {

            final TableCell<Voiture, String> cell = new TableCell<Voiture, String>() {
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
                            voiture = voitureTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (voiture != null) {
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
                                dialogLabel.setText("Voulez-vous supprimer définitivement ce client?");
                                dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
                                dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
                                dialog.setDialogContainer(stackPane);
                                dialog.show();

                                yesButton.setOnAction((Event) -> {
                                  //  voiture.hardDeleteVoiture(voiture.getId());
                                    refrechTable("NOT NULL");
                                    anchorPane.setEffect(null);
                                    dialog.close();
//                                    Tools t = new Tools(client.getEmail(), client.getPrenom(), client.getNom(), "", "", "Delete Account");
//                                    t.start();
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
                            voiture = voitureTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (voiture != null) {
                               // voiture.restorVoiture(voiture.getId());
                                Tools.alert(stackPane, anchorPane, "Le client a été restauré avec succès!");
                                refrechTable("NOT NULL");
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
        actionCol.setCellFactory(cellFoctory);
        voitureTable.setItems(voituresList);

    }
    public void softDelete(int id)
    {
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
        dialogLabel.setText("Voulez-vous déplacer cette voiture vers la corbeille?");
        dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
        dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
        dialog.setDialogContainer(stackPane);
        dialog.show();

        yesButton.setOnAction((Event) -> {
            voiture.softDelete(voiture.getId());
            refrechTable("NULL");
            anchorPane.setEffect(null);
            dialog.close();
            Tools t = new Tools(voiture.getImmatriculation(), voiture.getMarque(), voiture.getModel(), "", "", "Delete Account");
            t.start();
        });
        dialog.setOnDialogClosed((JFXDialogEvent Event) -> {
            anchorPane.setEffect(null);
        });
        noButton.setOnAction((Event) -> {
            anchorPane.setEffect(null);
            dialog.close();
        });
    }


    @FXML
    /*void resetPassword(MouseEvent event) {

    }*/
    private void refrechTable(String type) {
        try {
            voituresList.clear();
            Query = "SELECT * FROM `Voiture` WHERE soft_delete IS " + type;
            PreStatment = Con.prepareStatement(Query);
            resultSet = PreStatment.executeQuery();
            while (resultSet.next()) {
                //System.out.println(resultSet.getString(2));
                voituresList.add(new Voiture(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getInt(6),
                                resultSet.getInt(7),
                                resultSet.getDate(8),
                                resultSet.getString(9),
                                resultSet.getInt(10),
                                resultSet.getDate(11),
                                resultSet.getInt(12),
                                resultSet.getInt(13),
                                resultSet.getString(14)
                        )
                );
                voitureTable.setItems(voituresList);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /*catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }*/
    }

    public void fillTable() {
        Con = DataBaseConnection.GetConnection();
        refrechTable("NULL");
//        iconCol.setCellValueFactory(new PropertyValueFactory<>("icon"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        marqueCol.setCellValueFactory(new PropertyValueFactory<>("marque"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        immaCol.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
        etatCol.setCellValueFactory(new PropertyValueFactory<>("etat"));
//        actionCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
        Callback<TableColumn<Voiture, String>, TableCell<Voiture, String>> cellFoctory = (TableColumn<Voiture, String> param) -> {

            final TableCell<Voiture, String> cell = new TableCell<Voiture, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        MFXButton delete = new MFXButton("");

                        delete.setPrefSize(35, 35);

                        BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("/Images/trash_128px.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(30,30, true, true, true, false));
                        Background background = new Background(backgroundImage);
                        delete.setBackground(background);
                        delete.setStyle("-fx-effect: dropshadow(gaussian, #FFDE00, 8, 0.4, 0.0, 0.0);");
                        delete.setRippleColor(Color.TRANSPARENT);
                        delete.setOnMouseClicked((MouseEvent event) -> {
                            voiture = voitureTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (voiture != null) {
                                softDelete(voiture.getId());
                            }

                        });
                        HBox managebtn = new HBox(delete);
                        managebtn.setStyle("-fx-alignment:center");

                        setGraphic(managebtn);

                        setText(null);
                    }
                }
            };
            return cell;
        };
        actionCol.setCellFactory(cellFoctory);
        voitureTable.setItems(voituresList);
    }

    @FXML
    void primaryTable(MouseEvent event) {
        primaryTable.setVisible(false);
        corbeille.setVisible(true);
        fillTable();
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        primaryTable.setVisible(false);
        corbeille.setVisible(true);
        fillTable();
        voiture = new Voiture();
        //voiture.searchVoiture(rechercherTxt, voituresList, voitureTable);

    }
}

package com.gestion_des_taxis.gestion_des_taxis.Controllers;

import com.gestion_des_taxis.gestion_des_taxis.Models.Chauffeur;
import com.gestion_des_taxis.gestion_des_taxis.Models.DataBaseConnection;
import com.gestion_des_taxis.gestion_des_taxis.Models.Tools;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
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

public class ChauffeursController implements Initializable {

    @FXML
    private TableColumn<Chauffeur, String> actionCol;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MFXButton yesButton;

    @FXML
    private MFXButton noButton;

    @FXML
    private TableView<Chauffeur> chauffeursTable;

    @FXML
    private MFXButton corbeille;

    @FXML
    private JFXDialog dialog;

    @FXML
    private Label dialogLabel;

    @FXML
    private TableColumn<?, ?> emailCol;

    @FXML
    private TableColumn<?, ?> iconCol;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nomCol;

    @FXML
    private TableColumn<?, ?> prenomCol;

    @FXML
    private MFXButton primaryTable;

    @FXML
    private TextField rechercherTxt;

    @FXML
    private StackPane stackPane;

    @FXML
    private TableColumn<?, ?> teleCol;
    String Query = null;
    Connection Con = null;
    PreparedStatement PreStatment = null;
    ResultSet resultSet = null;
    Chauffeur chauffeur = null;
    public static ObservableList<Chauffeur> chauffeursList = FXCollections.observableArrayList();



    public void ajouter(MouseEvent mouseEvent) {
       try {
            FXMLLoader loader = new FXMLLoader();
            AddChauffeurController.isEdit = false;
            loader.setLocation(getClass().getResource("/Views/AddChauffeur.fxml"));
            DialogPane addChauffeurDialog = loader.load();
            AddChauffeurController addChauffeurController = loader.getController();
            addChauffeurController.setModifierBtn(false);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.setTitle("Ajouter un nouveau Chauffeur");
            dialog.setDialogPane(addChauffeurDialog);
            Optional<ButtonType> Result = dialog.showAndWait();


        } catch (IOException e) {
        }

        refrechTable("NULL");
    }

    public void modifier(MouseEvent mouseEvent) {
        chauffeur =  chauffeursTable.getSelectionModel().getSelectedItem();
        if (chauffeur != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/Addchauffeur.fxml"));
                DialogPane AddMemberDialog = loader.load();
                AddChauffeurController acc = loader.getController();
                acc.fillFields(chauffeur.getId(),chauffeur.getCNE(),chauffeur.getNumPermis(), chauffeur.getNom(), chauffeur.getPrenom(), chauffeur.getTelephone(),
                        chauffeur.getEmail(), chauffeur.getAdresse());

                acc.pageTitle("Modifier Chauffeur");
                acc.setAjouterBtn(false);
                //  AMC.setSpinner(false);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Modifier Chauffeur");
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

    public void resetPassword(MouseEvent event) {
            chauffeur = chauffeursTable.getSelectionModel().getSelectedItem();
        if (chauffeur != null) {
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
            dialogLabel.setText("Voulez-vous réinitialiser le mot de passe pour cet chauffeur?");
            dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
            dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
            dialog.setDialogContainer(stackPane);
            dialog.show();

            yesButton.setOnAction((Event) -> {
                chauffeur.resetpassword(chauffeur.getId());
                refrechTable("NULL");
                anchorPane.setEffect(null);
//                dialog.close();
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
                dialogLabel.setText("Voulez-vous envoyer un e-mail au chauffeur?");
                dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
                dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
                dialog.setDialogContainer(stackPane);
                dialog.show();

                yesButton.setOnAction((Event2) -> {
                    chauffeur.resetpassword(chauffeur.getId());
                    anchorPane.setEffect(null);
                    dialog.close();

                    Tools t = new Tools(chauffeur.getEmail(), chauffeur.getPrenom(), chauffeur.getNom(), "", "", "Update Account");
                    t.start();
                    Tools.alert(stackPane , anchorPane,"le mot de passe a été réinitialisé avec succès");
                });
                dialog.setOnDialogClosed((JFXDialogEvent Event2) -> {
                    anchorPane.setEffect(null);
                });
                noButton.setOnAction((Event2) -> {
                    anchorPane.setEffect(null);
                    dialog.close();
                    Tools.alert(stackPane , anchorPane,"le mot de passe a été réinitialisé avec succès");
                });
            });
            dialog.setOnDialogClosed((JFXDialogEvent Event) -> {
                anchorPane.setEffect(null);
            });
            noButton.setOnAction((Event) -> {
                anchorPane.setEffect(null);
                dialog.close();
            });
        }else {
            Tools.alert(stackPane, anchorPane, "Aucune ligne sélectionnée!");
        }

    }

    public void exporter(MouseEvent mouseEvent) throws IOException {
        chauffeur = new Chauffeur();
        chauffeur.exportChauffeur();
        System.out.println("okay");
        Tools.alert(stackPane, anchorPane, "Les chauffeurs ont été exportés avec succès!");
        Desktop.getDesktop().open(new File("Exported/Chauffeurs.xlsx"));
    }

    public void poubelle(MouseEvent event) {
        primaryTable.setVisible(true);
        corbeille.setVisible(false);
        Con = DataBaseConnection.GetConnection();
        refrechTable("NOT NULL");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        teleCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        Callback<TableColumn<Chauffeur, String>, TableCell<Chauffeur, String>> cellFoctory = (TableColumn<Chauffeur, String> param) -> {

            final TableCell<Chauffeur, String> cell = new TableCell<Chauffeur, String>() {
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
                            chauffeur = chauffeursTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (chauffeur != null) {
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
                                dialogLabel.setText("Voulez-vous supprimer définitivement ce chauffeur?");
                                dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
                                dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
                                dialog.setDialogContainer(stackPane);
                                dialog.show();

                                yesButton.setOnAction((Event) -> {
                                    chauffeur.hardDeletionChauffeur(chauffeur.getId());
                                    refrechTable("NOT NULL");
                                    anchorPane.setEffect(null);
                                    dialog.close();
//
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
                            chauffeur = chauffeursTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (chauffeur != null) {
                                chauffeur.restorChauffeur(chauffeur.getId());
                                Tools.alert(stackPane, anchorPane, "Le chauffeur a été restauré avec succès!");
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
        chauffeursTable.setItems(chauffeursList);
    }


    public void primaryTable(MouseEvent mouseEvent) {
        primaryTable.setVisible(false);
        corbeille.setVisible(true);
        fillTable();
    }

    public void fillTable() {
        Con = DataBaseConnection.GetConnection();
        refrechTable("NULL");
//        iconCol.setCellValueFactory(new PropertyValueFactory<>("icon"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        teleCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
//        actionCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
        Callback<TableColumn<Chauffeur, String>, TableCell<Chauffeur, String>> cellFoctory = (TableColumn<Chauffeur, String> param) -> {

            final TableCell<Chauffeur, String> cell = new TableCell<Chauffeur, String>() {
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
                            chauffeur = chauffeursTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (chauffeur != null) {
                                softDelete(chauffeur.getId());
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
        chauffeursTable.setItems(chauffeursList);

    }

    private void softDelete(int id) {
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
        dialogLabel.setText("Voulez-vous déplacer ce Chauffeur vers la corbeille?");
        dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
        dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
        dialog.setDialogContainer(stackPane);
        dialog.show();

        yesButton.setOnAction((Event) -> {
            chauffeur.softDelete(chauffeur.getId());
            refrechTable("NULL");
            anchorPane.setEffect(null);
            dialog.close();
            Tools t = new Tools(chauffeur.getEmail(), chauffeur.getPrenom(), chauffeur.getNom(), "", "", "Delete Account");
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

    private void refrechTable(String type) {
        try {
            chauffeursList.clear();
            Query = "SELECT * FROM `chauffeur` WHERE soft_delete IS " + type;
            PreStatment = Con.prepareStatement(Query);
            resultSet = PreStatment.executeQuery();
            while (resultSet.next()) {
                //System.out.println(resultSet.getString(2));
                chauffeursList.add(new Chauffeur(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getInt(1),
                        new ImageView()

                ));
                chauffeursTable.setItems(chauffeursList);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        primaryTable.setVisible(false);
        corbeille.setVisible(true);
        fillTable();
        chauffeur = new Chauffeur();
        chauffeur.searchChauffeur(rechercherTxt, chauffeursList, chauffeursTable);
    }
}

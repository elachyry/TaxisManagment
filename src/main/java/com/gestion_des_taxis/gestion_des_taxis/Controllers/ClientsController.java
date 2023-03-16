package com.gestion_des_taxis.gestion_des_taxis.Controllers;


import com.cathive.fonts.fontawesome.FontAwesomeIconView;
import com.gestion_des_taxis.gestion_des_taxis.Models.Client;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {

    @FXML
    private TextField rechercherTxt;

    @FXML
    private MFXButton corbeille;

    @FXML
    private MFXButton primaryTable;

    @FXML
    private TableView<Client> clientsTable;

    @FXML
    private TableColumn<Client, ImageView> iconCol;

    @FXML
    private TableColumn<Client, String> idCol;

    @FXML
    private TableColumn<Client, String> nomCol;

    @FXML
    private TableColumn<Client, String> prenomCol;

    @FXML
    private TableColumn<Client, String> teleCol;

    @FXML
    private TableColumn<Client, String> emailCol;

    @FXML
    private TableColumn<Client, String> actionCol;

    @FXML
    private JFXDialog dialog;

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MFXButton yesButton;

    @FXML
    private MFXButton noButton;

    @FXML
    private Label dialogLabel;

    String query = null;
    Connection con = null;
    PreparedStatement PreStatment = null;
    ResultSet resultSet = null;
    Client client = null;
    public static ObservableList<Client> clientsList = FXCollections.observableArrayList();

    @FXML
    void ajouter(MouseEvent event) {
        try {
            //BoxBlur Blur = new BoxBlur(3, 3, 3);
            //anchorPane.setEffect(Blur);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/AddClient.fxml"));
            DialogPane addClientDialog = loader.load();
            AddClientController acc = loader.getController();
            acc.setModifierBtn(false);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.setTitle("Ajouter un nouveau client");
            dialog.setDialogPane(addClientDialog);
            Optional<ButtonType> Result = dialog.showAndWait();

        } catch (IOException e) {
        }
        refrechTable("NULL");
    }

    @FXML
    void exporter(MouseEvent event) throws IOException {
        client = new Client();
        client.exportClient();
        System.out.println("okay1");
        Tools.alert(stackPane, anchorPane, "Les clients ont été exportés avec succès!");
        Desktop.getDesktop().open(new File("Exported/Clients.xlsx"));
    }

    @FXML
    void modifier(MouseEvent event) {
        client = clientsTable.getSelectionModel().getSelectedItem();
        if (client != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/AddClient.fxml"));
                DialogPane AddMemberDialog = loader.load();
                AddClientController acc = loader.getController();
                acc.fillFields(client.getId(), client.getNom(), client.getPrenom(), client.getTelephone(),
                        client.getEmail(), client.getAdresse());

                acc.pageTitle("Modifier Client");
                acc.setAjouterBtn(false);
                //  AMC.setSpinner(false);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Modifier Client");
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
        con = DataBaseConnection.GetConnection();
        refrechTable("NOT NULL");
//        iconCol.setCellValueFactory(new PropertyValueFactory<>("icon"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        teleCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
//        actionCol.setCellValueFactory(new PropertyValueFactory<>("delete"));
        Callback<TableColumn<Client, String>, TableCell<Client, String>> cellFoctory = (TableColumn<Client, String> param) -> {

            final TableCell<Client, String> cell = new TableCell<Client, String>() {
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
                            client = clientsTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (client != null) {
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
                                    client.hardDeleteClient(client.getId());
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
                            client = clientsTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (client != null) {
                                client.restorClient(client.getId());
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
        clientsTable.setItems(clientsList);
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
        dialogLabel.setText("Voulez-vous déplacer ce client vers la corbeille?");
        dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
        dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
        dialog.setDialogContainer(stackPane);
        dialog.show();

        yesButton.setOnAction((Event) -> {
            client.softDelete(client.getId());
            refrechTable("NULL");
            anchorPane.setEffect(null);
            dialog.close();
            Tools t = new Tools(client.getEmail(), client.getPrenom(), client.getNom(), "", "", "Delete Account");
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
    void resetPassword(MouseEvent event) {
        client = clientsTable.getSelectionModel().getSelectedItem();
        if (client != null) {
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
            dialogLabel.setText("Voulez-vous réinitialiser le mot de passe pour cet client?");
            dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
            dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
            dialog.setDialogContainer(stackPane);
            dialog.show();

            yesButton.setOnAction((Event) -> {
                client.resetpassword(client.getId());
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
                dialogLabel.setText("Voulez-vous envoyer un e-mail au client?");
                dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
                dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
                dialog.setDialogContainer(stackPane);
                dialog.show();

                yesButton.setOnAction((Event2) -> {
                    client.resetpassword(client.getId());
                    anchorPane.setEffect(null);
                    dialog.close();
//                    if (Tools.checkInternetConnection())
//                    {
                        Tools t = new Tools(client.getEmail(), client.getPrenom(), client.getNom(), "", "", "Update Account");
                        t.start();
//                    }else
//                    {
//                        Tools.alert(stackPane , anchorPane,"Vous n'êtes pas connecté! Veuillez vérifier votre connexion internet.");
//                    }
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

    public void fillTable() {
        con = DataBaseConnection.GetConnection();
        refrechTable("NULL");
        iconCol.setCellValueFactory(new PropertyValueFactory<>("icon"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        teleCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        Callback<TableColumn<Client, String>, TableCell<Client, String>> cellFoctory = (TableColumn<Client, String> param) -> {

            final TableCell<Client, String> cell = new TableCell<Client, String>() {
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
                            client = clientsTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (client != null) {
                                softDelete(client.getId());
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
        clientsTable.setItems(clientsList);
    }

    public void refrechTable(String type) {
        try {
            clientsList.clear();
            query = "SELECT * FROM `client` WHERE soft_delete IS " + type;
            PreStatment = con.prepareStatement(query);
            resultSet = PreStatment.executeQuery();
            while (resultSet.next()) {
                //System.out.println(resultSet.getString(2));
                clientsList.add(new Client(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getInt(1),
                        new ImageView()
                ));
                clientsTable.setItems(clientsList);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void primaryTable(MouseEvent event) {
        primaryTable.setVisible(false);
        corbeille.setVisible(true);
        fillTable();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        primaryTable.setVisible(false);
        corbeille.setVisible(true);
        fillTable();
        client = new Client();
        client.searchClient(rechercherTxt, clientsList, clientsTable);

    }
}

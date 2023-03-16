package com.gestion_des_taxis.gestion_des_taxis.Controllers;

import com.gestion_des_taxis.gestion_des_taxis.Models.Admin;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminsController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MFXButton corbeille;

    @FXML
    private MFXButton primaryTable;

    @FXML
    private TextField rechercherTxt;

    @FXML
    private TableView<Admin> adminsTable;

    @FXML
    private TableColumn<Admin, String> cinCol;

    @FXML
    private TableColumn<Admin, String> idCol;

    @FXML
    private TableColumn<Admin, String> nomCol;

    @FXML
    private TableColumn<Admin, String> prenomCol;

    @FXML
    private TableColumn<Admin, String> teleCol;

    @FXML
    private TableColumn<Admin, String> emailCol;

    @FXML
    private TableColumn<Admin, String> actionCol;

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
    Admin admin = null;
    public static ObservableList<Admin> adminsList = FXCollections.observableArrayList();
    @FXML
    void ajouter(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/AddAdmin.fxml"));
            DialogPane addClientDialog = loader.load();
            AddAdminController aac = loader.getController();
            aac.setModifierBtn(false);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.setTitle("Ajouter un nouveau Admin");
            dialog.setDialogPane(addClientDialog);
            Optional<ButtonType> Result = dialog.showAndWait();

        } catch (IOException e) {
        }
        refrechTable("NULL");
    }

    @FXML
    void exporter(MouseEvent event) throws IOException {
        admin = new Admin();
        admin.exportAdmin();
        System.out.println("okay1");
        Tools.alert(stackPane, anchorPane, "Les admins ont été exportés avec succès!");
        Desktop.getDesktop().open(new File("Exported/Admins.xlsx"));
    }

    @FXML
    void modifier(MouseEvent event) {
        admin = adminsTable.getSelectionModel().getSelectedItem();
        if (admin != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/AddAdmin.fxml"));
                DialogPane AddMemberDialog = loader.load();
                AddAdminController aac = loader.getController();
                aac.fillFields(admin.getId(), admin.getCin(), admin.getNom(), admin.getPrenom(), admin.getTelephone(),
                        admin.getEmail(), admin.getAdresse());

                aac.pageTitle("Modifier Admin");
                aac.setAjouterBtn(false);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Modifier Admin");
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
        cinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        teleCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        Callback<TableColumn<Admin, String>, TableCell<Admin, String>> cellFoctory = (TableColumn<Admin, String> param) -> {

            final TableCell<Admin, String> cell = new TableCell<Admin, String>() {
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
                            admin = adminsTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (admin != null) {
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
                                dialogLabel.setText("Voulez-vous supprimer définitivement ce admin?");
                                dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
                                dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
                                dialog.setDialogContainer(stackPane);
                                dialog.show();

                                yesButton.setOnAction((Event) -> {
                                    admin.hardDeleteAdmin(admin.getId());
                                    refrechTable("NOT NULL");
                                    anchorPane.setEffect(null);
                                    dialog.close();
                                    Tools t = new Tools(admin.getEmail(), admin.getPrenom(), admin.getNom(), "", "", "Delete Account");
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

                        });

                        restor.setStyle("-fx-effect: dropshadow(gaussian, #FFDE00, 5, 0.3, 0.0, 0.0);");
                        restor.setRippleColor(Color.TRANSPARENT);
                        restor.setOnMouseClicked((MouseEvent event) -> {
                            admin = adminsTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (admin != null) {
                                admin.restorAdmin(admin.getId());
                                Tools.alert(stackPane, anchorPane, "L'admin a été restauré avec succès!");
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
        adminsTable.setItems(adminsList);
    }


    @FXML
    void primaryTable(MouseEvent event) {
        primaryTable.setVisible(false);
        corbeille.setVisible(true);
        fillTable();
    }

    @FXML
    void resetPassword(MouseEvent event) {
        admin = adminsTable.getSelectionModel().getSelectedItem();
        if (admin != null) {
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
            dialogLabel.setText("Voulez-vous réinitialiser le mot de passe pour cet admin?");
            dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
            dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
            dialog.setDialogContainer(stackPane);
            dialog.show();

            yesButton.setOnAction((Event) -> {
                admin.resetpassword(admin.getId());
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
                dialogLabel.setText("Voulez-vous envoyer un e-mail au admin?");
                dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
                dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
                dialog.setDialogContainer(stackPane);
                dialog.show();

                yesButton.setOnAction((Event2) -> {
                    admin.resetpassword(admin.getId());
                    anchorPane.setEffect(null);
                    dialog.close();
//                    if (Tools.checkInternetConnection())
//                    {
                    Tools t = new Tools(admin.getEmail(), admin.getPrenom(), admin.getNom(), "", "", "Update Account");
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
        dialogLabel.setText("Voulez-vous déplacer ce admin vers la corbeille?");
        dialogLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
        dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
        dialog.setDialogContainer(stackPane);
        dialog.show();

        yesButton.setOnAction((Event) -> {
            admin.softDelete(admin.getId());
            refrechTable("NULL");
            anchorPane.setEffect(null);
            dialog.close();
            Tools t = new Tools(admin.getEmail(), admin.getPrenom(), admin.getNom(), "", "", "Delete Account");
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

    public void fillTable() {
        con = DataBaseConnection.GetConnection();
        refrechTable("NULL");
        cinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        teleCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        Callback<TableColumn<Admin, String>, TableCell<Admin, String>> cellFoctory = (TableColumn<Admin, String> param) -> {

            final TableCell<Admin, String> cell = new TableCell<Admin, String>() {
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
                            admin = adminsTable.getSelectionModel().getSelectedItem();
                            System.out.println("clicked");
                            if (admin != null) {
                                softDelete(admin.getId());
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
        adminsTable.setItems(adminsList);
    }

    public void refrechTable(String type) {
        try {
            adminsList.clear();
            query = "SELECT * FROM `admin` WHERE soft_delete IS " + type;
            PreStatment = con.prepareStatement(query);
            resultSet = PreStatment.executeQuery();
            while (resultSet.next()) {
                //System.out.println(resultSet.getString(2));
                adminsList.add(new Admin(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)

                ));
                adminsTable.setItems(adminsList);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        primaryTable.setVisible(false);
        corbeille.setVisible(true);
        fillTable();
        admin = new Admin();
        admin.searchAdmin(rechercherTxt, adminsList, adminsTable);
    }
}

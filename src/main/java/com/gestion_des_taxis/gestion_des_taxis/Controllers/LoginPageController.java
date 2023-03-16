package com.gestion_des_taxis.gestion_des_taxis.Controllers;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.gestion_des_taxis.gestion_des_taxis.Models.DataBaseConnection.con;

public class LoginPageController implements Initializable {

    @FXML
    private TextField emailVal;

    @FXML
    private PasswordField passwordVal;

    @FXML
    private Label errorMessage;

    @FXML
    void cancel(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void connexion(MouseEvent  event) {
        String emailValue = emailVal.getText();
        String passwordValue = passwordVal.getText();
        if (emailValue.isEmpty()) {
            emailVal.setStyle("-fx-border-color: red; -fx-text-fill: red;");
            new animatefx.animation.Shake(emailVal).play();
        } else {
            emailVal.setStyle("-fx-border-color: #FFDE00; -fx-text-fill: #FFDE00;");
        }
        if (passwordValue.isEmpty()) {
            passwordVal.setStyle("-fx-border-color: red; -fx-text-fill: red;");
            new animatefx.animation.Shake(passwordVal).play();
        } else {
            passwordVal.setStyle("-fx-border-color: #FFDE00; -fx-text-fill: #FFDE00;");
        }
        int status = Check_Login(emailValue.trim().toLowerCase(), passwordValue);
        switch (status) {
            case 0: {
                try {
                    Stage stage1 = (Stage) emailVal.getScene().getWindow();
                    stage1.close();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Views/ApplicationShape.fxml"));
                    loader.load();
                    Stage stage = new Stage();
                    Parent parent = loader.getRoot();
                    stage.setScene(new Scene(parent));
                    //stage.getIcons().add(new Image("/Images/reading_127px.png"));
                    stage.setMaximized(true);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException ex) {
                }
            }
                break;
                case 1: {
                    errorMessage.setVisible(true);
                    errorMessage.setTextFill(Color.RED);
                    errorMessage.setText("Nom d'utilisateur ou mot de passe incorrect!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                }
                break;
                case -1: {
                    errorMessage.setVisible(true);
                    errorMessage.setTextFill(Color.RED);
                    errorMessage.setText("Échec de la connexion!");
                    new animatefx.animation.BounceIn(errorMessage).play();

                }
                break;
        }
    }

    public int Check_Login(String email, String password) {
        Connection Con = con;
        String SQL = null;

        if (Con == null) {
            return -1;
        }
        SQL = "SELECT * FROM admin WHERE (email = ? OR username = ?) AND password = ?";
        try {
            PreparedStatement prest = Con.prepareStatement(SQL);
            prest.setString(1, email);
            prest.setString(2, email);
            prest.setString(3, password);
            ResultSet res = prest.executeQuery();
            while (res.next()) {
                return 0;
            }

        } catch (SQLException ex) {
        }
        return 1;
    }

    @FXML
    void forgotPassword(MouseEvent event) {
        errorMessage.setVisible(true);
        errorMessage.setTextFill(Color.valueOf("#ffde00"));
        errorMessage.setText("Veuillez contacter votre administrateur");
        new animatefx.animation.BounceIn(errorMessage).play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setVisible(false);
    }
}

package com.gestion_des_taxis.gestion_des_taxis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/LoginPage.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Gestion des Taxis - Login");
        // stage.getIcons().add(new Image("/Images/reading_127px.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
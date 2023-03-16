package com.gestion_des_taxis.gestion_des_taxis.Models;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

public class Tools extends Thread {

    private String query = null;
    private Connection con = null;
    private PreparedStatement preStatment = null;
    private ResultSet resultSet = null;

    private String nom;
    private String prenom;
    private String email;
    private String username;
    private String password;
    private String emailType;

    public Tools() {

    }

    public Tools(String email, String nom, String prenom, String username, String password, String emailType) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.username = username;
        this.password = password;
        this.emailType = emailType;
    }

    @Override
    public void run() {
        try {
            sendEmail(email, prenom, nom, username, password, emailType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void alert(StackPane stackPane, AnchorPane anchorPane, String message) {
        BoxBlur Blur = new BoxBlur(3, 3, 3);
        JFXDialogLayout DialogLayout = new JFXDialogLayout();
        MFXButton button = new MFXButton("Okay");
        button.setStyle(message);
        button.setFocusTraversable(false);
        button.setPrefSize(140, 40);
        button.setFont(new Font(17));
        button.setCursor(Cursor.HAND);
        button.setStyle("-fx-background-color : #05071F;\n" + "-fx-text-fill : #e7e5e5;\n" + "-fx-border-radius: 5;");
        button.setOnMouseEntered(mouseEvent -> {
            button.setStyle("-fx-background-color : #FFDE00;\n" + "-fx-text-fill: black;\n" + "-fx-cursor: hand;");
        });
        button.setOnMouseExited(mouseEvent -> {
            button.setStyle("-fx-background-color : #05071F;\n" + "-fx-text-fill : #e7e5e5;\n" + "-fx-border-color:  #FFDE00;\n" + "-fx-border-radius: 5;");
        });
        DialogLayout.setActions(button);
        JFXDialog Dialog = new JFXDialog(stackPane, DialogLayout, JFXDialog.DialogTransition.TOP);
        // DialogLayout.setPrefSize(300, 50);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, ((MouseEvent Event) -> {
            Dialog.close();
        }));
        Label label = new Label(message);
        label.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 20px; -fx-text-fill : #52438F;");
        DialogLayout.setBody(label);
        Dialog.show();
        anchorPane.setEffect(Blur);
        Dialog.setOnDialogClosed((JFXDialogEvent Event) -> {
            anchorPane.setEffect(null);
        });
    }

    public int testColumn(String tableName, String columnNmae, String value) {
        int Count = 0;
        try {
            con = DataBaseConnection.GetConnection();
            query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnNmae + " = '" + value + "'";
            preStatment = con.prepareStatement(query);
            resultSet = preStatment.executeQuery();
            resultSet.next();
            Count = resultSet.getInt(1);
        } catch (SQLException ex) {
        }
        return Count;
    }

    public int testColumnForEdit(String tableName, String columnNmae, String value, int id) {
        int Count = 0;
        try {
            con = DataBaseConnection.GetConnection();
            query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnNmae + " = '" + value + "' AND id != " + id;
            preStatment = con.prepareStatement(query);
            resultSet = preStatment.executeQuery();
            resultSet.next();
            Count = resultSet.getInt(1);
        } catch (SQLException ex) {
        }
        return Count;
    }

    public static String encryptPassword(String password) {
        Encoder encoder = Base64.getEncoder();
        String originalString = password;
        String encodedString = encoder.encodeToString(originalString.getBytes());
//        System.out.println("Encrypted Value :: " + encodedString);
        return encodedString;
    }

    public static String decryptPassword(String Password) {
        System.out.println("Password " + Password);
        Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(Password);
//        System.out.println("Decrypted Value :: " +new String(bytes));
        return new String(bytes);
    }


    public static String generatePassword() {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[8];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for (int i = 4; i < 8; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return new String(password);
    }


    public static boolean checkInternetConnection() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    public static void sendEmail(String recipient, String nom, String prenom, String username, String password, String emailType) throws Exception {
        if (checkInternetConnection()) {
            System.out.println("Praparing sent email");
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            String myEmail = "expresstaxislimited@gmail.com";
            String myPassword = "fojlgfwiozdawgiz";


            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myEmail, myPassword);
                }
            });
            Message message = preapareMessage(session, myEmail, recipient, nom, prenom, username, password, emailType);
            Transport.send(message);
            System.out.println("message sent!");
        }


    }

    private static Message preapareMessage(Session session, String MyEmail, String recipient, String nom, String prenom, String username, String password, String emailType) {
        try {
            if (username.isEmpty()) {
                username = nom + "_" + prenom + "_" + new Random(9);
                password = Tools.generatePassword();
            }
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MyEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            if ("New Account".equals(emailType)) {
                message.setSubject("Welcom to Our Company");
                message.setText("Hello " + prenom + " " + nom + ", \n\n" + "Tu es prêt. \nVous pouvez maintenant profiter de nos services" + "\n\nvos informations de connexion: " + "\nEmail: " + recipient + "\n" + "\nUsername: " + username + "\n" + "Mot de passe: " + password + "\n" + "Remarque: Vous pouvez modifier votre nom d'utilisateur et votre mot de passe à partir de votre compte.");
            }
            if ("Update Account".equals(emailType)) {
                message.setSubject("Votre mot de passe a été réinitialisé.");
                message.setText("Hello " + prenom + " " + nom + ", \n\n" + "Nous réinitialisons vos informations de connexion pour vous.\n\nVos informations de connexion:" + "\nEmail: " + recipient + "\n" + "\nUsername: " + username + "\n" + "Mot de passe: " + password + "\n" + "Remarque: Vous pouvez modifier votre nom d'utilisateur et votre mot de passe à partir de votre compte.");
            }
            if ("Delete Account".equals(emailType)) {
                message.setSubject("Your account have been deleted!");
                message.setText("Hello " + prenom + " " + nom + ", \n\n" + "Malheureusement votre compte a été supprimé, vous ne pourrez plus vous connecter à votre compte.\n" + "Si vous pensez qu'une erreur a été commise, veuillez nous contacter.");
            }

            return message;
        } catch (MessagingException ex) {
        }
        return null;
    }
}

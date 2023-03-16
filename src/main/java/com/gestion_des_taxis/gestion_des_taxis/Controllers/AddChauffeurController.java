    package com.gestion_des_taxis.gestion_des_taxis.Controllers;

    import com.gestion_des_taxis.gestion_des_taxis.Models.Chauffeur;
    import com.gestion_des_taxis.gestion_des_taxis.Models.DataBaseConnection;
    import com.gestion_des_taxis.gestion_des_taxis.Models.Tools;
    import io.github.palexdev.materialfx.controls.MFXButton;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.fxml.Initializable;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextField;
    import javafx.scene.input.MouseEvent;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.StackPane;
    import javafx.stage.Stage;

    import java.io.FileNotFoundException;
    import java.net.URL;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.util.Random;
    import java.util.ResourceBundle;


    public class AddChauffeurController implements Initializable {


        @FXML
        private TextField adresseVal;

        @FXML
        private TextField emailVal;

        @FXML
        private Label errorMessage;

        @FXML
        private MFXButton modifierBtn;

        @FXML
        private Label pageTitle;

        @FXML
        private TextField nomVal;

        @FXML
        private TextField CNEVal;

        @FXML
        private TextField NumpermisVal;

        @FXML
        private MFXButton ajouterBtn;

        @FXML
        private MFXButton anulerBtn;

        @FXML
        private TextField prenomVal;

        @FXML
        private TextField telephoneVal;

        @FXML
        private AnchorPane anchorPane;

        @FXML
        private StackPane stackPane;


        public static boolean isEdit;

        public static int chauffuerId;


        String query = null;
        Connection con = null;

        PreparedStatement PpeStatment = null;

        ResultSet resultSet = null;

        Chauffeur chauffeur = null;

        public static int chauffeurId;

        ObservableList<String> authorList = FXCollections.observableArrayList();

        public void ajouter(MouseEvent mouseEvent) throws FileNotFoundException {
            con = DataBaseConnection.GetConnection();
            String numPermis = NumpermisVal.getText();
            String CNE = CNEVal.getText();
             String nom = nomVal.getText();
            String prenom = prenomVal.getText();
            String adresse = adresseVal.getText();
            String telephone = telephoneVal.getText();
            String email = emailVal.getText();


            if (numPermis.isEmpty() || CNE.isEmpty() || nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || email.isEmpty() ||  (!(email.matches("[a-zA-Z0-9_@.]{10,}")))
                    || (!(telephone.matches("[0-9+]{9,}"))) || (!(numPermis.matches("^[a-zA-Z0-9]*$")))) {
                if (numPermis.isEmpty()) {
                    NumpermisVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(NumpermisVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("Le numero de Permis est obligatoire!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                } else {
                    NumpermisVal.setStyle("-fx-border-color: white;");
                }
                if (CNE.isEmpty()) {
                    CNEVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(CNEVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("Le CNE est obligatoire!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                } else {
                    CNEVal.setStyle("-fx-border-color: white;");
                }
                if (nom.isEmpty()) {
                    nomVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(nomVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("Le nom est obligatoire!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                } else {
                    nomVal.setStyle("-fx-border-color: white;");
                }
                if (prenom.isEmpty()) {
                    prenomVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(prenomVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("Le prenom est obligatoire!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                } else {
                    prenomVal.setStyle("-fx-border-color: white;");
                }
                if (telephone.isEmpty()) {
                    telephoneVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(telephoneVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("Le numero de téléphone est obligatoire!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                } else {
                    telephoneVal.setStyle("-fx-border-color: white;");
                }


                if (email.isEmpty()) {
                    emailVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(emailVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("L'E-mail est obligatoire!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                } else {
                    emailVal.setStyle("-fx-border-color: white;");
                }
                if (!(email.matches("[a-zA-Z0-9_@.]{10,}")) && (!(email.isEmpty()))) {
                    emailVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(emailVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("E-mail est incorrect!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                }
                if (!(telephone.matches("[0-9+]{9,}")) && (!(telephone.isEmpty()))) {
                    telephoneVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(telephoneVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("Le numero de téléphone est incorrect!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                }
                if (!(numPermis.matches("^[a-zA-Z0-9]*$")) && (!(numPermis.isEmpty()))) {
                    NumpermisVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(NumpermisVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("Le Numero de permis est incorrect!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                }
                System.out.println("test if");
            } else {
                System.out.println("test else");
                String username = nom + "_" + prenom + "_" + new Random(9);
                String password = Tools.generatePassword();
                Chauffeur chauffeur = new Chauffeur();
                int telephoneCount = 0;
                int emailCount = 0;
                int CNEcount =0;
                ///Test CNE And Moblie if are already exist
                System.out.println("test if isedit");
                Tools T = new Tools();
                telephoneCount = T.testColumn("chauffeur", "telephone", telephone.trim().toLowerCase());
                emailCount = T.testColumn("chauffeur", "email", email.trim().toLowerCase());
                CNEcount = T.testColumn("chauffeur","CNE",CNE.trim().toLowerCase());
                if (telephoneCount > 0 || emailCount > 0 || CNEcount >0) {
                    System.out.println("test if telephoneCount > 0 || emailCount > 0 || CNEcount >0");
                    if (CNEcount > 0) {
                        System.out.println("test if CNEcount > 0");
                        Tools.alert(stackPane, anchorPane, "CNE existe déjà!");
                    }
                    if (telephoneCount > 0) {
                        System.out.println("test if telephoneCount > 0");
                        Tools.alert(stackPane, anchorPane, "Ce numéro de téléphone existe déjà!");
                    }
                    if (emailCount > 0) {
                        System.out.println("test if emailCount > 0");
                        Tools.alert(stackPane, anchorPane, "Ce E-mail existe déjà!");
                    }
                } else {
                    System.out.println("test if else fin");
                    chauffeur.addChauffeur(CNE,numPermis,nom, prenom, adresse, telephone, email, username, password);
                }
                if (!(CNEcount > 0 || telephoneCount > 0 || emailCount > 0 )) {
                    Tools.alert(stackPane, anchorPane, "Le chauffeur a été ajouté avec succès!");
                }
            }
        }
        public void setModifierBtn(boolean visible) {
            this.modifierBtn.setVisible(visible);
        }

        public void modifier(MouseEvent mouseEvent) {

            con = DataBaseConnection.GetConnection();
            String CNE = CNEVal.getText();
            String numPermis = NumpermisVal.getText();
            String nom = nomVal.getText();
            String prenom = prenomVal.getText();
            String adresse = adresseVal.getText();
            String telephone = telephoneVal.getText();
            String email = emailVal.getText();
            if ( CNE.isEmpty()|| nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || email.isEmpty() || (!(email.matches("[a-zA-Z0-9_@.]{10,}")))
                    || (!(telephone.matches("[0-9+]{9,}"))) || (!(numPermis.matches("^[a-zA-Z0-9]*$")))) {
                if (nom.isEmpty()) {
                    nomVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(nomVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("Le nom est obligatoire!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                } else {
                    nomVal.setStyle("-fx-border-color: white;");
                }
                if (prenom.isEmpty()) {
                    prenomVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(prenomVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("Le prenom est obligatoire!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                } else {
                    prenomVal.setStyle("-fx-border-color: white;");
                }
                if (telephone.isEmpty()) {
                    telephoneVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(telephoneVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("Le numero de téléphone est obligatoire!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                } else {
                    telephoneVal.setStyle("-fx-border-color: white;");
                }

                if (email.isEmpty()) {
                    emailVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(emailVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("L'E-mail est obligatoire!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                } else {
                    emailVal.setStyle("-fx-border-color: white;");
                }
                if (!(email.matches("[a-zA-Z0-9_@.]{10,}")) && (!(email.isEmpty()))) {
                    emailVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(emailVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("E-mail est incorrect!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                }
                if (!(telephone.matches("[0-9+]{9,}")) && (!(telephone.isEmpty()))) {
                    telephoneVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(telephoneVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("Le numero de téléphone est incorrect!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                }
                if (!(numPermis.matches("^[a-zA-Z0-9]*$")) && (!(numPermis.isEmpty()))) {
                    NumpermisVal.setStyle("-fx-border-color: red;");
                    new animatefx.animation.Shake(NumpermisVal).play();
                    errorMessage.setVisible(true);
                    errorMessage.setText("Le Numero de permis est incorrect!");
                    new animatefx.animation.BounceIn(errorMessage).play();
                }
                System.out.println("test if");
            }
            else {
                System.out.println("test else");
                chauffeur = new Chauffeur();
                int telephoneCount = 0;
                int emailCount = 0;
                int CNEcount=0;
                ///Test CNE And Moblie if are already exist
                System.out.println("test if isedit");
                Tools T = new Tools();
                telephoneCount = T.testColumnForEdit("chauffeur", "telephone", telephone.trim().toLowerCase(), chauffeurId);
                emailCount = T.testColumnForEdit("chauffeur", "email", email.trim().toLowerCase(), chauffeurId);
                CNEcount = T.testColumnForEdit("chauffeur","CNE",CNE.trim().toLowerCase(), chauffeurId);
                if (telephoneCount > 0 || emailCount > 0 || CNEcount > 0) {
                    System.out.println("test if telephoneCount > 0 || emailCount > 0 || test if CNEcount > 0");
                    if (telephoneCount > 0) {
                        System.out.println("test if telephoneCount > 0");
                        Tools.alert(stackPane, anchorPane, "Ce numéro de téléphone existe déjà!");
                    }
                    if (emailCount > 0) {
                        System.out.println("test if emailCount > 0");
                        Tools.alert(stackPane, anchorPane, "Ce E-mail existe déjà!");
                    }
                    if (CNEcount > 0) {
                        System.out.println("test if CNECount > 0");
                        Tools.alert(stackPane, anchorPane, "CNE existe déjà!");
                    }
                } else {
                    System.out.println("test if else fin");
                    chauffeur.editChauffeur(chauffeurId,CNE,numPermis, nom, prenom, adresse, telephone, email);
                }
                if (!(telephoneCount > 0 || emailCount > 0 || CNEcount>0)) {
                    Tools.alert(stackPane, anchorPane, "Le chauffeur a été modifie avec succès!");
                }
            }
        }


        public void anulLer(MouseEvent mouseEvent) {
            Stage stage = (Stage) nomVal.getScene().getWindow();
            stage.close();

        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            errorMessage.setVisible(false);
        }

        public void fillFields(int id,String CNE,String numPermis ,String nom, String prenom, String telephone, String email, String adresse) {
            CNEVal.setText(CNE);
            NumpermisVal.setText(numPermis);
            chauffeurId = id;
            nomVal.setText(nom);
            prenomVal.setText(prenom);
            emailVal.setText(email);
            telephoneVal.setText(telephone);
            adresseVal.setText(adresse);
        }

        public void pageTitle(String TitleLabel) {
            this.pageTitle.setText(TitleLabel);
        }

        public void setAjouterBtn(boolean visible) {
            this.ajouterBtn.setVisible(visible);
        }
        public void setEdit(boolean Edit) {
            this.isEdit = Edit;
        }
    }

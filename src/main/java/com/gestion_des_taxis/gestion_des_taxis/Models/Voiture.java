package com.gestion_des_taxis.gestion_des_taxis.Models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Voiture {
    private int id;
    private String immatriculation;
    private String marque;
    private String model;
    private String carburant;
    private int consomation_moyenne;
    private int puissance_fiscale;
    private Date date_1er_immatru;
    private String etat;
    private int killometrage;
    private Date date_dernier_controle_tech;
    private int km_dernier_vidange;
    private int km_dernier_courroie;
    private String img_path;

    private String query = null;
    private Connection con = null;
    private PreparedStatement preStatment = null;
    private ResultSet resultSet = null;


    public Voiture() {
    }

    public Voiture(int id, String immatriculation, String marque, String model, String carburant, int puissance_fiscale, Date date_1er_immatru, String etat) {
        this.id = id;
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.model = model;
        this.carburant = carburant;
        this.puissance_fiscale = puissance_fiscale;
        this.date_1er_immatru = date_1er_immatru;
        this.etat = etat;
    }

    public Voiture(int id, String immatriculation, String marque, String model, String carburant, int consomation_moyenne, int puissance_fiscale, Date date_1er_immatru, String etat, int killometrage, Date date_dernier_controle_tech, int km_dernier_vidange, int km_dernier_courroie, String img_path) {
        this.id = id;
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.model = model;
        this.carburant = carburant;
        this.consomation_moyenne = consomation_moyenne;
        this.puissance_fiscale = puissance_fiscale;
        this.date_1er_immatru = date_1er_immatru;
        this.etat = etat;
        this.killometrage = killometrage;
        this.date_dernier_controle_tech = date_dernier_controle_tech;
        this.km_dernier_vidange = km_dernier_vidange;
        this.km_dernier_courroie = km_dernier_courroie;
        this.img_path = img_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public int getConsomation_moyenne() {
        return consomation_moyenne;
    }

    public void setConsomation_moyenne(int consomation_moyenne) {
        this.consomation_moyenne = consomation_moyenne;
    }

    public int getPuissance_fiscale() {
        return puissance_fiscale;
    }

    public void setPuissance_fiscale(int puissance_fiscale) {
        this.puissance_fiscale = puissance_fiscale;
    }

    public Date getDate_1er_immatru() {
        return date_1er_immatru;
    }

    public void setDate_1er_immatru(Date date_1er_immatru) {
        this.date_1er_immatru = date_1er_immatru;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getKillometrage() {
        return killometrage;
    }

    public void setKillometrage(int killometrage) {
        this.killometrage = killometrage;
    }

    public Date getDate_dernier_controle_tech() {
        return date_dernier_controle_tech;
    }

    public void setDate_dernier_controle_tech(Date date_dernier_controle_tech) {
        this.date_dernier_controle_tech = date_dernier_controle_tech;
    }

    public int getKm_dernier_vidange() {
        return km_dernier_vidange;
    }

    public void setKm_dernier_vidange(int km_dernier_vidange) {
        this.km_dernier_vidange = km_dernier_vidange;
    }

    public int getKm_dernier_courroie() {
        return km_dernier_courroie;
    }

    public void setKm_dernier_courroie(int km_dernier_courroie) {
        this.km_dernier_courroie = km_dernier_courroie;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public void addVoiture(String immatriculation, String model, String marque, String carburant, String etat){
        try {
            con = DataBaseConnection.GetConnection();
            query = "INSERT INTO `voiture` (`immatriculation`, `model`, `marque`, `carburant`, `etat`)" +
                    " VALUES (?,?,?,?,?)";
            preStatment = con.prepareStatement(query);
            preStatment.setString(1, immatriculation);
            preStatment.setString(2, model);
            preStatment.setString(3, marque);
            preStatment.setString(4, carburant);
            preStatment.setString(5, etat);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    public void editVoiture(int id, String immatriculation, String model, String marque, String carburant, String etat) {
        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `voiture` SET `immatriculation`=?,"
                    + "`model`=?,`marque`=?,"
                    + "`carburant`=?,`etat`=?"
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.setString(1, immatriculation);
            preStatment.setString(2, model);
            preStatment.setString(3, marque);
            preStatment.setString(4, carburant);
            preStatment.setString(5, etat);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void deleteVoiture(){}
    public void asearchVoiture(){}
    public void generateFichTech(){}
    public void exportVoitures(){}


    public void softDelete(int id) {

        try {
            con = DataBaseConnection.GetConnection();
            query = "UPDATE `voiture` SET `soft_delete`= CURRENT_TIMESTAMP "
                    + "WHERE `Id`='" + id + "'";
            preStatment = con.prepareStatement(query);
            preStatment.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

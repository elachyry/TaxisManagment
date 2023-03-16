package com.gestion_des_taxis.gestion_des_taxis.Models;

import io.github.palexdev.materialfx.controls.MFXButton;

import javax.swing.text.Element;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Personne {

    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String Telephone;
    private String email;
    private String Username;
    private String password;
    private String cin;


    public Personne() {
        super();
    }

    public Personne(String nom, String prenom, String adresse, String telephone, String email,
            String username, String password, int id) throws FileNotFoundException {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        Telephone = telephone;
        this.email = email;
        Username = username;
        this.password = password;
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdress() {
        return adresse;
    }

    public void setAdress(String adress) {
        this.adresse = adress;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

}

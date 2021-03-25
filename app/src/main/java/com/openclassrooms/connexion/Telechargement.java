package com.openclassrooms.connexion;

public class Telechargement {

    private String description, nom, imageUrl;

    public Telechargement() {
    }

    public Telechargement(String description, String nom, String imageUrl) {
        this.description = description;
        this.nom = nom;
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

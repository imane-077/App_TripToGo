package com.openclassrooms.connexion;

public class Model {
    String pays, lieu, env, image, budget;
    public Model(){
    }

    public Model(String pays, String lieu, String env, String image, String budget) {
        this.pays = pays;
        this.lieu = lieu;
        this.env = env;
        this.image = image;
        this.budget = budget;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBudget() { return budget; }

    public void setBudget(String budget) { this.budget = budget; }
}

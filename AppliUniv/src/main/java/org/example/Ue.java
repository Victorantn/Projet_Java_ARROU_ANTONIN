package org.example;

import java.util.ArrayList;

public class Ue {
    private static int compteur =0;
    private int codeUe;
    private String nomUe;
    private int ects;
    private boolean ouverture;
    private ArrayList<Ue> uePreRequis = new ArrayList<>();


    /**
     * <p>Cette méthode est le constructeur de notre classe Ue</p>
     * @param nomUe est le nom de l'Ue
     * @param ects est le nombre d'ECTs de l'Ue
     * @param ouverture est le type d'Ue
     */
    public Ue(String nomUe, int ects, boolean ouverture) {
        compteur++;
        this.codeUe=compteur;
        this.nomUe = nomUe;
        this.ects = ects;
        this.ouverture = ouverture;
    }

    /**
     * <p>Un getter qui permet d'obtenir le CodeUe</p>
     * @return le code de l'ue
     */
    public int getCodeUe() {
        return codeUe;
    }

    /**
     * <p>Un getter qui permet d'obtenir le NomUe</p>
     * @return le nom de l'ue
     */
    public String getNomUe() {
        return nomUe;
    }

    /**
     * <p>Un getter qui permet d'obtenir le nombre de credits ECTS</p>
     * @return le nombre de credits ECTS
     */
    public int getEcts() {
        return ects;
    }

    /**
     * <p>Une fonction qui permet de savoir si l'ue est une ue d'ouverture</p>
     * @return un boolean indiquant true si c'est une ue d'ouverture et false sinon
     */
    public boolean isOuverture() {
        return ouverture;
    }

    /**
     * <p>un getter qui permet d'obtenir les Ue prerequises</p>
     * @return la liste des Ue Prerequises
     */
    public ArrayList<Ue> getUePreRequis() {
        return uePreRequis;
    }
}

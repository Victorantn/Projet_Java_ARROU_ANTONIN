package org.example;

import java.util.ArrayList;

public class Formation {
    private static int compteur=0; /*Sert a connaitre le numéro de la dernière formation instanciée*/
    private int codeFormation;
    private String nomFormation;
    private ArrayList<Ue> ueBase = new ArrayList<>();

    /**
     * <p>Cette méthode est le constructeur de notre classe Formation</p>
     * @param nomFormation est le nom de la formation
     */
    public Formation(String nomFormation) {
        compteur++;/*Augmente de 1 pour la prochaine formation instanciée*/
        this.codeFormation =compteur;
        this.nomFormation = nomFormation;
    }

    /**
     * <p>Un getter qui permet d'obtenir le code le la formation</p>
     * @return le code le la formation
     */
    public int getCodeFormation() {
        return codeFormation;
    }

    /**
     * <p>Un getter qui permet d'obtenir le nom le la formation</p>
     * @return le nom de la formation
     */
    public String getNomFormation() {
        return nomFormation;
    }

    /**
     * <p>Un getter qui permet d'obtenir la liste des Ue de base le la formation</p>
     * @return la liste des Ue de base le la formation
     */
    public ArrayList<Ue> getUeBase() {
        return ueBase;
    }
}

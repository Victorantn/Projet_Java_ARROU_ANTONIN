package org.example;

import java.util.ArrayList;

public class Parcours{
    private static int compteur=0;/*Sert a connaitre le numéro du dernier parcours instancié*/
    private int codeParcours;
    private String nomParcours;
    private Formation formation;
    private ArrayList<Ue> ueSpe = new ArrayList<>();


    /**
     * <p>Cette méthode est le constructeur de notre classe Parcours</p>
     * @param nomParcours est le nom du parcours
     * @param formation est le nom de la formation a laquelle le parcours est rattaché
     */
    public Parcours(String nomParcours,Formation formation) {
        compteur++;/*Augmente de 1 pour le prochain parcours instancié*/
        this.codeParcours=compteur;
        this.nomParcours = nomParcours;
        this.formation=formation;
    }

    /**
     * <p>Un getter qui permet d'obtenir le code du parcours</p>
     * @return le code du parcours
     */
    public int getIdParcours() {
        return codeParcours;
    }

    /**
     * <p>Un getter qui permet d'obtenir le nom du parcours</p>
     * @return le nom du parcours
     */
    public String getNomParcours() {
        return nomParcours;
    }

    /**
     * <p>Un getter qui permet d'obtenir la formation a laquelle le parcours est lié</p>
     * @return la formation a laquelle le parcours est lié
     */
    public Formation getFormation() {
        return formation;
    }

    /**
     * <p>Un getter qui permet d'obtenir la liste des Ue speciale au parcours</p>
     * @return la liste des Ue speciale au parcours
     */
    public ArrayList<Ue> getUeSpe() {
        return ueSpe;
    }

    public String toString(){
        String s="";
        s+="------------------" + "\n";
        s +="CodeParcours : " + codeParcours+"\n";
        s +="NomParcours : " + nomParcours+"\n";
        s +="Formation : " + formation.getNomFormation() +"\n";
        s +="UeSpe : " + "\n";
        for(Ue u : ueSpe){
            s += "   - nomUe : " + u.getNomUe() + " | codeUe : " + u.getCodeUe() + "\n";
        }
        s+="------------------" + "\n";
        return s;
    }
}

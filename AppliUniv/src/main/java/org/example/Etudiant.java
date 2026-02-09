package org.example;

import java.util.ArrayList;

public class Etudiant {
    private static int compteur =26300000; /*Sert a connaitre le numéro du dernier étudiant instancié*/
    private int numeroEtudiant;
    private String nomE;
    private String prenomE;
    private Formation formation;
    private int totalECTS = 0;
    private ArrayList<InscriptionUe> inscriptions = new ArrayList<>();
    private Parcours parcours;


    /**
     * <p>Cette méthode est le constructeur de notre classe Etudiant</p>
     * @param nomE est le nom de l'étudiant
     * @param prenomE est le prénom de l'étudiant
     * @param formation est la formation de l'étudiant
     */
    public Etudiant(String nomE, String prenomE, Formation formation, Parcours parcours) {
        compteur++; /*Augmente de 1 pour le prochain étudiant instancié*/
        this.numeroEtudiant =compteur;
        this.nomE = nomE;
        this.prenomE = prenomE;
        this.formation = formation;
        this.parcours = parcours
    }

    /**
     * <p>Un getter qui permet d'obtenir le numero etudiant</p>
     * @return le numero etudiant
     */
    public int getNumeroEtudiant() {
        return numeroEtudiant;
    }

    /**
     * <p>Un getter qui permet d'obtenir le nom de l'etudiant</p>
     * @return le nom de l'etudiant
     */
    public String getNomE() {
        return nomE;
    }

    /**
     * <p>Un getter qui permet d'obtenir le prenom de l'etudiant</p>
     * @return le prenom de l'etudiant
     */
    public String getPrenomE() {
        return prenomE;
    }

    /**
     * <p>Un getter qui permet d'obtenir la formation de l'etudiant</p>
     * @return la formation de l'etudiant
     */
    public Formation getFormation() {
        return formation;
    }

    /**
     * <p>Un getter qui permet d'obtenir le nombre total d'ECTS de l'etudiant</p>
     * @return le nombre total d'ECTS de l'etudiant
     */
    public int getTotalECTS() {
        return totalECTS;
    }

    /**
     * <p>Un getter qui permet d'obtenir la liste des inscriptionsUE de l'etudiant</p>
     * @return la liste des inscriptionsUE de l'etudiant
     */
    public ArrayList<InscriptionUe> getInscriptions() {
        return inscriptions;
    }


    /**
     * <p>Le toString de notre classe Etudiant, elle retourne les informations les plus importantes par Etudiant sous forme de chaine de charactères</p>
     * @return le numero étudiant, le nom de l'étudiant, le prénom de l'étudiant, la formation à laquelle il appartient, le parcours dans lequel il étudie, la liste d'inscription d'Ue auxquelles il est inscrit ainsi que le total d'ects validé
     */
    public String toString(){
        String s="";
        s+="------------------" + "\n";
        s+="numeroEtudiant : "+numeroEtudiant +"\n";
        s+="nomE : "+nomE + "\n";
        s+="prenomE : "+prenomE + "\n";
        s+="formation : " + formation.getNomFormation() + "\n";
        s+="parcours : " + formation.getNomFormation() + "\n";
        s+="Inscriptions : " + "\n";
        for(InscriptionUe i : inscriptions){
            s+= "   - nomUe : " + i.getUe().getNomUe() + " | codeUe : " + i.getUe().getCodeUe() + "\n";
        }
        s+="totalECTS : " + totalECTS + "\n";
        s+="------------------" + "\n";
        return s;

    }
}

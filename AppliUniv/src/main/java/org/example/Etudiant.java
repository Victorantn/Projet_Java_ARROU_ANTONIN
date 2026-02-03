package org.example;

import java.util.ArrayList;

public class Etudiant {
    private static int compteur =26300000;
    private int numeroEtudiant;
    private String nomE;
    private String prenomE;
    private Formation formation;
    private int totalECTS = 0;
    private ArrayList<InscriptionUe> inscriptions =new ArrayList<>();


    /**
     * <p>Cette méthode est le constructeur de notre classe Etudiant</p>
     * @param nomE est le nom de l'étudiant
     * @param prenomE est le prénom de l'étudiant
     * @param formation est la formation de l'étudiant
     */
    public Etudiant(String nomE, String prenomE, Formation formation) {
        compteur++;
        this.numeroEtudiant =compteur;
        this.nomE = nomE;
        this.prenomE = prenomE;
        this.formation = formation;
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
}

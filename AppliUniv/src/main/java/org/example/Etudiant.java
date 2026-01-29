package org.example;

import java.util.ArrayList;

public class Etudiant {
    private static int numeroEtudiant= 22300000;
    private String nomE;
    private String prenomE;
    private Formation formation;
    private int totalECTS = 0;
    ArrayList<InscriptionUe> inscriptions =new ArrayList<>();


    /**
     * <p>Cette méthode est le constructeur de notre classe Etudiant</p>
     * @param numeroEtudiant est le numéro unique d'un étudiant
     * @param nomE est le nom de l'étudiant
     * @param prenomE est le prénom de l'étudiant
     * @param formation est la formation de l'étudiant
     */

    public Etudiant(int numeroEtudiant, String nomE, String prenomE, Formation formation) {
        this.numeroEtudiant +=1;
        this.nomE = nomE;
        this.prenomE = prenomE;
        this.formation = formation;
    }
}

package org.example;

import java.util.ArrayList;

public class Etudiant {
    private int numeroEtudiant;
    private String nomE;
    private String prenomE;
    private Formation formation;
    private int totalECTS = 0;
    ArrayList<InscriptionUe> inscriptions =new ArrayList<>();

    public Etudiant(int numeroEtudiant, String nomE, String prenomE, Formation formation) {
        this.numeroEtudiant = numeroEtudiant;
        this.nomE = nomE;
        this.prenomE = prenomE;
        this.formation = formation;
    }
}

package org.example;

public class InscriptionUe {
    private int codeInscrip=0;
    private Ue ue;
    private String anneeUniversitaire;
    private Semestre semestre;
    private Statut statut;

    public InscriptionUe(Ue ue, String anneeUniversitaire, Semestre semestre){
        this.codeInscrip+=1;
        this.ue = ue;
        this.anneeUniversitaire = anneeUniversitaire;
        this.semestre = semestre;
        this.statut = Statut.ENCOURS;
    }

}

package org.example;

public class InscriptionUe {
    private Ue ue;
    private String anneeUniversitaire;
    private Semestre semestre;
    private Statut statut;

    public InscriptionUe(Ue ue, String anneeUniversitaire, Semestre semestre){
        this.ue = ue;
        this.anneeUniversitaire = anneeUniversitaire;
        this.semestre = semestre;
        this.statut = new Statut("en cours");
    }

}

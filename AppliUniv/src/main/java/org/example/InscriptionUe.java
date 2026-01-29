package org.example;

public class InscriptionUe {
    private int codeInscrip=0;
    private Ue ue;
    private String anneeUniversitaire;
    private Semestre semestre;
    private Statut statut;

    /**
     * <p>Cette méthode est le constructeur de notre classe InscriptionUe</p>
     * @param ue est l'Ue auquel on souhaite inscrire l'étudiant
     * @param anneeUniversitaire est l'année universitaire correspondante
     * @param semestre est le type de semestre correspondant
     */


    public InscriptionUe(Ue ue, String anneeUniversitaire, Semestre semestre){
        this.codeInscrip+=1;
        this.ue = ue;
        this.anneeUniversitaire = anneeUniversitaire;
        this.semestre = semestre;
        this.statut = Statut.ENCOURS;
    }

}

package org.example;

public class InscriptionUe {
    private static int compteur=0;/*Sert a connaitre le numéro de la dernière inscription instanciée*/
    private int codeInscrip;
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
        compteur++; compteur++;/*Augmente de 1 pour la prochaine inscriptio, instanciée*/
        this.codeInscrip=compteur;
        this.ue = ue;
        this.anneeUniversitaire = anneeUniversitaire;
        this.semestre = semestre;
        this.statut = Statut.ENCOURS;
    }

    /**
     * <p>Un getter qui permet d'obtenir le code d'inscription a l'UE</p>
     * @return le code d'inscription a l'UE
     */
    public int getCodeInscrip() {
        return codeInscrip;
    }

    /**
     * <p>Un getter qui permet d'obtenir l'UE lié a l'inscription</p>
     * @return l'UE lié a l'inscription
     */
    public Ue getUe() {
        return ue;
    }

    /**
     * <p>Un getter qui permet d'obtenir l'année universitaire lié a l'inscription</p>
     * @return l'année universitaire lié a l'inscription
     */
    public String getAnneeUniversitaire() {
        return anneeUniversitaire;
    }

    /**
     * <p>Un getter qui permet d'obtenir le semestre lié a l'inscription</p>
     * @return le semestre lié a l'inscription
     */
    public Semestre getSemestre() {
        return semestre;
    }

    /**
     * <p>Un getter qui permet d'obtenir le statut lié a l'inscription</p>
     * @return le statut lié a l'inscription
     */
    public Statut getStatut() {
        return statut;
    }
}

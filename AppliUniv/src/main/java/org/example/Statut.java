package org.example;

public enum Statut {
    VALIDE("validé"),
    ECHOUE("echoué"),
    ENCOURS("en cours");

    private String typeStatut;

    /**
     *<p>Cette méthode est le constructeur de notre énumération Statut</p>
     * @param s est la chaine de caractère qui nous permet de visualiser le Statut d'un UE
     */

    Statut(String s) {
        this.typeStatut=s;
    }
}

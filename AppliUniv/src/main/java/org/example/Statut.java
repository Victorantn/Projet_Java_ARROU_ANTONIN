package org.example;

public enum Statut {
    VALIDE("validé"),
    ECHOUE("echoué"),
    ENCOURS("en cours");

    private String typeStatut;

    Statut(String s) {
        this.typeStatut=s;
    }
}

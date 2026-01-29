package org.example;

import java.util.ArrayList;

public class Parcours extends Formation{
    private static int idParcours = 0;
    private String nomParcours;
    ArrayList<Ue> ueSpé = new ArrayList<>();

    public Parcours(String nomParcours, String nomFormation) {
        super(nomFormation);
        Parcours.idParcours += 1;
        this.nomParcours = nomParcours;
    }

}

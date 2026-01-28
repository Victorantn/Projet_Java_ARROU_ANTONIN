package org.example;

import java.util.ArrayList;

public class Parcours extends Formation{
    private static int idParcours = 1;
    private String nomParcours;
    ArrayList<Ue> ueSpé = new ArrayList<>();



    public Parcours(int idParcours, String nomParcours) {
        this.idParcours += 1;
        this.nomParcours = nomParcours;
    }

}

package org.example;

import java.util.ArrayList;

public class Parcours extends Formation{
    private static int idParcours = 0;
    private String nomParcours;
    ArrayList<Ue> ueSpé = new ArrayList<>();


    /**
     * <p>Cette méthode est le constructeur de notre classe Parcours</p>
     * @param nomParcours est le nom du parcours
     * @param nomFormation est le nom de la formation
     */

    public Parcours(String nomParcours, String nomFormation) {
        super(nomFormation);
        Parcours.idParcours += 1;
        this.nomParcours = nomParcours;
    }

}

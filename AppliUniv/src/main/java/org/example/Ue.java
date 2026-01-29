package org.example;

import java.util.ArrayList;

public class Ue {
    private static int codeUe = 0;
    private String nomUe;
    private int ects;
    private boolean ouverture;
    private ArrayList<Ue> uePreRequis = new ArrayList<>();


    /**
     * <p>Cette méthode est le constructeur de notre classe Ue</p>
     * @param codeUe est le code unique de l'Ue
     * @param nomUe est le nom de l'Ue
     * @param ects est le nombre d'ECTs de l'Ue
     * @param ouverture est le type d'Ue
     */

    public Ue(int codeUe, String nomUe, int ects, boolean ouverture) {
        this.codeUe += 1;
        this.nomUe = nomUe;
        this.ects = ects;
        this.ouverture = ouverture;
    }
}

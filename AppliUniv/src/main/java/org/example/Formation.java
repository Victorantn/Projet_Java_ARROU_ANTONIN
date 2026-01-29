package org.example;

import java.util.ArrayList;

public class Formation {
    private static int codeFormation = 0;
    private String nomFormation;
    private ArrayList<Ue> ueBase = new ArrayList<>();

    /**
     * <p>Cette méthode est le constructeur de notre classe Formation</p>
     * @param nomFormation est le nom de la formation
     */


    public Formation(String nomFormation) {
        this.codeFormation += 1;
        this.nomFormation = nomFormation;
    }
}

package org.example;

import java.util.ArrayList;

public class Formation {
    private static int codeFormation = 1;
    private String nomFormation;
    private ArrayList<Ue> ueBase = new ArrayList<>();

    public Formation(int codeFormation, String nomFormation) {
        this.codeFormation += 1;
        this.nomFormation = nomFormation;
    }
}

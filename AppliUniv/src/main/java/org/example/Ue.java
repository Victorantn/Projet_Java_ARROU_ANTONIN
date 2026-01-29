package org.example;

import java.util.ArrayList;

public class Ue {
    private static int codeUe = 0;
    private String nomUe;
    private int ects;
    private boolean ouverture;
    private ArrayList<Ue> uePreRequis = new ArrayList<>();

    public Ue(int codeUe, String nomUe, int ects, boolean ouverture) {
        this.codeUe += 1;
        this.nomUe = nomUe;
        this.ects = ects;
        this.ouverture = ouverture;
    }
}

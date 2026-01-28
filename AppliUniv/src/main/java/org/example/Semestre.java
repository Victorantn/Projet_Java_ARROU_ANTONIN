package org.example;

public enum Semestre {
    PAIR("Semestre pair"),
    IMPAIR("Semestre impair");

    private String typeSemestre;

    Semestre(String s) {
        this.typeSemestre=s;
    }
}

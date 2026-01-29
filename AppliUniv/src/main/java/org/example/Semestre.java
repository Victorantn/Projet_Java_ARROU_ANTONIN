package org.example;

public enum Semestre {
    PAIR("Semestre pair"),
    IMPAIR("Semestre impair");

    private String typeSemestre;

    /**
     * <p>Cette méthode est le constructeur de notre énumération Semestre</p>
     * @param s est la chaine de caractère qui nous permet de visualiser le Semestre
     */

    Semestre(String s) {
        this.typeSemestre=s;
    }
}

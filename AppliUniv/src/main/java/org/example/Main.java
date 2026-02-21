package org.example;

import javafx.application.Application;
import org.example.javafx.MainApp;

/**
 * <p>Point d'entrée de l'application</p>
 *
 * <p>Cette classe contient la méthode {@link #main(String[])} qui démarre JavaFX</p>
 *
 * <p>Le démarrage est délégué à {@link MainApp}</p>
 */
public class Main {

    /**
     * <p>Méthode principale lancée par la JVM</p>
     *
     * <p>Elle lance l'application JavaFX en démarrant la classe {@link MainApp}</p>
     *
     * @param args arguments de la ligne de commande transmis à l'application
     */
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }
}
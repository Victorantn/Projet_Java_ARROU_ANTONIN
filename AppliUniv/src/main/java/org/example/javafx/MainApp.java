package org.example.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * <p>Classe principale JavaFX de l'application</p>
 *
 * <p>Elle initialise l'état global {@link AppState},
 * construit la vue principale {@link MainView}
 * puis affiche la fenêtre principale</p>
 */
public class MainApp extends Application {

    /**
     * <p>Méthode appelée automatiquement au démarrage de l'application JavaFX</p>
     *
     * <p>Elle :</p>
     * <ul>
     *   <li>instancie l'état partagé {@link AppState}</li>
     *   <li>crée la vue principale {@link MainView}</li>
     *   <li>configure la scène et la fenêtre</li>
     *   <li>affiche la fenêtre</li>
     * </ul>
     *
     * @param stage fenêtre principale fournie par JavaFX
     */
    @Override
    public void start(Stage stage) {
        AppState state = new AppState();
        MainView view = new MainView(state);

        Scene scene = new Scene(view.getRoot(), 1200, 700);
        stage.setTitle("Suivi étudiants");
        stage.setScene(scene);
        stage.show();
    }
}
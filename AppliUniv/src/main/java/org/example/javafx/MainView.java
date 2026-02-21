package org.example.javafx;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * <p>Vue principale de l'application</p>
 *
 * <p>Elle organise les différentes fonctionnalités dans des onglets :</p>
 * <ul>
 *   <li>gestion des formations et parcours</li>
 *   <li>gestion des UE et de leur affectation</li>
 *   <li>gestion des étudiants</li>
 * </ul>
 *
 * <p>Chaque onglet reçoit l'état partagé {@link AppState}
 * afin que toutes les vues manipulent les mêmes données</p>
 */
public class MainView {

    /** Conteneur racine de la vue principale */
    private final BorderPane root = new BorderPane();

    /**
     * <p>Construit la vue principale et initialise les onglets</p>
     *
     * @param state état partagé de l'application
     */
    public MainView(AppState state) {
        root.setPadding(new Insets(10));

        TabPane tabs = new TabPane(
                new Tab("Formations / Parcours", new FormationParcoursView(state).getRoot()),
                new Tab("UE / Affectation UE", new UeEtAffectationView(state).getRoot()),
                new Tab("Etudiants", new EtudiantsView(state).getRoot())
        );

        tabs.getTabs().forEach(t -> t.setClosable(false));

        root.setCenter(tabs);
    }

    /**
     * <p>Retourne le noeud racine de la vue</p>
     *
     * @return le {@link Parent} racine
     */
    public Parent getRoot() {
        return root;
    }
}
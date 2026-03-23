package org.example.javafx;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * <p>Vue principale de l'application</p>
 *
 * <p>Elle regroupe les fonctions de l'application dans quatre onglets :</p>
 * <ul>
 *   <li>formations et parcours</li>
 *   <li>UE et affectation des UE</li>
 *   <li>etudiants</li>
 *   <li>statistiques</li>
 * </ul>
 *
 * <p>Chaque onglet utilise le meme etat partage {@link AppState}</p>
 */
public class MainView {

    /** Conteneur racine de la vue principale */
    private final BorderPane root = new BorderPane();

    /**
     * <p>Construit la vue principale et initialise les onglets</p>
     *
     * @param state etat partage de l'application
     */
    public MainView(AppState state) {
        root.setPadding(new Insets(10));

        FormationParcoursView formationParcoursView = new FormationParcoursView(state);
        UeEtAffectationView ueEtAffectationView = new UeEtAffectationView(state);
        EtudiantsView etudiantsView = new EtudiantsView(state);
        StatistiquesView statistiquesView = new StatistiquesView(state);

        Tab tabFormation = new Tab("Formations / Parcours", formationParcoursView.getRoot());
        Tab tabUe = new Tab("UE / Affectation UE", ueEtAffectationView.getRoot());
        Tab tabEtudiants = new Tab("Etudiants", etudiantsView.getRoot());
        Tab tabStats = new Tab("Statistiques", statistiquesView.getRoot());

        TabPane tabs = new TabPane(tabFormation, tabUe, tabEtudiants, tabStats);

        tabs.getTabs().forEach(t -> t.setClosable(false));
        tabs.getSelectionModel().selectedItemProperty().addListener((o, a, selected) -> {
            if (selected == tabStats) statistiquesView.refreshData();
        });

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

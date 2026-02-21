package org.example.javafx;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.Formation;
import org.example.Parcours;

/**
 * <p>Vue JavaFX dédiée à la gestion des formations et des parcours</p>
 *
 * <p>Fonctionnalités principales :</p>
 * <ul>
 *   <li>créer et supprimer une {@link Formation}</li>
 *   <li>créer et supprimer un {@link Parcours} rattaché à la formation sélectionnée</li>
 *   <li>afficher la liste des formations</li>
 *   <li>afficher la liste des parcours filtrée selon la formation sélectionnée</li>
 * </ul>
 *
 * <p>Règle appliquée :</p>
 * <ul>
 *   <li>la liste des parcours affichée dépend uniquement de la formation sélectionnée</li>
 * </ul>
 */
public class FormationParcoursView {

    /** Conteneur racine de la vue */
    private final BorderPane root = new BorderPane();

    /** Etat partagé de l'application */
    private final AppState state;

    /** Liste des formations */
    private final ListView<Formation> lvFormations = new ListView<>();

    /** Liste des parcours filtrée selon la formation sélectionnée */
    private final ListView<Parcours> lvParcours = new ListView<>();

    /** Zone de messages pour l'utilisateur */
    private final Label lbl = new Label("Prêt");

    /**
     * <p>Construit la vue de gestion des formations et parcours</p>
     *
     * @param state état partagé de l'application contenant les listes observables
     */
    public FormationParcoursView(AppState state) {
        this.state = state;

        lvFormations.setItems(state.getFormations());
        lvFormations.setCellFactory(v -> new ListCell<>() {
            @Override protected void updateItem(Formation item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomFormation());
            }
        });

        lvParcours.setCellFactory(v -> new ListCell<>() {
            @Override protected void updateItem(Parcours item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomParcours());
            }
        });

        lvFormations.getSelectionModel().selectedItemProperty().addListener((o,a,f) -> refreshParcours(f));

        root.setPadding(new Insets(12));
        root.setCenter(content());
        root.setBottom(statusBar());
    }

    /**
     * <p>Construit le contenu principal de la vue</p>
     *
     * <p>Organisation :</p>
     * <ul>
     *   <li>à gauche : liste des formations et actions associées</li>
     *   <li>à droite : liste des parcours filtrés et actions associées</li>
     * </ul>
     *
     * @return un conteneur JavaFX à afficher au centre
     */
    private Parent content() {
        Button addF = new Button("Ajouter formation");
        Button delF = new Button("Supprimer formation");
        addF.setMaxWidth(Double.MAX_VALUE);
        delF.setMaxWidth(Double.MAX_VALUE);

        addF.setOnAction(e -> {
            TextInputDialog d = new TextInputDialog();
            d.setTitle("Nouvelle formation");
            d.setHeaderText(null);
            d.setContentText("Nom formation :");
            d.showAndWait().map(String::trim).filter(s -> !s.isEmpty()).ifPresent(n -> {
                state.getFormations().add(new Formation(n));
                lbl.setText("Formation ajoutée");
            });
        });

        delF.setOnAction(e -> {
            Formation f = lvFormations.getSelectionModel().getSelectedItem();
            if (f == null) { lbl.setText("Sélectionner une formation"); return; }

            state.getParcours().removeIf(p -> p.getFormation() == f);
            state.getFormations().remove(f);

            lvParcours.setItems(FXCollections.observableArrayList());
            lbl.setText("Formation supprimée");
        });

        Button addP = new Button("Ajouter parcours");
        Button delP = new Button("Supprimer parcours");
        addP.setMaxWidth(Double.MAX_VALUE);
        delP.setMaxWidth(Double.MAX_VALUE);

        addP.setOnAction(e -> {
            Formation f = lvFormations.getSelectionModel().getSelectedItem();
            if (f == null) { lbl.setText("Sélectionner une formation"); return; }

            TextInputDialog d = new TextInputDialog();
            d.setTitle("Nouveau parcours");
            d.setHeaderText(null);
            d.setContentText("Nom parcours :");
            d.showAndWait().map(String::trim).filter(s -> !s.isEmpty()).ifPresent(n -> {
                state.getParcours().add(new Parcours(n, f));
                refreshParcours(f);
                lbl.setText("Parcours ajouté");
            });
        });

        delP.setOnAction(e -> {
            Parcours p = lvParcours.getSelectionModel().getSelectedItem();
            Formation f = lvFormations.getSelectionModel().getSelectedItem();
            if (p == null) { lbl.setText("Sélectionner un parcours"); return; }

            state.getParcours().remove(p);
            refreshParcours(f);
            lbl.setText("Parcours supprimé");
        });

        VBox left = new VBox(10, new Label("Formations"), lvFormations, addF, delF);
        VBox right = new VBox(10, new Label("Parcours"), lvParcours, addP, delP);

        left.setPadding(new Insets(10));
        right.setPadding(new Insets(10));
        VBox.setVgrow(lvFormations, Priority.ALWAYS);
        VBox.setVgrow(lvParcours, Priority.ALWAYS);

        SplitPane sp = new SplitPane(left, right);
        sp.setDividerPositions(0.5);
        return sp;
    }

    /**
     * <p>Rafraîchit la liste des parcours selon la formation sélectionnée</p>
     *
     * <p>Si aucune formation n'est sélectionnée, la liste de parcours est vidée</p>
     *
     * @param f formation sélectionnée ou null
     */
    private void refreshParcours(Formation f) {
        if (f == null) {
            lvParcours.setItems(FXCollections.observableArrayList());
            lvParcours.getSelectionModel().clearSelection();
            return;
        }
        var filtered = FXCollections.<Parcours>observableArrayList();
        for (Parcours p : state.getParcours()) {
            if (p.getFormation() == f) filtered.add(p);
        }
        lvParcours.setItems(filtered);
        lvParcours.getSelectionModel().clearSelection();
    }

    /**
     * <p>Construit la barre de statut de la vue</p>
     *
     * @return un conteneur JavaFX affichant le message courant
     */
    private Parent statusBar() {
        HBox b = new HBox(lbl);
        b.setPadding(new Insets(8,10,0,10));
        return b;
    }

    /**
     * <p>Retourne le noeud racine de la vue</p>
     *
     * @return le {@link Parent} racine
     */
    public Parent getRoot() { return root; }
}
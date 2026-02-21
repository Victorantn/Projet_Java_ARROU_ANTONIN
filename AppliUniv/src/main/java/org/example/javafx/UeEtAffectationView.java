package org.example.javafx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import org.example.Formation;
import org.example.Parcours;
import org.example.Ue;

/**
 * <p>Vue JavaFX dédiée à la gestion des UE et à leur affectation</p>
 *
 * <p>Cette vue est organisée en deux onglets :</p>
 * <ul>
 *   <li>onglet UE : création, suppression et gestion des prérequis</li>
 *   <li>onglet Affectation UE : association d'UE aux formations ou aux parcours</li>
 * </ul>
 *
 * <p>Règles appliquées :</p>
 * <ul>
 *   <li>les UE d'ouverture sont accessibles à tous et ne doivent pas être affectées en base ou en spé</li>
 *   <li>la liste des parcours proposée dépend de la formation sélectionnée</li>
 *   <li>les listes affichées dans les tables proviennent de {@link AppState}</li>
 * </ul>
 */
public class UeEtAffectationView {

    /** Conteneur racine de la vue */
    private final BorderPane root = new BorderPane();

    /** Etat partagé de l'application */
    private final AppState state;

    /** Zone de messages pour l'utilisateur */
    private final Label lbl = new Label("Prêt");

    /** Table du catalogue global des UE */
    private final TableView<Ue> tvUe = new TableView<>();

    /** Sélecteur de formation utilisé dans l'onglet affectation */
    private final ComboBox<Formation> cbFormation = new ComboBox<>();

    /** Sélecteur de parcours utilisé dans l'onglet affectation */
    private final ComboBox<Parcours> cbParcours = new ComboBox<>();

    /** Groupe de boutons radio permettant de choisir base ou spé */
    private final ToggleGroup tg = new ToggleGroup();

    /** Bouton radio pour l'affectation en base (formation) */
    private final RadioButton rbBase = new RadioButton("BASE (formation)");

    /** Bouton radio pour l'affectation en spé (parcours) */
    private final RadioButton rbSpe = new RadioButton("SPÉ (parcours)");

    /** Sélecteur d'UE non ouverture à affecter */
    private final ComboBox<Ue> cbUeNonOuverture = new ComboBox<>();

    /** Table des UE affectées en base ou en spé selon le mode sélectionné */
    private final TableView<Ue> tvAffectees = new TableView<>();

    /**
     * <p>Construit la vue UE / Affectation UE</p>
     *
     * @param state état partagé de l'application
     */
    public UeEtAffectationView(AppState state) {
        this.state = state;

        root.setPadding(new Insets(12));
        TabPane tabs = new TabPane(
                new Tab("UE", uePane()),
                new Tab("Affectation UE", affectPane())
        );
        tabs.getTabs().forEach(t -> t.setClosable(false));

        root.setCenter(tabs);
        root.setBottom(statusBar());

        refreshUeNonOuverture();
    }

    /**
     * <p>Construit l'onglet de gestion du catalogue global des UE</p>
     *
     * <p>Actions proposées :</p>
     * <ul>
     *   <li>créer une UE via {@link UeDial}</li>
     *   <li>gérer les prérequis via {@link PrereqDial}</li>
     *   <li>supprimer une UE et la retirer des affectations base et spé</li>
     * </ul>
     *
     * @return un conteneur JavaFX pour l'onglet UE
     */
    private Parent uePane() {
        buildUeTable();

        Button btnCreate = new Button("Créer UE");
        Button btnPrereq = new Button("Gérer prérequis");
        Button btnDelete = new Button("Supprimer UE");

        btnCreate.setOnAction(e -> {
            Ue u = UeDial.ask();
            if (u == null) return;
            state.getUes().add(u);
            tvUe.refresh();
            refreshUeNonOuverture();
            lbl.setText("UE créée");
        });

        btnPrereq.setOnAction(e -> {
            Ue u = tvUe.getSelectionModel().getSelectedItem();
            if (u == null) { lbl.setText("Sélectionner une UE"); return; }

            var pool = FXCollections.observableArrayList(state.getUes());
            pool.removeIf(x -> x.getCodeUe() == u.getCodeUe());
            if (pool.isEmpty()) { lbl.setText("Aucune UE dispo en prérequis"); return; }

            PrereqDial.show(u, pool);
            tvUe.refresh();
            lbl.setText("Prérequis mis à jour");
        });

        btnDelete.setOnAction(e -> {
            Ue u = tvUe.getSelectionModel().getSelectedItem();
            if (u == null) { lbl.setText("Sélectionner une UE"); return; }

            for (Formation f : state.getFormations()) f.getUeBase().removeIf(x -> x.getCodeUe() == u.getCodeUe());
            for (Parcours p : state.getParcours()) p.getUeSpe().removeIf(x -> x.getCodeUe() == u.getCodeUe());

            state.getUes().remove(u);
            tvUe.refresh();
            refreshUeNonOuverture();
            refreshAffectees();
            lbl.setText("UE supprimée");
        });

        HBox actions = new HBox(10, btnCreate, btnPrereq, btnDelete);

        VBox v = new VBox(10,
                new Label("Catalogue global des UE"),
                tvUe,
                actions
        );
        VBox.setVgrow(tvUe, Priority.ALWAYS);
        v.setPadding(new Insets(10));
        return v;
    }

    /**
     * <p>Configure la table du catalogue global des UE</p>
     *
     * <p>Colonnes affichées :</p>
     * <ul>
     *   <li>code</li>
     *   <li>nom</li>
     *   <li>ECTS</li>
     *   <li>type (ouverture ou normale)</li>
     *   <li>nombre de prérequis</li>
     * </ul>
     */
    private void buildUeTable() {
        TableColumn<Ue, Number> cCode = new TableColumn<>("Code");
        cCode.setCellValueFactory(v -> new SimpleIntegerProperty(v.getValue().getCodeUe()));
        cCode.setMaxWidth(80);

        TableColumn<Ue, String> cNom = new TableColumn<>("Nom");
        cNom.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getNomUe()));

        TableColumn<Ue, Number> cEcts = new TableColumn<>("ECTS");
        cEcts.setCellValueFactory(v -> new SimpleIntegerProperty(v.getValue().getEcts()));
        cEcts.setMaxWidth(80);

        TableColumn<Ue, String> cType = new TableColumn<>("Type");
        cType.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().isOuverture() ? "Ouverture" : "Normale"));
        cType.setMaxWidth(120);

        TableColumn<Ue, Number> cPre = new TableColumn<>("Préreq.");
        cPre.setCellValueFactory(v -> new SimpleIntegerProperty(v.getValue().getUePreRequis().size()));
        cPre.setMaxWidth(80);

        tvUe.getColumns().setAll(cCode, cNom, cEcts, cType, cPre);
        tvUe.setItems(state.getUes());
        tvUe.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

    /**
     * <p>Construit l'onglet d'affectation des UE</p>
     *
     * <p>Deux modes sont possibles :</p>
     * <ul>
     *   <li>BASE : affectation à la formation sélectionnée</li>
     *   <li>SPÉ : affectation au parcours sélectionné</li>
     * </ul>
     *
     * <p>La ComboBox de parcours est filtrée selon la formation choisie</p>
     *
     * @return un conteneur JavaFX pour l'onglet affectation
     */
    private Parent affectPane() {
        rbBase.setToggleGroup(tg);
        rbSpe.setToggleGroup(tg);
        rbBase.setSelected(true);

        cbFormation.setItems(state.getFormations());
        cbFormation.setPromptText("Formation");
        cbParcours.setPromptText("Parcours (selon formation)");

        cbFormation.setConverter(new StringConverter<>() {
            @Override public String toString(Formation f) { return f == null ? "" : f.getNomFormation(); }
            @Override public Formation fromString(String s) { return null; }
        });

        cbParcours.setConverter(new StringConverter<>() {
            @Override public String toString(Parcours p) { return p == null ? "" : p.getNomParcours(); }
            @Override public Parcours fromString(String s) { return null; }
        });

        cbUeNonOuverture.setPromptText("UE (non ouverture)");
        cbUeNonOuverture.setConverter(new StringConverter<>() {
            @Override public String toString(Ue u) { return u == null ? "" : "#" + u.getCodeUe() + " - " + u.getNomUe(); }
            @Override public Ue fromString(String s) { return null; }
        });

        cbFormation.valueProperty().addListener((o,a,f) -> {
            refreshParcoursForFormation(f);
            refreshAffectees();
        });
        cbParcours.valueProperty().addListener((o,a,p) -> refreshAffectees());
        tg.selectedToggleProperty().addListener((o,a,t) -> refreshAffectees());

        buildAffecteesTable();

        Button btnAffecter = new Button("Affecter");
        Button btnRetirer = new Button("Retirer sélection");
        btnAffecter.setOnAction(e -> affecter());
        btnRetirer.setOnAction(e -> retirer());

        HBox top1 = new HBox(10, cbFormation, cbParcours);
        HBox top2 = new HBox(10, rbBase, rbSpe);
        HBox top3 = new HBox(10, cbUeNonOuverture, btnAffecter, btnRetirer);
        HBox.setHgrow(cbUeNonOuverture, Priority.ALWAYS);

        VBox v = new VBox(10,
                new Label("Affectation UE (BASE / SPÉ)"),
                top1,
                top2,
                tvAffectees,
                top3,
                new Label("Ue d'ouverture = accessible pour tous")
        );
        VBox.setVgrow(tvAffectees, Priority.ALWAYS);
        v.setPadding(new Insets(10));

        refreshAffectees();
        return v;
    }

    /**
     * <p>Configure la table des UE affectées</p>
     *
     * <p>La table affiche soit les UE de base d'une formation, soit les UE spé d'un parcours</p>
     */
    private void buildAffecteesTable() {
        TableColumn<Ue, Number> cCode = new TableColumn<>("Code");
        cCode.setCellValueFactory(v -> new SimpleIntegerProperty(v.getValue().getCodeUe()));
        cCode.setMaxWidth(80);

        TableColumn<Ue, String> cNom = new TableColumn<>("Nom");
        cNom.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getNomUe()));

        TableColumn<Ue, Number> cEcts = new TableColumn<>("ECTS");
        cEcts.setCellValueFactory(v -> new SimpleIntegerProperty(v.getValue().getEcts()));
        cEcts.setMaxWidth(80);

        tvAffectees.getColumns().setAll(cCode, cNom, cEcts);
        tvAffectees.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

    /**
     * <p>Rafraîchit la liste des UE non ouverture proposées à l'affectation</p>
     *
     * <p>Les UE d'ouverture sont exclues car elles ne doivent pas être affectées en base ou en spé</p>
     */
    private void refreshUeNonOuverture() {
        var list = FXCollections.<Ue>observableArrayList();
        for (Ue u : state.getUes()) if (!u.isOuverture()) list.add(u);
        cbUeNonOuverture.setItems(list);
        cbUeNonOuverture.setValue(null);
    }

    /**
     * <p>Rafraîchit la liste des parcours selon la formation sélectionnée</p>
     *
     * @param f formation sélectionnée ou null
     */
    private void refreshParcoursForFormation(Formation f) {
        if (f == null) {
            cbParcours.setItems(FXCollections.observableArrayList());
            cbParcours.setValue(null);
            return;
        }
        var filtered = FXCollections.<Parcours>observableArrayList();
        for (Parcours p : state.getParcours()) if (p.getFormation() == f) filtered.add(p);
        cbParcours.setItems(filtered);
        cbParcours.setValue(null);
    }

    /**
     * <p>Rafraîchit la table des UE affectées</p>
     *
     * <p>Le contenu dépend du mode sélectionné :</p>
     * <ul>
     *   <li>BASE : affiche les UE de base de la formation</li>
     *   <li>SPÉ : affiche les UE spé du parcours</li>
     * </ul>
     */
    private void refreshAffectees() {
        boolean base = tg.getSelectedToggle() == rbBase;
        Formation f = cbFormation.getValue();
        Parcours p = cbParcours.getValue();

        if (base) {
            tvAffectees.setItems(f == null ? FXCollections.observableArrayList()
                    : FXCollections.observableArrayList(f.getUeBase()));
        } else {
            tvAffectees.setItems(p == null ? FXCollections.observableArrayList()
                    : FXCollections.observableArrayList(p.getUeSpe()));
        }
        tvAffectees.refresh();
    }

    /**
     * <p>Affecte l'UE sélectionnée en base ou en spé</p>
     *
     * <p>Le mode BASE requiert une formation</p>
     * <p>Le mode SPÉ requiert un parcours</p>
     *
     * <p>Une UE d'ouverture ne peut pas être affectée</p>
     */
    private void affecter() {
        Ue u = cbUeNonOuverture.getValue();
        if (u == null) { lbl.setText("Choisir une UE"); return; }
        if (u.isOuverture()) { lbl.setText("UE d'ouverture non affectable"); return; }

        boolean base = tg.getSelectedToggle() == rbBase;
        Formation f = cbFormation.getValue();
        Parcours p = cbParcours.getValue();

        if (base) {
            if (f == null) { lbl.setText("Choisir une formation"); return; }
            if (!containsByCode(f.getUeBase(), u)) f.getUeBase().add(u);
        } else {
            if (p == null) { lbl.setText("Choisir un parcours"); return; }
            if (!containsByCode(p.getUeSpe(), u)) p.getUeSpe().add(u);
        }

        refreshAffectees();
        lbl.setText("UE affectée");
    }

    /**
     * <p>Retire l'UE sélectionnée de la liste des UE affectées</p>
     *
     * <p>Le retrait se fait de la formation (mode BASE) ou du parcours (mode SPÉ)</p>
     */
    private void retirer() {
        Ue u = tvAffectees.getSelectionModel().getSelectedItem();
        if (u == null) { lbl.setText("Sélectionner une UE affectée"); return; }

        boolean base = tg.getSelectedToggle() == rbBase;
        Formation f = cbFormation.getValue();
        Parcours p = cbParcours.getValue();

        if (base) {
            if (f == null) { lbl.setText("Choisir une formation"); return; }
            f.getUeBase().removeIf(x -> x.getCodeUe() == u.getCodeUe());
        } else {
            if (p == null) { lbl.setText("Choisir un parcours"); return; }
            p.getUeSpe().removeIf(x -> x.getCodeUe() == u.getCodeUe());
        }

        refreshAffectees();
        lbl.setText("UE retirée");
    }

    /**
     * <p>Vérifie si une liste contient déjà une UE selon son code</p>
     *
     * @param list liste à parcourir
     * @param u UE à rechercher
     * @return true si une UE de même code est présente sinon false
     */
    private boolean containsByCode(java.util.List<Ue> list, Ue u) {
        for (Ue x : list) if (x.getCodeUe() == u.getCodeUe()) return true;
        return false;
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
package org.example.javafx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import org.example.*;

import java.util.LinkedHashMap;

/**
 * <p>Vue JavaFX dédiée à la gestion des étudiants</p>
 *
 * <p>Fonctionnalités principales :</p>
 * <ul>
 *   <li>création et suppression d'un étudiant</li>
 *   <li>affichage de la liste des étudiants</li>
 *   <li>affichage des inscriptions UE de l'étudiant sélectionné</li>
 *   <li>inscription à une UE si les prérequis sont validés</li>
 *   <li>modification du statut d'une inscription (ENCOURS, VALIDE, ECHOUE)</li>
 *   <li>vérification de l'obtention du diplôme</li>
 * </ul>
 *
 * <p>Règles appliquées :</p>
 * <ul>
 *   <li>une UE est disponible si elle appartient aux UE obligatoires (base + spé) de l'étudiant
 *       ou si c'est une UE d'ouverture du catalogue global</li>
 *   <li>une UE n'est proposée à l'inscription que si tous ses prérequis sont validés</li>
 *   <li>le diplôme est validé si l'étudiant a au moins 180 ECTS validés
 *       et si toutes les UE obligatoires (base + spé) sont validées</li>
 *   <li>les UE d'ouverture peuvent compter dans les 180 ECTS mais ne sont pas obligatoires</li>
 * </ul>
 */
public class EtudiantsView {

    /** Conteneur racine de la vue */
    private final BorderPane root = new BorderPane();

    /** Tableau des étudiants */
    private final TableView<Etudiant> tvEtudiants = new TableView<>();

    /** Tableau des inscriptions UE de l'étudiant sélectionné */
    private final TableView<InscriptionUe> tvInscriptions = new TableView<>();

    /** Champ de saisie du nom étudiant */
    private final TextField tfNom = new TextField();

    /** Champ de saisie du prénom étudiant */
    private final TextField tfPrenom = new TextField();

    /** Sélecteur de formation pour la création étudiant */
    private final ComboBox<Formation> cbFormation = new ComboBox<>();

    /** Sélecteur de parcours pour la création étudiant */
    private final ComboBox<Parcours> cbParcours = new ComboBox<>();

    /** Champ de saisie de l'année universitaire lors d'une inscription */
    private final TextField tfAnnee = new TextField("2025 / 2026");

    /** Sélecteur du semestre lors d'une inscription */
    private final ComboBox<Semestre> cbSemestre = new ComboBox<>();

    /** Sélecteur de l'UE à inscrire pour l'étudiant sélectionné */
    private final ComboBox<Ue> cbUe = new ComboBox<>();

    /** Zone de messages pour l'utilisateur */
    private final Label lbl = new Label("Prêt");

    /** Etat partagé de l'application */
    private final AppState state;

    /**
     * <p>Construit la vue et initialise les composants</p>
     *
     * @param state état partagé de l'application contenant les listes observables
     */
    public EtudiantsView(AppState state) {
        this.state = state;

        cbFormation.setItems(state.getFormations());
        cbSemestre.setItems(FXCollections.observableArrayList(Semestre.values()));
        cbSemestre.setValue(Semestre.IMPAIR);

        cbFormation.setPromptText("Formation");
        cbParcours.setPromptText("Parcours");
        cbUe.setPromptText("UE dispo");

        cbFormation.setConverter(new StringConverter<>() {
            @Override public String toString(Formation f) { return f == null ? "" : f.getNomFormation(); }
            @Override public Formation fromString(String s) { return null; }
        });

        cbParcours.setConverter(new StringConverter<>() {
            @Override public String toString(Parcours p) { return p == null ? "" : p.getNomParcours(); }
            @Override public Parcours fromString(String s) { return null; }
        });

        cbUe.setConverter(new StringConverter<>() {
            @Override public String toString(Ue u) {
                if (u == null) return "";
                String tag = u.isOuverture() ? " (ouverture)" : "";
                return "#" + u.getCodeUe() + " - " + u.getNomUe() + tag;
            }
            @Override public Ue fromString(String s) { return null; }
        });

        cbFormation.valueProperty().addListener((o,a,f) -> {
            cbParcours.setItems(FXCollections.observableArrayList());
            cbParcours.setValue(null);
            if (f == null) return;

            var list = FXCollections.<Parcours>observableArrayList();
            for (Parcours p : state.getParcours()) {
                if (p.getFormation() == f) list.add(p);
            }
            cbParcours.setItems(list);
        });

        tvEtudiants.getSelectionModel().selectedItemProperty().addListener((o,a,sel) -> {
            refreshInscriptions(sel);
            refreshUeCombo(sel);
        });

        buildEtudiantsTable();
        buildInscriptionsTable();

        root.setPadding(new Insets(12));
        root.setLeft(leftPane());
        root.setCenter(centerPane());
        root.setBottom(statusBar());
    }

    /**
     * <p>Construit le panneau gauche de la vue</p>
     *
     * <p>Il contient :</p>
     * <ul>
     *   <li>le formulaire de création d'un étudiant</li>
     *   <li>les actions de suppression d'étudiant</li>
     *   <li>le formulaire d'inscription à une UE</li>
     * </ul>
     *
     * @return le panneau JavaFX à afficher à gauche
     */
    private Parent leftPane() {
        tfNom.setPromptText("Nom");
        tfPrenom.setPromptText("Prénom");

        Button btnAddEtu = new Button("Ajouter étudiant");
        btnAddEtu.setMaxWidth(Double.MAX_VALUE);
        btnAddEtu.setOnAction(e -> {
            String nom = tfNom.getText().trim();
            String prenom = tfPrenom.getText().trim();
            Formation f = cbFormation.getValue();
            Parcours p = cbParcours.getValue();

            if (nom.isEmpty() || prenom.isEmpty() || f == null || p == null) {
                lbl.setText("Nom, prénom, formation et parcours obligatoires");
                return;
            }

            state.getEtudiants().add(new Etudiant(nom, prenom, f, p));
            tfNom.clear();
            tfPrenom.clear();
            cbFormation.setValue(null);
            cbParcours.setValue(null);
            lbl.setText("Étudiant ajouté");
        });

        Button btnSupprEtu = new Button("Supprimer étudiant");
        btnSupprEtu.setMaxWidth(Double.MAX_VALUE);
        btnSupprEtu.setOnAction(e -> {
            Etudiant sel = tvEtudiants.getSelectionModel().getSelectedItem();
            if (sel == null) { lbl.setText("Sélectionner un étudiant"); return; }
            state.getEtudiants().remove(sel);
            tvInscriptions.setItems(FXCollections.observableArrayList());
            cbUe.setItems(FXCollections.observableArrayList());
            cbUe.setValue(null);
            lbl.setText("Étudiant supprimé");
        });

        Separator sep = new Separator();

        Button btnInscrire = new Button("Inscrire à l'UE");
        btnInscrire.setMaxWidth(Double.MAX_VALUE);
        btnInscrire.setOnAction(e -> {
            Etudiant sel = tvEtudiants.getSelectionModel().getSelectedItem();
            Ue ue = cbUe.getValue();

            if (sel == null) { lbl.setText("Sélectionner un étudiant"); return; }
            if (ue == null) { lbl.setText("Sélectionner une UE"); return; }

            String annee = tfAnnee.getText().trim();
            Semestre sem = cbSemestre.getValue();
            if (annee.isEmpty() || sem == null) { lbl.setText("Année et semestre obligatoires"); return; }

            if (!prerequisOk(sel, ue)) {
                lbl.setText("Prérequis pas validés");
                return;
            }

            boolean deja = sel.getInscriptions().stream().anyMatch(i ->
                    i.getUe().getCodeUe() == ue.getCodeUe() &&
                            i.getAnneeUniversitaire().equals(annee) &&
                            i.getSemestre() == sem
            );
            if (deja) { lbl.setText("Déjà inscrit à cette UE pour ce semestre"); return; }

            sel.ajouterInscription(new InscriptionUe(ue, annee, sem));
            refreshInscriptions(sel);
            refreshUeCombo(sel);
            lbl.setText("Inscription ajoutée");
        });

        VBox box = new VBox(10,
                new Label("Créer étudiant"),
                tfNom, tfPrenom, cbFormation, cbParcours,
                btnAddEtu, btnSupprEtu,
                sep,
                new Label("Inscrire l'étudiant sélectionné"),
                tfAnnee, cbSemestre, cbUe, btnInscrire
        );
        box.setPadding(new Insets(10));
        box.setPrefWidth(350);
        return box;
    }

    /**
     * <p>Construit le panneau central de la vue</p>
     *
     * <p>Il contient :</p>
     * <ul>
     *   <li>la table des étudiants</li>
     *   <li>la table des inscriptions UE de l'étudiant sélectionné</li>
     * </ul>
     *
     * @return le panneau JavaFX à afficher au centre
     */
    private Parent centerPane() {
        VBox v = new VBox(10,
                new Label("Liste des étudiants"),
                tvEtudiants,
                new Separator(),
                buildInscriptionsHeader(),
                tvInscriptions
        );
        VBox.setVgrow(tvEtudiants, Priority.ALWAYS);
        VBox.setVgrow(tvInscriptions, Priority.ALWAYS);
        return v;
    }

    /**
     * <p>Construit l'en-tête des actions liées aux inscriptions</p>
     *
     * <p>Les actions agissent sur l'inscription sélectionnée :</p>
     * <ul>
     *   <li>modification du statut</li>
     *   <li>suppression de l'inscription</li>
     *   <li>vérification du diplôme</li>
     * </ul>
     *
     * @return une barre d'actions pour la zone inscriptions
     */
    private Parent buildInscriptionsHeader() {
        Button btnEnCours = new Button("Mettre ENCOURS");
        Button btnValider = new Button("Mettre VALIDE");
        Button btnEchouer = new Button("Mettre ECHOUE");
        Button btnSuppr = new Button("Supprimer inscription");
        Button btnDiplome = new Button("Valider diplôme");

        btnEnCours.setOnAction(e -> setStatutSelection(Statut.ENCOURS));
        btnValider.setOnAction(e -> setStatutSelection(Statut.VALIDE));
        btnEchouer.setOnAction(e -> setStatutSelection(Statut.ECHOUE));
        btnSuppr.setOnAction(e -> supprimerInscriptionSelection());
        btnDiplome.setOnAction(e -> verifierDiplome());

        HBox h = new HBox(10,
                new Label("UE de l'étudiant sélectionné"),
                new Region(),
                btnEnCours, btnValider, btnEchouer,
                btnSuppr,
                btnDiplome
        );
        HBox.setHgrow(h.getChildren().get(1), Priority.ALWAYS);
        return h;
    }

    /**
     * <p>Configure la table des étudiants</p>
     *
     * <p>Colonnes affichées :</p>
     * <ul>
     *   <li>numéro étudiant</li>
     *   <li>nom et prénom</li>
     *   <li>formation et parcours</li>
     *   <li>nombre d'inscriptions</li>
     * </ul>
     */
    private void buildEtudiantsTable() {
        tvEtudiants.setItems(state.getEtudiants());
        tvEtudiants.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<Etudiant, Number> cNum = new TableColumn<>("Num");
        cNum.setCellValueFactory(v -> new SimpleIntegerProperty(v.getValue().getNumeroEtudiant()));
        cNum.setMaxWidth(140);

        TableColumn<Etudiant, String> cNom = new TableColumn<>("Nom");
        cNom.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getNomE()));

        TableColumn<Etudiant, String> cPrenom = new TableColumn<>("Prénom");
        cPrenom.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getPrenomE()));

        TableColumn<Etudiant, String> cForm = new TableColumn<>("Formation");
        cForm.setCellValueFactory(v -> new SimpleStringProperty(
                v.getValue().getFormation() == null ? "" : v.getValue().getFormation().getNomFormation()
        ));

        TableColumn<Etudiant, String> cParc = new TableColumn<>("Parcours");
        cParc.setCellValueFactory(v -> new SimpleStringProperty(
                v.getValue().getParcours() == null ? "" : v.getValue().getParcours().getNomParcours()
        ));

        TableColumn<Etudiant, Number> cIns = new TableColumn<>("Inscriptions");
        cIns.setCellValueFactory(v -> new SimpleIntegerProperty(v.getValue().getInscriptions().size()));
        cIns.setMaxWidth(140);

        tvEtudiants.getColumns().setAll(cNum, cNom, cPrenom, cForm, cParc, cIns);
    }

    /**
     * <p>Configure la table des inscriptions UE</p>
     *
     * <p>Chaque ligne représente une {@link InscriptionUe} de l'étudiant sélectionné</p>
     *
     * <p>Colonnes affichées :</p>
     * <ul>
     *   <li>code inscription</li>
     *   <li>UE + marqueur ouverture</li>
     *   <li>ECTS</li>
     *   <li>année universitaire</li>
     *   <li>semestre</li>
     *   <li>statut</li>
     * </ul>
     */
    private void buildInscriptionsTable() {
        tvInscriptions.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<InscriptionUe, Number> cCodeIns = new TableColumn<>("CodeIns");
        cCodeIns.setCellValueFactory(v -> new SimpleIntegerProperty(v.getValue().getCodeInscrip()));
        cCodeIns.setMaxWidth(90);

        TableColumn<InscriptionUe, String> cUe = new TableColumn<>("UE");
        cUe.setCellValueFactory(v -> new SimpleStringProperty(
                "#" + v.getValue().getUe().getCodeUe() + " - " + v.getValue().getUe().getNomUe() +
                        (v.getValue().getUe().isOuverture() ? " (ouverture)" : "")
        ));

        TableColumn<InscriptionUe, Number> cEcts = new TableColumn<>("ECTS");
        cEcts.setCellValueFactory(v -> new SimpleIntegerProperty(v.getValue().getUe().getEcts()));
        cEcts.setMaxWidth(80);

        TableColumn<InscriptionUe, String> cAnnee = new TableColumn<>("Année");
        cAnnee.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getAnneeUniversitaire()));
        cAnnee.setMaxWidth(120);

        TableColumn<InscriptionUe, String> cSem = new TableColumn<>("Semestre");
        cSem.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getSemestre().name()));
        cSem.setMaxWidth(110);

        TableColumn<InscriptionUe, String> cStatut = new TableColumn<>("Statut");
        cStatut.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getStatut().name()));
        cStatut.setMaxWidth(120);

        tvInscriptions.getColumns().setAll(cCodeIns, cUe, cEcts, cAnnee, cSem, cStatut);
    }

    /**
     * <p>Rafraîchit la table des inscriptions en fonction de l'étudiant sélectionné</p>
     *
     * @param sel étudiant sélectionné ou null
     */
    private void refreshInscriptions(Etudiant sel) {
        if (sel == null) {
            tvInscriptions.setItems(FXCollections.observableArrayList());
            tvEtudiants.refresh();
            return;
        }
        tvInscriptions.setItems(FXCollections.observableArrayList(sel.getInscriptions()));
        tvEtudiants.refresh();
    }

    /**
     * <p>Rafraîchit la liste des UE proposées dans la ComboBox</p>
     *
     * <p>Les UE proposées sont :</p>
     * <ul>
     *   <li>UE base + UE spé de l'étudiant</li>
     *   <li>toutes les UE d'ouverture du catalogue global</li>
     * </ul>
     *
     * <p>Le résultat est dédupliqué par code UE et filtré par prérequis</p>
     *
     * @param sel étudiant sélectionné ou null
     */
    private void refreshUeCombo(Etudiant sel) {
        if (sel == null) {
            cbUe.setItems(FXCollections.observableArrayList());
            cbUe.setValue(null);
            return;
        }

        LinkedHashMap<Integer, Ue> unique = new LinkedHashMap<>();

        if (sel.getFormation() != null) {
            for (Ue u : sel.getFormation().getUeBase()) unique.putIfAbsent(u.getCodeUe(), u);
        }
        if (sel.getParcours() != null) {
            for (Ue u : sel.getParcours().getUeSpe()) unique.putIfAbsent(u.getCodeUe(), u);
        }

        for (Ue u : state.getUes()) {
            if (u.isOuverture()) unique.putIfAbsent(u.getCodeUe(), u);
        }

        var eligible = FXCollections.<Ue>observableArrayList();
        for (Ue u : unique.values()) {
            if (prerequisOk(sel, u)) eligible.add(u);
        }

        cbUe.setItems(eligible);
        cbUe.setValue(null);
    }

    /**
     * <p>Indique si tous les prérequis d'une UE sont validés par un étudiant</p>
     *
     * @param e étudiant concerné
     * @param ue UE pour laquelle on vérifie les prérequis
     * @return true si tous les prérequis sont validés sinon false
     */
    private boolean prerequisOk(Etudiant e, Ue ue) {
        for (Ue pre : ue.getUePreRequis()) {
            if (!estValidee(e, pre)) return false;
        }
        return true;
    }

    /**
     * <p>Indique si une UE est validée par un étudiant</p>
     *
     * <p>Une UE est considérée validée si l'étudiant possède au moins une inscription
     * à cette UE avec le statut {@link Statut#VALIDE}</p>
     *
     * @param e étudiant concerné
     * @param ue UE à vérifier
     * @return true si l'UE est validée sinon false
     */
    private boolean estValidee(Etudiant e, Ue ue) {
        int code = ue.getCodeUe();
        for (InscriptionUe ins : e.getInscriptions()) {
            if (ins.getUe().getCodeUe() == code && ins.getStatut() == Statut.VALIDE) return true;
        }
        return false;
    }

    /**
     * <p>Met à jour le statut de l'inscription sélectionnée</p>
     *
     * <p>Nécessite que l'étudiant et l'inscription soient sélectionnés</p>
     *
     * <p>Attention : cette méthode suppose l'existence de InscriptionUe#setStatut</p>
     *
     * @param s nouveau statut à appliquer
     */
    private void setStatutSelection(Statut s) {
        Etudiant etu = tvEtudiants.getSelectionModel().getSelectedItem();
        InscriptionUe ins = tvInscriptions.getSelectionModel().getSelectedItem();
        if (etu == null || ins == null) { lbl.setText("Sélectionner étudiant et inscription"); return; }

        ins.setStatut(s);

        refreshInscriptions(etu);
        refreshUeCombo(etu);
        tvInscriptions.refresh();

        lbl.setText("Statut mis à jour: " + s.name());
    }

    /**
     * <p>Supprime l'inscription sélectionnée de l'étudiant sélectionné</p>
     */
    private void supprimerInscriptionSelection() {
        Etudiant etu = tvEtudiants.getSelectionModel().getSelectedItem();
        InscriptionUe ins = tvInscriptions.getSelectionModel().getSelectedItem();
        if (etu == null || ins == null) { lbl.setText("Sélectionner étudiant et inscription"); return; }

        etu.supprimerInscription(ins);
        refreshInscriptions(etu);
        refreshUeCombo(etu);
        lbl.setText("Inscription supprimée");
    }

    /**
     * <p>Calcule le total d'ECTS validés d'un étudiant</p>
     *
     * <p>Les UE d'ouverture peuvent être comptées dans ce total</p>
     *
     * @param e étudiant concerné
     * @return total des ECTS validés
     */
    private int ectsValidesTotal(Etudiant e) {
        int total = 0;
        for (InscriptionUe ins : e.getInscriptions()) {
            if (ins.getStatut() == Statut.VALIDE) total += ins.getUe().getEcts();
        }
        return total;
    }

    /**
     * <p>Vérifie si toutes les UE obligatoires sont validées par l'étudiant</p>
     *
     * <p>Les UE obligatoires sont l'union des UE de base de la formation et des UE spé du parcours</p>
     *
     * <p>Les UE d'ouverture ne font pas partie des UE obligatoires</p>
     *
     * @param e étudiant concerné
     * @return true si toutes les UE obligatoires sont validées sinon false
     */
    private boolean toutesUeObligatoiresValidees(Etudiant e) {
        LinkedHashMap<Integer, Ue> obligatoires = new LinkedHashMap<>();

        if (e.getFormation() != null) {
            for (Ue u : e.getFormation().getUeBase()) obligatoires.putIfAbsent(u.getCodeUe(), u);
        }
        if (e.getParcours() != null) {
            for (Ue u : e.getParcours().getUeSpe()) obligatoires.putIfAbsent(u.getCodeUe(), u);
        }

        for (Ue u : obligatoires.values()) {
            if (!estValidee(e, u)) return false;
        }
        return true;
    }

    /**
     * <p>Vérifie si l'étudiant sélectionné peut valider son diplôme</p>
     *
     * <p>Conditions :</p>
     * <ul>
     *   <li>au moins 180 ECTS validés</li>
     *   <li>toutes les UE obligatoires (base + spé) validées</li>
     * </ul>
     */
    private void verifierDiplome() {
        Etudiant etu = tvEtudiants.getSelectionModel().getSelectedItem();
        if (etu == null) { lbl.setText("Sélectionner un étudiant"); return; }

        int ects = ectsValidesTotal(etu);
        boolean okOblig = toutesUeObligatoiresValidees(etu);

        if (ects >= 180 && okOblig) {
            lbl.setText("Diplôme VALIDE (" + ects + " ECTS, UE obligatoires OK)");
        } else {
            lbl.setText("Diplôme NON VALIDE (" + ects + "/180 ECTS, UE obligatoires " + (okOblig ? "OK" : "NON") + ")");
        }
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
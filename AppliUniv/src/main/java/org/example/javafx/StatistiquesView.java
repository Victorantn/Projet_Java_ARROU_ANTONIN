package org.example.javafx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.example.Etudiant;
import org.example.Formation;
import org.example.InscriptionUe;
import org.example.Parcours;
import org.example.Statut;
import org.example.Ue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;

/**
 * <p>Vue JavaFX de statistiques</p>
 *
 * <p>Elle permet de :</p>
 * <ul>
 *   <li>filtrer les donnees par formation, parcours, annee et semestre</li>
 *   <li>rechercher un etudiant pour les graphiques qui le concernent</li>
 *   <li>afficher quatre graphiques simples a lire</li>
 * </ul>
 *
 * <p>Les donnees sont calculees a partir de l'etat partage {@link AppState}</p>
 */
public class StatistiquesView {

    /** Libelle utilise pour indiquer l'absence de filtre sur une liste */
    private static final String TOUS = "Tous";

    /** Couleur semantique de reussite */
    private static final String COLOR_SUCCESS = "#2e7d32";

    /** Couleur du graphique de taux pour une lecture plus neutre */
    private static final String COLOR_RATE = "#00838f";

    /** Couleur semantique de progression */
    private static final String COLOR_PROGRESS = "#f9a825";

    /** Couleur semantique d'echec */
    private static final String COLOR_FAILURE = "#c62828";

    /** Couleur secondaire utilisee par certains graphiques */
    private static final String COLOR_INFO = "#1565c0";

    /** Couleur du graphique ECTS quand il affiche des etudiants */
    private static final String COLOR_ECTS = "#ef6c00";

    /** Conteneur racine de la vue */
    private final BorderPane root = new BorderPane();

    /** Etat partage de l'application */
    private final AppState state;

    /** Zone de messages pour l'utilisateur */
    private final Label lbl = new Label("Pret");

    /** Resume de la population actuellement affichee */
    private final Label lblSynthese = new Label();

    /** Selecteur de formation */
    private final ComboBox<Formation> cbFormation = new ComboBox<>();

    /** Selecteur de parcours dependant de la formation */
    private final ComboBox<Parcours> cbParcours = new ComboBox<>();

    /** Selecteur d'annee universitaire */
    private final ComboBox<String> cbAnnee = new ComboBox<>();

    /** Selecteur de semestre */
    private final ComboBox<String> cbSemestre = new ComboBox<>();

    /** Champ de recherche d'un etudiant particulier */
    private final TextField tfRechercheEtudiant = new TextField();

    /** Selecteur d'UE dedie au camembert */
    private final ComboBox<UeChartFilter> cbUeCamembert = new ComboBox<>();

    /** Titre dynamique du graphique de taux */
    private final Label lblTitreReussite = new Label();

    /** Sous-titre d'aide a la lecture du graphique de taux */
    private final Label lblInfoReussite = new Label();

    /** Titre du graphique camembert */
    private final Label lblTitreStatuts = new Label("Statut des inscriptions");

    /** Sous-titre d'aide a la lecture du camembert */
    private final Label lblInfoStatuts = new Label("Vert = validees, orange = en cours, rouge = echouees.");

    /** Titre du graphique des UE les plus echouees */
    private final Label lblTitreEchecs = new Label("UE avec le plus d'echecs");

    /** Sous-titre d'aide a la lecture du graphique des UE echouees */
    private final Label lblInfoEchecs = new Label("Plus la barre est haute, plus cette UE est souvent echouee.");

    /** Titre du quatrieme graphique lie aux ECTS */
    private final Label lblTitreEcts = new Label();

    /** Sous-titre d'aide a la lecture du quatrieme graphique lie aux ECTS */
    private final Label lblInfoEcts = new Label();

    /** Graphique du taux de reussite par groupe */
    private final BarChart<Number, String> chartTauxReussite = createHorizontalBarChart("Taux de reussite (%)");

    /** Graphique de repartition des statuts */
    private final PieChart chartStatuts = createPieChart();

    /** Graphique des UE les plus echouees */
    private final BarChart<Number, String> chartUeEchouees = createHorizontalBarChart("Nombre d'echecs");

    /** Quatrieme graphique de la vue, utilise pour afficher les ECTS */
    private final BarChart<Number, String> chartEcts = createHorizontalBarChart("Valeur");

    /** Conteneur scrollable du graphique de taux */
    private final ScrollPane scrollTauxReussite = createChartScrollPane(chartTauxReussite);

    /** Conteneur scrollable du graphique des UE les plus echouees */
    private final ScrollPane scrollUeEchouees = createChartScrollPane(chartUeEchouees);

    /** Conteneur scrollable du quatrieme graphique lie aux ECTS */
    private final ScrollPane scrollEcts = createChartScrollPane(chartEcts);

    /**
     * <p>Construit la vue de statistiques et initialise les filtres</p>
     *
     * @param state etat partage de l'application
     */
    public StatistiquesView(AppState state) {
        this.state = state;

        cbFormation.setItems(state.getFormations());
        cbFormation.setPromptText("Formation");
        cbParcours.setPromptText("Parcours");
        cbAnnee.setPromptText("Annee universitaire");
        cbSemestre.setPromptText("Semestre");
        tfRechercheEtudiant.setPromptText("Recherche etudiant (nom, prenom ou numero)");
        cbUeCamembert.setPromptText("UE du camembert");

        cbFormation.setConverter(new StringConverter<>() {
            @Override public String toString(Formation f) { return f == null ? "" : f.getNomFormation(); }
            @Override public Formation fromString(String s) { return null; }
        });

        cbParcours.setConverter(new StringConverter<>() {
            @Override public String toString(Parcours p) { return p == null ? "" : p.getNomParcours(); }
            @Override public Parcours fromString(String s) { return null; }
        });

        cbSemestre.setItems(FXCollections.observableArrayList(TOUS, "IMPAIR", "PAIR"));
        cbSemestre.setValue(TOUS);
        cbUeCamembert.setConverter(new StringConverter<>() {
            @Override public String toString(UeChartFilter filter) {
                return filter == null ? "" : filter.label();
            }
            @Override public UeChartFilter fromString(String s) { return null; }
        });

        cbFormation.valueProperty().addListener((o, a, f) -> {
            refreshParcoursForFormation(f);
            refreshAnnees();
            refreshCharts();
        });
        cbParcours.valueProperty().addListener((o, a, p) -> {
            refreshAnnees();
            refreshCharts();
        });
        cbAnnee.valueProperty().addListener((o, a, y) -> refreshCharts());
        cbSemestre.valueProperty().addListener((o, a, s) -> refreshCharts());
        tfRechercheEtudiant.textProperty().addListener((o, a, text) -> refreshCharts());
        cbUeCamembert.valueProperty().addListener((o, a, u) -> refreshStatuts(collectFilteredEntries()));

        root.setPadding(new Insets(12));
        root.setCenter(content());
        root.setBottom(statusBar());

        configureCharts();
        refreshData();
    }

    /**
     * <p>Rafraichit les filtres derives et recalcule tous les graphiques</p>
     *
     * <p>Cette methode peut etre appelee quand la vue redevient visible</p>
     */
    public void refreshData() {
        if (cbFormation.getValue() != null && !state.getFormations().contains(cbFormation.getValue())) {
            cbFormation.setValue(null);
        }

        refreshParcoursForFormation(cbFormation.getValue());
        refreshAnnees();
        refreshCharts();
    }

    /**
     * <p>Construit le contenu principal de la vue</p>
     *
     * <p>Organisation :</p>
     * <ul>
     *   <li>en haut : les filtres et la synthese</li>
     *   <li>au centre : une grille de quatre graphiques</li>
     * </ul>
     *
     * @return un conteneur JavaFX a afficher au centre
     */
    private Parent content() {
        Button btnRefresh = new Button("Rafraichir statistiques");
        btnRefresh.setOnAction(e -> refreshData());

        HBox filters = new HBox(10, cbFormation, cbParcours, cbAnnee, cbSemestre);
        HBox.setHgrow(cbFormation, Priority.ALWAYS);
        HBox.setHgrow(cbParcours, Priority.ALWAYS);
        HBox.setHgrow(cbAnnee, Priority.ALWAYS);
        HBox.setHgrow(cbSemestre, Priority.ALWAYS);

        HBox searchRow = new HBox(10, tfRechercheEtudiant, btnRefresh);
        HBox.setHgrow(tfRechercheEtudiant, Priority.ALWAYS);

        lblSynthese.setStyle("-fx-font-weight: bold;");

        VBox top = new VBox(10,
                new Label("Filtres des statistiques"),
                filters,
                searchRow,
                lblSynthese
        );
        top.setPadding(new Insets(10));

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPadding(new Insets(10));
        grid.getColumnConstraints().addAll(new ColumnConstraints(), new ColumnConstraints());
        grid.getColumnConstraints().get(0).setPercentWidth(50);
        grid.getColumnConstraints().get(1).setPercentWidth(50);

        Parent cardTaux = buildChartCard(buildCardHeader(lblTitreReussite, lblInfoReussite), scrollTauxReussite);
        Parent cardStatuts = buildChartCard(buildPieHeader(), chartStatuts);
        Parent cardEchecs = buildChartCard(buildCardHeader(lblTitreEchecs, lblInfoEchecs), scrollUeEchouees);
        Parent cardEcts = buildChartCard(buildCardHeader(lblTitreEcts, lblInfoEcts), scrollEcts);

        grid.add(cardTaux, 0, 0);
        grid.add(cardEcts, 1, 0);
        grid.add(cardStatuts, 0, 1);
        grid.add(cardEchecs, 1, 1);

        GridPane.setHgrow(cardTaux, Priority.ALWAYS);
        GridPane.setHgrow(cardEcts, Priority.ALWAYS);
        GridPane.setHgrow(cardStatuts, Priority.ALWAYS);
        GridPane.setHgrow(cardEchecs, Priority.ALWAYS);
        GridPane.setVgrow(cardTaux, Priority.ALWAYS);
        GridPane.setVgrow(cardEcts, Priority.ALWAYS);
        GridPane.setVgrow(cardStatuts, Priority.ALWAYS);
        GridPane.setVgrow(cardEchecs, Priority.ALWAYS);

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setStyle("-fx-background-color: transparent;");

        VBox wrapper = new VBox(10, top, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return wrapper;
    }

    /**
     * <p>Construit une carte graphique homogene avec les autres vues</p>
     *
     * @param titre titre affiche au-dessus du graphique
     * @param chart composant graphique a integrer
     * @return un conteneur stylise pour la grille de bord
     */
    private Parent buildChartCard(String titre, Parent chart) {
        Label lblTitre = new Label(titre);
        lblTitre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        return buildChartCard(lblTitre, chart);
    }

    /**
     * <p>Construit une carte graphique homogene avec un en-tete deja compose</p>
     *
     * @param header en-tete affiche au-dessus du graphique
     * @param chart composant graphique a integrer
     * @return un conteneur stylise pour la grille de bord
     */
    private Parent buildChartCard(Parent header, Parent chart) {
        if (header instanceof Label label) {
            label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        }

        VBox box = new VBox(10, header, chart);
        box.setPadding(new Insets(12));
        box.setStyle(
                "-fx-background-color: #f5f5f5;" +
                        "-fx-border-color: #d8d8d8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;"
        );
        VBox.setVgrow(chart, Priority.ALWAYS);
        return box;
    }

    /**
     * <p>Construit un en-tete de carte avec titre et sous-titre explicatif</p>
     *
     * @param titre label du titre principal
     * @param info label d'aide a la lecture
     * @return un conteneur vertical pret a etre affiche
     */
    private Parent buildCardHeader(Label titre, Label info) {
        titre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        info.setStyle("-fx-font-size: 12px; -fx-text-fill: #5f6368;");

        VBox box = new VBox(4, titre, info);
        return box;
    }

    /**
     * <p>Construit l'en-tete specifique du camembert</p>
     *
     * <p>Le selecteur d'UE ne pilote que ce graphique</p>
     *
     * @return une barre d'en-tete pour le graphique circulaire
     */
    private Parent buildPieHeader() {
        lblTitreStatuts.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        lblInfoStatuts.setStyle("-fx-font-size: 12px; -fx-text-fill: #5f6368;");
        cbUeCamembert.setMaxWidth(Double.MAX_VALUE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(cbUeCamembert, Priority.ALWAYS);

        HBox topLine = new HBox(10, lblTitreStatuts, spacer, cbUeCamembert);
        topLine.setFillHeight(true);

        return new VBox(4, topLine, lblInfoStatuts);
    }

    /**
     * <p>Rafraichit la liste des parcours selon la formation selectionnee</p>
     *
     * <p>Si aucune formation n'est selectionnee, la liste des parcours est videe</p>
     *
     * @param f formation selectionnee ou null
     */
    private void refreshParcoursForFormation(Formation f) {
        Parcours current = cbParcours.getValue();

        if (f == null) {
            cbParcours.setItems(FXCollections.observableArrayList());
            cbParcours.setValue(null);
            return;
        }

        var filtered = FXCollections.<Parcours>observableArrayList();
        for (Parcours p : state.getParcours()) {
            if (p.getFormation() == f) filtered.add(p);
        }

        cbParcours.setItems(filtered);
        if (current != null && filtered.contains(current)) {
            cbParcours.setValue(current);
        } else {
            cbParcours.setValue(null);
        }
    }

    /**
     * <p>Rafraichit la liste des annees universitaires disponibles</p>
     *
     * <p>Les annees sont deduites des inscriptions des etudiants correspondant
     * a la formation et au parcours selectionnes</p>
     */
    private void refreshAnnees() {
        String current = cbAnnee.getValue();
        var years = new TreeSet<String>();

        for (Etudiant e : state.getEtudiants()) {
            if (!matchesEtudiantFilter(e)) continue;
            for (InscriptionUe ins : e.getInscriptions()) {
                years.add(ins.getAnneeUniversitaire());
            }
        }

        var items = FXCollections.<String>observableArrayList();
        items.add(TOUS);
        items.addAll(years);
        cbAnnee.setItems(items);

        if (current != null && items.contains(current)) {
            cbAnnee.setValue(current);
        } else {
            cbAnnee.setValue(TOUS);
        }
    }

    /**
     * <p>Recalcule les quatre graphiques selon les filtres courants</p>
     */
    private void refreshCharts() {
        List<Etudiant> filteredStudents = collectFilteredStudents();
        List<InscriptionEntry> filteredEntries = collectFilteredEntries();

        refreshPieUeFilters(filteredEntries);
        refreshSynthese(filteredStudents, filteredEntries);
        refreshLeftTitles();
        refreshTauxReussite(filteredStudents, filteredEntries);
        refreshStatuts(filteredEntries);
        refreshUeEchouees(filteredEntries);
        refreshEcts(filteredStudents);

        if (filteredEntries.isEmpty()) {
            lbl.setText("Aucune donnee pour les filtres choisis");
        } else {
            lbl.setText("Statistiques mises a jour");
        }
    }

    /**
     * <p>Configure les graphiques ayant un comportement particulier</p>
     *
     * <p>Le taux de reussite est borne entre 0 et 100</p>
     */
    private void configureCharts() {
        NumberAxis axis = (NumberAxis) chartTauxReussite.getXAxis();
        axis.setAutoRanging(false);
        axis.setLowerBound(0);
        axis.setUpperBound(100);
        axis.setTickUnit(10);

        chartTauxReussite.setCategoryGap(12);
        chartUeEchouees.setCategoryGap(10);
        chartEcts.setCategoryGap(12);
        chartTauxReussite.setBarGap(6);
        chartUeEchouees.setBarGap(6);
        chartEcts.setBarGap(6);
    }

    /**
     * <p>Met a jour la ligne de synthese sous les filtres</p>
     *
     * @param students etudiants retenus par les filtres de population
     * @param entries inscriptions retenues apres application des filtres
     */
    private void refreshSynthese(List<Etudiant> students, List<InscriptionEntry> entries) {
        var ueCodes = new TreeSet<Integer>();
        for (InscriptionEntry entry : entries) {
            ueCodes.add(entry.inscription().getUe().getCodeUe());
        }

        lblSynthese.setText(
                "Etudiants : " + students.size() +
                        " | Inscriptions : " + entries.size() +
                        " | UE distinctes : " + ueCodes.size()
        );
    }

    /**
     * <p>Met a jour les titres des deux graphiques de gauche</p>
     */
    private void refreshLeftTitles() {
        String suffixe = switch (currentGroupingMode()) {
            case FORMATION -> "par formation";
            case PARCOURS -> "par parcours";
            case ETUDIANT -> "par etudiant";
        };

        lblTitreReussite.setText("Taux de reussite " + suffixe);
        lblInfoReussite.setText("Plus la barre est haute, plus le taux de validation est bon.");
    }

    /**
     * <p>Rafraichit la liste des UE disponibles pour le camembert</p>
     *
     * <p>Le choix est deduit des inscriptions deja retenues par les filtres globaux</p>
     *
     * @param entries inscriptions filtrees
     */
    private void refreshPieUeFilters(List<InscriptionEntry> entries) {
        Integer currentCode = cbUeCamembert.getValue() == null ? null : cbUeCamembert.getValue().codeUe();
        Map<Integer, UeChartFilter> options = new LinkedHashMap<>();
        options.put(null, new UeChartFilter(null, "Toutes les UE"));

        entries.stream()
                .map(entry -> entry.inscription().getUe())
                .sorted(Comparator.comparingInt(Ue::getCodeUe))
                .forEach(ue -> options.putIfAbsent(
                        ue.getCodeUe(),
                        new UeChartFilter(ue.getCodeUe(), formatUe(ue))
                ));

        var items = FXCollections.observableArrayList(options.values());
        cbUeCamembert.setItems(items);

        UeChartFilter selected = null;
        if (currentCode == null) {
            selected = items.getFirst();
        } else {
            for (UeChartFilter filter : items) {
                if (currentCode.equals(filter.codeUe())) {
                    selected = filter;
                    break;
                }
            }
            if (selected == null) selected = items.getFirst();
        }
        cbUeCamembert.setValue(selected);
    }

    /**
     * <p>Alimente le graphique du taux de reussite par groupe</p>
     *
     * <p>Le groupe compare les formations, les parcours ou les etudiants selon les filtres courants</p>
     * <p>Le calcul ne prend en compte que les inscriptions VALIDE et ECHOUE</p>
     *
     * @param students etudiants filtres
     * @param entries inscriptions filtrees
     */
    private void refreshTauxReussite(List<Etudiant> students, List<InscriptionEntry> entries) {
        Map<String, GroupStats> byGroup = new LinkedHashMap<>();
        GroupingMode mode = currentGroupingMode();
        boolean searchActive = mode == GroupingMode.ETUDIANT && hasStudentSearch();

        for (Etudiant student : students) {
            if (searchActive && !matchesStudentSearch(student)) continue;

            String key = groupKey(student, mode);
            byGroup.computeIfAbsent(
                    key,
                    ignored -> new GroupStats(groupLabel(student, mode))
            );
        }

        for (InscriptionEntry entry : entries) {
            Statut statut = entry.inscription().getStatut();
            if (statut != Statut.VALIDE && statut != Statut.ECHOUE) continue;
            if (searchActive && !matchesStudentSearch(entry.etudiant())) continue;

            String key = groupKey(entry.etudiant(), mode);
            GroupStats stats = byGroup.computeIfAbsent(
                    key,
                    ignored -> new GroupStats(groupLabel(entry.etudiant(), mode))
            );
            if (statut == Statut.VALIDE) stats.valides++;
            if (statut == Statut.ECHOUE) stats.echoues++;
        }

        List<GroupStats> ordered = new ArrayList<>(byGroup.values());
        ordered.sort(Comparator
                .comparingDouble(GroupStats::tauxReussite)
                .reversed()
                .thenComparing(GroupStats::label));

        XYChart.Series<Number, String> series = new XYChart.Series<>();
        for (GroupStats stats : ordered) {
            double taux = round(stats.tauxReussite());
            String label = formatRateLabel(stats.label(), stats.valides(), stats.totalTentatives(), taux);
            series.getData().add(new XYChart.Data<>(taux, label));
        }

        chartTauxReussite.getData().clear();
        if (!series.getData().isEmpty()) {
            chartTauxReussite.getData().add(series);
            updateHorizontalChartHeight(chartTauxReussite, series.getData().size(), 34, 340);
            applyBarColor(chartTauxReussite, COLOR_RATE);
        }
    }

    /**
     * <p>Alimente le graphique de repartition des statuts</p>
     *
     * @param entries inscriptions filtrees
     */
    private void refreshStatuts(List<InscriptionEntry> entries) {
        Map<Statut, Integer> counts = new EnumMap<>(Statut.class);
        counts.put(Statut.ENCOURS, 0);
        counts.put(Statut.VALIDE, 0);
        counts.put(Statut.ECHOUE, 0);

        UeChartFilter filter = cbUeCamembert.getValue();
        Integer codeUe = filter == null ? null : filter.codeUe();

        for (InscriptionEntry entry : entries) {
            if (codeUe != null && entry.inscription().getUe().getCodeUe() != codeUe) continue;
            Statut statut = entry.inscription().getStatut();
            counts.put(statut, counts.get(statut) + 1);
        }

        var data = FXCollections.<PieChart.Data>observableArrayList();
        int total = counts.values().stream().mapToInt(Integer::intValue).sum();
        for (Statut statut : Statut.values()) {
            int value = counts.getOrDefault(statut, 0);
            if (value > 0) {
                double percent = total == 0 ? 0 : (value * 100.0) / total;
                data.add(new PieChart.Data(
                        displayStatusLabel(statut) + " - " + formatPercent(percent),
                        value
                ));
            }
        }

        lblTitreStatuts.setText(codeUe == null
                ? "Statut des inscriptions"
                : "Statut des inscriptions pour " + filter.label());
        chartStatuts.setData(data);
        applyPieColors();
        applyPieLegendColors();
    }

    /**
     * <p>Alimente le graphique des UE les plus echouees</p>
     *
     * @param entries inscriptions filtrees
     */
    private void refreshUeEchouees(List<InscriptionEntry> entries) {
        Map<Integer, UeFailureStats> byUe = new LinkedHashMap<>();

        for (InscriptionEntry entry : entries) {
            if (entry.inscription().getStatut() != Statut.ECHOUE) continue;

            Ue ue = entry.inscription().getUe();
            UeFailureStats stats = byUe.computeIfAbsent(
                    ue.getCodeUe(),
                    ignored -> new UeFailureStats(ue)
            );
            stats.echecs++;
        }

        List<UeFailureStats> ordered = new ArrayList<>(byUe.values());
        ordered.sort(Comparator
                .comparingInt(UeFailureStats::echecs)
                .reversed()
                .thenComparing(stats -> stats.ue().getCodeUe()));

        XYChart.Series<Number, String> series = new XYChart.Series<>();
        for (UeFailureStats stats : ordered) {
            String label = formatUe(stats.ue()) + " - " + stats.echecs() + " echecs";
            series.getData().add(new XYChart.Data<>(stats.echecs(), label));
        }

        chartUeEchouees.getData().clear();
        if (!series.getData().isEmpty()) {
            chartUeEchouees.getData().add(series);
            updateHorizontalChartHeight(chartUeEchouees, series.getData().size(), 34, 340);
            applyBarColor(chartUeEchouees, COLOR_FAILURE);
        }
    }

    /**
     * <p>Met a jour le quatrieme graphique lie aux ECTS</p>
     *
     * <p>Si une recherche etudiant est saisie, le graphique affiche les ECTS valides
     * des etudiants correspondants. Sinon, il affiche une repartition du nombre
     * d'etudiants pour chaque total d'ECTS visible dans la vue</p>
     *
     * <p>Seules les inscriptions VALIDE correspondant aux filtres d'annee
     * et de semestre sont comptabilisees</p>
     *
     * @param students etudiants filtres
     */
    private void refreshEcts(List<Etudiant> students) {
        List<StudentEcts> values = new ArrayList<>();

        for (Etudiant student : students) {
            if (hasStudentSearch() && !matchesStudentSearch(student)) continue;

            values.add(new StudentEcts(student, computeFilteredValidatedEcts(student)));
        }

        chartEcts.getData().clear();

        if (hasStudentSearch()) {
            values.sort(Comparator
                    .comparingInt(StudentEcts::ects)
                    .reversed()
                    .thenComparing(value -> value.etudiant().getNomE())
                    .thenComparing(value -> value.etudiant().getPrenomE()));

            lblTitreEcts.setText("ECTS valides des etudiants recherches");
            lblInfoEcts.setText("Plus la barre est haute, plus l'etudiant a valide d'ECTS.");

            XYChart.Series<Number, String> series = new XYChart.Series<>();
            for (StudentEcts value : values) {
                String label = formatEtudiant(value.etudiant()) + " - " + value.ects() + " ECTS valides";
                series.getData().add(new XYChart.Data<>(value.ects(), label));
            }

            if (!series.getData().isEmpty()) {
                chartEcts.getData().add(series);
                updateHorizontalChartHeight(chartEcts, series.getData().size(), 34, 340);
                NumberAxis axis = (NumberAxis) chartEcts.getXAxis();
                axis.setLabel("ECTS valides");
                axis.setAutoRanging(true);
                applyBarColor(chartEcts, COLOR_ECTS);
            }
            return;
        }

        lblTitreEcts.setText("Etudiants par total d'ECTS valides");
        lblInfoEcts.setText("Chaque barre montre combien d'etudiants ont exactement ce total.");

        LinkedHashMap<String, Integer> tranches = buildEctsRanges();

        for (StudentEcts value : values) {
            String range = resolveEctsRange(value.ects());
            if (tranches.containsKey(range)) {
                tranches.put(range, tranches.get(range) + 1);
            }
        }

        XYChart.Series<Number, String> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : tranches.entrySet()) {
            String label = entry.getKey() + " - " + entry.getValue() + " etudiants";
            series.getData().add(new XYChart.Data<>(entry.getValue(), label));
        }

        chartEcts.getData().add(series);
        updateHorizontalChartHeight(chartEcts, series.getData().size(), 38, 340);
        NumberAxis axis = (NumberAxis) chartEcts.getXAxis();
        axis.setLabel("Nombre d'etudiants");
        axis.setAutoRanging(true);
        applyBarColor(chartEcts, COLOR_INFO);
    }

    /**
     * <p>Calcule le total d'ECTS valides d'un etudiant sur les filtres courants</p>
     *
     * @param student etudiant concerne
     * @return total d'ECTS valides
     */
    private int computeFilteredValidatedEcts(Etudiant student) {
        int total = 0;
        for (InscriptionUe ins : student.getInscriptions()) {
            if (!matchesInscriptionFilter(ins)) continue;
            if (ins.getStatut() == Statut.VALIDE) total += ins.getUe().getEcts();
        }
        return total;
    }

    /**
     * <p>Construit les valeurs d'ECTS a afficher dans le dernier graphique</p>
     *
     * <p>Les valeurs sont fixes de 0 a 66 avec un pas de 3 ECTS</p>
     *
     * @return map ordonnee des valeurs initialisee a zero
     */
    private LinkedHashMap<String, Integer> buildEctsRanges() {
        LinkedHashMap<String, Integer> ranges = new LinkedHashMap<>();
        for (int ects = 0; ects <= 66; ects += 3) {
            ranges.put(formatEctsRange(ects), 0);
        }
        return ranges;
    }

    /**
     * <p>Retourne la valeur d'ECTS correspondant a un total</p>
     *
     * @param ects total d'ECTS valides
     * @return libelle de valeur exacte si elle est multiple de 3 et dans la plage sinon null
     */
    private String resolveEctsRange(int ects) {
        if (ects < 0 || ects > 66 || ects % 3 != 0) return null;
        return formatEctsRange(ects);
    }

    /**
     * <p>Formate une valeur exacte d'ECTS</p>
     *
     * @param value valeur exacte d'ECTS
     * @return libelle utilisateur
     */
    private String formatEctsRange(int value) {
        return value + " ECTS";
    }

    /**
     * <p>Collecte les etudiants correspondant aux filtres de population</p>
     *
     * @return liste des etudiants retenus
     */
    private List<Etudiant> collectFilteredStudents() {
        List<Etudiant> students = new ArrayList<>();
        for (Etudiant e : state.getEtudiants()) {
            if (matchesEtudiantFilter(e)) students.add(e);
        }
        return students;
    }

    /**
     * <p>Collecte les inscriptions correspondant a tous les filtres courants</p>
     *
     * @return liste des inscriptions retenues avec leur etudiant
     */
    private List<InscriptionEntry> collectFilteredEntries() {
        List<InscriptionEntry> entries = new ArrayList<>();

        for (Etudiant e : state.getEtudiants()) {
            if (!matchesEtudiantFilter(e)) continue;
            for (InscriptionUe ins : e.getInscriptions()) {
                if (matchesInscriptionFilter(ins)) {
                    entries.add(new InscriptionEntry(e, ins));
                }
            }
        }

        return entries;
    }

    /**
     * <p>Indique si un etudiant correspond aux filtres de formation et de parcours</p>
     *
     * @param e etudiant a tester
     * @return true si l'etudiant correspond sinon false
     */
    private boolean matchesEtudiantFilter(Etudiant e) {
        Formation formation = cbFormation.getValue();
        Parcours parcours = cbParcours.getValue();

        if (formation != null && e.getFormation() != formation) return false;
        if (parcours != null && e.getParcours() != parcours) return false;
        return true;
    }

    /**
     * <p>Indique si une inscription correspond aux filtres d'annee et de semestre</p>
     *
     * @param ins inscription a tester
     * @return true si l'inscription correspond sinon false
     */
    private boolean matchesInscriptionFilter(InscriptionUe ins) {
        String annee = cbAnnee.getValue();
        String semestre = cbSemestre.getValue();

        if (annee != null && !TOUS.equals(annee) && !annee.equals(ins.getAnneeUniversitaire())) return false;
        if (semestre != null && !TOUS.equals(semestre) && !semestre.equals(ins.getSemestre().name())) return false;
        return true;
    }

    /**
     * <p>Construit un graphique en barres standardise pour la vue</p>
     *
     * @param xLabel libelle de l'axe horizontal
     * @return un graphique configure et non anime
     */
    private static BarChart<Number, String> createHorizontalBarChart(String xLabel) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(xLabel);

        CategoryAxis yAxis = new CategoryAxis();

        BarChart<Number, String> chart = new BarChart<>(xAxis, yAxis);
        chart.setAnimated(false);
        chart.setLegendVisible(false);
        chart.setMinHeight(320);
        chart.setPrefHeight(320);
        chart.setCategoryGap(12);
        chart.setBarGap(6);
        return chart;
    }

    /**
     * <p>Retourne le mode de regroupement adapte aux filtres courants</p>
     *
     * @return mode de regroupement utilise par les graphiques de gauche
     */
    private GroupingMode currentGroupingMode() {
        if (cbParcours.getValue() != null) return GroupingMode.ETUDIANT;
        if (cbFormation.getValue() != null) return GroupingMode.PARCOURS;
        return GroupingMode.FORMATION;
    }

    /**
     * <p>Retourne le libelle humain du regroupement courant</p>
     *
     * @return libelle de regroupement
     */
    private String currentGroupingLabel() {
        return switch (currentGroupingMode()) {
            case FORMATION -> "Formation";
            case PARCOURS -> "Parcours";
            case ETUDIANT -> "Etudiant";
        };
    }

    /**
     * <p>Construit une cle de regroupement stable pour un etudiant</p>
     *
     * @param e etudiant source
     * @param mode mode de regroupement
     * @return cle interne stable
     */
    private String groupKey(Etudiant e, GroupingMode mode) {
        return switch (mode) {
            case FORMATION -> e.getFormation() == null ? "formation:null" : "formation:" + e.getFormation().getCodeFormation();
            case PARCOURS -> e.getParcours() == null ? "parcours:null" : "parcours:" + e.getParcours().getIdParcours();
            case ETUDIANT -> "etudiant:" + e.getNumeroEtudiant();
        };
    }

    /**
     * <p>Construit le libelle de regroupement pour un etudiant</p>
     *
     * @param e etudiant source
     * @param mode mode de regroupement
     * @return libelle affiche sur les axes
     */
    private String groupLabel(Etudiant e, GroupingMode mode) {
        return switch (mode) {
            case FORMATION -> e.getFormation() == null ? "Sans formation" : e.getFormation().getNomFormation();
            case PARCOURS -> e.getParcours() == null ? "Sans parcours" : e.getParcours().getNomParcours();
            case ETUDIANT -> formatEtudiant(e);
        };
    }

    /**
     * <p>Construit un graphique circulaire standardise pour la vue</p>
     *
     * @return un graphique configure et non anime
     */
    private static PieChart createPieChart() {
        PieChart chart = new PieChart();
        chart.setAnimated(false);
        chart.setLabelsVisible(true);
        chart.setMinHeight(320);
        chart.setPrefHeight(320);
        return chart;
    }

    /**
     * <p>Construit un conteneur scrollable pour afficher tous les items d'un graphique</p>
     *
     * @param chart graphique a rendre scrollable
     * @return un {@link ScrollPane} horizontal
     */
    private ScrollPane createChartScrollPane(BarChart<Number, String> chart) {
        ScrollPane scroll = new ScrollPane(chart);
        scroll.setFitToHeight(false);
        scroll.setFitToWidth(true);
        scroll.setPannable(true);
        scroll.setStyle("-fx-background-color: transparent;");
        scroll.setMinHeight(340);
        scroll.setPrefHeight(340);
        return scroll;
    }

    /**
     * <p>Adapte la hauteur du graphique au nombre d'elements affiches</p>
     *
     * <p>Permet de conserver toutes les categories lisibles via scroll vertical</p>
     *
     * @param chart graphique a ajuster
     * @param itemCount nombre de categories affichees
     * @param itemHeight hauteur cible par categorie
     */
    private void updateHorizontalChartHeight(BarChart<Number, String> chart, int itemCount, int itemHeight, int minHeight) {
        int height = Math.max(minHeight, 110 + itemCount * itemHeight);
        chart.setMinHeight(height);
        chart.setPrefHeight(height);
    }

    /**
     * <p>Applique une couleur semantique uniforme sur les barres d'un graphique</p>
     *
     * @param chart graphique cible
     * @param color couleur CSS a appliquer
     */
    private void applyBarColor(BarChart<?, ?> chart, String color) {
        Platform.runLater(() -> {
            for (XYChart.Series<?, ?> series : chart.getData()) {
                for (XYChart.Data<?, ?> data : series.getData()) {
                    if (data.getNode() != null) {
                        data.getNode().setStyle("-fx-bar-fill: " + color + ";");
                    }
                }
            }
        });
    }

    /**
     * <p>Applique des couleurs semantiques sur le camembert des statuts</p>
     *
     * <p>VALIDE est vert, ENCOURS orange et ECHOUE rouge</p>
     */
    private void applyPieColors() {
        Platform.runLater(() -> {
            for (PieChart.Data data : chartStatuts.getData()) {
                if (data.getNode() == null) continue;
                data.getNode().setStyle("-fx-pie-color: " + legendColor(data.getName()) + ";");
            }
        });
    }

    /**
     * <p>Applique aussi les couleurs semantiques sur la legende du camembert</p>
     */
    private void applyPieLegendColors() {
        Platform.runLater(() -> {
            for (Node item : chartStatuts.lookupAll(".chart-legend-item")) {
                if (!(item instanceof Label legendItem)) continue;

                String name = legendItem.getText();
                String color = legendColor(name);

                Node symbol = legendItem.getGraphic();
                if (symbol != null) {
                    symbol.setStyle(
                            "-fx-background-color: " + color + ";" +
                                    "-fx-background-radius: 6;"
                    );
                }

                legendItem.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");
            }
        });
    }

    /**
     * <p>Retourne la couleur semantique a utiliser pour un statut</p>
     *
     * @param name libelle de statut ou de legende
     * @return couleur CSS correspondante
     */
    private String legendColor(String name) {
        String lower = name == null ? "" : name.toLowerCase(Locale.ROOT);
        if (lower.startsWith("valide")) return COLOR_SUCCESS;
        if (lower.startsWith("en cours")) return COLOR_PROGRESS;
        if (lower.startsWith("echou")) return COLOR_FAILURE;
        return COLOR_INFO;
    }

    /**
     * <p>Retourne un libelle simple pour un statut d'inscription</p>
     *
     * @param statut statut a afficher
     * @return texte utilisateur du statut
     */
    private String displayStatusLabel(Statut statut) {
        return switch (statut) {
            case ENCOURS -> "En cours";
            case VALIDE -> "Validees";
            case ECHOUE -> "Echouees";
        };
    }

    /**
     * <p>Indique si une recherche etudiant est en cours</p>
     *
     * @return true si le champ de recherche n'est pas vide sinon false
     */
    private boolean hasStudentSearch() {
        return !tfRechercheEtudiant.getText().trim().isEmpty();
    }

    /**
     * <p>Indique si un etudiant correspond a la recherche saisie</p>
     *
     * <p>La recherche s'applique sur le nom, le prenom et le numero etudiant</p>
     *
     * @param e etudiant a tester
     * @return true si l'etudiant correspond sinon false
     */
    private boolean matchesStudentSearch(Etudiant e) {
        String q = tfRechercheEtudiant.getText().trim().toLowerCase();
        if (q.isEmpty()) return true;

        String numero = String.valueOf(e.getNumeroEtudiant());
        return e.getNomE().toLowerCase().contains(q)
                || e.getPrenomE().toLowerCase().contains(q)
                || (e.getPrenomE() + " " + e.getNomE()).toLowerCase().contains(q)
                || numero.contains(q);
    }

    /**
     * <p>Formate une UE pour affichage sur les axes des graphiques</p>
     *
     * @param ue UE a afficher
     * @return libelle court lisible
     */
    private String formatUe(Ue ue) {
        return "#" + ue.getCodeUe() + " - " + ue.getNomUe();
    }

    /**
     * <p>Formate un etudiant pour affichage sur les axes des graphiques</p>
     *
     * @param e etudiant a afficher
     * @return libelle court lisible
     */
    private String formatEtudiant(Etudiant e) {
        return e.getPrenomE() + " " + e.getNomE();
    }

    /**
     * <p>Arrondit une valeur a deux decimales pour l'affichage</p>
     *
     * @param value valeur a arrondir
     * @return valeur arrondie
     */
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    /**
     * <p>Formate un pourcentage pour affichage utilisateur</p>
     *
     * @param value pourcentage a formatter
     * @return texte formate avec une decimale
     */
    private String formatPercent(double value) {
        return String.format(Locale.ROOT, "%.1f%%", value);
    }

    /**
     * <p>Formate un libelle simple pour le graphique de reussite</p>
     *
     * @param label groupe affiche
     * @param valides nombre d'inscriptions validees
     * @param tentatives nombre de tentatives closes
     * @param taux taux de reussite calcule
     * @return libelle lisible pour l'utilisateur
     */
    private String formatRateLabel(String label, int valides, int tentatives, double taux) {
        return label + " - " + formatPercent(taux) + " (" + valides + " validees sur " + tentatives + ")";
    }

    /**
     * <p>Construit la barre de statut de la vue</p>
     *
     * @return un conteneur JavaFX affichant le message courant
     */
    private Parent statusBar() {
        lbl.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10;" +
                        "-fx-background-color: #2b2b2b;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox b = new HBox(10, lbl, spacer);
        b.setPadding(new Insets(8, 10, 0, 10));
        return b;
    }

    /**
     * <p>Retourne le noeud racine de la vue</p>
     *
     * @return le {@link Parent} racine
     */
    public Parent getRoot() {
        return root;
    }

    /**
     * <p>Association simple entre un etudiant et une inscription filtree</p>
     */
    private record InscriptionEntry(Etudiant etudiant, InscriptionUe inscription) { }

    /**
     * <p>Mode de regroupement des deux graphiques de gauche</p>
     */
    private enum GroupingMode {

        /** Comparaison globale entre formations */
        FORMATION,

        /** Comparaison entre parcours d'une meme formation */
        PARCOURS,

        /** Comparaison entre etudiants d'un meme parcours */
        ETUDIANT
    }

    /**
     * <p>Structure interne de cumul pour les statistiques par groupe</p>
     */
    private static final class GroupStats {

        /** Libelle affiche pour le groupe */
        private final String label;

        /** Nombre d'inscriptions validees du groupe */
        private int valides;

        /** Nombre d'inscriptions echouees du groupe */
        private int echoues;

        /**
         * <p>Construit un agregat sur un groupe</p>
         *
         * @param label libelle du groupe
         */
        private GroupStats(String label) {
            this.label = label;
        }

        /**
         * <p>Retourne le libelle du groupe</p>
         *
         * @return libelle du groupe
         */
        private String label() {
            return label;
        }

        /**
         * <p>Retourne le nombre de tentatives closes pour ce groupe</p>
         *
         * @return nombre de tentatives closes
         */
        private int totalTentatives() {
            return valides + echoues;
        }

        /**
         * <p>Calcule le taux de reussite du groupe</p>
         *
         * @return taux en pourcentage
         */
        private double tauxReussite() {
            int total = totalTentatives();
            if (total == 0) return 0;
            return (valides * 100.0) / total;
        }

        /**
         * <p>Retourne le nombre d'inscriptions validees du groupe</p>
         *
         * @return nombre d'inscriptions validees
         */
        private int valides() {
            return valides;
        }
    }

    /**
     * <p>Structure interne de cumul pour les statistiques d'echec par UE</p>
     */
    private static final class UeFailureStats {

        /** UE concernee */
        private final Ue ue;

        /** Nombre d'echecs observes */
        private int echecs;

        /**
         * <p>Construit un agregat sur une UE</p>
         *
         * @param ue UE concernee
         */
        private UeFailureStats(Ue ue) {
            this.ue = ue;
        }

        /**
         * <p>Retourne l'UE concernee</p>
         *
         * @return UE de l'agregat
         */
        private Ue ue() {
            return ue;
        }

        /**
         * <p>Retourne le nombre d'echecs recenses</p>
         *
         * @return nombre d'echecs
         */
        private int echecs() {
            return echecs;
        }
    }

    /**
     * <p>Option de filtre dediee au camembert</p>
     *
     * @param codeUe code UE cible ou null pour toutes
     * @param label texte affiche dans la ComboBox
     */
    private record UeChartFilter(Integer codeUe, String label) { }

    /**
     * <p>Structure interne pour afficher les ECTS valides par etudiant</p>
     */
    private record StudentEcts(Etudiant etudiant, int ects) { }
}

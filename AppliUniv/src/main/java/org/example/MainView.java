package org.example;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MainView {

    private final BorderPane root = new BorderPane();
    private final ObservableList<Etudiant> etudiants = FXCollections.observableArrayList();
    private final TableView<Etudiant> table = new TableView<>(etudiants);

    private final TextField tfNom = new TextField();
    private final TextField tfPrenom = new TextField();
    private final TextField tfFormation = new TextField();
    private final TextField tfParcours = new TextField();
    private final Label lblMsg = new Label();

    public MainView() {
        root.setPadding(new Insets(12));
        tfNom.setPromptText("Nom");
        tfPrenom.setPromptText("Prénom");
        tfFormation.setPromptText("Formation");
        tfParcours.setPromptText("Parcours");

        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setOnAction(e -> ajouter());

        Button btnSupprimer = new Button("Supprimer sélection");
        btnSupprimer.setOnAction(e -> supprimerSelection());

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(new Label("Nom"), 0, 0);
        form.add(tfNom, 1, 0);
        form.add(new Label("Prénom"), 0, 1);
        form.add(tfPrenom, 1, 1);
        form.add(new Label("Formation"), 0, 2);
        form.add(tfFormation, 1, 2);
        form.add(new Label("Parcours"), 0, 3);
        form.add(tfParcours, 1, 3);

        HBox actions = new HBox(10, btnAjouter, btnSupprimer);

        VBox left = new VBox(10, new Label("Ajouter un étudiant"), form, actions, lblMsg);
        left.setPadding(new Insets(10));
        left.setPrefWidth(360);

        TableColumn<Etudiant, Number> colNum = new TableColumn<>("Numéro");
        colNum.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getNumeroEtudiant()));
        colNum.setPrefWidth(120);

        TableColumn<Etudiant, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNomE()));
        colNom.setPrefWidth(180);

        TableColumn<Etudiant, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPrenomE()));
        colPrenom.setPrefWidth(180);

        TableColumn<Etudiant, String> colFormation = new TableColumn<>("Formation");
        colFormation.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getFormation().getNomFormation()
        ));
        colFormation.setPrefWidth(180);

        TableColumn<Etudiant, String> colParcours = new TableColumn<>("Parcours");
        colParcours.setCellValueFactory(c -> {
            Parcours p = c.getValue().getParcours();
            return new SimpleStringProperty(p == null ? "" : p.getNomParcours());
        });
        colParcours.setPrefWidth(180);

        table.getColumns().addAll(colNum, colNom, colPrenom, colFormation, colParcours);

        root.setLeft(left);
        root.setCenter(table);
    }

    private void ajouter() {
        String nom = tfNom.getText().trim();
        String prenom = tfPrenom.getText().trim();
        String formationNom = tfFormation.getText().trim();
        String parcoursNom = tfParcours.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || formationNom.isEmpty() || parcoursNom.isEmpty()) {
            lblMsg.setText("Remplir tous les champs");
            return;
        }

        Formation f = new Formation(formationNom);
        Parcours p = new Parcours(parcoursNom, f);
        Etudiant e = new Etudiant(nom, prenom, f, p);

        etudiants.add(e);

        tfNom.clear(); tfPrenom.clear(); tfFormation.clear(); tfParcours.clear();
        lblMsg.setText("Ajouté : " + e.getNumeroEtudiant());
    }

    private void supprimerSelection() {
        Etudiant selection = table.getSelectionModel().getSelectedItem();
        if (selection == null) {
            lblMsg.setText("Sélectionner un étudiant.");
            return;
        }
        etudiants.remove(selection);
        lblMsg.setText("Supprimé : " + selection.getNumeroEtudiant());
    }

    public Parent getRoot() {
        return root;
    }
}

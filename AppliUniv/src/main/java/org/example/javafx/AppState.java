package org.example.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.Etudiant;
import org.example.Formation;
import org.example.Parcours;
import org.example.Ue;

/**
 * <p>Classe qui centralise l'état de l'application</p>
 *
 * <p>Elle regroupe les collections principales manipulées par l'interface JavaFX :</p>
 * <ul>
 *   <li>les {@link Formation}</li>
 *   <li>les {@link Parcours}</li>
 *   <li>les {@link Etudiant}</li>
 *   <li>les {@link Ue}</li>
 * </ul>
 *
 * <p>Chaque collection est un {@link ObservableList} pour permettre aux composants JavaFX
 * (TableView, ListView, ComboBox, etc...) de se mettre à jour automatiquement</p>
 */
public class AppState {

    /** Liste observable des formations présentes dans l'application */
    private final ObservableList<Formation> formations = FXCollections.observableArrayList();

    /** Liste observable des parcours présents dans l'application */
    private final ObservableList<Parcours> parcours = FXCollections.observableArrayList();

    /** Liste observable des étudiants présents dans l'application */
    private final ObservableList<Etudiant> etudiants = FXCollections.observableArrayList();

    /** Liste observable du catalogue global des UE présentes dans l'application */
    private final ObservableList<Ue> ues = FXCollections.observableArrayList();

    /**
     * <p>Retourne la liste des formations</p>
     *
     * @return la liste observable des {@link Formation}
     */
    public ObservableList<Formation> getFormations() { return formations; }

    /**
     * <p>Retourne la liste des parcours</p>
     *
     * @return la liste observable des {@link Parcours}
     */
    public ObservableList<Parcours> getParcours() { return parcours; }

    /**
     * <p>Retourne la liste des étudiants</p>
     *
     * @return la liste observable des {@link Etudiant}
     */
    public ObservableList<Etudiant> getEtudiants() { return etudiants; }

    /**
     * <p>Retourne la liste des UE du catalogue global</p>
     *
     * @return la liste observable des {@link Ue}
     */
    public ObservableList<Ue> getUes() { return ues; }
}
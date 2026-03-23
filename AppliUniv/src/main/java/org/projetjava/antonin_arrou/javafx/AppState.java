package org.projetjava.antonin_arrou.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.projetjava.antonin_arrou.*;
import org.projetjava.antonin_arrou.dao.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * <p>Charge toutes les données depuis la base Oracle dans l'ordre des dépendances FK</p>
     *
     * @throws SQLException en cas d'erreur de connexion ou de requête
     */
    public void chargerDepuisBD() throws SQLException {
        FormationDAO formationDAO = new FormationDAO();
        UeDAO ueDAO = new UeDAO();
        ParcoursDAO parcoursDAO = new ParcoursDAO();
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        InscriptionUeDAO inscriptionUeDAO = new InscriptionUeDAO();

        // 1. Charger les formations
        List<Formation> fs = formationDAO.findAll();
        formations.setAll(fs);
        Map<Integer, Formation> formationsParId = new HashMap<>();
        for (Formation f : fs) formationsParId.put(f.getCodeFormation(), f);

        // 2. Charger les UE + prérequis
        List<Ue> us = ueDAO.findAll();
        ues.setAll(us);
        Map<Integer, Ue> uesParId = new HashMap<>();
        for (Ue u : us) uesParId.put(u.getCodeUe(), u);

        for (Ue u : us) {
            List<Ue> prereqs = ueDAO.findPrerequis(u.getCodeUe(), uesParId);
            for (Ue pre : prereqs) u.ajouterPrerequis(pre);
        }

        // 3. Peupler formation.getUeBase() depuis Formation_Ue
        ueDAO.loadFormationUes(formationsParId, uesParId);

        // 4. Charger les parcours (résout FK Formation)
        List<Parcours> ps = parcoursDAO.findAll(formationsParId);
        parcours.setAll(ps);
        Map<Integer, Parcours> parcoursParId = new HashMap<>();
        for (Parcours p : ps) parcoursParId.put(p.getIdParcours(), p);

        // 5. Peupler parcours.getUeSpe() depuis Parcours_Ue
        ueDAO.loadParcoursUes(parcoursParId, uesParId);

        // 6. Charger les étudiants (résout FK Formation + Parcours)
        List<Etudiant> es = etudiantDAO.findAll(formationsParId, parcoursParId);
        etudiants.setAll(es);

        // 7. Charger les inscriptions UE pour chaque étudiant
        for (Etudiant e : es) {
            List<InscriptionUe> ins = inscriptionUeDAO.infoEtudiant(e.getNumeroEtudiant(), uesParId);
            for (InscriptionUe i : ins) e.ajouterInscription(i);
        }
    }
}
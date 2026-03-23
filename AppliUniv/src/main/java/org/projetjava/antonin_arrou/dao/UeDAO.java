package org.projetjava.antonin_arrou.dao;

import org.projetjava.antonin_arrou.ConnexionBD;
import org.projetjava.antonin_arrou.Formation;
import org.projetjava.antonin_arrou.Parcours;
import org.projetjava.antonin_arrou.Ue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UeDAO {

    /**
     * <p>Cette fonction nous permet d'insérer une nouvelle UE dans la BD</p>
     * <p>Le codeUe généré par Oracle est automatiquement affecté à l'objet</p>
     * @param u l'UE à insérer
     * @throws SQLException
     */
    public void insert(Ue u) throws SQLException {
        String sql = "INSERT INTO Ue (nomUe, ects, ouverture) VALUES (?, ?, ?)";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql, new String[]{"CODEUE"})) {
            ps.setString(1, u.getNomUe());
            ps.setInt(2, u.getEcts());
            ps.setInt(3, u.isOuverture() ? 1 : 0);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) u.setCodeUe(rs.getInt(1));
            }
        }
    }

    /**
     * <p>Cette fonction nous permet de supprimer une UE de la BD</p>
     * <p>On s'en sert quand l'utilisateur supprime une UE depuis l'interface</p>
     * @param codeUe l'identifiant de l'UE à supprimer
     * @throws SQLException
     */
    public void delete(int codeUe) throws SQLException {
        String sql = "DELETE FROM Ue WHERE codeUe = ?";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeUe);
            ps.executeUpdate();
        }
    }

    /**
     * <p>Cette fonction nous permet de récupérer tout les éléments de la table Ue sur la BD</p>
     * <p>On s'en sert pour afficher la liste des UEs</p>
     * @return Une liste d'UEs de type java
     * @throws SQLException
     */
    public List<Ue> findAll() throws SQLException {
        List<Ue> list = new ArrayList<>();
        String sql = "SELECT codeUe, nomUe, ects, ouverture FROM Ue";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ue u = new Ue(rs.getString("nomUe"), rs.getInt("ects"), rs.getInt("ouverture") == 1);
                u.setCodeUe(rs.getInt("codeUe"));
                list.add(u);
            }
        }
        return list;
    }

    /**
     * <p>Cette fonction nous permet d'ajouter un prérequis à une UE dans la BD</p>
     * <p>On s'en sert pour lier deux UEs dans la table Ue_Prerequis</p>
     * @param codeUe l'identifiant de l'UE qui possède le prérequis
     * @param codeUePreRequis l'identifiant de l'UE prérequise
     * @throws SQLException
     */
    public void insertPrerequis(int codeUe, int codeUePreRequis) throws SQLException {
        String sql = "INSERT INTO Ue_Prerequis (codeUe, codeUePreRequis) VALUES (?, ?)";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeUe);
            ps.setInt(2, codeUePreRequis);
            ps.executeUpdate();
        }
    }

    /**
     * <p>Cette fonction nous permet de supprimer tous les prérequis d'une UE dans la BD</p>
     * <p>On s'en sert avant de réinsérer les prérequis mis à jour</p>
     * @param codeUe l'identifiant de l'UE dont on veut supprimer tous les prérequis
     * @throws SQLException
     */
    public void deleteAllPrerequis(int codeUe) throws SQLException {
        String sql = "DELETE FROM Ue_Prerequis WHERE codeUe = ?";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeUe);
            ps.executeUpdate();
        }
    }

    /**
     * <p>Cette fonction nous permet de récupérer la liste des prérequis d'une UE depuis la BD</p>
     * <p>On s'en sert pour savoir quelles UEs doivent être validées avant de s'inscrire</p>
     * @param codeUe l'identifiant de l'UE dont on veut les prérequis
     * @param uesParId map de toutes les UEs indexées par leur id (déjà chargées en mémoire)
     * @return la liste des UEs prérequises
     * @throws SQLException
     */
    public List<Ue> findPrerequis(int codeUe, Map<Integer, Ue> uesParId) throws SQLException {
        List<Ue> list = new ArrayList<>();
        String sql = "SELECT codeUePreRequis FROM Ue_Prerequis WHERE codeUe = ?";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeUe);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ue pre = uesParId.get(rs.getInt("codeUePreRequis"));
                    if (pre != null) list.add(pre);
                }
            }
        }
        return list;
    }

    /**
     * <p>Cette fonction nous permet d'affecter une UE à une formation dans la BD</p>
     * <p>On s'en sert pour lier une UE de base à une formation dans la table Formation_Ue</p>
     * @param codeFormation l'identifiant de la formation
     * @param codeUe l'identifiant de l'UE à affecter
     * @throws SQLException
     */
    public void insertFormationUe(int codeFormation, int codeUe) throws SQLException {
        String sql = "INSERT INTO Formation_Ue (codeFormation, codeUe) VALUES (?, ?)";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeFormation);
            ps.setInt(2, codeUe);
            ps.executeUpdate();
        }
    }

    /**
     * <p>Cette fonction nous permet de retirer une UE d'une formation dans la BD</p>
     * <p>On s'en sert pour supprimer le lien entre une UE et une formation dans Formation_Ue</p>
     * @param codeFormation l'identifiant de la formation
     * @param codeUe l'identifiant de l'UE à retirer
     * @throws SQLException
     */
    public void deleteFormationUe(int codeFormation, int codeUe) throws SQLException {
        String sql = "DELETE FROM Formation_Ue WHERE codeFormation = ? AND codeUe = ?";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeFormation);
            ps.setInt(2, codeUe);
            ps.executeUpdate();
        }
    }

    /**
     * <p>Cette fonction nous permet d'affecter une UE spécialisée à un parcours dans la BD</p>
     * <p>On s'en sert pour lier une UE de spécialisation à un parcours dans la table Parcours_Ue</p>
     * @param codeParcours l'identifiant du parcours
     * @param codeUe l'identifiant de l'UE à affecter
     * @throws SQLException
     */
    public void insertParcoursUe(int codeParcours, int codeUe) throws SQLException {
        String sql = "INSERT INTO Parcours_Ue (codeParcours, codeUe) VALUES (?, ?)";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeParcours);
            ps.setInt(2, codeUe);
            ps.executeUpdate();
        }
    }

    /**
     * <p>Cette fonction nous permet de retirer une UE spécialisée d'un parcours dans la BD</p>
     * <p>On s'en sert pour supprimer le lien entre une UE et un parcours dans Parcours_Ue</p>
     * @param codeParcours l'identifiant du parcours
     * @param codeUe l'identifiant de l'UE à retirer
     * @throws SQLException
     */
    public void deleteParcoursUe(int codeParcours, int codeUe) throws SQLException {
        String sql = "DELETE FROM Parcours_Ue WHERE codeParcours = ? AND codeUe = ?";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeParcours);
            ps.setInt(2, codeUe);
            ps.executeUpdate();
        }
    }

    /**
     * <p>Cette fonction nous permet de charger les UEs de base de chaque formation depuis la BD</p>
     * <p>On s'en sert au démarrage dans AppState pour reconstituer les listes en mémoire</p>
     * @param formationsParId map de toutes les formations indexées par leur id
     * @param uesParId map de toutes les UEs indexées par leur id
     * @throws SQLException
     */
    public void loadFormationUes(Map<Integer, Formation> formationsParId, Map<Integer, Ue> uesParId) throws SQLException {
        String sql = "SELECT codeFormation, codeUe FROM Formation_Ue";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Formation f = formationsParId.get(rs.getInt("codeFormation"));
                Ue u = uesParId.get(rs.getInt("codeUe"));
                if (f != null && u != null) f.getUeBase().add(u);
            }
        }
    }

    /**
     * <p>Cette fonction nous permet de charger les UEs spécialisées de chaque parcours depuis la BD</p>
     * <p>On s'en sert au démarrage dans AppState pour reconstituer les listes en mémoire</p>
     * @param parcoursParId map de tous les parcours indexés par leur id
     * @param uesParId map de toutes les UEs indexées par leur id
     * @throws SQLException
     */
    public void loadParcoursUes(Map<Integer, Parcours> parcoursParId, Map<Integer, Ue> uesParId) throws SQLException {
        String sql = "SELECT codeParcours, codeUe FROM Parcours_Ue";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Parcours p = parcoursParId.get(rs.getInt("codeParcours"));
                Ue u = uesParId.get(rs.getInt("codeUe"));
                if (p != null && u != null) p.getUeSpe().add(u);
            }
        }
    }
}

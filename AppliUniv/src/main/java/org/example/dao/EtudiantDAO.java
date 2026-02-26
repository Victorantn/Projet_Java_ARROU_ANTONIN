package org.example.dao;

import org.example.ConnexionBD;
import org.example.Etudiant;
import org.example.Formation;
import org.example.Parcours;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EtudiantDAO {

    /**
     * <p>Cette fonction nous permet d'insérer un nouvel étudiant dans la BD</p>
     * <p>Le numeroEtudiant généré est affecté à l'objet</p>
     * @param e l'étudiant à insérer
     * @throws SQLException
     */
    public void insert(Etudiant e) throws SQLException {
        String sql = "INSERT INTO Etudiant (nomE, prenomE, codeFormation, codeParcours, totalECTS) VALUES (?, ?, ?, ?, ?)";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql, new String[]{"NUMEROETUDIANT"})) {
            ps.setString(1, e.getNomE());
            ps.setString(2, e.getPrenomE());
            ps.setInt(3, e.getFormation().getCodeFormation());
            ps.setInt(4, e.getParcours().getIdParcours());
            ps.setInt(5, e.getTotalECTS());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) e.setNumeroEtudiant(rs.getInt(1));
            }
        }
    }

    /**
     * <p>Cette fonction nous permet de supprimer un étudiant de la BD</p>
     * <p>On s'en sert quand l'utilisateur supprime un étudiant depuis l'interface</p>
     * @param numeroEtudiant l'identifiant de l'étudiant à supprimer
     * @throws SQLException
     */
    public void delete(int numeroEtudiant) throws SQLException {
        String sql = "DELETE FROM Etudiant WHERE numeroEtudiant = ?";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numeroEtudiant);
            ps.executeUpdate();
        }
    }

    /**
     * <p>Cette fonction nous permet de récupérer tout les étudiants sur la BD</p>
     * <p>On s'en sert pour afficher la liste des étudiants dans la view Etudiants</p>
     * @return Une liste d'étudiants de type java
     * @throws SQLException
     */
    public List<Etudiant> findAll(Map<Integer, Formation> formationsParId, Map<Integer, Parcours> parcoursParId) throws SQLException {
        List<Etudiant> list = new ArrayList<>();
        String sql = "SELECT numeroEtudiant, nomE, prenomE, codeFormation, codeParcours FROM Etudiant";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Formation f = formationsParId.get(rs.getInt("codeFormation"));
                Parcours p = parcoursParId.get(rs.getInt("codeParcours"));
                if (f == null || p == null) continue;
                Etudiant e = new Etudiant(rs.getString("nomE"), rs.getString("prenomE"), f, p);
                e.setNumeroEtudiant(rs.getInt("numeroEtudiant"));
                list.add(e);
            }
        }
        return list;
    }

    /**
     * <p>Cette fonction nous permet de mettre à jour le total d'ECTS d'un étudiant dans la BD</p>
     * <p>On s'en sert quand le statut d'une inscription passe à VALIDE</p>
     * @param numeroEtudiant l'identifiant de l'étudiant à mettre à jour
     * @param totalECTS le nouveau total d'ECTS à enregistrer
     * @throws SQLException
     */
    public void updateTotalECTS(int numeroEtudiant, int totalECTS) throws SQLException {
        String sql = "UPDATE Etudiant SET totalECTS = ? WHERE numeroEtudiant = ?";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, totalECTS);
            ps.setInt(2, numeroEtudiant);
            ps.executeUpdate();
        }
    }
}

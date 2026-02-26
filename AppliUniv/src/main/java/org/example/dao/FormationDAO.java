package org.example.dao;

import org.example.ConnexionBD;
import org.example.Formation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormationDAO {

    /**
     * <p>Cette fonction nous permet d'insérer une nouvelle formation dans la BD</p>
     * <p>Le codeFormation généré par Oracle est automatiquement affecté à l'objet</p>
     * @param f la formation à insérer
     * @throws SQLException
     */
    public void insert(Formation f) throws SQLException {
        String sql = "INSERT INTO Formation (nomFormation) VALUES (?)";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql, new String[]{"CODEFORMATION"})) {
            ps.setString(1, f.getNomFormation());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) f.setCodeFormation(rs.getInt(1));
            }
        }
    }

    /**
     * <p>Cette fonction nous permet de supprimer une formation de la BD</p>
     * <p>On s'en sert quand l'utilisateur supprime une formation depuis l'interface</p>
     * @param codeFormation l'identifiant de la formation à supprimer
     * @throws SQLException
     */
    public void delete(int codeFormation) throws SQLException {
        String sql = "DELETE FROM Formation WHERE codeFormation = ?";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeFormation);
            ps.executeUpdate();
        }
    }

    /**
     * <p>Cette fonction nous permet de récupérer tout les éléments de la table Formation sur la BD</p>
     * <p>On s'en sert pour afficher la liste des formations</p>
     * @return Une liste de formation de type java
     * @throws SQLException
     */
    public List<Formation> findAll() throws SQLException {
        List<Formation> list = new ArrayList<>();
        String sql = "SELECT codeFormation, nomFormation FROM Formation";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Formation f = new Formation(rs.getString("nomFormation"));
                f.setCodeFormation(rs.getInt("codeFormation"));
                list.add(f);
            }
        }
        return list;
    }
}

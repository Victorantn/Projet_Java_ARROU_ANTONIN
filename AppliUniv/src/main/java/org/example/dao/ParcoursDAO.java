package org.example.dao;

import org.example.ConnexionBD;
import org.example.Formation;
import org.example.Parcours;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParcoursDAO {

    /**
     * <p>Cette fonction nous permet d'insérer un nouveau parcours dans la BD</p>
     * <p>Le codeParcours généré par Oracle est automatiquement affecté à l'objet</p>
     * @param p le parcours à insérer
     * @throws SQLException
     */
    public void insert(Parcours p) throws SQLException {
        String sql = "INSERT INTO Parcours (nomParcours, codeFormation) VALUES (?, ?)";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql, new String[]{"CODEPARCOURS"})) {
            ps.setString(1, p.getNomParcours());
            ps.setInt(2, p.getFormation().getCodeFormation());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setCodeParcours(rs.getInt(1));
            }
        }
    }

    /**
     * <p>Cette fonction nous permet de supprimer un parcours de la BD</p>
     * <p>On s'en sert quand l'utilisateur supprime un parcours depuis l'interface</p>
     * @param codeParcours l'identifiant du parcours à supprimer
     * @throws SQLException
     */
    public void delete(int codeParcours) throws SQLException {
        String sql = "DELETE FROM Parcours WHERE codeParcours = ?";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeParcours);
            ps.executeUpdate();
        }
    }

    /**
     * <p>Cette fonction nous permet de récupérer tout les éléments de la table Parcours sur la BD</p>
     * <p>On s'en sert pour afficher la liste des parcours</p>
     * @return Une liste de parcours de type java
     * @throws SQLException
     */
    public List<Parcours> findAll(Map<Integer, Formation> formationsParId) throws SQLException {
        List<Parcours> list = new ArrayList<>();
        String sql = "SELECT codeParcours, nomParcours, codeFormation FROM Parcours";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Formation f = formationsParId.get(rs.getInt("codeFormation"));
                if (f == null) continue;
                Parcours p = new Parcours(rs.getString("nomParcours"), f);
                p.setCodeParcours(rs.getInt("codeParcours"));
                list.add(p);
            }
        }
        return list;
    }
}

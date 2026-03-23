package org.projetjava.antonin_arrou.dao;

import org.projetjava.antonin_arrou.ConnexionBD;
import org.projetjava.antonin_arrou.InscriptionUe;
import org.projetjava.antonin_arrou.Semestre;
import org.projetjava.antonin_arrou.Statut;
import org.projetjava.antonin_arrou.Ue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InscriptionUeDAO {

    /**
     * <p>Cette fonction nous permet d'insérer une nouvelle inscription UE dans la BD</p>
     * <p>Le codeInscrip généré est affecté à l'objet</p>
     * @param ins l'inscription à insérer
     * @param numeroEtudiant l'identifiant de l'étudiant concerné
     * @throws SQLException
     */
    public void insertInscription(InscriptionUe ins, int numeroEtudiant) throws SQLException {
        String sql = "INSERT INTO InscriptionUe (numeroEtudiant, codeUe, anneeUniversitaire, semestre, statut) VALUES (?, ?, ?, ?, ?)";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql, new String[]{"CODEINSCRIP"})) {
            ps.setInt(1, numeroEtudiant);
            ps.setInt(2, ins.getUe().getCodeUe());
            ps.setString(3, ins.getAnneeUniversitaire());
            ps.setString(4, ins.getSemestre().name());
            ps.setString(5, ins.getStatut().name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) ins.setCodeInscrip(rs.getInt(1));
            }
        }
    }

    /**
     * <p>Cette fonction nous permet de supprimer une inscription UE de la BD</p>
     * <p>On s'en sert quand l'utilisateur désincrit un étudiant d'une UE</p>
     * @param codeInscrip l'identifiant de l'inscription à supprimer
     * @throws SQLException
     */
    public void deleteInscription(int codeInscrip) throws SQLException {
        String sql = "DELETE FROM InscriptionUe WHERE codeInscrip = ?";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codeInscrip);
            ps.executeUpdate();
        }
    }

    /**
     * <p>Cette fonction nous permet de mettre à jour le statut d'une inscription UE dans la BD</p>
     * <p>On s'en sert pour passer une inscription de ENCOURS à VALIDE ou ECHOUE</p>
     * @param codeInscrip l'identifiant de l'inscription à modifier
     * @param statut le nouveau statut à enregistrer
     * @throws SQLException
     */
    public void majStatut(int codeInscrip, Statut statut) throws SQLException {
        String sql = "UPDATE InscriptionUe SET statut = ? WHERE codeInscrip = ?";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, statut.name());
            ps.setInt(2, codeInscrip);
            ps.executeUpdate();
        }
    }

    /**
     * <p>Cette fonction nous permet de récupérer les informations d'UN seul étudiant sur la BD</p>
     * <p>On s'en sert pour principalement pour savoir à quels UEs il/elle est inscrit</p>
     * @param numeroEtudiant
     * @param uesParId
     * @return
     * @throws SQLException
     */
    public List<InscriptionUe> infoEtudiant(int numeroEtudiant, Map<Integer, Ue> uesParId) throws SQLException {
        List<InscriptionUe> list = new ArrayList<>();
        String sql = "SELECT codeInscrip, codeUe, anneeUniversitaire, semestre, statut FROM InscriptionUe WHERE numeroEtudiant = ?";
        Connection con = ConnexionBD.getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numeroEtudiant);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ue u = uesParId.get(rs.getInt("codeUe"));
                    if (u == null) continue;
                    InscriptionUe ins = new InscriptionUe(u, rs.getString("anneeUniversitaire"),
                            Semestre.valueOf(rs.getString("semestre")));
                    ins.setCodeInscrip(rs.getInt("codeInscrip"));
                    ins.setStatut(Statut.valueOf(rs.getString("statut")));
                    list.add(ins);
                }
            }
        }
        return list;
    }
}

package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
    private static final String url = "jdbc:oracle:thin:@//telline.univ-tlse3.fr:1521/etupre";
    private static final String usr = "ntv4762a";
    private static final String pdm = "PaulSabatier31$O";

    private static Connection con;

    /**
     * <p>Fonction qui nous sert à récupérer la connexion unique à la BD</p>
     * <p>Si la connexion n'existe pas encore ou a été coupée, elle est rétablie automatiquement</p>
     * @return La connexion active à la BD
     * @throws SQLException C'est l'exception qui nous renvoie le cas d'erreur de connection
     */
    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(url, usr, pdm);
        }
        return con;
    }
}

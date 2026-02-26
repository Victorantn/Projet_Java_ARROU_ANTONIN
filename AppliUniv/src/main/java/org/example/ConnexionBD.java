package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
    private static final String url = "jdbc:oracle:thin:@//telline.univ-tlse3.fr:1521/etupre";
    private static final String usr = "ntv4762a";
    private static final String pdm = "PaulSabatier31$O";

    /**
     * <p>Fonction qui nous sert à se connecter sur la BD</p>
     * @return Renvoie la connection à la BD si echec renvoie une exception
     * @throws SQLException C'est l'exception qui nous renvoie le cas d'erreur de connection
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, usr, pdm);
    }
}

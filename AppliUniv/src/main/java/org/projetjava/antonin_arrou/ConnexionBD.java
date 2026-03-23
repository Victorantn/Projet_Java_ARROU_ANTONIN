package org.projetjava.antonin_arrou;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnexionBD {
    private static final String url;
    private static final String usr;
    private static final String pdm;
    private static Connection con;

    static {
        Properties props = new Properties();
        try (InputStream is = ConnexionBD.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) {
                throw new RuntimeException("Fichier db.properties introuvable dans les ressources");
            }
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture db.properties", e);
        }
        url = props.getProperty("db.url");
        usr = props.getProperty("db.user");
        pdm = props.getProperty("db.password");
    }

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

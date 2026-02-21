package org.example.javafx;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.example.Ue;

import java.util.ArrayList;

/**
 * <p>Boîte de dialogue permettant de gérer les UE prérequis d'une UE cible</p>
 *
 * <p>La boîte affiche une liste multi-sélection de UE candidates afin de définir
 * la liste des prérequis de l'UE cible</p>
 *
 * <p>Règles appliquées :</p>
 * <ul>
 *   <li>l'UE cible ne peut pas être sélectionnée comme son propre prérequis</li>
 *   <li>les prérequis déjà présents sont pré-sélectionnés à l'ouverture</li>
 * </ul>
 */
public class PrereqDial {

    /**
     * <p>Affiche la boîte de dialogue de sélection des prérequis</p>
     *
     * <p>Si l'utilisateur valide, la liste des prérequis de {@code target} est remplacée
     * par la sélection courante dans la liste</p>
     *
     * @param target UE dont on veut modifier la liste des prérequis
     * @param pool liste des UE disponibles comme prérequis
     */
    public static void show(Ue target, ObservableList<Ue> pool) {
        Dialog<Void> d = new Dialog<>();
        d.setTitle("Prérequis des UE" + target.getCodeUe());
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        ListView<Ue> lv = new ListView<>(pool);
        lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        lv.setCellFactory(v -> new ListCell<>() {
            @Override protected void updateItem(Ue item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setDisable(false); return; }
                setText("#" + item.getCodeUe() + " - " + item.getNomUe());
                setDisable(item.getCodeUe() == target.getCodeUe());
            }
        });

        for (Ue pre : target.getUePreRequis()) {
            for (Ue cand : pool) {
                if (cand.getCodeUe() == pre.getCodeUe()) lv.getSelectionModel().select(cand);
            }
        }

        BorderPane pane = new BorderPane(lv);
        pane.setPadding(new Insets(10));
        d.getDialogPane().setContent(pane);

        d.setResultConverter(bt -> {
            if (bt != ButtonType.OK) return null;

            ArrayList<Ue> chosen = new ArrayList<>(lv.getSelectionModel().getSelectedItems());
            target.getUePreRequis().clear();
            target.getUePreRequis().addAll(chosen);

            target.getUePreRequis().removeIf(u -> u.getCodeUe() == target.getCodeUe());
            return null;
        });

        d.showAndWait();
    }
}
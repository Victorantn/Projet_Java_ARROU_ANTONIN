package org.example.javafx;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.Ue;

/**
 * <p>Boîte de dialogue permettant de créer une nouvelle UE</p>
 *
 * <p>L'utilisateur saisit :</p>
 * <ul>
 *   <li>le nom de l'UE</li>
 *   <li>le nombre d'ECTS</li>
 *   <li>si l'UE est une UE d'ouverture</li>
 * </ul>
 *
 * <p>Règles appliquées :</p>
 * <ul>
 *   <li>le nom est obligatoire</li>
 *   <li>le nombre d'ECTS doit être 3 ou 6</li>
 * </ul>
 */
public class UeDial {

    /**
     * <p>Affiche une boîte de dialogue de création d'UE</p>
     *
     * <p>Si l'utilisateur valide, une {@link Ue} est retournée</p>
     *
     * <p>Si l'utilisateur annule, null est retourné</p>
     *
     * @return l'UE créée ou null si annulation
     */
    public static Ue ask() {
        Dialog<Ue> d = new Dialog<>();
        d.setTitle("Créer UE");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField tfNom = new TextField();
        Spinner<Integer> spEcts = new Spinner<>(1, 30, 6);
        CheckBox cbOuverture = new CheckBox("UE d'ouverture");
        Label err = new Label();
        err.setStyle("-fx-text-fill: red;");

        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(10));

        g.addRow(0, new Label("Nom UE"), tfNom);
        g.addRow(1, new Label("ECTS (3 ou 6)"), spEcts);
        g.add(cbOuverture, 1, 2);
        g.add(err, 1, 3);

        d.getDialogPane().setContent(g);

        Button okBtn = (Button) d.getDialogPane().lookupButton(ButtonType.OK);
        okBtn.addEventFilter(javafx.event.ActionEvent.ACTION, ev -> {
            String nom = tfNom.getText().trim();
            int ects = spEcts.getValue();

            if (nom.isEmpty()) {
                err.setText("Nom obligatoire");
                ev.consume();
                return;
            }
            if (!(ects == 3 || ects == 6)) {
                err.setText("Nombre d'ECTS doit être 3 ou 6");
                ev.consume();
            }
        });

        d.setResultConverter(bt -> {
            if (bt != ButtonType.OK) return null;
            return new Ue(tfNom.getText().trim(), spEcts.getValue(), cbOuverture.isSelected());
        });

        return d.showAndWait().orElse(null);
    }
}
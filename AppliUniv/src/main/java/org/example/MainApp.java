package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        MainView view = new MainView();

        Scene scene = new Scene(view.getRoot(), 900, 550);
        stage.setTitle("Suivi étudiants");
        stage.setScene(scene);
        stage.show();
    }
}

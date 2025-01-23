package org.ims;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("System");
        stage.setMinWidth(1100);
        stage.setMinHeight(750);
        stage.setScene(scene);
        stage.show();
    }
}

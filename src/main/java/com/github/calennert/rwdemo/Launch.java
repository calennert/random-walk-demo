package com.github.calennert.rwdemo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Launcher for random walk demo app.
 *
 * @author calennert (Chris Lennert)
 */
public final class Launch extends Application {

    /**
     * Main entry point for application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));

            Scene scene = new Scene(root);
            primaryStage.setTitle("Random Walk Demo");
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
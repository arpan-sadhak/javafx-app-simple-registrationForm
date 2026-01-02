package org.example.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/app/view/register.fxml")
        );



        Scene scene = new Scene(loader.load());

        scene.getStylesheets().add(
                getClass().getResource("/org/example/app/css/style.css").toExternalForm()
        );


        stage.setTitle("Professional JavaFX App");
        stage.resizableProperty().setValue(false);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}

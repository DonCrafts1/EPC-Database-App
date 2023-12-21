package com.epc.environmentapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.stage.StageStyle;

public class App extends Application {
    
    private static Scene scene;
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    } //simplifies the loading  of fxml, no need to type .fxml everytime
    
    @Override
    
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED); //UNDECOREATED makes it so there is no buttons on top, looks cleaner
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml)); // Switching of FXML (i.e. pages)
    }
}
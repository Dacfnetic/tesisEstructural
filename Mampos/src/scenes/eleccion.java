package scenes;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import mampos.BDMamposSQLITE;
import mampos.SQLITE;

public class eleccion extends Application {

    public static void main(String[] args) {
        BDMamposSQLITE bd = new BDMamposSQLITE();
        bd.crearTabla();
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception { 
        
        Parent root = FXMLLoader.load(getClass().getResource("B_ModeChooser.fxml"));        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mampos");
             
        primaryStage.show();
    }   
}
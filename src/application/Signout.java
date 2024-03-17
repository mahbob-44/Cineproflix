package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Signout {
    public Signout(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/application/structure/login.fxml"));
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            Image image= new Image(getClass().getResourceAsStream("/application/styles/logo.jpg"));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().add(image);
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }
}

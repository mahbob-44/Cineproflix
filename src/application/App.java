package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application{
    private double x=0;
    private double y=0;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage){
        try{
            Parent root=FXMLLoader.load(getClass().getResource("/application/structure/login.fxml"));
            Scene scene=new Scene(root);
            primaryStage.setScene(scene);
            Image icon= new Image(getClass().getResourceAsStream("/application/styles/logo.jpg"));
            root.setOnMousePressed((MouseEvent event)->{
                x=event.getSceneX();
                y=event.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent event)->{
                primaryStage.setX(event.getScreenX()-x);
                primaryStage.setY(event.getScreenY()-y);
            });
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.getIcons().add(icon);
            primaryStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }
}

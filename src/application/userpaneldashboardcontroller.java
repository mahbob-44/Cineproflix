package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class userpaneldashboardcontroller implements Initializable{
    public static String winner_of_game="";
    public static String usrn;
    public static String name="";
    public static String email="";

    // Tools for database
    private Connection connect;
    private PreparedStatement prepare;
    private PreparedStatement prepare1;
    private Statement statement;
    private ResultSet result;
    
    @FXML
    private AnchorPane user_dashboard;

    @FXML
    private AnchorPane discount_choosing_page;

    @FXML
    private Button available_movies;

    @FXML
    private TextField buyticket_genre;

    @FXML
    private TextField buyticket_title;

    @FXML
    private Button dashboard;

    @FXML
    private TableColumn<movie, Integer> available_seats;

    @FXML
    private TableColumn<movie, String> movie_price;

    @FXML
    private TableColumn<movie, String> movie_date;

    @FXML
    private TableColumn<movie, String> movie_duration;

    @FXML
    private TableColumn<movie, String> movie_genre;

    @FXML
    private TableColumn<movie, String> movie_title;

    @FXML
    private TableView<movie> movielist;

    
    @FXML
    private Button no_btn;

    @FXML
    private Button procced_btn;

    @FXML
    private Button signout;

    @FXML
    private Label username;

    @FXML
    private ImageView image_view;

    @FXML
    void buy_ticekt(ActionEvent event) {
        String sql="select title and genre from movie where title=? and genre=?";
        connect=sqliteConnection.connector();
        Alert alert;
        try {
            prepare=connect.prepareStatement(sql);
            prepare.setString(1, buyticket_title.getText());
            prepare.setString(2, buyticket_genre.getText());
            
            result=prepare.executeQuery();
            if(result.next()){
                user_dashboard.setVisible(false);
                discount_choosing_page.setVisible(true);
                movie.m_name=buyticket_title.getText();
                movie.m_genre=buyticket_genre.getText();
            }
            else{
                alert=new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error Message!");
                alert.setContentText("Wrong movie title or genre.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    @FXML
    void buy_clear(ActionEvent event) {
        buyticket_title.setText("");
        buyticket_genre.setText("");
        image_view.setImage(null);
    }

    private ObservableList<movie> listmovies(){

        ObservableList<movie> listdata= FXCollections.observableArrayList();
        String sql="select * from movie";
        connect=sqliteConnection.connector();
        try {
            prepare=connect.prepareStatement(sql);
            result=prepare.executeQuery();
            movie mov;

            while (result.next()) {
                mov=new movie(result.getString("title"),result.getString("genre"),result.getString("duration"),result.getString("date"), result.getFloat("price"),result.getInt("seats"),result.getInt("hall_no"),result.getString("image"));
                listdata.add(mov);
            }
            return listdata;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ObservableList<movie> listadded;

    public void show_list(){
        listadded=listmovies();
        movie_title.setCellValueFactory(new PropertyValueFactory<>("_name"));
        movie_genre.setCellValueFactory(new PropertyValueFactory<>("_genre"));
        movie_duration.setCellValueFactory(new PropertyValueFactory<>("_duration"));
        movie_date.setCellValueFactory(new PropertyValueFactory<>("_date"));
        movie_price.setCellValueFactory(new PropertyValueFactory<>("_price"));
        available_seats.setCellValueFactory(new PropertyValueFactory<>("_seats"));

        movielist.setItems(listadded);
    }

    @FXML
    void getItem(MouseEvent event) {

        movie movd=movielist.getSelectionModel().getSelectedItem();
        int num=movielist.getSelectionModel().getSelectedIndex();
        if(num-1<-1){
            return;
        }
        buyticket_title.setText(movd.get_name());
        buyticket_genre.setText(movd.get_genre());

        Image img;
        String url="file:"+movd.get_image();

        img=new Image(url,200,250,false,true);
        image_view.setImage(img);
    }
    @FXML
    void dont_go_to_game(ActionEvent event) {
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/application/structure/ticket.fxml"));
            Scene scene=new Scene(root);
            Stage primarStage=new Stage();
            primarStage.setScene(scene);
            no_btn.getScene().getWindow().hide();
            Image icon= new Image(getClass().getResourceAsStream("/application/styles/logo.jpg"));
            primarStage.getIcons().add(icon);
            primarStage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    @FXML
    void proceed_to_game(ActionEvent event) {
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/application/structure/tictactoe.fxml"));
            Scene scene=new Scene(root);
            Stage primaryStage=new Stage();
            primaryStage.setScene(scene);
            procced_btn.getScene().getWindow().hide();
            Image icon= new Image(getClass().getResourceAsStream("/application/styles/logo.jpg"));
            primaryStage.getIcons().add(icon);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    void nav_bar_buy_ticket_btn(ActionEvent event) {
        user_dashboard.setVisible(true);
        discount_choosing_page.setVisible(false);

    }

    @FXML
    void user_sign_out(ActionEvent event) {
        signout.getScene().getWindow().hide();
        Signout so=new Signout();
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){
        show_list();
        username.setText(usrn);
        user_dashboard.setVisible(true);
        discount_choosing_page.setVisible(false);
        
    }



}

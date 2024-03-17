package application;

import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class dashboardcontroler implements Initializable{
    public static int is_admin;
    public static String usrn;

    private LoginModel loginmodel=new LoginModel();
    @FXML
    private Label username;
    @FXML
    private TextField addmove_genre;

    @FXML
    private Button addmovie_clear;

    @FXML
    private DatePicker addmovie_date;

    @FXML
    private TextField addmovie_duration;

    @FXML
    private Button addmovie_insert;

    @FXML
    private TextField addmovie_price;

    @FXML
    private TextField addmovie_title;

    @FXML
    private Button available_movies;

    @FXML
    private Button dashboard;

    @FXML
    private Button deletemovie;

    @FXML
    private TextField deletemovie_genre;

    @FXML
    private TextField deletemovie_title;

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
    private Button signout;

    @FXML
    private TextField hall_no;

    @FXML
    private TextField seats;

    

    // Tools for database
    private Connection connect;
    private PreparedStatement prepare;
    private PreparedStatement prepare1;

    private Statement statement;
    private ResultSet result;

    
    private ObservableList<movie> listmovies(){

        ObservableList<movie> listdata= FXCollections.observableArrayList();
        String sql="select * from movie";
        connect=sqliteConnection.connector();
        try {
            prepare=connect.prepareStatement(sql);
            result=prepare.executeQuery();
            movie mov;

            while (result.next()) {
                mov=new movie(result.getString("title"),result.getString("genre"),result.getString("duration"),result.getString("date"), result.getFloat("price"),result.getInt("seats"));
                listdata.add(mov);
            }
            return listdata;

        } catch (Exception e) {
            // TODO: handle exception
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

        movielist.setItems(listadded);
    }

    @FXML
    public void insert(ActionEvent event){
        
        String sql="insert into movie (title,genre,duration,date,price,seats,hall_no) values(?,?,?,?,?,?,?)";
        String sql1="select title from movie where title=?";

        connect=sqliteConnection.connector();
        Alert alert;

        try {
            prepare1=connect.prepareStatement(sql1);
            prepare1.setString(1, addmovie_title.getText());
            result=prepare1.executeQuery();
            prepare=connect.prepareStatement(sql);
            prepare.setString(1, addmovie_title.getText());
            prepare.setString(2, addmove_genre.getText());
            prepare.setString(3, addmovie_duration.getText());
            prepare.setString(4, addmovie_date.getEditor().getText());
            prepare.setFloat(5, Float.parseFloat(addmovie_price.getText()));
            prepare.setInt(6, Integer.parseInt(seats.getText()));
            prepare.setInt(7, Integer.parseInt(hall_no.getText()));

            if(result.next()){
                alert=new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error Message!");
                alert.setContentText("The movie "+addmovie_title.getText()+" is alreay exist.");
                alert.showAndWait();
            }
            else{
                prepare.execute();
                alert=new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Information Message!");
                alert.setContentText("The movie "+addmovie_title.getText()+" is added Successfully");
                show_list();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void add_clear(ActionEvent event) {
        addmovie_title.setText("");
        addmove_genre.setText("");
        addmovie_date.setValue(null);;
        addmovie_duration.setText("");
        addmovie_price.setText("");
        seats.setText("");
        hall_no.setText("");
    }

    @FXML
    void admin_sign_out(ActionEvent event) {
        signout.getScene().getWindow().hide();
        Signout so= new Signout();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        show_list();
        addmovie_price.setText("");
        username.setText(usrn);
        System.out.println(usrn);
    }
    @FXML
    void delete_movie(ActionEvent event) {
        String sql="delete from movie where title=? and genre=?";
        connect=sqliteConnection.connector();
        Alert alert;
        try {
            prepare=connect.prepareStatement(sql);
            prepare.setString(1, deletemovie_title.getText());
            prepare.setString(2, deletemovie_genre.getText());
            
            int rowsaffected=prepare.executeUpdate();
            if(rowsaffected>0){
                alert=new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Information message.");
                alert.setContentText("The movie "+deletemovie_title.getText()+" is deletede successfully.");
                alert.showAndWait();
                show_list();
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

    
}

package application;

import java.beans.Statement;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.mysql.cj.xdevapi.PreparableStatement;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class ticketcontroller implements Initializable{

    @FXML
    private Label Name;

    @FXML
    private Label date;

    @FXML
    private Label e_mail;

    @FXML
    private Label hall_number;

    @FXML
    private Label movie_name;

    @FXML
    private Label seat_number;

    @FXML
    private Label total_price;

    

    // Tools for database
    private Connection connect;
    private PreparedStatement prepare;
    private PreparedStatement prepare2;
    private ResultSet result;
    private ResultSet result2;
    private Statement statement;
    private String generate_seat_number(int st){
        int num=65+st/10;
        st=48+st%10+1;
        char ch=(char)num;
        char chh=(char)st;
        String str=String.valueOf(ch)+String.valueOf(chh);
        return str;
    }
    @Override 
    public void initialize(URL location, ResourceBundle resourceBundle){
        if(controller.winner.equals("X")){
            String sql="select * from user where username=?";
            String sql2="select * from movie where title=? and genre=? ";

            connect=sqliteConnection.connector();
            try {
                prepare=connect.prepareStatement(sql);
                prepare2=connect.prepareStatement(sql2);
                
                prepare.setString(1, userpaneldashboardcontroller.usrn);
                System.out.println(userpaneldashboardcontroller.usrn);
                System.out.println(movie.m_name);
                prepare2.setString(1, movie.m_name);
                prepare2.setString(2, movie.m_genre);

                Alert alert;
                result=prepare.executeQuery();
                result2=prepare2.executeQuery();
                if(result.next()){
                    System.out.println(result.getString("name"));
                    Name.setText(result.getString("name"));
                    e_mail.setText(result.getString("email"));
                }
                else{
                    alert=new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error Message!");
                    alert.setContentText("Wrong input!");
                }

                if(result2.next()){
                    date.setText(result2.getString("date"));
                    hall_number.setText(String.valueOf(result2.getInt("hall_no")));
                    movie_name.setText(result2.getString("title"));
                    total_price.setText(String.valueOf(Float.parseFloat(result2.getString("price"))-50));
                    int seat=Integer.parseInt(result2.getString("seats"));
                    String s_num=generate_seat_number(seat);
                    seat_number.setText(s_num);
                    decrese(Integer.parseInt(result2.getString("seats"))-1);
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        else{
            String sql="select * from user where username=?";
            String sql2="select * from movie where title=? and genre=? ";

            connect=sqliteConnection.connector();
            try {
                prepare=connect.prepareStatement(sql);
                prepare2=connect.prepareStatement(sql2);

                prepare.setString(1, userpaneldashboardcontroller.usrn);

                prepare2.setString(1, movie.m_name);
                prepare2.setString(2, movie.m_genre);

                Alert alert;
                result=prepare.executeQuery();
                result2=prepare2.executeQuery();
                if(result.next()){
                    Name.setText(result.getString("name"));
                    e_mail.setText(result.getString("email"));
                }
                else{
                    alert=new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error Message!");
                    alert.setContentText("Wrong input!");
                }

                if(result2.next()){
                    date.setText(result2.getString("date"));
                    hall_number.setText(String.valueOf(result2.getInt("hall_no")));
                    movie_name.setText(result2.getString("title"));
                    total_price.setText(String.valueOf(Float.parseFloat(result2.getString("price"))));
                    int seat=Integer.parseInt(result2.getString("seats"));
                    String s_num=generate_seat_number(seat);
                    seat_number.setText(s_num);
                    decrese(Integer.parseInt(result2.getString("seats"))-1);
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    
    public void decrese(int seat){
        if(seat>=0){
            String sql="UPDATE movie set seats=? where title=?";
            connect=sqliteConnection.connector();
            try {
                prepare=connect.prepareStatement(sql);
                prepare.setString(1, String.valueOf(seat));
                prepare.setString(2, movie.m_name);
                prepare.executeUpdate();
                if(seat==0){
                    delete_movie();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        else{
            delete_movie();
        }
    }

    public void delete_movie(){
        String sql="delete from movie where title=?";
        connect=sqliteConnection.connector();
        try {
            prepare=connect.prepareStatement(sql);
            prepare.setString(1, movie.m_name);
            prepare.execute();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


}
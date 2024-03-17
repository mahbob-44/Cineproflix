package application;

import java.beans.Statement;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class maincontroller implements Initializable{
    private boolean signin_opened=true;
    private boolean signup_opened=false;
    private LoginModel loginmodel=new LoginModel();
    
    @FXML
    private Label isconnected;
    

    @FXML
    private AnchorPane Login_form;

    @FXML
    private Label Login_label;

    @FXML
    private PasswordField Login_password;

    @FXML
    private TextField Login_username;

    @FXML
    private Button login_btn;

    @FXML
    private TextField Signup_email;

    @FXML
    private AnchorPane Signup_form;

    @FXML
    private Label Signup_label;

    @FXML
    private PasswordField Signup_password;

    @FXML
    private TextField Signup_username;

    @FXML
    private TextField age;

    @FXML
    private AnchorPane mainpage;

    @FXML
    private TextField name;

    @FXML
    private Label status;

    @FXML
    private Label status1;

    @FXML
    void Already_have_account(ActionEvent event) {
        Signup_form.setVisible(false);
        Login_form.setVisible(true);
        signin_opened=true;
        signup_opened=false;
    }

    @FXML
    void Create_account(ActionEvent event) {
        Login_form.setVisible(false);
        Signup_form.setVisible(true);
        signin_opened=false;
        signup_opened=true;
    }
//  Tools for database
    private Connection connect;
    private PreparedStatement prepare;
    private PreparedStatement prepare2;
    private Statement statement;
    private ResultSet result;

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    boolean validate_email(){
        Pattern pattern=Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher match=pattern.matcher(Signup_email.getText());
        if(match.find()&& match.group().equals(Signup_email.getText())){
            return true;
        }
        else{
            return false;
        }
    }
    
    @FXML
    void Signup(ActionEvent event) {
        String sql="insert into User (name,age,email,username,password) values(?,?,?,?,?)";
        String sql2="select username from User where username=?";
        connect=sqliteConnection.connector();

        try {
            prepare=connect.prepareStatement(sql);
            prepare2=connect.prepareStatement(sql2);
            prepare2.setString(1, Signup_username.getText());
            result=prepare2.executeQuery();
            prepare.setString(1, name.getText());
            prepare.setLong(2, Integer.parseInt(age.getText()));
            prepare.setString(3, Signup_email.getText());
            prepare.setString(4, Signup_username.getText());
            prepare.setString(5, Signup_password.getText());


            if(name.getText().isEmpty()||age.getText().isEmpty()||Signup_email.getText().isEmpty()||Signup_username.getText().isEmpty()||Signup_password.getText().isEmpty()){
                Alert alert=new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Fill the blank fields properly");
                alert.showAndWait();
            }
            else if(Signup_password.getText().length()<8){
                Alert alert=new Alert(AlertType.ERROR);
                alert.setTitle("Error Message!");
                alert.setHeaderText(null);
                alert.setContentText("The entered password is too short. Password must contain at least 8 character.");
                alert.showAndWait();
            }
            else if(!validate_email()){
                Alert alert=new Alert(AlertType.ERROR);
                alert.setTitle("Error Message!");
                alert.setHeaderText(null);
                alert.setContentText("Invalid email.");
                alert.showAndWait();
            }
            else{
                if(result.next()){
                    Alert alert=new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error message!");
                    alert.setContentText("The username already exist.");
                    alert.showAndWait();
                }
                else{

                    prepare.execute();
                    Alert alert=new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Signed up!");
                    alert.showAndWait();
    
                    login_btn.getScene().getWindow().hide();
                    
                    dashboardcontroler.usrn=Signup_username.getText();
                    Stage stage=new Stage();
                    Parent root= FXMLLoader.load(getClass().getResource("/application/structure/userpanel.fxml"));
                    Scene scene=new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    dashboardcontroler.usrn=Signup_username.getText();
                    
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
    @FXML
    void login(ActionEvent event) {
        String sql="select * from User where username=? and password=?";
        String sql1="select * from User where username=? and is_admin=1";
        connect=sqliteConnection.connector();
        try {
            prepare=connect.prepareStatement(sql);
            prepare2=connect.prepareStatement(sql1);
            prepare2.setString(1, Login_username.getText());
            prepare.setString(1,Login_username.getText());
            prepare.setString(2, Login_password.getText());
            result=prepare.executeQuery();

            if(Login_username.getText().isEmpty()||Login_password.getText().isEmpty()){
                Alert alert=new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Fill the blank fields properly");
                alert.showAndWait();
            }
            else{
                if(result.next()){
                    
                    Alert alert=new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully loged in!");
                    alert.showAndWait();

                    login_btn.getScene().getWindow().hide();
                    
                    
                    result=prepare2.executeQuery();
                    if(result.next()){
                        dashboardcontroler.usrn=Login_username.getText();

                        Stage stage=new Stage();
                        Parent root= FXMLLoader.load(getClass().getResource("/application/structure/dashboard.fxml"));
                        Scene scene=new Scene(root);
                        stage.setScene(scene);
                        Image icon= new Image(getClass().getResourceAsStream("/application/styles/logo.jpg"));
                        stage.getIcons().add(icon);
                        stage.show();
                    }
                    else{
                        userpaneldashboardcontroller.usrn=Login_username.getText();
                        Stage stage=new Stage();
                        Parent root= FXMLLoader.load(getClass().getResource("/application/structure/userpanel.fxml"));
                        Scene scene=new Scene(root);
                        stage.setScene(scene);
                        Image icon= new Image(getClass().getResourceAsStream("/application/styles/logo.jpg"));
                        stage.getIcons().add(icon);
                        stage.show();
                    }
                }
                else{
                    Alert alert=new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Entered username or password was incorrect.");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void minimise(ActionEvent event) {
        if(signin_opened==true){
            Stage stage=(Stage)Login_form.getScene().getWindow();
            stage.setIconified(true);
        }
        else if (signup_opened==true){
            Stage stage=(Stage)Signup_form.getScene().getWindow();
            stage.setIconified(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        if(!loginmodel.isDBconnected()){
            isconnected.setText("Oops the database is not connected");
        }
        else if (loginmodel.isDBconnected()){
            isconnected.setText("Connected");
        }
        Login_form.setVisible(true);
        Signup_form.setVisible(false);
        Signup_email.setText("");
        Signup_username.setText("");
        Signup_password.setText("");
        name.setText("");
        age.setText("");

        Login_username.setText("");
        Login_password.setText("");
    }

}

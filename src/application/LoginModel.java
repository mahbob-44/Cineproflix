package application;

import java.sql.Connection;

public class LoginModel {
    Connection conn;
    public LoginModel(){
        conn=sqliteConnection.connector();
        if(conn==null){
            System.exit(0);
        }
    }
    public boolean isDBconnected(){
       try {
        return !conn.isClosed();
       } catch (Exception e) {
        // TODO: handle exception
        System.out.println(e);
        return false;
       }
    }
}

package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class sqliteConnection {
    static final String DB_url="jdbc:mysql://localhost:3307/user";
    static final String username="root";
    static final String password="";
    public static Connection connector(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn=DriverManager.getConnection(DB_url, username, password);
            return conn;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
}

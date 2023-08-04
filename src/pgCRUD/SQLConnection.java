package pgCRUD;

import java.sql.*;

public class SQLConnection
{
    Connection connection;
    String db_name;
    String db_user;
    String db_password;
    public SQLConnection(String db_name, String db_user, String db_password){
        try {
            String url = "jdbc:postgresql://localhost:5432/" + db_name;
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, db_user, db_password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}



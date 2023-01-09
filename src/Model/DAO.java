package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
    private final static String timeZoneAttribute = "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final static String dbName = "pmfdb";
    private final static String dbAddress = "jdbc:mysql://localhost:3306/" + dbName + "?autoReconnect=true&useSSL=false";
    private final static String URL = dbAddress + timeZoneAttribute;
    private final static String LOGIN = "root";
    private final static String PASSWORD = "0123456789";
    private Connection connection;
    private Statement statement;

    public DAO(){
        this.connection = null;
        this.statement = null;
    }

    public Boolean open() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL,LOGIN,PASSWORD);
            this.statement = this.connection.createStatement();
        } catch (final ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("=== OPENING OF THE BDD ===");// to remove at the project's end
        return true;
    }

    public void close() {
        try {
            this.statement.close();
            //this.connection.close();
            System.out.println("=== CLOSING THE BDD ==="); // to remove at the project's end
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    private int executeUpdate(String Query) {
        try {
            this.statement.executeUpdate(Query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
        return 1;
    }


}

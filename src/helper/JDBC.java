package helper;

import java.sql.Connection;
import java.sql.DriverManager;

/** This is the class for JDBC. It is the class for the Java Database Connection driver. It handles the methods
 *  called to connect the Java program to the MySQL Database. */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "lesson_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection;

    /** The method to open the connection between the Java program and the SQL database. The method uses the
     *  variables listed in the class to make the connection. */
    public static void openConnection(){
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful!");
        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /** The method that closes the connection between the Java program and the SQL database. This method is called
     *  when the Java program is closed. */
    public static void closeConnection(){
        try{
            connection.close();
            System.out.println("Connection closed!");

        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

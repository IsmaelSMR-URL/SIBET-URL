package data;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class connectionPool {

    private static connectionPool INSTANCE = null;
    private static Connection con = null;
    private static BasicDataSource ds = null;
    private static String url = "jdbc:mysql://localhost:3306/sibeturl?autoReconnect=true&useSSL=false&serverTimezone=UTC";
    private static String user = "root";
    private static String password = "My$QL5183T.!";

    private connectionPool() {
        this.initDataSource();
    }

    private static synchronized void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new connectionPool();
        }
    }

    public static connectionPool getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public final void initDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setUrl(url);
        basicDataSource.setInitialSize(100);
        ds = basicDataSource;
    }

    public static boolean isConnected() {
        boolean resp = false;

        try {
            if (con != null && !con.isClosed()) {
                resp = true;
            } else {
                resp = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: " + e.getMessage());
        }
        return resp;
    }

    public static Connection getConnection() {
        if (!isConnected()){
            try {
                con = ds.getConnection();
                System.out.println("Connection Established Successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        return con;
    }

    public static void closeConnection(Connection c) {
        if (isConnected()) {
            try {
                con.close();
                System.out.println("Connection Closed");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws SQLException{
    }
}

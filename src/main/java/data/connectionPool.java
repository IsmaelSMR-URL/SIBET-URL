package data;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class connectionPool {

    private static connectionPool INSTANCE = null;
    private static Connection c = null;
    private static BasicDataSource dataSource;
    private static String url = "jdbc:mysql://localhost:3306/sibet";
    private static String user = "admin";
    private static String pass = "Nicaragua22.!";

    private connectionPool() {
        this.inicializaDataSource();
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

    public final void inicializaDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(pass);
        basicDataSource.setUrl(url);
        basicDataSource.setMaxIdle(100);
        dataSource = basicDataSource;
    }

    public static boolean EstaConectado() {
        boolean resp = false;

        try {
            if (c != null && !c.isClosed()) {
                resp = true;
            } else {
                resp = false;
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return resp;
    }

    public static Connection getConnection() {
        if (!EstaConectado()) {
            try {
                c = dataSource.getConnection();
            } catch (SQLException var1) {
                var1.printStackTrace();
            }
        }

        return c;
    }

    public static void closeConnection(Connection con) {
        if (EstaConectado()) {
            try {
                con.close();
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws SQLException {
    }
}
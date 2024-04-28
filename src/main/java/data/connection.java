package datos;
import java.SQL.Connection;
import java.SQL.SQLException;
import org.apache.commons.dbcp.BasicDataSource;


public class connectionPool{

    private statiic connectionPool INSTANCE = null;
    private static connection con = null;
    private BasicDataSource dataSource;
    private static String url = "jdbs:mysql://localhost:3306/sibet?autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=UTC";
    private static String user = "root";
    private static String password = "My$QL5183T.!";

    private connectionPool(){
        this.initDataSource();
    }

    private static synchronized void createInstance(){
        if(INSTANCE == null){
            INSTANCE = new connectionPool();
        }
    }

    public static connectionPool getInstance(){
        if(INSTANCE == null){
            createInstance();
        }
        return INSTANCE;
    }

    public final void initDataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setUrl(url);
        basicDataSource.setMaxActive(100);
        dataSource = basicDataSource;
    }

    public static boolean IsConnected(){
        boolean response = false;

        try{
            if(con != null && !con.isClosed()){
                response = true;
            }else{
                response = false;
            }
        }catch (Exception e){
            e1.printStackTrace();
            System.out.println("Error: " + e1.getMessage());
        }
        return response;
    }

    public static Connection getConnection(){
        if (!IsConnected()) {
            try{
                con = new connection(dataSource.getConnection());
                System.out.println("Connected to the database");
            }catch(SQLException e){
                e2.printStackTrace();
                System.out.println("Error: " + e2.getMessage());
            }
        }
        return con;
    }

    public static void closeConnection(){
        if (IsConnected()) {
            try{
                con.close();
                System.out.println("Connection closed");
            }catch(SQLException e1){
                e1.printStackTrace();
                System.out.println("Error: " + e3.getMessage());
            }
        }
    }

    public static void main(String[] args) throws SQLException {
    }

}


package rikkei.academy.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectMySQL {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/shop_spring_mvc";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Connect database success !");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Connect error !");
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static void close(Connection conn ) throws SQLException {
        if (conn != null) {
            conn.close();
        }

    }
}

package sptech.school;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class configuracaoBanco {
    private static String USER;
    private static String PASSWORD;
    private static String DATABASE = "sorvesys_db";
    private static String URL;
    
    public static Connection getConexao() throws SQLException {
        String host = System.getenv("DB_HOST");
        USER = System.getenv("DB_USER");
        PASSWORD = System.getenv("DB_PASS");
        URL = "jdbc:mysql://" + host + ":3306/" + DATABASE;
        
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
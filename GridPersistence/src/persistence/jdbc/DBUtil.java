/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author thomas_r
 */
public class DBUtil {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "developer";

    private static final String MYSQL_CONN_STRING = "jdbc:mysql://localhost/gridrepository";
    private static final String HSQLDB_CONN_STRING = "jdbc:hsqldb:data/hsqldb/explorecalifornia/explorecalifornia";

    public static Connection getConnection(DBType type) throws SQLException {
        Connection conn = null;

        switch (type) {
            case MYSQL: {
                conn = DriverManager.getConnection(MYSQL_CONN_STRING, USERNAME, PASSWORD);
                break;
            }
            case HSQLDB: {
                conn = DriverManager.getConnection(HSQLDB_CONN_STRING, USERNAME, PASSWORD);
                break;
            }
            case MONGODB:
                break;
        }
        return conn;
    }
    
    public static void processException(SQLException e) {
        System.err.println("Error msg: " + e.getMessage());
        System.err.println("Error code: " + e.getErrorCode());
        System.err.println("SQL state: " + e.getSQLState());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.jdbc.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import persistence.jdbc.DBType;
import persistence.jdbc.DBUtil;

/**
 *
 * @author Thomas Rothe
 */
public class StatesManager {

    public static void displayAllRows() throws SQLException {
        String sql = "SELECT adminId, userName, password FROM states";

        try (
                Connection conn = DBUtil.getConnection(DBType.HSQLDB);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            System.out.println("Admin Table: ");
            
            while (rs.next()) {
                StringBuilder buf = new StringBuilder();
                buf.append(rs.getObject("adminId", Integer.class)).append(": ");
                buf.append(rs.getObject("userName", String.class)).append(", ");
                buf.append(rs.getObject("password", String.class));
                System.out.println(buf.toString());
            }

        }
    }
}

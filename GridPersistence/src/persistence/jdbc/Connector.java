/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import logic.InputHelper;

import static persistence.jdbc.DBType.HSQLDB;
import persistence.jdbc.tables.Tours;

/**
 *
 * @author thomas_r
 */
public class Connector {

    private static final String SQL = "SELECT tourId, tourName, price FROM tours WHERE price <= ?";

    public static void main(String args[]) throws SQLException {

        double maxPrice = 0;
        
        try {
            maxPrice = InputHelper.getDoubleInput("Enter a maximum price: ");
        } catch (NumberFormatException e) {
            System.err.println("Error: invalid number");
            return;
        }

        ResultSet rs = null;

        try (
                Connection conn = DBUtil.getConnection(HSQLDB);
                //Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                PreparedStatement stmt = conn.prepareStatement(
                        SQL,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);) {

            stmt.setDouble(1, maxPrice);
            
            rs = stmt.executeQuery();
            Tours.displayData(rs);

            rs.last();
            System.out.println("Number of rows: " + rs.getRow());

        } catch (SQLException ex) {
            DBUtil.processException(ex);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

}

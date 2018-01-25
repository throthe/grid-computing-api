/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.jdbc.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import persistence.jdbc.DBType;
import persistence.jdbc.DBUtil;
import persistence.jdbc.beans.Admin;

/**
 *
 * @author Thomas Rothe
 */
public class AdminManager {

    public static void displayAllRows() throws SQLException {
        String sql = "SELECT adminId, userName, password FROM admin";

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

    public static Admin getRow(int adminId) throws SQLException {
        String sql = "SELECT * FROM admin WHERE adminId = ?";
        ResultSet rs = null;

        try (
                Connection conn = DBUtil.getConnection(DBType.HSQLDB);
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, adminId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminId(adminId);
                admin.setUserName(rs.getObject("userName", String.class));
                admin.setPassword(rs.getObject("password", String.class));
                return admin;
            } else {
                System.err.println("No data are found!");
                return null;
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return null;
    }

    public static boolean insert(Admin admin) throws SQLException {
        String sql = "INSERT INTO admin (userName, password) VALUES(?, ?)";

        ResultSet keys = null;

        try (
                Connection conn = DBUtil.getConnection(DBType.HSQLDB);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, admin.getUserName());
            stmt.setString(2, admin.getPassword());
            int affected = stmt.executeUpdate();

            if (affected == 1) {
                keys = stmt.getGeneratedKeys();
                keys.next();
                int newKey = keys.getInt(1);
                admin.setAdminId(newKey);
            } else {
                System.err.println("No rows affected");
                return false;
            }

        } catch (SQLException e) {
            DBUtil.processException(e);
        } finally {
            if (keys != null) {
                keys.close();
            }
        }

        return false;
    }

    public static boolean update(Admin admin) throws SQLException {

        String sql = "UPDATE admin SET userName = ?, password = ? WHERE adminId = ?";

        try (
                Connection conn = DBUtil.getConnection(DBType.HSQLDB);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, admin.getUserName());
            stmt.setString(2, admin.getPassword());
            stmt.setInt(3, admin.getAdminId());
            
            int affected = stmt.executeUpdate();
            
            return affected == 1;
            
        } catch (SQLException e) {
            DBUtil.processException(e);
        }
        
        return false;
    }

    public static boolean delete(int adminId) throws SQLException {
        
        String sql = "DELETE FROM admin WHERE adminId = ?";
        
        try (
                Connection conn = DBUtil.getConnection(DBType.HSQLDB);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ){
            
            stmt.setInt(1, adminId);
            int affected = stmt.executeUpdate();
            return affected == 1;
            
        } catch (SQLException e) {
            DBUtil.processException(e);
        }
        
        return false;
    }
}


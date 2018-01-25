/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.jdbc;

import java.sql.SQLException;
import logic.InputHelper;
import persistence.jdbc.beans.Admin;
import persistence.jdbc.tables.AdminManager;

/**
 *
 * @author thomas_r
 */
public class BeanTester {
    
    public static void main(String[] args) throws SQLException {
        
        AdminManager.displayAllRows();
        
        int adminId = InputHelper.getIntegerInput("Select a row: ");
        Admin admin = AdminManager.getRow(adminId);
        
        
        admin.setUserName("bla");
        AdminManager.update(admin);
        AdminManager.delete(2);
    }
}

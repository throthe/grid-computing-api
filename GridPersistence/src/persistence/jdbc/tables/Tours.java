/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.jdbc.tables;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Thomas Rothe
 */
public class Tours {

    public static void displayData(ResultSet rs) throws SQLException {
        while (rs.next()) {

            StringBuilder buf = new StringBuilder();

            int tourId = rs.getObject("tourId", Integer.class);
            String tourName = rs.getObject("tourName", String.class);
            BigDecimal price = rs.getObject("price", BigDecimal.class);

            buf.append("Tour ").append(tourId).append(": ");
            buf.append(tourName);

            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            String formattedPrice = nf.format(price);

            buf.append(" (").append(formattedPrice).append(")");

            System.out.println(buf.toString());
        }
    }
}

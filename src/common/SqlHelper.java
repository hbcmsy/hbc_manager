package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlHelper {

    public static Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hbc","root","qazwsxedc");
			//Connection con = DriverManager.getConnection("jdbc:mysql://sqld.duapp.com:4050/opTPOwYvGoaWCDcXZTFb", "03cT8F6ySDdSXnVVn78jtl71", "ds2VvYCowLQGU6hGq0RLWHu9Bl2cS0aF");
			return con;  
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;

    }

    public static void closeResult(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
        }
    }

    public static void closePreparedStatement(PreparedStatement ps) {
        try {
            ps.close();
        } catch (SQLException e) {
        }
    }

    public static void closeConneciton(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
        }
    }
}

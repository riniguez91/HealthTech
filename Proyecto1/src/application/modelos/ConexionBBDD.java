package application.modelos;

import java.sql.*;

public class ConexionBBDD {
    String BBDDName;
    Connection c = null;
    Statement stmt = null;

    // public ConexionBBDD(String path) { BBDDName = path; };

    public boolean sentenciaSQL(String sql) {
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
            return false;
        }
        return true;
    }
}

package application.modelos;

import java.sql.*;

public class ConexionBBDD {
    String BBDDName;
    Connection c = null;
    Statement stmt = null;

    public ConexionBBDD(String path) { BBDDName = path; };

    public boolean sentenciaSQL(String sql) {
        try {
            c = DriverManager.getConnection("jdbc:mariadb:"+BBDDName);
            stmt = c.createStatement();
            stmt.close();
            c.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
            return false;
        }
        return true;
    }
}

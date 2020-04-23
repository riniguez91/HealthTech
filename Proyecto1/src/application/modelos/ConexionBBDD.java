package application.modelos;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class ConexionBBDD {
    String BBDDName;
    Connection c = null;
    Statement stmt = null;
    PreparedStatement pstm = null;
    ResultSet rs;

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

    public boolean preparedStatementSQL(String sql, InputStream inputStream) {
        try {
            /*ConexionBBDD c = new ConexionBBDD();
            File file = new File("C:\\Users\\rinig\\Documents\\GitHub\\proyecto1-techhealth\\Proyecto1\\src\\resources\\fotos\\cuidador_crey.png");
            FileInputStream inputStream = new FileInputStream(file);
            Boolean stmt = cbbdd.preparedStatementSQL("INSERT INTO users (Name, Surnames, DOB, User, Password, Rol, Photo, Telephone, Adress, DNI) " +
            "VALUES(\"Peter Panda2\", \"odod2\", \"2010-12-02\", \"Pepe2\", MD5(\"pepe\"), \"Cuidador\", ?, 654321987, NULL, \"05466321W\");", inputStream);
            System.out.println(stmt);*/

            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            pstm = c.prepareStatement(sql);
            pstm.setBlob(1, inputStream);
            pstm.executeUpdate();
            pstm.close();
            c.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
            return false;
        }
        return true;
    }

    public ResultSet resultSetSQL(String sql, String username, String password) {
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            pstm = c.prepareStatement(sql);
            pstm.setString(1, username);
            pstm.setString(2, password);
            rs = pstm.executeQuery();
            pstm.close();
            c.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
            return null;
        }
        return rs;
    }
}

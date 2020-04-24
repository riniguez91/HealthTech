package application.modelos;

import java.sql.*;
import java.util.Vector;

public class ConexionBBDD {
    private String BBDDName;
    private Connection c;
    private Statement stmt;
    private PreparedStatement pstm;
    private ResultSet rs;
    private Vector<Integer> relatedIDS;

    // public ConexionBBDD(String path) { BBDDName = path; };

    public void sentenciaSQL(String sql) {
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
    }

    public ResultSet loginRS(String sql, String username, String password) {
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

    public void insertUserRS(String sql, String name, String surnames, Date DOB, String user, String password, String rol, int telephone, String adress,
                             String dni) {
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            pstm = c.prepareStatement(sql);

            pstm.setString(1, name);
            pstm.setString(2, surnames);
            pstm.setDate(3, DOB);
            pstm.setString(4, user);
            pstm.setString(5, password);
            pstm.setString(6, rol);
            pstm.setInt(7, telephone);
            pstm.setString(8, adress);
            pstm.setString(9, dni);

            rs = pstm.executeQuery();
            pstm.close();
            c.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
    }

    public Vector<Integer> relatedUserIDS(Usuario usuario, String tabla, String FK1, String FK2) {
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            String s = "SELECT `" + tabla + "`." + FK2 + " FROM users INNER JOIN `" + tabla + "` ON `" + tabla + "`." + FK1 + " = users.ID_User" +
                    " WHERE users.ID_User = " + usuario.getID_User();
            pstm = c.prepareStatement(s);
            rs = pstm.executeQuery();

            relatedIDS = new Vector<>();
            while (rs.next())
                relatedIDS.add(rs.getInt(FK2));

            pstm.close();
            c.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
        return relatedIDS;
    }

    public ResultSet selectUserFromID(int id) {
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            String s = "SELECT * \n" +
                    "FROM users \n" +
                    "WHERE users.ID_User = ?";
            pstm = c.prepareStatement(s);
            pstm.setInt(1, id);

            rs = pstm.executeQuery();

            pstm.close();
            c.close();
        } catch(SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
        return rs;
    }
}

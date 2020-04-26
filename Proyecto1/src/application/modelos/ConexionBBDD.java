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

    public Vector<Usuario> sentenciaSQL(String sql) {
    	Vector<Usuario> usuariosBBDD= new Vector<>();
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            stmt = c.createStatement();
           // stmt.executeUpdate(sql);
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
            	usuariosBBDD.add(new Usuario(rs.getInt("ID_User"), rs.getString("Name"), rs.getString("Surnames"), rs.getString("DOB"), 
            			rs.getString("User"), rs.getString("Password"), rs.getString("Rol"), rs.getString("Photo"), rs.getInt("Telephone"), 
            			rs.getString("Adress"), rs.getString("DNI")));
            }
            
            stmt.close();
            c.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
        return usuariosBBDD;
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

    public void insertarMensaje(String ID_Ticket, String message, String subject, int ID_User_Sender, int ID_User_Receiver) {
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            String s = "INSERT INTO enviar_mensaje (ID_Ticket, Message, Subject, Is_Read, ID_User_Sender, ID_User_Receiver) \n" +
                    "VALUES (?, ?, ?, 0, ?, ?);";
            pstm = c.prepareStatement(s);

            pstm.setString(1, ID_Ticket);
            pstm.setString(2, message);
            pstm.setString(3, subject);
            pstm.setInt(4, ID_User_Sender);
            pstm.setInt(5, ID_User_Receiver);
            pstm.executeQuery();

            pstm.close();
            c.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
    }

    public Vector<Message> getMensajesDeUsuario(int ID_User) {
        Vector<Message> mensajes = new Vector<>();
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            String s = "SELECT * FROM enviar_mensaje WHERE `enviar_mensaje`.ID_User_Sender = ? OR `enviar_mensaje`.ID_User_Receiver = ?";
            pstm = c.prepareStatement(s);

            pstm.setInt(1, ID_User);
            pstm.setInt(2, ID_User);

            rs = pstm.executeQuery();
            while (rs.next()) {
                mensajes.add(new Message(rs.getInt("PK_Ticket"), rs.getInt("ID_User_Sender"), rs.getInt("ID_User_Receiver"),
                        rs.getString("Subject"), rs.getString("Message"), rs.getString("ID_Ticket"),
                        rs.getBoolean("Is_Read")));
            }
            pstm.close();
            c.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
        return mensajes;
    }

    public Vector<Message> getMensajesDeTicket(String ID_Ticket) {
        Vector<Message> messgesInTicket = new Vector<>();
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            String s = "SELECT * FROM enviar_mensaje WHERE `enviar_mensaje`.ID_Ticket = ?";
            pstm = c.prepareStatement(s);

            pstm.setString(1, ID_Ticket);

            rs = pstm.executeQuery();
            while (rs.next())
                messgesInTicket.add(new Message(rs.getInt("PK_Ticket"), rs.getInt("ID_User_Sender"), rs.getInt("ID_User_Receiver"),
                        rs.getString("Subject"), rs.getString("Message"), rs.getString("ID_Ticket"),
                        rs.getBoolean("Is_Read")));

            pstm.close();
            c.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
        return messgesInTicket;
    }

    public void setMsgAsRead(Message msg) {
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            String s = "UPDATE pr_healthtech.enviar_mensaje SET Is_Read = 1 WHERE PK_Ticket = ?";

            pstm = c.prepareStatement(s);
            pstm.setInt(1, msg.getPK_Ticket());

            pstm.executeQuery();

            pstm.close();
            c.close();
        } catch(SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
    }

    public Vector<sensor> leerDatosSensor(int ID_User, String tipoSensor, String startDate, String endDate, String sql) {
        Vector<sensor> datosSensCont = new Vector<>();
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");

            pstm = c.prepareStatement(sql);

            pstm.setInt(1, ID_User);
            pstm.setString(2, tipoSensor);
            pstm.setString(3, startDate);
            pstm.setString(4, endDate);


            rs = pstm.executeQuery();
            while (rs.next())
                if (tipoSensor.equals("Temperatura") || tipoSensor.equals("Gas"))
                    datosSensCont.add(new sensor(rs.getInt("ID_Sensores_Continuos"), rs.getDouble("Reading"),
                            rs.getDate("Date_Time_Activation")));
                else
                    datosSensCont.add(new sensor(rs.getInt("ID_Sensores_Discretos"), rs.getDouble("Reading"),
                            rs.getDate("Date_Time_Activation")));

        } catch(SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
        return datosSensCont;
    }

} // ConexionBBDD()

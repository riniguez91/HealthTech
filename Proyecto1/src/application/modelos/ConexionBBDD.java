package application.modelos;

import com.calendarfx.model.Entry;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.swing.border.TitledBorder;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ConexionBBDD {
    private String BBDDName;
    private Connection c;
    private Statement stmt;
    private PreparedStatement pstm;
    private ResultSet rs;
    private Vector<Integer> relatedIDS;
    private modelo m = new modelo();

    // public ConexionBBDD(String path) { BBDDName = path; };

    //Conectar base de datos en local
    public void conexionLocal() {
    	String BBDDName = "C:/Users/victo/Documents/GitHub/proyecto1-techhealth/Proyecto1/src/BaseDatosLocal.db"; //RUTA absoluta
    	try {
    		c = DriverManager.getConnection("jdbc:sqlite:"+BBDDName); //LOCAL
    		//c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123"); //Remoto
    		System.out.println("GG WP");
    		c.close();
		} catch (SQLException sqle) {
			System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
		}
    }//Fin Conexion local
    
    
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
            // Se podria hacer buscando solo en la tabla que relaciona los usuarios (pac-med, pac-fam etc.) pero lo dejamos asi por si en el
            // futuro necesitamos información extra que solo se obtiene siguiendo las relaciones y multiplicando las tablas con el INNER JOIN
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
    
    public Vector<sensor> leerDatosSensorGPS(int ID_User, String tipoSensor, String sql) {
        Vector<sensor> datosSensorGPS = new Vector<>();
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");

            pstm = c.prepareStatement(sql);

            pstm.setInt(1, ID_User);
            pstm.setString(2, tipoSensor);

            rs = pstm.executeQuery();
            while (rs.next())
            	datosSensorGPS.add(new sensor(rs.getInt("ID_Sensor_GPS"), rs.getDouble("Latitude"), rs.getDouble("Longitude"),
                                              rs.getDate("Date_Time_Activation")));

        } catch(SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
        return datosSensorGPS;
    }
    
    //-------Admin
    

    public void eliminarUsuario(Integer ID_Usuario) {
    	try {
    		c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
    		String s = "DELETE FROM pr_healthtech.users WHERE ID_User = ? ;" ;
    		pstm = c.prepareStatement(s);
    		
    		pstm.setInt(1, ID_Usuario );
    		
    		pstm.executeQuery();
    		pstm.close();
    		c.close();
    	}catch (SQLException sqle) {   		
    	System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());    	}
    }
    
    //Editar Usuario para cambiarle los datos en la base de datos
    public void editUser(String name, String surnames, String DOB, String user, String password,
			String rol,String photo, int telephone, String adress, String dni, int ID_user) {
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            String s = "UPDATE pr_healthtech.users SET users.name = ? , users.surnames = ?, users.DOB = ? , users.user = ? , users.password = ?, users.rol = ?, " +
                       "users.photo = ?, users.telephone = ?, users.adress = ?, users.DNI = ? WHERE users.ID_User = ?;";

            pstm = c.prepareStatement(s);
            
            pstm.setString(1, name);
			pstm.setString(2, surnames);
			pstm.setString(3, DOB);
			pstm.setString(4, user);
			pstm.setString(5, password);
			pstm.setString(6, rol);
			pstm.setString(7, photo);
			pstm.setInt(8, telephone);
			pstm.setString(9, adress);
			pstm.setString(10, dni);
			pstm.setInt(11, ID_user);

            pstm.executeQuery();

            pstm.close();
            c.close();
        } catch(SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
    }

    public void recogerAlertas(HashMap<String, Vector<TextFlow>> registros, int ID_User, String startDate, String endDate){
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            String s = "SELECT alertas.Tipo_Sensor, alertas.Reading, alertas.Date_Time_Activation\n" +
                    "FROM alertas\n" +
                    "WHERE alertas.ID_User = ? AND alertas.Date_Time_Activation BETWEEN ? AND ?";
            pstm = c.prepareStatement(s);

            pstm.setInt(1, ID_User);
            pstm.setString(2, startDate);
            pstm.setString(3, endDate);

            rs = pstm.executeQuery();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date oldDate = sdf.parse("1999-12-10 18:44:44");
            while (rs.next()) {
                String[] aux = rs.getString("Date_Time_Activation").split(" "); // aux[0] respresenta "YYYY-MM-DD" mientras que Aux[1] "HH:MM:SS.fffff"
                if (sdf.parse(rs.getString("Date_Time_Activation")).getTime() != oldDate.getTime()) {
                    registros.put(aux[0], new Vector<>());
                    oldDate = sdf.parse(rs.getString("Date_Time_Activation"));
                }
                TextFlow flow = new TextFlow();
                flow.setStyle("-fx-font-family: Century Gothic; -fx-font-size: 14");

                Text t1 = new Text("\t" + rs.getString("Tipo_Sensor") + " -->  ");
                t1.setStyle("-fx-font-weight: bold");

                Text t2 = new Text(rs.getDouble("Reading") + " ºC  "); // Temperatura
                Text t3 = new Text(rs.getDouble("Reading") + " ppm  "); // Gas
                Text t4 = new Text("Activado  "); // Presion

                Text t5 = new Text("Hora: ");
                t5.setStyle("-fx-font-weight: bold");

                // Cambiamos de un formato del tipo "YYYY-MM-DD HH:MM:SS.ffffff" a "HH:MM:SS"
                Text t6 = new Text(rs.getString("Date_Time_Activation").split(" ")[1].split("\\.")[0]);

                switch (rs.getString("Tipo_Sensor")) {
                    case "Sensor Temperatura":
                        flow.getChildren().addAll(t1, t2, t5, t6);
                        registros.get(aux[0]).add(flow);
                        break;
                    case "Sensor Magnetico":
                    case "Sensor Presion":
                        flow.getChildren().addAll(t1, t4, t5, t6);
                        registros.get(aux[0]).add(flow);
                        break;
                    case "Sensor Gas":
                        flow.getChildren().addAll(t1, t3, t5, t6);
                        registros.get(aux[0]).add(flow);
                        break;
                }
            }

        } catch(SQLException | ParseException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
    } // recogerAlertas()

    @SuppressWarnings({"rawtypes"})
    public HashMap<String, Vector<entradaCalendario>> recogerEntradasUsuario(int ID_User) {
        HashMap<String, Vector<entradaCalendario>> entradasCal = new HashMap<>();
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            String sql = "SELECT *\n" +
                    "FROM entradas_calendario\n" +
                    "WHERE entradas_calendario.FK_User = ?";

            pstm = c.prepareStatement(sql);
            pstm.setInt(1, ID_User);
            rs = pstm.executeQuery();

            entradasCal.put("Personal", new Vector<>());
            entradasCal.put("Citas", new Vector<>());
            while (rs.next()) {
                Date sdate = rs.getDate("Start_DateTime");
                Date edate = rs.getDate("End_DateTime");

                Timestamp timestampStart = new Timestamp(sdate.getTime());
                Timestamp timestampEnd = new Timestamp(edate.getTime());
                if (rs.getString("Calendario").equals("Personal"))
                    entradasCal.get("Personal").add(new entradaCalendario(rs.getInt("ID_Entry"), m.createEntry(rs.getString("Title"),
                            timestampStart.toLocalDateTime(), timestampEnd.toLocalDateTime())) );
                else
                    entradasCal.get("Citas").add(new entradaCalendario(rs.getInt("ID_Entry"), m.createEntry(rs.getString("Title"),
                            timestampStart.toLocalDateTime(), timestampEnd.toLocalDateTime())) );
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
        return entradasCal;
    }

    public void insertarEntrada(int ID_User, String title, LocalDateTime startDate, LocalDateTime endDate, String calendario) {
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            String sql = "INSERT INTO entradas_calendario (FK_User, Title, Start_DateTime, End_DateTime, Calendario) VALUES(?, ?, ?, ?, ?);";

            pstm = c.prepareStatement(sql);
            pstm.setInt(1, ID_User);
            pstm.setString(2, title);
            pstm.setString(3, startDate.toString());
            pstm.setString(4, endDate.toString());
            pstm.setString(5, calendario);

            pstm.executeQuery();
        } catch(SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
    }

    public int idUltimaEntrada() {
        int x = 1;
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            String sql = "SELECT entradas_calendario.ID_Entry\n" +
                    "FROM entradas_calendario\n" +
                    "ORDER BY entradas_calendario.ID_Entry DESC\n" +
                    "LIMIT 1";
            stmt = c.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            x = rs.getInt("ID_Entry");


        } catch(SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage() + "hola pepe");
        }
        return x;
    }

    public void removeEntry(int ID_Entry) {
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            String sql = "DELETE FROM `entradas_calendario` WHERE `entradas_calendario`.`ID_Entry` = ?;";
            pstm = c.prepareStatement(sql);
            pstm.setInt(1, ID_Entry);

            pstm.executeQuery();
        } catch(SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
    }

    public void updateEntryInterval(String startDate, String endDate, int ID_Entry) {
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            String sql = "UPDATE `entradas_calendario` SET Start_DateTime = ?, End_DateTime = ? WHERE ID_Entry = ?;";
            pstm = c.prepareStatement(sql);

            pstm.setString(1, startDate);
            pstm.setString(2, endDate);
            pstm.setInt(3, ID_Entry);

            pstm.executeQuery();
        } catch(SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
    }

    /*public void updateEntry(String sql, String value, int ID_Entry) {
        try {
            c = DriverManager.getConnection("jdbc:mysql://2.139.176.212:3306/pr_healthtech", "pr_healthtech", "Jamboneitor123");
            pstm = c.prepareStatement(sql);

            pstm.setString(1, value);
            pstm.setInt(2, ID_Entry);

            System.out.println(pstm.toString());

            pstm.executeQuery();
        } catch(SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
    }*/

} // ConexionBBDD()

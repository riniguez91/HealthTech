package application.modelos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Alert;

import java.io.*;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class modelo {
	
	// Usuarios
    private List<Usuario> usuarios;
    public List<Usuario> getUsuarios(){
        return this.usuarios;
    }
    public void setUsuarios(List<Usuario> usuarios){
        this.usuarios=usuarios;
    }
    
    // Mensajes
    private List<Message> mensajes;
    public List<Message> getMessages(){
        return this.mensajes;
    }
    public void setMessages(List<Message> messages){
        this.mensajes=messages;
    }
    
    // Sensor de temperatura
    private List<modSensorTemperatura> datosTemperatura;
    public List<modSensorTemperatura> getDatosTemperatura(){
        return this.datosTemperatura;
    }
    public void setDatosTemperatura(List<modSensorTemperatura> datosTemperatura){
        this.datosTemperatura=datosTemperatura;
    }
    
    // Sensor de gas
    private List<modSensorGas> datosGas;
    public List<modSensorGas> getDatosGas(){
        return this.datosGas;
    }
    public void setDatosGas(List<modSensorGas> datosGas){
        this.datosGas=datosGas;
    }
    
    // Sensor GPS
    private List<modSensorGPS> datosGPS;
    public List<modSensorGPS> getDatosGPS(){
        return this.datosGPS;
    }
    public void setDatosGPS(List<modSensorGPS> datosGPS){
        this.datosGPS=datosGPS;
    }
    
    // Sensor de presión
    private List<modSensorPresion> datosPresion;
    public List<modSensorPresion> getDatosPresion(){
        return this.datosPresion;
    }
    public void setDatosPresion(List<modSensorPresion> datosPresion){
        this.datosPresion=datosPresion;
    }
    
    // Sensor Magnético
    private List<modSensorMagnetico> datosMagnetico;
    public List<modSensorMagnetico> getDatosMagnetico(){
        return this.datosMagnetico;
    }
    public void setDatosMagnetico(List<modSensorMagnetico> datosMagnetico){
        this.datosMagnetico=datosMagnetico;
    }

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void leerJson(String path){
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            File file = new File(path);
            br = new BufferedReader(new FileReader(file));
            Type tipoListaUsuarios = new TypeToken<List<Usuario>>(){}.getType();
            setUsuarios(gson.fromJson(br, tipoListaUsuarios));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void leerJsonMensajes(String path){
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            File file = new File(path);
            br = new BufferedReader(new FileReader(file));
            Type tipoListaMensajes = new TypeToken<List<Message>>(){}.getType();
            setMessages(gson.fromJson(br, tipoListaMensajes));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void leerJsonTemperatura(String path){
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            File file = new File(path);
            br = new BufferedReader(new FileReader(file));
            Type tipoListaDatosTemperatura = new TypeToken<List<modSensorTemperatura>>(){}.getType();
            setDatosTemperatura(gson.fromJson(br, tipoListaDatosTemperatura));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void leerJsonGas(String path){
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            File file = new File(path);
            br = new BufferedReader(new FileReader(file));
            Type tipoListaDatosGas = new TypeToken<List<modSensorGas>>(){}.getType();
            setDatosGas(gson.fromJson(br, tipoListaDatosGas));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void leerJsonMagnetico(String path){
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            File file = new File(path);
            br = new BufferedReader(new FileReader(file));
            Type tipoListaDatosMagnetico = new TypeToken<List<modSensorMagnetico>>(){}.getType();
            setDatosMagnetico(gson.fromJson(br, tipoListaDatosMagnetico));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void leerJsonPresion(String path){
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            File file = new File(path);
            br = new BufferedReader(new FileReader(file));
            Type tipoListaDatosPresion = new TypeToken<List<modSensorPresion>>(){}.getType();
            setDatosPresion(gson.fromJson(br, tipoListaDatosPresion));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int countWordsString(String words){
        int count=0;

        char ch[]= new char[words.length()];
        for(int i=0;i<words.length();i++)
        {
            ch[i]= words.charAt(i);
            if( ((i>0)&&(ch[i]!=' ')&&(ch[i-1]==' ')) || ((ch[0]!=' ')&&(i==0)) && (Character.isLetter(ch[i])))
                count++;
        }
        return count;
    }

    public boolean checkRol(String words){
        return words.equals("medico") || words.equals("paciente") || words.equals("familiar") || words.equals("cuidador");
    }

    public void serializarAJson(String path, List<?> users, boolean append){
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        BufferedWriter br = null;
        try {
            File file = new File(path);
            br = new BufferedWriter(new FileWriter(file, append));
            prettyGson.toJson(users,br);
        } catch(IOException e){
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int calculateAge(String dOB) throws ParseException {
        DateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = parseFormat.parse(dOB);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = dt.toInstant();
        LocalDate birthday = instant.atZone(defaultZoneId).toLocalDate(); // Convert Date to LocalDate
        LocalDate today = LocalDate.now();                //Today's date
        Period p = Period.between(birthday, today);

        return p.getYears();
    }

    public List<Usuario> userInRelatedUsers(List<Usuario> usuarios, Usuario usuario){
        List<Usuario> finalUsers = new ArrayList<>();
        if (usuario.getRelaciones() == null) {
        	return finalUsers;
		} else {
			for (Usuario users : usuarios) {
	            for (int i = 0;i<usuario.getRelaciones().size();i++){
	                if (users.getUser().equals(usuario.getRelaciones().get(i))){
	                    assert false;
	                    finalUsers.add(users);
	                }
	            }
			}
			return finalUsers;
        }
    }

    public void createAlert(String header, String body) {
        alert.setHeaderText(header);
        alert.setContentText(body);
        alert.showAndWait();
    }
    
    // Encriptacion de las constraseñas en MD5
    private static final char[] CONSTS_HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };
    
    public String encriptaEnMD5(String stringAEncriptar) {
        try {
           MessageDigest msgd = MessageDigest.getInstance("MD5");
           byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
           StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
           for (int i = 0; i < bytes.length; i++) {
               int bajo = (int)(bytes[i] & 0x0f);
               int alto = (int)((bytes[i] & 0xf0) >> 4);
               strbCadenaMD5.append(CONSTS_HEX[alto]);
               strbCadenaMD5.append(CONSTS_HEX[bajo]);
           }
           return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
           return null;
        }   
    }
      
    // Validador de DNI    
    public boolean validarDNI(String dni) { 
        boolean esValido = false;
        int i = 0;
        int caracterASCII = 0;
        char letra = ' ';
        int miDNI = 0;
        int resto = 0;
        char[] asignacionLetra = {'T', 'R', 'W', 'A', 'G', 'M',
        						  'Y', 'F', 'P', 'D', 'X','B', 
        						  'N', 'J', 'Z', 'S', 'Q', 'V', 
        						  'H', 'L', 'C', 'K', 'E'};
        
        if(dni.length() == 9 && Character.isLetter(dni.charAt(8))) {
            do {
                caracterASCII = dni.codePointAt(i);
                esValido = (caracterASCII > 47 && caracterASCII < 58);
                i++;
            } 
            while(i < dni.length()-1 && esValido);     
        }
        if(esValido) {
            letra = Character.toUpperCase(dni.charAt(8));
            miDNI = Integer.parseInt(dni.substring(0,8));
            resto = miDNI % 23;
            esValido = (letra == asignacionLetra[resto]);
        }
        return !esValido;
    }

    public boolean checkUniqueUsername(List<Usuario> usuarios, String username) {
        for (Usuario user : usuarios) {
            if (user.getUser().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Usuario> usuariosRelacionados(Usuario usuario) { // pac-cuid, pac-fam, pac-med, cuid-pac, cuid-med, cuid-fam,
        ArrayList<Usuario> usuariosRelacionados = new ArrayList<>();
        try {
            ConexionBBDD c = new ConexionBBDD();

            switch (usuario.getRol()){
                case "paciente":
                    caseUserRelated(c, usuario, usuariosRelacionados, "paciente-cuidador", "ID_Paciente_C", "ID_Cuidador_P",
                                    "paciente-familiar", "ID_Paciente_F", "ID_Familiar_P", "paciente-medico", "ID_Paciente_M",
                                    "ID_Medico_P");
                    break;
                case "medico":
                    caseUserRelated(c, usuario, usuariosRelacionados, "paciente-medico", "ID_Medico_P", "ID_Paciente_M",
                                    "medico-familiar", "ID_Medico_F", "ID_Familiar_M", "medico-cuidador", "ID_Medico_C",
                                    "ID_Cuidador_M");
                    break;
                case "cuidador":
                    caseUserRelated(c, usuario, usuariosRelacionados, "paciente-cuidador", "ID_Cuidador_P", "ID_Paciente_C",
                                    "cuidador-familiar", "ID_Cuidador_F", "ID_Familiar_C", "medico-cuidador", "ID_Cuidador_M",
                                    "ID_Medico_C");
                    break;
                case "familiar":
                    caseUserRelated(c, usuario, usuariosRelacionados, "paciente-familiar", "ID_Familiar_P", "ID_Paciente_F",
                                    "medico-familiar", "ID_Familiar_M", "ID_Medico_F", "cuidador-familiar", "ID_Familiar_C",
                                    "ID_Cuidador_F");
                    break;
            }
        } catch(SQLException | ParseException sqle) {
            System.out.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
        return usuariosRelacionados;
    }

    public ArrayList<Usuario> crearAListRolUsuario(Vector<Integer> rt, ArrayList<Usuario> alu, ConexionBBDD c) throws SQLException, ParseException {
        for (int i : rt){
            ResultSet rs = c.selectUserFromID(i);
            if (rs.next()) {
                Usuario user = new Usuario(rs.getInt("ID_User"), rs.getString("Name"), rs.getString("Surnames"), rs.getString("DOB"), rs.getString("User")
                        ,rs.getString("Password"), rs.getString("Rol"), rs.getString("Photo"), rs.getInt("Telephone"), rs.getString("Adress"),
                        rs.getString("DNI"));
                user.setAge(this.calculateAge(rs.getString("DOB")));
                alu.add(user);
            }
        }
        return alu;
    }

    public void caseUserRelated(ConexionBBDD c, Usuario usuario, ArrayList<Usuario> usuariosRelacionados, String tabla1, String FK1_t1,
                                String FK2_t1, String tabla2, String FK1_t2, String FK2_t2, String tabla3, String FK1_t3, String FK2_t3)
                                throws SQLException, ParseException {

        Vector<Integer> relatedUTable1 = c.relatedUserIDS(usuario, tabla1, FK1_t1, FK2_t1);
        Vector<Integer> relatedUTable2 = c.relatedUserIDS(usuario, tabla2, FK1_t2, FK2_t2);
        Vector<Integer> relatedUTable3 = c.relatedUserIDS(usuario, tabla3, FK1_t3, FK2_t3);

        usuariosRelacionados = crearAListRolUsuario(relatedUTable1, usuariosRelacionados, c);
        usuariosRelacionados = crearAListRolUsuario(relatedUTable2, usuariosRelacionados, c);
        crearAListRolUsuario(relatedUTable3, usuariosRelacionados, c);
    }
}

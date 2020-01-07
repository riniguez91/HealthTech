package application.modelos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Alert;

import java.io.*;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class modelo {
    private List<Usuario> usuarios;
    public List<Usuario> getUsuarios(){
        return this.usuarios;
    }
    public void setUsuarios(List<Usuario> usuarios){
        this.usuarios=usuarios;
    }

    private List<Message> mensajes;
    public List<Message> getMessages(){
        return this.mensajes;
    }
    public void setMessages(List<Message> messages){
        this.mensajes=messages;
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
        DateFormat parseFormat = new SimpleDateFormat("dd/M/yyyy");
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
	                if (users.getUsername().equals(usuario.getRelaciones().get(i))){
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
    
    public static String encriptaEnMD5(String stringAEncriptar) {
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
        } 
        catch (NoSuchAlgorithmException e) {
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
}

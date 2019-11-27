package application.modelos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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

    public void serializarAJson(String path, List<Usuario> users){
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        BufferedWriter br = null;
        try {
            File file = new File(path);
            br = new BufferedWriter(new FileWriter(file));
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


}

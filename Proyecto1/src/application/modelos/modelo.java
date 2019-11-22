package application.modelos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;


public class modelo {
    private List<Usuario> usuarios;
    public List<Usuario> getUsuarios(){
        return this.usuarios;
    }
    public void setUsuarios(List<Usuario> usuarios){
        this.usuarios=usuarios;
    }

    public void leerJson(){
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            // URL url = getClass().getResource("../Users.json");
            // File file = new File(url.getPath());
            File file = new File("./Proyecto1/src/application/Users.json");
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

    public void serializarAJson(List<Usuario> users){
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        BufferedWriter br = null;
        try {
            // URL url = getClass().getResource("../Users.json");
            // File file = new File(url.getPath());
            // System.out.println(file.getAbsolutePath());
            //br = new BufferedWriter(new FileWriter(new File("C:\\Users\\rinig\\Documents\\GitHub\\proyecto1-techhealth\\Proyecto1\\src\\application\\Users.json")));
            File file = new File("./Proyecto1/src/application/Users.json");
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


}

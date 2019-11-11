package application.modelos;

import com.google.gson.Gson;
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
            URL url = getClass().getResource("../Users.json");
            File file = new File(url.getPath());
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


}

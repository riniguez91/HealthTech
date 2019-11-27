package application.modelos;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class usuarioTTView extends RecursiveTreeObject<usuarioTTView> {
    private StringProperty name;
    private StringProperty surname;
    private StringProperty rol;

    public usuarioTTView(String name, String surname, String rol){
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.rol = new SimpleStringProperty(rol);
    }

    public StringProperty getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.name.set(surname);
    }

    public StringProperty getRolUsuario() {
        return this.rol;
    }

    public void setRolUsuario(String rol) {
        this.name.set(rol);
    }
}

package application.modelos;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class usuarioTTView extends RecursiveTreeObject<usuarioTTView> {
    private final IntegerProperty ID_User;
    private final StringProperty name;
    private final StringProperty surname;
    private final StringProperty rol;
    private final StringProperty birthday;
    private final IntegerProperty age;
    private final StringProperty imagenPerfil;

    public usuarioTTView(int ID_User, String name, String surname, String rol, String birthday, Integer age, String imagenPerfil){
        this.ID_User = new SimpleIntegerProperty(ID_User);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.rol = new SimpleStringProperty(rol);
        this.birthday = new SimpleStringProperty(birthday);
        this.age = new SimpleIntegerProperty(age);
        this.imagenPerfil = new SimpleStringProperty(imagenPerfil);
    }

    public IntegerProperty getID_User() { return this.ID_User; }

    public void setID_User(int ID_User) { this.ID_User.set(ID_User); }

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

    public StringProperty getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public IntegerProperty getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age.set(age);
    }

    public StringProperty getImagenPerfil() {
        return this.imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil.set(imagenPerfil);
    }
}

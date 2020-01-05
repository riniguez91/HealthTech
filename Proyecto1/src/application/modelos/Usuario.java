package application.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.util.List;

public class Usuario {
    public Usuario(){

    }

    public Usuario(String name, String surname, String birthday, String username, Integer telephone, String dni, String password, String rol){
        this.name=name;
        this.surname=surname;
        this.birthday=birthday;
        this.username=username;
        this.telephone=telephone;
        this.dni=dni;
        this.password=password;
        this.rol=rol;
    }

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("rol")
    @Expose
    private String rol;
    @SerializedName("imagenPerfil")
    @Expose
    private String imagenPerfil;
    @SerializedName("telephone")
    @Expose
    private Integer telephone;
    @SerializedName("dni")
    @Expose
    private String dni;
    
    private List<String> relaciones;

    public List<String> getRelaciones() {
        return relaciones;
    }

    public void setRelaciones(List<String> relaciones) {
        this.relaciones = relaciones;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }
    
    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }
    
    public String getDNI() {
        return dni;
    }

    public void setDNI(String dni) {
        this.dni = dni;
    }
}
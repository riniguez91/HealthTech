package application.modelos;


import java.util.List;

public class Usuario {
    

	public Usuario(){

    }

    public Usuario(Integer ID_User, String Name, String Surnames, String DOB, String User, String Password, String Rol, String Photo,  Integer Telephone, String Adress, String DNI){
        this.ID_User = ID_User;
    	this.Name = Name;
        this.Surnames = Surnames;
        this.DOB = DOB;
        this.User = User;
        this.Password = Password;
        this.Rol = Rol;
        this.Photo = Photo;
        this.Telephone = Telephone;
        this.Adress = Adress;
        this.DNI = DNI;
	}
    
    private Integer ID_User;
    
    private String Name;

    private String Surnames;

    private Integer age;

    private String DOB;

    private String User;

    private String Password;

    private String Rol;

    private String Photo;

    private Integer Telephone;

    private String DNI;

    private String Adress;
    
    private List<String> relaciones;

    // Metodos

    public List<String> getRelaciones() {
        return relaciones;
    }

    public void setRelaciones(List<String> relaciones) {
        this.relaciones = relaciones;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

	public Integer getID_User() {
		return ID_User;
	}

	public void setID_User(Integer iD_User) {
		ID_User = iD_User;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSurnames() {
		return Surnames;
	}

	public void setSurnames(String surnames) {
		Surnames = surnames;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getRol() {
		return Rol;
	}

	public void setRol(String rol) {
		Rol = rol;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public Integer getTelephone() {
		return Telephone;
	}

	public void setTelephone(Integer telephone) {
		Telephone = telephone;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getAdress() {
		return Adress;
	}

	public void setAdress(String adress) {
		Adress = adress;
	}

   
    
    
}
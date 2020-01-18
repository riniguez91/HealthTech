package application.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class modSensorPresion {
	
	@SerializedName("valor")
    @Expose
    private int valor;
    @SerializedName("durmiendo")
    @Expose
    private String durmiendo;
    @SerializedName("despierto")
    @Expose
    private String despierto;
    
    // Constructor
	public modSensorPresion(int valor, String durmiendo) {
		super();
		this.valor = valor;
		this.durmiendo = durmiendo;
	}
	
	// Getters and setters
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public String getDurmiendo() {
		return durmiendo;
	}
	public void setDurmiendo(String durmiendo) {
		this.durmiendo = durmiendo;
	}

	public String getDespierto() {
		return despierto;
	}

	public void setDespierto(String despierto) {
		this.despierto = despierto;
	}
    
    

}
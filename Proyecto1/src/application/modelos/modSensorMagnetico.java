package application.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class modSensorMagnetico {
	
	@SerializedName("valor")
    @Expose
    private int valor;
    @SerializedName("fecha")
    @Expose
    private String fecha;		//unicamente mostrará el DD/MM/YY (Dias)
    @SerializedName("hora")
    @Expose
    private String hora;		//unicamente mostrará hh:mm:ss (horas)
    
    // Constructor
	public modSensorMagnetico(int valor, String fecha, String hora) {
		super();
		this.valor = valor;
		this.fecha = fecha;
		this.hora = hora;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
}

package application.modelos;

import java.text.SimpleDateFormat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class modSensorGPS {
	
	// Variables
	@SerializedName("latitud")
    @Expose
    private double latitud;
	
	@SerializedName("longitud")
    @Expose
    private double longitud;
	
    @SerializedName("fecha")
    @Expose
    private SimpleDateFormat fecha;		//unicamente mostrará el DD/MM/YY (Dias)
    
    @SerializedName("hora")
    @Expose
    private SimpleDateFormat hora;		//unicamente mostrará hh:mm:ss (horas)
    
    // Constructor por defecto
    public modSensorGPS() {
    	
    }
    // Constructor
	public modSensorGPS(double latitud, double longitud, SimpleDateFormat fecha, SimpleDateFormat hora) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
		this.fecha = fecha;
		this.hora = hora;
	}
	
	// Getters and setters
	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public SimpleDateFormat getFecha() {
		return fecha;
	}

	public void setFecha(SimpleDateFormat fecha) {
		this.fecha = fecha;
	}

	public SimpleDateFormat getHora() {
		return hora;
	}

	public void setHora(SimpleDateFormat hora) {
		this.hora = hora;
	}
    
    

}

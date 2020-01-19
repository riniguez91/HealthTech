package application.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import jdk.nashorn.internal.ir.annotations.Ignore;
import java.util.Date;
import java.text.SimpleDateFormat;

public class modSensorTemperatura {
	
	public modSensorTemperatura(){
		
	}
	
	public modSensorTemperatura(int temperatura, String fecha, String hora) {
		this.temperatura=temperatura;
		this.fecha=fecha;
		this.hora=hora;
	}
	
	@SerializedName("temperatura")
    @Expose
    private int temperatura;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("hora")
    @Expose
    private String hora;
    
    //Getters y Setters
    public int getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
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

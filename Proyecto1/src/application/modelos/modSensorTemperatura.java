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
	
	public modSensorTemperatura(int temperatura, SimpleDateFormat fecha, SimpleDateFormat hora) {
		this.temperatura=temperatura;
		this.fecha=fecha;
		this.hora=hora;
	}
	
	@SerializedName("temperatura")
    @Expose
    private int temperatura;
    @SerializedName("fecha")
    @Expose
    private SimpleDateFormat fecha;		//unicamente mostrará el DD/MM/YY (Dias)
    @SerializedName("hora")
    @Expose
    private SimpleDateFormat hora;		//unicamente mostrará hh:mm:ss (horas)
    
    //Getters y Setters
    public int getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
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

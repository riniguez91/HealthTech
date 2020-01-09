package application.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import jdk.nashorn.internal.ir.annotations.Ignore;
import java.util.Date;
import java.text.SimpleDateFormat;


public class modSensorGas {
	public modSensorGas(){
		
	}
	public void modeSensorGas(int valor, SimpleDateFormat fecha, SimpleDateFormat hora, boolean alerta ) {
		this.valor=valor;
		this.fecha=fecha;
		this.hora=hora;
		this.alerta=alerta;
	}
	
	@SerializedName("valor")
    @Expose
    private int valor;
    @SerializedName("fecha")
    @Expose
    private SimpleDateFormat fecha;		//unicamente mostrará el DD/MM/YY (Dias)
    @SerializedName("hora")
    @Expose
    private SimpleDateFormat hora;		//unicamente mostrará hh:mm:ss (horas)
    @SerializedName("alerta")
    @Expose
    private boolean alerta;
    
    
    
    //GETTERS y SETTERS
    
    public int getValor() {
        return valor;
    }
    
    public void setValor(int valor) {
        this.valor = valor;
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
    
    public boolean getAlerta() {
        return alerta;
    }
    
    public void setAlerta(boolean alerta) {
        this.alerta = alerta;
    }

}

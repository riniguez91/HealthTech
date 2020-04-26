package application.modelos;

import java.util.Date;

public class sensor {

    public sensor(int ID_Sensor_Continuo_, double reading_, Date Date_Time_Activation_) {
        this.ID_Sensor_Continuo = ID_Sensor_Continuo_;
        this.Reading = reading_;
        this.Date_Time_Activation = Date_Time_Activation_;
    }
    
    public sensor(int ID_Sensor_GPS_, double Latitude_, double Longitude_, Date Date_Time_Activation_) {
		this.ID_Sensor_GPS = ID_Sensor_GPS_;
		this.latitude = Latitude_;
		this.longitude = Longitude_;
		this.Date_Time_Activation = Date_Time_Activation_;
	}
    
    // Variables sensores
    private int ID_Sensor_Continuo;

    private double Reading;

    private Date Date_Time_Activation;
    
    //Variables sensor GPS
    private int ID_Sensor_GPS;
    
    private double longitude;
    
    private double latitude;

    public int getID_Sensor_Continuo() {
        return ID_Sensor_Continuo;
    }

    public sensor setID_Sensor_Continuo(int ID_Sensor_Continuo) {
        this.ID_Sensor_Continuo = ID_Sensor_Continuo;
        return this;
    }

    public double getReading() {
        return Reading;
    }

    public sensor setReading(double reading) {
        Reading = reading;
        return this;
    }

    public Date getDate_Time_Activation() {
        return Date_Time_Activation;
    }

    public sensor setDate_Time_Activation(Date date_Time_Activation) {
        Date_Time_Activation = date_Time_Activation;
        return this;
    }

	public int getID_Sensor_GPS() {
		return ID_Sensor_GPS;
	}

	public void setID_Sensor_GPS(int iD_Sensor_GPS) {
		ID_Sensor_GPS = iD_Sensor_GPS;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
   
}

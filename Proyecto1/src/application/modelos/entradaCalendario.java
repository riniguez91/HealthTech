package application.modelos;

import com.calendarfx.model.Entry;

@SuppressWarnings({"rawtypes"})
public class entradaCalendario {
    public entradaCalendario(int ID_Entrada, Entry entradaCal) {
        this.ID_Entrada = ID_Entrada;
        this.entradaCal = entradaCal;
    }

    private int ID_Entrada;

    private Entry entradaCal;

    public int getID_Entrada() {
        return ID_Entrada;
    }

    public void setID_Entrada(int ID_Entrada) {
        this.ID_Entrada = ID_Entrada;
    }

    public Entry getEntradaCal() {
        return entradaCal;
    }

    public void setEntradaCal(Entry entradaCal) {
        this.entradaCal = entradaCal;
    }
}


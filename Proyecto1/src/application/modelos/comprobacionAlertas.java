package application.modelos;

import application.controladores.controladorVistaGeneral;
import javafx.application.Platform;

import java.util.TimerTask;
import java.util.Vector;

public class comprobacionAlertas extends TimerTask {


    public comprobacionAlertas(controladorVistaGeneral cvg, modelo m, ConexionBBDD c, int ID_Usuario) {
        this.cvg = cvg;
        this.m = m;
        this.c = c;
        this.ID_Usuario = ID_Usuario;
    }

    private controladorVistaGeneral cvg;
    private final modelo m;
    private final ConexionBBDD c;
    private final int ID_Usuario;

    @Override
    public void run() {
        Platform.runLater(() -> {
            Vector<String> alertasPaciente = c.comprobarAlertasPaciente(ID_Usuario);
            StringBuilder cuerpoMensaje = new StringBuilder();
            for (String alerta : alertasPaciente)
                cuerpoMensaje.append(alerta);

            if (alertasPaciente.size() > 0)
                cvg.crearAlertaPaciente(cuerpoMensaje.toString());
        });

    }
}

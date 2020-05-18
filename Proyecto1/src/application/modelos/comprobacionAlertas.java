package application.modelos;

import application.controladores.controladorVistaGeneral;
import javafx.application.Platform;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Vector;

public class comprobacionAlertas extends TimerTask {


    public comprobacionAlertas(controladorVistaGeneral cvg, modelo m, ConexionBBDD c, int ID_Usuario, String rol) {
        this.cvg = cvg;
        this.m = m;
        this.c = c;
        this.ID_Usuario = ID_Usuario;
        this.rol = rol;
    }

    public comprobacionAlertas(controladorVistaGeneral cvg, modelo m, ConexionBBDD c, String rol, ArrayList<Usuario> relUsers) {
        this.cvg = cvg;
        this.m = m;
        this.c = c;
        this.rol = rol;
        this.relUsers = relUsers;
    }

    private final controladorVistaGeneral cvg;
    private final modelo m;
    private final ConexionBBDD c;
    private int ID_Usuario;
    private final String rol;
    private ArrayList<Usuario> relUsers;

    public void alertasPaciente() {
        Vector<String> alertasPaciente = c.comprobarAlertasPaciente(ID_Usuario);

        crearAlerta(alertasPaciente);
    }

    public void alertasPacientesRelacionados(ArrayList<Usuario> relUsers) {
        Vector<String> alertasPaciente = c.comprobarAlertasPacientesRelacionados(relUsers);

        crearAlerta(alertasPaciente);
    }

    public void crearAlerta(Vector<String> alertasPaciente) {
        StringBuilder cuerpoMensaje = new StringBuilder();
        for (String alerta : alertasPaciente)
            cuerpoMensaje.append(alerta);

        if (alertasPaciente.size() > 0)
            cvg.crearAlertaPaciente(cuerpoMensaje.toString());
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            if (rol.equals("paciente"))
                alertasPaciente();
            else
                alertasPacientesRelacionados(relUsers);
        });

    }
}

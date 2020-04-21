package application.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import application.modelos.modelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class controladorResponderTicket {
    private modelo modelo;
    private controladorPaciente cp;

    public void initModelo(modelo modelo_, controladorPaciente cp_) {
        if (this.modelo != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.modelo = modelo_;
        this.cp = cp_;
    }
    
    @FXML private VBox respuestaTicketVBox;

    @FXML private JFXTextArea crearMensajeJFXTextAreaMensajes;

    @FXML private JFXButton cancelarRespuestaTicketBtn;

    @FXML private JFXButton crearMensajeResponderTicketBttnMensajes;

    private void closeScene(){
        Stage stageBttnBelongsTo = (Stage) cancelarRespuestaTicketBtn.getScene().getWindow();
        stageBttnBelongsTo.close();
    }

    @FXML 
    void cancelarRespuestaTicket(ActionEvent event) {
    	closeScene();
    }

    @FXML
    void crearMensajeYResponderTicket(ActionEvent event) {
    	if (crearMensajeJFXTextAreaMensajes.getText().isEmpty()){
            modelo.createAlert("Cuidado", "Debes de poner un mensaje");
    	} else {
    		cp.crearMensajeYResponderTicket(crearMensajeJFXTextAreaMensajes.getText());
    		closeScene();
    	}
    }
    
}

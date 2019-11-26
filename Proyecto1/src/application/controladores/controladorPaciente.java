package application.controladores;

import com.jfoenix.controls.JFXButton;

import application.modelos.Usuario;
import application.modelos.modelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class controladorPaciente {
    private application.modelos.modelo modelo;
    private Usuario usuario;

    public void initModelo(application.modelos.modelo modelo_, Usuario usuario_){
        if (this.modelo != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.modelo = modelo_ ;
        this.usuario = usuario_;
        labelNombre.setText(usuario.getName());
        labelApellidos.setText(usuario.getSurname());
        labelRol.setText(usuario.getRol());
        labelUsername.setText(usuario.getUsername());
    }

    @FXML
    private ImageView User;

    @FXML
    private Label Nombre;

    @FXML
    private Label Apellidos;

    @FXML
    private Label ID;

    @FXML
    private Label Rol;

    @FXML
    private Label labelNombre;

    @FXML
    private Label labelApellidos;

    @FXML
    private Label labelUsername;

    @FXML
    private Label labelRol;

    @FXML
    private Label Nombre1;

    @FXML
    private Label Apellidos1;

    @FXML
    private Label ID1;

    @FXML
    private Label Rol1;

    @FXML
    private Label ID11;

    @FXML
    private Label Nombre11;

    @FXML
    private Label Apellidos11;

    @FXML
    private Label ID12;

    @FXML
    private Label Rol11;

    @FXML
    private Label ID111;

    @FXML
    private Pane PaneInicio;

    @FXML
    private Pane PanePreguntasFrecuentes;
    
    @FXML
    private JFXButton preguntasfrecuentesbtn;

    @FXML
    private JFXButton atrasbtn;
    
    //Pestaña Inicio para ver y ocultar PreguntasFrecuentes
	@FXML
	void verInicio(ActionEvent event) {
		PaneInicio.setVisible(true);
		PanePreguntasFrecuentes.setVisible(false);
	}
	@FXML
	void verPreguntasFrecuentes(ActionEvent event) {
		PanePreguntasFrecuentes.setVisible(true);
		PaneInicio.setVisible(false);
	}
    
	

	
	
	
	
    
}

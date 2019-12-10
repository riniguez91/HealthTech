package application.controladores;

import application.modelos.Usuario;
import application.modelos.modelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

public class controladorFamiliar {
	
    private modelo modelo;
    private Usuario usuario;

    public void initModelo(modelo modelo_, Usuario usuario_){
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
    void AbrirMapa(MouseEvent event) throws IOException {
    	try{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/vistas/vistaMapa.fxml"));
    		Parent root1 = (Parent)fxmlLoader.load();
    		//Nueva ventana vacia
    		Stage stage = new Stage();
    		//Metemos al stage la escena leida 
    		stage.setScene(new Scene(root1));
    		//Muestra el stage
    		stage.setTitle("Localización");
    		stage.show();
    		 
    		} catch (Exception e) {
    			e.printStackTrace();
			}

    }
    
    @FXML
    private JFXButton BotonAbrirMapa;
    
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
    private Label labelBuena;

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

}
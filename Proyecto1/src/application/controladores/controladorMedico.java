package application.controladores;

import application.modelos.Usuario;
import application.modelos.modelo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class controladorMedico {
    private application.modelos.modelo modelo;
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

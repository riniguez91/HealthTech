package application.controladores;

import com.jfoenix.controls.*;

import application.modelos.Usuario;
import application.modelos.modelo;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.util.function.Predicate;

public class controladorPaciente {
    private application.modelos.modelo modelo;
    private Usuario usuario;

    public void initModelo(application.modelos.modelo modelo_, Usuario usuario_){
        if (this.modelo != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.modelo = modelo_ ;
        this.usuario = usuario_;
        // Datos pestaña inicio
        labelNombreInicio.setText(usuario.getName());
        labelApellidosInicio.setText(usuario.getSurname());
        labelRolInicio.setText(usuario.getRol());
        labelUsernameInicio.setText(usuario.getUsername());

        // Datos pestaña Usuario
        labelNombreUsuarios.setText(usuario.getUsername());
        labelApellidosUsuarios.setText(usuario.getSurname());
        labelRolUsuarios.setText(usuario.getRol());
        labelFechaNacimientoUsuarios.setText(usuario.getBirthday());
        labelEdadUsuarios.setText(usuario.getAge()+"");
        // Posibles roles del jfxComboBox para buscar un usuario
        filtradoJFXComboBox.getItems().add("Medico");
        filtradoJFXComboBox.getItems().add("Cuidador");
        filtradoJFXComboBox.getItems().add("Familiar");

        crearTreeTableViewUsuarios();



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
    private Label labelNombreInicio;

    @FXML
    private Label labelApellidosInicio;

    @FXML
    private Label labelUsernameInicio;

    @FXML
    private Label labelRolInicio;

    @FXML
    private JFXButton cerrarSesionBtn;

    @FXML
    private Pane PaneInicio;

    @FXML
    private JFXTextArea comunicacionJFXTextArea;

    @FXML
    private JFXTextArea calendarioJFXTextArea;

    @FXML
    private JFXButton preguntasfrecuentesbtn;

    @FXML
    private Pane PanePreguntasFrecuentes;

    @FXML
    private JFXButton atrasbtn;

    @FXML
    private JFXComboBox<String> filtradoJFXComboBox;

    @FXML
    private JFXTextArea nombresJFXTextArea;

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
    private Label labelNombreUsuarios;

    @FXML
    private Label labelApellidosUsuarios;

    @FXML
    private Label labelFechaNacimientoUsuarios;

    @FXML
    private JFXTextField filtrarUsuarioTFieldUsuarios;

    @FXML
    private Label labelRolUsuarios;

    @FXML
    private Label labelEdadUsuarios;

    @FXML
    private JFXTextArea mensajeJFXTestField;

    @FXML
    private JFXTextField asuntoJFXTextField;

    @FXML
    private JFXButton cancelarTicketbtn;

    @FXML
    private JFXTextArea mensajesJFXTextArea;

    @FXML
    private JFXComboBox<String> destinatarioJFXComboBox;

    @FXML
    private JFXButton enviarJFXbtn;

    @FXML
    private JFXTextArea mensajeJFXTextArea;

    @FXML
    private JFXTreeTableView<Usuario> treeTableViewUsuarios;
    
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

    @FXML
    void filterUsersUsuario(KeyEvent event) {
        treeTableViewUsuarios.setPredicate( usuarioTreeItem -> usuarioTreeItem.getValue().getName().toLowerCase().startsWith(filtrarUsuarioTFieldUsuarios.getText().toLowerCase()));
	}

	public void crearTreeTableViewUsuarios() {
        JFXTreeTableColumn<Usuario, String> nombreCol = new JFXTreeTableColumn<>("Nombre");
        JFXTreeTableColumn<Usuario, String> apellidosCol = new JFXTreeTableColumn<>("Apellidos");
        JFXTreeTableColumn<Usuario, String> rolCol = new JFXTreeTableColumn<>("Rol");

        nombreCol.setCellValueFactory(param -> {
            usuario.nameSS = new SimpleStringProperty(param.getValue().getValue().getName());
            return usuario.nameSS;
        });
        nombreCol.setMinWidth(189);
        nombreCol.setMaxWidth(189);
        apellidosCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getSurname()));
        apellidosCol.setMinWidth(189);
        apellidosCol.setMaxWidth(189);
        rolCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getRol()));
        rolCol.setMinWidth(189);
        rolCol.setMaxWidth(189);

        ObservableList<Usuario> users = FXCollections.observableArrayList();
        // Añadimos los usuarios
        users.addAll(modelo.getUsuarios());

        TreeItem<Usuario> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        treeTableViewUsuarios.getColumns().setAll(nombreCol, apellidosCol, rolCol);
        treeTableViewUsuarios.setRoot(root);
        treeTableViewUsuarios.setShowRoot(false);
    }
	

	
	
	
	
    
}

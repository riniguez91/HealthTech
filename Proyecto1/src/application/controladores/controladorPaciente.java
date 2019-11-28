package application.controladores;

import application.modelos.*;
import com.jfoenix.controls.*;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


public class controladorPaciente {
    private modelo modelo;
    private Usuario usuario;
    public List<Usuario> relatedUsers;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void initModelo(application.modelos.modelo modelo_, Usuario usuario_){
        if (this.modelo != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.modelo = modelo_ ;
        this.usuario = usuario_;
        modelo.leerJsonMensajes("./Proyecto1/src/application/jsonFiles/messages.json");

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

        // Escondemos los datos de usuario y la funcionalidad de mandar mensajes hasta que se seleccione un usuario
        panelDatosYMensajesUsuarios.setVisible(false);

        // Creamos las listas de usuarios y mensajes
        crearTreeTableViewUsuarios();
        // crearTreeTableViewMensajes();
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
    private JFXTextArea nombresJFXTextArea;

    @FXML
    private Pane panelDatosYMensajesUsuarios;

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
    private Label seleccionaMensajeLabelMensajes;

    @FXML
    private Label labelEdadUsuarios;

    @FXML
    private JFXTextArea mensajeJFXTextFieldUsuarios;

    @FXML
    private JFXTextField asuntoJFXTextFieldUsuarios;

    @FXML
    private JFXButton cancelarTicketbtn;

    @FXML
    private JFXTextArea mensajesJFXTextArea;

    @FXML
    private JFXButton enviarJFXbtn;

    @FXML
    private JFXTextArea crearMensajeJFXTextAreaMensajes;

    @FXML
    private JFXTreeTableView<usuarioTTView> treeTableViewUsuarios;

    @FXML
    private Tab tabInicioPaciente;

    @FXML
    private Tab tabCalendarioPaciente;

    @FXML
    private Tab tabUsuariosPaciente;

    @FXML
    private Tab tabMensajesPaciente;

    @FXML
    private JFXTabPane tabPanePaciente;

    @FXML
    private JFXTextField destinatarioJFXTextFieldUsuarios;

    @FXML
    private JFXTreeTableView<messageTTView> treeTableViewMensajes;

    @FXML
    private JFXTextField filtrarMensajeTFieldMensajes;

    @FXML
    private JFXButton botonCancelarMensajesUsuarios;

    @FXML
    private AnchorPane mensajePaneMensajes;

    @FXML
    private JFXTextField destinatarioJFXTextFieldMensajes;

    @FXML
    private JFXTextField asuntoJFXTextFieldMensajes;


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
        treeTableViewUsuarios.setPredicate( usuarioTreeItem -> usuarioTreeItem.getValue().getName().get().toLowerCase().startsWith(filtrarUsuarioTFieldUsuarios.getText().toLowerCase()) ||
        		usuarioTreeItem.getValue().getSurname().get().toLowerCase().startsWith(filtrarUsuarioTFieldUsuarios.getText().toLowerCase()));
	}

    @FXML
    void cancelarMensajeUsuarios(ActionEvent event) {
        asuntoJFXTextFieldUsuarios.clear();
        mensajeJFXTextFieldUsuarios.clear();
    }

	public void crearTreeTableViewUsuarios() {
        JFXTreeTableColumn<usuarioTTView, String> nombreCol = new JFXTreeTableColumn<>("Nombre");
        JFXTreeTableColumn<usuarioTTView, String> apellidosCol = new JFXTreeTableColumn<>("Apellidos");
        JFXTreeTableColumn<usuarioTTView, String> rolCol = new JFXTreeTableColumn<>("Rol");

        nombreCol.setCellValueFactory(param -> param.getValue().getValue().getName());
        nombreCol.setMinWidth(189);
        nombreCol.setMaxWidth(189);
        apellidosCol.setCellValueFactory(param -> param.getValue().getValue().getSurname());
        apellidosCol.setMinWidth(189);
        apellidosCol.setMaxWidth(189);
        rolCol.setCellValueFactory(param -> param.getValue().getValue().getRolUsuario());
        rolCol.setMinWidth(189);
        rolCol.setMaxWidth(189);

        ObservableList<usuarioTTView> users = FXCollections.observableArrayList();
        // Añadimos los usuarios
        relatedUsers = modelo.userInRelatedUsers(modelo.getUsuarios(), usuario);
        for (Usuario user : relatedUsers) {
            users.add(new usuarioTTView(user.getName(), user.getSurname(), user.getRol(),user.getBirthday(), user.getAge()));
        }

        TreeItem<usuarioTTView> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        treeTableViewUsuarios.getColumns().setAll(nombreCol, apellidosCol, rolCol);
        treeTableViewUsuarios.setRoot(root);
        treeTableViewUsuarios.setShowRoot(false);
    }

    public void crearTreeTableViewMensajes() {
        JFXTreeTableColumn<usuarioTTView, String> nombreCol = new JFXTreeTableColumn<>("Nombre");
        JFXTreeTableColumn<usuarioTTView, String> apellidosCol = new JFXTreeTableColumn<>("Apellidos");
        JFXTreeTableColumn<usuarioTTView, String> rolCol = new JFXTreeTableColumn<>("Rol");

        nombreCol.setCellValueFactory(param -> param.getValue().getValue().getName());
        nombreCol.setMinWidth(189);
        nombreCol.setMaxWidth(189);
        apellidosCol.setCellValueFactory(param -> param.getValue().getValue().getSurname());
        apellidosCol.setMinWidth(189);
        apellidosCol.setMaxWidth(189);
        rolCol.setCellValueFactory(param -> param.getValue().getValue().getRolUsuario());
        rolCol.setMinWidth(189);
        rolCol.setMaxWidth(189);

        ObservableList<usuarioTTView> users = FXCollections.observableArrayList();
        // Añadimos los usuarios
        relatedUsers = modelo.userInRelatedUsers(modelo.getUsuarios(), usuario);
        for (Usuario user : relatedUsers) {
            users.add(new usuarioTTView(user.getName(), user.getSurname(), user.getRol(),user.getBirthday(), user.getAge()));
        }

        TreeItem<usuarioTTView> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        treeTableViewUsuarios.getColumns().setAll(nombreCol, apellidosCol, rolCol);
        treeTableViewUsuarios.setRoot(root);
        treeTableViewUsuarios.setShowRoot(false);
    }

    @FXML
    void mostrarDatosYMensajeUsuarios(MouseEvent event) {
	    // Comprobamos que no este visible
	    if (!panelDatosYMensajesUsuarios.isVisible()){
	        panelDatosYMensajesUsuarios.setVisible(true);
        }
	    if (panelDatosYMensajesUsuarios.isVisible()){
            // Cambiamos los datos del usuario
            labelNombreUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getName().get());
            labelApellidosUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getSurname().get());
            labelRolUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getRolUsuario().get());
            labelFechaNacimientoUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getBirthday().get());
            labelEdadUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getAge().get()+"");
            destinatarioJFXTextFieldUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getName().get() + " "
                                                      + treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getSurname().get());
        }
    }

    @FXML
    void enviarMensajeUsuarios(ActionEvent event) {
        if (asuntoJFXTextFieldUsuarios.getText().isEmpty()) {
            alert.setHeaderText("Cuidado");
            alert.setContentText("Debes de poner un asunto");
            alert.showAndWait();
        }
        else if (mensajeJFXTextFieldUsuarios.getText().isEmpty()) {
            alert.setHeaderText("Cuidado");
            alert.setContentText("Debes de poner un mensaje");
            alert.showAndWait();
        }
        else {
            UUID uniqueKey = UUID.randomUUID();
            Message msg = new Message(usuario.getName() + " " + usuario.getSurname(), treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getName().get()+ " "
                    +treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getSurname().get(), asuntoJFXTextFieldUsuarios.getText(), mensajeJFXTextFieldUsuarios.getText(),
                    uniqueKey.toString());
            modelo.getMessages().add(msg);
            modelo.serializarAJson("./Proyecto1/src/application/jsonFiles/messages.json", modelo.getMessages(),false);
            alert.setHeaderText("Informacion");
            alert.setContentText("Se ha enviado el mensaje correctamente");
            alert.showAndWait();
            // Borramos los campos para evitar confusion
            asuntoJFXTextFieldUsuarios.clear();
            mensajeJFXTextFieldUsuarios.clear();
            destinatarioJFXTextFieldUsuarios.clear();

            // Cambiamos a la pestaña de mensajes para que vea que efectivamente se ha enviado correctamente junto al historial de mensajes
            tabPanePaciente.getSelectionModel().select(tabMensajesPaciente);
        }
    }

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
        Stage stageBttnBelongsTo = (Stage) cerrarSesionBtn.getScene().getWindow();
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/application/vistas/vistaLogin.fxml"));
        Parent rootLogin;
        rootLogin = loaderLogin.load();
        controladorLogin contrLogin = loaderLogin.getController();
        contrLogin.initModelo(modelo,usuario);
        stageBttnBelongsTo.setScene(new Scene(rootLogin));
    }

	
	
	
	
    
}

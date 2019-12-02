package application.controladores;

import application.modelos.*;

import com.calendarfx.view.page.DayPage;
import com.jfoenix.controls.*;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class controladorPaciente {
    private modelo modelo;
    private Usuario usuario;
    public List<Usuario> relatedUsers;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private List<Label> labelMessages = new ArrayList<>();
    private List<String> uniqueIDS = new ArrayList<>();

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

        // Escondemos los datos de usuario y la funcionalidad de mandar mensajes hasta que se seleccione un usuario
        panelDatosYMensajesUsuarios.setVisible(false);

        // Creamos las listas de usuarios y mensajes
        crearTreeTableViewUsuarios();
        crearTreeTableViewMensajes();
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

    @FXML
    private JFXTextField idTicketJFXTextFieldMensajes;

    @FXML
    private AnchorPane conversacionMensajes;

    @FXML
    private VBox vboxConversacionMensajes;

    @FXML
    private JFXButton responderTicketMensajes;

    @FXML
    private Label seleccionaUsuarioUsuarios;
    
    @FXML
    private DayPage calendario;
    
    @FXML
    private JFXButton guardarCalendario;
    
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
        JFXTreeTableColumn<messageTTView, String> idCol = new JFXTreeTableColumn<>("ID Ticket");
        JFXTreeTableColumn<messageTTView, String> senderCol = new JFXTreeTableColumn<>("De");
        JFXTreeTableColumn<messageTTView, String> asuntoCol = new JFXTreeTableColumn<>("Asunto");

        idCol.setCellValueFactory(param -> param.getValue().getValue().getIdTicket());
        idCol.setMinWidth(100);
        idCol.setMaxWidth(100);
        senderCol.setCellValueFactory(param -> param.getValue().getValue().getSender());
        senderCol.setMinWidth(169);
        senderCol.setMaxWidth(169);
        asuntoCol.setCellValueFactory(param -> param.getValue().getValue().getSubject());
        asuntoCol.setMinWidth(308);
        asuntoCol.setMaxWidth(308);

        ObservableList<messageTTView> messages = FXCollections.observableArrayList();
        // Añadimos los mensajes
        for (Message mensaje : modelo.getMessages()) {
            // Comprobamos que solo se añadan tickets para el usuario, y no todos los mensajes del json
            if (!uniqueIDS.contains(mensaje.getIdTicket()) && (mensaje.getSender().equals(usuario.getName()+" "+usuario.getSurname())
                    || mensaje.getReceiver().equals(usuario.getName()+" "+usuario.getSurname()))) {
                messages.add(new messageTTView(mensaje.getSender(), mensaje.getReceiver(), mensaje.getSubject(), mensaje.getMessage(), mensaje.getIdTicket()));
                uniqueIDS.add(mensaje.getIdTicket());
            }
        }

        TreeItem<messageTTView> root = new RecursiveTreeItem<>(messages, RecursiveTreeObject::getChildren);
        treeTableViewMensajes.getColumns().setAll(idCol, senderCol, asuntoCol);
        treeTableViewMensajes.setRoot(root);
        treeTableViewMensajes.setShowRoot(false);
    }

    @FXML
    void mostrarDatosYMensajeUsuarios(MouseEvent event) {
	    // Comprobamos que no este visible
	    if (!panelDatosYMensajesUsuarios.isVisible()){
	        panelDatosYMensajesUsuarios.setVisible(true);
	        seleccionaUsuarioUsuarios.setVisible(false);

        }
	    if (panelDatosYMensajesUsuarios.isVisible()) { // Cambiamos los datos del usuario
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
    void mostrarTicketMensajes(MouseEvent event) {
        // Comprobamos que no este visible
        if (!mensajePaneMensajes.isVisible()){
            mensajePaneMensajes.setVisible(true);
        }
        if (mensajePaneMensajes.isVisible()) { // Cambiamos los datos del mensaje
            // Borramos la conversacion en casa de que hubiese una seleccionada para poder introducir la siguiente
            labelMessages.clear();
            vboxConversacionMensajes.getChildren().clear();

            asuntoJFXTextFieldMensajes.setText(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getSubject().get());
            destinatarioJFXTextFieldMensajes.setText(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getReceiver().get());
            idTicketJFXTextFieldMensajes.setText(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getIdTicket().get());
            int i = 0;
            for (Message mensaje : modelo.getMessages()) {
                if (mensaje.getIdTicket().equals(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getIdTicket().get())) {
                    labelMessages.add(new Label(mensaje.getMessage()));
                    labelMessages.get(i).setPrefWidth(1202);
                    labelMessages.get(i).setWrapText(true);
                    if (!mensaje.getSender().equals(usuario.getName()+" "+usuario.getSurname())) {
                        labelMessages.get(i).setPadding(new Insets(0,0,0,669));
                    }
                    else {
                        labelMessages.get(i).setPadding(new Insets(0,669,0,0));
                    }
                    vboxConversacionMensajes.getChildren().add(labelMessages.get(i));
                    vboxConversacionMensajes.setSpacing(10);
                    i++;
                }
            }
            seleccionaMensajeLabelMensajes.setVisible(false);
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
            List<Message> updatedMessages = modelo.getMessages();
            updatedMessages.add(msg);
            modelo.setMessages(updatedMessages);
            modelo.serializarAJson("./Proyecto1/src/application/jsonFiles/messages.json", modelo.getMessages(),false);
            alert.setHeaderText("Informacion");
            alert.setContentText("Se ha enviado el mensaje correctamente");
            alert.showAndWait();
            // Borramos los campos para evitar confusion
            asuntoJFXTextFieldUsuarios.clear();
            mensajeJFXTextFieldUsuarios.clear();

            // Actualizamos la lista de mensajes
            treeTableViewMensajes.getRoot().getChildren().add(new TreeItem<>(new messageTTView(msg.getSender(), msg.getReceiver(), msg.getSubject(),
                                                                                                msg.getMessage(), msg.getIdTicket())));
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

    @FXML
    void responderTicketMensajes(ActionEvent event) {

    }
    
    @FXML
    void guardarCalendario(ActionEvent event) {
    }
}

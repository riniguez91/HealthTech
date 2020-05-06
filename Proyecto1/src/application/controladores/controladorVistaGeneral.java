package application.controladores;

import application.modelos.*;

import com.calendarfx.view.AgendaView;
import com.calendarfx.view.page.DayPage;
import com.jfoenix.controls.*;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class controladorVistaGeneral implements Initializable, MapComponentInitializedListener {

    private modelo modelo;
    private Usuario usuario;
    private final List<Label> labelMessages = new ArrayList<>();
    private final List<Label> labelMessagesInicio = new ArrayList<>();
    private final List<Label> labelFAQ = new ArrayList<>();
    private final List<String> uniqueMessageIDS = new ArrayList<>();
    private controladorVistaGeneral cp;
    private final ConexionBBDD conexionBBDD = new ConexionBBDD();

    public void initModelo(modelo modelo_, Usuario usuario_, controladorVistaGeneral cp_, String tipoVista) {
        if (this.modelo != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.modelo = modelo_;
        this.usuario = usuario_;
        this.cp = cp_;

        modelo.setMessages(conexionBBDD.getMensajesDeUsuario(usuario.getID_User()));

        aniadirPreguntasFrecuentes();

        // Datos pestaña inicio
        labelNombreInicio.setText(usuario.getName());
        labelApellidosInicio.setText(usuario.getSurnames());
        labelRolInicio.setText(usuario.getRol());
        labelUsernameInicio.setText(usuario.getUser());
        labelFechaNacimientoInicio.setText(usuario.getDOB());
        labelEdadInicio.setText(usuario.getAge() + "");
        labelDNIInicio.setText(usuario.getDNI());
        labelTelefonoInicio.setText(usuario.getTelephone() + "");

        // Establecemos la foto del usuario en la pestaña de Inicio
        if (usuario.getPhoto() == null)
            userImageViewInicio.setImage(new Image("@..\\..\\resources\\fotos\\user.png"));
        else
        	userImageViewInicio.setImage(new Image(usuario.getPhoto()));

        // Creamos las listas de usuarios y mensajes
        crearTreeTableView(treeTableViewUsuarios, false);
        crearTreeTableView(treeTableViewRegistros, true);
        crearTreeTableView(treeTableViewLocalizacion, true);
        
        crearTreeTableViewMensajes();

        // Comprobamos mensajes nuevos
        comprobarMensajesNuevos();

        if (tipoVista.equals("general")) tabPaneGeneral.getTabs().remove(4, 6);
    }

    // -------------------- Tab Inicio --------------------

    @FXML private JFXTabPane tabPaneGeneral;

    @FXML private Tab tabInicio;

    @FXML private ImageView userImageViewInicio;

    @FXML private Label labelNombreInicio;

    @FXML private Label labelApellidosInicio;

    @FXML private Label labelUsernameInicio;

    @FXML private Label labelRolInicio;

    @FXML private Label labelFechaNacimientoInicio;

    @FXML private Label labelEdadInicio;

    @FXML private Label labelDNIInicio;

    @FXML private Label labelTelefonoInicio;

    @FXML private JFXButton cerrarSesionBtn;

    @FXML private ScrollPane scrollPaneMensajesInicio;

    @FXML private AnchorPane conversacionMensajesInicio;

    @FXML private VBox vboxConversacionMensajesInicio;

    @FXML private AgendaView agendaViewInicio;

    @FXML private ScrollPane scrollPaneFAQ;

    @FXML private AnchorPane apaneFAQ;

    @FXML private VBox vboxFAQ;



    // -------------------- Tab Calendario --------------------

    @FXML private Tab tabCalendario;

    @FXML private DayPage calendario;

    @FXML private JFXButton guardarCalendario;



    // // -------------------- Tab Usuarios --------------------

    @FXML private Tab tabUsuarios;

    @FXML private Label seleccionaUsuarioLabel;

    @FXML private JFXTextField filtrarUsuarioTFieldUsuarios;

    @FXML private JFXTreeTableView<usuarioTTView> treeTableViewUsuarios;

    @FXML private VBox datosUsuarioVBox;

    @FXML private ImageView userImageViewUsuarios;

    @FXML private Label labelNombreUsuarios;

    @FXML private Label labelApellidosUsuarios;

    @FXML private Label labelFechaNacimientoUsuarios;

    @FXML private Label labelRolUsuarios;

    @FXML private Label labelEdadUsuarios;

    @FXML private JFXTextField destinatarioJFXTextFieldUsuarios;

    @FXML private JFXTextField asuntoJFXTextFieldUsuarios;

    @FXML private JFXTextArea mensajeJFXTextFieldUsuarios;

    @FXML private HBox generarTicketHBox;

    @FXML private JFXButton cancelarTicketBttnMensajes;

    @FXML private JFXButton crearTicketBttnMensajes;



    // -------------------- Tab Mensajes --------------------

    @FXML private Tab tabMensajes;

    @FXML private Label seleccionaMensajeLabelMensajes;

    @FXML private JFXTextField filtrarMensajeTFieldMensajes;

    @FXML private JFXTreeTableView<messageTTView> treeTableViewMensajes;

    @FXML private VBox datosVBoxMensajes;

    @FXML private JFXTextField asuntoJFXTextFieldMensajes;

    @FXML private JFXTextField destinatarioJFXTextFieldMensajes;

    @FXML private JFXTextField idTicketJFXTextFieldMensajes;

    @FXML private ScrollPane scrollPaneMensajes;

    @FXML private AnchorPane conversacionMensajes;

    @FXML private VBox vboxConversacionMensajes;

    @FXML private JFXButton botonResponderTicket;



    // -------------------- Tab Registros --------------------

    @FXML private Tab tabRegistros;

    @FXML private JFXTreeTableView<usuarioTTView> treeTableViewRegistros;

    @FXML private DatePicker calendarioSensores;

    @FXML private ScrollPane scrollPaneRegistros;

    @FXML private AnchorPane apaneRegistros;

    @FXML private VBox vboxRegistros_apane;

    @FXML private LineChart<Double, Double> graficaTemperatura;

    @FXML private StackedBarChart<Double, Double> graficaMagnetico;

    @FXML private PieChart graficaPresion;

    @FXML private Label horasDurmiendo;

    @FXML private StackedBarChart<Double, Double> graficaGas;

    private final ObservableList<PieChart.Data> detalles = FXCollections.observableArrayList();



    // -------------------- Tab Localizacion --------------------

    @FXML private Tab tabLocalizacion;

    @FXML private JFXTreeTableView<usuarioTTView> treeTableViewLocalizacion;

    @FXML private TextField addressTextField;

    @FXML private JFXButton buttonActualizarUbicacion;

    @FXML private JFXButton buttonUbicacionCasa;

    @FXML private GoogleMapView mapView;

    private GoogleMap map;

    private GeocodingService geocodingService;

    private final StringProperty address = new SimpleStringProperty();




    // -------------------- Metodos tab Inicio --------------------

    public void aniadirPreguntasFrecuentes() {
        int i = 0;
        String[] preguntasYRespuestas = {"1. ¿Cómo puedo enviar un mensaje a mi médico?\n", "En la pestaña \"Mensajes\" en la parte inferior izquierda hay que pulsar el botón \"Crear Nuevo Ticket\" ahi te aparece para introducir el destinatario, asunto y mensaje."
                , "2. ¿Dónde puedo cerrar sesión?\n", "En la pestaña de \"Inicio\" en la parte inferior izquierda, hay que pulsar el botón\"Cerrar Sesión\"."
                , "3. ¿Puedo buscar un Usuario por su nombre o apellido?\n", "Sí. En la pestaña de Usuarios, arriba a la izquierda pinchas donde pone buscar e introduces el nombre o apellido."
                , "4. ¿Dónde puedo ver todos mis mensajes?\n", "En la pestaña \"Mensajes\" sale la lista de mensajes recibidos y enviados, pudiendo leerlos pinchando en ellos."};
        for (String PyR : preguntasYRespuestas) {
            labelFAQ.add(new Label(PyR));
            if (i % 2 == 0)
                detailLabel(labelFAQ.get(i), Font.font("Century Gothic", FontWeight.BOLD, 20), 922, new Insets(0,0,0,10));
            else
                detailLabel(labelFAQ.get(i), Font.font("Century Gothic", 20), 922, new Insets(0,0,0,20));

            vboxFAQ.getChildren().add(labelFAQ.get(i));
            vboxFAQ.setSpacing(10);
            i++;
        }
    } // aniadirPreguntasFrecuentes()

    public void comprobarMensajesNuevos() {
        // Borramos la conversacion en casa de que hubiese una seleccionada para poder introducir la siguiente
        labelMessagesInicio.clear();
        vboxConversacionMensajesInicio.getChildren().clear();
        try {
            int i = 0;
            // En este caso no usamos una lambda para no tener que usar un AtomicInteger, por lo tanto simplificando el codigo
            for (Message mensaje : modelo.getMessages()) {
                // Si no esta leido y el senderID coincide con el del usuario
                if (!mensaje.getRead() && mensaje.getReceiverID() == usuario.getID_User()) {
                    ResultSet sender = conexionBBDD.selectUserFromID(mensaje.getSenderID());
                    sender.next(); // Sabemos que si que habra un resultado
                    labelMessagesInicio.add(new Label("- Asunto: " + mensaje.getSubject() + " || De parte de: " + sender.getString("Name")
                            + " " + sender.getString("Surnames")));
                    labelMessagesInicio.get(i).setPrefWidth(1330);
                    labelMessagesInicio.get(i).setWrapText(true);
                    labelMessagesInicio.get(i).setFont(new Font("Century Gothic", 26));
                    labelMessagesInicio.get(i).setPadding(new Insets(0, 0, 0, 20));
                    vboxConversacionMensajesInicio.getChildren().add(labelMessagesInicio.get(i));
                    vboxConversacionMensajesInicio.setSpacing(15);
                    i++;
                }
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }
        if (labelMessagesInicio.size() == 0) {
            labelMessagesInicio.add(new Label(" - No tiene mensajes nuevos"));
            labelMessagesInicio.get(0).setFont(new Font("Century Gothic", 26));
            vboxConversacionMensajesInicio.getChildren().add(labelMessagesInicio.get(0));
        }
    } // comprobarMensajesNuevos()

    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
        Stage stageBttnBelongsTo = (Stage) cerrarSesionBtn.getScene().getWindow();
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getResource("/application/vistas/vistaLogin.fxml"));
        Parent rootLogin;
        rootLogin = loaderLogin.load();
        controladorLogin contrLogin = loaderLogin.getController();
        contrLogin.initModelo(modelo, usuario);
        stageBttnBelongsTo.setScene(new Scene(rootLogin));
    } // cerrarSesion()

    // -------------------- Fin metodos Tab inicio --------------------








    // -------------------- Metodos tab Calendario --------------------

    @FXML
    void guardarCalendario(ActionEvent event) {
        if (calendario.getAgendaView().getListView().getItems().size() != 0) {
            agendaViewInicio.getListView().setItems(calendario.getAgendaView().getListView().getItems());
            modelo.createAlert("Informacion", "El evento se ha añadido correctamente");
            // System.out.println(calendario.getAgendaView().getListView().getItems().get(0).getEntries().get(0).calendarProperty().get());
            // calendario.getAgendaView().get
            LocalDate d = LocalDate.now();
            AgendaView.AgendaEntry n = new AgendaView.AgendaEntry(d);
            agendaViewInicio.getListView().getItems().add(n);
            calendario.getAgendaView().getListView().getItems().add(n);
        } else {
            modelo.createAlert("Informacion", "Primero debe de añadir un evento al calendario");
        }
    }
    // -------------------- Fin metodos tab Calendario --------------------








    // -------------------- Metodos tab Usuarios --------------------

    @FXML
    void filterUsersUsuario(KeyEvent event) {
        treeTableViewUsuarios.setPredicate(usuarioTreeItem -> usuarioTreeItem.getValue().getName().get().toLowerCase().startsWith(filtrarUsuarioTFieldUsuarios.getText().toLowerCase()) ||
                usuarioTreeItem.getValue().getSurname().get().toLowerCase().startsWith(filtrarUsuarioTFieldUsuarios.getText().toLowerCase())
                || usuarioTreeItem.getValue().getRolUsuario().get().toLowerCase().startsWith(filtrarUsuarioTFieldUsuarios.getText().toLowerCase()));
    } // filterUserUsuario()

    public void crearTreeTableView(TreeTableView<usuarioTTView> ttv, boolean soloPacientes) {
        JFXTreeTableColumn<usuarioTTView, String> nombreCol = new JFXTreeTableColumn<>("Nombre");
        JFXTreeTableColumn<usuarioTTView, String> apellidosCol = new JFXTreeTableColumn<>("Apellidos");
        JFXTreeTableColumn<usuarioTTView, String> rolCol = new JFXTreeTableColumn<>("Rol");

        nombreCol.setCellValueFactory(param -> param.getValue().getValue().getName());
        nombreCol.setMinWidth(129);
        nombreCol.setMaxWidth(129);
        apellidosCol.setCellValueFactory(param -> param.getValue().getValue().getSurname());
        apellidosCol.setMinWidth(189);
        apellidosCol.setMaxWidth(189);
        rolCol.setCellValueFactory(param -> param.getValue().getValue().getRolUsuario());
        rolCol.setMinWidth(159);
        rolCol.setMaxWidth(159);

        ObservableList<usuarioTTView> users = FXCollections.observableArrayList();
        
        // Añadimos los usuarios
        if (soloPacientes) {
        	for (Usuario user : modelo.usuariosRelacionados(usuario))
        		if (user.getRol().equals("paciente")) 
                    users.add(new usuarioTTView(user.getID_User(), user.getName(), user.getSurnames(), user.getRol(), user.getDOB(), user.getAge(), user.getPhoto(), user.getAdress()));
		} else {
	        // Añadimos los usuarios
			for (Usuario user : modelo.usuariosRelacionados(usuario))
				users.add(new usuarioTTView(user.getID_User(), user.getName(), user.getSurnames(), user.getRol(), user.getDOB(), user.getAge(), user.getPhoto(), user.getAdress()));
		}
 
        TreeItem<usuarioTTView> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);

        // TTV Usuarios
        ttv.getColumns().add(0, nombreCol);
        ttv.getColumns().add(1, apellidosCol);
        ttv.getColumns().add(2, rolCol);
        ttv.setRoot(root);
    } // crearTreeTableViewUsuarios()


    @FXML
    void mostrarDatosUsuarios(MouseEvent event) {
        // Cambiamos los datos del usuario mientras se haya seleccionado uno
        if (treeTableViewUsuarios.getSelectionModel().getSelectedItem() != null) {
            // Comprobamos si el panel de datos de usuario y creacion de mensajes esta visible
            if (!datosUsuarioVBox.isVisible()) {
                datosUsuarioVBox.setVisible(true);
                seleccionaUsuarioLabel.setVisible(false);
            }
            // Si esta visible actualizamos los datos del usuario seleccionado
            if (datosUsuarioVBox.isVisible()) {
                labelNombreUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getName().get());
                labelApellidosUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getSurname().get());
                labelRolUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getRolUsuario().get());
                labelFechaNacimientoUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getBirthday().get());
                labelEdadUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getAge().get() + "");
                destinatarioJFXTextFieldUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getName().get() + " "
                        + treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getSurname().get());
                userImageViewUsuarios.setImage(new Image(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getImagenPerfil().get()));

                // Reseteamos el campo de asunto y textfield en caso de que el usuario no cancelo la respuesta
                asuntoJFXTextFieldUsuarios.clear();
                mensajeJFXTextFieldUsuarios.clear();
            }
        }
    } // mostrarDatosYMensajeUsuarios()

    @FXML
    void crearTicket(ActionEvent event) {
        if (asuntoJFXTextFieldUsuarios.getText().isEmpty()) {
            modelo.createAlert("Cuidado", "Debes de poner un asunto");
        } else if (mensajeJFXTextFieldUsuarios.getText().isEmpty()) {
            modelo.createAlert("Cuidado", "Debes de poner un mensaje");
        } else {
            UUID uniqueKey = UUID.randomUUID();
            Message msg = new Message(usuario.getID_User(), treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getID_User().get(),
                                      asuntoJFXTextFieldUsuarios.getText(), mensajeJFXTextFieldUsuarios.getText(), uniqueKey.toString(), false);

            conexionBBDD.insertarMensaje(msg.getIdTicket(), msg.getMessage(), msg.getSubject(), msg.getSenderID(), msg.getReceiverID());
            modelo.createAlert("Informacion", "Se ha enviado el mensaje correctamente");

            // Borramos los campos para evitar confusion
            asuntoJFXTextFieldUsuarios.clear();
            mensajeJFXTextFieldUsuarios.clear();

            // Actualizamos la lista de mensajes
            treeTableViewMensajes.getRoot().getChildren().add(new TreeItem<>(new messageTTView(msg.getSenderID(), msg.getReceiverID(),
                                                               usuario.getName() + usuario.getSurnames(),
                                                               treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getName().get()
                                                                       + treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getSurname().get(),
                                                               msg.getSubject(), msg.getMessage(), msg.getIdTicket(), msg.getRead(), usuario.getUser())));
            // Junto a los mensajes no leidos
            comprobarMensajesNuevos();
        }
    } // enviarMensajeUsuarios()

    @FXML
    void cancelarTicket(ActionEvent event) {
        if (asuntoJFXTextFieldUsuarios.getText().isEmpty() && mensajeJFXTextFieldUsuarios.getText().isEmpty())
            modelo.createAlert("Informacion", "Primero debe introducir un asunto o un mensaje");
        else {
            asuntoJFXTextFieldUsuarios.clear();
            mensajeJFXTextFieldUsuarios.clear();
        }
    } // cancelarMensajeUsuarios()

    // -------------------- Fin metodos tab Usuarios --------------------








    // -------------------- Metodos tab Mensajes --------------------

    @FXML
    void filterTicketsMensajes(KeyEvent event) {
        treeTableViewMensajes.setPredicate(mensajeTreeItem -> mensajeTreeItem.getValue().getSubject().get().toLowerCase().startsWith(filtrarMensajeTFieldMensajes.getText().toLowerCase()) ||
                mensajeTreeItem.getValue().getIdTicket().get().startsWith(filtrarMensajeTFieldMensajes.getText()));
    } // filterTicketsMensajes()

    public void crearTreeTableViewMensajes() {
        JFXTreeTableColumn<messageTTView, String> idCol = new JFXTreeTableColumn<>("ID Ticket");
        JFXTreeTableColumn<messageTTView, String> senderCol = new JFXTreeTableColumn<>("De");
        JFXTreeTableColumn<messageTTView, String> asuntoCol = new JFXTreeTableColumn<>("Asunto");

        idCol.setCellValueFactory(param -> param.getValue().getValue().getIdTicket());
        idCol.setMinWidth(129);
        idCol.setMaxWidth(129);
        senderCol.setCellValueFactory(param -> param.getValue().getValue().getSender());
        senderCol.setMinWidth(189);
        senderCol.setMaxWidth(189);
        asuntoCol.setCellValueFactory(param -> param.getValue().getValue().getSubject());
        asuntoCol.setMinWidth(159);
        asuntoCol.setMaxWidth(159);

        ObservableList<messageTTView> messages = FXCollections.observableArrayList();

        // Añadimos los mensajes
        try {
            for (Message msg : modelo.getMessages() ) {
                if (!uniqueMessageIDS.contains(msg.getIdTicket())) { // Comprobamos que solo añadimos el ticket, no la conversacion entera
                    ResultSet sender = conexionBBDD.selectUserFromID(msg.getSenderID()), receiver = conexionBBDD.selectUserFromID(msg.getReceiverID());
                    if (sender.next() && receiver.next()) { // Si hay un mensaje
                        messages.add(new messageTTView(msg.getSenderID(), msg.getReceiverID(), sender.getString("Name") + " " + sender.getString("Surnames"),
                                receiver.getString("Name") + " " + receiver.getString("Surnames"), msg.getSubject(), msg.getMessage(), msg.getIdTicket(),
                                msg.getRead(), sender.getString("User")));
                        uniqueMessageIDS.add(msg.getIdTicket());
                    }
                }
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getClass().getName() + ": " + sqle.getMessage());
        }

        TreeItem<messageTTView> root = new RecursiveTreeItem<>(messages, RecursiveTreeObject::getChildren);
        treeTableViewMensajes.getColumns().add(idCol);
        treeTableViewMensajes.getColumns().add(senderCol);
        treeTableViewMensajes.getColumns().add(asuntoCol);
        treeTableViewMensajes.setRoot(root);
    } // createTreeTableViewMensajes()

    @FXML
    void mostrarTicketMensajes(MouseEvent event) {
        if (treeTableViewMensajes.getSelectionModel().getSelectedItem() != null) { // Cambiamos los datos del mensaje
            // Comprobamos si el panel de mensajes esta visible
            if (!datosVBoxMensajes.isVisible())
                datosVBoxMensajes.setVisible(true);

            // Borramos la conversacion en caso de que hubiese una seleccionada para poder introducir la siguiente
            labelMessages.clear();
            vboxConversacionMensajes.getChildren().clear();
            asuntoJFXTextFieldMensajes.setText(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getSubject().get());
            destinatarioJFXTextFieldMensajes.setText(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getReceiver().get());
            idTicketJFXTextFieldMensajes.setText(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getIdTicket().get());

            // Cojemos todos los mensajes pertenecientes a un ticket
            Vector<Message> messagesInATicket = conexionBBDD.getMensajesDeTicket(idTicketJFXTextFieldMensajes.getText());

            // Marcamos el mensaje/s como leido
            setMsgAsRead(messagesInATicket);

            // Cambiamos el scroll pane para mostrar la lista de mensajes correspondientes al ticket seleccionado
            changeTicketConversation(messagesInATicket);
        }
    } // mostrarTicketMensajes()

    public void setMsgAsRead(Vector<Message> messages) {
        // Cambiamos el mensaje como leido si no lo estaba
        for (Message msg : messages) {
            if (!msg.getRead() && msg.getReceiverID() == usuario.getID_User())
                conexionBBDD.setMsgAsRead(msg);
        }

        // Actualizamos la lista de mensajes
        modelo.setMessages(conexionBBDD.getMensajesDeUsuario(usuario.getID_User()));

        // Borramos el mensaje de la pestaña recordatorios
        comprobarMensajesNuevos();

    } // setMsgAsRead()

    public void changeTicketConversation(Vector<Message> messages) {
        // En este caso no usamos una lambda para no tener que usar un AtomicInteger, por lo tanto simplificando el codigo
        int i = 0;
        for (Message mensaje : messages) {
            VBox vb = new VBox();
            vb.setFillWidth(false);
            vb.setPrefWidth(scrollPaneMensajes.getPrefWidth());

            TextFlow tf = new TextFlow();
            Text t1 = new Text();
            t1.setFont(Font.font("Century Gothic", FontWeight.BOLD, 15));

            labelMessages.add(new Label(mensaje.getMessage()));
            labelMessages.get(i).setMaxWidth(scrollPaneMensajes.getPrefWidth()/2);
            labelMessages.get(i).setWrapText(true);
            labelMessages.get(i).setFont(new Font("Century Gothic", 18));
            if (mensaje.getSenderID() == usuario.getID_User()) {
                t1.setText(usuario.getUser().toUpperCase()+"\n");
                vb.setAlignment(Pos.CENTER_RIGHT);
                labelMessages.get(i).setBackground(new Background(new BackgroundFill(Color.WHEAT, new CornerRadii(5, 5, 5, 5, false), Insets.EMPTY)));
                labelMessages.get(i).setPadding(new Insets(0, 10, 0, 10));
            } else {
                t1.setText(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getSenderUName().get().toUpperCase()+"\n");
                labelMessages.get(i).setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, new CornerRadii(5, 5, 5, 5, false), Insets.EMPTY)));
                labelMessages.get(i).setPadding(new Insets(0,0,0,10));
            }
            tf.getChildren().addAll(t1, labelMessages.get(i));
            vb.getChildren().add(tf);
            vboxConversacionMensajes.getChildren().add(vb);
            vboxConversacionMensajes.setSpacing(15);
            i++;
        }
        // Bajamos el scrollPane hasta el ultimo mensaje
        vboxConversacionMensajes.heightProperty().addListener(observable -> scrollPaneMensajes.setVvalue(1.0));
    } // changeTicketConversation()


    public void crearMensajeYResponderTicket(String mensaje) {
        Message msg = new Message(usuario.getID_User(),
                usuario.getID_User() == treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getSenderID().get() ?
                        treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getReceiverID().get() :
                        treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getSenderID().get(),
                treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getSubject().get(),
                mensaje,
                treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getIdTicket().get(),
                false
        );
        // Actualizamos los mensajes
        List<Message> updatedMessages = modelo.getMessages();
        updatedMessages.add(msg);
        modelo.setMessages(updatedMessages);

        // Insertamos el mensaje en la BBDD
        conexionBBDD.insertarMensaje(msg.getIdTicket(), msg.getMessage(), msg.getSubject(), msg.getSenderID(), msg.getReceiverID());
        modelo.createAlert("Informacion", "Se ha enviado el mensaje correctamente");

        // Borramos la conversacion en caso de que hubiese una seleccionada para poder introducir la siguiente
        labelMessages.clear();
        vboxConversacionMensajes.getChildren().clear();
        changeTicketConversation(conexionBBDD.getMensajesDeTicket(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getIdTicket().get()));

    } // crearMensajeYResponderTicket()

    @FXML
    void responderTicketMensajes(ActionEvent event) throws IOException {
        // Cargamos 2nda escena
        FXMLLoader loaderResponderTicket = new FXMLLoader(getClass().getResource("/application/vistas/vistaEnviarMensaje.fxml"));
        Parent rootResponderTicket = loaderResponderTicket.load();

        // Cojemos el controlador de la 2nd escena
        controladorResponderTicket controladorCU = loaderResponderTicket.getController();
        controladorCU.initModelo(modelo, cp);

        // Display stage
        Stage stage = new Stage();
        stage.setScene(new Scene(rootResponderTicket));
        stage.show();
    } // responderTicketMensajes()

    // -------------------- Fin metodos tab Mensajes --------------------









    // -------------------- Metodos tab Registros --------------------

    @FXML
    void mostrarSensoresDia(ActionEvent event) {
        if (treeTableViewRegistros.getSelectionModel().getSelectedItem() != null) {
            llenarGraficasSensores();
            dumpRegistrosSensores();
        }
        else
            modelo.createAlert("Cuidado", "Primero debe escojer un usuario");
    }

    private void detailLabel(Label label, Font font, int prefWidth, Insets insets) {
        label.setFont(font);
        label.setPrefWidth(prefWidth);
        label.setWrapText(true);
        label.setPadding(insets);
    }

    private void dumpRegistrosSensores() {
        // Limpiamos los registros
        vboxRegistros_apane.getChildren().clear();

        HashMap<String, Vector<String>> ss = conexionBBDD.recogerAlertasTemp(treeTableViewRegistros.getSelectionModel().getSelectedItem().getValue().getID_User().get(),
                20, "Temperatura", "less");
        for (Map.Entry<String, Vector<String>> entry : ss.entrySet()) {
            Label dateInfo = new Label("--------------" + entry.getKey() + " --------------");
            detailLabel(dateInfo, Font.font("Century Gothic", FontWeight.BOLD, 17), 595, new Insets(0, 0, 0, 5));
            vboxRegistros_apane.getChildren().add(dateInfo);
            for (String registro : entry.getValue()) {
                Label registroSensor = new Label("\t" + registro);
                detailLabel(registroSensor, Font.font("Century Gothic", 14), 595, new Insets(0,0,0,5));
                vboxRegistros_apane.getChildren().add(registroSensor);
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void poblarStackedBarChart(DateTimeFormatter formatter, XYChart.Series series, StackedBarChart<Double, Double> grafica, String sentencia,
                                      String tipoSensor) {
        for (sensor sc : conexionBBDD.leerDatosSensor(treeTableViewRegistros.getSelectionModel().getSelectedItem().getValue().getID_User().get(),
                tipoSensor, formatter.format(calendarioSensores.getValue().atStartOfDay()),
                formatter.format(calendarioSensores.getValue().atTime(23, 59, 59)), sentencia) ) {
            Timestamp timestamp = new Timestamp(sc.getDate_Time_Activation().getTime());
            series.getData().add(new XYChart.Data(timestamp.toLocalDateTime().getHour()+"", sc.getReading()));
        }
        grafica.getData().addAll(series);
    }

    @FXML
    void mostrarDatosSensoresPacientes(MouseEvent event) {
        if (calendarioSensores.getValue() != null) {
            llenarGraficasSensores();
            dumpRegistrosSensores();
        }
    } // mostrarDatosSensoresPacientes()

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void llenarGraficasSensores() {
        //Limpiamos todas las gráficas
        graficaTemperatura.getData().clear();
        graficaMagnetico.getData().clear();
        graficaGas.getData().clear();
        graficaPresion.getData().clear();
        detalles.clear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Formato que le daremos a la fecha

        // Temperatura
        XYChart.Series seriesTemperatura = new XYChart.Series();

        // Magnetico
        XYChart.Series seriesMagnetico = new XYChart.Series();

        // Gas
        XYChart.Series seriesGas = new XYChart.Series();

        String sentenciaContinuo = "SELECT sensores_continuos.*\n" +
                "FROM sensores_continuos\n" +
                "INNER JOIN sensores ON sensores_continuos.Sensores_ID1 = sensores.ID_Sensor\n" +
                "INNER JOIN users ON sensores.Users_ID1 = users.ID_User\n" +
                "WHERE users.ID_User = ? AND sensores.`Type` = ? AND sensores_continuos.Date_Time_Activation BETWEEN ? AND ?";

        String sentenciaDiscreto = "SELECT sensores_discretos.*\n" +
                "FROM sensores_discretos\n" +
                "INNER JOIN sensores ON sensores_discretos.Sensores_ID2 = sensores.ID_Sensor\n" +
                "INNER JOIN users ON sensores.Users_ID1 = users.ID_User\n" +
                "WHERE users.ID_User = ? AND sensores.`Type` = ? AND sensores_discretos.Date_Time_Activation BETWEEN ? AND ?";

        if (treeTableViewRegistros.getSelectionModel().getSelectedItem() != null) {
            for (sensor sc : conexionBBDD.leerDatosSensor(treeTableViewRegistros.getSelectionModel().getSelectedItem().getValue().getID_User().get(),
                    "Temperatura", formatter.format(calendarioSensores.getValue().atStartOfDay()),
                    formatter.format(calendarioSensores.getValue().atTime(23, 59, 59)), sentenciaContinuo)  ) {
                Timestamp timestamp = new Timestamp(sc.getDate_Time_Activation().getTime());
                seriesTemperatura.getData().add(new XYChart.Data(timestamp.toLocalDateTime().getHour()+"", sc.getReading()));
            }
            graficaTemperatura.getData().addAll(seriesTemperatura);

            poblarStackedBarChart(formatter, seriesGas, graficaGas, sentenciaContinuo, "Gas");

            poblarStackedBarChart(formatter, seriesMagnetico, graficaMagnetico, sentenciaDiscreto, "Magnetico");

            double durmiendo = 0;
            for (sensor sd : conexionBBDD.leerDatosSensor(treeTableViewRegistros.getSelectionModel().getSelectedItem().getValue().getID_User().get(),
                    "Presion", formatter.format(calendarioSensores.getValue().atStartOfDay()),
                    formatter.format(calendarioSensores.getValue().atTime(23, 59, 59)), sentenciaDiscreto) ) {
                if (sd.getReading() == 1)
                    durmiendo += 1;
            }
            detalles.add(new PieChart.Data("Durmiendo", durmiendo));
            detalles.add(new PieChart.Data("Despierto", 24-durmiendo));
            graficaPresion.setData(detalles);
            horasDurmiendo.setText(durmiendo+"");
        }
    }

    // ---------------------------- Tab Localizacion --------------------------------
    
    public void initialize(URL url, ResourceBundle rb) {
        mapView.setKey("AIzaSyABUQnPXeldroN__fhm1LDiZh5sUtkSMBM"); // No usar
        mapView.addMapInializedListener(this);
        address.bind(addressTextField.textProperty());
    }
    
    @Override
    public void mapInitialized() {
        geocodingService = new GeocodingService();
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(40.371830555556, -3.9189527777778))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(16);

        map = mapView.createMap(mapOptions);

        // Añadir un Marker al mapa
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(new LatLong(40.371830555556, -3.9189527777778))
                .visible(Boolean.TRUE)
                .title("My Marker");

        Marker marker = new Marker(markerOptions);
        map.addMarker(marker);
    }
    
    @FXML
    public void addressTextFieldAction(ActionEvent event) {
        geocodingService.geocode(address.get(), (GeocodingResult[] results, GeocoderStatus status) -> {
            LatLong latLong;

            // No hubo resultados
            if (status == GeocoderStatus.ZERO_RESULTS) modelo.createAlert("Error", "No se encontraron direcciones coincidentes");

            if (results.length > 1) modelo.createAlert("Informacion", "Multiples resultados encontrados, mostrando el primero.");

            latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            map.setCenter(latLong);
            
            // Añadir un Marker al mapa de la casa
            MarkerOptions markerOptions = new MarkerOptions();

            markerOptions.position(latLong)
                    .visible(Boolean.TRUE)
                    .title("My Marker");

            Marker marker = new Marker(markerOptions);
            map.addMarker(marker);
        });
    }

    @FXML
    void verUbicacionCasa(ActionEvent event) {
    	if (treeTableViewLocalizacion.getSelectionModel().getSelectedItem().getValue().getAdress().get() != null &&
                treeTableViewLocalizacion.getSelectionModel().getSelectedItem() != null) {
            geocodingService.geocode(treeTableViewLocalizacion.getSelectionModel().getSelectedItem().getValue().getAdress().get(), (GeocodingResult[] results, GeocoderStatus status) -> {
                LatLong latLong;
                // No hubo resultados
                if (status == GeocoderStatus.ZERO_RESULTS)
                	modelo.createAlert("Error", "No se encontraron direcciones coincidentes");

                if (results.length > 1) 
                	modelo.createAlert("Informacion", "Multiples resultados encontrados, mostrando el primero.");

                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
                map.setCenter(latLong);
                
                // Añadir un Marker al mapa de la casa
                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.position(latLong)
                        .visible(Boolean.TRUE)
                        .title("Casa de "+ treeTableViewLocalizacion.getSelectionModel().getSelectedItem().getValue().getName().get()+" "+treeTableViewLocalizacion.getSelectionModel().getSelectedItem().getValue().getSurname().get());
                Marker marker = new Marker(markerOptions);
         
                map.addMarker(marker);
            });
		} else {
			modelo.createAlert("Selección de usuario", "Debes seleccionar un usuario antes de poder ver la ubicación");
		}
    }
    
    @FXML
    void ubicacionPaciente(ActionEvent event) {
    	LatLong latlong = null;
        String sentenciaGPS = "SELECT sensor_GPS.*\n" +
                "FROM sensor_GPS\n" +
                "INNER JOIN sensores ON sensor_GPS.Sensores_ID3 = sensores.ID_Sensor\n" +
                "INNER JOIN users ON sensores.Users_ID1 = users.ID_User\n" +
                "WHERE users.ID_User = ? AND sensores.`Type` = ?";
        
        for (sensor sg : conexionBBDD.leerDatosSensorGPS(treeTableViewLocalizacion.getSelectionModel().getSelectedItem().getValue().getID_User().get(),
                                                "GPS" , sentenciaGPS)) {
        	latlong = new LatLong (sg.getLatitude(), sg.getLongitude());
        	map.setCenter(latlong);
        }

        // Añadir un Marker al mapa de la casa
        MarkerOptions markerOptions = new MarkerOptions();
        
        markerOptions.position(latlong)
                .visible(Boolean.TRUE)
                .title("Ubicacion en tiempo real de "+ treeTableViewLocalizacion.getSelectionModel().getSelectedItem().getValue().getName().get()+" "+treeTableViewLocalizacion.getSelectionModel().getSelectedItem().getValue().getSurname().get());
        		
        Marker marker = new Marker(markerOptions);
        marker.setTitle("Ubicacion");
 
        map.addMarker(marker);

    }

    @FXML
    void mostrarDatosMapaPacientes(MouseEvent event) throws ParseException {
        calendarioSensores.setValue(LocalDate.now()); // Asignamos la fecha actual al seleccionar un usuario
    }

    // -------------------- Fin metodos tab Localizacion --------------------

} // controladorVistaGeneral()


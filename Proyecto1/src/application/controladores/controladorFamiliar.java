package application.controladores;

import application.modelos.*;

import com.calendarfx.view.AgendaView;
import com.calendarfx.view.page.DayPage;
import com.jfoenix.controls.*;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
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
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class controladorFamiliar implements Initializable, MapComponentInitializedListener {
	
    private modelo modelo;
    private Usuario usuario;
    public List<Usuario> relatedUsers;
    @SuppressWarnings("unused")
	private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private List<Label> labelMessages = new ArrayList<>();
    private List<Label> labelMessagesInicio = new ArrayList<>();
    private List<String> uniqueIDS = new ArrayList<>();

    public void initModelo(modelo modelo_, Usuario usuario_){
        if (this.modelo != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.modelo = modelo_;
        this.usuario = usuario_;
        modelo.leerJsonMensajes("./Proyecto1/src/application/jsonFiles/messages.json");
        modelo.leerJsonTemperatura("./Proyecto1/src/application/jsonFiles/SensorTemp.json");
        modelo.leerJsonGas("./Proyecto1/src/application/jsonFiles/SensorGas.json");
        modelo.leerJsonMagnetico("./Proyecto1/src/application/jsonFiles/SensorMagnetico.json");
        modelo.leerJsonPresion("./Proyecto1/src/application/jsonFiles/SensorPresion.json");
        // Datos pestaña inicio
        labelNombreInicio.setText(usuario.getName());
        labelApellidosInicio.setText(usuario.getSurname());
        labelRolInicio.setText(usuario.getRol());
        labelUsernameInicio.setText(usuario.getUsername());
        labelFechaNacimientoInicio.setText(usuario.getBirthday());
        labelEdadInicio.setText(usuario.getAge() + "");
        labelDNIInicio.setText(usuario.getDni());
        labelTelefonoInicio.setText(usuario.getTelephone() + "");
        labelDomicilioInicio.setText(usuario.getDomicilio() + "");
        
        // Añadimos los usuarios relacionados
        relatedUsers = modelo.userInRelatedUsers(modelo.getUsuarios(), usuario);

        // Escondemos los datos de usuario y la funcionalidad de mandar mensajes hasta que se seleccione un usuario
        //panelDatosYMensajesUsuarios.setVisible(false);

        // Establecemos la foto del usuario en la pestaña de Inicio
        if (usuario.getImagenPerfil().isEmpty()) {
        	userImageViewInicio.setImage(new Image("@..\\..\\resources\\fotos\\user.png"));
		} else {
			userImageViewInicio.setImage(new Image(usuario.getImagenPerfil()));
		}

        // Creamos las listas de usuarios y mensajes
        crearTreeTableViewUsuarios();
        crearTreeTableViewMensajes();
        crearTreeTableViewPacientes();
        
        // Comprobamos mensajes nuevos
        comprobarMensajesNuevos();
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
    private Label Domicilio;

    @FXML
    private Label labelNombreInicio;

    @FXML
    private Label labelApellidosInicio;

    @FXML
    private Label labelUsernameInicio;

    @FXML
    private Label labelRolInicio;
    
    @FXML
    private Label labelFechaNacimientoInicio;

    @FXML
    private Label labelEdadInicio;

    @FXML
    private Label labelDNIInicio;

    @FXML
    private Label labelTelefonoInicio;
    
    @FXML
    private Label labelDomicilioInicio;
    
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
    private VBox VBox_PreguntasFrecuentes;
    
    @FXML
    private VBox VBox_Separator;
    
    @FXML
    private VBox VBox_PregFrec;

    @FXML
    private JFXButton atrasbtn;

    @FXML
    private JFXTextArea nombresJFXTextArea;

    @FXML
    private Label Nombre1;

    @FXML
    private JFXTextArea crearMensajeJFXTextAreaMensajes;

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
    private ScrollPane scrollPaneMensajes;

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
    private Label seleccionaUsuarioUsuariosLabel;
    
    @FXML
    private DayPage calendario;
    
    @FXML
    private JFXButton guardarCalendario;

    @FXML
    private JFXButton crearMensajeResponderTicketBttnMensajes;
    
    @FXML
    private JFXButton cancelarRespuestaTicketBtn;

    @FXML
    private ImageView userImageViewUsuarios;

    @FXML
    private ImageView userImageViewInicio;

    @FXML
    private AnchorPane aPaneCreacionTicket;

    @FXML
    private AnchorPane aPaneRespuestaTicket;

    @FXML
    private AgendaView prueba;

    @FXML
    private ScrollPane scrollPaneMensajesInicio;

    @FXML
    private AnchorPane conversacionMensajesInicio;

    @FXML
    private VBox vboxConversacionMensajesInicio;

    @FXML
    private VBox seleccionaUsuarioUsuarios;

    @FXML
    private VBox paneInicio;

    @FXML
    private VBox imagenVBox;

    @FXML
    private VBox datosVBox;

    @FXML
    private HBox generarTicketHBox;

    @FXML
    private VBox seleccionaMensajeVBox;

    @FXML
    private JFXButton botonResponderTicket;

    @FXML
    private VBox respuestaTicketVBox;

    @FXML
    private VBox datosVBoxMensajes;


    //Pestaña Inicio para ver y ocultar PreguntasFrecuentes
	@FXML
	void verInicio(ActionEvent event) {
		paneInicio.setVisible(true);
		VBox_PreguntasFrecuentes.setVisible(false);
		VBox_PregFrec.setVisible(true);
		VBox_Separator.setVisible(true);
	}
	@FXML
	void verPreguntasFrecuentes(ActionEvent event) {
		//panePreguntasFrecuentes.setVisible(true);
		paneInicio.setVisible(false);
		VBox_PreguntasFrecuentes.setVisible(true);
		VBox_PregFrec.setVisible(false);
		VBox_Separator.setVisible(false);
	}

    @FXML
    void filterUsersUsuario(KeyEvent event) {
        treeTableViewUsuarios.setPredicate( usuarioTreeItem -> usuarioTreeItem.getValue().getName().get().toLowerCase().startsWith(filtrarUsuarioTFieldUsuarios.getText().toLowerCase()) ||
        		usuarioTreeItem.getValue().getSurname().get().toLowerCase().startsWith(filtrarUsuarioTFieldUsuarios.getText().toLowerCase())
                || usuarioTreeItem.getValue().getRolUsuario().get().toLowerCase().startsWith(filtrarUsuarioTFieldUsuarios.getText().toLowerCase()));
	}

    @FXML
    void filterTicketsMensajes(KeyEvent event) {
        treeTableViewMensajes.setPredicate( mensajeTreeItem -> mensajeTreeItem.getValue().getSubject().get().toLowerCase().startsWith(filtrarMensajeTFieldMensajes.getText().toLowerCase()) ||
                mensajeTreeItem.getValue().getIdTicket().get().startsWith(filtrarMensajeTFieldMensajes.getText()));
    }

    @FXML
    void cancelarMensajeUsuarios(ActionEvent event) {
	    if (asuntoJFXTextFieldUsuarios.getText().isEmpty() && mensajeJFXTextFieldUsuarios.getText().isEmpty()){
            modelo.createAlert("Informacion", "Primero debe introducir un asunto o un mensaje");
        }
	    else {
            asuntoJFXTextFieldUsuarios.clear();
            mensajeJFXTextFieldUsuarios.clear();
        }
    }

	@SuppressWarnings("unchecked")
	public void crearTreeTableViewUsuarios() {
        JFXTreeTableColumn<usuarioTTView, String> nombreCol = new JFXTreeTableColumn<>("Nombre");
        JFXTreeTableColumn<usuarioTTView, String> apellidosCol = new JFXTreeTableColumn<>("Apellidos");
        JFXTreeTableColumn<usuarioTTView, String> rolCol = new JFXTreeTableColumn<>("Rol");

        nombreCol.setCellValueFactory(param -> param.getValue().getValue().getName());
        nombreCol.setMinWidth(119);
        nombreCol.setMaxWidth(119);
        apellidosCol.setCellValueFactory(param -> param.getValue().getValue().getSurname());
        apellidosCol.setMinWidth(189);
        apellidosCol.setMaxWidth(189);
        rolCol.setCellValueFactory(param -> param.getValue().getValue().getRolUsuario());
        rolCol.setMinWidth(169);
        rolCol.setMaxWidth(169);

        ObservableList<usuarioTTView> users = FXCollections.observableArrayList();
      
        for (Usuario user : relatedUsers) {
            users.add(new usuarioTTView(user.getName(), user.getSurname(), user.getRol(),user.getBirthday(), user.getAge(), user.getImagenPerfil()));
        }

        TreeItem<usuarioTTView> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        treeTableViewUsuarios.getColumns().setAll(nombreCol, apellidosCol, rolCol);
        treeTableViewUsuarios.setRoot(root);
        treeTableViewUsuarios.setShowRoot(false);
    }

    @SuppressWarnings("unchecked")
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
        modelo.getMessages().forEach(mensaje -> {
            if (!uniqueIDS.contains(mensaje.getIdTicket()) && (mensaje.getSender().equals(usuario.getName()+" "+usuario.getSurname())
                    || mensaje.getReceiver().equals(usuario.getName()+" "+usuario.getSurname()))) {
                messages.add(new messageTTView(mensaje.getSender(), mensaje.getReceiver(), mensaje.getSubject(), mensaje.getMessage(), mensaje.getIdTicket(),mensaje.getRead()));
                uniqueIDS.add(mensaje.getIdTicket());
            }
            
        });

        TreeItem<messageTTView> root = new RecursiveTreeItem<>(messages, RecursiveTreeObject::getChildren);
        treeTableViewMensajes.getColumns().setAll(idCol, senderCol, asuntoCol);
        treeTableViewMensajes.setRoot(root);
        treeTableViewMensajes.setShowRoot(false);
        }

    @FXML
    void mostrarDatosYMensajeUsuarios(MouseEvent event) {
        // Cambiamos los datos del usuario mientras se haya seleccionado uno
	    if (treeTableViewUsuarios.getSelectionModel().getSelectedItem() != null) {
            // Comprobamos si el panel de datos de usuario y creacion de mensajes esta visible
            if (!datosVBox.isVisible()){
                // Hacemos el panel visible
                imagenVBox.setVisible(true);
                datosVBox.setVisible(true);
                generarTicketHBox.setVisible(true);


                seleccionaUsuarioUsuarios.setVisible(false);
            }
            // Si esta visible actualizamos los datos del usuario seleccionado
            if (datosVBox.isVisible()) {
                labelNombreUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getName().get());
                labelApellidosUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getSurname().get());
                labelRolUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getRolUsuario().get());
                labelFechaNacimientoUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getBirthday().get());
                labelEdadUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getAge().get()+"");
                destinatarioJFXTextFieldUsuarios.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getName().get() + " "
                        + treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getSurname().get());
                userImageViewUsuarios.setImage(new Image(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getImagenPerfil().get()));
            }
        }
    }

    public void setMsgAsRead() {
        // Cambiamos el mensaje como leido si no lo estaba
        if (!treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getRead().get()) {
            for (Message msg : modelo.getMessages()) {
                // Compramos el mensaje que corresponde con el ID del mensaje y la persona que lo tiene que recibir (receiver)
                if (msg.getIdTicket().equals(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getIdTicket().get()) &&
                        msg.getReceiver().equals(usuario.getName() + " " + usuario.getSurname())){
                    msg.setRead(true);

                    // Lo marcamos como leido en el JSON de mensajes
                    modelo.serializarAJson("./Proyecto1/src/application/jsonFiles/messages.json", modelo.getMessages(), false);

                    // Actualizamos la lista de mensajes
                    modelo.leerJsonMensajes("./Proyecto1/src/application/jsonFiles/messages.json");

                    // Borramos el mensaje de la pestaña Recordatorios
                    comprobarMensajesNuevos();
                    break;
                }
            }
        }
    }

    public void changeTicketConversation() {
        // En este caso no usamos una lambda para no tener que usar un AtomicInteger, por lo tanto simplificando el codigo
        int i = 0;
        for (Message mensaje : modelo.getMessages()) {
            if (mensaje.getIdTicket().equals(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getIdTicket().get())) {
                labelMessages.add(new Label(mensaje.getMessage()));
                labelMessages.get(i).setPrefWidth(872);
                labelMessages.get(i).setWrapText(true);
                labelMessages.get(i).setFont(new Font("Century Gothic", 17));
                if (!mensaje.getSender().equals(usuario.getName()+" "+usuario.getSurname())) {
                    labelMessages.get(i).setPadding(new Insets(10,13,0,150));
                    labelMessages.get(i).setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,new CornerRadii(5,5,5,5,false), Insets.EMPTY)));
                } else {
                    labelMessages.get(i).setPadding(new Insets(10,310,0,13));
                    labelMessages.get(i).setBackground(new Background(new BackgroundFill(Color.WHEAT,new CornerRadii(5,5,5,5,false), Insets.EMPTY)));
                }
                vboxConversacionMensajes.getChildren().add(labelMessages.get(i));
                vboxConversacionMensajes.setSpacing(15);
                i++;
            }
        }
    }

    @FXML
    void mostrarTicketMensajes(MouseEvent event) {
        if (treeTableViewMensajes.getSelectionModel().getSelectedItem() != null) { // Cambiamos los datos del mensaje
            // Comprobamos si el panel de mensajes esta visible
            if (!datosVBoxMensajes.isVisible())
                datosVBoxMensajes.setVisible(true);

            // Si presiona un mensaje mientras esta respondiendo a un ticket, se cancela el ticket y se muestra el mensaje seleccionado
            if (respuestaTicketVBox.isVisible()) {
                respuestaTicketVBox.setVisible(false);
                scrollPaneMensajes.setVisible(true);
            }

			// Borramos la conversacion en caso de que hubiese una seleccionada para poder introducir la siguiente
			labelMessages.clear();
			vboxConversacionMensajes.getChildren().clear();
			asuntoJFXTextFieldMensajes.setText(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getSubject().get());
			destinatarioJFXTextFieldMensajes.setText(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getReceiver().get());
			idTicketJFXTextFieldMensajes.setText(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getIdTicket().get());

			// Marcamos el mensaje como leido
			setMsgAsRead();

			// Cambiamos el scroll pane para mostrar la lista de mensajes correspondientes al ticket seleccionado
            changeTicketConversation();
            seleccionaMensajeVBox.setVisible(false);
        }
    }

    @FXML
    void enviarMensajeUsuarios(ActionEvent event) {
        if (asuntoJFXTextFieldUsuarios.getText().isEmpty()) {
            modelo.createAlert("Cuidado", "Debes de poner un asunto");
        }
        else if (mensajeJFXTextFieldUsuarios.getText().isEmpty()) {
            modelo.createAlert("Cuidado", "Debes de poner un mensaje");
        }
        else {
            UUID uniqueKey = UUID.randomUUID();
            Message msg = new Message(usuario.getName() + " " + usuario.getSurname(), treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getName().get()+ " "
                        + treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getSurname().get(), asuntoJFXTextFieldUsuarios.getText(), mensajeJFXTextFieldUsuarios.getText(),
                        uniqueKey.toString(), false);
            List<Message> updatedMessages = modelo.getMessages();
            updatedMessages.add(msg);
            modelo.setMessages(updatedMessages);
            modelo.serializarAJson("./Proyecto1/src/application/jsonFiles/messages.json", modelo.getMessages(),false);
            modelo.createAlert("Informacion", "Se ha enviado el mensaje correctamente");
            // Borramos los campos para evitar confusion
            asuntoJFXTextFieldUsuarios.clear();
            mensajeJFXTextFieldUsuarios.clear();

            // Actualizamos la lista de mensajes
            treeTableViewMensajes.getRoot().getChildren().add(new TreeItem<>(new messageTTView(msg.getSender(), msg.getReceiver(), msg.getSubject(),
                                                                                                msg.getMessage(), msg.getIdTicket(), msg.getRead())));
            // Junto a los mensajes no leidos
            comprobarMensajesNuevos();
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
        scrollPaneMensajes.setVisible(false);
        botonResponderTicket.setVisible(false);
	    respuestaTicketVBox.setVisible(true);
    }

    @FXML
    void crearMensajeYResponderTicket(ActionEvent event) {
        if (crearMensajeJFXTextAreaMensajes.getText().isEmpty()){
            modelo.createAlert("Cuidado", "Debes de poner un mensaje");
        }
        else {
            Message msg = new Message(treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getSender().get(),
                                      treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getReceiver().get(),
                                      treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getSubject().get(),
                                      crearMensajeJFXTextAreaMensajes.getText(),
                                      treeTableViewMensajes.getSelectionModel().getSelectedItem().getValue().getIdTicket().get(),
                                      false
            );

            List<Message> updatedMessages = modelo.getMessages();
            updatedMessages.add(msg);
            modelo.setMessages(updatedMessages);
            modelo.serializarAJson("./Proyecto1/src/application/jsonFiles/messages.json", modelo.getMessages(),false);
            modelo.createAlert("Informacion", "Se ha enviado el mensaje correctamente, por favor compruebelo pinchando donde le indica la tabla");

            // Borramos los campos para evitar confusion
            crearMensajeJFXTextAreaMensajes.clear();
        }
    }
    
    @FXML
    void guardarCalendario(ActionEvent event) {
	    if (calendario.getAgendaView().getListView().getItems().size() != 0) {
            prueba.getListView().setItems(calendario.getAgendaView().getListView().getItems());
            modelo.createAlert("Informacion", "El evento se ha añadido correctamente");
        } else {
            modelo.createAlert("Informacion", "Primero debe de añadir un evento al calendario");
        }
    }
    
    @FXML
    void cancelarRespuestaTicket(ActionEvent event) {
	    respuestaTicketVBox.setVisible(false);
	    scrollPaneMensajes.setVisible(true);
	    botonResponderTicket.setVisible(true);
    }

    public void comprobarMensajesNuevos(){
        // Borramos la conversacion en casa de que hubiese una seleccionada para poder introducir la siguiente
        labelMessagesInicio.clear();
        vboxConversacionMensajesInicio.getChildren().clear();
        int i = 0;
        // En este caso no usamos una lambda para no tener que usar un AtomicInteger, por lo tanto simplificando el codigo
        for (Message mensaje : modelo.getMessages()) {
            // Si no esta leido y el "sender" coincide con el nombre completo del usuario
            if (!mensaje.getRead() && (mensaje.getReceiver().equals(usuario.getName() + " " + usuario.getSurname()))) {
                labelMessagesInicio.add(new Label("- Asunto: " + mensaje.getSubject() + " || De parte de: " + mensaje.getSender()));
                labelMessagesInicio.get(i).setPrefWidth(1202);
                labelMessagesInicio.get(i).setWrapText(true);
                labelMessagesInicio.get(i).setFont(new Font("Century Gothic", 26));
                labelMessagesInicio.get(i).setPadding(new Insets(0,0,0,20));
                vboxConversacionMensajesInicio.getChildren().add(labelMessagesInicio.get(i));
                vboxConversacionMensajesInicio.setSpacing(15);
                i++;
            }
        }
        if (labelMessagesInicio.size()==0){
            labelMessagesInicio.add(new Label(" - No tiene mensajes nuevos"));
            labelMessagesInicio.get(0).setFont(new Font("Century Gothic", 26));
            vboxConversacionMensajesInicio.getChildren().add(labelMessagesInicio.get(0));
        }
    }
    
    // MAPA
    // Variables y métodos del GoogleMaps
    private GoogleMap map;
    
    private GeocodingService geocodingService;

    private StringProperty address = new SimpleStringProperty();
    
    @FXML
    private GoogleMapView mapView;
    
    @FXML
    private TextField addressTextField;
    
    @FXML
    private JFXButton buttonActualizarUbicacion;
    
    @FXML
    private JFXButton buttonUbicacionCasa;
    
    @FXML
    private JFXTreeTableView<usuarioTTView> treeTableViewPacientesMapa;

    @Override
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
	        
	        //Añadir un Marker al mapa
	        MarkerOptions markerOptions = new MarkerOptions();

	        markerOptions.position(new LatLong(40.371830555556, -3.9189527777778))
	                    .visible(Boolean.TRUE)
	                    .title("MarKer Ubicación");

	        Marker marker = new Marker(markerOptions);

	        map.addMarker(marker);
	}
    
    @FXML
    public void addressTextFieldAction(ActionEvent event) {
        geocodingService.geocode(address.get(), (GeocodingResult[] results, GeocoderStatus status) -> {
            
            LatLong latLong = null;
            
            if( status == GeocoderStatus.ZERO_RESULTS) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se encontraron direcciones coincidentes");
                alert.show();
                return;
            } else if( results.length > 1 ) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Multiples resultados encontrados, mostrando el primero.");
                alert.show();
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            } else {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            }
            
            map.setCenter(latLong);

        });
    }
    
    @FXML
    void actualizarUbicacion(ActionEvent event) {
        map.setCenter(new LatLong(40.371830555556, -3.9189527777778));
    }
    
    @FXML
    void verUbicacionCasa(ActionEvent event) {
    	for (Usuario user : relatedUsers) {
    		System.out.println(user.getRol());
        	if(user.getRol().equals("paciente")) {
        		if (user.getName().equals(treeTableViewPacientes.getSelectionModel().getSelectedItem().getValue().getName().get())) {
        			
        			//user.getDomicilio()
        	        geocodingService.geocode(user.getDomicilio(), (GeocodingResult[] results, GeocoderStatus status) -> {
        	        	
        	            LatLong latLong = null;
        	            
        	            if( status == GeocoderStatus.ZERO_RESULTS) {
        	                Alert alert = new Alert(Alert.AlertType.ERROR, "No se encontraron direcciones coincidentes");
        	                alert.show();
        	                return;
        	            } else if( results.length >= 1 ) {
        	                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ubicación de la casa: "+usuario.getDomicilio());
        	                alert.show();
        	                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
        	            } else {
        	                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
        	            }
        	            
        		        //Añadir un Marker al mapa
        		        MarkerOptions markerOptions = new MarkerOptions();

        		        markerOptions.position(latLong)
        		                    .visible(Boolean.TRUE)
        		                    .title("MarKer Ubicación");

        		        Marker marker = new Marker(markerOptions);

        		        map.addMarker(marker);
        	            
        	            map.setCenter(latLong);

        	        });
        		}
        	}
    	}
    }
    
	@FXML
    void mostrarDatosMapaPacientes(MouseEvent event) throws ParseException {    	
    	calendarioSensores.setValue(LocalDate.now()); // Asignamos la fecha actual al seleccionar un usuario
    }  
    
	// SENSORES
    // Variables y métodos de los sensores
    @FXML
    private JFXTreeTableView<usuarioTTView> treeTableViewPacientes;
    
	@SuppressWarnings("unchecked")
	public void crearTreeTableViewPacientes() {
        JFXTreeTableColumn<usuarioTTView, String> nombreCol = new JFXTreeTableColumn<>("Nombre");
        JFXTreeTableColumn<usuarioTTView, String> apellidosCol = new JFXTreeTableColumn<>("Apellidos");

        nombreCol.setCellValueFactory(param -> param.getValue().getValue().getName());
        nombreCol.setMinWidth(189);
        nombreCol.setMaxWidth(189);
        apellidosCol.setCellValueFactory(param -> param.getValue().getValue().getSurname());
        apellidosCol.setMinWidth(189);
        apellidosCol.setMaxWidth(189);

        ObservableList<usuarioTTView> users = FXCollections.observableArrayList();
        // Añadimos los usuarios
        relatedUsers = modelo.userInRelatedUsers(modelo.getUsuarios(), usuario);
        for (Usuario user : relatedUsers) {
        	if(user.getRol().equals("paciente")) {
        		users.add(new usuarioTTView(user.getName(), user.getSurname(), user.getRol(),user.getBirthday(), user.getAge(), user.getImagenPerfil()));
        	}
        }

        TreeItem<usuarioTTView> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        // Sensores
        treeTableViewPacientes.getColumns().setAll(nombreCol, apellidosCol);
        treeTableViewPacientes.setRoot(root);
        treeTableViewPacientes.setShowRoot(false);
        // Mapa
        treeTableViewPacientesMapa.getColumns().setAll(nombreCol, apellidosCol);
        treeTableViewPacientesMapa.setRoot(root);
        treeTableViewPacientesMapa.setShowRoot(false);
    }
	
    @FXML private LineChart<Double, Double> graficaTemperatura;
    @FXML private StackedBarChart<Double, Double> graficaMagnetico;
    @FXML private StackedBarChart<Double, Double> graficaGas;
    @FXML private PieChart graficaPresion;
	private final ObservableList<PieChart.Data> detalles = FXCollections.observableArrayList();
	@FXML private Label horasDurmiendo;
	@FXML private DatePicker calendarioSensores;
	
	@FXML
    void mostrarDatosSensoresPacientes(MouseEvent event) throws ParseException {    	
    	calendarioSensores.setValue(LocalDate.now()); // Asignamos la fecha actual al seleccionar un usuario
    }  
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
    void mostrarSensoresDia(ActionEvent event) {
    	//Limpiamos todas las gráficas
    	graficaTemperatura.getData().clear();
    	graficaMagnetico.getData().clear();
    	graficaGas.getData().clear();
    	graficaPresion.getData().clear();
    	detalles.clear();
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Formato que le daremos a la fecha
    	calendarioSensores.setConverter(new LocalDateStringConverter(formatter, null)); // Convertimos la fecha del objetos LocalDate con nuestro formato

    	// Temperatura
		XYChart.Series seriesTemperatura = new XYChart.Series();
		for (modSensorTemperatura temperatura : modelo.getDatosTemperatura()) {
			if ((formatter.format(calendarioSensores.getValue()).compareTo(temperatura.getFecha())) == 0) {
				seriesTemperatura.getData().add(new XYChart.Data(temperatura.getHora(), temperatura.getTemperatura()));
			}
		}
    	graficaTemperatura.getData().addAll(seriesTemperatura);
    	
    	// Magnetico
		XYChart.Series seriesMagnetico = new XYChart.Series();
		for (modSensorMagnetico magnetico : modelo.getDatosMagnetico()) {
			if ((formatter.format(calendarioSensores.getValue()).compareTo(magnetico.getFecha())) == 0) {
				seriesMagnetico.getData().add(new XYChart.Data(magnetico.getHora(), magnetico.getValor()));
			}
		}
    	graficaMagnetico.getData().addAll(seriesMagnetico);
    	
    	// Gas
		XYChart.Series seriesGas = new XYChart.Series();
		for (modSensorGas gas : modelo.getDatosGas()) {
			if ((formatter.format(calendarioSensores.getValue()).compareTo(gas.getFecha())) == 0) {
			    seriesGas.getData().add(new XYChart.Data(gas.getHora(), gas.getValor()));
			}
		}
    	graficaGas.getData().addAll(seriesGas);
    	
    	//Presion
		for (modSensorPresion presion : modelo.getDatosPresion()) {		
			if ((formatter.format(calendarioSensores.getValue()).compareTo(presion.getFecha())) == 0) {
				detalles.addAll(new PieChart.Data(presion.getDurmiendo(),presion.getValor()));
				detalles.addAll(new PieChart.Data(presion.getDespierto(),(24-presion.getValor())));
				graficaPresion.setData(detalles);
				graficaPresion.setLegendSide(Side.LEFT);
				horasDurmiendo.setText(presion.getValor()+ ""); 	
			}	
		}  
    }
}
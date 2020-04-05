package application.controladores;

import com.jfoenix.controls.*;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.text.ParseException;


import application.modelos.Usuario;
import application.modelos.modelo;

public class controladorLogin {
    private modelo modelo;
    private Usuario usuario;
    private String nUsuario;


    public void initModelo(modelo modelo_, Usuario usuario_){
        if (this.modelo != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.modelo = modelo_;
        this.usuario = usuario_;
        modelo.leerJson("./Proyecto1/src/application/jsonFiles/Users.json");
        if (!checkBox.isSelected()) {
        	nUsuario = usrnameField.getText();
        	usrnameField.setText(nUsuario);
        	}
       
    }
    @FXML
    private AnchorPane rootp;

    @FXML
    private ImageView logo;

    @FXML
    private ImageView fotolgn_3;

    @FXML
    private ImageView fotolgn_2;

    @FXML
    private ImageView fotolgn_1;

    @FXML
    private JFXTextField crearNombreTField;

    @FXML
    private JFXTextField crearApellidosTField;

    @FXML
    private JFXTextField crearCumpleTField;

    @FXML
    private JFXTextField crearUsernameTField;

    @FXML
    private JFXTextField crearPasswordTField;

    @FXML
    private JFXTextField crearRolTField;
    
    @FXML
    private JFXTextField crearDNITField;

    @FXML
    private JFXTextField crearTelefonoTField;
    
    @FXML
    private JFXTextField crearDomicilioTField;
    
    @FXML
    private JFXButton crearCuentaBttn;

    @FXML
    private JFXTextField usrnameField;

    @FXML
    private JFXPasswordField pswdField;

    @FXML
    private JFXCheckBox checkBox;

    @FXML
    private JFXButton loginButton;

    @FXML
    private Hyperlink accountHyperLink;

    @FXML
    private Hyperlink cancelarCrecionHyperLink;

    @FXML
    private Label incorrectFieldLabel;

    @FXML
    private VBox crearUsuarioElementos;

    @FXML
    private VBox loginElements;

    @FXML
    void keyTab(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB){
            pswdField.setFocusTraversable(true);
        }
    }

    @FXML
    void defaultBtn(ActionEvent event) throws IOException {
        login();
    }
    
    @FXML
    public void onEnter(ActionEvent event) throws IOException{
        login();
    }

    @FXML
    void mostrarCreacionUsuario(ActionEvent event) {
        if (incorrectFieldLabel.isVisible()){
            incorrectFieldLabel.setVisible(false);
        }
        loginElements.setVisible(false);
        crearUsuarioElementos.setVisible(true);
        logo.setY(-125);
    }

    @FXML
    void cancelarCreacion(MouseEvent event) {
        crearUsuarioElementos.setVisible(false);
        loginElements.setVisible(true);
        logo.setY(-30);
    }

    @FXML
    void crearUsuario(ActionEvent event) {
        try {
            if (modelo.countWordsString(crearNombreTField.getText()) != 1) {
            	modelo.createAlert("Cuidado", 
            			"Debes introducir un nombre válido, que consista de una sola palabra.");
            } else if (modelo.countWordsString(crearApellidosTField.getText()) != 2) {
            	modelo.createAlert("Cuidado", 
            			"Debes introducir unos apellidos válidos, que consista de dos palabras.");
            } else if (crearUsernameTField.getText().length() > 16 || modelo.countWordsString(crearUsernameTField.getText()) >1) {
                modelo.createAlert("Cuidado",
                        "Debes introducir una única palabra de longitud máxima 16, que consista de letras y/o numeros (riniguez91).");
            } else if (!modelo.checkUniqueUsername(modelo.getUsuarios(), crearUsernameTField.getText())) {
                modelo.createAlert("Cuidado", "Ese nombre ya ha sido elegido, porfavor escoja otro");
            } else if (crearPasswordTField.getText().isEmpty()) {
                modelo.createAlert("Cuidado", "Porfavor rellene el campo de contraseña");
            } else if (crearCumpleTField.getText().length() != 10) {
            	modelo.createAlert("Cuidado", 
            			"Debes introducir una fecha válida (27/10/1989).");
            } else if (modelo.validarDNI(crearDNITField.getText())) { //validarDNI(crearDNITField.getText().length()) != 9)
            	modelo.createAlert("Cuidado", 
            			"Debes introducir un DNI válido. (8 digitos y 1 letra).");
            } else if (crearTelefonoTField.getText().length() != 9) {
                Integer.parseInt(crearTelefonoTField.getText()); // Comprobamos
            	modelo.createAlert("Cuidado", 
            			"Debes introducir un número de teléfono valido de 9 dígitos (628638442).");
            } else if (!modelo.checkRol(crearRolTField.getText())) {
            	modelo.createAlert("Cuidado", 
            			"Debes introducir un rol válido, que consista de una sola palabra (médico, cuidador, paciente, familiar).");
            } else
             {
                Usuario newUser = new Usuario(crearNombreTField.getText(), crearApellidosTField.getText(), crearCumpleTField.getText(),
                                              crearUsernameTField.getText(), Integer.parseInt(crearTelefonoTField.getText()), crearDNITField.getText(), 
                                              modelo.encriptaEnMD5(crearPasswordTField.getText()), crearRolTField.getText(), crearDomicilioTField.getText());
                newUser.setAge(modelo.calculateAge(newUser.getBirthday())); // throws ParseException
                modelo.getUsuarios().add(newUser);
                modelo.serializarAJson("./Proyecto1/src/application/jsonFiles/Users.json", modelo.getUsuarios(),false);
                loginElements.setVisible(true);
                crearUsuarioElementos.setVisible(false);
                logo.setY(-60);
            }
        } catch (NumberFormatException nfe){
            modelo.createAlert("Cuidado", "Debes introducir un telefono valido (626 574 329)");
        } catch (ParseException pe) {
            modelo.createAlert("Cuidado", "Debes introducir una fecha valida");
        }
    }

    public FadeTransition getFadeTransition(ImageView imageView, double fromValue, double toValue, int durationInMilliseconds) {

        FadeTransition ft = new FadeTransition(Duration.millis(durationInMilliseconds), imageView);
        ft.setFromValue(fromValue);
        ft.setToValue(toValue);
        return ft;
    }

    public void initialize(){
        ImageView[] slides = new ImageView[3];
        slides[0] = fotolgn_1;
        slides[1] = fotolgn_2;
        slides[2] = fotolgn_3;
        SequentialTransition slideshow = new SequentialTransition();

        for (ImageView slide : slides) {
            SequentialTransition sequentialTransition = new SequentialTransition();

            FadeTransition fadeIn = getFadeTransition(slide, 0.0, 1.0, 2000);
            PauseTransition stayOn = new PauseTransition(Duration.millis(10000));
            FadeTransition fadeOut = getFadeTransition(slide, 1.0, 0.0, 2000);

            sequentialTransition.getChildren().addAll(fadeIn, stayOn, fadeOut);
            slideshow.getChildren().add(sequentialTransition);
        }
        slideshow.setCycleCount(Timeline.INDEFINITE);
        slideshow.play();
        fotolgn_1.fitWidthProperty().bind(rootp.widthProperty()); // Allows for image resize
        fotolgn_2.fitWidthProperty().bind(rootp.widthProperty());
        fotolgn_3.fitWidthProperty().bind(rootp.widthProperty());
        
    }

    // CAMBIAR FOR LOOP A UN WHILE
    public void login() throws IOException {
        for (Usuario usuario: modelo.getUsuarios()){
            if (usrnameField.getText().equals(usuario.getUsername()) && modelo.encriptaEnMD5(pswdField.getText()).equals(usuario.getPassword())){
                Stage stageBttnBelongsTo = (Stage) loginButton.getScene().getWindow();
                // Recordar username
                if (checkBox.isSelected()) {
                	nUsuario=usuario.getUsername();
                	checkBox.setSelected(true);
                }
                switch(usuario.getRol()){
                    case "medico":
                        FXMLLoader loaderMedico = new FXMLLoader(getClass().getResource("/application/vistas/vistaMedico.fxml"));
                        Parent rootMedico = loaderMedico.load();
                        controladorMedico contrMedico = loaderMedico.getController();
                        contrMedico.initModelo(modelo,usuario);
                        stageBttnBelongsTo.setScene(new Scene(rootMedico));
                        break;
                    case "paciente":
                        FXMLLoader loaderPaciente = new FXMLLoader(getClass().getResource("/application/vistas/vistaPaciente.fxml"));
                        Parent rootPaciente = loaderPaciente.load();
                        controladorPaciente contrPaciente = loaderPaciente.getController();
                        contrPaciente.initModelo(modelo,usuario);
                        stageBttnBelongsTo.setScene(new Scene(rootPaciente));
                        break;
                    case "familiar":
                        FXMLLoader loaderFamiliar = new FXMLLoader(getClass().getResource("/application/vistas/vistaFamiliar.fxml"));
                        Parent rootFamiliar = loaderFamiliar.load();
                        controladorFamiliar contrFamiliar = loaderFamiliar.getController();
                        contrFamiliar.initModelo(modelo,usuario);
                        stageBttnBelongsTo.setScene(new Scene(rootFamiliar));
                        break;
                    case "cuidador":
                        FXMLLoader loaderCuidador = new FXMLLoader(getClass().getResource("/application/vistas/vistaCuidador.fxml"));
                        Parent rootCuidador = loaderCuidador.load();
                        controladorCuidador contrCuidador = loaderCuidador.getController();
                        contrCuidador.initModelo(modelo,usuario);
                        stageBttnBelongsTo.setScene(new Scene(rootCuidador));
                        break;
                }
                break;
            }
            incorrectFieldLabel.setVisible(true);
        }
    }
}
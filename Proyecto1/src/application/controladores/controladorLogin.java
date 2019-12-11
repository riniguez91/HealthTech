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
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;


import application.modelos.Usuario;
import application.modelos.modelo;

public class controladorLogin {
    private modelo modelo;
    private Usuario usuario;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void initModelo(modelo modelo_, Usuario usuario_){
        if (this.modelo != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.modelo = modelo_;
        this.usuario = usuario_;
        modelo.leerJson("./Proyecto1/src/application/jsonFiles/Users.json");
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
    private AnchorPane crearUsuarioElementos;

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
    private JFXButton crearCuentaBttn;

    @FXML
    private AnchorPane loginElements;

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
    void keyTab(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB){
            pswdField.setFocusTraversable(true);
        }
    }

    @FXML
    void defaultBtn(ActionEvent event) throws IOException {
        for (Usuario usuario: modelo.getUsuarios()){
            if (usrnameField.getText().equals(usuario.getUsername()) && encriptaEnMD5(pswdField.getText()).equals(usuario.getPassword())){
                Stage stageBttnBelongsTo = (Stage) loginButton.getScene().getWindow();
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
    
    @FXML
    public void onEnter(ActionEvent event) throws IOException{
        for (Usuario usuario: modelo.getUsuarios()){
            if (usrnameField.getText().equals(usuario.getUsername()) && encriptaEnMD5(pswdField.getText()).equals(usuario.getPassword())){
                Stage stageBttnBelongsTo = (Stage) loginButton.getScene().getWindow();
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
                        controladorPaciente contrFamiliar = loaderFamiliar.getController();
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
    
    @FXML
    public void onEnterNombreDeUsuario(ActionEvent event) throws IOException {
    	if (pswdField.getText() != null && (usrnameField.getText().equals(usuario.getUsername()) && encriptaEnMD5(pswdField.getText()).equals(usuario.getPassword()))) {
    		incorrectFieldLabel.setText("Introduzca una contraseña");
    		incorrectFieldLabel.setVisible(true);
		} else {
			for (Usuario usuario: modelo.getUsuarios()){
	            if (usrnameField.getText().equals(usuario.getUsername()) && encriptaEnMD5(pswdField.getText()).equals(usuario.getPassword())){
	                Stage stageBttnBelongsTo = (Stage) loginButton.getScene().getWindow();
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
	                        Parent rootFamiliar = FXMLLoader.load(getClass().getResource("/application/vistas/vistaFamiliar.fxml"));
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
                alert.setHeaderText("Cuidado");
                alert.setContentText("Debes introducir un nombre valido, que consista de una sola palabra");
                alert.showAndWait();
            } else if (modelo.countWordsString(crearApellidosTField.getText()) != 2) {
                alert.setHeaderText("Cuidado");
                alert.setContentText("Debes introducir unos apellidos validos, que consista de dos palabras");
                alert.showAndWait();
            } else if (modelo.checkRol(crearUsernameTField.getText())) {
                alert.setHeaderText("Cuidado");
                alert.setContentText("Debes introducir un nombre de usuario valido, que consista de una sola palabra (riniguez)");
                alert.showAndWait();
            } else if (!modelo.checkRol(crearRolTField.getText())) {
                alert.setHeaderText("Cuidado");
                alert.setContentText("Debes introducir un rol valido, que consista de una sola palabra (medico, cuidador, paciente, familiar)");
                alert.showAndWait();
            } else if (crearCumpleTField.getText().length() != 10) {
                alert.setHeaderText("Cuidado");
                alert.setContentText("Debes introducir una fecha valida (27/10/1989)");
                alert.showAndWait();
            } else {
                Usuario newUser = new Usuario(crearNombreTField.getText(), crearApellidosTField.getText(), crearCumpleTField.getText(),
                                              crearUsernameTField.getText(), encriptaEnMD5(crearPasswordTField.getText()), crearRolTField.getText());
                newUser.setAge(modelo.calculateAge(newUser.getBirthday())); // throws ParseException
                modelo.getUsuarios().add(newUser);
                modelo.serializarAJson("./Proyecto1/src/application/jsonFiles/Users.json", modelo.getUsuarios(),false);
                loginElements.setVisible(true);
                crearUsuarioElementos.setVisible(false);
                logo.setY(-60);
            }
        } catch (NumberFormatException | ParseException nfe){
            nfe.printStackTrace();
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
    }
    
    
    // Encriptacion de las constraseñas en MD5
    private static final char[] CONSTS_HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };
    
    public static String encriptaEnMD5(String stringAEncriptar) {
        try {
           MessageDigest msgd = MessageDigest.getInstance("MD5");
           byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
           StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
           for (int i = 0; i < bytes.length; i++) {
               int bajo = (int)(bytes[i] & 0x0f);
               int alto = (int)((bytes[i] & 0xf0) >> 4);
               strbCadenaMD5.append(CONSTS_HEX[alto]);
               strbCadenaMD5.append(CONSTS_HEX[bajo]);
           }
           return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
           return null;
        }   
    }
}
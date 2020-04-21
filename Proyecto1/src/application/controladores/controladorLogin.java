package application.controladores;

import com.jfoenix.controls.*;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @FXML private AnchorPane rootp;

    @FXML private ImageView logo;

    @FXML private ImageView fotolgn_3;

    @FXML private ImageView fotolgn_2;

    @FXML private ImageView fotolgn_1;

    @FXML private JFXTextField usrnameField;

    @FXML private JFXPasswordField pswdField;

    @FXML private JFXCheckBox checkBox;

    @FXML private JFXButton loginButton;

    // @FXML private Hyperlink accountHyperLink;

    // @FXML private Hyperlink cancelarCrecionHyperLink;

    @FXML private Label incorrectFieldLabel;

    // @FXML private VBox crearUsuarioElementos;

    @FXML private VBox loginElements;

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
    public void onEnter(ActionEvent event) throws IOException{ // "//2.139.176.212:3306/pr_healthtech?user=pr_healthtech&password=Jamboneitor123"
        login();
    }

    @FXML
    void mostrarCreacionUsuario(ActionEvent event) throws IOException {
        // Cargamos 2nda escena
        FXMLLoader loaderCreacionUsuario = new FXMLLoader(getClass().getResource("/application/vistas/vistaCreacionUsuario.fxml"));
        Parent rootCreacionUsuario = loaderCreacionUsuario.load();

        // Cojemos el controlador de la 2nd escena
        controladorCreacionUsuario controladorCU = loaderCreacionUsuario.getController();
        controladorCU.initModelo(modelo);

        // Display stage
        Stage stage = new Stage();
        stage.setScene(new Scene(rootCreacionUsuario));
        stage.show();
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

    public void cargarVistaGeneral(Usuario usuario) throws IOException {
        Stage stageBttnBelongsTo = (Stage) loginButton.getScene().getWindow();
        FXMLLoader loaderPaciente = new FXMLLoader(getClass().getResource("/application/vistas/vistaPaciente.fxml"));
        Parent rootPaciente = loaderPaciente.load();
        controladorPaciente contrPaciente = loaderPaciente.getController();
        contrPaciente.initModelo(modelo,usuario, contrPaciente);
        stageBttnBelongsTo.setScene(new Scene(rootPaciente));
    }

    public void cargarVistaDetallada(Usuario usuario) throws IOException {
        Stage stageBttnBelongsTo = (Stage) loginButton.getScene().getWindow();
        FXMLLoader loaderMedico = new FXMLLoader(getClass().getResource("/application/vistas/vistaMedico.fxml"));
        Parent rootMedico = loaderMedico.load();
        controladorMedico contrMedico = loaderMedico.getController();
        contrMedico.initModelo(modelo,usuario);
        stageBttnBelongsTo.setScene(new Scene(rootMedico));
    }

    // CAMBIAR FOR LOOP A UN WHILE
    public void login() throws IOException {
        for (Usuario usuario: modelo.getUsuarios()){
            if (usrnameField.getText().equals(usuario.getUsername()) && modelo.encriptaEnMD5(pswdField.getText()).equals(usuario.getPassword())){
                // Recordar username
                if (checkBox.isSelected()) {
                	nUsuario=usuario.getUsername();
                	checkBox.setSelected(true);
                }
                switch(usuario.getRol()){
                    case "medico":
                    case "familiar":
                        cargarVistaDetallada(usuario);
                        break;
                    case "paciente":
                    case "cuidador":
                        cargarVistaGeneral(usuario);
                        break;
                } // switch
                break;
            } // if
            incorrectFieldLabel.setVisible(true);
        } // for
    } // login
}
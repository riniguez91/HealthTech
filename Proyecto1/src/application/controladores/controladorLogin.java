package application.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;


import application.modelos.Usuario;
import application.modelos.modelo;

public class controladorLogin {
    private modelo modelo;
    private Usuario usuario;

    public void initModelo(modelo modelo_, Usuario usuario_){
        if (this.modelo != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.modelo = modelo_ ;
        this.usuario = usuario_;
        modelo.leerJson();
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
    private JFXTextField crearEdadTField;

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
            if (usrnameField.getText().equals(usuario.getUsername()) && pswdField.getText().equals(usuario.getPassword())){
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

    @FXML
    void mostrarCreacionUsuario(ActionEvent event) {
        loginElements.setVisible(false);
        crearUsuarioElementos.setVisible(true);
        logo.setY(-125);
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
}
package application.controladores;

import application.modelos.ConexionBBDD;
import com.jfoenix.controls.*;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Vector;

import application.modelos.Usuario;
import application.modelos.modelo;

public class controladorLogin {
    private modelo modelo;
    private Usuario usuario;
    private String nUsuario;
    private ConexionBBDD c;

    public void initModelo(modelo modelo_, Usuario usuario_){
        if (this.modelo != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.modelo = modelo_;
        this.usuario = usuario_;
        c = new ConexionBBDD();

        if (!checkBox.isSelected()) {
        	nUsuario = usrnameField.getText();
        	usrnameField.setText(nUsuario);
        	}
    } // initModelo()

    @FXML private AnchorPane rootp;

    @FXML private ImageView logo;

    @FXML private ImageView fotolgn_3;

    @FXML private ImageView fotolgn_2;

    @FXML private ImageView fotolgn_1;

    @FXML private JFXTextField usrnameField;

    @FXML private JFXPasswordField pswdField;

    @FXML private JFXCheckBox checkBox;

    @FXML private JFXButton loginButton;

    @FXML private Label incorrectFieldLabel;

    @FXML private VBox loginElements;

    @FXML
    void keyTab(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB){
            pswdField.setFocusTraversable(true);
        }
    } // keyTab()

    @FXML
    void defaultBtn(ActionEvent event) throws IOException {
        login();
    } // defaultBtn()
    
    @FXML
    public void onEnter(ActionEvent event) throws IOException{ // "//2.139.176.212:3306/pr_healthtech?user=pr_healthtech&password=Jamboneitor123"
        login();
    } // onEnter()

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
    } // mostrarCreacionUsuario()

    public FadeTransition getFadeTransition(ImageView imageView, double fromValue, double toValue, int durationInMilliseconds) {
        FadeTransition ft = new FadeTransition(Duration.millis(durationInMilliseconds), imageView);
        ft.setFromValue(fromValue);
        ft.setToValue(toValue);
        return ft;
    } // getFadeTransition()

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
        
    } // initialize()

    public void cargarVista(Usuario usuario, String tipoVista, ConexionBBDD c) throws IOException {
        // FXML Loader and Parent
        FXMLLoader loaderVistaGeneral = new FXMLLoader(getClass().getResource("/application/vistas/vistaGeneral.fxml"));
        Parent rootVistaGeneral = loaderVistaGeneral.load();

        // Controllers
        controladorVistaGeneral controladorVistaGeneral = loaderVistaGeneral.getController();
        controladorVistaGeneral.initModelo(modelo,usuario, controladorVistaGeneral, tipoVista, c);

        Stage stageBttnBelongsTo = (Stage) loginButton.getScene().getWindow();
        stageBttnBelongsTo.setScene(new Scene(rootVistaGeneral));

        stageBttnBelongsTo.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });

    } // cargarVista()
    
    public void cargarVistaAdmin() throws IOException {
    	FXMLLoader loaderVistaAdmin = new FXMLLoader(getClass().getResource("/application/vistas/vistaAdmin.fxml"));
    	Parent rootVistaAdmin = loaderVistaAdmin.load();
    	
    	//controllers
    	controladorAdmin controladorAdmin = loaderVistaAdmin.getController();
    	controladorAdmin.initModelo(modelo);
    	
    	Stage stageBttnBelongsTo = (Stage) loginButton.getScene().getWindow();
    	stageBttnBelongsTo.setScene(new Scene(rootVistaAdmin));
    
    }
    public void login() {

        try {
            ResultSet rs = c.loginRS("SELECT * \n" +
                    "FROM users\n" +
                    "WHERE users.User = ? AND users.Password = MD5(?)", usrnameField.getText(), pswdField.getText());

            if (rs.next()) {
                usuario = new Usuario(rs.getInt("ID_User"), rs.getString("Name"), rs.getString("Surnames"), rs.getString("DOB"), rs.getString("User")
                                     ,rs.getString("Password"), rs.getString("Rol"), rs.getString("Photo"), rs.getInt("Telephone"), rs.getString("Adress"),
                                      rs.getString("DNI"));
                usuario.setAge(modelo.calculateAge(rs.getString("DOB")));

                switch(usuario.getRol()){
                    case "medico":
                    case "familiar":
                        cargarVista(usuario, "detallada", c);
                        break;
                    case "paciente":
                    case "cuidador":
                        cargarVista(usuario, "general", c);
                        break;
                    
                }          
            } else if (usrnameField.getText().equals("admin") && pswdField.getText().equals("admin"))
            	cargarVistaAdmin();
            if (!pswdField.getText().isEmpty())
                pswdField.clear();
            incorrectFieldLabel.setVisible(true);
        }
        catch (SQLException | IOException | ParseException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

} // controladorLogin
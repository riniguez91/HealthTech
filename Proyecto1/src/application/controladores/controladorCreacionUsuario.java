package application.controladores;

import application.modelos.ConexionBBDD;
import application.modelos.Usuario;
import application.modelos.modelo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class controladorCreacionUsuario {
    private modelo modelo;

    public void initModelo(modelo modelo_) {
        if (this.modelo != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.modelo = modelo_;
    } // initModelo()

    @FXML private VBox crearUsuarioElementos;

    @FXML private JFXTextField crearNombreTField;

    @FXML private JFXTextField crearApellidosTField;

    @FXML private JFXTextField crearUsernameTField;

    @FXML private JFXTextField crearPasswordTField;

    @FXML private JFXTextField crearCumpleTField;

    @FXML private JFXTextField crearDNITField;

    @FXML private JFXTextField crearTelefonoTField;

    @FXML private JFXTextField crearRolTField;

    @FXML private JFXTextField crearDomicilioTField;

    @FXML private JFXButton crearCuentaBttn;

    @FXML private Hyperlink cancelarCrecionHyperLink;

    @FXML
    void crearUsuario(ActionEvent event) {
        try {
            if (modelo.countWordsString(crearNombreTField.getText()) > 2) {
                modelo.createAlert("Cuidado",
                        "Debes introducir un nombre válido, que consista de una/dos palabras.");
            } else if (modelo.countWordsString(crearApellidosTField.getText()) != 2) {
                modelo.createAlert("Cuidado",
                        "Debes introducir unos apellidos válidos, que consista de dos palabras.");
            } else if (crearUsernameTField.getText().length() > 16 || modelo.countWordsString(crearUsernameTField.getText()) >1) {
                modelo.createAlert("Cuidado",
                        "Debes introducir una única palabra de longitud máxima 16, que consista de letras y/o numeros (pepe27).");
            } else if (crearPasswordTField.getText().isEmpty()) {
                modelo.createAlert("Cuidado", "Porfavor rellene el campo de contraseña");
            } else if (crearCumpleTField.getText().length() != 10) {
                modelo.createAlert("Cuidado",
                        "Debes introducir una fecha válida (27-10-1989).");
            } else if (modelo.validarDNI(crearDNITField.getText())) { //validarDNI(crearDNITField.getText().length()) != 9)
                modelo.createAlert("Cuidado",
                        "Debes introducir un DNI válido. (8 digitos y 1 letra).");
            } else if (crearTelefonoTField.getText().length() != 9) {
                Integer.parseInt(crearTelefonoTField.getText()); // Comprobamos
                modelo.createAlert("Cuidado",
                        "Debes introducir un número de teléfono valido de 9 dígitos (654987123).");
            } else if (!modelo.checkRol(crearRolTField.getText())) {
                modelo.createAlert("Cuidado",
                        "Roles validos: medico, cuidador, paciente, familiar");
            } else {
                ConexionBBDD c = new ConexionBBDD();
                String sentenciaSQL = "INSERT INTO users (`Name`, `Surnames`, `DOB`, `User`, `Password`, `Rol`, `Photo`, `Telephone`, `Adress`, `DNI`) \n" +
                            "VALUE (?, ?, ?, ?, MD5(?), ?, \"@..\\\\..\\\\resources\\\\fotos\\\\user.png\", ?, ?, ?);";
                Date dob_util = new SimpleDateFormat("yyyy-MM-dd").parse(crearCumpleTField.getText());
                java.sql.Date dob = new java.sql.Date(dob_util.getTime()); // Casteamos de java.util.Date a java.sql.Date mediante el metodo .getTime()
                c.insertUserRS(sentenciaSQL, crearNombreTField.getText(), crearApellidosTField.getText(), dob,  crearUsernameTField.getText(), crearPasswordTField.getText(),
                        crearRolTField.getText(), Integer.parseInt(crearTelefonoTField.getText()), crearDomicilioTField.getText(), crearDNITField.getText());

                // Cerramos escena
                Stage stageBttnBelongsTo = (Stage) crearCuentaBttn.getScene().getWindow();
                stageBttnBelongsTo.close();
            }
        } catch (NumberFormatException nfe){
            modelo.createAlert("Cuidado", "Debes introducir un telefono valido (626 574 329)");
        } catch (ParseException pe) {
            modelo.createAlert("Cuidado", "Debe introducir la fecha en formato yyyy-MM-dd");
        }
    } // crearUsuario

    @FXML
    void cerrarEscena(MouseEvent event) {
        Stage stageBttnBelongsTo = (Stage) crearCuentaBttn.getScene().getWindow();
        stageBttnBelongsTo.close();
    } // cerrarEscena()

} // controladorCreacionUsuario

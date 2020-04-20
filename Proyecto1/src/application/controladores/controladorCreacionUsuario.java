package application.controladores;

import application.modelos.Usuario;
import application.modelos.modelo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;

import java.text.ParseException;

public class controladorCreacionUsuario {
    private modelo modelo;

    public void initModelo(modelo modelo_) {
        if (this.modelo != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.modelo = modelo_;
    }

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
                crearUsuarioElementos.setVisible(false);
            }
        } catch (NumberFormatException nfe){
            modelo.createAlert("Cuidado", "Debes introducir un telefono valido (626 574 329)");
        } catch (ParseException pe) {
            modelo.createAlert("Cuidado", "Debes introducir una fecha valida");
        }
    } // crearUsuario
}

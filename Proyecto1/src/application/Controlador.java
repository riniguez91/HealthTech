package application;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controlador {

    @FXML
    private PasswordField password_tfield;

    @FXML
    private TextField user_tfield;

    @FXML
    void changeFocus_TAB(KeyEvent event) {
        switch (event.getCode()){
            case TAB:
                password_tfield.setFocusTraversable(true);
                break;
            case ENTER:
                System.out.println("Iii");
                break;
        }
    }

}
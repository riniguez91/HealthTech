package application.controladores;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.modelos.ConexionBBDD;
import application.modelos.Usuario;
import application.modelos.modelo;
import application.modelos.usuarioTTView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sun.reflect.generics.tree.Tree;

public class controladorAdmin{

    private modelo modelo;


    public void initModelo(modelo modelo_) {
        if (this.modelo != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.modelo = modelo_; 
        
        crearTreeTableView(treeTableViewUsuarios);
        
    }
    //----------------------
    
    @FXML private Label seleccionaUsuarioLabel;

    @FXML private VBox VBoxInformacion;

    @FXML private ImageView userImageViewUsuarios;

    @FXML private Label labelNombreUsuarios;

    @FXML private Label labelApellidosUsuarios;

    @FXML private Label labelFechaNacimientoUsuarios;

    @FXML private Label labelRolUsuarios;

    @FXML private Label labelEdadUsuarios;

    @FXML private Label labelIDUsuarios;

    @FXML private Label labelFotoUsuarios;

    @FXML private Label labelTelefonoUsuarios;

    @FXML private Label labelDireccionUsuarios;

    @FXML private Label labelDNIUsuarios;

    @FXML private Label labelRelacionesUsuarios;

    @FXML private JFXButton EliminarUsuarioBtn;

    @FXML private JFXButton GuardarCambiosBtn;

    @FXML private VBox VBoxUsuarios;

    @FXML private JFXTextField filtrarUsuarioTFieldUsuarios;

    @FXML private TreeTableView<Usuario> treeTableViewUsuarios;

    @FXML void cancelarRespuestaTicket(ActionEvent event) {
    }
    @FXML void filterUsersUsuario(KeyEvent event) {
    }
    @FXML void mostrarDatosUsuarios(MouseEvent event) {
        
    }
   
    
    public void crearTreeTableView(TreeTableView<Usuario> ttv) {
        JFXTreeTableColumn<Usuario, String> nombreCol = new JFXTreeTableColumn<>("Nombre");
        JFXTreeTableColumn<Usuario, String> apellidosCol = new JFXTreeTableColumn<>("Apellidos");
        JFXTreeTableColumn<Usuario, String> rolCol = new JFXTreeTableColumn<>("Rol");
        JFXTreeTableColumn<Usuario, Integer> userIDCol = new JFXTreeTableColumn<>("ID User");

        nombreCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("Name"));
        nombreCol.setMinWidth(129);
        nombreCol.setMaxWidth(129);
        apellidosCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("Surnames"));
        apellidosCol.setMinWidth(189);
        apellidosCol.setMaxWidth(189);
        rolCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("Rol"));
        rolCol.setMinWidth(159);
        rolCol.setMaxWidth(159);
        userIDCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("ID_User"));
        userIDCol.setMinWidth(130);

        TreeItem<Usuario> root = new TreeItem<>(new Usuario());

        // Añadimos los usuarios
        ConexionBBDD c = new ConexionBBDD();

        for (Usuario user : c.sentenciaSQL("SELECT * FROM users"))
            root.getChildren().add(new TreeItem<>(user));

        // ss.getChildren().addAll(so);
        /*TreeItem<Usuario> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);*/

        // TTV Usuarios
        ttv.getColumns().add(0, nombreCol);
        ttv.getColumns().add(1, apellidosCol);
        ttv.getColumns().add(2, rolCol);
        ttv.getColumns().add(3, userIDCol);
        ttv.setRoot(root);
    } // crearTreeTableViewUsuarios()


    
}

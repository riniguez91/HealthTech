package application.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;

import application.modelos.ConexionBBDD;
import application.modelos.Usuario;
import application.modelos.modelo;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Vector;

public class controladorAdmin{

    private modelo modelo;
    private final ConexionBBDD conexionBBDD = new ConexionBBDD();


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

    @FXML private TextField TextFieldID;

    @FXML private TextField TextFieldNombre;

    @FXML private TextField TextFieldPassword;

    @FXML private TextField TextFieldDOB;

    @FXML private TextField TextFieldRol;

    @FXML private TextField TextFieldUsuario;

    @FXML private TextField TextFieldPhoto;

    @FXML private TextField TextFieldAddress;

    @FXML private TextField TextFieldRelaciones;

    @FXML private TextField TextFieldApellidos;

    @FXML private TextField TextFieldDNI;

    @FXML private TextField TextFieldTelephone;
    
    @FXML private JFXButton EliminarUsuarioBtn;

    @FXML private JFXButton GuardarCambiosBtn;

    @FXML private VBox VBoxUsuarios;

    @FXML private JFXTextField filtrarUsuarioTFieldUsuarios;

    @FXML private TreeTableView<Usuario> treeTableViewUsuarios;

    @FXML void cancelarRespuestaTicket(ActionEvent event) {

    }
    
    @FXML void DeleteUser(ActionEvent event) {    	
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Cuidado");
        alert.setContentText("Estas seguro de que quieres borrar el usuario? Esto borrara todo dato asociado con el en la base de datos");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ConexionBBDD c = new ConexionBBDD();
            c.eliminarUsuario(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getID_User());
        }
        else {
            alert.close();
        }
    }
    
    @FXML void GuardarCambios(ActionEvent event) {
  	
    	ConexionBBDD c = new ConexionBBDD();
    		c.editUser(TextFieldNombre.getText(), TextFieldApellidos.getText(), TextFieldDOB.getText(), TextFieldUsuario.getText(), TextFieldPassword.getText(), TextFieldRol.getText(), 
    				TextFieldPhoto.getText(), Integer.parseInt(TextFieldTelephone.getText()), TextFieldAddress.getText(), TextFieldDNI.getText(), Integer.parseInt(TextFieldID.getText()));   				    	
    }

   /* @FXML void filterUsersUsuario(KeyEvent event) {
        (treeTableViewUsuarios).setPredicate(usuarioTreeItem -> usuarioTreeItem.getValue().getName().toLowerCase().startsWith(filtrarUsuarioTFieldUsuarios.getText().toLowerCase()) ||
                usuarioTreeItem.getValue().getSurnames().toLowerCase().startsWith(filtrarUsuarioTFieldUsuarios.getText().toLowerCase())
                || usuarioTreeItem.getValue().getRol().toLowerCase().startsWith(filtrarUsuarioTFieldUsuarios.getText().toLowerCase()));
    }*/
    
    @FXML void filterUsersUsuario(KeyEvent event) {
    	
    }
    @FXML void mostrarDatosUsuarios(MouseEvent event) {
        
    	// Cambiamos los datos del usuario mientras se haya seleccionado uno
        if (treeTableViewUsuarios.getSelectionModel().getSelectedItem() != null) {
            // Comprobamos si el panel de datos de usuario y creacion de mensajes esta visible
        	//System.out.println(VBoxUsuarios.setVisible(false));
        	//VBoxUsuarios.setVisible(false);
            if (!VBoxInformacion.isVisible()) {
            	VBoxInformacion.setVisible(true);
                seleccionaUsuarioLabel.setVisible(false);
            }
            // Si esta visible actualizamos los datos del usuario seleccionado
            if (VBoxUsuarios.isVisible()) {
                TextFieldNombre.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getName());
                TextFieldApellidos.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getSurnames());
                TextFieldRol.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getRol());
                TextFieldDOB.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getDOB());
                TextFieldPassword.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getPassword());
                TextFieldTelephone.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getTelephone()+"");
                TextFieldDNI.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getDNI());
                TextFieldAddress.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getAdress());
                TextFieldID.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getID_User()+"");
                TextFieldPhoto.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getPhoto());
                TextFieldUsuario.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getUser());
                userImageViewUsuarios.setImage(new Image(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getPhoto()));
                //TextFieldRelaciones.setText(treeTableViewUsuarios.getSelectionModel().getSelectedItem().getValue().getRelaciones());
            }
        }
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

        ObservableList<TreeItem<Usuario>> firstLevel = FXCollections.observableArrayList();

        // Añadimos los usuarios
        ConexionBBDD c = new ConexionBBDD();

        for (Usuario user : c.sentenciaSQL("SELECT * FROM users")) {
            if (user.getRol().equals("paciente")) {
                TreeItem<Usuario> userNode = new TreeItem<>(user);
                for (Usuario relatedUsers : modelo.usuariosRelacionados(user))
                    userNode.getChildren().add(new TreeItem<>(relatedUsers));
                firstLevel.add(userNode);
            }
        }

        root.setExpanded(true);

        FilteredList<TreeItem<Usuario>> filteredList = new FilteredList<>(firstLevel, usuarioTreeItem -> true);

        filteredList.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            String filter = filtrarUsuarioTFieldUsuarios.getText();
            if (filter.isEmpty()) return usuarioTreeItem -> true;
            return usuarioTreeItem -> usuarioTreeItem.getValue().getName().startsWith(filter);
        }, filtrarUsuarioTFieldUsuarios.textProperty()));

        Bindings.bindContent(root.getChildren(), filteredList);

        // TTV Usuarios
        ttv.getColumns().add(0, nombreCol);
        ttv.getColumns().add(1, apellidosCol);
        ttv.getColumns().add(2, rolCol);
        ttv.getColumns().add(3, userIDCol);
        ttv.setRoot(root);

    } // crearTreeTableViewUsuarios()


    
}

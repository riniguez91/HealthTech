package application;

import application.controladores.*;
import application.modelos.Usuario;
import application.modelos.modelo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/application/vistas/vistaLogin.fxml"));
		Parent root = loginLoader.load();
		controladorLogin loginControlador = loginLoader.getController();

		modelo m = new modelo();
		Usuario u = new Usuario();
		loginControlador.initModelo(m,u);

		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setScene(new Scene(root, visualBounds.getWidth(), visualBounds.getHeight()));

		//set Stage boundaries to visible bounds of the main screen
		primaryStage.setX(visualBounds.getMinX());
		primaryStage.setY(visualBounds.getMinY());
		primaryStage.setWidth(visualBounds.getWidth());
		primaryStage.setHeight(visualBounds.getHeight());
		primaryStage.setMaximized(true);
		primaryStage.getIcons().add(new Image("@../../resources/fotos/Logo.png"));
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
	
}
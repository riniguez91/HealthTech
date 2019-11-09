package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/application/vistas/vistaPaciente.fxml"));
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setScene(new Scene(root, visualBounds.getWidth(), visualBounds.getHeight()));


		//set Stage boundaries to visible bounds of the main screen
		primaryStage.setX(visualBounds.getMinX());
		primaryStage.setY(visualBounds.getMinY());
		primaryStage.setWidth(visualBounds.getWidth());
		primaryStage.setHeight(visualBounds.getHeight());
		primaryStage.setMaximized(true);
		primaryStage.show();
	}


	public static void main(String[] args) { 
		launch(args); 
		
	}
	
}
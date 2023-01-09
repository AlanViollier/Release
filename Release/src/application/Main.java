package application;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

/**
 * Starter class Main.
 * 
 * @author Alan Viollier
 */
public class Main extends Application {
	
	private static Stage stg;
	private final static int WIDTH = 640;
	private final static int HEIGHT = 480;
	
	// Starts the program by setting up the scene.
	@Override
	public void start(Stage primaryStage) {
		try {
			stg = primaryStage;
			stg.setWidth(WIDTH);
			stg.setHeight(HEIGHT);
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("lock.fxml"));
			Scene scene = new Scene(root,WIDTH,HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Release");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Changes from one scene to another.
	public void changeScene(String fxml) throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getScene().setRoot(pane);
	}
	
	// Main method.
	public static void main(String[] args) {
		launch(args);
	}
}

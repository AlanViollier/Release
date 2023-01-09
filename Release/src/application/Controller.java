package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Controller abstract class for implementing controllers.
 * 
 * @author Alan Viollier
 */
public abstract class Controller {
	
	// Initiates the utility class for all all the other controllers to use.
	public static Utility utility = new Utility();
	
	// Switches scenes
	public void switchScene(ActionEvent event, String s) throws IOException {
		Parent homePage = FXMLLoader.load(getClass().getResource(s));
		Scene homePageScene = new Scene(homePage);
		Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
		stg.setScene(homePageScene);
		stg.show();
	}
	
	// Makes an alert popup
	public void alert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);		
		alert.setContentText(content);
		alert.showAndWait();
	}

}

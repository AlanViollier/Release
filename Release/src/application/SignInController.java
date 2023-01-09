package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller for the sign up screen.
 * 
 * @author Alan Viollier
 */
public class SignInController extends Controller {
	
	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
	
	public void signIn(ActionEvent event) throws Exception{		
		
		// verify that account is valid
		// if so, go to home page
		if (Controller.utility.isAccountValid(username.getText(), password.getText())) {
			super.switchScene(event, "home.fxml");
		} else {
			this.alert("Warning", "This account is not valid");
		}
		
	}
	
	// On back event go back to lock page.
	public void back(ActionEvent event) throws IOException{
		super.switchScene(event, "lock.fxml");
	}
	
	
}

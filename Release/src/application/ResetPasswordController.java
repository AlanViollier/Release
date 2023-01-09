package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller for the reset password screen.
 * 
 * @author Alan Viollier
 */
public class ResetPasswordController extends Controller{
	
	@FXML
	private TextField newPassword;
	
	// save password
	public void save(ActionEvent event) throws IOException{
		// check that user has entered password
		if (newPassword.getText().length() == 0) {
			this.alert("Warning", "You must enter a password");
			return;
		}
		
		// successful password, save it
		Controller.utility.updatePassword(newPassword.getText());
		this.switchScene(event, "signIn.fxml");
		
	}
	
	// On back event go back to the lock page.
	public void back(ActionEvent event) throws IOException{
		super.switchScene(event, "home.fxml");
	}

}

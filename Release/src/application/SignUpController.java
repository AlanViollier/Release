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
public class SignUpController extends Controller {
	
	@FXML
	private TextField name;
	
	@FXML
	private TextField password;
	
	@FXML
	private TextField email;
	
	// On next event go to the security question page.
	public void next(ActionEvent event) throws Exception{
		
		String sName = name.getText();
		String sPassword = password.getText();
		String sEmail = email.getText();
		
		// User must enter all fields first
		if (sName.length() == 0 || sPassword.length() == 0 || sEmail.length() == 0) {
			this.alert("Warning", "You must enter all fields first");
			return;
		}
		if(sName.contains(",") || sEmail.contains(",")) {
			this.alert("Warning", "Sorry, no commas :(");
			return;
		}
		
		// Check if account w/ username or email already exists
		if (Controller.utility.doesAccountExist(sName, sEmail)) {
			this.alert("Warning", "Account with username or email already exists");
			return;
		}
		
		// Check valid email format
		int indexOfAt = sEmail.indexOf("@");
		
		// Check if @ exists
		if (indexOfAt != -1) {
			
			// Get characters everything after the @
			String temp = sEmail.substring(indexOfAt+1);
			
			if (!temp.contains(".com") && !temp.contains(".net") && !temp.contains(".edu")&& !temp.contains(".org")) {
				this.alert("Warning", "Invalid email");
				return;
			}
		} else {
			this.alert("Warning", "Invalid email");
			return;
		}
		
		// Add account to list of accounts.
		Controller.utility.saveAccount(sName, sPassword, sEmail);
		super.switchScene(event, "security.fxml");
		
	}
	
	// On back event go back to lock page.
	public void back(ActionEvent event) throws IOException{
		super.switchScene(event, "lock.fxml");
	}
	
}
	

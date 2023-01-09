package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller for the reset master password check screen.
 * 
 * @author Alan Viollier
 */
public class ResetPasswordCheckController extends Controller implements Initializable {
	
	
	@FXML
	private TextField answer;
	
	@FXML
	private Text question;
	
	// On initialization load the website.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Set the correct question.
		question.setText(Controller.utility.getLoggedInQuestion());
    }
	
	// If the password is correct move on.
	public void next(ActionEvent event) throws IOException{
		if (answer.getText().equals(Controller.utility.getLoggedInAnswer())) {
			this.switchScene(event, "resetPassword.fxml");
		} else {
			this.alert("Warning", "Incorrect answer");
		}
	}
	
	// On back event go back to the lock page.
	public void back(ActionEvent event) throws IOException{
		super.switchScene(event, "home.fxml");
	}


}

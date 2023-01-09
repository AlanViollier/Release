package application;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * Controller for the security screen.
 * 
 * @author Alan Viollier
 */
public class SecurityController extends Controller implements Initializable {

	// Security questions
	private String[] securityQuestions = new String[]
	{
	"What is your mom's name?",
	"What college did you go to?",
	"What is your favorite sport?",
	"What is your favorite food?"
	};
	
	@FXML
	private ChoiceBox<String> question;
	
	@FXML
	private TextField answer;
	
	// On initialization load the questions.
	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {		
		// Load questions upon initialization
		question.getItems().addAll(securityQuestions);
		
	}
	
	// On sumbit event go to the home page.
	public void submit(ActionEvent event) throws IOException{
		// User must enter all fields first
		if (question.getValue() == null || answer.getText().length() == 0) {
			this.alert("Warning", "You must enter all fields first");
			return;
		}
		
		// Save security info
		Controller.utility.saveSecurity((String) question.getValue(), answer.getText());
		
		// create account in database
		Controller.utility.createAccount();
		
		// Redirect user to lock scren
		this.switchScene(event, "signIn.fxml");
	}
	
	/** 
	* On back event go back to lock page.
	* 
	* @param event the back event
	*/
	public void back(ActionEvent event) throws IOException{
		super.switchScene(event, "lock.fxml");
	}

}

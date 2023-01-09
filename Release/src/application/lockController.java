package application;

import java.io.IOException;
import javafx.event.ActionEvent;

/**
 * Controller for the Lock screen.
 * 
 * @author Alan Viollier
 */
public class lockController extends Controller {
	

	//On signUp event go to the sign up page.
	public void signUp(ActionEvent event) throws IOException {
		super.switchScene(event, "signUp.fxml");
	}
	
	//On signIn event go to the sign in page.
	public void signIn(ActionEvent event) throws IOException {
		super.switchScene(event, "signIn.fxml");
	}
	
	//On why event go to the sign in page.
	public void why(ActionEvent event) throws IOException {
		super.switchScene(event, "why.fxml");
	}

}

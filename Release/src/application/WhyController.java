package application;

import java.io.IOException;
import javafx.event.ActionEvent;

/**
 * Controller for the Lock screen.
 * 
 * @author Alan Viollier
 */
public class WhyController extends Controller {
		
	// On why event go to the sign in page.
	public void back(ActionEvent event) throws IOException {
		super.switchScene(event, "lock.fxml");
	}

}

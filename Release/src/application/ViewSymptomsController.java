package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * Controller for the view symptoms screen.
 * 
 * @author Alan Viollier
 */
public class ViewSymptomsController extends Controller implements Initializable {

	@FXML
	private TextArea symptoms;
	

	// On initialization load the website.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		symptoms.setEditable(true);
		try {
			symptoms.setText(Controller.utility.getAllSymptomsData());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		symptoms.setEditable(false);
    }
	
	// Go back to home page.
	public void home(ActionEvent e) throws IOException {
		super.switchScene(e, "home.fxml");
	}

	// Go to the track symptoms page.
	public void track(ActionEvent e) throws IOException {
		super.switchScene(e, "trackSymptoms.fxml");
	}
}

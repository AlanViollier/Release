package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * Controller for the home screen.
 * 
 * @author Alan Viollier
 */
public class HomeController extends Controller implements Initializable {

	@FXML
	private Text welcome;
	
	@FXML
	private TextArea journalPreview;

	// On initialization load the home screen with the proper name and preview.
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Customize the welcome text to say the username.
		welcome.setText("Welcome back "+Controller.utility.getLoggedInUsername()+"!");
		
        // Remove any temporary items in the journal preview
		journalPreview.setEditable(true);
		try {
			journalPreview.setText(Controller.utility.getJournalData());
		} catch (IOException e) {
			e.printStackTrace();
		}
		journalPreview.setEditable(false);
    }
	
	// Go back to sign in page.
	public void backToSignIn(ActionEvent e) throws IOException {
		Controller.utility.logOut();
		super.switchScene(e, "signIn.fxml");
	}

	// Go to reset password page.
	public void resetPassword(ActionEvent e) throws IOException {
		super.switchScene(e, "resetPasswordCheck.fxml");
	}
	
	// Go to journal page.
	public void journal(ActionEvent e) throws IOException {
		super.switchScene(e, "journal.fxml");
	}
	
	// Go to view journal page.
	public void viewJournal(ActionEvent e) throws IOException {
		super.switchScene(e, "viewJournal.fxml");
	}
	
	// Go to track symptoms page page.
	public void trackSymptoms(ActionEvent e) throws IOException {
		super.switchScene(e, "trackSymptoms.fxml");
	}
	
	// Go to view symptoms page.
	public void viewSymptoms(ActionEvent e) throws IOException {
		super.switchScene(e, "viewSymptoms.fxml");
	}

}

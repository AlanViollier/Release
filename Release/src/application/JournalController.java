package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controller for the jounral screen.
 * 
 * @author Alan Viollier
 */
public class JournalController extends Controller implements Initializable {

	@FXML
	private TextArea journal;
	
	@FXML
	private ToggleGroup category;
	
	@FXML
	private RadioButton thoughts;
	
	@FXML
	private RadioButton todo;
	
	@FXML
	private RadioButton ideas;
	
	@FXML
	private RadioButton stressors;
	
	@FXML
	private RadioButton improvements;


	// On initialization load event listeners.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Sets the journal prompt
		journal.setPromptText("Write anything");
		//Allows you to press enter while on the TextArea instead of having to click release
		journal.setOnKeyPressed(new EventHandler<KeyEvent>() {
			 
		    @Override
		    public void handle(KeyEvent event) {
		        if(event.getCode() == KeyCode.ENTER) {
		        	try {
						release(null);
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
		    }
		});
		journal.setOnKeyReleased(new EventHandler<KeyEvent>() {
			 
		    @Override
		    public void handle(KeyEvent event) {
		        if(event.getCode() == KeyCode.ENTER) {
		        	journal.setText("");
		        }
		    }
		});
    }
	
	// Go back to home page.
	public void home(ActionEvent e) throws IOException {
		super.switchScene(e, "home.fxml");
	}

	// Save journal writing.
	public void release(ActionEvent e) throws IOException {
		if(journal.getText().equals("")) {
			this.alert("Warning", "You did not enter anything");
			return;
		} else if(category.getSelectedToggle() == thoughts) {
			Controller.utility.writeToJournal(journal.getText(),"thoughts");
		} else if(category.getSelectedToggle() == todo) {
			Controller.utility.writeToJournal(journal.getText(),"todo");
		} else if(category.getSelectedToggle() == ideas) {
			Controller.utility.writeToJournal(journal.getText(),"ideas");
		} else if(category.getSelectedToggle() == stressors) {
			Controller.utility.writeToJournal(journal.getText(),"stressors");
		} else if(category.getSelectedToggle() == improvements) {
			Controller.utility.writeToJournal(journal.getText(),"improvements");
		}
		journal.setPromptText("Great! Keep Going!");
		journal.setText("");
	}

}

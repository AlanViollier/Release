package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * Controller for the view journal screen.
 * 
 * @author Alan Viollier
 */
public class ViewJournalController extends Controller implements Initializable {
	
	@FXML
	private TextArea thoughts;

	@FXML
	private TextArea todo;

	@FXML
	private TextArea ideas;

	@FXML
	private TextArea stressors;

	@FXML
	private TextArea improvements;
	
	// Gets all the categorized data from the file and puts it into the respective text areas.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			String data[] = Controller.utility.getCategoryJournalData();
			thoughts.setEditable(true);
			thoughts.setText(data[0]);
			
			todo.setEditable(true);
			todo.setText(data[1]);
			
			ideas.setEditable(true);
			ideas.setText(data[2]);
			
			stressors.setEditable(true);
			stressors.setText(data[3]);
			
			improvements.setEditable(true);
			improvements.setText(data[4]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Go back to home page, but sends all the categorized edited data back to utility to be saved first.
	public void home(ActionEvent e) throws IOException {
		String texts[] = new String[5];
		for(int i=0;i<texts.length;i++) {
			switch(i) {
			case 0: 
				if(!thoughts.getText().equals("")) {
					texts[i]=thoughts.getText();
				}
				break;
			case 1: 
				if(!todo.getText().equals("")) {
					texts[i]=todo.getText();
				}
				break;
			case 2: 
				if(!ideas.getText().equals("")) {
					texts[i]=ideas.getText();
				}
				break;
			case 3: 
				if(!stressors.getText().equals("")) {
					texts[i]=stressors.getText();
				}
				break;
			case 4: 
				if(!improvements.getText().equals("")) {
					texts[i]=improvements.getText();
				}
				break;
			}
		}
		Controller.utility.editJournal(texts);
		super.switchScene(e, "home.fxml");
	}

}

package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.Calendar;

/**
 * Controller for the track symptoms screen.
 * 
 * @author Alan Viollier
 */
public class TrackSymptomsController extends Controller implements Initializable {

	@FXML
	private ComboBox<String> name;
	
	@FXML
	private VBox traits;

	// On initialization load the website.
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			String[] symptomList = Controller.utility.getSymptomList();
			if(symptomList!=null) {
				name.getItems().addAll(symptomList);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		name.setValue("");
		
    }
	
	// Go back to home page.
	public void home(ActionEvent e) throws IOException {
		super.switchScene(e, "home.fxml");
	}

	// Go to the view Symptoms page.
	public void view(ActionEvent e) throws IOException {
		super.switchScene(e, "viewSymptoms.fxml");
	}
	
	// On selection of name, set the appropriate attributes of the specific symptom.
	public void name(ActionEvent e) throws IOException {
		
		String itemName = name.getValue();
		// makes sure the name isn't empty.
		if(itemName!=null && !itemName.equals("")) {
			String[] attributes = Controller.utility.getSymptomAttributes(itemName);
			if(attributes==null) {
				return;
			}
			// Clears the attributes
			traits.getChildren().clear();
			//based on the specific attribute whether a title or a text or a slider, add that to the vbox.
			for(int i=0;i<attributes.length;i++) {
				if(i%2==0) {
					Text traitName = new Text(attributes[i]);
					traitName.setFont(new Font("Hiragino Sans W0", 25));
					traitName.setFill(Paint.valueOf("#01579b"));
					traits.getChildren().add(traitName);
				} else {
					if(attributes[i].equals("Text")) {
						TextField traitCharacteristic = new TextField();
						traitCharacteristic.setFont(new Font("Hiragino Sans W9", 15));
						traitCharacteristic.setPromptText("Characteristic");
						traitCharacteristic.setEditable(true);
						traitCharacteristic.setAlignment(Pos.CENTER);
						traits.getChildren().add(traitCharacteristic);
					} else {
						Slider traitSlider = new Slider(0,10,5);
						traitSlider.setShowTickMarks(true);
						traitSlider.setShowTickLabels(true);
						traitSlider.setMajorTickUnit(1);
						traitSlider.setMinorTickCount(0);
						traitSlider.setBlockIncrement(1);
						traitSlider.setSnapToTicks(true);
						traits.getChildren().add(traitSlider);
					}
				}
			}
		}
	}
	
	// Sends the current symptom with its traits to be written in the file and clears everything for the next one.
	public void track(ActionEvent e) throws IOException {
		
		// Get the name
		String itemName = name.getValue();
		// If the name is empty send an error and stop the function
		if(itemName==null||itemName.equals("")) {
			this.alert("Warning", "You did not put a name for your symptom");
			return;
		}
		
		//Get the Vbox items
		ObservableList<Node> itemsNode = traits.getChildren();
		// Make 2 lists
		// one for the data
		// one for the attribute names
		// itemsnode size + 1 so its vbox items + name
		String itemsString[] = new String[itemsNode.size()+2];
		String symptomCharacteristics[] = new String[itemsNode.size()+1];
		itemsString[0] = Calendar.getInstance().getTime().toString();
		itemsString[1] = itemName;
		symptomCharacteristics[0] = itemName;
		
		// go through all the vbox items
		for(int i=0;i<itemsNode.size();i++) {
			Node itemNode = itemsNode.get(i);
			// if its a text then just save the text to both lists
			if(itemNode instanceof Text) {
				itemsString[i+2] = ((Text) itemNode).getText();
				symptomCharacteristics[i+1] = ((Text) itemNode).getText();
			}
			// if its a textfield save the data to the data list and the title or "text" (if its a characteristic) to the attributes.
			// textfields cannot be left empty or a warning will pop up.
			else if(itemNode instanceof TextField) {
				if(((TextField) itemNode).getText().equals("")) {
					this.alert("Warning", "You left an empty TextField");
					return;
				}
				itemsString[i+2] = ((TextField) itemNode).getText();
				if(i%2!=0) {
					symptomCharacteristics[i+1] = "Text";
				} else {
					symptomCharacteristics[i+1] = ((TextField) itemNode).getText();
				}
			}
			//if its a slider save the data to the data list and that its a slider to the attributes list.
			else if(itemNode instanceof Slider) {
				itemsString[i+2] = Double.toString(((Slider) itemNode).getValue());
				symptomCharacteristics[i+1] = "Slider";
			}
		}
		
		Controller.utility.addSymptom(symptomCharacteristics);
		
		Controller.utility.trackSymptom(itemsString);
		
		// Clear everything
		name.getItems().clear();
		
		traits.getChildren().clear();
		
		// refresh name combobox
		String[] symptomList = Controller.utility.getSymptomList();
		if(symptomList!=null) {
			name.getItems().addAll(symptomList);
		}
		name.setValue("");
		
	}
	
	// Adds a trait to the traits VBox with a slider based trait
	public void addBar(ActionEvent e) throws IOException {
		TextField traitName = new TextField();
		traitName.setFont(new Font("Hiragino Sans W9", 15));
		traitName.setPromptText("Trait");
		traitName.setEditable(true);
		traitName.setAlignment(Pos.CENTER);
		
		
		Slider traitSlider = new Slider(0,10,5);
		traitSlider.setShowTickMarks(true);
		traitSlider.setShowTickLabels(true);
		traitSlider.setMajorTickUnit(1);
		traitSlider.setMinorTickCount(0);
		traitSlider.setBlockIncrement(1);
		traitSlider.setSnapToTicks(true);
		
		traits.getChildren().add(traitName);
		traits.getChildren().add(traitSlider);
	}
	
	// Adds a trait to the traits VBox with a text based trait
	public void addText(ActionEvent e) throws IOException {
		TextField traitName = new TextField();
		traitName.setFont(new Font("Hiragino Sans W9", 15));
		traitName.setPromptText("Trait");
		traitName.setEditable(true);
		traitName.setAlignment(Pos.CENTER);
		
		TextField traitCharacteristic = new TextField();
		traitCharacteristic.setFont(new Font("Hiragino Sans W9", 15));
		traitCharacteristic.setPromptText("Characteristic");
		traitCharacteristic.setEditable(true);
		traitCharacteristic.setAlignment(Pos.CENTER);
		
		traits.getChildren().add(traitName);
		traits.getChildren().add(traitCharacteristic);
	}
	
	// Clears the last trait in the scrollpane of traits.
	public void clear(ActionEvent e) throws IOException {
		if(traits.getChildren().size()!=0) {
			traits.getChildren().remove(traits.getChildren().size() - 1);
			traits.getChildren().remove(traits.getChildren().size() - 1);
		}
	}

}

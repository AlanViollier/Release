package application;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import edu.sjsu.yazdankhah.crypto.util.PassUtil;
import java.io.FileNotFoundException;

/**
 * Model
 * 
 * @author Alan Viollier
 */
public class Utility {
	
	// Text file names
	final static String accountsFilePath = "accounts.txt";
	final static String journalDataFilePath = "journalData.txt";
	final static String symptomDataFilePath = "symptomData.txt";
	final static String userSymptomsFilePath = "userSymptoms.txt";
	
	static Scanner s;
	private static Account loggedInAccount;
	private static Account signUpAccount;
	
	// Encryption/Decryption utility class.
	private static PassUtil passUtil = new PassUtil();

	// Creates a model with a logged in account and sign up account.
	public Utility () {
		Utility.loggedInAccount = new Account();
		Utility.signUpAccount = new Account();
	}
	
	// Saves the account name password and email
	public void saveAccount(String name, String password, String email) {
		Utility.signUpAccount.setUserName(name);
		Utility.signUpAccount.setPassword(password);
		Utility.signUpAccount.setEmail(email);
	}
	
	// Saves the account security question and answer.
	public void saveSecurity(String question, String answer) {
		Utility.signUpAccount.setSecurityQuestion(question);
		Utility.signUpAccount.setSecurityAnswer(answer);
	}
	
	// Creates an account.
	public void createAccount() throws IOException {
		
		//Creating buffers
		FileWriter fw = new FileWriter(accountsFilePath, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		
		//Writes new account to the account file.
		// Encrypt password when putting it into files.
		pw.println(signUpAccount.getUserName()+","+passUtil.encrypt(signUpAccount.getPassword())+","+signUpAccount.getEmail());
		// Encrypt answer when putting it into files.
		pw.println(signUpAccount.getSecurityQuestion()+","+passUtil.encrypt(signUpAccount.getSecurityAnswer()));
		// Store security and answer for logging in
		pw.println("END");	
		// Ensures that the database visually separates accounts
		pw.flush();
		pw.close();
		
		signUpAccount = null;
	}
	
	// Checks if an account exists.
	public boolean doesAccountExist(String user, String email) throws Exception {
		s = new Scanner(new File(accountsFilePath));
		while(s.hasNextLine()) {
			String databaseAccountInfo = s.nextLine();				// This should equal to user, password, email
			s.nextLine();											// This should equal to question, answer
			String endOfDatabaseAccount = s.nextLine();				// This should equal to "END"
			
			// Ensures that data is not corrupt
			if (!endOfDatabaseAccount.equals("END"))
				throw new Exception(": : DATABASE ERROR: USERNAME AND PASSWORD UNALIGNED. PLEASE CHECK DATABASE FILE: :");
			
			
			// Get username and password from databaseAccountInfo
			String[] splitAccountInfo = databaseAccountInfo.split(",");
			String databaseUsername = splitAccountInfo[0];
			String databaseEmail = splitAccountInfo[2];
					
			// Check if username/email already exists in database
			if (user.equals(databaseUsername) || email.equals(databaseEmail)) {
				s.close();
				return true;
			}
		}
		s.close();
		return false;
	}
	
	// Correct format of each account in database
	// 1) ACCOUNT INFO
	// 2) SECURITY ACCOUNT INFO
	// 3) END
	
	// This also logs the user into the model
	public boolean isAccountValid(String username, String password) throws Exception {
		s = new Scanner(new File(accountsFilePath));
		while(s.hasNextLine()) {
			String databaseAccountInfo = s.nextLine();
			String databaseSecurityAccountInfo = s.nextLine();
			String endOfDatabaseAccount = s.nextLine();				// This should equal to "END"
			
			// Ensures that data is not corrupt
			if (!endOfDatabaseAccount.equals("END"))
				throw new Exception(": : DATABASE ERROR: USERNAME AND PASSWORD UNALIGNED. PLEASE CHECK DATABASE FILE: :");
			
			
			// Get username and password 
			String[] splitAccountInfo = databaseAccountInfo.split(",");
			String[] splitSecurityAccountInfo = databaseSecurityAccountInfo.split(",");
			String databaseUsername = splitAccountInfo[0];
			// Decrypt password when retieving it from files.
			String databasePassword = passUtil.decrypt(splitAccountInfo[1]);
			String databaseEmail = splitAccountInfo[2];
			String databaseQuestion = splitSecurityAccountInfo[0];
			// Decrypt answer when retieving it from files.
			String databaseAnswer = passUtil.decrypt(splitSecurityAccountInfo[1]);
			
			// Account match found
			if (databaseUsername.equals(username) && databasePassword.equals(password)) {
				this.logIn(databaseUsername, databasePassword, databaseEmail, databaseQuestion, databaseAnswer);
				s.close();
				return true;
			}
		}
		s.close();
		return false;
	}
	
	// Logs the account into the system
	private void logIn(String username,String password,String email,String question,String answer) {
		loggedInAccount = new Account(username, password, email, question, answer);
	}
	
	// Log account out of system
	public void logOut() {
		loggedInAccount = null;
	}
	
	// Updates password in the database
	public void updatePassword(String newPassword) throws IOException {
		
		/* Implementation:
		 * Read through all lines in file and store it into a string object
		 * Use replaceAll() to search through string and replace string's password w/ new password
		 * Rewrite the string back to the file
		 * */
		
		// STEP 1) READ FILE
		String oldContent = "";
		s = new Scanner(new File(accountsFilePath));
		while(s.hasNextLine()) {
			oldContent = oldContent + s.nextLine() + System.lineSeparator();
		}
		s.close();
		
		
		// STEP 2) REPLACE OLD PASSWORD w/ NEW PASSWORD
		// Find the account to be modified in the string
		String username = Utility.loggedInAccount.getUserName();
		String password = Utility.loggedInAccount.getPassword();
		String email = Utility.loggedInAccount.getEmail();
		
		// Replace the account's password w/ a new password in the string
		String findThisString = username + "," + passUtil.encrypt(password) + "," + email;
		// Encrypt password when putting it into files.
		String replacementString = username + "," + passUtil.encrypt(newPassword) + "," + email;
		String newContent = oldContent.replace(findThisString, replacementString);
		
		
		// STEP3) REWRITE STRING BACK TO DATABASE
		// newContent contains the fully updated database. Write it back to the database file.
		FileWriter fw = new FileWriter(accountsFilePath);
		fw.write(newContent);
		fw.close();
		
		// Finally, log out
		this.logOut();
	}
	
	public String getLoggedInUsername() {
		return loggedInAccount.getUserName();
	}
	
	public String getLoggedInQuestion() {
		return loggedInAccount.getSecurityQuestion();
	}
	
	public String getLoggedInAnswer() {
		return loggedInAccount.getSecurityAnswer();
	}

	// Adds journal items to file and encrypts it.
	public void writeToJournal(String text, String category) throws IOException {
		// Buffers
		FileWriter fw = new FileWriter(journalDataFilePath, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		
		//print encrypted text to text file
		pw.println(loggedInAccount.getUserName()+","+category+","+passUtil.encrypt(text));
		pw.flush();
		pw.close();
	}
	
	// Re-writes edited jounral back into the journal encrypted with the unedited items.
	public void editJournal(String texts[]) throws IOException {
		// Gets all the other journal entries that aren't edited.
		String allOtherText = "";
		s = new Scanner(new File(journalDataFilePath));
		while(s.hasNextLine()) {
			String line = s.nextLine();
			String[] splitLine = line.split(",");
			if(!splitLine[0].equals(loggedInAccount.getUserName())) {
				allOtherText = allOtherText.concat(line);
				allOtherText += "\n";
			}
		}
		s.close();
		
		FileWriter fw = new FileWriter(journalDataFilePath);
		//Prints the unedited journal entries back into a clean file.
		fw.write(allOtherText);
		// prints the rest of the edited data back into the file with the correct tags and name.
		for(int i=0;i<texts.length;i++) {
			switch(i) {
			case 0: 
				if(texts[i]!=null) {
					fw.write(loggedInAccount.getUserName()+",thoughts,"+passUtil.encrypt(texts[i])+"\n");
				}
				break;
			case 1: 
				if(texts[i]!=null) {
					fw.write(loggedInAccount.getUserName()+",todo,"+passUtil.encrypt(texts[i])+"\n");
				}
				break;
			case 2: 
				if(texts[i]!=null) {
					fw.write(loggedInAccount.getUserName()+",ideas,"+passUtil.encrypt(texts[i])+"\n");
				}
				break;
			case 3: 
				if(texts[i]!=null) {
					fw.write(loggedInAccount.getUserName()+",stressors,"+passUtil.encrypt(texts[i])+"\n");
				}
				break;
			case 4: 
				if(texts[i]!=null) {
					fw.write(loggedInAccount.getUserName()+",improvements,"+passUtil.encrypt(texts[i])+"\n");
				}
				break;
			}
		}
		fw.close();
	}
	
	// Gets the logged in users journal notes uncategorized.
	public String getJournalData() throws IOException {
		
		String allText = "";
		s = new Scanner(new File(journalDataFilePath));
		while(s.hasNextLine()) {
			String line = s.nextLine();
			String[] splitLine = line.split(",");
			if(splitLine[0].equals(loggedInAccount.getUserName())) {
				allText = allText.concat(passUtil.decrypt(splitLine[2]));
				allText += "\n";
			}
		}
		s.close();
		return allText;
	}

	// Gets the logged in users categorized journal notes.
	public String[] getCategoryJournalData() throws FileNotFoundException {
		String categoryText[] = new String[5];
		for(int i = 0; i<categoryText.length;i++) {categoryText[i]="";}
		s = new Scanner(new File(journalDataFilePath));
		while(s.hasNextLine()) {
			String line = s.nextLine();
			String[] splitLine = line.split(",");
			if(splitLine[0].equals(loggedInAccount.getUserName())) {
				if(splitLine[1].equals("thoughts")) {
					categoryText[0] = categoryText[0].concat(passUtil.decrypt(splitLine[2]));
					categoryText[0] += "\n";
				} else if(splitLine[1].equals("todo")) {
					categoryText[1] = categoryText[1].concat(passUtil.decrypt(splitLine[2]));
					categoryText[1] += "\n";
				} else if(splitLine[1].equals("ideas")) {
					categoryText[2] = categoryText[2].concat(passUtil.decrypt(splitLine[2]));
					categoryText[2] += "\n";
				} else if(splitLine[1].equals("stressors")) {
					categoryText[3] = categoryText[3].concat(passUtil.decrypt(splitLine[2]));
					categoryText[3] += "\n";
				} else if(splitLine[1].equals("improvements")) {
					categoryText[4] = categoryText[4].concat(passUtil.decrypt(splitLine[2]));
					categoryText[4] += "\n";
				}
			}
		}
		s.close();
		return categoryText;
	}
	
	// Adds the symptoms to the logged in users list of symptoms.
	public void addSymptom(String[] symptom) throws IOException {
		
		String allOtherText = "";
		s = new Scanner(new File(userSymptomsFilePath));
		while(s.hasNextLine()) {
			String line = s.nextLine();
			String[] splitLine = line.split(",");
			if(!splitLine[0].equals(loggedInAccount.getUserName()) || !passUtil.decrypt(splitLine[1]).equals(symptom[0])) {
				allOtherText = allOtherText.concat(line);
				allOtherText += "\n";
			}
		}
		s.close();
		
		FileWriter fw = new FileWriter(userSymptomsFilePath);
		//Prints the unedited journal entries back into a clean file.
		fw.write(allOtherText);

		String text = loggedInAccount.getUserName();
		for(int i=0;i<symptom.length;i++) {
			text += ","+passUtil.encrypt(symptom[i]);
		}
		fw.write(text+"\n");
		fw.close();
	}
	
	// Add the symptoms and all the traits to be stored in the symptom data file.
	public void trackSymptom(String[] data) throws IOException {
		// Buffers
		FileWriter fw = new FileWriter(symptomDataFilePath, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		
		//print encrypted text to text file
		String text = loggedInAccount.getUserName();
		for(int i=0;i<data.length;i++) {
			text += ","+passUtil.encrypt(data[i]);
		}
		pw.println(text);
		pw.flush();
		pw.close();
	}
	
	// Get list of symptoms for the combobox for the logged in user
	public String[] getSymptomList() throws FileNotFoundException {
		boolean found = false;
		s = new Scanner(new File(userSymptomsFilePath));
		ArrayList<String> list = new ArrayList<String>();
		while(s.hasNextLine()) {
			String line = s.nextLine();
			String[] splitLine = line.split(",");
			if(splitLine[0].equals(loggedInAccount.getUserName())) {
				list.add(passUtil.decrypt(splitLine[1]));
				found = true;
			}
		}
		s.close();
		if(!found) {
			return null;
		}
		String listArray[] = new String[list.size()];
		for(int i=0;i<list.size();i++) {
			listArray[i] = list.get(i);
		}
		return listArray;
	}
	
	// Gets the symtom attributes to but items in the Vbox for the symptom chosen in the combobox.
	public String[] getSymptomAttributes(String symptom) throws FileNotFoundException {
		s = new Scanner(new File(userSymptomsFilePath));
		while(s.hasNextLine()) {
			String line = s.nextLine();
			String[] splitLine = line.split(",");
			if(splitLine[0].equals(loggedInAccount.getUserName()) && passUtil.decrypt(splitLine[1]).equals(symptom)) {
				String list[] = new String[splitLine.length-2];
				for(int i=2;i<splitLine.length;i++) {
					list[i-2] = passUtil.decrypt(splitLine[i]);
				}
				return list;
			}
		}
		s.close();
		
		return null;
	}
	
	// Get all symptom data.
	public String getAllSymptomsData() throws FileNotFoundException{
		
		String data = "";
		s = new Scanner(new File(symptomDataFilePath));
		while(s.hasNextLine()) {
			String line = s.nextLine();
			String[] splitLine = line.split(",");
			if(splitLine[0].equals(loggedInAccount.getUserName())) {
				for(int i=1;i<splitLine.length;i++) {
					if(!(i<4) && i%2==0) {
						data += passUtil.decrypt(splitLine[i])+" ";
					}
					else if(i<3) {
						data += passUtil.decrypt(splitLine[i])+" - ";
					} else {
						data += passUtil.decrypt(splitLine[i])+": ";
					}
				}
				data += "\n";
			}
		}
		s.close();
		return data;
	}
	
}

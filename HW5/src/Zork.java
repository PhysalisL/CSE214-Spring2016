import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

/**
 * This is the driver class with the main method.
 * It allows users to use methods in the StoryTree to manipulate the 
 * attributes of the nodes. 
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 *
 */
public class Zork {
	private static StoryTree tree = new StoryTree();
	private static String fileName;
	private static final Scanner sc = new Scanner(System.in);
	private static final String mainMenu = 
			"Would you like to edit (E), play (P) or quit (Q)? ";
	private static final String menu = 
			"Zork Editor:\n"+
			"V: View the cursor's position, option and message.\n"+
			"S: Select a child of this cursor.\n"+
			"O: Set the option of the cursor.\n"+
			"M: Set the message of the cursor.\n"+
			"A: Add a child StoryNode to the cursor.\n"+
			"D: Delete one of the cursor's children and all its "+
			"descendants.\n"+
			"R: Move the cursor to the root of the tree.\n"+
			"U: Move cursor to parent\n"+
			"Q: Quit editing and return to main menu.\n";
	
	/**
	 * Main method
	 * 
	 * @param args
	 * Commandline args
	 */
	public static void main(String[]args){
		boolean validFile = false;
		System.out.println(tree.getCursorMessage());
		do{
			System.out.println("Enter the file name");
			fileName = sc.nextLine();
			try {
				System.out.println("Loading...");
				tree = StoryTree.readTree(fileName);
				System.out.println("Loaded!");
				validFile = true;
			} catch (IllegalArgumentException | FileNotFoundException e) {
				validFile = false;
				System.out.println("FILE NOT FOUND!");
			} catch (DataFormatException e){
				validFile = false;
				System.out.println("FILE FORMAT DOES NOT MATCH!");
			}
		}while(!validFile);
		
		boolean programEnd = false;
		while(!programEnd){
			System.out.println(mainMenu);
			switch(sc.nextLine().toUpperCase().trim()){
			case "P":
				play();
				break;
			case "E": 
				edit();
				break;
			case "Q":
				System.out.printf("Game saved to %s...\n", fileName);
				StoryTree.saveTree(fileName, tree);
				System.out.println("Save successful!");
				programEnd = true;
				break;
			default: System.out.println("Invalid command");
			}
		}
		
		System.out.println("Program terminated normally...");
	}
	
	/**
	 * Prints the child node options of the cursor node.
	 */
	private static void printOptions(StoryTree tree){
		String[][]optionList = tree.getOptions();
		for(int i=0; i<optionList.length; i++){
			if(optionList[i].length != 0){
				System.out.println("Option "+optionList[i][0].
						substring(optionList[i][0].length()-1)+": "+
						optionList[i][1]);
			}
		}
	}
	
	/**
	 * Play the StoryTree.
	 * This method is looped until game is finished.
	 */
	private static void play(){
		tree.resetCursor();
		System.out.println(tree.getCursorMessage());
		while(tree.getGameState() == GameState.GAME_NOT_OVER){
			String[][]optionList = tree.getOptions();
			String userInput;
			for(int i=0; i<optionList.length; i++){
				if(optionList[i].length != 0){
					System.out.println("Option "+optionList[i][0].
							substring(optionList[i][0].length()-1)+": "+
							optionList[i][1]);
				}
			}
			System.out.println("Select Option :");
			userInput = sc.nextLine().trim().toUpperCase();
			try {
				switch(userInput){
				case "C": 
					System.out.printf(
							"Win chance: %.2f\n",tree.winProbability());
					break;
				default: tree.selectChild(tree.getCursorPosition()+
						"-"+userInput);
				}
			} catch (IllegalArgumentException | NodeNotPresentException e) {
				System.out.println(e.getMessage());
			}
			System.out.println(tree.getCursorMessage());
		}
		System.out.println("Thanks for playing.");
	}
	
	/**
	 * Edit the tree.
	 * This method is looped until exit is used.
	 */
	private static void edit(){
		tree.resetCursor();
		boolean finishEdit = false;
		while(!finishEdit){
			System.out.println(menu);
			switch(sc.nextLine().trim().toUpperCase()){
			case "V": 
				System.out.printf(
						"Position: %s\n Option: %s\n Message: %s\n",
						tree.getCursorPosition(), 
						tree.getCursorOption(), 
						tree.getCursorMessage()
						);
				break;
			case "S": 
				System.out.println("Please select a child: ");
				printOptions(tree);
				try {
					tree.selectChild(tree.getCursorPosition()+
							"-"+sc.nextLine().trim());
				}catch(IllegalArgumentException|NodeNotPresentException e){
					System.out.println(e.getMessage());
				}
				break;
			case "O": 
				System.out.println("Enter a new option: ");
				tree.setCursorOption(sc.nextLine());
				System.out.println("Option set");
				break;
			case "M": 
				System.out.println("Enter a new message: ");
				tree.setCursorMessage(sc.nextLine());
				System.out.println("Message set");
				break;
			case "A": 
				System.out.println("Enter a new option: ");
				String addOption = sc.nextLine();
				System.out.println("Enter a new message: ");
				String addMessage = sc.nextLine();
				try {
					tree.addChild(addOption, addMessage);
					System.out.println("Child added");
				} catch (IllegalArgumentException | TreeFullException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "D": 
				System.out.println("Please select a child: ");
				printOptions(tree);
				try {
					tree.removeChild(tree.getCursorPosition()+
							"-"+sc.nextLine().trim());
					System.out.println("Subtree removed");
				}catch(IllegalArgumentException|NodeNotPresentException e){
					System.out.println(e.getMessage());
				}
				break;
			case "R": 
				tree.resetCursor();
				System.out.println("Cursor reseted");
				break;
			case "Q": 
				finishEdit = true;
				break;
			default: System.out.println("Invalid menu option");
			}
		}
	}
	
}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * The StorageManager is the manager class that allows users to manipulate
 * the contents in the StorageTable.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class StorageManager {
	private static StorageTable storageTable = new StorageTable();
	private static Scanner inputSc = new Scanner(System.in);
	private static String saveName = "storage.obj";
	private static String menu = 
			"P - Print all storage boxes\n"+
			"A - Insert into storage box\n"+
			"R - Remove contents from a storage box\n"+
			"C - Select all boxes owned by a particular client\n"+
			"F - Find a box by ID and display its owner and contents\n"+
			"Q - Quit and save workspace\n"+
			"X - Quit and delete workspace\n";
	private static boolean run = true;
	
	public static void main(String[]args){
		if(new File(saveName).exists()){
			try {
				FileInputStream file = new FileInputStream(saveName);
				ObjectInputStream input = new ObjectInputStream(file);
				storageTable = (StorageTable)(input.readObject());
				input.close();
			} catch (IOException|ClassCastException|
					ClassNotFoundException e){
				System.out.println("Failed to read file");
			}
		}
		
		System.out.println("Welcome to Rocky Stream Storage Manager!");
		while(run){
			System.out.println(menu);
			switch(inputSc.nextLine().trim().toUpperCase()){
			case "P":
				System.out.println(storageTable.toString());
				break;
			case "A":
				try{
					System.out.println("Please enter id: ");
					int id = Integer.parseInt(inputSc.nextLine().trim());
					System.out.println("Please enter client: ");
					String client = inputSc.nextLine();
					System.out.println("Please Enter Contents: ");
					String content = inputSc.nextLine();
					
					Storage storage = new Storage(id, client, content);
					storageTable.putStorage(id, storage);
					System.out.printf("Storage %d set!\n", id);
				}catch(NumberFormatException e){
					System.out.println("Wrong input format "+e.getMessage());
				}catch(IllegalArgumentException e){
					System.out.println(e.getMessage());
				}
				break;
			case "R":
				try{
					System.out.println("Please enter ID: ");
					int id = Integer.parseInt(inputSc.nextLine().trim());
					storageTable.remove(id);
					System.out.printf("Box %d is now removed\n", id);
				}catch(NumberFormatException e){
					System.out.println("Wrong input format "+e.getMessage());
				}catch(NullPointerException e){
					System.out.println("ID does not exist");
				}
				break;
			case "C":
				System.out.println("Please enter the name of the client: ");
				try{
					System.out.println(
							storageTable.toString(inputSc.nextLine().trim()));
				}catch(IllegalArgumentException e){
					System.out.println(e.getMessage());
				}
				break;
			case "F":
				try{
					System.out.println("Please enter ID: ");
					int id = Integer.parseInt(inputSc.nextLine().trim());
					if(storageTable.getStorage(id) == null){
						System.out.println("ID does not exist");
						break;
					}
					
					Storage storage = storageTable.getStorage(id);
					System.out.printf("Box %d\n", storage.getId());
					System.out.printf("Contents: %s\n", storage.getContent());
					System.out.printf("Owner: %s\n", storage.getClient());
				}catch(NumberFormatException e){
					System.out.println("Wrong input format "+e.getMessage());
				}catch(IllegalArgumentException e){
					System.out.println(e.getMessage());
				}
				break;
			case "Q":
				try {
					FileOutputStream file = 
							new FileOutputStream(saveName);
					ObjectOutputStream output = new ObjectOutputStream(file);
					output.writeObject(storageTable);
					output.close();
					System.out.println("Data saved for next session");
				} catch (IOException e) {
					System.out.println("Data failed to save");;
				}
				run = false;
				System.out.println("Program exited");
				break;
			case "X":
				(new File(saveName)).delete();
				System.out.println("Data erased");
				run = false;
				System.out.println("Program exited");
				break;
			default: System.out.println("Invalid Option");
			}
			
		}
	}
}

import java.util.Scanner;

/**
 * The NeoViewer class is the driver class that lets the user manipulate
 * the data in NeoDataBase.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class NeoVIewer {
	private static Scanner sc = new Scanner(System.in);
	private static NeoDataBase dataBase = new NeoDataBase();
	private static String mainMenu = 
					"A) Add a page to the database\n"+
					"S) Sort the database\n"+
					"P) Print the database as a table\n"+
					"Q) Quit\n";
	private static String sortMenu =
					"R) Sort by referenceID\n"+
					"D) Sort by diameter\n"+
					"A) Sort by approach date\n"+
					"M) Sort by miss distance\n";
	
	/**
	 * Driver main method
	 * 
	 * @param args
	 * Optinal parameter that is not going to do anything.
	 */
	public static void main(String[]args){
		System.out.println("Welcome to DataBase!");
		boolean end = false;
		while(!end){
			System.out.println(mainMenu);
			switch(sc.nextLine().trim().toUpperCase()){
			case "A":
				addPage();
				break;
			case "S":
				sortOptions();
				break;
			case "P":
				dataBase.printTable();
				break;
			case "Q":
				end = true;
				System.out.println("Program ended safely");
				break;
			default: 
				System.out.println("Invalid Input");
			}
		}
	}
	
	/**
	 * Prompt user to enter page number, generate a query url from
	 * the page number and pass the url as parameter to addAll(String url)
	 * to add the data to database.
	 */
	public static void addPage(){
		try{
			System.out.println("Enter page number");
			int pageNumber = Integer.parseInt(sc.nextLine().trim());
			dataBase.addAll(dataBase.buildQueryURL(pageNumber));
		}catch(IllegalArgumentException e){
			System.out.println("Invalid. "+e.getMessage());
		}catch(Exception e){
			System.out.println("Page can't be read");
		}
	}
	
	/**
	 * Prompt the user to input sorting option and sort the database based
	 * on the option.
	 */
	public static void sortOptions(){
		System.out.println(sortMenu);
		switch(sc.nextLine().trim().toUpperCase()){
		case "R":
			dataBase.sort(new ReferenceIDComparator());
			System.out.println("Table sorted on ID.");
			break;
		case "D":
			dataBase.sort(new DiameterComparator());
			System.out.println("Table sorted on diameter.");
			break;
		case "A":
			dataBase.sort(new ApproachDateComparator());
			System.out.println("Table sorted on approach date.");
			break;
		case "M":
			dataBase.sort(new MissDistanceComparator());
			System.out.println("Table sorted on miss distance.");
			break;
		default: System.out.println("Invalid Input");
		}
	}
	
	
	
}

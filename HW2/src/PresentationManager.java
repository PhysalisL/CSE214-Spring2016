import java.util.Scanner;

/**
 * The manipulator class of SlideList
 * This class prints out a menu that can moddify the SlideList class
 * 
 * @author Yixiu Liu 110602460 
 * yixiu.liu@stonbybrook.edu 
 * CSE 214-R03 Daniel Scanteianu 
 *
 */
public class PresentationManager {
	
	/**
	 * Prints SlideList object in a formatted table
	 * 
	 * @param list
	 * The SlideList to be printed out with format
	 */
	private static void printSummary(SlideList list){
		int separatorLength = 46; //The length of line
		String symbol = "="; //The symbol used for line
		String content = ""; //Content builder
		Slide mark = list.getCursorSlide(); //For cursor reset
		list.resetCursorToHead();
		
		String lineSeparator = new String(new char[separatorLength])
				.replace("\u0000", symbol);
		
		String heading = String.format(
				"%-2s%-9s%-14s%-11s%-10s", 
				"", "Slide", "Title", "Duration", "Bullets");
		
		for(int i=1; i<=list.size(); i++){
			content += String.format(
					"%-2s%-9d%-14s%-11.1f%-10d\n", 
					list.getCursorSlide().equals(mark)?"->":"", 
					i, 
					list.getCursorSlide().getTitle(), 
					list.getCursorSlide().getDuration(), 
					list.getCursorSlide().getNumBullets());

			//Because cursorForward will run one extra time
			//before termination, need to use a check to prevent it
			if(i<list.size()){
				try {
					list.cursorForward();
				} catch (EndOfListException e) {
					System.out.println("Size or iteration error");
				}
			}
		}
		
		String total = String.format(
				"Total: %d slide(s), %.1f minutes(s), %d bullet(s)",
				list.size(), list.duration(), list.numBullets());
		
		System.out.println(
				lineSeparator+
				"\n"+heading+
				"\n"+lineSeparator+
				"\n"+content+
				"\n"+lineSeparator+
				"\n"+total+
				"\n"+lineSeparator
				);
		
		//restore cursor
		if(mark!=null){
			list.resetCursorToHead();
			while(!list.getCursorSlide().equals(mark)){
				try {
					list.cursorForward();
				} catch (EndOfListException e) {
					System.out.println("Logic Error: Size or Iteration");
				}
			}
		}
	}
	
	/**
	 * Run the manager program
	 * 
	 * @param args
	 */
	public static void main(String[]args){
		boolean run = true;
		String menu =  "F) Move cursor forward\n"+
					   "B) Move cursor backward\n"+
					   "D) Display cursor slide\n"+
					   "E) Edit cursor slide\n"+
					   "P) Print presentation summary\n"+
					   "A) Append new slide to tail\n"+
					   "I) Insert new slide before cursor\n"+
					   "R) Remove slide at cursor\n"+
					   "H) Reset cursor to head\n"+
					   "Q) Quit";
		SlideList list = new SlideList();
		Scanner sc = new Scanner(System.in);
		
		while(run){
			System.out.println(menu);
			
			try{
			String choice = sc.nextLine().trim().toUpperCase();
			
			switch(choice){
			
			case "F": 
				list.cursorForward(); 
				System.out.printf(
						"Cursor moved forward to slide \"%s\"\n",
						list.getCursorSlide().getTitle());
			break;
			
			case "B": 
				list.cursorBackward(); 
				System.out.printf(
						"Cursor moved backward to slide \"%s\"\n",
						list.getCursorSlide().getTitle());
			break;
			
			case "D":
				System.out.println(
						list.getCursorSlide()==null?
								"No slide at cursor":
									list.getCursorSlide().toString()
									);
			break;
			
			case "E":
				System.out.print(
						"Edit title, duration, or bullets? (t/d/b) ");
				
				switch(sc.nextLine().trim().toUpperCase()){
				
				case "T" :
					System.out.print("Enter title: ");
					list.getCursorSlide().setTitle(sc.nextLine());
					break;
					
				case "D" :
					System.out.print("Enter duration: ");
					list.getCursorSlide().setDuration(
							Double.parseDouble(sc.nextLine()));
					break;
					
				case "B" :
					System.out.print("Bullet index: ");
					int eIndex = Integer.parseInt(sc.nextLine().trim());
					
					System.out.print("Delete or edit? (d/e) ");
					String eChoice = sc.nextLine().trim().toUpperCase();
					
					switch(eChoice){
					case "D":
						list.getCursorSlide().setBullet(null, eIndex);
						break;
						
					case "E":
						System.out.printf("Bullet %d: ", eIndex);
						String eNewBullet = sc.nextLine().trim();
						list.getCursorSlide().setBullet(
								eNewBullet,
								eIndex);
						break;
						
					default:
						throw new 
						IllegalArgumentException("Invalid Choice");
					}
				}
			break;
			
			case "P": 
				printSummary(list);
			break;
			
			case "A": 
				boolean aKeepAdding = true;
				int aIndex = 1;
				Slide aSlide = new Slide();
				
				System.out.print("Enter the title: ");
				aSlide.setTitle(sc.nextLine().trim());
				
				System.out.print("Enter the duration: ");
				aSlide.setDuration(Double.parseDouble(sc.nextLine()));

				System.out.printf("Bullet %d: ", aIndex);
				aSlide.setBullet(sc.nextLine().trim(), aIndex++);
				
				while(aKeepAdding && aIndex<=Slide.MAX_BULLETS){
					System.out.print("Add another bullet? (y/n) ");
					String aChoice = sc.nextLine().trim().toUpperCase();
					
					if(aChoice.equals("Y")){
						System.out.printf("Bullet %d: ", aIndex);
						aSlide.setBullet(sc.nextLine().trim(), aIndex++);
					}
					else if(aChoice.equals("N"))
						aKeepAdding = false;
					else
						System.out.print("Y or N only please\n");
				}
				list.appendToTail(aSlide);
			break;
			
			case "I":
				boolean iKeepAdding = true;
				int iIndex = 1;
				Slide iSlide = new Slide();
				
				System.out.print("Enter the title: ");
				iSlide.setTitle(sc.nextLine().trim());
				
				System.out.print("Enter the duration: ");
				iSlide.setDuration(Double.parseDouble(sc.nextLine()));

				System.out.printf("Bullet %d: ", iIndex);
				iSlide.setBullet(sc.nextLine().trim(), iIndex++);
				
				while(iKeepAdding && iIndex<=Slide.MAX_BULLETS){
					System.out.print("Add another bullet? (y/n) ");
					String iChoice = sc.nextLine().trim().toUpperCase();
					
					if(iChoice.equals("Y")){
						System.out.printf("Bullet %d: ", iIndex);
						iSlide.setBullet(sc.nextLine().trim(), iIndex++);
					}
					else if(iChoice.equals("N"))
						iKeepAdding = false;
					else
						System.out.print("Y or N only please\n");
				}
				list.insertBeforeCursor(iSlide);
			break;
			
			case "R":
				System.out.printf(
						"Slide \"%s\" has been removed\n", 
						list.removeCursor().getTitle());
			break;
			
			case "H": 
				System.out.println("Cursor moved to head");
				list.resetCursorToHead(); 
			break;
			
			case "Q": 
				run = false;
			break;
			
			default: 
				System.out.println("Invalid choice");
			}
			
			}catch(NumberFormatException e){
				System.out.println("Invalid input format");
			}catch(NullPointerException e){
				System.out.println("The slide does not exist");
			}catch(IllegalArgumentException | EndOfListException e){
				System.out.println(e.getMessage());
			}
		}
	}
}

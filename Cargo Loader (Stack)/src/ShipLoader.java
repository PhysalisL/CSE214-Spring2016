import java.util.Scanner;
import java.util.Stack;

/**
 * Manager class that handles information
 * travel between Cargo and CargoShip
 *
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class ShipLoader {
    private static int totalStacks;
    private static int maxHeight;
    private static int maxWeight;
    private static double currentWeight = 0;
    private static boolean run = true;
    private static Scanner sc = new Scanner(System.in);
    private static CargoShip ship;
    private static Stack<Cargo> dock = new Stack<Cargo>();
    private static String menu =
            "C) Create new cargo\n" +
            "L) Load cargo from dock\n" +
            "U) Unload cargo from ship\n" +
            "M) Move cargo between stacks\n" +
            "K) Clear dock\n" +
            "P) Print ship stacks\n" +
            "S) Search for cargo\n" +
            "Q) Quit\n" +
            "Select a menu option: \n";

    /**
     * Runs the program
     *
     * @param args
     */
    public static void main(String[]args){
        generateShip();
        while(run) {
            System.out.print(menu);
            try{
                switch(sc.nextLine().trim().toUpperCase()){
                    case "C":
                        System.out.print("Enter the name of the cargo: ");
                        String cName = sc.nextLine().trim();
                        
                        System.out.print(
                        		"Enter the weight of the cargo: ");
                        double cWeight = Double.parseDouble(
                        		sc.nextLine().trim());
                        
                        System.out.print(
                        		"Enter the container strength (F/M/S): ");
                        String cInput = sc.nextLine().trim().toUpperCase();
                        createCargo(cName, cWeight, cInput);
                        break;
                    case "L":
                        System.out.print(
                        		"Select the load destination stack index: ");
                        int lStackPosition = 
                        		Integer.parseInt(sc.nextLine().trim());
                        loadCargo(lStackPosition);
                        break;
                    case "U":
                        System.out.println(
                        		"Select the unload source stack index: ");
                        int uStackPosition = 
                        		Integer.parseInt(sc.nextLine().trim());
                        unloadCargo(uStackPosition);
                        break;
                    case "M":
                        System.out.println("Source stack index: ");
                        int mFromIndex = 
                        		Integer.parseInt(sc.nextLine().trim());
                        
                        System.out.println("Destination stack index: ");
                        int mToIndex = 
                        		Integer.parseInt(sc.nextLine().trim());
                        moveCargo(mFromIndex, mToIndex);
                        break;
                    case "K":
                        clearDock();
                        break;
                    case "P":
                        displayState();
                        break;
                    case "S":
                        System.out.println("Enter the name of the cargo: ");
                        String sName = sc.nextLine().trim();
                        searchAndPrint(sName);
                        break;
                    case "Q":
                        quit();
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            } catch (CargoStrengthException|EmptyStackException e){
                System.out.println("Operation failed! "+e.getMessage());
            } catch (IllegalArgumentException e){
                System.out.println("Invalid input: "+e.getMessage());
            }
        }
    }

    /**
     * Creates CargoShip Object with user parameters.
     * This method prompts user to enter inputs until parameters
     * satisfy the data types.
     */
    private static void generateShip(){
        boolean acceptable = false;
        System.out.println("Welcome to ShipLoader!");
        do {
            try {
                System.out.println("Cargo Ship Parameters");
                System.out.println("Number of stacks: ");
                totalStacks = Integer.parseInt(sc.nextLine().trim());
                
                System.out.println("Max height of stacks: ");
                maxHeight = Integer.parseInt(sc.nextLine().trim());
                
                System.out.println("Max total cargo weight: ");
                maxWeight = Integer.parseInt(sc.nextLine().trim());

                acceptable = true;
                System.out.println("Cargo ship created.\n" +
                                "Pulling ship in to dock...\n" +
                                "Cargo ship ready to be loaded.\n");
                ship = new CargoShip(totalStacks, maxHeight, maxWeight);
            }catch(NumberFormatException e){
                System.out.println("Invalid parameters, try again\n");
            }
        }while(!acceptable);
    }

    /**
     * Creates Cargo Object with user parameters
     * and place it onto the dock.
     * If an exception occurred, state remain unchanged.
     *
     * @throws CargoStrengthException
     * Indicates the new Cargo Object cannot be placed on
     * dock due to strength incompatibility
     */
    private static void createCargo(String name, double weight, 
    		String strengthChar)throws CargoStrengthException{
        CargoStrength cStrength =
                strengthChar.equals("F") ? CargoStrength.FRAGILE :
                strengthChar.equals("M") ? CargoStrength.MODERATE :
                strengthChar.equals("S") ? CargoStrength.STURDY : null;
        if(cStrength==null)
            throw new IllegalArgumentException("Invalid Strength Type");

        Cargo cNewCargo = new Cargo(name, weight, cStrength);
        if(!dock.isEmpty()) {
            CargoStrength dockStrength = dock.peek().getStrength();
            if(dockStrength == CargoStrength.FRAGILE)
                if(cNewCargo.getStrength()!= CargoStrength.FRAGILE)
                    throw new CargoStrengthException();
            if(dockStrength == CargoStrength.MODERATE)
                if(cNewCargo.getStrength()== CargoStrength.STURDY)
                    throw new CargoStrengthException();

        }
        dock.push(cNewCargo);
        displayState();
    }

    /**
     * Pops the cargo from dock onto the indicated stack.
     * If an exception occurred, state remain unchanged.
     *
     * @throws EmptyStackException
     * Indicates the dock is empty
     */
    private static void loadCargo(int stackPosition) 
    		throws EmptyStackException{
        if(dock.isEmpty())
            throw new EmptyStackException();
        String lName = dock.peek().getName();
        Cargo lTempCargo = dock.pop();
        try {
            ship.pushCargo(lTempCargo, stackPosition);
            currentWeight += lTempCargo.getWeight();
            System.out.println("Cargo " +lName +
                            " moved from dock to stack "+ stackPosition);
            displayState();
        }catch (IllegalArgumentException|FullStackException|
                ShipOverweightException| CargoStrengthException e){
            dock.push(lTempCargo);
            System.out.println("Operation failed! "+e.getMessage());
        }
    }

    /**
     * Pops the cargo from indicated stack onto another indicated stack.
     * If an exception occurred, state remain unchanged.
     *
     * @throws EmptyStackException
     * Indicates the stack index to pop from is empty
     */
    private static void moveCargo(int from, int to) 
    		throws EmptyStackException{
        Cargo mTempCargo = ship.popCargo(from);
        try {
            ship.pushCargo(mTempCargo, to);
            System.out.println("Cargo moved from stack "+from+
                            " to stack "+to);
            displayState();
        }catch (IllegalArgumentException|FullStackException|
                CargoStrengthException|ShipOverweightException e){
            System.out.println("Operation failed! "+e.getMessage());
            try {
                ship.pushCargo(mTempCargo, from);
            }catch (FullStackException|CargoStrengthException|
                    ShipOverweightException e1){
                System.out.println(
                        "LOGIC ERROR: COULD NOT REVERSE POP OPERATION");
                e1.printStackTrace();
            }
        }
    }

    /**
     * Pops a cargo off from the ship at the indicated index
     * and pushes onto the dock.
     * If an exception occurred, state remain unchanged.
     *
     * @throws EmptyStackException
     * Indicate selected index is empty
     * @throws CargoStrengthException
     * Indicate dock cargo cannot support the strength of the ship Cargo
     */
    private static void unloadCargo(int stackPosition)
            throws EmptyStackException, CargoStrengthException{
        if(isShipIndexEmpty(ship, stackPosition))
            throw new EmptyStackException();

        Cargo uTempCargo = ship.popCargo(stackPosition);
        if(!dock.isEmpty()) {
            CargoStrength dockStrength = dock.peek().getStrength();
            if(dockStrength == CargoStrength.FRAGILE){
                if(uTempCargo.getStrength()!= CargoStrength.FRAGILE) {
                    try {
                        ship.pushCargo(uTempCargo, stackPosition);
                    }catch (CargoStrengthException|ShipOverweightException|
                            FullStackException e){
                        System.out.println("LOGIC ERROR");
                        e.printStackTrace();
                    }
                    throw new CargoStrengthException();
                }
            }
            if(dockStrength == CargoStrength.MODERATE){
                if(uTempCargo.getStrength()== CargoStrength.STURDY) {
                    try {
                        ship.pushCargo(uTempCargo, stackPosition);
                    }catch (CargoStrengthException|
                            ShipOverweightException|
                            FullStackException e){
                        System.out.println("LOGIC ERROR");
                        e.printStackTrace();
                    }
                    throw new CargoStrengthException();
                }
            }
        }
        dock.push(uTempCargo);
        currentWeight -= uTempCargo.getWeight();
        System.out.println(
                "Cargo moved from stack "+stackPosition+" to dock");
        displayState();
    }

    /**
     * Clears the dock
     */
    private static void clearDock(){
        while(!dock.isEmpty()) 
        	dock.pop();
        System.out.println("Dock cleared");
        displayState();
    }

    /**
     * Read input name and calls the search and print method in
     * CargoShip using the given name as parameter.
     */
    private static void searchAndPrint(String name){
        ship.findAndPrint(name);
    }

    /**
     * Exits the main loop
     */
    private static void quit(){
        run = false;
        System.out.println("Program terminating normally...");
    }

    /**
     * Check whether or not the indicated stack in the ship is empty.
     * This is used because CargoShip has no public isEmpty() method.
     *
     * @param ship
     * The CargoShip object
     * @param stackPosition
     * The stack to check
     * @return
     * Is the given stack on the ship empty?
     */
    private static boolean isShipIndexEmpty(CargoShip ship,
                                            int stackPosition){
        Cargo temp;
        try{
            temp = ship.popCargo(stackPosition);
            ship.pushCargo(temp, stackPosition);
        }catch(EmptyStackException e){
            return true;
        }catch(ShipOverweightException|FullStackException|
        		CargoStrengthException e){
            System.out.println("LOGIC ERROR: COULD NOT REVERSE POP");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Prints the cargoes, ship and dock
     */
    private static void displayState(){
        Stack<Cargo>[] temps = new Stack[totalStacks];
        for(int i=0; i<totalStacks; i++)
            temps[i] = new Stack<Cargo>();
        System.out.print("\n<bottom-----top>\n/==");
        for(int i=0; i<totalStacks; i++){
            System.out.print("\n"+(int)(i+1)+"||");
            while(!isShipIndexEmpty(ship, i+1)){
                try {
                    temps[i].push(ship.popCargo(i + 1));
                }catch (EmptyStackException e){
                    e.printStackTrace();
                }
            }
            while(!temps[i].isEmpty()){
                try {
                    System.out.print(
                            temps[i].peek().getStrength().name().charAt(0));
                    ship.pushCargo(temps[i].pop(), i + 1);
                }catch (FullStackException |ShipOverweightException |
                        CargoStrengthException e){
                    System.out.println("LOGIC ERROR");
                    e.printStackTrace();
                }
            }
            if(i<totalStacks-1)
            	System.out.print("\n===");
            else
            	System.out.print("\n\\==");
        }

        System.out.print("\n======\nDOCK||");
        Stack<Cargo>tempDock = new Stack<Cargo>();
        while(!dock.isEmpty())
            tempDock.push(dock.pop());
        while(!tempDock.isEmpty()){
            System.out.print(tempDock.peek().getStrength().name().charAt(0));
            dock.push(tempDock.pop());
        }
        System.out.print("\n======\n");
        System.out.print(
        		"Total Weight = "+currentWeight+"/"+maxWeight+"\n\n");
    }
}

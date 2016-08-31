import java.util.Stack;

/**
 * The CargoShip Class contains array of Stacks of generic type Cargo.
 * CargoShip abides to the CargoStrength rules and has
 * max height, max weight and finite number of stacks,
 * all of which have to be initialized at the instantiation.
 * CargoShip manipulates Cargoes while abiding to those rules
 * and will throw exception at any attempts to break them.
 *
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class CargoShip {
    private Stack<Cargo>[] stacks;
    private int numStacks;
    private int maxHeight;
    private double maxWeight;
    private int totalWeight = 0; //keep track of weight

    /**
     * Default Constructor
     *
     * @param numStacks
     * The number of stacks this ship can support
     * @param initMaxHeight
     * The maximum height of any stack on this ship
     * @param initMaxWeight
     * The maximum weight for all the cargo on the ship
     *
     * <dt>Preconditions:
     * <dd>numStacks > 0
     * <dd>initMaxHeight > 0
     * <dd>maxWeight > 0
     *
     * <dt>Postconditions:
     * <dd>CargoShip object has been initialized
     * with the indicated number of stacks, maxHeight, and maxWeight.
     *
     * @throws IllegalArgumentException
     * Indicate given parameters are out of range (<=0)
     */
    public CargoShip(int numStacks, int initMaxHeight, int initMaxWeight)
            throws IllegalArgumentException{

        if( !(numStacks>0 && initMaxHeight>0 && initMaxWeight>0) )
            throw new IllegalArgumentException(
                    "Invalid CargoShip properties"
            );

        stacks = new Stack[numStacks];
        for(int i=0; i<stacks.length; i++)
            stacks[i] = new Stack<Cargo>();

        this.numStacks = numStacks;
        maxHeight = initMaxHeight;
        maxWeight = initMaxWeight;
    }

    /**
     * Push cargo onto the given stack on the ship
     *
     * @param cargo
     * cargo to be place on stack
     * @param stack
     * Position of the stack, leftmost is 1
     *
     * <dt>Preconditions:
     * <dd>CargoShip initialized
     * <dd>Cargo not null
     * <dd>1<=stack<=stacks.length
     * <dd>Size of stack is less than maxHeight
     * <dd>Total weight of all Cargo plus new Cargo is less than maxWeight
     *
     * <dt>Postconditions:
     * <dd>Cargo successfully pushed, or exception thrown without changing
     * the state of the CargoShip
     *
     * @throws IllegalArgumentException
     * Indicate cargo is null or stack is not in bound
     * @throws FullStackException
     * Indicate max height reached
     * @throws ShipOverweightException
     * Indicate max weight reached
     * @throws CargoStrengthException
     * Indicate violation of strength rules
     */
    public void pushCargo(Cargo cargo, int stack)
            throws IllegalArgumentException, FullStackException,
            ShipOverweightException, CargoStrengthException{
        int stackIndex = stack - 1;

        //check exceptions
        if(cargo == null || stackIndex<0 || stackIndex>stacks.length-1)
            throw new IllegalArgumentException("No such stack");
        else if(stacks[stackIndex].size() >= maxHeight)
            throw new FullStackException();
        else if(cargo.getWeight()+totalWeight > maxWeight)
            throw new ShipOverweightException();
        else if(!stacks[stackIndex].isEmpty()) {
            CargoStrength topStrength =
                    stacks[stackIndex].peek().getStrength();
            if (topStrength == CargoStrength.FRAGILE)
                if (cargo.getStrength() != CargoStrength.FRAGILE)
                    throw new CargoStrengthException();
            if (topStrength == CargoStrength.MODERATE)
                if (cargo.getStrength() == CargoStrength.STURDY)
                    throw new CargoStrengthException();
        }

        stacks[stackIndex].push(cargo);
        totalWeight += cargo.getWeight();
    }

    /**
     * Pops a cargo from one of the specified stack
     *
     * @param stack
     * Position of the stack with leftmost being 1
     *
     * <dt>Preconditions:
     * <dd>CargoShip initialized
     * <dd>1<=stack<=stacks.length
     *
     * <dt>Postconditions:
     * <dd>Cargo successfully popped,
     * or exception thrown without changing the state of the CargoShip
     *
     * @return
     * Cargo on top of the indicated stack
     *
     * @throws IllegalArgumentException
     * Indicate stack position out of bounds
     * @throws EmptyStackException
     * Indicate stack is empty
     */
    public Cargo popCargo(int stack)
            throws IllegalArgumentException, EmptyStackException{
        int stackIndex = stack-1;

        //Check exceptions
        if(stackIndex<0 || stackIndex>stacks.length-1)
            throw new IllegalArgumentException("Index out of bounds");
        else if(stacks[stackIndex].isEmpty())
            throw new EmptyStackException();

        totalWeight -= stacks[stackIndex].peek().getWeight();
        return stacks[stackIndex].pop();
    }

    /**
     * Search the cargo with given name
     * and print all the Cargoes with the same name
     * along with each Cargo's
     * stack position, depth, weight and strength.
     * If not found, will print not found.
     *
     * @param name
     * Name of the cargo to be searched and printed
     */
    public void findAndPrint(String name){
        int totalCount = 0;
        double totalWeight = 0;
        String heading =
                " Stack   Depth   Weight   Strength \n"+
                "=======+=======+========+==========\n";
        String content = "";

        for(int i=0; i<stacks.length; i++){
            int depth = 0; //depth at each stack
            Stack<Cargo>temp = new Stack<Cargo>();
            while(!stacks[i].isEmpty()){
                Cargo top = stacks[i].peek();
                if(top.getName().equals(name)){
                    content += String.format(
                            "%-7d|%-7d|%-8.1f|%-10s\n",
                            i+1,
                            depth,
                            top.getWeight(),
                            top.getStrength().name());
                    totalCount++;
                    totalWeight += top.getWeight();
                }
                temp.push(stacks[i].pop());
                depth++;
                System.out.println("Emptying stack: "+i);
            }
            while(!temp.isEmpty()){
                stacks[i].push(temp.pop());
                System.out.println("Restoring stack: "+i);
            }
        }
        if(totalCount==0)
           System.out.print(
                   "Cargo \""+name+"\" could not be found on the ship\n");
        else{
            System.out.print(
                    "\""+name+"\" found at the following locations: \n"+
                    heading+content+
                    "Total Count: "+ totalCount+"\n"+
                    "Total Weight: "+ totalWeight+"\n");
        }
    }
}

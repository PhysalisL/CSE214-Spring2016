/**
 * The Cargo class contains data on
 * name, weight and strength
 *
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class Cargo {
    private String name;
    private double weight;
    private CargoStrength strength;

    /**
     *Default Constructor of Cargo Object
     *
     * @param initName
     * Name of the cargo
     * @param initWeight
     * Weight of the cargo
     * 0 < weight
     * @param initStrength
     * Strength of the cargo
     *
     * <dt>Preconditions:
     * <dd>initName not null
     * <dd>initWeight > 0
     *
     * <dt>Postconditions:
     * <dd>Cargo object initialized with given name, weight, and strength.
     *
     * @throws IllegalArgumentException
     * Indicate given name is null or given weight not greater than 0
     */
    public Cargo(String initName, double initWeight, 
    		CargoStrength initStrength) throws IllegalArgumentException{

        if(initName == null || initWeight <= 0)
            throw new IllegalArgumentException("Invalid cargo properties");
        name = initName;
        weight = initWeight;
        strength = initStrength;
    }

    /**
     * @return
     * name of the cargo
     */
    public String getName(){
        return name;
    }

    /**
     * @return
     *weight of the cargo
     */
    public double getWeight(){
        return weight;
    }

    /**
     * @return
     *strength of the cargo
     */
    public CargoStrength getStrength(){
        return strength;
    }
}

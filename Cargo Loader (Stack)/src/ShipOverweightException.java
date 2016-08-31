/**
 * Should be thrown if the user attempts to push
 * a Cargo object on to any stack of the CargoShip
 * that would result in exceeding max weight.
 *
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class ShipOverweightException extends Exception{
    public ShipOverweightException(){
        super("Weight exceeding max weight allowed!");
    }
}

/**
 * Should be thrown if the user attempts to push
 * a Cargo object on to any stack of the CargoShip
 * that would result in exceeding max height.
 *
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class FullStackException extends Exception{
    public FullStackException(){
        super("Max height reached!");
    }
}

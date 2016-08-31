/**
 * Should be thrown if the user attempts to pop
 * from the CargoShip when there is no Cargo object
 * on it
 *
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class EmptyStackException extends Exception{
    public EmptyStackException(){
        super("No cargo to pop!");
    }
}

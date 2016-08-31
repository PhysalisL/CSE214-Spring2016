/**
 * Should be thrown if the user attempts to push a
 * Cargo object onto another Cargo object that
 * violates the CargoStrength rule:
 * FRAGILE can only have FRAGILE on top,
 * MODERATE can only both FRAGILE and MODERATE top
 * STURDY can support all on top
 *
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class CargoStrengthException extends Exception{
    public CargoStrengthException(){
        super("Cargo at top of stack cannot support weight!");
    }
}

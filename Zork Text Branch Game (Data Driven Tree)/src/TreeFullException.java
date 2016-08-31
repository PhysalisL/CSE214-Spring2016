/**
 * Should be thrown if all nodes of the tree are occupied.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 *
 */
public class TreeFullException extends Exception{
	public TreeFullException(){
		super("All child nodes are occupied");
	}
}

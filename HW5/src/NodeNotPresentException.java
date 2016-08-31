/**
 * Should be thrown if node cannot be found.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 *
 */
public class NodeNotPresentException extends Exception{
	public NodeNotPresentException(String message){
		super(message);
	}
}

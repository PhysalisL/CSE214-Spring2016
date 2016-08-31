/**
 * An Exception class 
 * indicates that the user attempted
 * to access a SlideListNode that does not exist
 * (either before the head node or after the tail node). 
 * This exception can also be thrown 
 * when an operation is performed on an empty list 
 * (i.e. head, tail, and cursor are all equal to null). 
 * 
 * @author Yixiu Liu 110602460 
 * yixiu.liu@stonbybrook.edu 
 * CSE 214-R03 Daniel Scanteianu 
 */
public class EndOfListException extends Exception{
	public EndOfListException(){
		super("End of List");
	}
}

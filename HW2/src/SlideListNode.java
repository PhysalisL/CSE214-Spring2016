/**
 * Wrapper class of Slide. 
 * This class provides methods to link to other Slide wrappers
 * 
 * @author Yixiu Liu 110602460 
 * yixiu.liu@stonbybrook.edu 
 * CSE 214-R03 Daniel Scanteianu 
 *
 */
public class SlideListNode {
	private Slide data;
	private SlideListNode next;
	private SlideListNode prev;
	
	/**
	 * Default Constructor 
	 * @param initData
	 * The data to be wrapped by this SlideListNode. 
	 * This parameter should not be null.
	 * <dt>Preconditions:
	 * <dd>initData Slide is not null
	 * <dt>Postconditions:
	 * <dd> SlideListNode has been initialized to wrap the Slide,
	 * and prev and next have been set to null
	 * @throws IllegalArgumentException 
	 * Indicate initData Slide is null
	 */
	public SlideListNode(Slide initData) 
			throws IllegalArgumentException {
		if(initData==null)
			throw new IllegalArgumentException("Slide is null");
		
		data = initData;
		next = null;
		prev = null;
	}
	
	/**
	 * Gets the referene to the data variable of the list node
	 * @return
	 * The reference of the data variable.
	 */
	public Slide getData(){
		return data;
	}
	
	/**
	 * Updates the data variable with a new Slide reference.
	 * @param newData
	 * New Slide reference to update to.
	 * <dt>Preconditions:
	 * <dd>newData is not null
	 * @throws IllegalArgumentException 
	 * Indicate newData is null
	 */
	public void setData(Slide newData) throws IllegalArgumentException {
		if(newData==null)
			throw new IllegalArgumentException("Slide is null");
		
		data = newData;
	}
	
	/**
	 * Gets the reference to the next member variable of the list node.
	 * @return
	 * The reference of the next member variable. 
	 * Null means that there is no next SlideListNode in the list.
	 */
	public SlideListNode getNext(){
		return next;
	}
	
	/**
	 * Updates the next member variable 
	 * with a new SlideListNode reference.
	 * @param newNext
	 * Reference to a new SlideListNode object 
	 * to update the next variable to.
	 * This parameter may be null, 
	 * this means we’ve reached the tail of the list.
	 */
	public void setNext(SlideListNode newNext){
		next = newNext;
	}
	
	/**
	 * Gets the reference to the prev member variable of the list node.
	 * @return
	 * The reference of the prev member variable. 
	 * Null means that there is no prev SlideListNode in the list.
	 */
	public SlideListNode getPrev(){
		return prev;
	}
	
	/**
	 * Updates the prev member variable 
	 * with a new SlideListNode reference.
	 * @param newPrev
	 * Reference to a new SlideListNode object 
	 * to update the prev variable to.
	 * This parameter may be null, 
	 * this means we’ve reached the head of the list.
	 */
	public void setPrev(SlideListNode newPrev){
		prev = newPrev;
	}
	
}

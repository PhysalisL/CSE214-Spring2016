/**
 * The SlideList Class serves to link and sever SlideListNodes
 * 
 * @author Yixiu Liu 110602460 
 * yixiu.liu@stonbybrook.edu 
 * CSE 214-R03 Daniel Scanteianu 
 */
public class SlideList {
	private SlideListNode head;
	private SlideListNode tail;
	private SlideListNode cursor;
	private int size;
	
	/**
	 * Default constructor
	 * Initializes this object to an empty list of Slides.
	 * 
	 * <dt>Postconditions:
	 * <dd>SlideList has been initialized
	 * with head, tail, and cursor all set to null.
	 */
	public SlideList(){
		head = null;
		tail = null;
		cursor = null;
		size = 0;
	}
	
	/**
	 * Returns the total number of Slides in the slideshow.
	 * 
	 * @return
	 * The count of all Slides in the slideshow.
	 */
	public int size(){
		return size;
	}
	
	/**
	 * Returns the total duration of Slides in the slideshow.
	 * 
	 * @return
	 * The total duration of all Slides in the slideshow.
	 */
	public double duration(){
		double total = 0;
		SlideListNode iter = head;
		while(iter!=null){
			total += iter.getData().getDuration();
			iter = iter.getNext();
		}
		return total;
	}
	
	/**
	 * Returns the total number of bullets of Slides in the slideshow.
	 * 
	 * @return
	 * The total number of bullets of all Slides in the slideshow.
	 */
	public int numBullets(){
		int total = 0;
		SlideListNode iter = head;
		while(iter!=null){
			total += iter.getData().getNumBullets();
			iter = iter.getNext();
		}
		return total;
	}

	/**
	 * Gets the reference to the Slide wrapped by the SlideListNode
	 * currently referenced by cursor.
	 * 
	 * @return
	 * The reference of the Slide wrapped by the SlideListNode 
	 * currently referenced by cursor. 
	 * If the cursor is null, then this method should return null as well
	 */
	public Slide getCursorSlide(){
		return cursor == null ? null : cursor.getData();
	}
	
	/**
	 * Returns the cursor to the start of the list.
	 * 
	 * <dt>Postconditions:
	 * <dd> If head is not null, 
	 * the cursor now references the first SlideListNode.
	 * If head is null, 
	 * the cursor is set to null as well,
	 * meaning there are no Slides in this list.
	 */
	public void resetCursorToHead(){
		cursor = head;
	}
	
	/**
	 * Moves cursor to select the next SlideListNode
	 * If the cursor is at the tail of the list, 
	 * this method throws an exception
	 * 
	 * @throws EndOfListException
	 * Indicate cursor is at the tail of the list
	 */
	public void cursorForward() throws EndOfListException{
		if(cursor.getNext()==null)
			throw new EndOfListException();
		
		cursor = cursor.getNext();
	}
	
	/**
	 * Moves cursor to select the prev SlideListNode
	 * If the cursor is at the head of the list, 
	 * this method throws an exception
	 * 
	 * @throws EndOfListException
	 * Indicate cursor is at the head of the list
	 */
	public void cursorBackward() throws EndOfListException{
		if(cursor.getPrev()==null)
			throw new EndOfListException();
		
		cursor = cursor.getPrev();
	}
	
	/**
	 * Inserts the indicated Slide before the cursor.
	 * 
	 * @param newSlide
	 * The Slide object to be wrapped and inserted 
	 * into the list before the cursor.
	 * 
	 * <dt>Preconditions:
	 * <dd>newSlide is not null.
	 * 
	 * <dt>Postconditions:
	 * <dd> newSlide has been wrapped in a new SlideListNode object
	 * <dd> If cursor was previously not null, 
	 * the newly created SlideListNode has been 
	 * inserted into the list before the cursor.
	 * <dd> If cursor was previously null, 
	 * the newly created SlideListNode has been 
	 * set as the new head of the list (as well as the tail).
	 * <dd> The cursor now references the newly created SlideListNode.
	 * 
	 * @throws IllegalArgumentException
	 * Indicate null newSlide
	 */
	public void insertBeforeCursor(Slide newSlide) 
			throws IllegalArgumentException{
		if(newSlide==null)
			throw new IllegalArgumentException("Slide is null");

		SlideListNode newNode = new SlideListNode(newSlide);
		
		//All null case
		//initialization
		if(cursor==null){
			head = newNode;
			tail = newNode;
		}
		
		//Head case
		//only one double link to set
		else if(cursor.equals(head)){
			newNode.setNext(cursor);
			cursor.setPrev(newNode);
			head = newNode;
		}
		
		//Middle case
		//two double links to set
		else{
			cursor.getPrev().setNext(newNode);
			newNode.setPrev(cursor.getPrev());
			newNode.setNext(cursor);
			cursor.setPrev(newNode);
		}
		cursor = newNode;
		size++;
	}
	
	/**
	 * Inserts the indicated Slide after the tail of the list.
	 * 
	 * @param newSlide
	 * The Slide object to be wrapped and inserted 
	 * into the list after the tail of the list.
	 * 
	 * <dt>Preconditions:
	 * <dd>newSlide is not null.
	 * 
	 * <dt>Postconditions:
	 * <dd> newSlide has been wrapped in a new SlideListNode object
	 * <dd> If tail was previously not null, 
	 * the newly created SlideListNode has been 
	 * inserted into the list after the tail.
	 * <dd> If tail was previously null, 
	 * the newly created SlideListNode has been 
	 * set as the new head of the list (as well as the tail).
	 * <dd> The tail now references the newly created SlideListNode.
	 * 
	 * @throws IllegalArgumentException
	 * Indicate null newSlide
	 */
	public void appendToTail(Slide newSlide)
			throws IllegalArgumentException{
		if(newSlide==null)
			throw new IllegalArgumentException("Slide is null");
		
		SlideListNode newNode = new SlideListNode(newSlide);
		
		//initialization
		if(cursor == null){
			head = newNode;
			tail = newNode;
			cursor = newNode;
		}
		
		//post initialization
		else{
			tail.setNext(newNode);
			newNode.setPrev(tail);
		}
		tail = newNode;
		size++;
	}
	
	/**
	 * Removes the SlideListNode referenced by 
	 * cursor and returns the Slide inside.
	 * 
	 * <dt>Preconditions:
	 * <dd>cursor is not null.
	 * 
	 * <dt>Postconditions:
	 * <dd>The SlideListNode referenced by cursor 
	 * has been removed from the list.
	 * <dd> All other SlideListNodes in the list 
	 * exist in the same order as before.
	 * <dd> The cursor now references the previous SlideListNode 
	 * (or the head, if the cursor previously 
	 * referenced the head of the list).
	 * 
	 * @return
	 * The reference to the Slide contained within the 
	 * SlideListNode which was just removed from the list.
	 * 
	 * @throws IllegalArgumentException
	 * Indicate null newSlide
	 */
	public Slide removeCursor() throws EndOfListException{
		if(cursor==null)
			throw new EndOfListException();
		
		Slide returnSlide = cursor.getData();
		
		//one slide left case
		if(tail.equals(head)){
			tail = null;
			head = null;
			cursor = null;
		}
		//head case
		else if(cursor.equals(head)){
			head = head.getNext();
			cursor.setNext(null);
			cursor = head;
			cursor.setPrev(null);
		}
		//tail case
		else if(cursor.equals(tail)){
			tail = tail.getPrev();
			cursor.setPrev(null);
			cursor = tail;
			cursor.setNext(null);
		}
		//middle case
		else{
			cursor.getPrev().setNext(cursor.getNext());
			cursor.getNext().setPrev(cursor.getPrev());
			cursor = cursor.getPrev();
		}
		size--;
		return returnSlide;
	}
}

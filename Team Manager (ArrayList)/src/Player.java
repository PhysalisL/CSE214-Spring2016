/**
 * The Player Class stores information: name, hits and errors
 * 
 * @author Yixiu Liu
 *
 */
public class Player {
	private String name;
	private int hits;
	private int errors;
	
	/**
	 * Construct instance of Player class
	 * 
	 * <dt>Postcondition:
	 *    <dd>Player instantiated to 0 hits 0 errors and NoName name
	 */
	public Player(){
		this("NoName", 0, 0);
	}
	
	/**
	 * Construct instance of Player class with specified name,
	 *     hits, and errors
	 * 
	 * @param name
	 *    name of Player
	 * @param hits
	 *    number of hits
	 * @param errors
	 *    number of errors
	 * 
	 * <dt>Postcondition:
	 *    <dd>Player instantiated to 0 hits 0 errors and NoName name
	 */
	public Player(String name, int hits, int errors){
		setName(name);
		setHits(hits);
		setErrors(errors);
	}
	
	/**
	 * 
	 * @param name
	 *    Player name to change to
	 *    
	 * <dt>Postcondition:
	 *    <dd>Player name changed
	 *    
	 * @throws IllegalArgumentException
	 */
	public void setName(String name) throws IllegalArgumentException{
		name = name.trim();
		
		if(name.length()>=25)
			throw new IllegalArgumentException("Name too long");
		
		this.name = name;
	}

	/**
	 * Set the number of errors of this Player
	 * 
	 * @param hits
	 *    Number of hits to change to
	 * 
	 * <dt>Postcondition:
	 *    <dd>Number of hits changed
	 * 
	 * @throws IllegalArgumentException
	 *    Indicate his is in invalid range
	 */
	public void setHits(int hits) throws IllegalArgumentException{
		if(hits<0)
			throw new IllegalArgumentException("Invalid Number of Hits");
		this.hits = hits;
	}
	
	/**
	 * Set the number of errors of this Player
	 * 
	 * @param errors
	 *    Number of errors to change to
	 * 
	 * <dt>Precondition:
	 *    <dd> Player Object has been instantiated
	 *    
	 * <dt>Postcondition:
	 *    <dd>Number of errors changed
	 * 
	 * @throws IllegalArgumentException
	 *    Indicate errors number is in invalid range
	 */
	public void setErrors(int errors) throws IllegalArgumentException{
		if(errors<0)
			throw new IllegalArgumentException("Invalid Number of Errors");
		this.errors = errors;
	}
	
	/**
	 * Return name of this Player
	 * 
	 * <dt>Precondition:
	 *    <dd> Player Object has been instantiated
	 *    
	 * @return
	 *    The name of this Player
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Return number of errors of this Player
	 * 
	 * <dt>Precondition:
	 *    <dd> Player Object has been instantiated
	 * 
	 * @return
	 *    The number of hits of this Player
	 */
	public int getHits(){
		return hits;
	}

	/**
	 * Return number of errors of this Player
	 * 
	 * <dt>Precondition:
	 *    <dd> Player Object has been instantiated
	 * 
	 * @return
	 *    The number of errors of this Player
	 */
	public int getErrors(){
		return errors;
	}

	/**
	 * Returns formatted description of the Player
	 * 
	 * <dt>Precondition:
	 *    <dd> Player Object has been instantiated
	 *    
	 * @return
	 *    Formatted description of Player
	 */
	public String toString(){
		return String.format("%s - %d hits, %d errors", name, hits, errors);
	}
	
	/**
	 * Compare this Player to another object for equality
	 * 
	 * @param other
	 *    Object that this Player is compared to
	 * 
	 * <dt>Precondition:
	 *    <dd>Player has been initialized
	 *    
	 * @return
	 *    True if <code>other</code> refers to a Player object
	 *    and have same String representation as this Player
	 */
	public boolean equals(Object other){
		boolean isEqual;
		if(other instanceof Player)
			isEqual = toString().toLowerCase().equals(
					other.toString().toLowerCase()
					);
		else
			isEqual = false;
		return isEqual;
	}
	
}

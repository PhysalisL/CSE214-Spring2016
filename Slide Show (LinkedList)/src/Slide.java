/**
 * The Slide class stores
 * String called title
 * String Array called bullets, initalized to 5 capacity
 * double called duration
 * 
 * @author Yixiu Liu 110602460 
 * yixiu.liu@stonbybrook.edu 
 * CSE 214-R03 Daniel Scanteianu 
 *
 */
public class Slide {
	public static final int MAX_BULLETS = 5;
	private String title;
	private String[] bullets;
	private double duration;
	
	/**
	 * Default constructor
	 * 
	 * <dt> Posconditions:
	 * <dd>Object initialized to empty slide
	 * title and bullets null and duration 0
	 */
	public Slide(){
		title = null;
		bullets = new String[MAX_BULLETS];
		duration = 0;
	}
	
	/**
	 * Getter for title
	 * 
	 * @return
	 * The title of the Slide.
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * Setter or title variable
	 * 
	 * @param newTitle
	 * The new title of this slide
	 * 
	 * <dt>Preconditions:
	 * <dd>newTitle is not null
	 * 
	 * @throws IllegalArgumentException
	 * Indicates newTitle is null
	 */
	public void setTitle(String newTitle) 
			throws IllegalArgumentException{
		if(newTitle == null)
			throw new IllegalArgumentException("Title is null");
		
		title = newTitle;
	}
	
	/**
	 * Getter for duration
	 * 
	 * @return
	 * The duration of the Slide.
	 */
	public double getDuration(){
		return duration;
	}
	
	/**
	 * Setter or duration variable
	 * 
	 * @param newDuration
	 * The new duration of this slide
	 * 
	 * <dt>Preconditions:
	 * <dd>newDuration is not null
	 * 
	 * @throws IllegalArgumentException
	 * Indicates newDuration is null
	 */
	public void setDuration(double newDuration) 
			throws IllegalArgumentException{
		if(newDuration<=0)
			throw new IllegalArgumentException("Invalid Duration");
		
		duration = newDuration;
	}
	
	/**
	 * Getter for total number of bullet points in this slide
	 * 
	 * @return
	 * Number of non-null bullet points in this slide
	 */
	public int getNumBullets(){
		int count = 0;
		for(int i=0; i<MAX_BULLETS; i++){
			if(bullets[i]!=null)
				count++;
			else
				break;
		}
		return count;
	}
	
	/**
	 * Gets the bullet point at index i
	 * 
	 * @param i
	 * The index to retrieve bullet from
	 * 
	 * <dt>Preconditions:
	 * <dd> 1 {@literal<=} i {@literal<=} MAX_BULLETS
	 * 
	 * @return
	 * String representing the bullet point at given index.
	 * May be null if there is not bullet at this index.
	 * 
	 * @throws IllegalArgumentException
	 * Indicates i is not in valid range
	 * 
	 */
	public String getBullet(int i) throws IllegalArgumentException{
		if(i<1 || i>MAX_BULLETS)
			throw new IllegalArgumentException("Invalid bullet index");
		
		return bullets[i-1];
	}
	
	/**
	 * Sets bullet as the i'th bullet point. 
	 * If bullet is null, 
	 * this method erases the bullet point at index i 
	 * and pushes all bullet points greater than i back one index
	 * 
	 * @param bullet
	 * String to place in the ith bullet 
	 * @param i
	 * Index to place the bullet. 
	 * Value must be between 1 and MAX_BULLETS inclusive
	 * <dt>Preconditions:
	 * <dd> 1 {@literal<=} i {@literal<=} MAX_BULLETS
	 * <dt>Postconditions:
	 * <dd> Bullet point at index i has been updated 
	 * to the parameter bullet
	 * @throws IllegalArgumentException
	 * Indicate i is not in valid range
	 */
	public void setBullet(String bullet, int i) 
			throws IllegalArgumentException{
		if(i<1 || i>MAX_BULLETS)
			throw new IllegalArgumentException("Invalid bullet index");
		
		bullets[i-1] = bullet;
		
		//remove nulls
		int counter = 0;
		String[]temp = new String[MAX_BULLETS];
		for(int j=0; j<MAX_BULLETS; j++){
			if(bullets[j]!=null){
				temp[counter] = bullets[j];
				counter++;
			}
		}
		bullets = temp;
	}
	
	/**
	 * String representation of this slide
	 * 
	 * @return
	 * Formatted information on title, duration and bullet
	 */
	public String toString(){
		//line separator
		int length = 46;
		String style = "=";
		String line = new String(new char[length])
				.replace("\u0000", style);
		
		//heading
		String heading = String.format(
				line + "\n  %s\n" + line + "\n",title);
		
		//content
		String content = "";
		for(int i=0; i<getNumBullets(); i++)
			content += String.format("%2d. %s\n", i+1, bullets[i]);
		
		return heading + content + line + "\n";
	}
}

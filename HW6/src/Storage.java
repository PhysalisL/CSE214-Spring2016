import java.io.Serializable;

/**
 * The Storage class contains information on the integer ID which is also
 * the key used to map it in a Hastable, and it contains information on
 * the client which is stored as String and the content which is stored
 * as String. 
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class Storage implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id; 
	private String client;
	private String content;
	
	/**
	 * Default Constructor
	 * 
	 * @param id
	 * unique id which is also the key of the Storage object.
	 * @param client
	 * client that owns the Storage object
	 * @param content
	 * content in the Storage object.
	 */
	public Storage(int id, String client, String content){
		this.id = id;
		this.client = client;
		this.content = content;
	}
	
	/**
	 * Returns the id which is also the key of the Storage object.
	 * 
	 * @return
	 * id which is also the key of the Storage object.
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Returns the client that owns the Storage object.
	 * 
	 * @return
	 * client that owns the Storage object
	 */
	public String getClient(){
		return client;
	}
	
	/**
	 * Returns the content in the Storage object.
	 * 
	 * @return
	 * content in the Storage object.
	 */
	public String getContent(){
		return content;
	}
	
	/**
	 * Returns the formatted String representation of the Storage object.
	 * 
	 * @return
	 * Formatted String representation of the Storage object.
	 */
	public String toString(){
		return String.format("%-13d%-32s%-6s", id, content, client);
	}
}

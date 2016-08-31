import java.io.Serializable;
import java.util.Hashtable;

/**
 * The StorageTable class is a Hashtable to store Storage objects as values 
 * using Integer objects as key.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 *
 */
public class StorageTable extends Hashtable<Integer, Storage> 
		implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default Constructor
	 */
	public StorageTable(){
		super();
	}
	
	/**
	 * Inserts into the Hashtable using the indicated key.
	 * 
	 * @param id
	 * The key for the Storage object.
	 * @param storage
	 * Storage object to insert into the table.
	 * 
	 * <dt>Preconditions:
	 * <dd>id {@literal>=}0 and does not already exist in table.
	 * <dd>Storage object is not null
	 * 
	 * <dt>Postconditions:
	 * <dd>The Storage object has been inserted into the Hashtable with the
	 * indicated key.
	 * 
	 * @throws IllegalArgumentException
	 * Indicate id is {@literal<=} 1 or id already exists.
	 */
	public void putStorage(int id, Storage storage) 
			throws IllegalArgumentException{
		if(id <= 0 || storage == null)
			throw new IllegalArgumentException("Invalid parameters");
		if(containsKey(id))
			throw new IllegalArgumentException("ID already taken");
		
		put(id, storage);
	}
	
	/**
	 * Retrieve the Storage Object from the table having the indicated
	 * id.
	 * 
	 * @param id
	 * Key to retrieve from.
	 * 
	 * @return
	 * The Storage object with the given key.
	 * Null if it doesn't exist.
	 */
	public Storage getStorage(int id){
		return get(id);
	}
	
	/**
	 * Returns formatted String representation of every object in the table.
	 * 
	 * @return
	 * Formatted table of String representation of all Storage objects
	 * in the table.
	 */
	public String toString(){
		StringBuilder content = new StringBuilder();
		//65
		content.append(String.format(
				"%-13s%-32s%-6s\n", "Box #", "Content", "Owner"));
		for(int i=0; i<65; i++) content.append("-");
		content.append("\n");
		
		Object[] keys = keySet().toArray();
		sort(keys);
		for(Object i: keys){
			content.append(get((Integer)i).toString());
			content.append("\n");
		}
		return content.toString();
	}
	
	/**
	 * Returns a table with only Storage objects with the indicated
	 * client String.
	 * 
	 * @param client
	 * client value to retrieve Storages.
	 * 
	 * @return
	 * Formatted table with only the Storage with the indicated 
	 * client String.
	 * Empty String if no such client exists.
	 */
	public String toString(String client) throws IllegalArgumentException{
		if(keySet().size()<=0)
			throw new IllegalArgumentException("Invalid client");
		
		StringBuilder content = new StringBuilder();
		content.append(String.format(
				"%-13s%-32s%-6s\n", "Box #", "Content", "Owner"));
		for(int i=0; i<65; i++) content.append("-");
		content.append("\n");
		
		Object[] keys = keySet().toArray();
		sort(keys);
		for(Object i: keys){
			if(get((Integer)i).getClient().equalsIgnoreCase(client)){
				content.append(get((Integer)i).toString());
				content.append("\n");
			}
		}
		return content.toString();
	}
	
	/**
	 * Sorts the Integer array using bubble sort. If the array is not
	 * Integer array, nothing is changed.
	 * 
	 * @param array
	 * The array to sort
	 */
	private void sort(Object[] array){
		if(!(array instanceof Integer[])) return;
		for(int i=0; i<array.length; i++){
			for(int j=1; j<array.length-i; j++){
				if(((Integer) array[j]).compareTo((Integer) array[j-1])<0){
					Object temp = array[j];
					array[j] = array[j-1];
					array[j-1] = temp;
				}
			}
		}
	}
}

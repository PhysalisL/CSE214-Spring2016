import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;
import big.data.DataSource;

/**
 * The NeoDataBase class stores collection of NearEarthObjects, and provide
 * methods to manipulate the data.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class NeoDataBase extends LinkedList<NearEarthObject>{
	public static final String API_KEY = 
			"J9y4rDebEzTcv0rhMvnWeBwp4AC9FAlXFrzf9sNr";
	public static final String API_ROOT = 
			"https://api.nasa.gov/neo/rest/v1/neo/browse?";
	
	/**
	 * Builds a query URL given a page number.
	 * 
	 * @param pageNumber
	 * Page to load.
	 * 
	 * <dt>Preconditions:
	 * <dd>pageNumber between 0 and 715 inclusive
	 * 
	 * @return
	 * URL string that leads to the indicated page.
	 * 
	 * @throws IllegalArgumentException
	 * Indicate pageNumber is not in range.
	 */
	public String buildQueryURL(int pageNumber) throws IllegalArgumentException{
		if(pageNumber<0 || pageNumber>715)
			throw new IllegalArgumentException("Invalid page range");
		
		return API_ROOT + "page=" + pageNumber + "&api_key=" + API_KEY;
	}
	
	/**
	 * Add all the NearEarthObject information on the indicate page
	 * to the database.
	 * 
	 * @param url
	 * URL to request information from.
	 * 
	 * <dt>Preconditions:
	 * <dd>URL is not null and it is a valid request made to NASA Neow Service
	 * <dt>Postconditions:
	 * <dd>All NearEarthObject records have been added to the database
	 * 
	 * @throws IllegalArgumentException
	 * If URL is null or could not be resolved by server.
	 */
	public void addAll(String url) throws IllegalArgumentException{
		if(url == null)
			throw new IllegalArgumentException("Null URL");
		
		DataSource ds = DataSource.connectJSON(url);
		ds.load();
		NearEarthObject[] array;
		try{
		array = ds.fetchArray(
				"NearEarthObject", 
				"near_earth_objects/neo_reference_id",
				"near_earth_objects/name",
				"near_earth_objects/absolute_magnitude_h",
				"near_earth_objects/estimated_diameter/"
				+ "kilometers/estimated_diameter_min",
				"near_earth_objects/estimated_diameter/"
				+ "kilometers/estimated_diameter_max",
				"near_earth_objects/is_potentially_hazardous_asteroid",
				"near_earth_objects/close_approach_data/"
				+ "epoch_date_close_approach",
				"near_earth_objects/close_approach_data/"
				+ "miss_distance/kilometers",
				"near_earth_objects/close_approach_data/orbiting_body");
		}catch(NullPointerException e){
			throw new IllegalArgumentException("");
		}
		
		for(int i=0; i<array.length; i++){
			add(array[i]);
		}
		
	}
	
	/**
	 * 
	 * @param comparator
	 * @throws EmptyStackException
	 */
	public void sortM(Comparator<NearEarthObject> comparator) throws EmptyStackException{
		//before sorting, get the data into an array: ie turn linkedlist->array
		if(size()<=0)
			throw new EmptyStackException();
		
		mergeSort(this, comparator);
	}
	
	/**
	 * Print the formatted table representation of the database
	 */
	public void printTable(){
		String line = new String[96].toString();
		System.out.println(String.format(
				"%-10s %-15s %-8s %-10s %-10s %-16s %-12s %-9s\n%s", 
				"ID","Name","Mag","Diameter","Danger","Close Date",
				"Miss Dist","Orbits", line.replace('\u0000', '-')));
		
		Iterator<NearEarthObject> it = iterator();
		while(it.hasNext()){
			System.out.println(it.next().toString());
		}
	}
	
	
	//MERGE SORT!
	private LinkedList<NearEarthObject> mergeSort(
			LinkedList<NearEarthObject> list, 
			Comparator<NearEarthObject> comparator){
		
		if(list.size()<=1){
			return list;
		}
		else{
			LinkedList<NearEarthObject> left = mergeSort(subList(0, list.size()/2), comparator);
			LinkedList<NearEarthObject> right = mergeSort(subList(list.size()/2, list.size()), comparator);
			return merge(left, right, list, comparator);
		}
	}
	
	@Override
	public LinkedList<NearEarthObject> subList(int start, int end){
		return new LinkedList<NearEarthObject>(super.subList(start, end));
	}
	
	private LinkedList<NearEarthObject> merge(
			LinkedList<NearEarthObject> left, 
			LinkedList<NearEarthObject> right, 
			LinkedList<NearEarthObject> list, 
			Comparator<NearEarthObject> comparator){
		
		int iterIndex = 0;
		while(!left.isEmpty() && !right.isEmpty()){
			if(comparator.compare(left.getFirst(), right.getFirst())<=0){
				//list.add(left.pop());
				list.add(iterIndex++, left.pop());
			}
			else{
				list.add(iterIndex++, right.pop());
			}
		}
		if(!left.isEmpty()){
			list.addAll(iterIndex, left);
		}
		else if(!right.isEmpty()){
			list.addAll(iterIndex, right);
		}
		return list;
	}
}

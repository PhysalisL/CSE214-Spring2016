import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Object representation of a NearEarthObject. Stores information
 * on a NearEarthObject.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class NearEarthObject{
	private int referenceID;
	private String name;
	private double absoluteMagnitude;
	private double averageDiameter;
	private Date closestApproachDate;
	private double missDistance;
	private String orbitingBody;
	private boolean isDangerous;
	
	/**
	 * Default Constructor
	 * 
	 * @param referenceID
	 * ID of the NearEarthObject.
	 * @param name
	 * Name of the NearEarthObject.
	 * @param absoluteMagnitude
	 * Absolute Magnitude of the NearEarthObject.
	 * @param minDiameter
	 * Min Diameter of the NearEarthObject.
	 * @param maxDiameter
	 * Max Diameter of the NearEarthObject.
	 * @param isDangerous
	 * Danger Level of the NearEarthObject.
	 * @param closestDateTimeStamp
	 * Cloesest Approach Date of the NearEarthObject.
	 * @param missDistance
	 * Miss Distance of the NearEarthObject.
	 * @param orbitingBody
	 * Orbiting body of the NearEarthObject.
	 */
	public NearEarthObject(
			int referenceID, 
			String name, 
			double absoluteMagnitude, 
			double minDiameter, 
			double maxDiameter, 
			boolean isDangerous,
			long closestDateTimeStamp,
			double missDistance, 
			String orbitingBody){
		
		this.referenceID = referenceID;
		this.name = name;
		this.absoluteMagnitude = absoluteMagnitude;
		this.averageDiameter = (minDiameter+maxDiameter)/2;
		this.isDangerous = isDangerous;
		this.closestApproachDate = new Date(closestDateTimeStamp);
		this.missDistance = missDistance;
		this.orbitingBody = orbitingBody;
	}

	/**
	 * Formatted Information of the NearEarthObject object.
	 * 
	 * @return
	 * Formatted Information of the NearEarthObject object.
	 */
	public String toString(){
		return String.format(
				"%-10d %-15s %-8.1f %-10.3f %-10b %-16s %-12.0f %-9s", 
				referenceID, name, absoluteMagnitude, averageDiameter, 
				isDangerous, 
				DateFormat.getDateInstance(DateFormat.MEDIUM)
				.format(closestApproachDate), missDistance, orbitingBody);
		//DateFormat.getDateInstance(DateFormat.SHORT).format(closestApproachDate)
		//new SimpleDateFormat("MM-dd-yy").format(closestApproachDate)
	}

	/**
	 * Set the Cloest Approach Date.
	 * 
	 * @param cloestApproachDate
	 * Cloest Approach Date.
	 */
	public void setCloestApproachDate(Date cloestApproachDate) {
		this.closestApproachDate = cloestApproachDate;
	}
	
	/**
	 * Set the ID.
	 * 
	 * @param referenceID
	 * ID of the NearEarthObject.
	 */
	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}
	
	/**
	 * Set the name.
	 * 
	 * @param name
	 * Name of the NearEarthObject.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Set the Absolute Magnitude.
	 * 
	 * @param absoluteMagnitude
	 * Absolute Magnitude of the NearEarthObject.
	 */
	public void setAbsoluteMagnitude(double absoluteMagnitude) {
		this.absoluteMagnitude = absoluteMagnitude;
	}
	
	/**
	 * Set the Average Diameter
	 * 
	 * @param averageDiameter
	 * Average Diameter of the NearEarthObject.
	 */
	public void setAverageDiameter(double averageDiameter) {
		this.averageDiameter = averageDiameter;
	}
	
	/**
	 * Set the Miss Distance
	 * 
	 * @param missDistance
	 * Miss Distance of the NearEarthObject.
	 */
	public void setMissDistance(double missDistance) {
		this.missDistance = missDistance;
	}
	
	/**
	 * Set the Orbiting Body
	 * 
	 * @param orbitingBody
	 * Orbiting Body of the NearEarthObject.
	 */
	public void setOrbitingBody(String orbitingBody) {
		this.orbitingBody = orbitingBody;
	}
	
	/**
	 * Set the Danger Level
	 * 
	 * @param isDangerous
	 * Danger Level of the NearEarthObject.
	 */
	public void setDangerous(boolean isDangerous) {
		this.isDangerous = isDangerous;
	}
	
	/**
	 * Set the Cloest Approach Date
	 * 
	 * @return
	 * Cloest Approach Date of the NearEarthObject.
	 */
	public Date getCloestApproachDate() { return closestApproachDate; }
	
	/**
	 * Get the ID
	 * 
	 * @return
	 * ID of the NearEarthObject.
	 */
	public int getReferenceID() { return referenceID; }
	
	/**
	 * Get the name
	 * 
	 * @return
	 * name of the NearEarthObject.
	 */
	public String getName() { return name; }
	
	/**
	 * Get the Absolute Magnitude
	 * 
	 * @return
	 * Absolute Magnitude of the NearEarthObject.
	 */
	public double getAbsoluteMagnitude() { return absoluteMagnitude; }
	
	/**
	 * Get the Average Diameter
	 * 
	 * @return
	 * Average Diameter of the NearEarthObject.
	 */
	public double getAverageDiameter() { return averageDiameter; }
	
	/**
	 * Get the Miss Distance.
	 * 
	 * @return
	 * Miss Distance of the NearEarthObject.
	 */
	public double getMissDistance() { return missDistance; }
	
	/**
	 * Get the Orbiting Body
	 * 
	 * @return
	 * Orbiting Body of the NearEarthObject.
	 */
	public String getOrbitingBody() { return orbitingBody; }
	
	/**
	 * Get the Danger Level.
	 * 
	 * @return
	 * Danger Level of the NearEarthObject.
	 */
	public boolean isDangerous() { return isDangerous; }
}

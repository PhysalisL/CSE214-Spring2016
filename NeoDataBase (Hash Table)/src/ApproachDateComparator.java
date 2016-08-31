import java.util.Comparator;

/**
 * The ApproachDateComparator class compoares NearEarthObjects 
 * based on their Approach Date.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class ApproachDateComparator implements Comparator<NearEarthObject>{
	
	/**
	 * Returns an integer based on comparisons.
	 * @param left
	 * NearEarthObject object to compare.
	 * @param right
	 * NearEarthObject object to compare.
	 * 
	 * @return
	 * 1 if left is greater than right.
	 * -1 if left is less than right.
	 * 0 if both are equal.
	 */
	public int compare(NearEarthObject left, NearEarthObject right){
		if(left.getClosestApproachDate().after(
				right.getClosestApproachDate()))
			return 1;
		else if(left.getClosestApproachDate().before(
				right.getClosestApproachDate()))
			return -1;
		else
			return 0;
	}
}

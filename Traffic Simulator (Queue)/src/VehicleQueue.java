import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * The VehicleQueue class extends LinkedList with Vehicle as its type.
 * Vehicles added to this list always goes to the tail,
 * and removing an Vehicle can only be applied to the head.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class VehicleQueue extends LinkedList<Vehicle>{

    /**
     * Adds a Vehicle object to the rear of the queue.
     * 
     * @param vehicle
     * Vehicle to enqueue.
     */
    public void enqueue(Vehicle vehicle){
        addLast(vehicle);
    }

    /**
     * Remove the fist Vehicle in queue and 
     * return that Vehicle Object.
     * 
     * @return
     * Return the Vehicle object being dequeued.
     * Return null if the queue is empty.
     */
    public Vehicle dequeue(){
        try{
            return removeFirst();
        }catch (NoSuchElementException e){
            return null;
        }
    }

    /**
     * Return the formatted version of the queue
     *
     * @param reverse
     * Reverse the order of print order in returned String?
     * True for BACKWARD lanes
     * False for FORWARD lanes
     *
     * @return
     * Formatted String representation of the queue
     */
    public String toString(boolean reverse){
        StringBuilder builder = new StringBuilder();
        if(reverse)
            for(Iterator<Vehicle> i = iterator(); i.hasNext();)
                builder.append("["+i.next().getSerialID()+"]");
        else
            for(Iterator<Vehicle> i = iterator(); i.hasNext();)
                builder.insert(0, "["+i.next().getSerialID()+"]");
        return builder.toString();
    }

    /**
     * Return array of Vehicle Objects of the the queue.
     * This method overrides the super toArray, because that
     * method returns array of Objects.
     *
     * @return
     * Array of Vehicle Objects of the the queue
     */
    public Vehicle[] toArray(){
        Object[] temp = super.toArray();
        Vehicle[] returnArr = new Vehicle[size()];
        for(int i=0;i<size();i++)
            returnArr[i] = (Vehicle)(temp[i]);
        return returnArr;
    }
}

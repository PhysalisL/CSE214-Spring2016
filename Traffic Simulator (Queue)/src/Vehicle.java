/**
 * The Vehicle class holds information on the time step a new object 
 * is created and its creation number.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class Vehicle {
    private static int serialCounter = 0;
    private int serialID;
    private int timeArrived;

    /**
     * Default Constructor. SerialCounter increase with each successful
     * creation and set the new Vehicle object id to the new serial number
     *
     * <dt>Preconditions:
     * <dd>initTimeArrived > 0.
     *
     * @param initTimeArrived
     * Time the vehicle arrived at the intersection.
     *
     * @throws IllegalArgumentException
     * Indicate initTimeArrived <= 0.
     */
    public Vehicle(int initTimeArrived) throws IllegalArgumentException{
        if(initTimeArrived <= 0)
            throw new IllegalArgumentException("Invalid arrival time");

        serialID = ++serialCounter;
        timeArrived = initTimeArrived;
    }

    /**
     * Returns the serialID of the Vehicle object.
     * SerialID starts from 1 for the first car created,
     * and increment by 1 for each new car created.
     * 
     * @return
     * The serialID of the Vehicle object.
     */
    public int getSerialID(){
        return serialID;
    }

    /**
     * Returns the time the Vehicle Object is enqueued/created
     * 
     * @return
     * Time the Vehicle Object is enqueued/created
     */
    public int getTimeArrived(){
        return timeArrived;
    }
}

/**
 * Intersection manipulates which TwoWayRoad should be active
 * at a time step.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class Intersection {
    private TwoWayRoad[] roads;
    private int lightIndex = 0;
    private int countdownTimer;

    /**
     * Default Constructor.
     * Initializes the array of roads to parameter.
     * Set countdownTimer = the first TwoWayRoad Object in the array.
     * 
     * @param initRoads
     * Array of TwoWayRoad Objects for this class to manage.
     */
    public Intersection(TwoWayRoad[] initRoads){
        roads = initRoads;
        countdownTimer = roads[lightIndex].getGreenTime();
    }

    /**
     * Change TwoWayRoad selection if the current one is empty
     * or if the timer is at 0. Then dequeue appropriate Vehicles and 
     * decrement countdownTimer by 1. Lastly return an array of dequeued
     * Vehicles.
     * 
     * @return
     * Array of Vehicles dequeued during the time step.
     */
    public Vehicle[] timeStep(){
        Vehicle[] dequeued;
        if(roads[lightIndex].isRoadEmpty())
            countdownTimer = 0;
        try {
            dequeued = roads[lightIndex].proceed(countdownTimer);
        }catch (IllegalArgumentException e){
            lightIndex = nextNonEmptyIndex();
            countdownTimer = roads[lightIndex].getGreenTime();
            dequeued = roads[lightIndex].proceed(countdownTimer);
        }
        countdownTimer--;
        return dequeued;
    }

    /**
     * Obtain the green time of the next non-empty TwoWayRoad 
     * object after current TwoWayRoad object. 
     * Return the current if it is the only one that is not empty.
     *
     * @return
     * Green time of the next road object after current one.
     * Return current if it is the only non-empty TwoWayRoad object
     */
    public int nextNonEmptyGreenTime(){
        return roads[nextNonEmptyIndex()].getGreenTime();
    }

    /**
     * Obtain the next index of TwoWayRoad that is not empty.
     * The iteration is circular, and return the original position
     * if everything is empty.
     *
     * @return
     * Next index of TwoWayRoad that is not empty.
     * Or original position if everything is empty.
     */
    private int nextNonEmptyIndex(){
        int iterator = lightIndex;
        do{
            iterator = (iterator+1)%roads.length;
        }while(roads[iterator].isRoadEmpty() && iterator != lightIndex);
        return  iterator;
    }

    /**
     * Enqueues a vehicle onto a lane in the intersection.
     *
     * <dt>Preconditions:
     * <dd> 0 <= roadIndex < roads.length.
     * <dd> 0 <= wayIndex < TwoWayRoad.NUM_WAYS.
     * <dd> 0 <= laneIndex < TwoWayRoad.NUM_LANES.
     * <dd> vehicle != null.
     *
     * <dt>Postconditions:
     * <dd> Vehicle object has been enqueued.
     *
     * @param roadIndex
     * Index of the road in roads which contains the lane to enqueue onto.
     * @param wayIndex
     * Index of the direction the vehicle is headed.
     * TwoWayRoad.FORWARD or TwoWayRoad.BACKWARD.
     * @param laneIndex
     * Index of the lane on which the vehicle is to be enqueue.
     * TwoWayRoad.RIGHT_LANE, TwoWayRoad.MIDDLE_LANE, or 
     * TwoWayRoad.LEFT_LANE.
     * @param vehicle
     * The Vehicle to enqueue onto the lane.
     *
     * @throws IllegalArgumentException
     * If vehicle is null.
     * If parameters are not within the valid range.
     */
    public void enqueueVehicle(int roadIndex, int wayIndex, int laneIndex,
                               Vehicle vehicle)
            throws IllegalArgumentException{
        if(0>roadIndex || roadIndex >= roads.length || 0>wayIndex ||
                wayIndex >=TwoWayRoad.NUM_WAYS || 0>laneIndex ||
                laneIndex >= TwoWayRoad.NUM_LANES || vehicle == null)
            throw new IllegalArgumentException("Invalid Enqueue Parameters");

        roads[roadIndex].enqueueVehicle(wayIndex, laneIndex, vehicle);
    }

    /**
     * Display all roads formatted like a real intersection
     */
    public void display(){
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("%s for %s\n", 
        		roads[lightIndex].getLightValue().toString(), 
        		roads[lightIndex].getName()));
        
        for(TwoWayRoad i: roads)
            builder.append(i.toString());
        System.out.println(builder);
    }

    /**
     * Get number of Road objects in the intersection.
     * @return
     * The number of Road objects that formed the intersection.
     */
    public int getNumRoads(){ return roads.length; }

    /**
     * Get light index
     * @return
     * The index of the lit road
     */
    public int getLightIndex(){ return lightIndex; }

    /**
     * Get the preempted timer value.
     * Due to the preempt nature of timeStep(), getCountdownTimer() has
     * to behave in similar way due to preempt timer decrement. 
     * At the timeStep in which the intersection
     * perform red_light_switch, road_switch, 
     * counter_decrement all at the same time, 
     * this method has preempt to the next eligible light and return
     * that timer.
     *
     * @return
     * Timer that is keeping track of the time step of the lit road.
     * This method considers the special case during lightIndex change.
     */
    public int getCountdownTimer(){
        if(countdownTimer==0)
            return roads[nextNonEmptyIndex()].getGreenTime();
        return countdownTimer;
    }

    /**
     * Get the light value of the lit road
     * @return
     * Light value of the non-Red lit TwoWayRoad
     */
    public LightValue getCurrentLightValue(){
        return roads[lightIndex].getLightValue();
    }

    /**
     * Check whether the entire intersection is empty or not.
     * @return
     * Is the entire intersection empty?
     */
    public boolean isIntersectionEmpty(){
        for(TwoWayRoad i: roads)
            if(!i.isRoadEmpty())
                return false;
        return true;
    }

}

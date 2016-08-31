/**
 * The TwoWayRoad class represents one of the roads in Intersection.
 * This class contains one VehicleQueue for each lane and handles the
 * lighting of the road.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class TwoWayRoad {
    public static final int FORWARD_WAY = 0;
    public static final int BACKWARD_WAY = 1;
    public static final int NUM_WAYS = 2;
    public static final int LEFT_LANE = 0;
    public static final int MIDDLE_LANE = 1;
    public static final int RIGHT_LANE = 2;
    public static final int NUM_LANES = 3;

    private String name;
    private int greenTime;
    private int leftSignalGreenTime;
    private LightValue lightValue;
    private VehicleQueue[][] lanes = new VehicleQueue[NUM_WAYS][NUM_LANES];
    private boolean isRoadEmpty;

    /**
     * Default Constructor.
     * Initializes all VehicleQueues.
     * leftSignalGreenTime set to 1.0/NUM_LANES * initGreenTime
     * 
     * <dt>Preconditions:
     * <dd>initGreenTime > 0.
     * 
     * <dt>Postconditions:
     * <dd>All lanes initialized to empty queues.
     * 
     * @param initName
     * Name of the TwoWayRoad
     * @param initGreenTime
     * Maximum green time for the TwoWayRoad
     * @throws IllegalArgumentException
     * Indicates green time is <=0 or name is null
     */
    public TwoWayRoad(String initName, int initGreenTime)
            throws IllegalArgumentException{
        if(initGreenTime<=0 || initName == null)
            throw new IllegalArgumentException("Invalid Name or Green Time");

        name = initName;
        greenTime = initGreenTime;
        leftSignalGreenTime = (int)(1.0/NUM_LANES*initGreenTime);
        for(int i=0; i<NUM_WAYS; i++)
            for(int j=0; j<NUM_LANES; j++)
                lanes[i][j] = new VehicleQueue();
        isRoadEmpty = true;
        lightValue = LightValue.RED;
    }

    /**
     * Executes the passage of time in the simulation. 
     * The timerVal represents the current value of a
     * countdown timer counting down total green time steps. 
     * 
     * <dt>Preconditions:
     * <dd>The TwoWayRoad object should be instantiated.
     * 
     * <dt>Postconditions:
     * <dd>Dequeued necessary vehicles and set the lights
     * 
     * @param timerVal
     * Current timer value, determines the state of light.
     * 
     * @return
     * Array of Vehicles that has been dequeued
     * 
     * @throws IllegalArgumentException
     * Indicates timerVal <= 0.
     */
    public Vehicle[] proceed(int timerVal) throws IllegalArgumentException{
        if(timerVal<=0) {
            lightValue = LightValue.RED;
            throw new IllegalArgumentException("GreenTime Has Ended");
        }
        VehicleQueue dequeueList = new VehicleQueue();
        if(timerVal>leftSignalGreenTime){
            lightValue = LightValue.GREEN;
        }
        else if(timerVal<=leftSignalGreenTime&&timerVal>=1){
            lightValue = LightValue.LEFT_SIGNAL;
        }
        isRoadEmpty = true;
        for(int i=0; i<NUM_WAYS; i++){
            for(int j=0; j<NUM_LANES; j++){
                if (lightValue == LightValue.GREEN) {
                    if (j != LEFT_LANE && !lanes[i][j].isEmpty())
                        dequeueList.enqueue(lanes[i][j].dequeue());
                }
                else if (lightValue == LightValue.LEFT_SIGNAL) {
                    if (j == LEFT_LANE && !lanes[i][j].isEmpty())
                        dequeueList.enqueue(lanes[i][j].dequeue());
                }
                if(!lanes[i][j].isEmpty())
                    isRoadEmpty = false;
            }
        }
        return dequeueList.toArray();
    }

    /**
     * Enqueues a vehicle into a the specified lane.
     *
     * @param wayIndex
     * The direction the car is going in.
     * @param laneIndex
     * The lane the car arrives in.
     * @param vehicle
     * The vehicle to enqueue.
     *
     * <dt>Postconditions:
     * <dd>Vehicle added to the queue rear.
     *
     * @throws IllegalArgumentException
     * Indicate invalid parameter range.
     * If wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2
     * or vehicle==null.
     */
    public void enqueueVehicle(int wayIndex, int laneIndex, Vehicle vehicle)
            throws IllegalArgumentException{
        if(wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2 ||
                vehicle==null)
            throw new IllegalArgumentException("Invalid enqueue parameters");

        lanes[wayIndex][laneIndex].enqueue(vehicle);
        isRoadEmpty = false;
    }

    /**
     * Whether or not the VehicleQueue(Lane) is empty.
     *
     * @param wayIndex
     * The direction of the lane.
     * @param laneIndex
     * The index of the lane to check.
     *
     * <dt>Postconditions:
     * <dd>The TwoWayRoad object should remain unchanged.
     *
     * @return
     * Is the lane empty?
     *
     * @throws IllegalArgumentException
     * Indicate invalid parameter range.
     * If wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2
     */
    public boolean isLaneEmpty(int wayIndex, int laneIndex)
            throws IllegalArgumentException{
        if(wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2)
            throw new IllegalArgumentException("Invalid lane");

        return lanes[wayIndex][laneIndex].isEmpty();
    }

    /**
     * Get the maximum green time.
     * @return
     * The maximum Green Time before Red light is forced lit.
     */
    public int getGreenTime(){
        return greenTime;
    }

    /**
     * Get the LightValue of this TwoWayRoad.
     * @return
     * The LightValue of this TwoWayRoad.
     */
    public LightValue getLightValue(){
        return lightValue;
    }

    /**
     * Whether or not the whole array of VehicleQueues is empty.
     * @return
     * Are all the VehicleQueues in the array empty?
     */
    public boolean isRoadEmpty(){
        return isRoadEmpty;
    }

    /**
     * Get the name of the TwoWayRoad object.
     * @return
     * Name of the TwoWayRoad object.
     */
    public String getName(){ return name; }

    /**
     * Returns the formatted string representation of the TwoWayRoad object.
     * 
     * @return
     * String representation of the TwoWayRoad object formatted
     * similar to a real-life street.
     */
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(name+"\n");
        builder.append(
                "                        FORWARD               " +
                "BACKWARD\n" +
                "-------------------------------               " +
                "-------------------------------\n");
        for(int i=0, j=NUM_LANES-1; i<NUM_LANES; i++, j--){
            builder.append(String.format(
            		"%31s",lanes[FORWARD_WAY][i].toString(false)));
            String lightCombination = "";
            switch(lightValue){
                case GREEN:
                    if(i==LEFT_LANE && j==RIGHT_LANE)
                        lightCombination = "x              ";
                    if(i==MIDDLE_LANE && j==MIDDLE_LANE)
                        lightCombination = "               ";
                    if(i==RIGHT_LANE && j==LEFT_LANE)
                        lightCombination = "              x";
                    break;
                case LEFT_SIGNAL:
                    if(i==LEFT_LANE && j==RIGHT_LANE)
                        lightCombination = "              x";
                    if(i==MIDDLE_LANE && j==MIDDLE_LANE)
                        lightCombination = "x             x";
                    if(i==RIGHT_LANE && j==LEFT_LANE)
                        lightCombination = "x              ";
                    break;
                case RED:
                    lightCombination = "x             x";
                    break;
            }
            builder.append(lightCombination);
            builder.append(
                    String.format("%-30s",lanes[BACKWARD_WAY][j].toString(true))
            );
            builder.append(
                    "\n-------------------------------               " +
                    "-------------------------------\n");
        }
        return builder.toString();
    }
}

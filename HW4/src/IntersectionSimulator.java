import java.util.Scanner;

/**
 * Manager class of Intersection.
 * Executes simulation, keeps track of inforamtion
 * and prints visual representation of the Intersection.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class IntersectionSimulator {

	/**
	 * Begin simulation using the given parameters.
	 * 
	 * @param simulationTime
	 * The time Vehicles should be entering intersection.
	 * @param arrivalProb
	 * Arrival probability of Vehicles in each lane.
	 * @param numRoads
	 * Number of TwoWayRoad Objects
	 * @param roadNames
	 * Names of the TwoWayRoad Objects
	 * @param maxGreenTimes
	 * Max green time for each TwoWayRoad Object.
	 */
    public static void simulate(int simulationTime, double arrivalProb,
                                int numRoads, String[] roadNames,
                                int[] maxGreenTimes){
        if(roadNames.length != maxGreenTimes.length || 
        		numRoads != roadNames.length || arrivalProb<=0 || 
        		arrivalProb>1 || simulationTime<1){
            throw new IllegalArgumentException("Invalid parameters");
        }
        for(int i=0; i<numRoads; i++){
        	if(hasDuplicate(roadNames, roadNames[i], i-1))
        		throw new IllegalArgumentException("Duplicate names");
        }

        TwoWayRoad[] roads = new TwoWayRoad[numRoads];
        for(int i=0; i<roads.length; i++) {
            roads[i] = new TwoWayRoad(roadNames[i], maxGreenTimes[i]);
        }
        Intersection intersection = new Intersection(roads);
        BooleanSource booleanSource = new BooleanSource(arrivalProb);
        boolean run = true;
		int timeStepCounter = 1;
        int longestWait = 0;
        int totalWait = 0;
        int totalPassed = 0;
        int carOnQueue = 0;
        int greenTimer;
        boolean initialWayEmpty = true;

        while(run){
            String arrivalRecord = "ARRIVING CARS:\n";
            String passRecord = "PASSING CARS:\n";
            greenTimer = intersection.getCountdownTimer();
            if(simulationTime>0) {
                for (int i=0; i<roads.length; i++) {
                    for (int j = 0; j < TwoWayRoad.NUM_WAYS; j++) {
                        for (int k = 0; k < TwoWayRoad.NUM_LANES; k++) {
                            if (booleanSource.occurs()) {
                                Vehicle vehicle = new Vehicle(
                                		timeStepCounter);
                                roads[i].enqueueVehicle(j, k, vehicle);
                                carOnQueue++;
                                if(i==0){
                                	initialWayEmpty = false;
                                }
                                String way = (j==TwoWayRoad.FORWARD_WAY)?
                                        "FORWARD" : "BACKWARD";
                                String lane = (k==TwoWayRoad.LEFT_LANE)?
                                        "LEFT LANE" : 
                                        	(k==TwoWayRoad.RIGHT_LANE)?
                                        "RIGHT LANE" : "MIDDLE LANE";
                                arrivalRecord += String.format(
                                		"Car[%d] entered %s, "
                                		+ "going %s in %s\n",
                                		vehicle.getSerialID(),
                                		roads[i].getName(),
                                		way, 
                                		lane);
                            }
                        }
                    }
                }
            }
            if(timeStepCounter==1 && initialWayEmpty){
                greenTimer = intersection.nextNonEmptyGreenTime();
            }
            Vehicle[] dequeueList = intersection.timeStep();
            totalPassed += (dequeueList==null)? 0 : dequeueList.length;
            carOnQueue -= (dequeueList==null)? 0 : dequeueList.length;
            if(dequeueList!=null) {
                for (int i=0; i<dequeueList.length; i++) {
                    int waitTime = timeStepCounter - 
                    		dequeueList[i].getTimeArrived();
                    totalWait += waitTime;
                    longestWait = (longestWait>waitTime)? 
                    		longestWait: waitTime;
                    passRecord += String.format(
                    		"Car[%d] passed through. Wait time of %d\n",
                    		dequeueList[i].getSerialID(),
                    		waitTime);
                }
            }
            if(simulationTime<1) {
                arrivalRecord += "Cars no longer arriving.\n";
            }
            System.out.println("######################################\n");
            System.out.println("Time Step: "+timeStepCounter);
            System.out.println("Timer : "+greenTimer);
            System.out.println(arrivalRecord);
            System.out.println(passRecord);
            intersection.display();
            System.out.println("Cars currently waiting: "+carOnQueue);
            System.out.println("Total car passed: "+totalPassed);
            System.out.println("Total wait time: "+totalWait);
            System.out.printf("Average wait time: %.2f\n\n",
                    totalPassed==0? 0 : totalWait*1.0/totalPassed);
            if(simulationTime<=0 && intersection.isIntersectionEmpty()) {
                run = false;
                System.out.println("######################################");
                System.out.println("Total Time: "+timeStepCounter);
                System.out.println("Total Cars: "+totalPassed);
                System.out.println("Longest wait time: "+longestWait);
                System.out.println("Total wait time: "+totalWait);
                System.out.printf("Average wait time: %.2f\n",
                        totalPassed==0? 0 : totalWait*1.0/totalPassed);
            }
            simulationTime--;
            timeStepCounter++;
        }
    }

    /**
     * Executes the program.
     * Prompts for inputs and read them into simulation.
     * Can take either command line arguments.
     * 
     * @param args
     * Simulation time, arrival probability, names, and green times
     * for the simulation.
     */
    public static void main(String[]args){
    	int simTime = 0;
    	double arrivalProb = 0;
    	int numRoads = 0;
    	String[] names = null;
    	int[] times = null;
    	Scanner sc = new Scanner(System.in);
    	boolean useInteractive;
    	
    	if (args.length>=5) {
    		useInteractive = false;
    		try{
	        	simTime = Integer.parseInt(args[0]);
	        	arrivalProb = Double.parseDouble(args[1]);
	        	numRoads = Integer.parseInt(args[2]);
	        	names = new String[numRoads];
	        	times = new int[numRoads];
	        	for (int i = 0; i < numRoads; ++i) {
	        		names[i] = args[3+i];
	        		times[i] = Integer.parseInt(args[3+numRoads+i]);
	        	}
	        	simulate(simTime, arrivalProb, numRoads, names, times);
    		}catch(Exception e){
    			useInteractive = true;
    			System.out.println("Invalid command-line arguments");
    			System.out.println("Proceed to interactive...\n");
    		}
        }else{
        	useInteractive = true;
        }
    	
    	if(useInteractive){
        	System.out.println("Welcome to IntersectionSimulator 2016");
        	boolean done = false;
        	while(!done){
        		try{
        			System.out.println("Input the simulation time: ");
        	    	simTime = Integer.parseInt(sc.nextLine());
        	    	if (simTime<=0){
        	    		throw new IllegalArgumentException();
        	    	}else{
        	    		done = true;
        	    	}
        		}catch(NumberFormatException e){ 
        			System.out.println("Please enter a integer");
        		}catch(IllegalArgumentException e){
        			System.out.println("Please time greater than 0");
        		}
        	}
        	
        	done = false;
        	while(!done){
        		try{
        			System.out.println("Input the arrival probability: ");
        	    	arrivalProb = Double.parseDouble(sc.nextLine());
        	    	if (arrivalProb<=0 || arrivalProb>1){
        	    		throw new IllegalArgumentException();
        	    	}else{
        	    		done = true;
        	    	}
        		}catch(NumberFormatException e){ 
        			System.out.println("Please enter a double");
        		}catch(IllegalArgumentException e){
        			System.out.println("Please enter 0<probability<=1");
        		}
        	}
        	
        	done = false;
        	while(!done){
        		try{
        			System.out.println("Input number of Streets: ");
        	    	numRoads = Integer.parseInt(sc.nextLine());
        	    	if (numRoads<=0){
        	    		throw new IllegalArgumentException();
        	    	}else{
    	    	    	names = new String[numRoads];
    	    	    	times = new int[numRoads];
    	    	    	done = true;
        	    	}
        		}catch(NumberFormatException e){ 
        			System.out.println("Please enter a number");
        		}catch(IllegalArgumentException e){
        			System.out.println("Please number greater than 0");
        		}
        	}
        	for(int i=0; i<numRoads; i++){
        		done = false;
    	    	while(!done){
    	    		try{
    	    			System.out.printf("Input street %d name: \n", i+1);
    	    	    	names[i] = sc.nextLine();
    	    	    	if (names[i]==""){
    	    	    		throw new IllegalArgumentException(
    	    	    				"Name cannot be empty");
    	    	    	}
    	    	    	else if(hasDuplicate(names, names[i], i-1)){
    	    	    		throw new IllegalArgumentException(
    	    	    				"Duplicate name");
    	    	    	}
    	    	    	else{
    	    	    		done = true;
    	    	    	}
    	    		}catch(IllegalArgumentException e){
    	    			System.out.println(e.getMessage());
    	    		}
    	    	}
        	}
        	for(int i=0; i<numRoads; i++){
        		done = false;
    	    	while(!done){
    	    		try{
    	    			System.out.printf("Enter time for %s: \n", names[i]);
    	    	    	times[i] = Integer.parseInt(sc.nextLine());
    	    	    	if (times[i]<=0){
    	    	    		throw new IllegalArgumentException();
    	    	    	}else{
    	    	    		done = true;
    	    	    	}
    	    		}catch(NumberFormatException e){
    	    			System.out.println("Please enter an integer");
    	    		}catch(IllegalArgumentException e){
    	    			System.out.println("Number cannot be <=0");
    	    		}
    	    	}
        	}
        	simulate(simTime, arrivalProb, numRoads, names, times);
    	}
    }
    
    /**
     * Returns whether or not the string has duplicate in the array
     * up to the given index.
     * 
     * @param arr
     * Array of string to check duplicate from.
     * @param string
     * String to compare to elements in the array.
     * @param toIndex
     * Index of the array to to comparie up to.
     * 
     * @return
     * True if all the element up to given index in array does not equal 
     * the given string.
     * False if one or more the element up to given index in array equal 
     * the given string.
     */
    public static boolean hasDuplicate(String[] arr, String string, 
    		int upToIndex){
    	if(arr==null || arr.length==0 || upToIndex<0)
    		return false;
    	else{
    		for(int i=0; i<=upToIndex; i++)
    			if(arr[i].trim().toLowerCase().equals(
    					string.trim().toLowerCase()))
    				return true;
    	}
    	return false;
    }
}

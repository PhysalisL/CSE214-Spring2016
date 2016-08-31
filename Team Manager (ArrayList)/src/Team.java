/**
 * The Team class is an array got storing Player Objects
 * 
 * @author Yixiu Liu
 */
public class Team {
	public static final int MAX_PLAYERS = 40; //Max capacity of Team
	private Player[] playerList; //An array holding Players in Team
	
	/**
	 * Construct an instance of the Team class with zero Players
	 * 
	 * <dt>Postcondition:
	 *    <dd>Team initialized to empty array of Players
	 */
	public Team(){
		playerList = new Player[0];
	}

	/**
	 * Determinnes the number of Players in this Team
	 * 
	 * <dt>Precondition:
	 *    <dd>This Team has been initialized
	 *    
	 * @return
	 *    Number of Players in this Team
	 */
	public int size(){
		return playerList.length;
	}
	
	/**
	 * Returns reference to the Player in position
	 * 
	 * @param position
	 *    The position in the lineup from which Player will be retrieved
	 * 
	 * <dt>Precondition:
	 *    <dd> Team Object has been instantiated
	 * 
	 * @return
	 *    The Player from the given index
	 *    
	 * @throws IllegalArgumentException
	 *    Indicates position is not within range
	 */
	public Player getPlayer(int position) throws IllegalArgumentException{
		if(position>size()||position<1)
			throw new IllegalArgumentException("Invalid Range");
		else
			return playerList[position-1];
	}
	
	/**
	 * Return the Player with the best value in stat
	 * 
	 * @param stat
	 *    Player attribute,either "hits" or "errors"
	 *
	 * <dt>Precondition:
	 *    <dd>Team Object has been instantiated
	 *    
	 * @return
	 *    The Player with best stat
	 *    
	 * @throws IllegalArgumentException
	 *    Indicates stat was neither "hits" or "errors"
	 */
	public Player getLeader(String stat) throws IllegalArgumentException{
		stat = stat.toLowerCase().replaceAll(" ", "");
		
		if((!stat.equals("hits")&&!stat.equals("errors")))
			throw new IllegalArgumentException("Invalid Stat");
		if((size()==0))
			throw new IllegalArgumentException("Team is Empty");
		
		Player leader = playerList[0];
		switch(stat){
			case "hits":
				for(Player i: playerList)
					leader = (i.getHits()>leader.getHits())? i: leader;
				break;
			case "errors": 
				for(Player i: playerList)
					leader = (i.getErrors()<leader.getErrors())? i: leader;
				break;
			default: 
				throw new IllegalArgumentException("No such stat");
		}
		return leader;
	}
	
	/**
	 * Adds a Player to Team at the position
	 * 
	 * @param player
	 *    Player Object to add to this Team
	 * @param position
	 *    Position where the new Player will be inserted
	 * 
	 * <dt>Precondition:
	 *    <dd>Team Object has been instantiated
	 *    <dd>1 {@literal<=} position {@literal<=} Players_in_Team+1
	 *    <dd>The number Player Object in Team is less than MAX_PLAYERS
	 *    
	 * <dt>Postcondition:
	 *    <dd>The new Player is stored in the position in Team
	 *    <dd>Team size increased by 1
	 *    
	 * @throws IllegalArgumentException
	 *    Position is not within range
	 *    
	 * @throws FullTeamException
	 *    Team is at MAX_PLAYERS
	 */
	public void addPlayer(Player player, int position)
			throws IllegalArgumentException, FullTeamException{
		
		if((position>size()+1)||(position<1))
			throw new IllegalArgumentException("Invalid Range");
		if(size()>=MAX_PLAYERS)
			throw new FullTeamException("Team full");
		
		//Copy all players prior to the position
		Player[] newList = new Player[size()+1];
		for(int i=0; i<position-1; i++)
			newList[i] = playerList[i];
		
		//Insert the new Player in the position
		newList[position-1] = player;
		
		//Copy rest of Players after the position in order
		for(int i=position; i<size()+1; i++)
			newList[i] = playerList[i-1];
		playerList = newList;
	}
	
	/**
	 * Removes Player from the given position in Team
	 * 
	 * @param position
	 *    The position from which Player will be removed from Team
	 * 
	 * <dt>Precondition:
	 *    <dd>Team has been instantiated
	 *    <dd>1 {@literal<=} position {@literal<=} number of Players in Team
	 *    
	 * <dt>Postcondition:
	 *    <dd>The Player at the position in Team has been removed
	 *    <dd>All Players in greater indexes move forward one index
	 *    
	 * @throws IllegalArgumentException
	 *    Indicate poisition is not within valid range
	 */
	public void removePlayer(int position)throws IllegalArgumentException{
		if(position>size()||position<1)
			throw new IllegalArgumentException("Invalid Range");
		
		Player[]newList = new Player[size()-1];
		for(int i=0; i<position-1; i++)
			newList[i] = playerList[i];
		for(int i=position-1; i<size()-1; i++)
			newList[i] = playerList[i+1];
		playerList = newList;
	}

	/**
	 * Compare this Team to another object for equality
	 * 
	 * @param other
	 *    Object that this Team is compared to
	 * 
	 * <dt>Precondition:
	 *    <dd>Team has been initialized
	 *    
	 * @return
	 *    True if <code>other</code> refers to a Team object
	 *    and have same Players in same order as this Team
	 */
	public boolean equals(Object other){
		boolean isEqual = false;
		if(other instanceof Team){
			if(size()==((Team)other).size()){
				isEqual = true;
				for(int i=1; i<=size(); i++){
					if(!getPlayer(i).equals(((Team)other).getPlayer(i)))
						isEqual = false;
				}
			}
		}
		return isEqual;
	}
	
	/**
	 * Prints formatted table of each Player in Team next to their position
	 * 
	 * <dt>Precondition:
	 *    <dd>Team has been instantiated
	 *    
	 * <dt>Postcondition:
	 *    <dd>Formatted table of each Player in Team has been displayed
	 */
	public void printAllPlayers(){
		System.out.println(toString());
	}
	
	/**
	 * Generate a clone of this Team
	 * 
	 * @return
	 *    Return a deep copy of this Team
	 */
	public Object clone(){
		Team cloneTeam = new Team();
		cloneTeam.playerList = new Player[this.size()];
		for(int i=0; i<size(); i++){
			cloneTeam.playerList[i]= new Player(
					this.playerList[i].getName(), 
					this.playerList[i].getHits(), 
					this.playerList[i].getErrors()
					);
		}
		return cloneTeam;
	}
	
	/**
	 * <dt>Precondition:
	 *    <dd>Team has been instantiated
	 *    
	 * @return
	 *    Formatted String of the table of each Players in Team
	 */
	public String toString(){
		//heading
		String description = String.format(
				"%-7s %-25s %-10s %-10s\n", 
				"Player#",
				"Name",
				"Hits",
				"Errors"
				);
		description += "----------------------------"
				+ "----------------------------\n";
		
		//content
		for(int i=0; i<size(); i++)
			description += String.format(
					"%-7d %-25s %-10d %-10d\n", 
					i+1, 
					playerList[i].getName(),
					playerList[i].getHits(),
					playerList[i].getErrors()
					);
		return description;
	}
}

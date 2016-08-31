
import java.util.Scanner;

/**
 * TeamManager is an array of Team objects 
 * and has ability to manipulate them
 * 
 * @author Yixiu Liu
 *
 */
public class TeamManager{
	public static final int MAX_TEAMS = 5; //Max and current number of Teams in array
	private Team[] teamList; //Array of Teams
	private Team selectedTeam; //The Team in which the methods affect
	private Scanner scanner; //Scanner for console input
	private int teamNumber; //Selected Team number
	
	/**
	 * Construct an instance of the TeamManager class with MAX_TEAMS Team objects
	 * 
	 * <dt>Postcondition:
	 *    <dd>TeamManager initialized to array of MAX_TEAMS Team objects
	 *    <dd>selectedTeam refers to 0 index of teamList
	 *    <dd>teamNumber is selectTeam_reference_index+1
	 */
	public TeamManager(){
		scanner = new Scanner(System.in);
		teamList = new Team[MAX_TEAMS];
		for(int i=0; i<MAX_TEAMS; i++)
			teamList[i] = new Team();
		selectedTeam = teamList[0];
		teamNumber = 1;
	}

	/**
	 * String of formatted options
	 * 
	 * <dt>Precondition:
	 *    <dd>TeamManager has been instantiated
	 * 
	 * @return
	 *    Formatted String representation of menu
	 */
	public String menu(){
		return	"Team "+teamNumber+" is currently selected\n"
				+"Please select an option:\n"
				+"A)  Add Player\n"
				+"G)  Get Player stats\n"
				+"L)  Get leader in a stat\n"
				+"R)  Remove a player\n"
				+"P)  Print all players\n"
				+"S)  Size of team\n"
				+"T)  Select team\n"
				+"C)  Clone team\n"
				+"E)  Team equals\n"
				+"U)  Update stat\n"
				+"Q)  Quit\n"
				+"Select a menu option: ";
	}
	
	/**
	 * {@link Team#addPlayer(Player, int)}
	 */
	public void addPlayer(Player player, int position) 
			throws IllegalArgumentException, FullTeamException{
		selectedTeam.addPlayer(player, position);
	}
	
	/**
	 * {@link Team#removePlayer(int)}
	 */
	public void removePlayer(int position) throws IllegalArgumentException{
		selectedTeam.removePlayer(position);
	}
	
	/**
	 * {@link Team#getPlayer(int)}
	 * {@link Player#toString()}
	 */
	public String getPlayerStats(int position) throws IllegalArgumentException{
		return selectedTeam.getPlayer(position).toString();
	}
	
	/**
	 * {@link Team#getLeader(String)}
	 */
	public Player getLeader(String stat) throws IllegalArgumentException{
		return selectedTeam.getLeader(stat);
	}
	
	/**
	 * Print all players in the concole
	 * 
	 * {@link Team#printAllPlayers()}
	 * 
	 * @param position
	 * position of the Team to be printed out
	 * 
	 * <dd>Precondition:
	 *    <dt> 1 {@literal<=} position {@literal<=} MAX_TEAMS
	 * 
	 * <dd>Postcondition:
	 *    <dt> Formatted playerlist of team in position printed out 
	 * 
	 * @throws IllegalArgumentException
	 * Indicates that position is not within range
	 */
	private void printAllPlayers(int position) throws IllegalArgumentException{
		if(position<1||position>MAX_TEAMS)
			throw new IllegalArgumentException("Invalid Range");
		teamList[position-1].printAllPlayers();
	}
	
	/**
	 * {@link Team#toString()}
	 * 
	 * @return
	 * toString of the Team at specified position
	 * 
	 * @throws IllegalArgumentException
	 *    Indicate that the position is invalid
	 */
	public String teamToString(int position) throws IllegalArgumentException{
		if(position<1 || position>MAX_TEAMS)
			throw new IllegalArgumentException("Invalid Range");
		
		return teamList[position-1].toString();
	}
	
	/**
	 * {@link Team#size()}
	 */
	public int sizeOfTeam(int position) throws IllegalArgumentException{
		if(position<1||position>MAX_TEAMS)
			throw new IllegalArgumentException("Invalid Range");
		
		return teamList[position-1].size();
	}
	
	/**
	 * Select a Team in which the methods will apply to
	 * 
	 * @param teamNumber
	 *    The team number that selectedTeam and this teamNumber will refer to
	 * 
	 * <dt>Precondition:
	 *    <dd>TeamManager has been instantiated
	 *    <dd>1 {@literal<=} teamNumber {@literal<=} MAX_TEAMS
	 * 
	 * <dt>Postcondition:
	 *    <dd>selectedTeam set to teamNumber
	 *    <dd>current teamNumber set to indicated teamNumber
	 *    
	 * @throws IllegalArgumentException
	 *    Indicate invalid range of teamNumber
	 */
	public void selectTeam(int teamNumber) throws IllegalArgumentException{
		if(teamNumber<1||teamNumber>MAX_TEAMS)
			throw new IllegalArgumentException("Invalid Range");
		
		selectedTeam = teamList[teamNumber-1];
		this.teamNumber = teamNumber;
	}
	
	/**
	 * Clone a Team and assign it to another Team
	 * 
	 * @param from
	 *    Team to clone from
	 * @param to
	 *    Team to clone to
	 * 
	 * <dt>Precondition:
	 *    <dd>TeamManager has been instantiated
	 *    <dd> 1 {@literal<=} from, to {@literal<=} MAX_TEAMS
	 *    
	 * <dt>Postcondition:
	 *    <dd>Team to is referecing a deep copy of Team from
	 */
	public void cloneTeam(int from, int to) throws IllegalArgumentException{
		if(from>MAX_TEAMS||from<0||to>MAX_TEAMS||to<0)
			throw new IllegalArgumentException("Invalid Range");
		
		teamList[to-1] = (Team)(teamList[from-1].clone());
		updateSelectedTeam();//due to cloning not updating the selectedTeam reference
	}
	
	/**
	 * Refresh the reference to selectTeam
	 * 
	 * <dt>Precondition:
	 *    <dd>TeamManager has been instantiated
	 * 
	 * <dt>Postcondition:
	 *    <dd>selectedTeam reference updated
	 * 
	 */
	private void updateSelectedTeam(){
		selectedTeam = teamList[teamNumber-1];
	}
	
	/**
	 * Check the equality of two Team ojects
	 * 
	 * @param position1
	 *    Position of the first Team to compare
	 * @param position2
	 *    Position of the second Team to compare
	 * 
	 * <dt>Precondition:
	 *    <dd>TeamManager has been instantiated
	 *    <dd> 1 {@literal<=} position1, position2 {@literal<=} 5
	 * 
	 * @return
	 *     True if two Teams have same Players and in same order
	 */
	public boolean teamsEqual(int position1, int position2) throws IllegalArgumentException{
		if(position1<1||position2<1||position1>MAX_TEAMS||position2>MAX_TEAMS)
			throw new IllegalArgumentException("Invalid Range");
		
		return teamList[position1-1].equals(teamList[position2-1]);
	}
	
	/**
	 * Update stat of the Player in selectedTeam
	 * 
	 * @param name
	 *    The Player to change stats on
	 *    
	 * @param stat
	 *    Player stat to change
	 * 
	 * @param statNumber
	 *    stat number to change to
	 * 
	 * <dt>Precondition:
	 *    <dd>TeamManager has been instantiated
	 *    <dd>stat is "hits" or "errors" case insensitive
	 *    <dd> 0 {@literal<=} statNumber
	 * 
	 * <dt>Postcondition:
	 *    <dd>selectedTeam set to 
	 *    <dd>Player indicated stat changed to amount specified in statNumber
	 * 
	 * @throws IllegalArgumentException
	 *    Indicate invalid stat or invalid Player
	 */
	public void updateStat(String name, String stat, int statNumber) throws IllegalArgumentException{
		name = name.toLowerCase().trim();
		stat = stat.toLowerCase().replaceAll(" ", "");
		
		if(!stat.equals("hits")&&!stat.equals("errors"))
			throw new IllegalArgumentException("No such stat");
		
		for(int i=0; i<selectedTeam.size(); i++){
			Player player = selectedTeam.getPlayer(i+1);
			if(player.getName().toLowerCase().trim().equals(name)){
				if(stat.equals("hits"))
					player.setHits(statNumber);
				else
					player.setErrors(statNumber);
				return;
			}
		}
		throw new IllegalArgumentException("No such player");
	}

	/**
	 * Exit the program
	 * 
	 * <dt>Precondition:
	 *    <dd>TeamManager has been instantiated
	 * 
	 * <dt>Postcondition:
	 *    <dd>Program exited
	 */
	public void terminate(){
		System.exit(0);
	}
	
	/**
	 * Prompt user to enter input and invoke addPlayer 
	 * with Exception check
	 */
	private void consoleAddPlayer(){
		String name;
		int hits;
		int errors;
		int position;
		
		try {
			//Read Input
			System.out.println("Enter player name: ");
			name = scanner.nextLine();
			System.out.println("Enter player hits: ");
			hits = Integer.parseInt(scanner.nextLine());
			System.out.println("Enter player errors: ");
			errors = Integer.parseInt(scanner.nextLine());
			System.out.println("Enter player position: ");
			position = Integer.parseInt(scanner.nextLine());
			
			//Use Input
			Player player = new Player(name, hits, errors);
			selectedTeam.addPlayer(player, position);
			System.out.printf("Player added: %s\n", player.toString());
			
		} catch (NumberFormatException e){
			System.out.println(e.getMessage()+" is not an integer");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (FullTeamException e){
			System.out.println("Team is full");
		}
	}
	
	/**
	 * Prompt user to enter input and invoke <code>getPlayerStats()</code>
	 * with Exception check
	 */
	private void consoleGetPlayerStats(){
		int position;
		
		try {
			//Read Input
			System.out.println("Enter the position: ");
			position = Integer.parseInt(scanner.nextLine());
			
			//Use Input
			System.out.println(selectedTeam.getPlayer(position));
			
		} catch (NumberFormatException e){
			System.out.println(e.getMessage()+" is not an integer");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Prompt user to enter input and invoke getLeader 
	 * with Exception check
	 */
	private void consoleGetLeader(){
		String stat;
		
		try{
			//Read Input
			System.out.println("Enter the stat: ");
			stat = scanner.nextLine().toLowerCase().replaceAll(" ", "");
			
			//Use Input
			System.out.printf("Leader in %s: %s\n", stat, getLeader(stat).toString());
			
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Prompt user to enter input and invoke removePlayer 
	 * with Exception check
	 */
	private void consoleRemovePlayer(){
		int position= -1;
		
		try{
			//Read Input
			System.out.println("Enter position: ");
			position = Integer.parseInt(scanner.nextLine());
			
			//Use Input
			System.out.printf("%s has been removed from the Team\n",
					selectedTeam.getPlayer(position).getName());
			removePlayer(position);
			
		} catch (NumberFormatException e){
			System.out.println(e.getMessage()+" is not an integer");
		} catch (IllegalArgumentException e){
			System.out.printf("No player at position %d to remove\n", position);
		}
	}
	
	private void consolePrintAllPlayers(){
		int position;
		
		try{
			//Read Input
			System.out.println("Enter team position: ");
			position = Integer.parseInt(scanner.nextLine());
			
			//Use Input
			printAllPlayers(position);
			
		} catch (NumberFormatException e){
			System.out.println(e.getMessage()+" is not an integer");
		}catch (IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
	}
	
	private void consoleSizeOfTeam(){
		int position;
		
		try{
			//Read Input
			System.out.println("Enter team position: ");
			position = Integer.parseInt(scanner.nextLine());
			
			//Use Input
			System.out.printf("There are %d players int the currrent Team\n", sizeOfTeam(position));
			
		} catch (NumberFormatException e){
			System.out.println(e.getMessage()+" is not an integer");
		}catch (IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Prompt user to enter input and invoke selectTeam 
	 * with Exception check
	 */
	private void consoleSelectTeam(){
		int teamNumber;
		
		try{
			//Read Input
			System.out.println("Select team: ");
			teamNumber = Integer.parseInt(scanner.nextLine());
			
			//Use Input
			selectTeam(teamNumber);
			
		} catch (NumberFormatException e){
			System.out.println(e.getMessage()+" is not an integer");
		}catch (IllegalArgumentException e){
			System.out.println(e.getMessage());
		};
	}

	/**
	 * Prompt user to enter input and invoke clone 
	 * with Exception check
	 */
	private void consoleClone(){
		int from;
		int to;
		
		try{
			//Read Input
			System.out.println("Clone from Team index: ");
			from = Integer.parseInt(scanner.nextLine());
			System.out.println("Clone to Team index: ");
			to = Integer.parseInt(scanner.nextLine());
			
			//Use Input
			cloneTeam(from, to);
			System.out.printf("Team %s has been cloned to Team %s\n", from, to);
			
		}catch (NumberFormatException e){
			System.out.println(e.getMessage()+" is not an integer");
		}catch (IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Prompt user to enter input and invoke teamEquals 
	 * with Exception check
	 */
	private void consoleTeamEquals(){
		int position1;
		int position2;
		
		try{
			//Read Input
			System.out.println("First Team: ");
			position1 = Integer.parseInt(scanner.nextLine());
			System.out.println("Second Team: ");
			position2 = Integer.parseInt(scanner.nextLine());
			
			//Use Input
			System.out.printf("These teams are %s\n",
					teamsEqual(position1, position2)? "equal": "not equal");
			
		}catch (NumberFormatException e){
			System.out.println(e.getMessage()+" is not an integer");
		}catch (IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Prompt user to enter input and invoke updatePlayer 
	 * with Exception check
	 */
	private void consoleUpdatePlayer(){
		String name;
		String stat;
		int statNumber;
		
		try{
			//Read Input
			System.out.println("Enter the player to date: ");
			name = scanner.nextLine();
			System.out.println("Enter stat to update: ");
			stat = scanner.nextLine().toLowerCase().replaceAll(" ", "");
			System.out.println("Enter number of " + stat);
			statNumber = Integer.parseInt(scanner.nextLine());
			
			//Use Input
			updateStat(name, stat, statNumber);
			
		}catch (NumberFormatException e){
			System.out.println(e.getMessage()+" is not an integer");
		}catch (IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Prompt user to enter input and invoke methods
	 * according to input
	 */
	public void runConsole(){
		System.out.println(menu());
		switch(scanner.nextLine()){
		case "a": 
			consoleAddPlayer();
			break;
		case "g":
			consoleGetPlayerStats();
			break;
		case "l":
			consoleGetLeader();
			break;
		case "r":
			consoleRemovePlayer();
			break;
		case "p": 
			consolePrintAllPlayers();
			break;
		case "s":
			consoleSizeOfTeam();
			break;
		case "t":
			consoleSelectTeam();
			break;
		case "c":
			consoleClone();
			break;
		case "e":
			consoleTeamEquals();
			break;
		case "u":
			consoleUpdatePlayer();
			break;
		case "q": 
			System.out.println("Terminated");
			terminate();
			break;
		default: 
			System.out.println("Invalid Option");
		}
		System.out.println();
	}
	
	/*
	 * Run the console version of program
	 */
	public static void main(String[]args){
		TeamManager teamManager = new TeamManager();
		while(true){
			teamManager.runConsole();
		}
	}
}

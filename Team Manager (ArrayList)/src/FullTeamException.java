/**
 * The FullTeamException Classs should be thrown if attempted to
 * add Player Object to Team when Team size is at MAX_PLAYERS
 * 
 * @author Yixiu Liu
 *
 */
public class FullTeamException extends Exception {
	public FullTeamException(String message){
		super(message);
	}
}

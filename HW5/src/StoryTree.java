import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

/**
 * StoryTree class is a tree that contains StoryTreeNodes.
 * This class stores the Nodes and has methods to manipulate them.
 * The tree will always be complete.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 */
public class StoryTree {
	private StoryTreeNode root; //dummy
	private StoryTreeNode cursor;
	private GameState state;
	private static final int positionTextOffset = 2;
	private static final Pattern positionPattern = 
			Pattern.compile("([0-9]-)*[0-9]( )*");
	/**
	 * Default constructor.
	 */
	public StoryTree(){
		root = new StoryTreeNode("root", "root", "Hello, welcome to Zork");
		cursor = root;
		state = GameState.GAME_OVER_WIN;
	}
	
	/**
	 * Read and construct a new StoryTree object based on the data.
	 * 
	 * @param fileName
	 * File to read from
	 * 
	 * @return
	 * StoryTree constructed according to the data from the given file.
	 * 
	 * @throws IllegalArgumentException
	 * File name is empty
	 * 
	 * @throws DataFormatException
	 * Data format is not in desired format
	 * 
	 * @throws FileNotFoundException
	 * File does not exist
	 */
	public static StoryTree readTree(String fileName) 
			throws IllegalArgumentException, DataFormatException, 
			FileNotFoundException{
		if(fileName == null || fileName == "")
			throw new IllegalArgumentException("Invalid file name");
		
		Scanner reader = new Scanner(new File(fileName));
		String wholeFile = reader.useDelimiter("\\Z").next();
		reader.close();
		String[] lines = wholeFile.split("\\n");
		
		StoryTree tree = new StoryTree();
		for(int i=0; i<lines.length; i++){
			String[] attributes = lines[i].split("\\|");
			if(!Pattern.matches(positionPattern.pattern(), attributes[0]))
				throw new DataFormatException("Data not match");
			StoryTreeNode node = new StoryTreeNode(
					attributes[0].trim(),
					attributes[1].trim(), 
					attributes[2].trim());
			tree.insert(node, node.getPosition(), tree.root);
		}
		tree.cursor = tree.root.getLeft();
		return tree;
	}
	
	/**
	 * Insert a new node into the tree. Node parameter should always be 
	 * the root
	 * 
	 * @param node
	 * Node to be inserted
	 * @param position
	 * Position attribute of the new Node
	 * @param cursor
	 * Node to start traverse from. Should be root node.
	 * 
	 * @throws DataFormatException
	 * Invalid data format
	 */
	private void insert(StoryTreeNode node, String position, 
			StoryTreeNode cursor) throws DataFormatException{
		if(position.length()<1)
			throw new DataFormatException("Invalid Format");
		
		String direction = position.substring(0, 1);
		if(position.length()<=positionTextOffset){ 
			switch(direction){
			case "1": cursor.setLeft(node); break;
			case "2": cursor.setMiddle(node); break;
			case "3": cursor.setRight(node); break;
			default: throw new DataFormatException("Invalid Format");
			}
		}
		else{
			switch(direction){
			case "1" : 
				insert(node, position.substring(positionTextOffset), 
						cursor.getLeft()); 
				break;
			case "2" : 
				insert(node, position.substring(positionTextOffset), 
						cursor.getMiddle());
				break;
			case "3" : 
				insert(node, position.substring(positionTextOffset), 
						cursor.getRight()); 
				break;
			default: throw new DataFormatException("Invalid Format");
			}
		}
	}
	
	/**
	 * Save the data of the tree into a file.
	 * File with the same names in same location will be replaced by
	 * the new, otherwise generate a new file with the given name.
	 * 
	 * @param fileName
	 * Name of the file.
	 * 
	 * @param targetTree
	 * Tree to read data from.
	 */
	public static void saveTree(String fileName, StoryTree targetTree){
		try {
			PrintWriter writer = new PrintWriter(fileName);
			writer.write(generateData(targetTree.root.getLeft()));
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: Cannot save");
		}
	}
	
	/**
	 * Recursively obtain the formatted data starting at the startNode
	 * 
	 * @param startNode
	 * Root node to start obtaining info
	 * 
	 * @return
	 * Formatted data of the tree.
	 */
	private static String generateData(StoryTreeNode startNode){
		StringBuilder builder = new StringBuilder();
		builder.append(startNode.getPosition());
		builder.append(" | ");
		builder.append(startNode.getOption()); 
		builder.append(" | ");
		builder.append(startNode.getMessage());
		builder.append("\r\n");
		if(startNode.getLeft() != null)
			builder.append(generateData(startNode.getLeft()));
		if(startNode.getMiddle() != null)
			builder.append(generateData(startNode.getMiddle()));
		if(startNode.getRight() != null)
			builder.append(generateData(startNode.getRight()));
		return builder.toString();
	}
	
	
	/**
	 * Selects the child with the name indicated by position.
	 * 
	 * <dt>Preconditions:
	 * <dd>The child with the indicated position member 
	 * variable exists as a direct child of the cursor.
	 * 
	 * <dt>Postconditions:
	 * <dd>Cursor references node indicated by position.
	 * 
	 * @param position
	 * The position String of the child to node to select.
	 * 
	 * @throws IllegalArgumentException
	 * Psition is empty or null.
	 * @throws NodeNotPresentException
	 * Node with indicated position variable was not found.
	 */
	public void selectChild(String position) 
			throws IllegalArgumentException, NodeNotPresentException{
		if(position == null || position == "")
			throw new IllegalArgumentException("Invalid position");
		
		if(hasPosition(cursor.getLeft(), position))
			cursor = cursor.getLeft();
		else if(hasPosition(cursor.getMiddle(), position))
			cursor = cursor.getMiddle();
		else if(hasPosition(cursor.getRight(), position))
			cursor = cursor.getRight();
		else
			throw new NodeNotPresentException("No child "
					+position.substring(position.length()-1)
					+" for current node");
		
		if(cursor.isWinningNode())
			state = GameState.GAME_OVER_WIN;
		else if(cursor.isLosingNode())
			state = GameState.GAME_OVER_LOSE;
		else
			state = GameState.GAME_NOT_OVER;
	}
	
	/**
	 * Adds a new child under the current cursor, with given option and 
	 * message.
	 * 
	 * <dt>Postconditions:
	 * <dd>Cursor has new child, with specified message and option.
	 * 
	 * @param option
	 * The new String to set as the option of the new child.
	 * 
	 * @param message
	 * The new String to set as the message of the new child.
	 * 
	 * @throws IllegalArgumentException
	 * Either String is empty or null.
	 * @throws TreeFullException
	 * All three child spots are already full.
	 */
	public void addChild(String option, String message) 
			throws IllegalArgumentException, TreeFullException{
		if(option==null || message==null || option=="" || message=="")
			throw new IllegalArgumentException("Invalid inputs");
		
		if(cursor.getLeft() == null){
			cursor.setLeft(new StoryTreeNode(cursor.getPosition()+"-1", 
					option, message));
		}
		else if(cursor.getMiddle() == null){
			cursor.setMiddle(new StoryTreeNode(cursor.getPosition()+"-2", 
					option, message));
		}
		else if(cursor.getRight() == null){
			cursor.setRight(new StoryTreeNode(cursor.getPosition()+"-3", 
					option, message));
		}
		else{
			throw new TreeFullException();
		}
	}
	
	/**
	 * Removes an immediate subtree/child under the current cursor. 
	 * 
	 * @param position
	 * Position of the child to be removed
	 * 
	 * <dt>Preconditions:
	 * <dd>The child with the indicated position member variable exists 
	 * as a direct child of the cursor.
	 * 
	 * <dt>Postconditions:
	 * <dd>The indicated child and it's entire sub-tree have been removed 
	 * 
	 * @return
	 * The removed child and the attached subtree.
	 * 
	 * @throws NodeNotPresentException
	 * Indicate Node not found with indicated position.
	 */
	public StoryTreeNode removeChild(String position) 
			throws NodeNotPresentException{
		if(position == null || position == "")
			throw new NodeNotPresentException("Invalid position");
		
		StoryTreeNode removedChild;
		if(hasPosition(cursor.getLeft(), position)){
			if(cursor.getRight() != null)
				modifyPosition(cursor.getRight(), cursor.getMiddle());
			if(cursor.getMiddle()!= null)
				modifyPosition(cursor.getMiddle(), cursor.getLeft());
			removedChild = cursor.getLeft();
			cursor.setLeft(cursor.getMiddle());
			cursor.setMiddle(cursor.getRight());
			cursor.setRight(null);
			return removedChild;
		}
		else if(hasPosition(cursor.getMiddle(), position)){
			if(cursor.getRight() != null)
				modifyPosition(cursor.getRight(), cursor.getMiddle());
			removedChild = cursor.getMiddle();
			cursor.setMiddle(cursor.getRight());
			cursor.setRight(null);
			return removedChild;
		}
		else if(hasPosition(cursor.getRight(), position)){
			removedChild = cursor.getRight();
			cursor.setRight(null);
			return removedChild;
		}
		else
			throw new NodeNotPresentException(
					"No child "+position.substring(position.length()-1)
					+" for current node");
	}
	
	/**
	 * Recursively rename the position of sub/tree and its descendants.
	 * 
	 * @param renamer
	 * The subtree to rename.
	 * 
	 * @param takeFrom
	 * The subtree to take name from.
	 */
	private void modifyPosition(StoryTreeNode renamer, 
			StoryTreeNode takeFrom){
		int changeLength = takeFrom.getPosition().length();
		String newPosition = renamer.getPosition().
				replace(renamer.getPosition().substring(0, changeLength), 
						takeFrom.getPosition());
		renamer.setPosition(newPosition);
		if(renamer.getLeft() != null)
			modifyPosition(renamer.getLeft(), takeFrom);
		if(renamer.getMiddle() != null)
			modifyPosition(renamer.getMiddle(), takeFrom);
		if(renamer.getRight() != null)
			modifyPosition(renamer.getRight(), takeFrom);
	}
	
	/**
	 * Checks if a node has the same position attribute in a safer way.
	 * First check null, then check equality.
	 * 
	 * @param node
	 * Node to compare with position.
	 * 
	 * @param position
	 * Position String.
	 * 
	 * @return
	 * False if Node is null or not equal to position
	 * True otherwise
	 */
	private boolean hasPosition(StoryTreeNode node, String position){
		if(node == null || !position.equals(node.getPosition())) 
			return false;
		else 
			return true;
	}
	
	/**
	 * Returns an array of String pairs - {position, option} 
	 * for each immediate child of the cursor. 
	 * 
	 * @return
	 * Array of String pairs of each child node of cursor node
	 */
	public String[][] getOptions(){
		String[] left = new String[0];
		String[] middle = new String[0];
		String[] right = new String[0];
		
		if(cursor.getLeft()!=null){
			left = new String[]{
					cursor.getLeft().getPosition(), 
					cursor.getLeft().getOption()};
		}
		if(cursor.getMiddle()!=null){
			middle = new String[]{
					cursor.getMiddle().getPosition(), 
					cursor.getMiddle().getOption()};
		}
		if(cursor.getRight()!=null){
			right = new String[]{
					cursor.getRight().getPosition(), 
					cursor.getRight().getOption()};
		}
		return new String[][]{left, middle, right};
	}
	
	/**
	 * Return the winning probability relative to cursor position
	 * 
	 * @return
	 * Winning probability: winningLeaf / totalLeaf
	 */
	public double winProbability(){
		return winLeaf(cursor)/totalLeaf(cursor);
	}
	
	/**
	 * Return the number of leaf in the tree with 
	 * indicate node as root
	 * 
	 * @param node
	 * root of a tree to start traverse
	 * 
	 * @return
	 * Number of leaf in the tree with indicate node as root
	 */
	private double totalLeaf(StoryTreeNode node){
		double total = 0.0;
		if(node.isLeaf()){
			return 1.0;
		}
		else{
			if(node.getLeft()!=null) 
				total += totalLeaf(node.getLeft());
			if(node.getMiddle()!=null) 
				total += totalLeaf(node.getMiddle());
			if(node.getRight()!=null) 
				total += totalLeaf(node.getRight());
			return total;
		}
	}
	
	/**
	 * Return the number of winning leaf in the tree with 
	 * indicate node as root
	 * 
	 * @param node
	 * root of a tree to start traverse
	 * 
	 * @return
	 * Number of winning leaf in the tree with indicate node as root
	 */
	private double winLeaf(StoryTreeNode node){
		double total = 0.0;
		if(node.isWinningNode()){
			return 1.0;
		}
		else{
			if(node.getLeft()!=null) 
				total += winLeaf(node.getLeft());
			if(node.getMiddle()!=null) 
				total += winLeaf(node.getMiddle());
			if(node.getRight()!=null) 
				total += winLeaf(node.getRight());
			return total;
		}
	}
	
	/**
	 * Recursively obtain the node with the indicated position
	 * 
	 * @param pointer
	 * Starting node
	 * 
	 * @param position
	 * Position String to compare with
	 * 
	 * @return
	 * The node with indicated position.
	 * If not found, return the cursor.
	 */
	public StoryTreeNode helper(StoryTreeNode pointer, String position){
		String direction = position.substring(0, 1);
		if(position.length()<=2){
			if("1".equals(direction)){
				return pointer.getLeft();
			}
			else if("2".equals(direction)){
				return pointer.getMiddle();
			}
			else if("3".equals(direction)){
				return pointer.getRight();
			}
			else return cursor;
		}
		else{
			if("1".equals(direction)){
				return(helper(pointer.getLeft(), position.substring(2)));
			}
			else if("2".equals(direction)){
				return(helper(pointer.getMiddle(), position.substring(2)));
			}
			else if("3".equals(direction)){
				return(helper(pointer.getRight(), position.substring(2)));
			}
			else{
				System.out.println("Cannot find parent");
				return cursor;
			}
		}
	}
	
	/**
	 * Get the position of the cursor node.
	 * 
	 * @return
	 * Node position pointed by the current cursor.
	 */
	public String getCursorPosition(){
		return cursor.getPosition();
	}
	
	/**
	 * Get the option of the cursor node.
	 * 
	 * @return
	 * Node option pointed by the current cursor.
	 */
	public String getCursorOption(){
		return cursor.getOption();
	}
	
	/**
	 * Get the message of the cursor node.
	 * 
	 * @return
	 * Node message pointed by the current cursor.
	 */
	public String getCursorMessage(){
		return cursor.getMessage();
	}
	
	/**
	 * Set the node message pointed by the current cursor.
	 * 
	 * @param message
	 * New message
	 */
	public void setCursorMessage(String message){
		cursor.setMessage(message);
	}
	
	/**
	 * Set the node option pointed by the current cursor.
	 * 
	 * @param option
	 * New option
	 */
	public void setCursorOption(String option){
		cursor.setOption(option);
	}

	/**
	 * Return cursor to the node after dummy node.
	 */
	public void resetCursor(){
		state = GameState.GAME_NOT_OVER;
		cursor = root.getLeft();
	}
	
	/**
	 * Return the state of the game.
	 * 
	 * @return
	 * State of the game.
	 */
	public GameState getGameState(){
		return state;
	}
}

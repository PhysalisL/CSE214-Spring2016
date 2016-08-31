/**
 * The StoryTreeNode class is the Nodes in the StoryTree.
 * It can hold references to maximun of three other StoryTreeNodes.
 * It contains variables that describe it's properties.
 * 
 * @author Yixiu Liu 110602460
 * yixiu.liu@stonbybrook.edu
 * CSE 214-R03 Daniel Scanteianu
 *
 */
public class StoryTreeNode {
	public static final String WIN_MESSAGE = "YOU WIN";
	public static final String LOSE_MESSAGE = "YOU LOSE";
	public static final int textOffset = 2;
	private String position;
	private String option;
	private String message;
	private StoryTreeNode leftChild;
	private StoryTreeNode middleChild;
	private StoryTreeNode rightChild;
	
	/**
	 * Default Constructor.
	 * 
	 * @param position
	 * Position of the Node.
	 * @param option
	 * Name of the Node.
	 * @param message
	 * Message of the Node.
	 */
	public StoryTreeNode(String position, String option, String message){
		this.position = position;
		this.option = option;
		this.message = message;
		leftChild = null;
		middleChild = null;
		rightChild = null;
	}
	
	/**
	 * Checks if this node has any children.
	 * 
	 * <dt>Precondition:
	 * <dd>This node is initialized.
	 * 
	 * <dt>Postcondition:
	 * <dd>The tree remains unchanged.
	 * 
	 * @return
	 * True if this node has no children.
	 * False if this node has at least one child.
	 */
	public boolean isLeaf(){
		if(leftChild==null && middleChild==null && rightChild==null)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if this is a losing node. It is a losing node if it is a leaf 
	 * and it contains anything else other than winning message.
	 * 
	 * <dt>Precondition:
	 * <dd>This node is initialized.
	 * 
	 * <dt>Postcondition:
	 * <dd>The tree remains unchanged.
	 * 
	 * @return
	 * True if this node is a losing node.
	 * False otherwise.
	 */
	public boolean isLosingNode(){
		if(isLeaf() && !message.toUpperCase().contains(WIN_MESSAGE))
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if this is a winning node. It is a winning node if it is a 
	 * leaf and contains the winning message.
	 * 
	 * <dt>Precondition:
	 * <dd>This node is initialized.
	 * 
	 * <dt>Postcondition:
	 * <dd>The tree remains unchanged.
	 * 
	 * @return
	 * True if this node is a winning node.
	 * False otherwise.
	 */
	public boolean isWinningNode(){
		if(isLeaf() && message.toUpperCase().contains(WIN_MESSAGE))
			return true;
		else
			return false;
	}
	
	/**
	 * Set node position.
	 * 
	 * @param position
	 * Position to be set to the Node.
	 */
	public void setPosition(String position){
		this.position = position;
	}
	
	/**
	 * Set node option.
	 * 
	 * @param option
	 * Option to be set to the Node.
	 */
	public void setOption(String option){
		this.option = option;
	}
	
	/**
	 * Set node message.
	 * 
	 * @param message
	 * Message to be set to the Node.
	 */
	public void setMessage(String message){
		this.message = message;
	}
	
	/**
	 * Set left node.
	 * 
	 * @param node
	 * Node to be set to left child.
	 */
	public void setLeft(StoryTreeNode node){
		leftChild = node;
	}
	
	/**
	 * Set middle node.
	 * 
	 * @param node
	 * Node to be set to middle child.
	 */
	public void setMiddle(StoryTreeNode node){
		middleChild = node;
	}
	
	/**
	 * Set right node.
	 * 
	 * @param node
	 * Node to be set to right child.
	 */
	public void setRight(StoryTreeNode node){
		rightChild = node;
	}
	
	/**
	 * Get position of the node.
	 * 
	 * @return
	 * Position of the node
	 */
	public String getPosition(){
		return position;
	}
	
	/**
	 * Get option of the node.
	 * 
	 * @return
	 * Name of the node.
	 */
	public String getOption(){
		return option;
	}
	
	/**
	 * Get message of the node.
	 * 
	 * @return
	 * Message of the node.
	 */
	public String getMessage(){
		return message;
	}
	
	/**
	 * Get the left child node.
	 * 
	 * @return
	 * Left child node.
	 */
	public StoryTreeNode getLeft(){
		return leftChild;
	}
	
	/**
	 * Get the middle child node.
	 * 
	 * @return
	 * Middle child node.
	 */
	public StoryTreeNode getMiddle(){
		return middleChild;
	}
	
	/**
	 * Get the right child node.
	 * 
	 * @return
	 * Right child node.
	 */
	public StoryTreeNode getRight(){
		return rightChild;
	}
	
}

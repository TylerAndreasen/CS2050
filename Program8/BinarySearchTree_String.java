package Program8;

/**Tyler Andreasen
 * Driver Class for managing a BinarySearchTree.
 * This class assumes that Nodes only contain Strings,
 * no parsing or regular expressions are applied
 * within the BST_S or BST_S_N classes. Root Nodes
 * value is assigned by passing a String into the
 * BST_S Constructor. Further Strings are passed
 * into the BST via the pushValue(String) method
 * for possible insertion into new Nodes.
 * */
public class BinarySearchTree_String
{
	/**Field stores a reference to the root Node of the tree,
	 * keeping the entire tree in memory.
	 * */
	private final BinarySearchTree_String_Node root;
	/**Maintains a running count of all Nodes in the BST.
	 * Only incremented when a String passed in actually creates a new Node.
	 * Node deletion is not specified in the assignment and is not implemented,
	 * therefore this value will never decrement.
	 * */
	private int totalNodes;

	/**Constructor for the BinarySearchTree_String class.
	 * Expects the first word the tree should contain to be passed,
	 * as it is used to define the root node.
	 * */
	BinarySearchTree_String(String rootValue)
	{
		this.root = new BinarySearchTree_String_Node(rootValue);
		totalNodes = 1;
	}

	/**Method accepts any String and passes it to the tree structure of Nodes contained within the Tree object.
	 * This and invoked methods have no functionality to ensure the values passed in meet the requirements of
	 * the assignment. *Clean up the inputs prior to making this call.*
	 *
	 * If a passed in String is found to match that of another Node, the value is discarded,
	 * false is returned and the total number of nodes is not increased.
	 *
	 * If a passed in String is not found, a child Node is made (based on the lexicographic
	 * order and relative String lengths), true is returned and this.totalNodes is increased.
	 * */
	public void pushValueToTree(String input)
	{
		if (root.pushValue(input)) this.totalNodes++; // Now that's what I call elegant code.
	}

	/**Returns the current number of Nodes in the BST.
	 * */
	public int getTotalNodes()
	{
		return this.totalNodes;
	}

	/**Calculates and returns the current height of the tree.
	 * */
	public int getTreeHeight()
	{
		return this.root.getTreeHeight() - 1;
	}

	/**Convenience method which returns the value of the root Node of the BST
	 * */
	public String getRootValue()
	{
		return root.getData();
	}

	/**Waring: Recursive Print Method: Do not call in turned in version.
	 * */
	public void displayTree()
	{
		this.root.displayAllNodes(1);
	}/**/


	/**Warning: Recursive Print Method: Do not call in turned in version.
	 * */
	public void displayLeftEdge()
	{
		root.displayLeftEdge();
	}/**/
}


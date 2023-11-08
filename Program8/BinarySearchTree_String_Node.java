package Program8;

/**	Tyler Andreasen
 *  Node Class for a BinarySearchTree containing only Strings.
 *  New Nodes are created by passing a String (assumed to contain
 *  only good data) into the pushValue(String) Method, which
 *  determines that the String matches this node or decides its
 *  lexicographic order, passing the String further down the tree
 *  or creating a new Node when appropriate.
 * */
public class BinarySearchTree_String_Node
{
	/*The data maintained by this Node.
	* */
	private final String data;

	/*The left and right children of this Node in the BST
	*/
	private BinarySearchTree_String_Node left, right;

	BinarySearchTree_String_Node(String input)
	{
		this.data = input;
	}

	//Private Method(s)

	/**Method Compares this.data to the input string.
	 * Returns 1 if the input should go to the left,
	 * returns -1 if the input should go to the right,
	 * returns 0 if the input and this.data match.
	 *
	 * This method is more complicated than originally
	 * planned, as ~words~ like "xxvii" contain the
	 * word "x" in at the start, and a simple length
	 * comparison into character-pair-matching would
	 * yield a 'match' result erroneously. Now this
	 * method can only return 0 if the input and
	 * this.data contain the same number of letters
	 * and match in all characters.
	 * */
	private int compareInputToSelf(String input)
	{
		int longerString = Integer.compare(this.data.length(), input.length());
		switch (longerString)
		{
			case 1: //this.data is longer
				for (int i = 0; i < input.length(); i++)
				{
					if (input.charAt(i) > this.data.charAt(i)) return -1; //Shorter and Earlier
					else if (input.charAt(i) < this.data.charAt(i)) return +1; //Shorter and Later
				}
				return 1; //Shorter and Contained > Earlier
			case -1: //input is longer
				for (int i = 0; i < data.length(); i++)
				{
					if (input.charAt(i) > this.data.charAt(i)) return -1; //Longer and Earlier
					else if (input.charAt(i) < this.data.charAt(i)) return +1; //Longer and Later
				}
				return -1; //Longer and Contains > Later
			case 0: //this.data.length == input.length
				for (int i = 0; i < data.length(); i++)
				{
					if (input.charAt(i) > this.data.charAt(i)) return -1; //Longer and Earlier
					else if (input.charAt(i) < this.data.charAt(i)) return +1; //Longer and Later
				}
				return 0; //Only if the two strings are of the same length AND all characters match.
			default:
				return 1; //If there is somehow an unexpected value present, make the input the left child.
		}
	}

	//Public Methods
	/**Returns true if the passed in String created a new Node.
	 * Returns false if the input was found in the tree
	 * 	(ie, no new node was created.)
	 * */
	public boolean pushValue(String input)
	{
		int positionFlag  = this.compareInputToSelf(input);
		switch(positionFlag)
		{
			case 1: //Case that the input is earlier alphabetically than this.data.
				if (this.hasLeftChild())
					return this.left.pushValue(input);
				else
				{
					this.left = new BinarySearchTree_String_Node(input);
					return true;
				}
			case -1: //Case that the input is later alphabetically than this.data
				if (this.hasRightChild())
					return this.right.pushValue(input);
				else
				{
					this.right = new BinarySearchTree_String_Node(input);
					return true;
				}
			default: //Case that the input matches this.data
				return false;

		}
	}

	/**Returns the field data if the Node has data. Returns null otherwise.
	* */
	public String getData()
	{
		return this.data;
	}

	/**Returns whether the Node has a left child Node.
	* */
	public boolean hasLeftChild()
	{
		if (this.left != null) return true;
		return false;
	}

	/**Returns whether the Node has a right child Node.
	 * */
	public boolean hasRightChild()
	{
		if (this.right != null) return true;
		return false;
	}

	/**Waring: Recursive Print Method Remove from turned in version.
	 * */
	public void displayLeftEdge()
	{
		System.out.println(this.data);
		if (this.left != null) this.left.displayLeftEdge();
	}/**/
	/**Waring: Recursive In-Order Print Method Remove from turned in version.
	 * */
	public void displayAllNodes(int depth)
	{
		if (this.left != null) this.left.displayAllNodes(depth+1);
		//for (int i = 0; i < depth; i++) System.out.print("\t");
		System.out.print(this.data+" ");

		if (this.right != null) this.right.displayAllNodes(depth+1);
	}/**/

	/**Returns the height of the entire tree
	 * */
	public int getTreeHeight()
	{
		int 	leftHeight = (this.left == null ? 0 : this.left.getTreeHeight()),
				rightHeight = (this.right == null ? 0 : this.right.getTreeHeight());
				//Not to be confused with correctHeight
		return 1 + (leftHeight > rightHeight ? leftHeight : rightHeight);
	}


}

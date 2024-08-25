package Program9;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**Tyler Andreasen
 * Adapted from RedBlackTree.java by Blanche Cohen
 *
 * This class defines the structure of a String based Red-Black Tree.
 * */
// Red Black Tree implementation in Java
// class RedBlackTree implements the operations in Red Black Tree
public class RedBlackTree {
	private Node root;
	private Node TNULL;
	public final int RED = 1;
	public final int BLACK = 0;

	private static String FILE_LOCATION = "rbtree.txt";
	private BufferedWriter fileOut;

	private void preOrderHelper(Node node) {
		if (node != TNULL) {
			System.out.print(node.data + " ");
			preOrderHelper(node.left);
			preOrderHelper(node.right);
		} 
	}

	private void inOrderHelper(Node node) {
		if (node != TNULL) {
			inOrderHelper(node.left);
			System.out.print(node.data + " ");
			inOrderHelper(node.right);
		} 
	}

	private void postOrderHelper(Node node) {
		if (node != TNULL) {
			postOrderHelper(node.left);
			postOrderHelper(node.right);
			System.out.print(node.data + " ");
		} 
	}
	/**Rewritten due to very strange behavior.
	 * Takes in a Node and a key. Returns the Node which matches the key in its field data.
	 * Method recursively checks the children of the passed in Node, finding the desired
	 * Node or discovering the proper node it not present.
	 * */
	private Node searchTreeHelper(Node node, String key)
	{
		Node placeholderNode = new Node();
		placeholderNode.data = key;
		int comparison = RedBlackTree.compare(node, placeholderNode);
		if (comparison == 0) return node; //FOUND IT
		if (comparison == -1) //Took me far longer than it should have to realize this needed to be -1 in place of +1
		{
			if (node.left != null && node.left.data != null)
					return this.searchTreeHelper(node.left, key); //Continue searching left
			return null; //Value does not exist
		} else
		{
			if (node.right != null && node.right.data != null)
				return this.searchTreeHelper(node.right, key); //Continue searching right
			return null; //Value does not exist
		}
	}

	// fix the rb tree modified by the delete operation
	private void fixDelete(Node x) {
		Node s;
		while (x != root && x.color == BLACK) {
			if (x == x.parent.left) {
				s = x.parent.right;
				if (s.color == RED) {
					// case 3.1
					s.color = BLACK;
					x.parent.color = RED;
					leftRotate(x.parent);
					s = x.parent.right;
				}

				if (s.left.color == BLACK && s.right.color == BLACK) {
					// case 3.2
					s.color = RED;
					x = x.parent;
				} else {
					if (s.right.color == BLACK) {
						// case 3.3
						s.left.color = BLACK;
						s.color = RED;
						rightRotate(s);
						s = x.parent.right;
					} 

					// case 3.4
					s.color = x.parent.color;
					x.parent.color = BLACK;
					s.right.color = BLACK;
					leftRotate(x.parent);
					x = root;
				}
			} else {
				s = x.parent.left;
				if (s.color == RED) {
					// case 3.1
					s.color = BLACK;
					x.parent.color = RED;
					rightRotate(x.parent);
					s = x.parent.left;
				}

				if (s.right.color == BLACK && s.right.color == BLACK) {
					// case 3.2
					s.color = RED;
					x = x.parent;
				} else {
					if (s.left.color == BLACK) {
						// case 3.3
						s.right.color = BLACK;
						s.color = RED;
						leftRotate(s);
						s = x.parent.left;
					} 

					// case 3.4
					s.color = x.parent.color;
					x.parent.color = BLACK;
					s.left.color = BLACK;
					rightRotate(x.parent);
					x = root;
				}
			} 
		}
		x.color = BLACK;
	}


	private void rbTransplant(Node u, Node v){
		if (u.parent == null) {
			root = v;
		} else if (u == u.parent.left){
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}
		v.parent = u.parent;
	}

	private void deleteNodeHelper(Node node, String key) {
		// find the node containing key
		Node z = TNULL;
		Node x, y;
		while (node != TNULL){
			Node placeholderNode = new Node();
			placeholderNode.data = key;
			int comparison = compare(node, placeholderNode);
			if (0 == comparison) {
				z = node;
			}

			if (0 == comparison || 1 == comparison) {
				node = node.right;
			} else {
				node = node.left;
			}
		}

		if (z == TNULL) {
			System.out.println("Couldn't find key in the tree");
			return;
		} 

		y = z;
		int yOriginalColor = y.color;
		if (z.left == TNULL) {
			x = z.right;
			rbTransplant(z, z.right);
		} else if (z.right == TNULL) {
			x = z.left;
			rbTransplant(z, z.left);
		} else {
			y = minimum(z.right);
			yOriginalColor = y.color;
			x = y.right;
			if (y.parent == z) {
				x.parent = y;
			} else {
				rbTransplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}

			rbTransplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}
		if (yOriginalColor == BLACK){
			fixDelete(x);
		}
	}
	
	// fix the red-black tree
	private void fixInsert(Node k){
		Node u;
		if (k.parent != null)
		while (k.parent.color == RED) {
			if (k.parent == k.parent.parent.right) {
				u = k.parent.parent.left; // uncle
				if (u.color == RED) {
					// case 3.1
					u.color = BLACK;
					k.parent.color = BLACK;
					k.parent.parent.color = RED;
					k = k.parent.parent;
				} else {
					if (k == k.parent.left) {
						// case 3.2.2
						k = k.parent;
						rightRotate(k);
					}
					// case 3.2.1
					k.parent.color = BLACK;
					k.parent.parent.color = RED;
					leftRotate(k.parent.parent);
				}
			} else {
				u = k.parent.parent.right; // uncle

				if (u.color == RED) {
					// mirror case 3.1
					u.color = BLACK;
					k.parent.color = BLACK;
					k.parent.parent.color = RED;
					k = k.parent.parent;	
				} else {
					if (k == k.parent.right) {
						// mirror case 3.2.2
						k = k.parent;
						leftRotate(k);
					}
					// mirror case 3.2.1
					k.parent.color = BLACK;
					k.parent.parent.color = RED;
					rightRotate(k.parent.parent);
				}
			}
			if (k == root) {
				break;
			}
		}
		root.color = BLACK;
	}

	private void printHelper(Node root, String indent, boolean last, int depth) throws IOException
	{

		if (depth == 0) fileOut = new BufferedWriter(new FileWriter(new File(FILE_LOCATION).getCanonicalFile()));
		if (depth > 3) return;
		// print the tree structure on the screen
	   	if (root != TNULL) {
			fileOut.write(indent);
		   if (last) {
			   fileOut.write("R----");
		      indent += "     ";
		   } else {
			   fileOut.write("L----");
		      indent += "|    ";
		   }
            
           String sColor = root.color == 1?"RED":"BLACK";
		   fileOut.write(root.data + "(" + sColor + ")(Count :"+root.count+":)\n");
		   printHelper(root.left, indent, false, depth+1);
		   printHelper(root.right, indent, true, depth+1);
		}
	}

	public void closeFile() throws IOException
	{
		fileOut.flush();
		fileOut.close();
	}

	public int getTreeHeight(Node in)
	{
		int 	leftHeight = (in.left == null ? 0 : this.getTreeHeight(in.left)),
				rightHeight = (in.right == null ? 0 : this.getTreeHeight(in.right));
		//Not to be confused with correctHeight
		return 1 + (leftHeight > rightHeight ? leftHeight : rightHeight);
	}

	private int totalNodes;

	public int getTotalNodes() { return this.totalNodes; }

	public RedBlackTree() throws IOException
	{
		TNULL = new Node();
		TNULL.color = BLACK;
		TNULL.left = null;
		TNULL.right = null;
		root = TNULL;
		totalNodes = 0;
	}

	// Pre-Order traversal
	// Node.Left Subtree.Right Subtree
	public void preorder() {
		preOrderHelper(this.root);
	}

	// In-Order traversal
	// Left Subtree . Node . Right Subtree
	public void inorder() {
		inOrderHelper(this.root);
	}

	// Post-Order traversal
	// Left Subtree . Right Subtree . Node
	public void postorder() {
		postOrderHelper(this.root);
	}

	// search the tree for the key k
	// and return the corresponding node
	public Node searchTree(String k) {
		return searchTreeHelper(this.root, k);
	}

	// find the node with the minimum key
	public Node minimum(Node node) {
		while (node.left != TNULL) {
			node = node.left;
		}
		return node;
	}

	// find the node with the maximum key
	public Node maximum(Node node) {
		while (node.right != TNULL) {
			node = node.right;
		}
		return node;
	}

	// find the successor of a given node
	public Node successor(Node x) {
		// if the right subtree is not null,
		// the successor is the leftmost node in the
		// right subtree
		if (x.right != TNULL) {
			return minimum(x.right);
		}

		// else it is the lowest ancestor of x whose
		// left child is also an ancestor of x.
		Node y = x.parent;
		while (y != TNULL && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	// find the predecessor of a given node
	public Node predecessor(Node x) {
		// if the left subtree is not null,
		// the predecessor is the rightmost node in the 
		// left subtree
		if (x.left != TNULL) {
			return maximum(x.left);
		}

		Node y = x.parent;
		while (y != TNULL && x == y.left) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	// rotate left at node x
	public void leftRotate(Node x) {
		Node y = x.right;
		x.right = y.left;
		if (y.left != TNULL) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
	}

	// rotate right at node x
	public void rightRotate(Node x) {
		Node y = x.left;
		x.left = y.right;
		if (y.right != TNULL) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;
	}

	// insert the key to the tree in its appropriate position
	// and fix the tree
	/**Fixing the tree should remain the same, but how I insert nodes needs to change.
	 * At any time, a node could be found with the same data, which means that an increase
	 * in the existing Node's data, return from the method. Because of how the while
	 * loop works, this needs to be done there, and actually should be pretty simple.
	 *
	 * */
	public void insert(String key) {
		// Ordinary Binary Search Insertion
		Node node = new Node();
		node.parent = null;
		node.data = key;
		node.left = TNULL;
		node.right = TNULL;
		node.color = RED; // new node must be red
		node.count = 1;

		Node y = null;
		Node x = this.root;

		while (x != TNULL) {
			y = x;
			int comparison = compare(node, x);
			if (0 == comparison)
			{
				x.count++;
				fixInsert(node);
				return;
			}
			else if (1 == comparison) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		// y is parent of x
		node.parent = y;
		this.totalNodes++;
		if (y == null) {
			root = node;
		} else if (1 == compare(node, y)) {
			y.left = node;

		} else {
			y.right = node;
		}

		// if new node is a root node, simply return
		if (node.parent == null){
			node.color = BLACK;
			return;
		}

		// if the grandparent is null, simply return
		if (node.parent.parent == null) {
			return;
		}

		// Fix the tree
		fixInsert(node);
	}

	public Node getRoot(){
		return this.root;
	}

	// delete the node from the tree
	public void deleteNode(String data) {
		deleteNodeHelper(this.root, data);
	}

	// print the tree structure on the screen
	public void prettyPrint() throws IOException
	{
        printHelper(this.root, "", true, 0);
	}

	/**Compares the field data from the two Node objects supplied.
	 * Method was adapted from BinarySearchTree_String_Node.java by Tyler Andreasen
	 * Returns +1 if the input should go to the left,
	 * returns -1 if the input should go to the right,
	 * returns 0 if the input and this.data match.
	 * */
	private static int compare(Node u, Node v)
	{
		String uData = u.data, vData = v.data;
		int longerString = Integer.compare(uData.length(), vData.length());
		switch (longerString)
		{
			case 1: //uData is longer
				for (int i = 0; i < vData.length(); i++)
				{
					if (vData.charAt(i) > uData.charAt(i)) return +1; //Shorter and Earlier
					else if (vData.charAt(i) < uData.charAt(i)) return -1; //Shorter and Later
				}
				return 1; //Shorter and Contained > Earlier
			case -1: //vData is longer
				for (int i = 0; i < uData.length(); i++)
				{
					if (vData.charAt(i) > uData.charAt(i)) return +1; //Longer and Earlier
					else if (vData.charAt(i) < uData.charAt(i)) return -1; //Longer and Later
				}
				return -1; //Longer and Contains > Later
			case 0: //uData.length == vData.length
				for (int i = 0; i < uData.length(); i++)
				{
					if (vData.charAt(i) > uData.charAt(i)) return +1; //Longer and Earlier
					else if (vData.charAt(i) < uData.charAt(i)) return -1; //Longer and Later
				}
				return 0; //Only if the two strings are of the same length AND all characters match.
			default:
				return 1; //If there is somehow an unexpected value present, make the vData the left child.
		}
	}
}
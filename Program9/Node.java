package Program9;
/**Tyler Andreasen
 * Adapted from RedBlackTree.java by Blanche Cohen
 *
 * This class defines the structure of a String based Node in a Red-Black Tree.
 * */
// data structure that represents a node in the tree
public class Node {
	String data; // holds the key
	Node parent; // pointer to the parent
	Node left; // pointer to left child
	Node right; // pointer to right child
	int color; // 1 . Red, 0 . Black
	int count;
}
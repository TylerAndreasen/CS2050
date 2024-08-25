package Program9;

import java.io.*;
import java.util.ArrayList;

/**Tyler Andreasen
 * Adapted from Program9.java by Tyler Andreasen
 * Program 9 reads in a text file, and create Nodes for each distinct word,
 * incrementing a counter when an identical word is inserted inplace of ignoring
 * it. Initially, I planned to scrap the first BST classes I wrote, and make
 * something from scratch, but seeing the complexity of the sample code, it
 * will be simpler to adapt the given code.
 * */

public class Program9
{
	private static final String FILE_LOCATION = "src\\Program9\\dracula.txt";

	public static void main(String [] args) throws IOException
	{
		RedBlackTree bst = new RedBlackTree();

		BufferedReader fileIn = new BufferedReader(new FileReader(new File(FILE_LOCATION).getCanonicalFile()));
		ArrayList<String> fileLines = new ArrayList<String>();
		String lineIn = fileIn.readLine();
		while (lineIn != null)
		{
			fileLines.add(lineIn.trim().toLowerCase());
			lineIn = fileIn.readLine();
		}
		fileIn.close();

		for (int i = 0; i < fileLines.size(); i++)
		{
			String[] cleaned = scrub(fileLines.get(i));
			for (String word : cleaned)
			{
				bst.insert(word);
			}
		}

		bst.prettyPrint();
		bst.closeFile();

		Node theNode = bst.searchTree("the");
		String theReportString = (theNode == null ? "Does Not Exist" : ""+theNode.count);
		int totalNodes = bst.getTotalNodes();
		int heightCounter = bst.getTreeHeight(bst.getRoot())-1;
		long potentialNodes = (long) Math.pow(2, heightCounter);

		BufferedWriter fileOut = new BufferedWriter(new FileWriter(new File("results.txt").getCanonicalFile()));
		fileOut.write("Tyler Andreasen\n");
		fileOut.write("Document at :"+FILE_LOCATION+": contains the word \"the\" a total of :"+theReportString+": times,\n");
		fileOut.write("contains a total of :"+totalNodes+": nodes,\n");
		fileOut.write("spanning a height of :"+heightCounter+": nodes\n");
		fileOut.write("while a total of :"+potentialNodes+": nodes could have been used.");

		fileOut.flush();
		fileOut.close();

		//bst.inorder();

		/*
		bst.insert("the");		bst.prettyPrint();
		bst.insert("quick");		bst.prettyPrint();
		bst.insert("red");		bst.prettyPrint();
		bst.insert("fox");		bst.prettyPrint();
		bst.insert("jumped");		bst.prettyPrint();
		bst.insert("over");		bst.prettyPrint();
		bst.insert("the");		bst.prettyPrint();
		bst.insert("lazy");		bst.prettyPrint();
		bst.insert("brown");		bst.prettyPrint();
		bst.insert("dog");		bst.prettyPrint();
		bst.deleteNode("25");	bst.prettyPrint();
		*/
	}

	/**Splits the incoming String into words and removes punctuation characters from each word,
	 * only returning words that are not empty, grouped into a String[].
	 * A scrub is a guy who thinks he fly and is also known as a buster.
	 * */
	public static String[] scrub(String input)
	{
		String[] splitList = input.split("[ .]");
		ArrayList<String> activeList = new ArrayList<String>();
		for (String word : splitList)
		{
			String withoutPunct = word.replaceAll("([\\p{IsPunctuation}])|(\\d)","").trim(); //Not totally convinced this will work.
			if (!withoutPunct.equals("")) activeList.add(withoutPunct); //No Need to add a blank element
		}
		String[] output = new String[activeList.size()];
		for (int i = 0; i < output.length; i++) output[i] = activeList.get(i);
		return output;
	}
}

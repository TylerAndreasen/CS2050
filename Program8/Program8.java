package Program8;

import java.io.*;
import java.util.ArrayList;

/**Tyler Andreasen
 *
 * */
public class Program8
{

	private static final String FILE_LOCATION = "src\\Program8\\dracula0.txt";

	public static void main(String[] args) throws IOException
	{
		BufferedReader fileIn = new BufferedReader(new FileReader(new File(FILE_LOCATION).getCanonicalFile()));
		ArrayList<String> fileLines = new ArrayList<String>();
		String lineIn = fileIn.readLine();
		while (lineIn != null)
		{
			fileLines.add(lineIn.trim().toLowerCase());
			lineIn = fileIn.readLine();
		}
		fileIn.close();


		//This does technically mean the first word is inserted as the root and then again in the main loop, but
		//it is only the one element, and the system already ignores duplicates.
		String[] firstLine = scrub(fileLines.get(0));
		BinarySearchTree_String bst = new BinarySearchTree_String(firstLine[0]);
		for (int i = 0; i < fileLines.size(); i++)
		{
			String[] cleaned = scrub(fileLines.get(i));
			for (String word : cleaned)
			{
				bst.pushValueToTree(word);
			}
		}
		//bst.displayLeftEdge(); //Warning: Uncomment this line only with small test files, prints the root and left edge of Tree.
		bst.displayTree(); //Warning: Uncomment this line only with small test files, prints all Node values.

		int totalNodes = bst.getTotalNodes();
		int treeHeight = bst.getTreeHeight();
		long maximumNodesFromHeight = (long) Math.pow(2,treeHeight);
		long unusedPossibleNodes = maximumNodesFromHeight - totalNodes;


		BufferedWriter fileOut = new BufferedWriter(new FileWriter(new File("src\\Program8\\analysis.txt").getCanonicalFile()));
		fileOut.write("Tyler Andreasen\n");
		fileOut.write("Read total of :"+fileLines.size()+": lines from file :"+FILE_LOCATION+".\n");
		fileOut.write("Total Nodes in the BST :"+totalNodes+":. Root node value :"+bst.getRootValue()+":.\n");
		fileOut.write("Tree Height :"+treeHeight+":.\n");
		fileOut.write("Maximum Possible Nodes from Height :"+ maximumNodesFromHeight +":.\n");
		fileOut.write("Count of unused nodes (based on maximum possible) :"+ unusedPossibleNodes +":.");
		fileOut.flush();
		fileOut.close();
	}

	/**Splits the incoming String into words and removes punctuation characters from each word,
	 * only returning words that are not empty, grouped into a String[].
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

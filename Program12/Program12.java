package Program12;

import java.util.ArrayList;
import java.util.HashMap;

/**Tyler Andreasen
 * Program 12 (building on Program11 by Tyler Andreasen)
 * Reads a file, parses the word into a regular form, and hashes each word into a Java HashMap.
 * Repeats this process a total of three times with differing HashMap initial sizes.
 * Prints report to results.txt.
 * */
public class Program12
{
	private static final long BILLION = 1_000_000_000;

	/**
	 * Two Non-prime values used to define the lengths of the HashMap.
	 */
	private static final int[] TABLE_SIZES = {10_000,20_000};

	private static final String
			INPUT_FILE_LOCATION = "dracula.txt",
	//It took me until just now to realize that I could just move dracula.txt outside the src folder.
			OUTPUT_FILE_LOCATION = "results.txt"
	;

	public static void main(String[] args)
	{
		ArrayList<String> fileLines = CustomFileHandler.readFile(INPUT_FILE_LOCATION, true);
		ArrayList<String> allWords = new ArrayList<>();
		if (fileLines == null) System.out.println("#ERROR# Program11.main() - No Data found in :"+INPUT_FILE_LOCATION+":. #");
		else
		{
			for (String fileLine : fileLines) {
				String[] cleaned = scrubList(fileLine);
				for (String word : cleaned) {
					if (word != null) {
						allWords.add(word);
					}
				}
			}
		}

		ArrayList<String> linesOut = new ArrayList<>();
		linesOut.addAll(generateHashTableResults(allWords, 0, true));
		linesOut.addAll(generateHashTableResults(allWords, 1, false));

		linesOut.addAll(generateHashTableResults(allWords, 2, false));

		CustomFileHandler.writeFile(linesOut, OUTPUT_FILE_LOCATION, CustomFileHandler.ADD_UNFOUND_LINE_BREAKS);
	}

	/**
	 * Generates a report on a HashTable creates with the String Objects in the first parameter.
	 * Uses the second parameter to decide the hashing algorithm will be used, the third to decide
	 * the size of the hash table, and the fourth to add data like Developer Name,
	 * @param cleanedWords - Strings which are a single word, containing no punctuation or digit characters.
	 * @param primeIndex - the index of which prime number should be used for the Hash Table Size.
	 * @param addNameLine - flag to indicate if the method should include certain elements in
	 *                       the returned AL&lt;String&gt;, though this is more than just the developer name.
	 * @return - The necessary Strings (as ArrayList) to describe the created HashMap, including special data
	 * 				when tje field addNameLine is true.
	 */
	private static ArrayList<String> generateHashTableResults(ArrayList<String> cleanedWords,
															  int primeIndex, boolean addNameLine)
	{
		ArrayList<String> preparedOut = new ArrayList<>();
		int tableLength;
		HashMap<String, Integer> hashTable = switch (primeIndex) //Thank you, IntelliJ, for teaching me new Java syntax, neat.
		{
			case 1 -> {
				tableLength = TABLE_SIZES[0];
				yield new HashMap<>(TABLE_SIZES[0]);
			}
			case 2 -> {
				tableLength = TABLE_SIZES[1];
				yield new HashMap<>(TABLE_SIZES[1]);
			}
			default -> {
				tableLength = 16;
				yield new HashMap<>();
			}
		};
		double start = System.nanoTime();
		for (String word : cleanedWords) {
			if (!hashTable.containsKey(word)) {
				hashTable.put(word, 1);
			} else {
				//hashTable.replace(word, Integer.valueOf(hashTable.get(word).intValue() + 1));
				hashTable.replace(word, hashTable.get(word) + 1);
			}
		}
		double diff = (System.nanoTime() - start)/BILLION;
		if (addNameLine)
		{
			preparedOut.add("Tyler Andreasen\n");
		}
		preparedOut.add("Hash Table Initial Length :"+tableLength+":.\n");
		preparedOut.add("Hash Table Runtime :"+(diff)+":s. ");
		int wordCount = hashTable.size();
		preparedOut.add("Hash Table Nodes :"+wordCount+":, Load Factor :"+
				((double) wordCount /(double) tableLength)+":.\n");
		if (addNameLine)
		{
			preparedOut.add("Program 11 Custom HashTable Node Count 10868 from dracula.txt.\n\n");
			preparedOut.add("Program 12 Java HashMap Node Count "+wordCount+" from "+INPUT_FILE_LOCATION+".\n");
			preparedOut.add("(Comparison of Programming) Using the Native Java HashMap definitely\nrequired less set up" +
					" than building one manually, though the lack of\nability to get the size of the contained array " +
					"from the HashMap is a\nbit strange. Or the means of doing so is simply beyond me.");
		}
		preparedOut.add("\n\n");
		return preparedOut;
	}

	/**Splits the incoming String into words and removes punctuation characters from each word,
	 * only returning words that are not empty, grouped into a String[].
	 * A scrub is a guy who thinks he fly and is also known as a buster.
	 * */
	public static String[] scrubList(String input)
	{
		String[] splitList = input.split("[ .]");
		ArrayList<String> activeList = new ArrayList<>();
		for (String word : splitList)
		{
			String withoutPunct = word.replaceAll("(\\p{IsPunctuation})|(\\d)","").trim(); //Not totally convinced this will work.
			if (!withoutPunct.isEmpty()) activeList.add(withoutPunct); //No Need to add a blank element
		}
		String[] output = new String[activeList.size()];
		for (int i = 0; i < output.length; i++) output[i] = activeList.get(i);
		return output;
	}
}

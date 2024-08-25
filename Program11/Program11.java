/*package Program11;

import java.util.ArrayList;

/**Tyler Andreasen
 * Program 11 (building on Program9 by Tyler Andreasen)
 * Reads a file, parses the word into a regular form, can hash each word into a Hash Table via one of several
 * included hashing algorithms (only uses hash #3 after change in assignment description, which I now suspect
 * may have been deliberate). Collisions are passed into LinkedLists, taking operation times from O(c) to O(n).
 *
 * * /
public class Program11
{
	/*
	/**
	 * A series of descriptions of the hashes included in this Program
	 * /
	private static String[] descriptions =
			{
				"Hash Index 0 : Sums the ASCII character values of the key, then takes the remainder after division of the table's size.\n",
				"Hash Index 1 : Concatenates the ASCII character values of the key, then takes the remainder after division of the table's size.\n",
				"Hash Index 2 : Preforms a Mid-Square on the key, then takes the remainder after division of the table's size.\n",
				"Hash Index 3 : Concatenates the ASCII character values of the key, then sums while alternating between\n" +
						"doubling and tripling the value of each digit before adding to the sum. Seemingly quite inefficient.\n"
			};

	private static final String
			INPUT_FILE_LOCATION = "dracula.txt",
	//It took me until just now to realize that I could just move dracula.txt outside the src folder.
			OUTPUT_FILE_LOCATION = "results.txt"
	;

	/**
	 * Static longs to define the size of the Hash Table.
	 * /
	private static final long[] PRIMES = {12347, 55547, 10007};

	/**
	 * Used to determine the hashing algorithm used. See the static field descriptions for a description of the
	 * available algorithms. Valid values 0-3 inclusive.
	 * /
	private static final int HASH_NUMBER = 3;

	private static int rbTreeSize = 0;

	public static void main(String[] args)
	{
		ArrayList<String> fileLines = CustomFileHandler.readFile(INPUT_FILE_LOCATION, true);
		RedBlackTree rbt = new RedBlackTree();
		ArrayList<String> allWords = new ArrayList<>();
		if (fileLines == null) System.out.println("#ERROR# Program11.main() - No Data found in :"+INPUT_FILE_LOCATION+":. #");
		else
		{
			for (String fileLine : fileLines) {
				String[] cleaned = scrubList(fileLine);
				for (String word : cleaned) {
					if (word != null) {
						rbt.insert(word);
						allWords.add(word);
					}
				}
			}
		}
		rbTreeSize = rbt.getTotalNodes();
		rbt = null;
		ArrayList<String> linesOut = new ArrayList<>();
		linesOut.addAll(generateHashTableResults(allWords, HASH_NUMBER, 0, true));
		linesOut.addAll(generateHashTableResults(allWords, HASH_NUMBER, 1, false));

		//linesOut.addAll(generateHashTableResults(allWords, HASH_NUMBER, 2, false));
		//The above is commented out to reduce runtime, though does reach a load factor of above 1.0, given
		//table size and number of unique words.


		CustomFileHandler.writeFile(linesOut, OUTPUT_FILE_LOCATION, false);
		/*
		*
		* Plan - Left in this file you are curious.
		* Steps
		* 0. Read dracula.txt, pushing each word to uppercase (for easier conversions) and filtering, and then into an AL<> for storage.
		* 1. (HashTable: Size 1) Create an array of ListableWord Objects, with the initial capacity being a prime number
		* 		between 10k and 1M.
		* 2. For each word in the AL<>,
		* 2.1. Hash the word into an int.
		* 2.2. Pass the word into the LW at the index defined by the hashed int.
		*
		* 3. When the AL<> has been completed, write:
		* 			The description of the hashing algorithm,
		* 			The load factor (the number of filled elements divide the total element count)
		* 				Trouble here is I can either count the number of filled slots in the [], or the total number of LW objects.
		* 				Add both.
		* 			The table length
		* 		to an AL&lt;String&gt; for later file writing.
		* 4. Empty the hash table.
		* 5. Repeat Steps 1-3 for a different sized hash table.
		* 6. Write data saved to results.txt
		*
		* * /
		//System.out.println(hash3("dracula",PRIMES[0]));
	}

	/**
	 * Generates a report on a HashTable creates with the String Objects in the first parameter.
	 * Uses the second parameter to decide the hashing algorithm will be used, the third to decide
	 * the size of the hash table, and the fourth to add data like Developer Name,
	 * @param cleanedWords - Strings which are a single word, containing no punctuation or digit characters.
	 * @param hashNumber - indicator for which of the four available hashes should be used.
	 * @param primeIndex - the index of which prime number should be used for the Hash Table Size.
	 * @param addNameLine - flag to indicate if the method should include certain elements in
	 *                       the returned AL&lt;String&gt;, though this is more than just the developer name.
	 * @return
	 * /
	private static ArrayList<String> generateHashTableResults(ArrayList<String> cleanedWords, int hashNumber,
															  int primeIndex, boolean addNameLine)
	{
		ListableWord[] table = new ListableWord[(int) PRIMES[primeIndex]];

		for (String word : cleanedWords)
		{
			int index = hashHelper(word, table.length, hashNumber);
			if (table[index] == null) table[index] = new ListableWord();
			table[index].addWord(word);
		}
		ArrayList<String> prepparedOut = new ArrayList<>();
		if (addNameLine)
		{
			prepparedOut.add("Tyler Andreasen\n");
			prepparedOut.add("Red-Black Tree of :"+INPUT_FILE_LOCATION+": had (with the same filter) :"+rbTreeSize+": Nodes.\n\n");
		}
		prepparedOut.add(descriptions[hashNumber]);
		prepparedOut.add("Hash Table Length :"+table.length+":.\n");
		float usedSlots = 0f, totalWords = 0f;
		boolean currentHasContent = false;
		for (ListableWord entry: table)
		{
			if (entry == null) continue;
			currentHasContent = entry.hasContent();
			usedSlots += currentHasContent ? 1f : 0f;
			if (currentHasContent) totalWords += entry.getDepth();
		}

		prepparedOut.add("Hash Table Slots :"+(int) usedSlots+":, Load Factor :"+(usedSlots/table.length)+":.\n");
		prepparedOut.add("Hash Table Total-Entries :"+(int) totalWords+": Load Factor :"+(totalWords/table.length)+":.\n");
		if (addNameLine)
		{
			prepparedOut.add("Both of the above included as colliding Hash Table Entries are stored in chains.\n");
			prepparedOut.add("On review, the Total-Entry- and Slot-Based Load factors are the same when dealing with such small data sets.\n\n\n");
		} else prepparedOut.add("\n\n");
		return prepparedOut;
	}

	/** (SO) CALL ME (MAYBE)
	 * Method expects a String to be hashed, the size of the hash table, and the hash algorithm
	 * to be used. This is largely a hang over from an earlier stage of development, in which the
	 * assignment was required to use to distinct hashing algorithms.
	 * @param input - the value to be hashed.
	 * @param tableSize - the size of the table into which the hash will be placed.
	 * @param hashNumber - the index of which hash is to be used.
	 * @return - In the case that the tableSize parameter is less than 1, returns -2.
	 * 				In the case that the input parameter is empty, returns -1.
	 * 				In the case that the hash type is invalid returns -3;
	 *				Otherwise, returns the hashed value.
	 * /
	private static int hashHelper(String input, int tableSize, int hashNumber)
	{
		if (input.length() < 1)
		{
			System.out.println("#ERROR# Program11.hashHelper(input :"+input+":, tableSize :"+tableSize+":) - Size 0 input. #");
			return -1;
		}
		if (tableSize < 1)
		{
			System.out.println("#ERROR# Program11.hashHelper(input :"+input+":, tableSize :"+tableSize+":) - Table Size Invalid. #");
			return -2;
		}
		switch (hashNumber)
		{
			case 0:
				return hash0(input, tableSize);
			case 1:
				return hash1(input, tableSize);
			case 2:
				return hash2(input,tableSize);
			case 3:
				return hash3(input,tableSize);
			default:
				System.out.println("#ERROR# Program11.hashHelper(input :"+input+":, tableSize :"+tableSize+":) - Invalid Hash type :"+hashNumber+":. Valid indices 0-3 (inclusive). #");
				return -3;
		}
	}

	/**DO NOT CALL DIRECTLY
	 * Method expects a String. Returns an integer which is the sum of the
	 * ASCII character values of the input parameter.
	 * @param input - the String to be operated on.
	 * @return - the value calculated as described in the method description.
	 * /
	private static int asciiToIntegerSum(String input)
	{
		int test = 0;
		for (int i = 0; i < input.length(); i++)
		{
			test += input.charAt(i);
		}
		return test;
	}

	/**DO NOT CALL DIRECTLY
	 * Method expects a String value of at least one character to be hashed and the size of the table
	 * which the value will be hashed. The hash takes the sum of the ACSII character values of the input
	 * String into an integer. This value then has the modulo operator applied to it in with the divisor
	 * being the parameter tableSize.
	 * @param input - the value to be hashed
	 * @param tableSize - the size of the table into which the value will be hashed,
	 *                     output will be smaller than this parameter.
	 * @return - returns the hash of the input parameter.
	 * /
	private static int hash0(String input, int tableSize)
	{
		return asciiToIntegerSum(input) % tableSize;
	}

	/**DO NOT CALL DIRECTLY
	 * Method expects a String. An integer is defined with the value 0. For each character
	 * in the input String, the integer is multiplied by 100, and has the integer ASCII
	 * value of the character added to it. Note: When I first wrote the hash1() algorithm,
	 * I thought this was the original hash described, I quickly realized that this has is
	 * strongly biased to the last few characters of the input, or perhaps entirely determined
	 * by the last character with a small enough table size.
	 *
	 * @param input - the String to be operated on.
	 * @return - the value calculated as described in the method description.
	 * /
	private static int asciiToIntegerConcat(String input)
	{
		int test = 0;
		for (int i = 0; i < input.length(); i++)
		{
			test = (test * 100) + input.charAt(i);
		}
		return test;
	}

	/**DO NOT CALL DIRECTLY
	 * Method expects a String value of at least one character to be hashed and the size of the table
	 * which the value will be hashed. The hash concatenates the ACSII character values of the input
	 * String into an integer. This value then has the modulo operator applied to it in with the divisor
	 * being the parameter tableSize.
	 * @param input - the value to be hashed
	 * @param tableSize - the size of the table into which the value will be hashed,
	 *                     output will be smaller than this parameter.
	 * @return - returns the hash of the input parameter.
	 * /
	private static int hash1(String input, int tableSize)
	{
		return asciiToIntegerConcat(input) % tableSize;
	}

	/**DO NOT CALL DIRECTLY
	 * Method expects a String value of at least one character to be hashed and the size of the table
	 * which the value will be hashed. The middle two digits (ignoring the last digit if the input
	 * is of an odd length), are selected as a unit, and then has the modulo operator applied to it
	 * in with the divisor being the parameter tableSize.
	 * @param input - the value to be hashed
	 * @param tableSize - the size of the table into which the value will be hashed,
	 *                     output will be smaller than this parameter.
	 * @return - returns the hash of the input parameter.
	 * /
	private static int hash2(String input, int tableSize)
	{
		String extractFrom = ""+asciiToIntegerSum(input);
		int startIndex = extractFrom.length()/2;
		String twoChars = extractFrom.substring(startIndex, startIndex+1);
		return (int) Math.pow(Integer.parseInt(twoChars), 2) % tableSize;
	}

	/**DO NOT CALL DIRECTLY
	 * Method expects a String value of at least one character to be hashed and the size of the table
	 * which the value will be hashed into.
	 * Calculates the concatenated ASCII character value, then sums the resulting digits, doubling the
	 * rightmost and (moving left) every second digit, tripling the value of every other digit.
	 * This value then has the modulo operator applied to it in with the divisor being the parameter tableSize.
	 * @param input - the value to be hashed
	 * @param tableSize - the size of the table into which the value will be hashed,
	 *                     output will be non-negative and smaller than this parameter.
	 * @return - returns the hash of the input parameter.
	 * /
	private static int hash3(String input, int tableSize)
	{
		int ascii = asciiToIntegerConcat(input), runningTotal = 0;
		//System.out.println("Original Ascii Value :"+ascii+": for String :"+input+":");
		boolean tripleDoubleToggle = false;
		while (ascii > 0)
		{
			runningTotal += (getLastDigit(ascii) * (tripleDoubleToggle ? 3 : 2)) % tableSize;
			ascii /= 10;
			tripleDoubleToggle = !tripleDoubleToggle;
		}
		return runningTotal % tableSize;
	}

	/**DO NOT CALL DIRECTLY
	 * Method expects an integer. Returns the decimal 1's digit of precision.
	 * @param input - the value to be manipulated.
	 * /
	private static int getLastDigit(int input)
	{
		return input - stripDecimalOnesDigit(input);
	}
	/**DO NOT CALL DIRECTLY
	 * Method expects an integer. Returns the input without the decimal 1's digit of precision.
	 * @param input - the value to be manipulated.
	 * @return - returns the hash of the input parameter.
	 * /
	private static int stripDecimalOnesDigit(int input)
	{
		return (input/10) * 10;
	}

	/**Splits the incoming String into words and removes punctuation characters from each word,
	 * only returning words that are not empty, grouped into a String[].
	 * A scrub is a guy who thinks he fly and is also known as a buster.
	 * * /
	public static String[] scrubList(String input)
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
}*/

package PalindromeHashing;

import Program12.CustomFileHandler;

import java.util.ArrayList;

/**Tyler Andreasen
 *
 * Aim: Write a hashing algorithm which resolves a palindrome into
 * different values if read backwards and forwards.
 *
 * Philosophy: By definition, a palindrome is a word or phrase which
 * is read the same backwards and forwards, simply changing the
 * representation of the palindrome will not change this. Therefore,
 * to find any meaningful answer to this aim is either to implement
 * a creative work-around or to cheat, depending on your perspective.
 *
 * Implementation: Both hashing algorithms implement conversion of each
 * character into it's ASCII representation (a process which does not
 * change regardless of the reading direction). Then the value is
 * reversed or not. [Simple example: Input is 114115114: Forwards it
 * remains, but the Backwards becomes 411511411, a clearly different
 * value.] Again, whether this counts as palindrome operations is
 * debatable, and in truth it feels like a bit of a forced solution
 * to me. But from what thinking I have done, there is no way to do
 * a non-matching hash on a palindrome without some system like this.
 * Excluding any operation which accounts for the reading of the
 * value backwards is no different from simply hashing the same value
 * (palindrome or not) twice. And hashing algorithms being deterministic
 * is a cornerstone of their design, being unable to access an element
 * despite knowing its key makes a hash worthless for purpose.
 *
 * */
public class PalindromeHashing
{
	/**
	 * The palindrome Strings used to test the algorithm.
	 */
	private static final String[] palindromes =
			{
				"racecar", "tacocat", "noon", "civic", "level"
			};

	/**
	 * The prime number used as the tableSize when hashing palindromes.
	 */
	private static final int PRIME = 23;

	private static ArrayList<String> linesOut;

	public static void main(String[] args)
	{
		linesOut = new ArrayList<>();


		for (String word : palindromes)
		{
			linesOut.add(word);
			linesOut.add("f "+hashPalindromeForwards(word,PRIME)+" : b "+hashPalindromeBackwards(word, PRIME));
		}
		CustomFileHandler.writeFile(linesOut, "palindromeResults.txt", CustomFileHandler.ADD_UNFOUND_LINE_BREAKS);
	}

	/**
	 * Expects a String to hash and the size of the table it will go into.
	 * Concatenates the ASCII Character values of the input String, then
	 * character-wise reverses the digits, then converts to an integer.
	 * @param in - The String to hash.
	 * @param tableSize - the Size of the table which the entry will be added to.
	 * @return - the hash of the input String.
	 */
	private static int hashPalindromeForwards(String in, int tableSize)
	{
		String digits = parseHashToNumberCharacters(in);
		linesOut.add(digits);
		return (Integer.parseInt(digits) % tableSize);
	}

	/**
	 * Expects a String to hash and the size of the table it will go into.
	 * Concatenates the ASCII Character values of the input String, then
	 * character-wise reverses the digits, then converts to an integer,
	 * then takes the remainder modulo the passed in table size.
	 * @param in - The String to hash.
	 * @param tableSize - the Size of the table which the entry will be added to.
	 * @return - the hash of the input String.
	 */
	private static int hashPalindromeBackwards(String in, int tableSize)
	{
		String digits = reverseString(parseHashToNumberCharacters(in) );
		linesOut.add(digits);
		return (Integer.parseInt(digits) % tableSize);
	}

	/**
	 * Expects a String, returns a String which contains the concatenated
	 * digits of the ASCII Character Representations of the characters in
	 * String.
	 * @param in - the String to convert.
	 * @return - String containing concatenated ASCII values.
	 */
	private static String parseHashToNumberCharacters(String in)
	{
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < in.length(); i++)
		{
			int temp = in.charAt(i);
			build.append(temp);
		}
		//The below will return the last 9 characters of the String,
		// used to /ensure the Integer.parseInt(String) method can
		// return a value. I feel comfortable doing this as the early
		// characters are those that will influence the modulo
		// operations the most, as the higher position digits largely
		// result in a larger divisor rather than altering the remainder.

		//1_000_000_000

		//	-1 to move to the last character, -9 to use only the last 9 characters.
		return build.substring( (build.length()-1-9), (build.length()-1));
	}

	/**
	 * Expects a String, returns a new String with the same
	 * ASCII characters in the reverse order as was passed in.
	 * @param in - the String to reverse.
	 * @return - The reversed String.
	 */
	private static String reverseString(String in)
	{
		StringBuilder out = new StringBuilder();
		for (int i = in.length()-1; i > -1; i--)
		{
			out.append(in.charAt(i));
		}
		return out.toString();
	}
}

package Program11;

/**Tyler Andreasen
 * An partial implementation of the LinkedList ADT, which stores String objects
 * in the instance field content, the number of times the String has been added
 * to the LL in the instance field count, and can can traverse down the LL via
 * the instance field next.
 *
 */
public class ListableWord
{
	private String content;
	private int count = 0;
	private ListableWord next;
	ListableWord(){}


	/**
	 * Expects a String to attempt to push into the ListableWord this method was called on.
	 * Method will recursively call itself on an object's field next if the LW object
	 * addWord() is called on has a word already present.
	 *
	 * @param in - The String to add to the ListableWord.
	 */
	public void addWord(String in)
	{
		if (this.content == null) //If this exists but has not had content filled.
		{
			this.content = in;
			this.count = 1; //Set directly to 1 for safety's sake.
		} else if (in.equalsIgnoreCase(this.content))
		{
			this.count++;
		}
		//Covering all my bases
		else if (this.next == null)
		{
			this.next = new ListableWord();
			next.addWord(in);
		} else {
			next.addWord(in);
		}
	}

	/**
	 * Recursively counts the depth of the LL of ListableWord Objects from the object this method is called on.
	 *
	 * @return - The total depth of the LL of ListableWord Objects
	 */
	public int getDepth()
	{
		return 	(this.next == null ? 0 : (this.next.hasContent() ? this.next.getDepth() : 0))
				+ (this.content == null ? 0 : 1);
	}

	/**
	 * Returns whether the object has content filled. Does not operate recursively.
	 *
	 * @return - simple non-equality between the object's field content and null.
	 */
	public boolean hasContent()
	{
		return this.content != null;
	}

	public String displayContent(int depth)
	{
		String output = " ";
		if (this.content == null || this.content.equals("")) return " : "+depth;
		output += this.content;
		if (this.next == null) return output+" ; "+depth;
		if (this.next.hasContent()) output += this.next.displayContent(depth+1);
		return output;
	}
}

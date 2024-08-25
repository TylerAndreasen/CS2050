package FlowingFreely;

public class Position
{
	private static final boolean position_Class_Debug_Toggle = true;

	private static int totalNodes = 0;

	/**
	 * Value for the Object which defines the color of the node when drawn.
	 */
	private int color = -1;

	/**
	 * The 2D position of this Position Object.
	 */
	private byte[] position;

	private boolean isNode = false, isSize = false;

	/**
	 * Default Constructor for Position Objects that are not Nodes and should have inherent colors.
	 * @param position - the location in the 2D grid of positions.
	 */
	Position(byte[] position)
	{
		if (position_Class_Debug_Toggle)
			System.out.println("#NOTE# Position(byte["+position.length+"]) - Created a Position. #");
		if (position.length != 2)
			System.out.println("#ERROR# Position(byte["+position.length+"]) - Position Data Invalid, length :"+position.length+":. #");
		this.position = position;

	}

	/**
	 * Constructor for Node Positions.
	 * @param position - the location in the 2D grid of positions.
	 * @param color - the color index number which this Position is to be assigned.
	 */
	Position(byte[] position, int color)
	{
		this(position);
		this.isNode = true;
		this.color = color;
	}

	/**
	 * Constructor used to define the field size in the Level class, not to be rendered.
	 * @param position  - the location in the 2D grid of positions.
	 * @param unused - unused parameter to maintain unique signitures.
	 */
	Position(byte[] position, boolean unused)
	{
		this(position);
		this.isSize = true;
	}

	public boolean isSize() { return this.isSize; }

	public boolean isNode() { return this.isNode; }

	public int getColor() { return this.color; }
	public boolean pushColor(int color)
	{
		if (this.isNode) return false;
		else if (this.color != -1) return false;
		this.color = color;
		return true;
	}

	public boolean removeColor()
	{
		if (this.isNode) return false;
		this.color = -1;
		return true;
	}

	public byte[] getPosition() { return this.position; }

	public boolean matchesPosition(byte[] input)
	{
		if (input.length != 2)
		{
			System.out.println("#ERROR# Position.matchesPosition(byte[" + input.length + "]) - Requested Match Position Data Invalid. #");
			return false;
		}
		if (this.position[0] == input[0] && this.position[1] == input[1])
			return true;
		return false;
	}
}

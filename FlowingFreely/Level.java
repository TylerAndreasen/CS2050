package FlowingFreely;

public class Level
{
	private Position[] nodes;

	/**
	 *
	 */
	private Position size;


	Level(Position[] nodes, Position size)
	{
		this.nodes = nodes;
		this.size = size;
	}

	public void fakeRender()
	{
		byte[] dimensions = this.size.getPosition();
		for (byte i = 0; i < dimensions[0]; i++)
		{
			String build = "";
			for (byte j = 0; j < dimensions[1]; j++)
			{
				boolean found = false;
				for (Position pos : nodes)
				{
					if (pos.matchesPosition(new byte[]{j, i}))
					{
						build += pos.isNode() ? ""+pos.getColor() : "";
						found = true;
						break;
					}
				}
				if (!found) build += "_";
			}
			System.out.println(build);
		}
	}
}

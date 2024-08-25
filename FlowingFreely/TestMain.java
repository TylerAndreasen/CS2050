package FlowingFreely;

public class TestMain
{
	public static void main(String[] args)
	{
		Position[] nodes = new Position[]
				{
					new Position(new byte[] {0,0}, 0),
					new Position(new byte[] {1,4}, 0),
					new Position(new byte[] {2,0}, 1),
					new Position(new byte[] {1,3}, 1),
					new Position(new byte[] {4,0}, 2),
					new Position(new byte[] {3,3}, 2),
					new Position(new byte[] {2,1}, 3),
					new Position(new byte[] {2,4}, 3),
					new Position(new byte[] {4,1}, 4),
					new Position(new byte[] {3,4}, 4)
				};

		Level level = new Level(nodes, new Position(new byte[] {5,5}, false));
		level.fakeRender();
	}
}

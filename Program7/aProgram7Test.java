package Program7;

public class aProgram7Test
{
	public static void main(String[] args)
	{
		int[] testIn = {4,3,2,1,9,8,7,6,5,4};
		for (int i : testIn)
			System.out.print(""+i+", ");
		System.out.println();
		testIn = Program7.customInsertionSort(testIn);
		for (int i : testIn)
			System.out.print(""+i+", ");
	}
}

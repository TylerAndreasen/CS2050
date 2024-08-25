package Program10;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**Tyler Andreasen
 * Program10 will take in a list of integers from a file and
 * sorts them using the following: Bubble, Insertion, Selection,
 * Timsort (Java Native), Quicksort.
 * */

public class Program10
{
	/**Constant representing 1 billion (short system),
	 * used for calculating runtime in seconds.
	 * */
	private final static double BILLION = 1_000_000_000;

	/**Class variable to store an unchanging ArrayList version of the data needed, this will
	 * not change after being filled with all integers from the file, only read from.
	 * */
	private static ArrayList<Integer> stableIntegers;
	/**Class variable to store the data for the current sorting algorithm.
	 * This is refilled with the values from stableIntegers after each sort.
	 * */
	private static int[] operatingIntegers;

	/**The location from which data is read or written. I know that the literals defined here are problematic
	 * for moving source code between systems, but I am struggling to determine a solution that is both
	 * OS-independent and avoids searching potentially the entire file system.
	 * */
	private final static String INPUT_FILE_LOCATION = "src\\Program10\\NumbersInFile.txt",
								OUTPUT_FILE_LOCATION = "src\\Program10\\results.txt";


	public static void main(String[] args)
	{
		try {
			ArrayList<String> preparedOut = new ArrayList<String>();

			stableIntegers = new ArrayList<Integer>();
			BufferedReader fileIn = new BufferedReader(new FileReader(new File(INPUT_FILE_LOCATION).getCanonicalFile()));
			Integer lineIn = Integer.parseInt(fileIn.readLine().trim());
			while (lineIn != null) {
				stableIntegers.add(lineIn);
				try {
					String temp = fileIn.readLine().trim();
					lineIn = Integer.parseInt(temp);

				} catch (NullPointerException e) {
					break;
				}
			}
			fileIn.close();

			preparedOut.add("Tyler Andreasen\n");

			//Bubble Sort
			operatingIntegers = fillArray(stableIntegers); //Reuse this line
			long start = System.nanoTime();
			operatingIntegers = customBubbleSort(operatingIntegers);
			double diff = (System.nanoTime()-start) / BILLION;
			preparedOut.add("Bubble Sort : "+diff+'\n');

			//Insertion Sort
			operatingIntegers = fillArray(stableIntegers); //Reuse this line
			start = System.nanoTime();
			operatingIntegers = customInsertionSort(operatingIntegers);
			diff = (System.nanoTime()-start) / BILLION;
			preparedOut.add("Insertion Sort : "+diff+'\n');

			//Selection Sort
			operatingIntegers = fillArray(stableIntegers); //Reuse this line
			start = System.nanoTime();
			operatingIntegers = customSelectionSort(operatingIntegers);
			diff = (System.nanoTime()-start) / BILLION;
			preparedOut.add("Selection Sort : "+diff+'\n');



			/* I am very glad I read over the requirements again, as I was most of the way through implementing
			 * Timsort before I noticed the change / it being native.
			 *
			 * Timsort - Took me longer than it should have to discover that TimSort is the default for objects
			 * and not something called on primitives, as I was struggling to figure out how to call TimSort.sort().
			 */
			Integer[] operatingIntegerObjects = fillArrayObjects(stableIntegers);
			start = System.nanoTime();
			Arrays.sort(operatingIntegerObjects);
			diff = (System.nanoTime()-start) / BILLION;
			preparedOut.add("TimSort Sort : "+diff+'\n');

			//Quicksort
			operatingIntegers = fillArray(stableIntegers); //Reuse this line
			start = System.nanoTime();
			operatingIntegers = customSelectionSort(operatingIntegers);
			diff = (System.nanoTime()-start) / BILLION;
			preparedOut.add("Quicksort : "+diff+'\n');


			/*
			To visually test outputs if desired.
			for (int i = 0; i < 100; i++)
			{
				//System.out.println(operatingIntegers[i]);
				//System.out.println(operatingIntegerObjects[i]);
			}
			 */

			BufferedWriter fileOut = new BufferedWriter(new FileWriter(new File(OUTPUT_FILE_LOCATION).getCanonicalFile()));
			for (String line : preparedOut)
			{
				fileOut.write(line);
			}
			fileOut.flush();
			fileOut.close();

		} catch (IOException e)
		{
			System.out.println("#EXCEPTION# FILE IO Issue. #");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println("#EXCEPTION# Non-FILE Issue. #");
			e.printStackTrace();
		}
	}

	private static int[] fillArray(ArrayList<Integer> in)
	{
		int[] output = new int[in.size()];
		for (int i = 0; i < output.length; i++)
			output[i] = in.get(i);
		return output;
	}
	private static Integer[] fillArrayObjects(ArrayList<Integer> in)
	{
		Integer[] output = new Integer[in.size()];
		for (int i = 0; i < output.length; i++)
			output[i] = in.get(i);
		return output;
	}

	/**Performs a Quicksort on the parameter in, and returns the sorted int[].
	 * */
	private static int[] customQuicksort(int[] in)
	{
		return customQuicksort(in, 0, in.length);
	}

	/**Performs a Quicksort on the parameter in over the range specified by the parameters
	 * start and end, and returns the sorted int[].
	 * Modified from the Open Data Structures (in Java) textbook.
	 * */
	private static int[] customQuicksort(int[] in, int start, int end)
	{
		rangeCheck(in.length, start, end);
		if (end <= 1) return in;
		int x = in[start]; //I am choosing to simply use the first element of the sub array.
		int p = start-1, j = start, q = start+end;
		while (j < q)
		{
			int comp = (in[j] > x ? 1 : (in[j] < x ? -1 : 0));
			if (comp < 0)
			{
				in = swap(in, j++, ++p);
			} else if (comp > 0)
			{
				in = swap(in, j, --q);
			} else j++;
		}
		in = customQuicksort(in, start, p-start+1);
		in = customQuicksort(in, q, end-(q-start));
		return in;
	}

	/**Method which swaps the values of two indices in an int[], determined by the two int parameters.
	 * */
	private static int[] swap(int[] in, int a, int b)
	{
		int temp = in[b];
		in[b] = in[a];
		in[a] = temp;
		return in;
	}



	/**Taken directly from java.util.Arrays.java;, performs a check to ensure that the
	 * beginning and end indexes into an array are valid.
	 * */
	private static void rangeCheck(int arrayLength, int fromIndex, int toIndex)
	{
		if (fromIndex > toIndex) {
			throw new IllegalArgumentException(
					"fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
		}
		if (fromIndex < 0) {
			throw new ArrayIndexOutOfBoundsException(fromIndex);
		}
		if (toIndex > arrayLength) {
			throw new ArrayIndexOutOfBoundsException(toIndex);
		}
	}


	/**Performs an Insertion sort on the parameter in, and returns the sorted int[].
	 * */
	public static int[] customInsertionSort(int[] in)
	{
		if (in.length < 2) return in;
		if (in[0] > in[1])
		{
			int temp = in[0];
			in[0] = in[1];
			in[1] = temp;
		}
		if (in.length == 2) return in;
		int largestSortedIndex = 1;
		for (int i = 1; i < in.length-1; i++)
		{
			if (in[i] <= in[i+1])
			{
				largestSortedIndex++;
				continue;
			} else
			{
				int temp = in[i];
				in[i] = in[i+1];
				in[i+1] = temp;
				for (int j = i-1; j > -1; j--) //i starts at 1, so no risk of negative array index.
				{
					if (in[j] > in[j+1])
					{
						temp = in[j];
						in[j] = in[j+1];
						in[j+1] = temp;
					} else break;
				}
			}
		}

		return in;
	}
	/**Performs a Bubble sort on the parameter in, and returns the sorted array of int_s.
	 * */
	private static int[] customBubbleSort(int[] in)
	{
		int temp;
		for (int i = 0; i < in.length-1; i++)
		{
			for (int j = i+1; j < in.length; j++)
			{
				if (in[i] > in[j])
				{
					temp = in[i];
					in[i] = in[j];
					in[j] = temp;
				}
			}
		}
		return in;
	}
	/**Performs a Selection sort on the parameter in, and returns the sorted array of int_s.
	 * */
	private static int[] customSelectionSort(int[] in)
	{
		int temp, minimumIndex;
		for (int i = 0; i < in.length-1; i++)
		{
			minimumIndex = i;
			for (int j = i+1; j < in.length; j++)
			{
				if (in[j] < in[minimumIndex])
				{
					minimumIndex = j;
				}
			}
			if (minimumIndex != i)
			{
				temp = in[i];
				in[i] = in[minimumIndex];
				in[minimumIndex] = temp;
			}
		}
		return in;
	}

}

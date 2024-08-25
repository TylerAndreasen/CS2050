package Program7;
/**Tyler Andreasen
 * Program7 - preforms an Insertion sort on a list of int_s,  * and preforms Selection, Bubble, and Insertion sorts on a
 * list String. The String list used is the same for each sort, and each sort is timed via System.nanoTime() and only
 * the sort operation is timed. Improvement over P6, moved writing timing results together and after all calculations
 * are completed, leaving the file open for a shorter duration.
 *      STRING_FILE_LOCATION
 *      and
 *      INT_FILE_LOCATION.
 *
 *      Also, I was amused by a typo earlier, ProfesSort.
 * */

import java.io.*;
import java.util.ArrayList;

public class Program7
{

	private static int programNumber = 7;
	private static ArrayList<String> preparedOut;
	private final static String INT_FILE_LOCATION = "src\\Program"+programNumber+"\\NumbersInFile.txt",
			STRING_FILE_LOCATION = "src\\Program"+programNumber+"\\StringsInFile.txt";
	private final static double BILLION = 1_000_000_000;
	private static int[]  intInsertionValues, intBubbleValues, intSelectionValues;
	public static void main(String[] args)
	{
		try
		{
			preparedOut = new ArrayList<String>();
			BufferedReader fileIn = new BufferedReader(new FileReader(new File(INT_FILE_LOCATION).getCanonicalFile()));
			ArrayList<Integer> fileLines = new ArrayList<Integer>();
			Integer lineIn = Integer.parseInt(fileIn.readLine().trim());
			while (lineIn != null)
			{
				fileLines.add(lineIn);
				try
				{
					String temp = fileIn.readLine().trim();
					lineIn = Integer.parseInt(temp);

				} catch (NullPointerException e)
				{
					break;
				}
			}
			fileIn.close();

			int totalIntegerCount = fileLines.size();

			intBubbleValues = new int[totalIntegerCount];

			for (int i = 0; i < intBubbleValues.length; i++)
			{
				intBubbleValues[i] = fileLines.get(i);
			}
			long start = System.nanoTime();
			intBubbleValues = customBubbleSort(intBubbleValues);
			double diff = (System.nanoTime()-start) / BILLION;


			//preparedOut.add("Tyler Andreasen\n");
			preparedOut.add("Bubble Sort on    "+totalIntegerCount+" Integers in: "+diff+'\n');

			intBubbleValues = null; //Array no longer needs data
			intSelectionValues = new int[totalIntegerCount];
			for (int i = 0; i < intSelectionValues.length; i++)
			{
				intSelectionValues[i] = fileLines.get(i);
			}
			start = System.nanoTime();
			intSelectionValues = customSelectionSort(intSelectionValues);
			diff = (System.nanoTime() - start) / BILLION;
			preparedOut.add("Selection Sort on "+totalIntegerCount+"Integers in:  "+diff+'\n');

			intSelectionValues = null; //Array no longer needs data

			intInsertionValues = new int[totalIntegerCount];

			for (int i = 0; i < intInsertionValues.length; i++)
			{
				intInsertionValues[i] = fileLines.get(i);
			}
			start = System.nanoTime();
			intInsertionValues = customInsertionSort(intInsertionValues);
			diff = (System.nanoTime()-start) / BILLION;


			preparedOut.add("Insertion Sort on "+totalIntegerCount+" Integers in: "+diff+'\n');

			intInsertionValues = null; //Array no longer needs data
			/*
			fileIn = new BufferedReader(new FileReader(new File(STRING_FILE_LOCATION).getCanonicalFile()));
			ArrayList<String> sFileLines = new ArrayList<String>();
			String sLineIn = fileIn.readLine().trim();
			while (sLineIn != null)
			{
				sFileLines.add(sLineIn);
				try
				{
					sLineIn = fileIn.readLine().trim();

				} catch (NullPointerException e)
				{
					break;
				}
			}
			fileIn.close();

			int totalStringCount = sFileLines.size();

			String[] stringBubbleSort = new String[totalStringCount];
			String[] stringSelectionSort = new String[totalStringCount];

			for (int i = 0; i < sFileLines.size(); i++) {
				stringBubbleSort[i] = sFileLines.get(i);
			}

			start = System.nanoTime();
			stringBubbleSort = customBubbleSortS(stringBubbleSort);
			diff = (System.nanoTime()-start) / BILLION;
			preparedOut.add("Bubble Sort on    "+totalStringCount+" Strings in:  "+diff+'\n');

			stringBubbleSort = null; //Array no longer needs data
			for (int i = 0; i < sFileLines.size(); i++) {
				stringSelectionSort[i] = sFileLines.get(i);
			}

			start = System.nanoTime();
			stringSelectionSort = customSelectionSortS(stringSelectionSort);
			diff = (System.nanoTime()-start) / BILLION;
			preparedOut.add("Selection Sort on "+totalStringCount+" Strings in:  "+diff+'\n');
			stringSelectionSort = null; //Array no longer needs data

			start = System.nanoTime();
			Collections.sort(sFileLines);
			diff = (System.nanoTime()-start) / BILLION;
			preparedOut.add("System Sort on    "+totalStringCount+" Strings in:  "+diff+'\n');
			sFileLines = null; //ArrayList no longer needs data
			*/


			BufferedWriter resultsDoc = new BufferedWriter(new FileWriter(
					new File("src\\Program"+programNumber+"\\results.txt")));
			resultsDoc.write("Tyler Andreasen\n");
			for (String line : preparedOut)
				resultsDoc.write(line);
			resultsDoc.flush();
			resultsDoc.close();

		} catch (IOException e)
		{
			System.out.println("#EXCEPTION# FILE IO Issue");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println("#EXCEPTION# Non-FILE Issue");
			e.printStackTrace();
		}
	}
	/**Preforms an Insertion sort on the parameter in, and returns the sorted array of int_s.
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
	/**Preforms a Bubble sort on the parameter in, and returns the sorted array of int_s.
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
	/**Preforms a Bubble sort on the parameter in, and returns the sorted array of String_s.
	 * */
	private static String[] customBubbleSortS(String[] in)
	{
		String temp;
		for (int i = 0; i < in.length-1; i++)
		{
			for (int j = i+1; j < in.length; j++)
			{
				boolean shouldSwap = false;
				String A = in[i], B = in[j]; //A and B are convenience names
				for (int k = 0; k < Math.min(A.length(), B.length()); k++)
				{
					char a = A.charAt(k), b = B.charAt(k);
					if (a > b)
					{
						shouldSwap = true;
						break;
					} else if (a < b)
					{
						break;
					}
				}
				if (shouldSwap)
				{
					temp = in[i];
					in[i] = in[j];
					in[j] = temp;
				}
			}
		}
		return in;
	}
	/**Preforms a Selection sort on the parameter in, and returns the sorted array of int_s.
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
	/**Preforms a Selection sort on the parameter in, and returns the sorted array of String_s.
	 * */
	private static String[] customSelectionSortS(String[] in)
	{
		String temp;
		int minIndex;
		for (int i = 0; i < in.length-1; i++)
		{
			minIndex = i;
			for (int j = i+1; j < in.length; j++)
			{
				boolean shouldSwap = false;
				int max = Math.min(in[minIndex].length(), in[j].length());
				for (int k = 0; k < max; k++)
				{
					char a = in[minIndex].charAt(k), b = in[j].charAt(k);
					if (b < a)
					{
						shouldSwap = true;
						break;
					}
					else if (b > a)
					{
						break;
					}
				}
				if (shouldSwap)
					minIndex = j;

			}
			if (minIndex != i)
			{
				temp = in[i];
				in[i] = in[minIndex];
				in[minIndex] = temp;
			}
		}
		return in;
	}
}

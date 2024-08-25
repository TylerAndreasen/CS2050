package Program6;
/**Tyler Andreasen
 * Program6 - preforms Selection and Bubble sorts on lists of String or int arrays as specified in the fields
 *      STRING_FILE_LOCATION
 *      and
 *      INT_FILE_LOCATION.
 * */
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Program6
{
    private final static String INT_FILE_LOCATION = "src\\Program6\\NumbersInFile.txt",
                                STRING_FILE_LOCATION = "src\\Program6\\StringsInFile.txt";
    private final static double BILLION = 1_000_000_000;
    private static int[] intBubbleValues, intSelectionValues;
    private static int verificationCounter = 0;
    public static void main(String[] args)
    {
        try
        {
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

            intBubbleValues = new int[fileLines.size()];

            for (int i = 0; i < intBubbleValues.length; i++)
            {
                intBubbleValues[i] = fileLines.get(i);
            }
            long start = System.nanoTime();
            intBubbleValues = customBubbleSort(intBubbleValues);
            double diff = (System.nanoTime()-start) / BILLION;

            BufferedWriter resultsDoc = new BufferedWriter(new FileWriter(new File("src\\Program6\\results.txt")));
            resultsDoc.write("Tyler Andreasen\n");
            resultsDoc.write("Bubble Integers "+diff+'\n');

            intBubbleValues = null; //Array no longer needs data
            intSelectionValues = new int[fileLines.size()];
            for (int i = 0; i < intSelectionValues.length; i++)
            {
                intSelectionValues[i] = fileLines.get(i);
            }
            fileLines = null; //Values moved into Selection array, and are no longer needed here.

            start = System.nanoTime();
            intSelectionValues = customSelectionSort(intSelectionValues);
            diff = (System.nanoTime() - start) / BILLION;
            resultsDoc.write("Selection Integers "+diff+'\n');

            intSelectionValues = null; //Array no longer needs data


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

            String[] stringBubbleSort = new String[sFileLines.size()];
            String[] stringSelectionSort = new String[sFileLines.size()];

            for (int i = 0; i < sFileLines.size(); i++) {
                stringBubbleSort[i] = sFileLines.get(i);
            }

            start = System.nanoTime();
            stringBubbleSort = customBubbleSortS(stringBubbleSort);
            diff = (System.nanoTime()-start) / BILLION;
            resultsDoc.write("Bubble Strings "+diff+'\n');

            stringBubbleSort = null; //Array no longer needs data
            for (int i = 0; i < sFileLines.size(); i++) {
                stringSelectionSort[i] = sFileLines.get(i);
            }

            start = System.nanoTime();
            stringSelectionSort = customSelectionSortS(stringSelectionSort);
            diff = (System.nanoTime()-start) / BILLION;
            resultsDoc.write("Selection Strings "+diff+'\n');
            stringSelectionSort = null; //Array no longer needs data

            start = System.nanoTime();
            Collections.sort(sFileLines);
            diff = (System.nanoTime()-start) / BILLION;
            resultsDoc.write("System Strings "+diff+'\n');
            resultsDoc.flush();
            resultsDoc.close();
            sFileLines = null; //ArrayList no longer needs data

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
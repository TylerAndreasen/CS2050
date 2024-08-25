package NonClassExperiment;

import java.io.*;

public class FileManipulator
{
    private static String fileLoc = "C:\\Users\\Tyler\\Desktop\\Senior 2\\CS1030\\Week 6\\program 1\\";

    public static void main(String[] args) throws IOException
    {
        BufferedReader fileIn = new BufferedReader(new FileReader(fileLoc+"Factorial Instructions.txt"));
        BufferedWriter fileOut = new BufferedWriter(new FileWriter(fileLoc+"fileOut.txt"));
        String lineIn= fileIn.readLine();
        int i = 0;
        while (lineIn != null)
        {
            //System.out.println(lineIn);
            fileOut.write(""+i+'\t'+lineIn+'\n');
            i++;
            lineIn= fileIn.readLine();
        }
        fileIn.close();
        fileOut.flush();
        fileOut.close();
    }
}

package Program5;

import java.io.*;

public class TestClass
{
    public static void main(String[] args) throws IOException
    {
        File me = new File("src\\Program5\\Program5.txt");
        System.out.println(me.getName());
        BufferedReader fileIn = new BufferedReader(new FileReader(me.getCanonicalFile()));
        String lineIn = fileIn.readLine();
        while (lineIn != null)
        {
            System.out.println(lineIn);
            lineIn = fileIn.readLine();
        }
    }
}

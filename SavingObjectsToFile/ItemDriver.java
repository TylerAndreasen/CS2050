package SavingObjectsToFile;

import java.io.*;

public class ItemDriver
{
    public static void main(String[] args) throws IOException
    {
        File fileOut = new File("src\\SavingObjectsToFile\\savedItemFile.txt");
        Item[] three = new Item[]
                {
                        new Item(2,200,"Watch"),
                        new Item(1,1,"Pen"),
                        new Item(55,-1,"Emotional Damage")
                };
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileOut));
        for (int i = 0; i < three.length; i++)
        oos.writeObject(three[i]);

        oos.close();
    }
}

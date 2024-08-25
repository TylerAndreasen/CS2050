package SavingObjectsToFile;

import java.io.Serializable;

public class Item implements Serializable
{
    private int wieght, value;
    private String displayName, id;
    private static int idCounter = 0;

    Item(int wieght, int value, String name)
    {
        this.wieght = wieght;
        this.id = "item"+idCounter;
        idCounter++;
        this.value = value;
        this.displayName = name;
    }
}

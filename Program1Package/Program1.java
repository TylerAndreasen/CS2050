package Program1Package;

import java.util.Scanner;

public class Program1
{
    static Scanner userIn = new Scanner(System.in);
    private static final char[] DIGITS_AS_CHARS = {'0','1','2','3','4','5','6','7','8','9',};
    public static void main(String[] args)
    {
        Album[] userGenAlbums = new Album[3];
        for (int i = 0; i < userGenAlbums.length; i++)
        {
            userGenAlbums[i] = new Album();
        }
        System.out.println(
                "Greetings and Salutations User :[REDACTED]:! You have launched this program to store and access information about music albums."
                +"\nBecause of budget cuts, we are only authorized to allow three albums per user to be stored."
                +"\nNote also the below list of currently allowed genres (use the closest descriptor for your desired album).\n");
        StringBuilder allFlagsNavy = new StringBuilder("Available Genres: ");
            //Elder Scrolls reference - All Flags Navy. Link if curious: https://en.uesp.net/wiki/Lore:All_Flags_Navy
        for (String line : Album.validGenres)
        {
            allFlagsNavy.append(line).append(", "); //Thanks to IntelliJ for suggesting a String Builder
        }
        System.out.println(allFlagsNavy.substring(0, allFlagsNavy.length() - 2) + ".");
        allFlagsNavy = null;
        int albumCounter = 0;
        while (albumCounter < 3)
        {
            System.out.println("What is the title of Program1Package.Album["+(albumCounter+1)+"]? ");
            userGenAlbums[albumCounter].setTitle(getInput());

            System.out.println("Who is the performer of Program1Package.Album["+(albumCounter+1)+"]? ");
            userGenAlbums[albumCounter].setPerformer(getInput());

            System.out.println("What is the genre of Program1Package.Album["+(albumCounter+1)+"]? ");
            userGenAlbums[albumCounter].setGenre(getInput().toLowerCase()); //Normalize to ensure matching with valid genres

            System.out.println("How many songs are present on Program1Package.Album["+(albumCounter+1)+"]? (Minimum 10 is enforced) ");
            userGenAlbums[albumCounter].setCount(parseInt(getInput()));
            albumCounter++; //DO NOT DELETE
        }
        userIn.close(); //DO NOT DELETE
        for (Album a : userGenAlbums) a.toString(false); //Boolean used to fingerprint from the String return.
    }
    private static String getInput()
    {
        try
        {
            return userIn.nextLine().trim();
        } catch (Exception e)
        {
            return "";
        }
    }

    private static int parseInt(String in)
    {
        String calc = "";
        char[] CHARS = in.toCharArray();
        for (char c : CHARS)
        {
            if (contains(DIGITS_AS_CHARS, c)) calc += c; //Cull all non-digit chars from the input.
        }
        if (calc.length() == 0) calc = "-1";
        return Integer.parseInt(calc);
    }
    public static int contains(String[] bank, String key)
    {
        for (int i = 0; i < bank.length; i++)
        {
            if (bank[i].equals(key)) return i;
        }
        return -1;
    }
    public static boolean contains(char[] bank, char key)
    {
        for (char c : bank) if (c == key) return true;
        return false;
    }
}

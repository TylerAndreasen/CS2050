package Lab1Package;

public class FindForHypot42
{
    public static void main(String[] args)
    {
        //System.out.println(42*42);
        int target = 1764, max = 42;
        int[] squares = new int[42];
        for (int i = 0; i < max; i++)
        {
            squares[i] = (i+1)*(i+1);
        }
        for (int i = 1; i < max; i++)
        {
            for (int j = 0; j < max; j++)
            {
                int sum = squares[i]+squares[j];
                if (sum == target)
                {
                    System.out.println("Found Target :"+target+": with sides :"+i+":"+j+": ");
                    return;
                }
            }
        }
        System.out.println("No Value Found :: No integer solution");
    }
}

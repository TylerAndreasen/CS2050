package Program3_DONOTUSE;

public class StressTester
{
    public static void main(String[] args)
    {
        ArrayStackClass stack = new ArrayStackClass(3);
        System.out.println(stack.size()+", "+stack.empty());

        stack.devShow();

        for (int i = 0; i < 10; i++)
        {
            char val = Integer.toString(i).charAt(0);
            stack.push(val);
            System.out.println("Pushed "+val+", "+i);
        }

        stack.devShow();
        String concat = "";
        try {
            for (int i = 0; i < 15; i++) {
                concat += stack.pop();
            }
        } catch (Exception e)
        {
            System.out.println(concat);
        }

        stack.devShow();
    }
}

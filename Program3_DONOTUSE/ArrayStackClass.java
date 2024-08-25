package Program3_DONOTUSE;
/**Tyler Andreasen
 * Program3.ArrayStackClass
 * ArrayStackClass is an implementation of the Stack ADT using a Java Array of the Type char, initially for the purpose
 * of building an infix to postfix notation program.
 * */
public class ArrayStackClass
{

    private char[] stack;
    private int n;

    ArrayStackClass() {this(10);}
    ArrayStackClass(int i)
    {
        stack = new char[i];
        n = 0;
    }

    public void devShow()
    {
        System.out.println("Current n = "+n);
        System.out.println("Current array size = "+stack.length);
        for (int i = 0; i < stack.length; i++)
        {
            System.out.println("Char["+i+"] :"+stack[i]);
        }
    }

    /**Returns the index of the last used element in the stack's array.
     * */
    public int size()
    {
        return n;
    }
    /**Returns true if the stack is empty, and false if not.
     * */
    public boolean empty()
    {
        return n > 0;
    }

    /**Pushes the parameter {@code c} onto the stack.
     * */
    public void push(char c)
    {
        if (n >= stack.length) resize();
        stack[n] = c;
        n += 1;

    }
    /**Method returns the top element of the stack and decrements the top of the stack.
     * Some consideration should be made about voiding the removed element in the stack.
     * */
    public char pop()
    {
        if (n == 0) return 0;
        char output = stack[n];
        if (stack.length >= 3*n) resize();
        n--;
        return output;
    }

    /**Returns the top element in the stack without modifying the internal stack top pointer nor voiding the top element.
     * */
    public char peek()
    {
        return stack[n];
    }

    /**Method reallocates the memory necessary to store the object's Stack Array.
     * This method should not be called unless the need to resize the array is established,
     * IE: too small for n+1 on push() * or to large on pop() when n ~= 1/3 of array.length).
     * */
    private void resize()
    {
        System.out.println("Calling Resize :"+stack.length);
        char[] S = new char[Math.max(n*2,1)];
        if (n >= 0) System.arraycopy(stack, 0, S, 0, n);
        stack = S;
        System.out.println("Finished Resizing :"+stack.length);
    }
}

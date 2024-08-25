package Program3;
/**Tyler Andreasen
 * An implementation of a Stack relying on the native Java Array, and in this implementation of char_s. (Joke
 * incoming) And let me tell you, it is a Stack implementation of all time.
 * */
public class ArrayStackClass
{
    private int maxSize;
    int top;
    private char[] items;

    ArrayStackClass(int i)
    {
        this.items = new char[i];
        this.maxSize = items.length;
        this.top = 0;
    }

    /**Returns the 1-based number of items in the stack, not the underlying array.
     * */
    public int size()
    {
        return this.top;
    }

    /*
    * Debug Tool, not necessary for processing data, method name
    public void devShow()
    {
        System.out.println("Stack Top :"+this.top+":, Array Size :"+this.items.length+":, this.size() :"+this.size()+":.");
        for (int i = 0; i < this.items.length; i++)
        {
            System.out.println("Character["+i+"] - :"+this.items[i]+":.");
        }
    }
    */


    /**Method adds the input element to the top of the stack.
     * Ensures the array is not full, then assigns the top value,
     * then increments the top to element above.
     * */
    public void push(char c)
    {
        if (this.top == this.maxSize) resize();
        this.items[this.top] = c;
        this.top += 1;
    }

    public char pop()
    {
        if (this.top == 0)
        {
            return 0;
            //throw new ArrayIndexOutOfBoundsException("Stack Contains no items to pop.");
        }
        else
        {
            this.top -= 1;
            char out = this.items[this.top];
            //Test to see if making the popped() value null causes problems.
            this.items[this.top] = 0;
            return out;
        }
    }

    public char peek()
    {
        if (this.top == 0) return 0;
        //Do not alter any values, as this is only a peek.
        return this.items[top];
    }
    /**Method returns whether the stack is empty.
     * Should I include a debug method which exuhastively searches the array to ensure there are no lingering values?
     * */
    public boolean empty()
    {
        return this.top == 0;
        /*
        for (int i = this.items.length-1; i > -1; i--)
        {
            if (this.items[i] != 0) return false;
        }
        return true;
        */
    }

    private void resize()
    {
        //System.out.println("Calling Resize :"+items.length);
        char[] S = new char[Math.max(top*2,1)];
        if (top >= 0) System.arraycopy(items, 0, S, 0, top);
        this.items = S;
        this.maxSize = items.length;
        //System.out.println("Finished Resizing :"+items.length);
    }
}

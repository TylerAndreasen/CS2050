package Program3_DONOTUSE;

public class ArrayStackClass2
{
    private char[] stack;
    private int n;

    ArrayStackClass2(int i)
    {
        stack = new char[i];
        n = 0;
    }

    public int size() { return n; }

    public void devShow()
    {
        System.out.println("DevShow - Array Size :"+this.stack.length+":, N-Value :"+this.n+":");
        String temp = "Listed Values - ";
        for (int i = 0; i < stack.length; i++)
        {
            temp += ":"+stack[i]+":, ";
        }
        System.out.println(temp);
    }

    private char get(int i) { return stack[i]; }

    public char peek()
    {
        return get(n);
    }
    public char pop()
    {
        char temp = this.get(n);
        stack[n] = 0;
        n--;
        return temp;
    }

    private char set(int i, char c)
    {
        char replaced = stack[i];
        stack[i] = c;
        return replaced;
    }

    public void push(char c)
    {
        this.add(n,c);
    }

    private void add(int i, char c)
    {
        if (n+1 > stack.length) resize();
        for (int j = n; j > i; j--)
            stack[j] = stack[j-1];
        stack[i] = c;
        n++;
    }
    private char remove(int i)
    {
        char output = stack[i];
        for (int j = i; j < n-1; j++)
            stack[j] = stack[j+1];
        n--;
        if (stack.length >= 3*n) resize();
        return output;
    }
    private void resize()
    {
        System.out.println("Calling Resize :"+stack.length);
        char[] b = new char[Math.max(n*2,1)];
        if (n >= 0) System.arraycopy(stack, 0, b, 0, n);
        stack = b;
        System.out.println("Finished Resizing :"+stack.length);
    }
}

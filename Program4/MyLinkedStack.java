package Program4;

public class MyLinkedStack
{
    Node node0;

    MyLinkedStack(int unused) {}

    public void push(char c)
    {
        Node toPush = new Node(c);
        toPush.setNext(node0);
        node0 = toPush;
    }

    /**Verification of the correctness of this method is HIGHLY IMPORTANT
     * */
    public char pop()
    {

        if (testNode0Null()) throw new ArrayIndexOutOfBoundsException("Stack Contains no items to pop.");
        char output = node0.getValue();
        Node temp = node0;
        node0 = node0.getNext();
        return output;
    }

    public char peek()
    {
        if (testNode0Null()) return 0;
        return node0.getValue();
    }

    public int size()
    {
        if (node0.getValue() == 0)
            return 0;
        else
        {
            int output = 1;
            Node test = node0.getNext();
            while (test != null)
            {
                output++;
                test = test.getNext();
            }
            return output;
        }
    }

    public boolean empty()
    {
        if (testNode0Null()) return true;
        return this.node0.getValue() == 0;
    }
    private boolean testNode0Null()
    {
        return this.node0 == null;
    }
}

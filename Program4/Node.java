package Program4;

public class Node
{
    private char datum;
    private Node next = null;

    Node(char c) { this.datum = c; }

    public char getValue() { return this.datum; }
    public void setNext(Node n) { this.next = n; }
    public Node getNext() { return this.next;}
}

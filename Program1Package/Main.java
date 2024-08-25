package Program1Package;

class Main
{
    public static void main(String[] args)
    {
        System.out.println(factorial(70));
    }
    private static long factorial (long n)
    {
        if (n < 0) return -1;
        if (n == 1) return 1;
        if (n == 0) return 1;
        return n*factorial(n-1);
    }


}
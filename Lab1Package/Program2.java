package Lab1Package;
/*
 * Tyler Andreasen:
 * Description: An assigned program to ensure developer's familiarity with the tool JUnit.
 * Implements methods (add, subtract, multiply, divide, sineOfAngle, hypOfTriangle) taking and returning double_s.
 */

public class Program2 {

    /**
     * Expects two values of type double, and returns the sum.
     * Note: no overflow protection, however, all tests are passed.
     * */
    public static double add(double a, double b)
    {
        return (double) a + b;
    }

    /**
     * Expects two values of type double, and returns the difference.
     * Note: no underflow protection, however, all tests are passed.
     * */
    public static double subtract(double a, double b)
    {
        return (double) a - b;
    }

    /**
     * Expects two values of type double, and returns the product.
     * Note: no overflow protection, however, all tests are passed.
     * */
    public static double multiply(double a, double b)
    {
        boolean aN = false, bN = false;
        if (a < 0d)
        {
            aN = true;
            a *= -1;
        } else if (a == 0d) return 0d;
        if (b < 0d)
        {
            bN = true;
            b *= -1;
        } else if (b == 0d) return 0d;
        return (a * b * (aN ^ bN ? -1 : 1));
    }

    /**
     * Expects two values of type double, and returns the quotient.
     * @throws ArithmeticException if second parameter is 0.
     * */
    public static double divide(double a, double b)
    {
        if (b == 0.0d) throw new ArithmeticException("#ERROR# Program2.divide(double :"+a+":, double :"+b+":) - Attempted " +
                "to divide by 0. #");
        if (a == 0.0d) return 0.0d;
        boolean aN = false, bN = false;
        if (a < 0)
        {
            aN = true;
            a *= -1;
        }
        if (b < 0)
        {
            bN = true;
            b *= -1;
        }
        return ((a / b) * (aN ^ bN ? -1 : 1));
    }

    /**
     * Returns the sine of the angle implied by (ordered) the opposite side and the hypotenuse of a triangle,
     * passed as arguments to the method.
     * @throws ArithmeticException if either parameter is 0.
     * Note: No under or overflow protection, all tests are passed.
     * Previously called sineOfAngle(double, double), changed to sinOfAngle(double, double) to match test suite.
     * */
    public static double sinOfAngle(double oppSide, double hyp)
    {
        if (hyp == 0.0d) throw new ArithmeticException("#ERROR# Program2.sinOfAngle(double :"+oppSide+":, double" +
                ":"+hyp+":) - Attempted to calculate sine of an angle with 0 length hypotenuse. #");
        if (oppSide == 0.0d) throw new ArithmeticException("#ERROR# Program2.sinOfAngle(double :"+oppSide+":, double" +
                ":"+hyp+":) - Attempted to calculate sine of an angle with 0 length opposite side. #");
        return truncate(divide(oppSide, hyp), 2);
    }

    /**
     * Expects two doubles, interpreted as the side lengths of a right triangle, and returns the length of the hypotenuse.
     * */
    public static double hypOfTriangle(double sideA, double sideB)
    {
        //System.out.println("#DEBUG# Program2.hypOfTriangle(:"+sideA+":, :"+sideB+":)");
        if (sideA <= 0) throw new ArithmeticException("#ERROR# Program2.hypOfTriangle(double :"+sideA+":, double" +
                ":"+sideB+":) - Attempted to calculate a hypotenuse  of a triangle with 0 length side. #");
        if (sideB <= 0) throw new ArithmeticException("#ERROR# Program2.hypOfTriangle(double :"+sideA+":, double" +
                ":"+sideB+":) - Attempted to calculate a hypotenuse  of a triangle with 0 length side. #");
        return truncate(Math.pow((Math.pow(sideA, 2) + Math.pow(sideB, 2)), 0.5d), 2);
    }
    /**
     * Expects a double value to be truncated and an integer which determines the number of decimal digits to maintain.
     * Ex: truncate( double :3.14159268:, int :3: ) returns :3.141:
     *
     * */
    private static double truncate(double value, int precision)
    {
        if (precision == 0) return Math.round(value);
        if (value == 0d) return 0d;
        double n = Math.pow(10, precision);
        return Math.round(value * n) / n;
    }

    /**
     * Main : Prints a greeting message to Professor Cohen and runs a test on the methods add, subtract, multiply,
     * divide, sinOfAngle, and hypOfTriangle, and prints the input values and the result of each method.
     * */
    public static void main(String[] args)
    {
        System.out.println(
                "Good [TIME_OF_DAY] Professor Cohen.\n" +
                "I recalled you saying that there should be some code in main,\n" +
                "so I threw in some examples of the implemented methods in this program.\n\n"
        );
        //(add, subtract, multiply, divide, sinOfAngle, hypOfTriangle)
        double inputA = 42, inputB = 52.7;
        System.out.println("\tadd(:"+inputA+":, :"+inputB+":) ~= "+add(inputA, inputB));
        System.out.println("\tsubtract(:"+inputA+":, :"+inputB+":) ~= "+subtract(inputA, inputB));
        System.out.println("\tmultiply(:"+inputA+":, :"+inputB+":) ~= "+multiply(inputA, inputB));
        System.out.println("\tdivide(:"+inputA+":, :"+inputB+":) ~= "+divide(inputA, inputB));
        System.out.println("\tsinOfAngle(:"+inputA+":, :"+inputB+":) ~= "+sinOfAngle(inputA, inputB));
        System.out.println("\thypOfTriangle(:"+inputA+":, :"+inputB+":) ~= "+hypOfTriangle(inputA, inputB));
    }
}

package VisualCalculator;

import java.util.Scanner;

/**Depreciated: This was perhaps a poor choice of technique.
 *
 */
public class ConsoleCalculator
{
	private static final int INVALID_OPERATOR_VALUE = 6;
	private static boolean
			specialOperation = false,
			restartLoop = false,
			quitFlag = false,
			helpFlag = false,
			tripleInput = false;
					;
	private static Scanner userIn = new Scanner(System.in);
	private static String specialOperators = "[^%]", allOperators = "+-*/^%";

	private static int operatorType = INVALID_OPERATOR_VALUE; // 0+, 1-, 2*, 3/, 4^, 5%,
	private static float firstValue = 0f, secondValue = 0f;

	public static void main(String[] args)
	{

		System.out.println("Greetings and Welcome to a console calculator.");
		System.out.println("You can learn all about the calculation options you have by using (h),\n or exit with (q)");
		System.out.println("If you are ready, input a value.");

		boolean dontQuit = true;
		calculatorLoop: while (dontQuit)
		{
			specialOperation = false; restartLoop = false; helpFlag = false;
			firstValue = Float.MIN_VALUE; secondValue = Float.MIN_VALUE; operatorType = INVALID_OPERATOR_VALUE;
			System.out.println("Ready for first Input");
			firstValue = getInput();
			if (quitFlag) return;
			if (restartLoop) continue calculatorLoop;
			if (helpFlag)
			{
				System.out.println(
						"There are a couple of ways to input your desired calculations." +
								"You can enter a value, then an operator, then another value." +
								"You can place all three into one line with a space between each." +
								"Or you can use on of the special operators." +
								"\tThe '^' (carrot) operator will indicate you wish to an number to the power of another number (in the input order)." +
								"\tThe '%' (modulo) operator will return the remainder after division of the two input values."+
								"The operators +, -, *, and / or \\ function as expected, and can be inserted between two values." +
								"Note: neither of the valued you input can be the minimum as defined by the Java Float class."
				);
				continue calculatorLoop;
			}
			if (specialOperation)
			{
				if (operatorType < 4 || operatorType > 5)
				{
					System.out.println("Special Operator Type Error, Restarting.");
					continue calculatorLoop;
				} else {
					System.out.println("Insert first value for Special Operation :" + allOperators.charAt(operatorType) + ": ");
					firstValue = getInput();
					if (quitFlag) return;
					if (restartLoop || helpFlag) continue calculatorLoop;


					System.out.println("Insert second value for Special Operation :" + allOperators.charAt(operatorType) + ": ");
					secondValue = getInput();
					if (quitFlag) return;
					if (restartLoop || helpFlag) continue calculatorLoop;

					if (operatorType == 4) //^
					{
						System.out.println("Calculated :"+firstValue+" ^ "+secondValue+": = :"+Math.pow(firstValue,secondValue)+":.");
					}
					if (operatorType == 5) //%
					{
						System.out.println("Calculated :"+firstValue+" % "+secondValue+": = :"+(firstValue%secondValue)+":.");
					}
				}
			} else if (firstValue != Float.MIN_VALUE && operatorType != INVALID_OPERATOR_VALUE && secondValue != Float.MIN_VALUE)
			{
				float result;
				switch (operatorType)
				{
					case 0:
						result = firstValue + secondValue;
						break;
					case 1:
						result = firstValue - secondValue;
						break;
					case 2:
						result = firstValue * secondValue;
						break;
					case 3:
						if (secondValue == 0f)
						{
							System.out.println("Cannot Divide by 0.");
							continue calculatorLoop;
						}
						result = firstValue / secondValue;
						break;
					default:
						result = Float.MIN_VALUE;
						break;
				}
				if (result == Float.MIN_VALUE)
				{
					System.out.println("Intended Operator not found :"+operatorType+":.");
					continue calculatorLoop;
				} else
				{
					System.out.println("Calculated :"+firstValue+" "+allOperators.charAt(operatorType)+" "+secondValue+": = :"+result+": ");
				}
			}
		}
	}

	private static float getInput()
	{
		String lineIn = userIn.nextLine().trim();
		String[] words = lineIn.split("[ \t]");

		if (words.length == 0)
		{
			restartLoop = true;
			System.out.println("No input detected, please try again.");
			return 0f;
		}

		if (words.length == 1)
		{
			if (words[0].matches(specialOperators))
			{
				if (specialOperation)
				{
					restartLoop = true;
					System.out.println("Already attempting a special operation. Please try again.");
					return 0f;
				} else
				{
					if (words[0].charAt(0) == '^') operatorType = 4;
					if (words[0].charAt(0) == '%') operatorType = 5;
					else operatorType = INVALID_OPERATOR_VALUE;

					if (operatorType != INVALID_OPERATOR_VALUE)
					{
						specialOperation = true;
						System.out.println("Beginning Special Operator Routine.");
						return 0f;
					} else
					{
						restartLoop = true;
						System.out.println("Special Operator Error: found but invalid. Restarting.");
						return 0f;
					}
				}
			} else if (words[0].equalsIgnoreCase("q") || words[0].equalsIgnoreCase("quit"))
			{
				quitFlag = true;
				System.out.println("Goodbye.");
				return 0f;
			} else if (words[0].equalsIgnoreCase("h") || words[0].equalsIgnoreCase("help"))
			{
				helpFlag = true;
				System.out.println("This is Life-Alert, help is on the way.");
				return 0f;
			} else
			{
				int outputOperator = parseOperator(words[0]);
				float outputValue = parseFloat(words[0]);
				if (restartLoop) { return 0f; }
				if (outputOperator == INVALID_OPERATOR_VALUE)
					return outputValue;
				else
				{
					operatorType = outputOperator;
					return 0f;
				}
			}
		}

		//Case for 2 will be annoying and handled later

		else if (words.length == 3)
		{
			float 	potentialFirst = parseFloat(words[0]),
					potentialSecond = parseFloat(words[2]);
			int operator = parseOperator(words[1]);
			if (restartLoop)
			{
				return 0f;
			}
			secondValue = potentialSecond;
			operatorType = operator;
			return potentialFirst;

		} else {
			restartLoop = true;
			System.out.println("Input not understood, please try again.");
			return 0f;
		}
	}

	private static float parseFloat(String lineIn)
	{
		float output = 0f;
		try
		{
			output = Float.parseFloat(lineIn);
		} catch (Exception e)
		{
			restartLoop = true;
			System.out.println("Number Not understood.");
		}
		return output;
	}

	private static int parseOperator(String lineIn)
	{
		if (lineIn.matches(specialOperators))
		{
			restartLoop = true;
			System.out.println("Cannot insert special operations into standard calculations.");
			return INVALID_OPERATOR_VALUE;
		}
		char op = lineIn.trim().charAt(0); // Hopefully the operator
		switch (op)
		{
			case '+':
				return 0;
			case '-':
				return 1;
			case '*':
				return 2;
			case '/', '\\':
				return 3;
			default:
				return INVALID_OPERATOR_VALUE;
		}
	}

}

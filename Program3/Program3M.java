package Program3;

public class Program3M
{

    public static void main(String[] args)
    {
        System.out.println(InfixToPostfix("4 + 6 * 3.1"));
        //("+++++"));//
    }

    public static String InfixToPostfix(String input)
    {
        int minSize = 5;
        //ArrayStackClass operandStack = new ArrayStackClass(minSize);
        String output = "";
        ArrayStackClass operatorStack = new ArrayStackClass(minSize);
        boolean openParenthesisFlag = false;
        //1. Scan infix expression from left to right.
        parseInputChars: for (int i = 0; i < input.length(); i++)
        {
            //2. If there is a character as operand, output it.
            char c = input.charAt(i);
            System.out.println("Parsing Character :"+c+":.");
            if (checkOperand(c))
            {
                output += (c);
                continue parseInputChars;
            }
            //3. if not "If the character is an operator"
            else if (checkOperator(c)) //High Precedence operators, only time it may be necessary to pop values off operatorStack.
            {
                char topOperator = operatorStack.peek();
                //3.1. If the precedence of the scanned operator is greater than the
                //precedence of the operator in the stack(or the stack is empty or the
                //stack contains a ‘(‘ ), push it.
                if ((checkHigherPrecedence(c, topOperator)) || operatorStack.empty() || openParenthesisFlag)
                {
                    operatorStack.push(c);
                }
                //3.2. Else, Pop all the operators from the stack which are greater than or
                //equal to in precedence than that of the scanned operator. After doing
                //that Push the scanned operator to the stack. (If you encounter
                //parenthesis while popping then stop there and push the scanned
                //operator in the stack.)
                else
                {
                    /*
                     * Current Precedence <= top Precedence
                     * */
                    moveOperatorsToOutput: do
                    {
                        if (operatorStack.empty())
                        {
                            break moveOperatorsToOutput; //Will push c.
                        }
                        char poppedOperator = operatorStack.pop();
                        if (isParenthesis(poppedOperator))
                        {
                            operatorStack.push(c);
                            //Does the current operator go to the operand or operator stack?
                            continue parseInputChars;
                        }
                        if (checkHigherPrecedence(c,poppedOperator))
                        {
                            break moveOperatorsToOutput; //Will push c.
                        }
                        output += ' '+(poppedOperator)+' ';
                    } while(true); //Self breaking
                    operatorStack.push(c); // I believe this should be the operator stack regardless of the do-while,
                    //no need for a flag to control this as the parenthesis case continues the main char loop.
                }
            }
            //X. If the scanned character is either parenthesis
            else if (isParenthesis(c))
            {
                //4. If the scanned character is an ‘(‘, push it to the stack.
                if (c == 40)
                {
                    operatorStack.push(c);
                }
                //5. If the character is an ‘)’, pop the stack and output it until a ‘(‘ is
                //encountered, and discard both the parenthesis.
                else //c == 41 aka ')'
                {
                    parenthesisLoop: do
                    {
                        char topOperator = operatorStack.pop();
                        if (topOperator == 40) //Found '('
                        {
                            continue parseInputChars;
                        }
                        output += ' '+(topOperator)+' ';
                    } while(true); //Self breaking
                }
            }
            else if (c == 32) continue parseInputChars;
            else //Character is not a valid operand, operator, parenthesis, .
            {
                return "#ERROR# Program3.InfixToPostFix(input :"+input+":) Illegal Character :"+c+":. #";
            }
        }
        /*
        System.out.println("TTT");
        operatorStack.devShow();
        System.out.println("DDD");
        System.out.println(output);
        if (!operatorStack.empty())
        {
            System.out.println("Operator Stack current size :"+operatorStack.size());
        }
        */
        for (int i = 0; i < operatorStack.size(); i++)
        {
            output += ' '+(operatorStack.pop())+' ';
        }
        /*
        System.out.println("TTT");
        operatorStack.devShow();
        System.out.println("DDD");
        System.out.println(output);
        */
        return("Input :"+input+":, Output :"+output+":");
    }

    private static boolean checkHigherPrecedence(char a, char b)
    {
        return (checkHighOperator(a) && checkLowOperator(b));
    }
    private static boolean checkLowerPrecedence(char a, char b)
    {
        return (checkLowOperator(a) && checkHighOperator(b));
    }
    private static boolean checkEqualPrecedence(char a, char b)
    {
        return (checkHighOperator(a) && checkHighOperator(b)) || (checkLowOperator(a) && checkLowOperator(b));
    }

    private static boolean checkOperand(char c)
    {
        if ((c > 47 & c < 58) || c == 46) return true; //Digit Characters or '.' ' '
        return false;
    }
    private static boolean checkOperator(char c)
    {
        return checkLowOperator(c) || checkHighOperator(c);
    }
    private static boolean checkHighOperator(char c)
    {
        return (c == 37 || c == 42 || c == 47); // '%', '*', '/'
    }
    private static boolean checkLowOperator(char c)
    {
        return (c == 43 || c == 45); // '+', '-'
    }
    private static boolean isParenthesis(char c)
    {
        return (c == 40 || c == 41);
    }
}

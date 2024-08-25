package Program3;
/**Tyler Andreasen
 * Program3 is the driver for an infix to postfix notation converter.
 * IMPORTANT::: I am using the absolute path of my .txt and .out files, Line 12, and will near certainly require editing
 * for your system. I know there is a way to do relative file paths, though I cannot seem to get it to function, and you
 * may also wish one both files to be elsewhere relative to the source.
 * Note: The final output line begins "4 2 9.2...", while it should be "42 9.2...", I cannot determine the algorithm
 * changes necessary to fix this, barring moving to String_s.
 * */
import java.io.*;

public class Program3
{                                                //Unsure
    private static final String FILE_LOCATION = "C:\\Users\\Tyler\\IdeaProjects\\CS2050MegaProject\\src\\Program3\\Program3.";

    public static void main(String[] args) throws IOException
    {

        BufferedReader fileIn = new BufferedReader(new FileReader(FILE_LOCATION+"txt"));
        BufferedWriter fileOut = new BufferedWriter(new FileWriter(FILE_LOCATION+"out"));
        String lineIn;
        fileOut.write("Tyler Andreasen\n");
        while ((lineIn = fileIn.readLine()) != null)
        {
            lineIn = lineIn.trim();
            //System.out.println("Input:"+lineIn+":");
            String postFix = InfixToPostFix(lineIn);
            fileOut.write(postFix+"\n");
            //System.out.printf("Output - :%s: -> :%s:\n",lineIn, postFix);
            //break;
        }
        fileIn.close();
        fileOut.flush();
        fileOut.close();
        System.out.println("Execution of Program 3 Complete.");

        //System.out.println(InfixToPostfix("4 + 6 * 3.1"));
        //("+++++"));//
    }

    public static String InfixToPostFix(String input)
    {
        int minSize = 5;
        ArrayStackClass operandStack = new ArrayStackClass(minSize);
        ArrayStackClass operatorStack = new ArrayStackClass(minSize);
        boolean openParenthesisFlag = false;
        //1. Scan infix expression from left to right.
        parseInputChars: for (int i = 0; i < input.length(); i++)
        {
            /*System.out.println("OPERATORS");
            operatorStack.devShow();
            System.out.println("OPERANDS");
            operandStack.devShow();*/
            //2. If there is a character as operand, output it.
            char c = input.charAt(i);
            //System.out.println("Parsing Character :"+c+":.");
            if (checkOperand(c))
            {
                operandStack.push(c);
                continue parseInputChars;
            }
            //3. if not "If the character is an operator"
            else if (checkOperator(c)) //High Precedence operators, only time it may be necessary to pop values off operatorStack.
            {
                //System.out.println("parsing operator :"+c+":.");
                char topOperator = operatorStack.peek();
                //3.1. If the precedence of the scanned operator is greater than the
                //precedence of the operator in the stack(or the stack is empty or the
                //stack contains a ‘(‘ ), push it.

                boolean precedenceFlag = checkHigherPrecedence(c, topOperator);
                boolean emptyFlag = operatorStack.empty();

                if ((precedenceFlag) || emptyFlag || openParenthesisFlag)
                {
                    /*if (precedenceFlag) System.out.println("Operator :"+c+": has higher precedence than :"+topOperator+":.");
                    if (emptyFlag) System.out.println("Pushing Operator To Empty OpStack");
                    if (openParenthesisFlag) System.out.println("Pushing Operator to stack inside parenthesis");*/
                    operatorStack.push(c);
                }
                //3.2. Else, Pop all the operators from the stack which are greater than or
                //equal to in precedence than that of the scanned operator. After doing
                //that Push the scanned operator to the stack. (If you encounter
                //parenthesis while popping then stop there and push the scanned
                //operator in the stack.)
                else
                {
                    //System.out.println("Begin Moving Operators to output");
                    /*
                    * Current Precedence <= top Precedence
                    * */
                    moveOperatorsToOutput: do
                    {
                        if (operatorStack.empty())
                        {
                            //System.out.println("Operator Stack Empty, break and push current operator");
                            break moveOperatorsToOutput; //Will push c.
                        }
                        char poppedOperator = operatorStack.pop();
                        //System.out.println("#DEBUG# operatorStack.pop() :"+poppedOperator+":.");
                        if (isParenthesis(poppedOperator))
                        {
                            //System.out.println("Popped Parenthesis, push operator to dStack and parse next char.");
                            operatorStack.push(c);
                            //Does the current operator go to the operand or operator stack?
                            continue parseInputChars;
                        }
                        if (checkHigherPrecedence(c,poppedOperator))
                        {
                            //System.out.println("Current Op :"+c+":, of higher precedence than popped :"+poppedOperator+":.");
                            operatorStack.push(poppedOperator);
                            break moveOperatorsToOutput; //Will push c.
                        }
                        operandStack.push(poppedOperator);
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
                    openParenthesisFlag = true;
                    operatorStack.push(c);
                }
                //5. If the character is an ‘)’, pop the stack and output it until a ‘(‘ is
                //encountered, and discard both the parenthesis.
                else //c == 41 aka ')'
                {
                    if (!openParenthesisFlag) return input+" unmatched parens";//, found close first";
                    parenthesisLoop: do
                    {
                        if (operatorStack.empty())
                        {
                            openParenthesisFlag = false;
                            break parenthesisLoop;
                        }
                        char topOperator = operatorStack.pop();
                        if (topOperator == 40) //Found '('
                        {
                            openParenthesisFlag = false;
                            continue parseInputChars;
                        }
                        operandStack.push(topOperator);
                    } while(true); //Self breaking
                }
            }
            else if (c == 32) continue parseInputChars;
            else //Character is not a valid operand, operator, parenthesis, .
            {
                return input+" invalid character";//"#ERROR# Program3.InfixToPostFix(input :"+input+":) Illegal Character :"+c+":. #";
            }
        }
        if (openParenthesisFlag)
        {
            return input+" unmatched parens";//, no close";//"#ERROR# Program3.InfixToPostFix(input :"+input+":) Mismatched Parentatheses. #";
        }
//        System.out.println("OPERATORS");
//        operatorStack.devShow();
//        System.out.println("OPERANDS");
//        operandStack.devShow();
        /*if (!operatorStack.empty())
        {
            System.out.println("Operator Stack current size :"+operatorStack.size());
        }*/
        int charsToPop = operatorStack.size();
        //System.out.println("Operators to Pop:"+charsToPop);
        for (int i = 0; i < charsToPop; i++)
        {
            operandStack.push(operatorStack.pop());
        }
        String temp = "";
        charsToPop = operandStack.size();
        //System.out.println("Chars to pop :"+charsToPop);
        for (int i = 0; i < charsToPop; i++)
        {
            char t = operandStack.pop();
            //System.out.println("Pushing Value :"+t);
            temp = t+" "+temp;
        }
        /*System.out.println("OPERATORS");
        operatorStack.devShow();
        System.out.println("OPERANDS");
        operandStack.devShow();*/

        //System.out.println("Preprocessed Output :"+temp+":.");
        temp = postFixOutputFixer(temp);
        //System.out.println("Spaces better? :"+temp+":.");

        //return("Input :"+input+":, Output :"+temp+":");

        operatorStack = null;
        operandStack = null;
        return temp;
    }

    private static String postFixOutputFixer(String in)
    {
        StringBuilder out = new StringBuilder(1);
        //Push first char ensuring that checks are not done at a negative index.
        out.append(in.charAt(0));
        for (int i = 1; i < in.length()-1; i++)
        {
            if (in.charAt(i) == ' ')
            {
                if ((in.charAt(i-1) == '.') || (in.charAt(i+1) == '.'))
                {
                    continue;
                } else
                {
                    out.append(in.charAt(i));
                }
            } else
            {
                out.append(in.charAt(i));
            }
        }
        //Also make sure to push the last value
        char last = in.charAt(in.length()-1);
        //System.out.println("Fixing Output, last char :"+last+":");
        out.append(last);
        return out.toString().trim();
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
        if ((c > 47 & c < 58) || c == 46 ) return true; //Digit Characters or '.' || c == 32
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
        return (c == 43 || c == 45 || c == '\u2013'); // '+', '-', '-' but different, took a while to figure this one out.
    }
    private static boolean isParenthesis(char c)
    {
        return (c == 40 || c == 41);
    }
}

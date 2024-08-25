package Program3_DONOTUSE;
/**Tyler Andreasen
 * Program3 is the driver for an infix to postfix notation converter.
 * NOTE::: I am using the absolute path of my test file, Line 14, and will near certainly require editing for your system.
 * I know there is a way to do relative file paths, though I cannot seem to get it to function, and you may also store
 * the file else where relative to the source.
 * */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Program3
{
    private static final String FILE_LOCATION = "C:\\Users\\Tyler\\IdeaProjects\\CS2050MegaProject\\src\\Program3\\Program3.txt";
    private static final char[] operands = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
    //private static char[] lowPrecOperators = {'+', '-'};
    private static final char[] highPrecOperators = {'*', '/', '%'};
    private static final char[] allOperators = {'*', '/', '%', '+', '-'};
    private static final char[] parens = {'(', ')'};
    private static final boolean mainDebug = true; // if (mainDebug) System.out.println("");
    public static void main(String[] args) throws IOException
    {
        BufferedReader fileIn = new BufferedReader(new FileReader(FILE_LOCATION));
        String lineIn;
        while ((lineIn = fileIn.readLine()) != null)
        {
            lineIn = lineIn.trim();
            System.out.println("Input:"+lineIn+":");
            String postFix = InfixToPostFix(lineIn);
            System.out.printf("Output - :%s: -> :%s:\n",lineIn, postFix);
            break;
        }
        fileIn.close();
        System.out.println("Execution of Program 3 Complete.");
    }

    public static String InfixToPostFix(String s)
    {
        //Step 1
        ArrayStackClass2 tStack = new ArrayStackClass2(s.length()), dStack = new ArrayStackClass2(s.length());

        boolean openParenFlag = false;

        if (s.length() == 0)
        {
            //System.out.println("#ERROR# Program3.main() - Input String is empty");
            return "#ERROR# Program3.main() - Input String is empty";
        }

        //Step 2
        parseLoop: for (int i = 0; i < s.length(); i++)
        {
            /*
            System.out.println("TTT");
            tStack.devShow();
            System.out.println("DDD");
            dStack.devShow();
            */

            char current = s.charAt(i);
            System.out.println((i)+" char "+current);

            //Step 3
            if (contains(operands, current)) //Current is an operand
            {
                if (mainDebug) System.out.println("Pushing :"+current+": to dStack as Operand.");
                dStack.push(current);
            }
            //Step 4
            else if (contains(allOperators, current)) //Current is an operator
            {
                if (mainDebug) System.out.println("Handling :"+current+": as Operator.");
                int loopCount = 0;
                boolean notPlacedOperator = true;
                operatorLoop: do {
                    if (loopCount >= s.length()) {
                        System.out.println("#ERROR# Program3.main() Repeating Code Problem - While Loops :" + loopCount + ":, Problem Operator :" + current + ":, Input :" + s + ":. #");
                        break parseLoop;
                    }
                    loopCount++;
                    boolean highPrecTop = contains(highPrecOperators, tStack.peek());
                    boolean highPrecCurrent = contains(highPrecOperators, current);
                    if (highPrecTop && !highPrecCurrent) {
                        char temp = tStack.pop();
                        if (mainDebug) System.out.println("Moving char :"+temp+": from tStack to dStack while Handling Operator :"+current+":.");
                        dStack.push(temp);
                        continue operatorLoop; //Maintain the value of current, as it must be checked against the next operator
                    } else {
                        tStack.push(current);
                        if (mainDebug) System.out.println("Pushed Operator :"+current+": to tStack.");
                        notPlacedOperator = false;
                        break operatorLoop;
                    }
                } while (notPlacedOperator);
            }
            //Step 5
            else if (current == '(')
            {
                openParenFlag = true;
                tStack.push(current);
                if (mainDebug) System.out.println("Pushed Open Parenthesis to tStack and set openParenFlag to true.");
            }
            //Step 6
            else if (current == ')')
            {
                if (!openParenFlag)
                {
                    System.out.println("#ERROR# Program3.main() - Unmatched Open Parenthesis in :"+s+":. #");
                    break parseLoop;
                } else {
                    tStack.push(current);
                    openParenFlag = false;
                    if (mainDebug) System.out.println("Pushed Close Parenthesis to tStack and set openParenFlag to false.");
                }
            }
            else if (current == ' ')
            {
                continue parseLoop;
            }
            //Step 7
            else
            {
                //System.out.println("#ERROR# Program3.main() - Invalid Character :"+current+": in s :"+s+":. #");
                return "#ERROR# Program3.main() - Invalid Character :"+current+": in s :"+s+":. #";
            }
        } //Step 8, go to step 2
        System.out.println("TTT");
        tStack.devShow();
        System.out.println("DDD");
        dStack.devShow();

        if (mainDebug) System.out.println("Completed basic Parsing");
        //Step 9
        if (openParenFlag)
        {
            //System.out.println("#ERROR# Program3.main() - Unmatched Close Parenthesis in :"+s+":. #");
            return "#ERROR# Program3.main() - Unmatched Close Parenthesis in :"+s+":. #";
        }
        if (mainDebug) System.out.println("Input :"+s+": has matched parentheses. dStack :"+dStack.size()+":, tStack :"+tStack.size()+":.");
        //Step 10
        for (int i = 0; i < tStack.size()+1; i++)
        {
            char temp = tStack.pop();
            dStack.push(temp);
            if (mainDebug) System.out.println("Pushed :"+temp+": to dStack from tStack.");
            System.out.println("TTT");
            tStack.devShow();
            System.out.println("DDD");
            dStack.devShow();
        }
        if (mainDebug) System.out.println("dStack length :"+dStack.size()+":, tStack length :"+tStack.size()+":.");
        //Step 11
        StringBuilder bobTheBuilder = new StringBuilder();
        int tempDSize = dStack.size();
        for (int i = tempDSize; i > -1; i--)
        {
            char temp = dStack.pop();
            if (!contains(parens, temp))
            {
                bobTheBuilder.insert(0, temp + " ");
                if (mainDebug) System.out.println("Pushed :"+temp+": to output String, dStack :"+dStack.size()+":.");
            } else continue;
        }
        return postFixOutputFixer(bobTheBuilder.toString());
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
        System.out.println("Fixing Output, last char :"+last+":");
        out.append(last);
        return out.toString().trim();
    }
    private static boolean contains(char[] bank, char key)
    {
        for (char c : bank)
        {
            if (c == key) return true;
        }
        return false;
    }
}

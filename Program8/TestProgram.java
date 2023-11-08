package Program8;

import java.util.ArrayList;


/**Created to test if the Java ArrayList allows duplicate Strings, which it does. FML.
 * */
public class TestProgram
{
	public static void main(String[] args)
	{
		ArrayList<String> test = new ArrayList<String>();
		String a = "AAA", b = "BBB", c = "CCC";
		test.add(a);
		test.add(b);
		test.add(c);
		test.add(a);
		System.out.println(test.size());
	}
}

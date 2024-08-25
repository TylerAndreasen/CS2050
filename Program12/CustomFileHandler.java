package Program12;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**Tyler Andreasen
 * A custom file reading class which reads text files into Strings either as an Array or an ArrayList.
 * File reading currently does not allow toggling of removal of whitespace, though that can be implemented
 * TODO Refactor the main readFile(AL<S>...) to work on a range of lines within the file, simpler method signitures
 * TODO /\ assume all lines, others give just a maximum line count, others specify a range. All of these then call
 * TODO /\ the primary with the needed values. Also, ponder if discussed is necessary.
 */
public class CustomFileHandler
{
	//
	// <FILE IN>
	//
	/**
	 * Lists the file extensions which should be checked when attempting to read a file.
	 */
	private static String[] fileExtentions =
			{
					".txt"
					//,".lascii"
					//
			};
	/**
	 * Static Field which is used to indicate which file reading method is calling the primary.
	 */
	private static int read_Indirect_Call_Indicator = 0;

	private final static int DIRECT_CALL = 0, STRING_ARRAY_CALL = 1;
	/**
	 * Contains Strings used to indicate which method was called
	 * to indirectly call CustomFileHandler.readFile(String).
	 */
	private final static String[] INDIRECT_READ_METHOD_STRING =
			{
					"",
					"# Called by readFileArray(String) - String[]. #"
			};

	/**
	 * Returns the readable contents of the file at the location specified in
	 * the parameter {code fileName}, as a String[]. Note: Removes trailing
	 * and leading white space on each line.
	 * @param fileName - the String version of the file location desired.
	 * @return - String[] containing either null or
	 */
	public static String[] readFileArray(String fileName)
	{
		read_Indirect_Call_Indicator = STRING_ARRAY_CALL;
		ArrayList<String> temp = readFile(fileName, true);
		String[] output = new String[temp.size()];
		for (int i = 0; i < temp.size(); i++) output[i] = temp.get(i);
		read_Indirect_Call_Indicator = DIRECT_CALL;
		return output;
	}


	/**
	 * Returns the readable contents of the file at the location specified in
	 * the parameter {code fileName}, as a String[]. Toggleable removal of
	 * trailing and leading white space on each line.
	 * @param fileName - the String version of the file location desired.
	 * @return - String[] containing either null or
	 */
	public static String[] readFileArray(String fileName, boolean trimLines)
	{
		read_Indirect_Call_Indicator = STRING_ARRAY_CALL;
		ArrayList<String> temp = readFile(fileName, trimLines);
		String[] output = new String[temp.size()];
		for (int i = 0; i < temp.size(); i++) output[i] = temp.get(i);
		read_Indirect_Call_Indicator = DIRECT_CALL;
		return output;
	}

	/**
	 * Returns a ArrayList<String> which contains the lines of a text document.
	 * @param fileName - the name of the file to read from.
	 * @param trimLines - Flag to indicate whether to remove trailing/leading whitespace.
	 * @return - The data read from file or null if the file is not found.
	 */
	public static ArrayList<String> readFile(String fileName, boolean trimLines)
	{
		if (fileName == null)
		{
			System.out.println("#ERROR# CustomFileHandler.readFile(String :"+fileName+":) - File Name Blank. "
					+ (read_Indirect_Call_Indicator > 0 ?
					"#" : (read_Indirect_Call_Indicator < INDIRECT_READ_METHOD_STRING.length ?
					INDIRECT_READ_METHOD_STRING[read_Indirect_Call_Indicator] : "#"))
			);
			return null;
		}
		if (!fileNameContainsExtension(fileName))
		{
			System.out.println("#WARNING# CustomFileHandler.readFile(String :"+fileName+":) - File Name does not contain extentions checked for. #" +
					"\n# File may not be found. See CustomFileHandler.java for full list of extensions. #");
		}
		try
		{
			BufferedReader fileIn = new BufferedReader(new FileReader(new File(fileName).getCanonicalFile()));
			ArrayList<String> fileLines = new ArrayList<>();
			String oneLine = fileIn.readLine();
			while (oneLine != null)
			{
				if (trimLines) fileLines.add(oneLine.trim());
				else fileLines.add(oneLine);
				oneLine = fileIn.readLine();
			}
			if (fileLines.size() < 1)
			{
				System.out.println("#WARNING# CustomFileHandler.readFile(String :"+fileName+":) - " +
						"File Empty - No Lines Read. #");
			}
			return fileLines;

		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("#ERROR# CustomFileHandler.readFile(String :"+fileName+":) - " +
					"File IO Exception. #");
			return null;
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("#ERROR# CustomFileHandler.readFile(String :"+fileName+":) - " +
					"Non-File IO Exception. #");
			return null;
		}
	}
	/**
	 * Tests if the passed in fileName String contains a file
	 * extension listed in CustomFileHandler.fileExtensions.
	 * Returns true if an extension is found, returns false
	 * otherwise.
	 * @param fileName - the file name to test.
	 * @return - if the file name contains an extension.
	 */
	private static boolean fileNameContainsExtension(String fileName)
	{
		allExtensions: for (String e : CustomFileHandler.fileExtentions)
		{
			extensionChars: for (int i = 0; i < e.length(); i++)
			{
				if (fileName.charAt(fileName.length()-1-i) == e.charAt(e.length()-1-i)) continue extensionChars;
				else continue allExtensions;
			}
			return true;
		}
		return false;
	}
	//
	// </FILE IN>
	// <FILE OUT>
	//

	/**
	 * Static Field which is used to indicate which file writing method is calling the primary.
	 */
	private static int write_Indirect_Call_Indicator = 0;


	public static final int NO_LINE_BREAKS = 0, FORCE_ADD_LINE_BREAKS = 1, ADD_UNFOUND_LINE_BREAKS = 2;

	/**
	 * Writes the elements of the String[] parameter to a file at the file location specified.
	 * Will or will not append a line break based on the boolean flag.
	 * @param inFileLines - the Strings which are to be written to a file.
	 * @param fileName - the name of the file to be written to.
	 * @param addLineBreaks - flag to control the appending of line breaks.
	 * @return - defined by CustomFileHandler.writeFile(ArrayList&lt;String&gt;, String, boolean)
	 */
	public static boolean writeFile(String[] inFileLines, String fileName, int addLineBreaks)
	{
		write_Indirect_Call_Indicator = STRING_ARRAY_CALL;
		ArrayList<String> fileLines = new ArrayList<>();
		fileLines.addAll(List.of(inFileLines));
		write_Indirect_Call_Indicator = DIRECT_CALL;
		return writeFile(fileLines, fileName, addLineBreaks);
	}

	/**
	 * #Risk - If the file name and or data are not stored outside the method call,
	 * the data will be lost as it is not returned or maintained.
	 * TODO Consider implementing a system which maintains a file data in a class variable if the call fails for some reason.
	 * @param fileLines - The ArrayList&lt;String&gt; which is to be saved to a file.
	 * @param fileName - the name of the file which data id to be saved to.
	 * @param addLineBreaks - Value which indicates if the file writer should add
	 *                         line break characters to the end of each line as
	 *                         it writes the file. Use the Constants to indicate
	 *                      desired behavior: NO_LINE_BREAKS, FORCE_ADD_LINE_BREAKS
	 *                      & FORCE_ADD_LINE_BREAKS.
	 * @return
	 */
	public static boolean writeFile(ArrayList<String> fileLines, String fileName, int addLineBreaks)
	{
		if (fileName == null)
		{
			System.out.println(
					"#ERROR# CustomFileHandler.writeFile(AL<S> :"+fileLines.size()+":," +
							" String :"+fileName+":, boolean :"+addLineBreaks+":) -\n - No File Name Given");
			return false;
		}
		if (fileLines.size() < 1)
		{
			System.out.println(
					"#ERROR# CustomFileHandler.writeFile(AL<S> :"+fileLines.size()+":," +
							" String :"+fileName+":, boolean :"+addLineBreaks+":) -#\n#- No " +
							"Data To Write to file. Failing to Write. #");
			return false;
		}
		if (!fileNameContainsExtension(fileName))
		{
			System.out.println("#WARNING# CustomFileHandler.readFile(String ::) - File Name does not contain " +
					"extentions checked for. #\n# File may not be found. See CustomFileHandler.java for full " +
					"list of extensions. #");
		}
		try
		{
			BufferedWriter fileOut = new BufferedWriter(new FileWriter(new File(fileName).getCanonicalFile()));
			for (String line : fileLines)
				fileOut.write(line+
					(addLineBreaks == NO_LINE_BREAKS ?
						"" :
						(addLineBreaks == FORCE_ADD_LINE_BREAKS ?
							"\n" :
							(line.charAt(line.length()-1) == '\n' ?
								"" :
								"\n"
							)
						)
					)
			);
			fileOut.close();
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("#ERROR# CustomFileHandler.writeFile(AL<S> :"+fileLines.size()+":," +
					" String :"+fileName+":, boolean :"+addLineBreaks+":) -#\n#- FILE IO Error. Failing to Write. #");
			return false;
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("#ERROR# CustomFileHandler.writeFile(AL<S> :"+fileLines.size()+":," +
					" String :"+fileName+":, boolean :"+addLineBreaks+":) -#\n#- NON-FILE IO Error. " +
					"Failing to Write. #");
			return false;
		}
	}
}


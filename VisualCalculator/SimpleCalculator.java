package VisualCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class SimpleCalculator
{
	private static String currentDisplay = "3 + 2";

	private static int
			DISPLAY_FONT_SIZE = 30,
			BUTTON_FONT_SIZE = 30,
			TOP_BAR_HEIGHT = 40,
			BUTTON_SIZE_CONST = 50
					;

	private static Font DISPLAY_FONT = new Font ("courier new", 0, DISPLAY_FONT_SIZE),
			BUTTON_FONT = new Font ("courier new", 0, BUTTON_FONT_SIZE)
					;

	public static void main(String[] args)
	{
		//System.out.println(calculate("1 / 2 * 3 - 4 + 5 / 6 * 7 - 8 + 9"));


		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(321,560);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.DARK_GRAY);


		//Display the value in the line currently.
		JLabel currentDisplayLabel = new JLabel(currentDisplay);
		currentDisplayLabel.setSize(300,50);
		currentDisplayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		currentDisplayLabel.setLocation(0,90);
		currentDisplayLabel.setForeground(Color.white);
		currentDisplayLabel.setFont(DISPLAY_FONT);
		frame.add(currentDisplayLabel);

		/*
		JButton widthTest = new JButton("?");
		widthTest.setVisible(false);
		widthTest.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{

			}
		});*/



		//Next Steps
		//Using two loops, create a set of nine buttons to handle the digits 1-9,
		//Values and features beyond will be handled separately.

		SpecialButton[] numberButtons = new SpecialButton[9];
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				int ind = (i*3)+j;
				numberButtons[ind] = new SpecialButton();
				numberButtons[ind].setText("a");//Integer.toString(ind));
				numberButtons[ind].setVisible(true);
				numberButtons[ind].setFont(BUTTON_FONT);
				numberButtons[ind].setSize(BUTTON_SIZE_CONST,BUTTON_SIZE_CONST);
				numberButtons[ind].setLocation(
						BUTTON_SIZE_CONST*j,//x -- j
						450 - BUTTON_SIZE_CONST*i//y -- i
				);
				numberButtons[ind].addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						currentDisplay += numberButtons[ind].getAddedCharacter();
						currentDisplayLabel.setText(currentDisplay);
					}
				});
				numberButtons[ind].validate();
				frame.add(numberButtons[ind]);
			}
		}
		for (SpecialButton s : numberButtons)
		{
			s.setVisible(true);
			s.repaint();
		}

		//TODO When an operator is added, add " # ", where # is the operator, as this will separate operators from digits.
		//TODO Don't add parentheses.


		// Button for =
		JButton equalsButton = new JButton("=");
		equalsButton.setFont(BUTTON_FONT);
		equalsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				currentDisplay = calculate(currentDisplay);
				currentDisplayLabel.setText(currentDisplay);
				//System.out.println("Added '1' to "+currentDisplay);
			}
		});
		equalsButton.setSize(BUTTON_SIZE_CONST,BUTTON_SIZE_CONST);
		equalsButton.setLocation(frame.getWidth()-(2*BUTTON_SIZE_CONST),frame.getHeight()-equalsButton.getHeight()-TOP_BAR_HEIGHT);
		equalsButton.revalidate();
		equalsButton.repaint();
		frame.add(equalsButton);

	}
	private static char[] operatorsList = {'*','/','+','-'};
	private static String highPresOps = "[*/]", lowPresOps = "[+-]", digits = "\\d+";

	/**Expects a string which contains whole numbers and the four basic arithmetic operators,
	 * with a space separating one from another. If formatted correctly, returns the resulting
	 * value when calculated.
	 * */
	private static String calculate(String in)
	{
		String[] sections = in.split(" ");
		if (sections.length == 1) return in;
		if (sections.length < 3 || sections.length%2 !=  1)
		{
			System.out.println("Invalid calculation, incorrect element count :"+sections.length+":.");
			return "";
		}

		if (divideZeroTest(sections))
		{
			return "Attempted to Divide By Zero.";
		}


		/*
		* Plan for how to calculate
		* Option 1. Integrate this with the Infix to Postfix notation, then calculate from that.
		* 		Honestly, may not be a bad option.
		*
		* Option 2. Relying on the order and sections array to build an AL<>, run through elements to
		*
		* */

		ArrayList<String> manipulate = new ArrayList<String>();
		for (String a : sections)
			manipulate.add(a);
		boolean doLowPrecedence = false;

		repeater: while (manipulate.size() > 1)
		{
			calculator: for (int i = 0; i < manipulate.size()-1; i++)
			{
				if (Pattern.matches(digits, manipulate.get(i))) continue calculator;
				if (!doLowPrecedence && Pattern.matches(highPresOps, manipulate.get(i)))
				{
					int first = Integer.parseInt(manipulate.get(i-1)),
						second = Integer.parseInt(manipulate.get(i+1));
					switch (manipulate.get(i).charAt(0))
					{
						case '*':
							first *= second;
							break;
						case '/':
							first /= second;
							break;
					}
					manipulate.set(i-1, ""+first);
					//Remove the second value and the operator.
					manipulate.remove(i+1);
					manipulate.remove(i);
					continue repeater;

				} else if (doLowPrecedence && Pattern.matches(lowPresOps, manipulate.get(i)))
				{
					int first = Integer.parseInt(manipulate.get(i-1)),
							second = Integer.parseInt(manipulate.get(i+1));
					switch (manipulate.get(i).charAt(0))
					{
						case '+':
							first += second;
							break;
						case '-':
							first -= second;
							break;
					}
					manipulate.set(i-1, ""+first);
					//Remove the second value and the operator.
					manipulate.remove(i+1);
					manipulate.remove(i);
					continue repeater;
				}
			}
			if (doLowPrecedence) break repeater;
			else doLowPrecedence = true;
		}
		if (manipulate.size() > 1) System.out.println("WhaaWhaaWhaaWhaa..........");
		return manipulate.get(0);
	}


	private static boolean divideZeroTest(String[] in)
	{
		for (int i = 1; i < in.length-2; i = i+2)
		{
			if (in[i].equals("/") && Integer.parseInt(in[i+1]) == 0)
			{
				System.out.println("Attempted to Divide By Zero.");
				return true;
			}
		}
		return false;
	}

	//private static boolean testCompleted(int nextOperator, int lastOperator, int nextValue, int lastValue)

}


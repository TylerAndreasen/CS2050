package VisualCalculator;

import javax.swing.*;

public class SpecialButton extends JButton
{
	private static char[] operatorList = {'=','+','-','*','/'};
	private static int counter = 1;
	private int addedCharacter;

	SpecialButton()
	{
		super(counter+"");
		this.addedCharacter = counter++;
	}
	SpecialButton(boolean unused)
	{
		super("0");
		this.addedCharacter = 0;
	}
	SpecialButton(int index)
	{
		super(""+operatorList[index]);
		this.addedCharacter = operatorList[index];
	}
	public int getAddedCharacter() { return this.addedCharacter; }
}

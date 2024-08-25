package SwingExperiment;

import javax.swing.*;

public class Main
{
	public static void main(String[] args)
	{
		JFrame f=new JFrame();//creating instance of JFrame

		JButton b = InputManager.makeBasicButton();
		f.add(b);//adding button in JFrame

		f.setSize(400,500);//400 width and 500 height
		f.setLayout(null);//using no layout managers
		f.setVisible(true);//making the frame visible
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

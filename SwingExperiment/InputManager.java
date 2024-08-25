package SwingExperiment;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputManager
{
	private static JButton basicButton;

	public static JButton makeBasicButton()
	{
		basicButton = new JButton("click");//creating instance of JButton
		basicButton.setBounds(130,100,100, 40);
		basicButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//tf.setText("Welcome to Javatpoint.");
				System.out.println("CLICK");
			}
		});

		return basicButton;
	}

}

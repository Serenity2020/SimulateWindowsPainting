package code;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args){
		ViewFrame f = new ViewFrame();
		f.pack();
		/*
		 * Causes this Window to be sized to fit the preferred size and layouts of its subcomponents. 
		 * The resulting width and height of the window are automatically enlarged if either of dimensions is less than 
		 * the minimum size as specified by the previous call to the setMinimumSize method. 
		 * If the window and/or its owner are not displayable yet, 
		 * both of them are made displayable before calculating the preferred size. 
		 * The Window is validated after its size is being calculated.
		*/
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

package ross;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

/** First Frame to appear. asks how many cards to play with. 
 * written by Ross*/
public class Frame1 extends JFrame {
	
	// Declare variables before constructor to increase their scope.
	
	// Create GUI components. start with RadioButtons in button group. 
	JRadioButton jrdio10 = new JRadioButton("10 cards");
	JRadioButton jrdio20 = new JRadioButton("20 cards");
	JRadioButton jrdio30 = new JRadioButton("30 cards");
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	
	
	// Create play button! 
	JButton play = new JButton("Play Game!");
	
	// create and add radio buttons to button group
	ButtonGroup btnGroup = new ButtonGroup();
	
	Frame1() {
		btnGroup.add(jrdio10);
		btnGroup.add(jrdio20);
		btnGroup.add(jrdio30);
		jrdio10.setSelected(true); // makes first choice default. 
		// Since ButtonGroup is not ButtonGroup is not a subclass of java.awt.Component, a ButtonGroup
		// object cannot be added to a container. So, we add the radioButtons to a ButtonGroup, 
		// and then add the radioButtons to a container (JPanel)
		
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(jrdio10);
		p1.add(jrdio20);
		p1.add(jrdio30);
		
		// Add the question to the panel using a border
		p1.setBorder(new TitledBorder("How many cards would you like to play with?"));

		// Add p1 and play button to p2.
		
		p2.setSize(500, 100);
		p2.setPreferredSize(new Dimension(300,100));
		p2.add(p1);
		p2.add(play);
		
		// Create frame, add panel and button
		this.setTitle("Memory Match Game");
		setLayout(new FlowLayout());
		add(p2);
		
		// Make frame appear in center of screen.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null); // center on screen.
		setVisible(true);
		
		
		// Make play button do something! 
		play.addActionListener(new ActionListener(){
			

			@Override
			public void actionPerformed(ActionEvent e) {
				// replace this window (radio buttons) with game window (cards)
				dispose(); // kills this Window.

				// find out which radiobutton in btnGroup is selected. 
			      for (Enumeration<AbstractButton> buttons = btnGroup.getElements(); buttons.hasMoreElements();) {
			            AbstractButton button = buttons.nextElement();
			            if (button.isSelected()) {
			               // this button is selected. create a new window with this many cards. 
			            	switch(button.getText()) {
			            		case "10 cards": Memory2.frame2 = new Frame2(10); break; 
			            		case "20 cards": Memory2.frame2 = new Frame2(20); break;
			            		case "30 cards": Memory2.frame2 = new Frame2(30); break;
			            	}
			            }
			      }
			}
		});
	}
}

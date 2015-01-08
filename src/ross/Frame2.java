package ross;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import austin.Card;

/** Second Window to appear. actually plays the game!
 * Created by Ross, modified by Austin */
public class Frame2 extends JFrame {
	
	// Declare variables before constructor to increase their scope.
	public JPanel playingField = new JPanel(); // middle of frame, where game actually happens. 
	public JLabel instructions = new JLabel(" Pick two cards:");

	// no-arg constructor
	Frame2(int fieldSize) {
		// make sure fieldSize is right size.
		if(!(fieldSize == 10 || fieldSize == 20 || fieldSize == 30)) {
			// if it's not 10, 20, or 30, set it back to 10. 
			fieldSize = 10;
		}
		
		
		// borderLayout for frame, gridLayout for inside section (panel)
		playingField.setLayout(new GridLayout(0, 5, 5, 5));
		// Create frame, add cards
		this.setTitle("Memory Match Game");
		// add playingField to center of frame. add instructions to top, feedback to bottom

		this.add(playingField, BorderLayout.CENTER);
		this.add(instructions, BorderLayout.NORTH);
		
		Card[] deck = Memory2.createField(fieldSize);
		
		// set size of window based on number of cards
		switch (fieldSize) {  // width: 100, height: 135. 
		case 10: this.setPreferredSize(new Dimension(520,330)); break;
		case 20: this.setPreferredSize(new Dimension(520,615)); break;
		case 30: this.setPreferredSize(new Dimension(353,620)); break;
		}


		
		// Make frame appear in center of screen.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null); // center on screen.
		setVisible(true);
		
		for(int i = 0; i < deck.length; i++) {
			playingField.add(deck[i]); // add all cards from array to Frame. 
		}

	}
}

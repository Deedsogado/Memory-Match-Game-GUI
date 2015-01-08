/* Ross Higley
 * Memory.java
 * Oct 22, 2014
 * Directs the gameplay of the memory match game, using resources found in Card.java.
 * added Try catch blocks on 11/5/14 so it won't break when user enters non-integer values. 
 */
package ross;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import austin.Card;

public class Memory2 {
/** Initialize all variables as static outside the main method. 
 * This will allow the actionListeners to access them, even from another class. 
 * Also, since main() is static, it will still be able to access them. */

	// Initialize variables
	public static long beginTime = 0;
	public static long endTime = 0;
	public static Frame2 frame2;
	
	
	
	public static void main(String[] args) {
		
		// Make window appear. GUI does the rest.
		new Frame1();

		// Record current time. Subtract later for score. (happens in CardActionListener) 
		beginTime = System.currentTimeMillis();

	}
	
	/** Returns a scrambled array of Cards based on size. */
	public static Card[] createField(int deckSize){
		// Create cards in pairs, then shuffle array
		Card[] pairedcards = new Card[deckSize];

		// Fill array with cards, two by two with the same values. 
		for (int i = 0, j = 1; i < pairedcards.length ; i+=2, j++ ) { // increment i by 2 because we create two cards at a time
			pairedcards[i] = new Card(j); // j is what the value should be set as. The int becomes a String or Picture in the object. 
			pairedcards[i+1] = new Card(j);
		}
		
		// Swap cards around so they are in random places.
		for (int i = pairedcards.length - 1; i > 0 ; i-- ) {
			int random = (int) (Math.random() * (i + 1) );
			if (i == random ) {
				++i;
			} else { 
				Card temp = pairedcards[random];
				pairedcards[random] = pairedcards[i];
				pairedcards[i] = temp;
			}	
		}
		
		// now that they are in random places, tell each card object it's new coordinate.
		for (int i = 0; i < pairedcards.length; i++) {
			pairedcards[i].setFieldLocation(i); // now each object knows what slot it's in. 
		}
		
		return pairedcards;
	}
	
}




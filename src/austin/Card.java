/* Austin Beauregard
 * Card.java
 * Oct 22, 2014
 * Contains all constructors, fields, and methods for Cards, to be used in Memory.java. 
 * Extended JButton on 11/24/14 by Ross.
 * Above line 106 is Ross, below line 106 is Austin. 
 */
package austin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ross.Memory2;

public class Card extends JButton{ // makes cards clickable. 
// All fields and methods written by Austin. 
	private boolean faceup = false;
	private boolean matched = false;
	private static int deckCount = 0;
	static int matchCount = 0;
	static int guessCount = 0;
	ImageIcon shape ;
	Image image;
	Image cardback = new ImageIcon("cardimages\\b2fv.png").getImage();
	int fieldLocation; // Where it lives in an array. assigned after the shuffle. 
	String cardName; // keep in sync with image and cardBack. String representation of the image. set when card is created.
	static Stack<Card> stackOfCards = new Stack<>();
	
	// Many methods below set to public because Card.java is in different package from Card.java.
	public Card(int newShape){ 
		setShape(newShape);
		deckCount++;
		this.addActionListener(new ActionListener(){
			/** This action listener mostly written by Ross. */
			@Override
			public void actionPerformed(ActionEvent e) {
				Card thisCard = (Card) e.getSource();
				/** choose first card, then choose second card. both will be displayed. no counter needed. 
				 * just wait until third card is selected, then either set previous two cards facedown, or 
				 * remove previous two cards from play depending on if they are matched or not. 
				 * tracking of 3 cards can happen in Stack.  */
				
				if (!thisCard.getFaceUp()) {
						thisCard.setFaceUp();
					stackOfCards.push(thisCard); // push into stack.
	
					// When stack has reached 3 cards, compare cards 1 and 2 and act accordingly. 
					if (stackOfCards.size() == 3) {
						if ((stackOfCards.get(0).equals(stackOfCards.get(1))) // If they match, remove both from play.  
								&& (stackOfCards.get(0).fieldLocation != stackOfCards.get(1).fieldLocation )) {
		
							stackOfCards.get(0).removeFromPlay();
							try{
								stackOfCards.get(1).removeFromPlay();	
							} catch (ArrayIndexOutOfBoundsException exception) {
							
							}
						} else { // If they don't match, flip them both facedown.
				
							stackOfCards.get(0).setFaceDown();
							try{
								stackOfCards.get(1).setFaceDown();	
							} catch (ArrayIndexOutOfBoundsException exception) {
							
							}
						}
						
						// now reset the stack, so the third card becomes the only item in stack. 
						stackOfCards.clear();
						stackOfCards.push(thisCard); // now only 1 card here. 
						// They made a Guess!
						guessCount++;	
					
					}
				
					// On the last pair, there is no 3rd card to select, to remove the previous two. 
					// to fix this, we will look for the matchCount to equal half of the deckCount
					if ((stackOfCards.size() == 2) && (matchCount+2) == (deckCount)) {
						stackOfCards.get(0).removeFromPlay();	
						try{
							stackOfCards.get(1).removeFromPlay();	
						} catch (ArrayIndexOutOfBoundsException exception) {
				
						}
					
						// begin end phase. show points! 
						Memory2.endTime = System.currentTimeMillis();
						// show results in two Large Labels in center of screen
						JLabel time = new JLabel("Your score is: " + (Memory2.endTime - Memory2.beginTime)/1000 + " seconds");
						JLabel guesses = new JLabel("You made " + guessCount + " guesses. ");
						time.setHorizontalAlignment(JLabel.CENTER);
						guesses.setHorizontalAlignment(JLabel.CENTER);
						JButton playAgain = new JButton("Play Again?");
						playAgain.addActionListener(new ActionListener() {
							/** This action listener written by Austin */
							@Override
							public void actionPerformed(ActionEvent e) {
								// close exiting frame.
								Card.guessCount =0;
								Card.matchCount =0;
								Card.deckCount = 0;
								Card.stackOfCards.clear();
								Memory2.frame2.dispose();
								// begin new game
								Memory2.main(null);
							}});
						
						Memory2.frame2.playingField.removeAll();
						Memory2.frame2.playingField.setLayout(new GridLayout(4,0,0,0));
						Memory2.frame2.playingField.add(time);
						Memory2.frame2.playingField.add(guesses);
						Memory2.frame2.playingField.add(playAgain);
						Memory2.frame2.instructions.setText(" Congratulations!  You've Won Memory Match Game!");
					}
				}
			}
		});
	}
	public boolean getFaceUp(){
		return faceup;
	}
	public void setFaceUp(){
		faceup=true;
		repaint();
	}
	public void setFaceDown(){
		faceup=false;
		repaint();
	}
	public boolean getMatched(){
		return matched;
	}
	public void setMatched(){
		matched = true;
		repaint();
	}
	public int getDeckCount(){
		return deckCount;
	}
	public ImageIcon getShape(){
		return shape;
		//TODO: return path to image here. 
	}
	@Override
	public String toString() {
		return cardName;
	}
	public int getFieldLocation() {
		return fieldLocation;
	}
	public void setFieldLocation(int newLocation) {
		if (newLocation > 0 && newLocation < deckCount) {
			fieldLocation = newLocation;
		}
	}
	public boolean equals(Card otherCard) {
		if(this.toString() == otherCard.toString()) {
			return true;
		}
		else
			return false;
	}
	public void removeFromPlay() {
		matchCount++;
		setVisible(false);
	}
	void setShape(int newShape){
		switch (newShape){ // paths no longer absolute. should work on both Austin's and Ross's computer. 
		case 1 : shape = new ImageIcon("cardimages\\windowslogo.png");
			cardName = "windowslogo";
			break;
		case 2 : shape = new ImageIcon("cardimages\\appleicon.png"); 
			cardName = "appleicon";
			break;
		case 3 : shape = new ImageIcon("cardimages\\chromeicon.jpg"); 
			cardName = "chromeicon";
			break;
		case 4 : shape = new ImageIcon("cardimages\\andriodicon.png");
			cardName = "androidicon";
			break;
		case 5 : shape = new ImageIcon("cardimages\\firefox.jpg");
			cardName = "firefox";
			break;
		case 6 : shape = new ImageIcon("cardimages\\safari icon.jpg"); 
			cardName = "safariIcon";
			break;
		case 7 : shape = new ImageIcon("cardimages\\interexplorer.png");
			cardName = "internetExplorer";
			break;
		case 8 : shape = new ImageIcon("cardimages\\linux.jpg"); 
			cardName = "linux";
			break;
		case 9 : shape = new ImageIcon("cardimages\\n64-logo.gif");
			cardName = "n64";
			break;
		case 10 : shape = new ImageIcon("cardimages\\Netscape-logo1.jpg");
			cardName = "netscape";
			break;
		case 11 : shape = new ImageIcon("cardimages\\playstation.png");
			cardName = "playstation";
			break;
		case 12 : shape = new ImageIcon("cardimages\\steam.png"); 
			cardName = "steam";
			break;
		case 13 : shape = new ImageIcon("cardimages\\triforce.png");
			cardName = "triforce";
			break;
		case 14 : shape = new ImageIcon("cardimages\\XBox_360.png");
			cardName = "xbox360";
			break;
		case 15 : shape = new ImageIcon("cardimages\\Assassin-s-Creed-Logo-1.png");
			cardName = "assassinsCreed";
			break;
		}
		image = shape.getImage();
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(getMatched()){
			//do nothing
			return;
		}
		if(getFaceUp()) {//if the card face up draw the face icon
			g.drawImage(image, 0, 0, getWidth(),getHeight(), this);
		} else { // if the card is face down, draw the back.
			g.drawImage(cardback, 0, 0, getWidth(), getHeight(), this);//if it's not face up it will draw the back of the card
		} 
	}
}

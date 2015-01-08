/* Ross Higley
 * Memory.java
 * Oct 22, 2014
 * Directs the gameplay of the memory match game, using resources found in Card.java.
 * added Try catch blocks on 11/5/14 so it won't break when user enters non-integer values. 
 */
package ross;

import java.util.InputMismatchException;
import java.util.Scanner;

import austin.Card;

public class Memory {
	public static void main(String[] args) {
		/** follows psuedoCode available online at 
		* https://docs.google.com/a/ldsbc.edu/document/d/1xfT5hI-iKllSEB91jhAOpiw07WmoEl37iytKAnLaHjo/edit  
		* however, procedural stuff is placed into methods. **/
		
		// Initialize variables
		Scanner input = new Scanner (System.in);
		int matchedCount = 0;
		int guessCount = 0;
		int numberOfCards = 0;
		long beginTime = 0;
		long endTime = 0;
		Card[] field;
		int card1 = 0; // stores index of card, not location on board (fixes off by 1 error)
		int card2 = 0; // stores index of card, not location on board (fixes off by 1 error)
		boolean goodInput = false; // true if the input is good. 
		
		// Ask for number of cards (10, 20, or 30). 
		System.out.print("How many cards would you like to play with? 10, 20, or 30? ");
		try{
			numberOfCards = input.nextInt();
		} catch(InputMismatchException e) {
			System.out.println(input.next() + " is not an Integer. ");
			input.nextLine(); // clears the input buffer cache.
		}
		//Verify that input is indeed either 10, 20 or 30.
		while (!(numberOfCards == 10 || numberOfCards == 20 || numberOfCards == 30)) { // will loop until they give a 10, 20 or 30. 
			// This block only runs if the user entered a wrong number. 
			System.out.print( numberOfCards + " is not a 10, 20, or 30. try again. "
					+ "\nHow many cards would you like to play with? 10, 20, or 30? ");
			try{
				numberOfCards = input.nextInt();
			} catch(InputMismatchException e) {
				System.out.println( input.next() + " is not an Integer. ");
				input.nextLine(); // clears the input buffer cache.
			}
		}  // card wasn't 10, 20 or 30? Ask again.
				
		// Now the numberOfCards is the right number. Create the field of cards 
		field = createField(numberOfCards);
		
		// Record current time. Subtract later for score. 
		beginTime = System.currentTimeMillis();
		
		do { // Loop until no cards left (when matchedCount equals half of numberOfCards)
			// Show the cards. 
			showCards(field);
			
			do { // ask for values until we receive two cards that were actually displayed. 	
				System.out.print("Select two cards: ");
				try{
					card1 = (input.nextInt() -1); // stores index of card, not place on board (fixes off by 1 error)
					
					card2 = (input.nextInt() -1); // stores index of card, not place on board (fixes off by 1 error)
				} catch(InputMismatchException e) {
					System.out.println("There is no Card " + input.next() + " Try again.");
					input.nextLine(); // clears the input buffer cache.
					card1 = -404; // will fail validateInput() later.
				}
		
				guessCount++;
				
				/* Before revealing cards, make sure they haven't been matched before.
				 * In other words, did they accidently choose a card that was not printed? */
				
				goodInput = validateInput(field, card1, card2); // True when the input is good.		
			} while (!goodInput); // keep asking until we get good input
			
			// If you escaped the above loop, your input is good. Go ahead and compare the values. 
			if (compareCards(field, card1, card2) ) { //printing the cards happens inside this method. 
				matchedCount+=2; // if they matched, increment matched count by two.  
			}
		} while (matchedCount != numberOfCards); // Loop until no cards left (when matchedCount equals half of numberOfCards)
		
		// Record current time.  Subtract from beginTime for score. 
		endTime = System.currentTimeMillis();
		System.out.println("Your score is: " + (endTime - beginTime)/1000 + " seconds");
		System.out.println("You made " + guessCount + " guesses. ");
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
		
		for (int i = pairedcards.length - 1; i > 0 ; i-- ) {
			int random = (int) (Math.random() * (i + 1) );
			if (i == random ) {
				++i; // why preincrement?
			} else { 
				Card temp = pairedcards[random];
				pairedcards[random] = pairedcards[i];
				pairedcards[i] = temp;
			}	
		}
		return pairedcards;
	}
	
	/** Shows the cards in the field.  */
	public static void showCards(Card[] cards) {
		// Display cards in terminal. Each number will be given number to identify its position. 
		for (int i = 0; i < cards.length; i++) { // print each item in array. will add line breaks for every fifth item later.
			if (cards[i].getMatched())  // if the card has been matched, print empty space.
				System.out.print("      ");  // 6 spaces. 
			else  // if the card is unmatched, print the card. 
				System.out.printf("\u005B %2s \u005D", (i+1)); //  [= \u005B.  ]=\u005D.    6 spaces. 
			if ((i+1) % 5 == 0)
				System.out.println(""); // line break after every 5 elements. 
		}
	}
	
	/** Return true if two cards match, false if they are different. */
	public static boolean compareCards(Card[] field, int card1, int card2) {
		//  Tell user what the cards are. 
		System.out.println ("Card " + (card1 + 1) + ": " + field[card1].getShape());
		System.out.println ("Card " + (card2 + 1) + ": " + field[card2].getShape());
		
		if (field[card1].getShape().equals(field[card2].getShape()) ) {// If the cards are the same, set isMatched to true for both of them. Tell user.
			field[card1].setMatched();
			field[card2].setMatched();
			System.out.println("You found a match! ");
			return true;
		} else {
			// If not match, just continue onward. 
			return false;
		}
	}
	
	/** Returns true if the input is possible, false if it's a bad input.  */
	public static boolean validateInput(Card[] field, int card1, int card2) {
		/// Assume the input is good. try to prove it false.
		boolean goodInput = true;
		// Check if either card was forced to be -404 because the user entered a non-int value
		if (card1 == -404 || card2 == -404) {
			return false; // Don't even print. They've already been told. 
		}
		
		// Check if both cards could potentially be on the field (not bigger than our array).
		if (card1 < 0 || card1 > field.length - 1) { // can't be less than 0, or more than 10, 20, or 30.
			System.out.println("There is no Card " + (card1 + 1) + " Try again.");
			goodInput = false; // Don't return false yet. card2 could be out of bounds.
		}
		if (card2 < 0 || card2 > field.length - 1) { // can't be less than 0, or more than 10, 20, or 30.
			System.out.println("There is no Card " + (card2 + 1) + " Try again.");
			goodInput = false; 
		}		
		// Now return false if the input has been proven bad so far. if we continue, and the input is out of bounds, 
		// the other checks may throw outOfBounds Exception.  
		if (goodInput == false) {
			return false;
		}
		// Check if cards have previously been matched.
		if (field[card1].getMatched() ) {// if one has been matched, tell user. Ask again, but don't reshow field. 
			System.out.println("Card " + (card1 + 1) + " has already been matched. Try again.");
			goodInput =  false; // Don't return false yet. could be duplicate cards. 
		}
		
		// We check if they are the same in between checking previous matches so that
		// in case someone selects a card twice that has already been matched, it only outputs it once. 
		
		// Check if both cards are the same card. 
		if (card1 == card2){
			System.out.println("Card " + (card1 + 1) + " and Card " + (card2  + 1) + " are the same. Try again. ");
			return false; 
		}
		
		if (field[card2].getMatched() ) {// if one has been matched, tell user. Ask again, but don't reshow field. 
			System.out.println("Card " + (card2 + 1) + " has already been matched. Try again.");
			return false; 
		} 
		 
		return goodInput;
	}

}

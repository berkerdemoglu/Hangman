package hangman;

import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {
	private Scanner scanner;
	Word word;
	private ArrayList<Character> previousGuesses;

	public Hangman() {
		// Sets up the back-end stuff like a scanner.
		scanner = new Scanner(System.in);
	}

	public void runGame() {
		// Runs the hangman game.
		boolean isProgramRunning = true; // Used for playing the game again
		boolean isGameRunning = true; // Used for making letter guesses
		char guess;
		int guessResult;

		System.out.println("Welcome to Hangman!\n");
		while (isProgramRunning) {
			word = new Word(); // It will choose a new word every time.
			previousGuesses = new ArrayList<>(); // Create an empty list to store user's guesses.
			System.out.printf("I have chosen a word with %d letters!\n", word.getChosenWord().length());

			do {
				guess = askUserGuess(); // Ask the user for a letter
				addGuessToList(guess); // Add it to the list of guesses
				guessResult = tryLetter(guess); // Try and see if the user's guess was correct
				
			} while (isGameRunning);
		}
	}

	private char askUserGuess() {
		/*
		Takes the user's guess and returns it as a char.
		 */
		System.out.print("Enter a letter: ");

		return scanner.nextLine().charAt(0);
	}

	private void addGuessToList(char guess) {
		if (!previousGuesses.contains(guess)) { // If guess was not made previously
			previousGuesses.add(guess);
		}
	}

	private int tryLetter(char guess) {
		/*
		Possible return values are:
		1 if letter was found in the word
		0 if letter was previously guessed
		-1 if letter was not found in the word
		 */
		int returnValue;
		int wordContains = word.getChosenWord().toString().toLowerCase().indexOf(Character.toLowerCase(guess));

		if (wordContains != -1) { // If letter in word
			if (!previousGuesses.contains(guess)) { // If letter was not made previously:
				returnValue = 1;
				for (int i = 0; i < word.getChosenWord().length(); i++) {
					if (Character.toLowerCase(word.getChosenWord().charAt(i)) == Character.toLowerCase(guess)) {
						word.getDashedWord().setCharAt(i, word.getChosenWord().charAt(i));
					}
				}
			} else { // If letter was made previously
				returnValue = 0;
			}
		} else { // If letter not in word
			returnValue = -1;
		}

		return returnValue;
	}
}

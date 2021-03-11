package hangman;

import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {
	private final Scanner scanner;
	private Word word;
	private ArrayList<Character> previousGuesses;
	private int numberOfMadeGuesses;
	private final int maxNumberOfAllowedGuesses;

	public Hangman() {
		// A backup ctor, main fn allows user to enter max allowed guesses.
		this(5);
	}

	public Hangman(int maxNumberOfAllowedGuesses) {
		this.maxNumberOfAllowedGuesses = maxNumberOfAllowedGuesses;
		scanner = new Scanner(System.in);
	}

	public void runGame() {
		// Runs the hangman game.
		boolean isProgramRunning = true; // Used for playing the game again
		boolean isGameOver = false; // Used for making letter guesses for a word
		char guess;
		int guessResult;

		System.out.println("Welcome to Hangman!\n");
		while (isProgramRunning) {
			word = new Word(); // It will choose a new word every time.
			previousGuesses = new ArrayList<>(); // Create an empty list to store user's guesses.
			numberOfMadeGuesses = 0;
			System.out.printf("I have chosen a word with %d letters!\n", word.getChosenWord().length());

			do {
				guess = askUserGuess(); // Ask the user for a letter
				guessResult = tryLetter(guess); // Try and see if the user's guess was correct
				addGuessToList(guess); // Add it to the list of guesses
				evaluateGuessResult(guess, guessResult); // Evaluate the user's guess
				printGameInfo();
				isGameOver = isGameOver(); // Check if the loop should end
			} while (!isGameOver); // While the game is not over
			isProgramRunning = checkUserWin();
		}
	}

	private char askUserGuess() {
		/*
		Takes the user's guess and returns it as a char.
		If the user enters 2 or more letters, it will request the user to enter a valid letter.
		 */
		String userGuess = "";
		do {
			System.out.print("Enter a letter: ");
			userGuess = scanner.nextLine();
			if (userGuess.length() != 1) {
				System.out.println("Invalid input. Please try again.");
			}
		} while (userGuess.length() != 1);

		return userGuess.charAt(0);
	}

	private int tryLetter(char letter) {
		/*
		Sees if letter was guessed previously or is in the chosen word or is not in the chosen word.

		Possible return values are:
		1 if letter was found in the word
		0 if letter was previously guessed
		-1 if letter was not found in the word
		 */
		int returnValue;

		if (word.getChosenWord().toString().toLowerCase().indexOf(Character.toLowerCase(letter)) != -1) { // If letter in word
				returnValue = 1;
		} else { // If letter not in word
			returnValue = -1;
		}

		if (previousGuesses.contains(letter)) { // If guess was previously made
			returnValue = 0;
		}

		return returnValue;
	}

	private void addGuessToList(char guess) {
		if (!previousGuesses.contains(guess)) { // If guess was not made previously
			previousGuesses.add(guess);
		}
	}

	private void evaluateGuessResult(char letter, int guessResult) {
		switch (guessResult) {
			case 1: // if letter was found in the word
				System.out.println("You have guessed correctly!");
				for (int i = 0; i < word.getChosenWord().length(); i++) {
					if (Character.toLowerCase(word.getChosenWord().charAt(i)) == Character.toLowerCase(letter)) {
						word.getDashedWord().setCharAt(i, word.getChosenWord().charAt(i));
					}
				}
				break;
			case 0: // if letter was previously guessed
				System.out.println("You have previously guessed this letter. Try again.");
				break;
			case -1:
				System.out.println("You have guessed incorrectly.");
				numberOfMadeGuesses++; // increment the number of user's previously made guesses
				break;
		}
	}

	private void printGameInfo() {
		/*
		Prints how many guesses the user has remaining, the letters they have guessed, the word covered with dashes.
		 */
		if (numberOfMadeGuesses != maxNumberOfAllowedGuesses) {
			System.out.printf(word.getDashedWord() + " (%d guesses left)\n", maxNumberOfAllowedGuesses - numberOfMadeGuesses);
			System.out.println("Your previous guesses: " + previousGuesses);
		}
	}

	private boolean isGameOver() {
		/*
		Checks if the game should end.
		If the number of made guesses is equal to allowed number of guesses then it will return true.
		If the user has revealed all the letters in the chosen word then it will return true.
		Otherwise, it will return false.
		 */
		return numberOfMadeGuesses == maxNumberOfAllowedGuesses || word.getChosenWord().toString().equals(word.getDashedWord().toString());
	}

	private boolean checkUserWin() {
		/*
		Checks if user has won or lost.
		Asks the user if they wish to continue playing or not.
		 */
		boolean returnValue = false;

		if (word.getChosenWord().toString().equals(word.getDashedWord().toString())) { // user won
			System.out.println("You win! You guessed the word that I chose!");
		} else {
			System.out.printf("You lost! The word that I chose was %s. Better luck next time.\n", word.getChosenWord());
		}

		String userOption;
		do {
			System.out.print("Would you like to play again? ('y' for yes, 'n' for no): ");
			userOption = scanner.nextLine();

			if (userOption.equals("y")) {
				returnValue = true;
			} else if (userOption.equals("n")) {
				returnValue = false;
			} else {
				System.out.println("Invalid input. Please try again.");
			}

		} while (!userOption.equals("y") && !userOption.equals("n"));

		return returnValue;
	}
}

package hangman;

import java.util.LinkedList;
import java.util.Scanner;
import command.Command;

public class Hangman {
	private final Scanner scanner;
	private Word word;
	private LinkedList<Character> previousLetterGuesses;
	private LinkedList<String> previousWordGuesses;
	private int numberOfMadeGuesses;
	private int maxNumberOfAllowedGuesses;

	private int numberOfWins;
	private int numberOfLosses;

	private boolean isProgramRunning;
	private boolean isGameOver;

	public Hangman() {
		// A backup ctor
		this(6);
	}

	public Hangman(int maxNumberOfAllowedGuesses) {
		this.maxNumberOfAllowedGuesses = maxNumberOfAllowedGuesses;
		scanner = new Scanner(System.in);
		numberOfWins = 0;
		numberOfLosses = 0;
		isProgramRunning = true; // Used for playing the game again
		isGameOver = false; // Used for making letter guesses for a word
	}

	public void runGame() {
		// Runs the hangman game.
		String guess;
		char letterGuess;
		int guessResult;
		int numberOfLetters;

		Command command = new Command(this);
		int commandResult;

		System.out.println("Welcome to Hangman!\n");
		System.out.println("Enter /help for help with commands.");
		while (isProgramRunning) {
			commandResult = 0;
			isGameOver = false;
			numberOfLetters = howManyLetters();
			word = new Word(numberOfLetters); // It will choose a new word every time.
			previousLetterGuesses = new LinkedList<>(); // Create two empty lists to store user's guesses.
			previousWordGuesses = new LinkedList<>();
			numberOfMadeGuesses = 0;
			System.out.println("I have chosen a new word!");

			do {
				guess = askUserGuess(); // Ask the user for a guess
				if (guess.startsWith("/")) { // If the user has entered a command
					commandResult = command.evaluateCommand(guess);
				} else if (guess.length() == 1) { // If the user has entered a letter
					letterGuess = guess.charAt(0);
					guessResult = tryLetter(letterGuess); // Try and see if the user's entry was correct
					addLetterGuessToList(letterGuess);
					evaluateLetterGuessResult(letterGuess, guessResult);
				} else if (guess.length() != word.getChosenWord().length()) { // If the user has entered a word not matching the number of letters
					System.out.println(guess + " is not a valid guess. Please try again.");
				} else { // If the user has entered a word matching the number of letters
					guessResult = tryWord(guess);
					addWordGuessToList(guess, guessResult);
					evaluateWordGuessResult(guess, guessResult);
				}
				if (!isGameOver) { // To make sure that /new command works
					isGameOver = isGameOver(); // Check if the loop should end
					printGameInfo(isGameOver);
				}
			} while (!isGameOver); // While the game is not over

			if (commandResult != 1 && commandResult != 2) { // if the command is not /new or /end
				checkUserWin();
			}
			if (commandResult == 0) { // if no command was entered
				isProgramRunning = askReplay();
			}
		}
		System.out.println("Good bye!");
	}

	private int howManyLetters() {
		int letters;
		do {
			System.out.print("How many letters would you like? ");
			letters = Integer.parseInt(scanner.nextLine());
			if (letters > 1) {
				break;
			} else {
				System.out.println("Invalid input. Please enter a number greater than 1.");
			}
		} while (true);

		return letters;
	}

	private String askUserGuess() {
		/*
		Takes the user's guess and returns it as a String.
		 */
		System.out.print("Enter your guess: ");

		return scanner.nextLine();
	}

	private int tryLetter(char letter) {
		/*
		Sees if letter was guessed previously or is in the chosen word or is not in the chosen word.

		Possible return values are:
		1 if letter was found in the word
		0 if letter was previously guessed
		-1 if letter was not found in the word
		-2 if letter is not alphabetic
		 */
		int returnValue;

		if (word.getChosenWord().toString().toLowerCase().indexOf(Character.toLowerCase(letter)) != -1) { // If letter in word
				returnValue = 1;
		} else { // If letter not in word
			returnValue = -1;
		}

		if (previousLetterGuesses.contains(letter)) { // If guess was previously made
			returnValue = 0;
		}

		if (!Character.isLetter(letter)) { // If character is not alphabetic
			returnValue = -2;
		}

		return returnValue;
	}

	private void addLetterGuessToList(char guess) {
		if (!previousLetterGuesses.contains(guess) && Character.isAlphabetic(guess)) { // If guess was not made previously
			previousLetterGuesses.add(Character.toLowerCase(guess));
		}
	}

	private void evaluateLetterGuessResult(char letter, int guessResult) {
		switch (guessResult) {
			case 1: // if letter was found in the word
				System.out.println("You have guessed correctly!\n");
				for (int i = 0; i < word.getChosenWord().length(); i++) {
					if (Character.toLowerCase(word.getChosenWord().charAt(i)) == Character.toLowerCase(letter)) {
						word.getDashedWord().setCharAt(i, word.getChosenWord().charAt(i));
					}
				}
				break;
			case 0: // if letter was previously guessed
				System.out.println("You have previously guessed this letter. Try again.\n");
				break;
			case -1:
				System.out.println("You have guessed incorrectly.\n");
				numberOfMadeGuesses++; // increment the number of user's previously made guesses
				break;
			case -2:
				System.out.println("Please enter a valid letter.");
				break;
		}
	}

	private int tryWord(String guess) {
		/*
		Possible return values are:
		1 if word matches the chosen word
		0 if word was previously guessed
		-1 if word does not match the chosen word
		-2 if word is not alphabetic

		This function does not need to specify a return value if the number of letters does not match the word's,
		because that is already handled in runGame().
		 */
		int returnValue;

		if (guess.equalsIgnoreCase(word.getChosenWord().toString())) { // if guess matches the chosen word
			returnValue = 1;
		} else { // if guess does not match the chosen word
			returnValue = -1;
		}

		if (previousWordGuesses.contains(guess)) { // if word was previously guessed
			returnValue = 0;
		}

		// Check if the word is alphabetic
		for (int i = 0; i < guess.length(); i++) {
			if (!Character.isLetter(guess.charAt(i))) { // if char is not a letter
				returnValue = -2;
				break;
			}
		}

		return returnValue;
	}

	private void addWordGuessToList(String guess, int guessResult) {
		if (guessResult != -2 && guessResult != 0) {
			previousWordGuesses.add(guess);
		}
	}

	private void evaluateWordGuessResult(String guess, int guessResult) {
		switch (guessResult) {
			case 1:
				System.out.println(guess + " is correct!\n");
				word.setDashedWord(word.getChosenWord()); // remove dashes so the game can end
				break;
			case 0:
				System.out.println("You have previously guessed this word. Try again.\n");
				break;
			case -1:
				System.out.println(guess + " is incorrect.\n");
				numberOfMadeGuesses++;
				break;
			case -2:
				System.out.println("Please enter a valid word.");
				break;
		}
	}

	private void printGameInfo(boolean isGameOver) {
		/*
		Prints how many guesses the user has remaining, their guesses, the word covered with dashes.
		 */
		if (!isGameOver) { // check if the game is over
			System.out.printf(word.getDashedWord() + " (%d false guesses left)\n", maxNumberOfAllowedGuesses - numberOfMadeGuesses);
			System.out.println("Your previous letter guesses: " + previousLetterGuesses);

			if (!previousWordGuesses.isEmpty()) { // if word guesses have been made
				System.out.println("Your previous word guesses: " + previousWordGuesses);
			}

			System.out.println(); // print an empty line for better readability
		}
	}

	private boolean isGameOver() {
		/*
		Checks if the game should end.
		If the number of made guesses is equal to allowed number of guesses then it will return true.
		If the user has revealed all the letters in the chosen word then it will return true.
		Otherwise, it will return false.
		 */
		return numberOfMadeGuesses == maxNumberOfAllowedGuesses || word.getChosenWord().toString().
				equals(word.getDashedWord().toString());
	}

	private void checkUserWin() {
		/*
		Checks if user has won or lost.
		Asks the user if they wish to continue playing or not.
		 */
		if (word.getChosenWord().toString().equals(word.getDashedWord().toString())) { // user won
			System.out.println("You win! You guessed the word that I chose! :)");
			numberOfWins++;
		} else {
			System.out.printf("You lost! The word that I chose was %s. Better luck next time. :(\n",
					word.getChosenWord().toString().toUpperCase());
			numberOfLosses++;
		}
		System.out.printf("Score - %d correct, %d wrong\n", numberOfWins, numberOfLosses);
	}

	private boolean askReplay() {
		boolean returnValue = false;

		if (!Word.getWordsToChooseFrom().isEmpty()) { // If there are still words to choose from
			String userOption;
			do {
				System.out.print("Would you like to play again? ('y' for yes, 'n' for no): ");
				userOption = scanner.nextLine();
				System.out.println(); // Print a new line for aesthetic purposes

				if (userOption.equals("y")) {
					returnValue = true;
				} else if (userOption.equals("n")) {
					returnValue = false;
				} else {
					System.out.println("Invalid input. Please try again.");
				}

			} while (!userOption.equals("y") && !userOption.equals("n"));
		} else {
			System.out.println("We have run out of words to play with!");
		}

		return returnValue;
	}

	//Setters for Commands"
	public void setMaxNumberOfAllowedGuesses(int maxNumberOfAllowedGuesses) {
		this.maxNumberOfAllowedGuesses = maxNumberOfAllowedGuesses;
	}

	public void setProgramRunning(boolean programRunning) {
		isProgramRunning = programRunning;
	}

	public void setGameOver(boolean gameOver) {
		isGameOver = gameOver;
	}
}

package hangman;

public class Main {

	public static void main(String[] args) {
		Integer maxGuesses = 5;
		if (args.length > 0) { // If arguments were passed in
			maxGuesses = Integer.valueOf(args[0]);
		}

		Hangman hangman = new Hangman(maxGuesses);
		hangman.runGame();
	}
}

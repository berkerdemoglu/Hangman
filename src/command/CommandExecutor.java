package command;

import hangman.Hangman;
import java.util.Arrays;

public class CommandExecutor {
	private static final String[] commands = {"/help", "/new", "/hint", "/exit", "/guesses"};
	private Hangman hangman;

	public CommandExecutor(Hangman hangman) {
		this.hangman = hangman;
	}

	public int executeCommand(String userCommand) {
		/*
		//todo add command help e.g /guesses help
		//todo add custom file command

		/guesses value - will alter maxNumberOfAllowedGuesses
		/new - will alter isGameOver
		/hint will alter word
		/end - will alter isProgramRunning
		/help - will print all the available commands including itself

		Return values:
		2 if /end is the command
		1 if /new is the command
		0 (otherwise)
		-1 if an invalid command was entered
		 */
		int returnValue = 0;

		String[] command = userCommand.split(" ");

		switch (command[0]) {
			case "/guesses":
				if (command.length == 2) {
					Integer newAllowed = Integer.valueOf(command[1]);
					System.out.printf("Setting allowed false guesses to %s...\n\n", command[1]);
					hangman.setMaxNumberOfAllowedGuesses(newAllowed);
				} else {
					returnValue = invalidCommand();
				}
				break;
			case "/new":
				if (command.length == 1) {
					hangman.setGameOver(true);
					returnValue = 1;
				} else {
					returnValue = invalidCommand();
				}
				break;
			case "/hint":
				if (command.length == 1) {
					// Determine hint to give (a letter)
					int randomLetterIndex = (int) (Math.random() * hangman.getWord().getChosenWord().length());
					char hintLetter = hangman.getWord().getChosenWord().charAt(randomLetterIndex);
					for (int i = 0; i < hangman.getWord().getChosenWord().length(); i++) {
						if (hangman.getWord().getChosenWord().charAt(i) == hintLetter) {
							// hint letter may occur more than once, so replace every occurrence with hint
							hangman.getWord().getDashedWord().setCharAt(i, hintLetter);
						}
					}

					System.out.printf("Your hint is %c.\n", hintLetter);
				} else {
					returnValue = invalidCommand();
				}
				break;
			case "/help":
				if (command.length == 1) {
					System.out.println("Commands: " + Arrays.toString(commands));
				} else {
					returnValue = invalidCommand();
				}
				break;
			case "/exit":
				if (command.length == 1) {
					hangman.setGameOver(true);
					hangman.setProgramRunning(false);
					returnValue = 1;
				} else {
					returnValue = invalidCommand();
				}
				break;
			default: // no such command
				returnValue = invalidCommand();
				break;
		}

		return returnValue;
	}

	private int invalidCommand() {
		System.out.println("Invalid command/command use. Enter /help to get a list of commands.\n");
		return -1;
	}
}

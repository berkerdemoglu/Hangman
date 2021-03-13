package command;

import hangman.Hangman;
import java.util.Arrays;

public class Command {
	private static final String[] commands = {"/help", "/new", "/exit", "/guesses"};
	private Hangman hangman;

	public Command(Hangman hangman) {
		this.hangman = hangman;
	}

	public int evaluateCommand(String userCommand) {
		/*
		//todo add command help e.g /guesses help

		/guesses value - will alter maxNumberOfAllowedGuesses
		/new - will alter isGameOver
		/end - will alter isProgramRunning
		/help - will print all the available commands including itself

		Return values:
		2 if /end is the command
		1 if /new is the command
		0 (otherwise)
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
					System.out.println("Invalid command use. You must specify a value for the /guesses command.");
				}
				break;
			case "/new":
				hangman.setGameOver(true);
				returnValue = 1;
				break;
			case "/help":
				System.out.println("Commands: " + Arrays.toString(commands));
				break;
			case "/exit":
				hangman.setGameOver(true);
				hangman.setProgramRunning(false);
				returnValue = 1;
				break;
			default: // no such command
				System.out.println("Invalid command. Enter /help to get a list of the commands.");
				break;
		}

		return returnValue;
	}
}

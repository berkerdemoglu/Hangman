package hangman;

import java.util.ArrayList;
import java.util.List;

public class Word {
	private static ArrayList<String> wordsToChooseFrom = new ArrayList<String>(List.of("Force", "Momentum", "Impulse", "Displacement"));
	private StringBuilder chosenWord;
	private StringBuilder dashedWord; // This variable will be shown to the player and will receive updates

	public Word() {
		// Select a new word from available words.
		chosenWord = chooseNewWord();
		dashedWord = new StringBuilder("");
		dashedWord.append("-".repeat(chosenWord.length()));

		// Remove the selected word from the list of available words to make sure that it doesn't get selected again.
		wordsToChooseFrom.remove(chosenWord.toString());
	}

	public StringBuilder getChosenWord() {
		//todo check if you should return the string value or stringbuilder value
		return chosenWord;
	}

	public void setChosenWord(StringBuilder chosenWord) {
		this.chosenWord = chosenWord;
	}

	public StringBuilder getDashedWord() {
		//todo check if you should return the string value or stringbuilder value
		return dashedWord;
	}

	public void setDashedWord(StringBuilder dashedWord) {
		this.dashedWord = dashedWord;
	}

	private StringBuilder chooseNewWord() {
		// Used in the constructor to select a random word.
		return new StringBuilder(wordsToChooseFrom.get((int) (Math.random() * wordsToChooseFrom.size())));
	}
}

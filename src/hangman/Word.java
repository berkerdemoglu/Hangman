package hangman;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Word {
	private static ArrayList<String> wordsToChooseFrom;

	static {
		try {
			wordsToChooseFrom = readWordsFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private StringBuilder chosenWord;
	private StringBuilder dashedWord; // This variable will be shown to the player and will receive updates

	public Word(int letters) {
		// Select a new word from available words.
		try {
			readWordsFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		chosenWord = chooseNewWord(letters);
		dashedWord = new StringBuilder("");
		for (int i = 0; i < chosenWord.length(); i++) {
			dashedWord.append('*');
		}

		// Remove the selected word from the list of available words to make sure that it doesn't get selected again.
		wordsToChooseFrom.remove(chosenWord.toString());
	}

	public StringBuilder getChosenWord() {
		return chosenWord;
	}

	public void setChosenWord(StringBuilder chosenWord) {
		this.chosenWord = chosenWord;
	}

	public StringBuilder getDashedWord() {
		return dashedWord;
	}

	public void setDashedWord(StringBuilder dashedWord) {
		this.dashedWord = dashedWord;
	}

	private StringBuilder chooseNewWord(int letters) {
		// Used in the constructor to select a random word.
		while (true) {
			StringBuilder newWord = new StringBuilder(wordsToChooseFrom.get((int) (Math.random() * wordsToChooseFrom.size())));
			if (newWord.length() > letters) {
				return newWord;
			}
		}
	}

	private static ArrayList<String> readWordsFile() throws IOException {
		ArrayList<String> words = new ArrayList<>();
		Path wordsFilePath = Paths.get("game_words.txt");
		Files.lines(wordsFilePath).forEach(words::add);

		return words;
	}

	public static ArrayList<String> getWordsToChooseFrom() {
		return wordsToChooseFrom;
	}
}

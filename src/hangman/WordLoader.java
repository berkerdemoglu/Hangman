package hangman;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;

public class WordLoader {
    public ArrayList<String> LoadWords(Path wordfile) {
        ArrayList<String> words = new ArrayList<>(); //Creates new ArrayList instance
        try {
            Files.lines(wordfile).forEach(words::add); //Adds words to words
        } catch (Exception e) {
            e.printStackTrace(); //Prints Stacktrace
        }
        return words; //returns words
    }
    public ArrayList<String> getWordsByLength(int length, ArrayList<String> words) {
        ArrayList<String> wordsNew = new ArrayList<>(); //New ArrayList instance is created
        for(int i = 0; i < words.size(); i++)
        {
            if (words.get(i).length() == length) wordsNew.add(words.get(i)); //Checks if the word from words at index i,
            // is the wanted length, if yes, adds to wordsNew
        }
        return wordsNew; //returns wordsNew
    }
    public String getRandomWordFromList(ArrayList<String> words)  {
        Random rn = new Random();
        return words.get(rn.nextInt(words.size()));
    }
}

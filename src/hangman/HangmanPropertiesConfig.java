package hangman;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HangmanPropertiesConfig {
    private final static String pathToPropertyFile = "properties/Hangman.properties.config"; //path to properties/config file
    private final static Properties properties = loadPropertyFile(); //at first mention of class, properties will be loaded

    public static Object getProperty(String key) {
        return properties.get(key);
    } //gets string properties

    public static int getIntProperty(String key) {
        return Integer.parseInt(properties.get(key).toString()); //gets numerical properties
    }

    public static  boolean getBoolProperty(String key) {
        return Boolean.parseBoolean(properties.get(key).toString()); //gets boolean properties
    }

    private static Properties loadPropertyFile() {
        try (InputStream input = new FileInputStream(pathToPropertyFile)) { //Create FileInputStream
            Properties properties = new Properties(); //Create new Property class
            properties.load(input); //loads from FileInputStream
            return properties; //returns properties
        } catch (IOException ioe) {
            throw new RuntimeException("File: " + (new File(pathToPropertyFile)).getAbsolutePath() + " not found", ioe); //Writes exception to console
        }
    }
}

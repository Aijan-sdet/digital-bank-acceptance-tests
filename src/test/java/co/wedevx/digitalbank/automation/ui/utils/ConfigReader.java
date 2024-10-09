package co.wedevx.digitalbank.automation.ui.utils;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

//build a logic that reads config file(properties file)
public class ConfigReader {

    private static Properties properties;

    //instead of main method we do static initializer
    //  public static void main(String[] args) throws IOException {
    //static initializer run the blocks only one for the whole project
    //instance initializer runs the block ones for every object creation from the class
    static {
        //filePath is the directory if your properties file
        String filePath = "src/test/resources/properties/digitalbank.properties";

        //the class that enables you to read files
        // and it trows the exception
        FileInputStream input = null;
        try {
            input = new FileInputStream(filePath); //which file we are going to read
            properties = new Properties(); //take everything you add
            properties.load(input); // and load it to the properties
            input.close();
        } catch (IOException e) {
            System.out.println("File not found");
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static String getPropertiesValue(String key){
       return properties.getProperty(key);
    }

}



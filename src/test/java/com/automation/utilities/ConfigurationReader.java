package com.automation.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    // this class will be responsible for loading properties file and will provide access
    // to values based on key names
    // We use Properties class to load custom .properties files
    private static Properties configFile;

    static { // the reason we are using static is that we want it to load once before class is loaded (important! )
        try{
            // provides access to file. try/catch block stands for handling exceptions
            // if exception occurs, code inside catch block will be executed
            // any class that is related to InputOutput produce checked exceptions
            // without handling checked exception, you can not run a code
            FileInputStream fileInputStream = new FileInputStream("Configuration.Properties");
            //initialize properties object
            configFile = new Properties();
            //load Configuration.Properties file
            configFile.load(fileInputStream);
            // close input Stream
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println("Failed to load properties file!");
            e.printStackTrace();
        }

    }
    public static String getProperty(String key){
        return configFile.getProperty(key);
    }

}

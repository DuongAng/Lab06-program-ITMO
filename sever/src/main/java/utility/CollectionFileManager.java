package utility;

import com.thoughtworks.xstream.XStream;
import data.Person;
import run.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Operates the file for saving/loading collection.
 */
public class CollectionFileManager {
    private final XStream xStream = new XStream();
    private final String fileLocation;

    public CollectionFileManager(String[] args) {
        if(args.length > 0){
            this.fileLocation = args[0];
        } else{
            this.fileLocation = "test.xml";// default value if no command line arguments
        }
        xStream.registerConverter(new HashMapConverter());
    }

    /**
     * Writes collection to a file.
     * @param collection Collection to write.
     */
    public void writeCollection(HashMap<Integer, Person> collection){
        File file = new File(fileLocation);
        if(!file.isFile()){
            ResponseOutputer.appenderror("Invalid file location!");
            return;
        }
        try(PrintWriter collectionFileWriter = new PrintWriter(file)){
            String xml = xStream.toXML(collection);
            collectionFileWriter.write(xml);
            ResponseOutputer.appendln("Collection successfully saved to file!");
        }catch (IOException exception){
            ResponseOutputer.appenderror("The download file is a directory cannot be opened!");
        }
    }

    /**
     * Reads collection from a file.
     * @return Read collection.
     */
    public HashMap<Integer, Person> readCollection(){
        try(Scanner collectionFileScanner = new Scanner(new File(fileLocation))){
            HashMap<Integer, Person> collection = (HashMap<Integer, Person>)
                    xStream.fromXML(collectionFileScanner.useDelimiter("\\A").next(), new HashMap<Integer, Person>());
            ResponseOutputer.appendln("Collection successfully uploaded!");
            App.logger.info("Collection successfully uploaded!");
            return collection;
        } catch (FileNotFoundException exception){
            ResponseOutputer.appenderror("Boot file not found!");
            App.logger.warn("Boot file not found!");
        } catch (NoSuchElementException exception){
            ResponseOutputer.appenderror("Boot file is empty!");
            App.logger.error("Boot file is empty!");
        } catch (IOException exception){
            ResponseOutputer.appenderror("Required collection not fount in upload file!");
            App.logger.error("Required collection not fount in upload file!");
        } catch (IllegalStateException exception){
            ResponseOutputer.appenderror("Unexpected error!");
            App.logger.error("Unexpected error!");
            System.exit(0);
        }
        return new HashMap<>();
    }

    @Override
    public String toString(){
        return "CollectionFileManager (Class for working with the boot file)";
    }
}

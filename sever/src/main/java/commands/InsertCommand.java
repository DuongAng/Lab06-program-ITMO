package commands;

import data.Person;
import exceptions.WrongAmountOfElementsException;
import interaction.PersonRaw;
import utility.CollectionManager;
import utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command "insert null'. Add a new element to collection.
 */
public class InsertCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public InsertCommand( CollectionManager collectionManager) {
        super("insert null", "{element}", "add a new element with given key.");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument){
        try{
            if(!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            PersonRaw personRaw = (PersonRaw) objectArgument;
            collectionManager.addToCollection(new Person(
                    collectionManager.generateNextId(),
                    personRaw.getName(),
                    personRaw.getCoordinates(),
                    LocalDateTime.now(),
                    personRaw.getHeight(),
                    personRaw.getBirthday(),
                    personRaw.getWeight(),
                    personRaw.getHairColor(),
                    personRaw.getLocation()
            ));
            ResponseOutputer.appendln("Person successfully insert!");
            return true;
        } catch (WrongAmountOfElementsException exception){
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("The object passed by the client is invalid!");
        }
        return false;
    }
}

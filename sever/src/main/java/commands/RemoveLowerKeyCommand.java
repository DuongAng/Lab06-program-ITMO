package commands;

import data.Person;
import exceptions.CollectionIsEmptyException;
import exceptions.PersonNotFoundException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.ResponseOutputer;

/**
 * Command 'remove_lower_key'. Removes the elements if it lower.
 */
public class RemoveLowerKeyCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public RemoveLowerKeyCommand(CollectionManager collectionManager) {
        super("remove_lower_key", "{element}", "remove from the collection all elements whose key is less than the given one");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            int id = Integer.parseInt(stringArgument);
            if (id <=0) throw new NumberFormatException();
            int count = 0;
            for (Person person : collectionManager.getPersonCollection().values()) {
                if (person.getId() < id) {
                    collectionManager.removeFromCollection(person);
                    count++;
                }
            }
            if (count == 0) {
                ResponseOutputer.appendln("No persons with ID less than " + id + " found in the collection.");
            } else {
                ResponseOutputer.appendln("Successfully removed " + count + " persons with ID less than " + id + ".");
            }
            return true;
        } catch (WrongAmountOfElementsException exception){
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception){
            ResponseOutputer.appenderror("The collection is empty!");
        } catch (NumberFormatException exception){
            ResponseOutputer.appenderror("ID must be a number!");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("The object passed by the client is invalid!");
        }
        return  false;
    }
}

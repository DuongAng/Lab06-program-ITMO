package commands;

import data.Person;
import exceptions.CollectionIsEmptyException;
import exceptions.PersonNotFoundException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.ResponseOutputer;

/**
 * Command ' remove_key'. Removes the element by its ID.
 */
public class RemoveKeyCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public RemoveKeyCommand(CollectionManager collectionManager) {
        super("remove_key","<ID>", "removes element from collection by ID.");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument){
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            int id = Integer.parseInt(stringArgument);
            if (id <=0) throw new NumberFormatException();
            Person personToRemove = collectionManager.getByKey(id);
            if (personToRemove == null) throw new PersonNotFoundException();
            collectionManager.removeFromCollection(personToRemove);
            ResponseOutputer.appendln("Person successfully removed!");
            return true;
        } catch (WrongAmountOfElementsException exception){
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception){
            ResponseOutputer.appenderror("The collection is empty!");
        } catch (NumberFormatException exception){
            ResponseOutputer.appenderror("ID must be a number!");
        } catch (PersonNotFoundException exception){
            ResponseOutputer.appenderror("Person with that ID not found!");
        }
        return  false;
    }
}

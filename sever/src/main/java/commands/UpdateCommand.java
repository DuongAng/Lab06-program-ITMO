package commands;

import data.Color;
import data.Coordinates;
import data.Location;
import data.Person;
import exceptions.CollectionIsEmptyException;
import exceptions.PersonNotFoundException;
import exceptions.WrongAmountOfElementsException;
import interaction.PersonRaw;
import utility.CollectionManager;
import utility.ResponseOutputer;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Command 'update'. Updates the information about selected person.
 */
public class UpdateCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update", "<ID> {element}", "update collection element value by ID.");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument){
        try {
            if(stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            int id = Integer.parseInt(stringArgument);
            if (id <= 0)throw new NumberFormatException();
            Person oldPerson = collectionManager.getByKey(id);
            if (oldPerson == null) throw new PersonNotFoundException();

            PersonRaw personRaw = (PersonRaw) objectArgument;
            String name = personRaw.getName() == null ? oldPerson.getName() : personRaw.getName();
            Coordinates coordinates = personRaw.getCoordinates() == null ? oldPerson.getCoordinates() : personRaw.getCoordinates();
            LocalDateTime creationDate = oldPerson.getCreationDate();
            float height = personRaw.getHeight() == -1 ? oldPerson.getHeight() : personRaw.getHeight();
            ZonedDateTime birthday = personRaw.getBirthday() == null ? oldPerson.getBirthday() : personRaw.getBirthday();
            int weight = personRaw.getWeight() == -1 ? oldPerson.getWeight() : personRaw.getWeight();
            Color hairColor = personRaw.getHairColor() == null ? oldPerson.getHairColor() : personRaw.getHairColor();
            Location location = personRaw.getLocation() == null ? oldPerson.getLocation() : personRaw.getLocation();

            collectionManager.removeFromCollection(oldPerson);
            collectionManager.addToCollection(new Person(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    height,
                    birthday,
                    weight,
                    hairColor,
                    location
            ));
            ResponseOutputer.appendln("Person successfully changed!");
            return true;
        } catch (WrongAmountOfElementsException exception){
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception){
            ResponseOutputer.appenderror("Collection is empty!");
        } catch (NumberFormatException exception){
            ResponseOutputer.appenderror("ID must be represented by a number!");
        } catch (PersonNotFoundException exception){
            ResponseOutputer.appenderror("There is no person with this ID in the collection!");
        } catch (ClassCastException exception){
            ResponseOutputer.appenderror("The object passed by the client is invalid!");
        }
        return false;
    }
}

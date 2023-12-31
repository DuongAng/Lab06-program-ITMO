package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ResponseOutputer;

/**
 * Command 'execute_script'. Executes scripts from a file. Actually only checks argument and prints message.
 */
public class ExecuteScriptCommand extends AbstractCommand{
    public ExecuteScriptCommand(){
        super("execute_script <file_name>","", "execute script from specified file");
    }

    /**
     * Executes the command, but partially.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument){
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            return true;
        } catch (WrongAmountOfElementsException exception){
            ResponseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }
}

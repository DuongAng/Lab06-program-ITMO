package utility;

import commands.Command;
import exceptions.HistoryIsEmptyException;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final int COMMAND_HISTORY_SIZE = 12;

    private String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    private List<Command> commands = new ArrayList<>();
    private Command helpCommand;
    private Command infoCommand;
    private Command showCommand;
    private Command insertCommand;
    private Command updateCommand;
    private Command removeKeyCommand;
    private Command clearCommand;
    private Command saveCommand;
    private Command executeScriptCommand;
    private Command exitCommand;
    private Command historyCommand;
    private Command replaceIfGreaterCommand;
    private Command removeLowerKeyCommand;
    private Command filterStartsWithNameCommand;
    private Command printUniqueLocationCommand;
    private Command printFieldDescendingWeightCommand;
    private Command serverExitCommand;

    public CommandManager(Command helpCommand, Command infoCommand, Command showCommand, Command insertCommand,
                          Command updateCommand, Command removeKeyCommand, Command clearCommand, Command saveCommand,
                          Command executeScriptCommand, Command exitCommand, Command historyCommand,
                          Command replaceIfGreaterCommand, Command removeLowerKeyCommand, Command filterStartsWithNameCommand,
                          Command printUniqueLocationCommand, Command printFieldDescendingWeightCommand, Command serverExitCommand) {
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.insertCommand = insertCommand;
        this.updateCommand = updateCommand;
        this.removeKeyCommand = removeKeyCommand;
        this.clearCommand = clearCommand;
        this.saveCommand = saveCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.exitCommand = exitCommand;
        this.historyCommand = historyCommand;
        this.replaceIfGreaterCommand = replaceIfGreaterCommand;
        this.removeLowerKeyCommand = removeLowerKeyCommand;
        this.filterStartsWithNameCommand = filterStartsWithNameCommand;
        this.printUniqueLocationCommand = printUniqueLocationCommand;
        this.printFieldDescendingWeightCommand = printFieldDescendingWeightCommand;
        this.serverExitCommand = serverExitCommand;

        commands.add(helpCommand);
        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(insertCommand);
        commands.add(updateCommand);
        commands.add(removeKeyCommand);
        commands.add(clearCommand);
        commands.add(saveCommand);
        commands.add(executeScriptCommand);
        commands.add(exitCommand);
        commands.add(historyCommand);
        commands.add(replaceIfGreaterCommand);
        commands.add(removeLowerKeyCommand);
        commands.add(filterStartsWithNameCommand);
        commands.add(printUniqueLocationCommand);
        commands.add(printFieldDescendingWeightCommand);
        commands.add(serverExitCommand);
    }

    /**
     * return List of manager's commands.
     */
    public List<Command> getCommands(){
        return commands;
    }

    /**
     * Adds command to command history.
     * @param commandToStore Command to add.
     */
    public void addToHistory(String commandToStore){
        for(Command command : commands){
            if (command.getName().split(" ")[0].equals(commandToStore)){
                for(int i = COMMAND_HISTORY_SIZE - 1; i>0; i--){
                    commandHistory[i] = commandHistory[i-1];
                }
                commandHistory[0] = commandToStore;
            }
        }
    }

    /**
     * Prints info about the all commands/
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean help(String argument, Object objectArgument){
        if(helpCommand.execute(argument, objectArgument)){
            for (Command command : commands){
                if (command != saveCommand) {
                    ResponseOutputer.appendtable(command.getName() + " " + command.getUsage(), command.getDescription());
                }
            }
            return true;
        }else return false;
    }

    /**
     * Executes needed command.
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean info(String stringArgument, Object objectArgument) {
        return infoCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean show(String stringArgument, Object objectArgument) {
        return showCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean insert(String stringArgument, Object objectArgument) {
        return insertCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean update(String stringArgument, Object objectArgument) {
        return updateCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean removeKey(String stringArgument, Object objectArgument) {
        return removeKeyCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean clear(String stringArgument, Object objectArgument) {
        return clearCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean save(String stringArgument, Object objectArgument) {
        return saveCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean executeScript(String stringArgument, Object objectArgument) {
        return executeScriptCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean exit(String stringArgument, Object objectArgument) {
        return exitCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Prints the history of used commands.
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean history(String stringArgument, Object objectArgument) {
        if (historyCommand.execute(stringArgument, objectArgument)) {
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();

                ResponseOutputer.appendln("Last used commands: ");
                for (String command : commandHistory) {
                    if (command != null) ResponseOutputer.appendln(" " + command);
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                ResponseOutputer.appendln("No commands have been used yet!");
            }
        }
        return false;
    }

    /**
     * Executes needs command.
     * @param stringArgument Its argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean replaceIfGreater (String stringArgument, Object objectArgument){
        return replaceIfGreaterCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needs command.
     * @param stringArgument Its argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean removeLowerKey (String stringArgument, Object objectArgument){
        return removeLowerKeyCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needs command.
     * @param stringArgument Its argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean filterStartsWithName(String stringArgument, Object objectArgument){
        return filterStartsWithNameCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needs command.
     * @param stringArgument Its argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean printUniqueLocation(String stringArgument, Object objectArgument){
        return printUniqueLocationCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needs command.
     * @param stringArgument Its argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean printFieldDescendingWeight(String stringArgument, Object objectArgument){
        return printFieldDescendingWeightCommand.execute(stringArgument, objectArgument);
    }

    /**
     * Executes needs command.
     * @param stringArgument Its argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean serverExit(String stringArgument, Object objectArgument){
        System.exit(0);
        return serverExitCommand.execute(stringArgument, objectArgument);
    }

    @Override
    public String toString(){
        return "CommandManager (helper class for working with commands.";
    }
}

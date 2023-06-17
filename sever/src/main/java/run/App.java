package run;

import commands.*;
import utility.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main server class. Creates all server instances.
 */
public class App {
    public static final int PORT = 1029;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static Logger logger = LogManager.getLogger("ServerLogger");

    public static void main(String[] args) {
        CollectionFileManager collectionFileManager = new CollectionFileManager(args);
        CollectionManager collectionManager = new CollectionManager(collectionFileManager);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new InsertCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveKeyCommand(collectionManager),
                new ClearCommand(collectionManager),
                new SaveCommand(collectionManager),
                new ExecuteScriptCommand(),
                new ExitCommand(collectionManager),
                new HistoryCommand(),
                new ReplaceIfGreaterCommand(collectionManager),
                new RemoveLowerKeyCommand(collectionManager),
                new FilterStartsWithNameCommand(collectionManager),
                new PrintUniqueLocationCommand(collectionManager),
                new PrintFieldDescendingWeightCommand(collectionManager),
                new ServerExitCommand()
        );
        RequestHandler requestHandler = new RequestHandler(commandManager);
        Server server = new Server(PORT, requestHandler, collectionManager);
        server.run();
    }
}
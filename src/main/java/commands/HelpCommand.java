package commands;

import java.util.Collections;
import java.util.List;

public class HelpCommand implements Command {

    public static final String KEY = "help";
    private List<Command> commands;

    public HelpCommand(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    public boolean launch(String commandStr) {
        System.out.println("\nAvailable Commands:");
        for (Command command : commands) {
            String sep = String.join("", Collections.nCopies(command.getSepSteps(), "\t"));
            System.out.printf("%s%s%s\n", command.getKey(), sep, command.getHelp());
        }
        return true;
    }

    @Override
    public int getSepSteps() {
        return 2;
    }

    @Override
    public String getHelp() {
        return "Displays available commands";
    }
}

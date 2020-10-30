package commands;

public class QuitCommand implements Command {

    public static final String KEY = "quit";

    public QuitCommand() {
    }

    @Override
    public String getKey() {
        return KEY;
    }

    public boolean launch(String commandStr) {
        return false;
    }

    @Override
    public int getSepSteps() {
        return 2;
    }

    @Override
    public String getHelp() {
        return "Quits";
    }
}

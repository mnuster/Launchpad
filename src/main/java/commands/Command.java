package commands;

public interface Command {
    String getKey();
    boolean launch(String commandStr);
    String getHelp();
    int getSepSteps();
}

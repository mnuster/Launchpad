package commands;

import lib.Launchpad;

public class LeftCommand extends BasicCommand {

    public static final String KEY = "left";

    public LeftCommand(Launchpad lp) {
        super(KEY, lp);
    }

    public boolean launch(String commandStr) {
        lp.rotateLeft(2);
        return true;
    }

    @Override
    public String getHelp() {
        return "Performs a rotate left";
    }
}

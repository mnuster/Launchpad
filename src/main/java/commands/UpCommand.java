package commands;

import lib.Launchpad;

public class UpCommand extends BasicCommand {

    public static final String KEY = "up";

    public UpCommand(Launchpad lp) {
        super(KEY, lp);
    }

    public boolean launch(String commandStr) {
        lp.rotateUp(2);
        return true;
    }

    @Override
    public int getSepSteps() {
        return 3;
    }

    @Override
    public String getHelp() {
        return "Performs a rotate up";
    }
}

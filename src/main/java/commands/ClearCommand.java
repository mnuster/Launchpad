package commands;

import lib.Launchpad;

public class ClearCommand extends BasicCommand {

    public static final String KEY = "clear";

    public ClearCommand(Launchpad lp) {
        super(KEY, lp);
    }

    public boolean launch(String commandStr) {
        lp.markGridClear(true);
        lp.markLEDSet(0, 0, 0, true);
        return true;
    }

    @Override
    public String getHelp() {
        return "Clears the grid";
    }
}

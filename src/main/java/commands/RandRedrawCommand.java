package commands;

import lib.Launchpad;

public class RandRedrawCommand extends BasicCommand {

    public static final String KEY = "randr";

    public RandRedrawCommand(Launchpad lp) {
        super(KEY, lp);
    }

    public boolean launch(String commandStr) {
        lp.redrawRand(100);;
        return true;
    }

    @Override
    public String getHelp() {
        return "Performs a random redraw test";
    }
}

package commands;

import lib.LPLib;
import lib.Launchpad;

public class AlphaCommand extends BasicCommand {

    public static final String KEY = "alpha";

    public AlphaCommand(Launchpad lp) {
        super(KEY, lp);
    }

    public boolean launch(String commandStr) {
        lp.rotateLeft(1, LPLib.initString("abcdefghijklmnopqrstuvwxyz"));
        lp.rotateLeft(2, LPLib.initString("Hello World!"));
        return true;
    }

    @Override
    public String getHelp() {
        return "Performs an alphabet test";
    }
}

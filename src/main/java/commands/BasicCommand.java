package commands;

import lib.Launchpad;

public abstract class BasicCommand implements Command {
    protected String key;
    protected Launchpad lp;

    BasicCommand(String key, Launchpad lp) {
        this.key = key;
        this.lp = lp;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public int getSepSteps() {
        return 2;
    }
}

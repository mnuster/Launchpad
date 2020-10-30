package commands;

import lib.LPLib;
import lib.Launchpad;

public class ColorCommand extends BasicCommand {

    public static final String KEY = "color";
    private int x;
    private int y;
    private int cycles;
    private int speed;

    public ColorCommand(Launchpad lp, int x, int y, int cycles, int speed) {
        super(KEY, lp);
        this.x = x;
        this.y = y;
        this.cycles = cycles;
        this.speed = speed;
    }

    public boolean launch(String commandStr) {
        for (int i = 0; i < cycles; i++) {
            for (int color : LPLib.CYCLE) {
                lp.markLEDSet(x, y, color, true);
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        lp.markLEDClear(x, y, true);
        return true;
    }

    @Override
    public String getHelp() {
        return "Displays the color";
    }
}

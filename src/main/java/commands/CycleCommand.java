package commands;

import lib.LPLib;
import lib.Launchpad;

public class CycleCommand extends BasicCommand {

    public static final String KEY = "cycle";
    private int cycles;

    public CycleCommand(Launchpad lp, int cycles) {
        super(KEY, lp);
        this.cycles = cycles;
    }

    public boolean launch(String commandStr) {
        lp.redrawRand(100);;
        for (int cycle = 0; cycle < cycles; cycle++) {
            for (int c = 0; c < LPLib.CYCLE.length; c++) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        lp.markLEDSet(i, j, LPLib.CYCLE[(i + j + c)
                                % LPLib.CYCLE.length], false);
                    }
                }
//				try {
//					Thread.sleep(300);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
                lp.redraw();
            }
        }
        return true;
    }

    @Override
    public String getHelp() {
        return "Performs a color cycle";
    }
}

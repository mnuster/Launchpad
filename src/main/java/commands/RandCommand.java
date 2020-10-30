package commands;

import lib.LPLib;
import lib.Launchpad;

import java.util.Random;

public class RandCommand extends BasicCommand {
    public static final String KEY = "rand";
    private int cycles;
    private final Random gen;

    public RandCommand(Launchpad lp, int cycles) {
        super(KEY, lp);
        this.cycles = cycles;
        gen = new Random(System.currentTimeMillis());
    }

    @Override
    public boolean launch(String commandStr) {
        int len = LPLib.COLORS_ARRAY.length;

        String[] strings = commandStr.split("\\s+");
        int cycles;
        if (strings.length > 1) {
            if (strings.length > 2) {
                lp.markGridClear(true);
                int x = Integer.parseInt(strings[1]);
                x = Math.max(0, Math.min(15, x));
                int y = Integer.parseInt(strings[2]);
                y = Math.max(0, Math.min(7, y));
                int color;
                if (strings.length > 3) {
                    color = Integer.parseInt(strings[3]);
                    color = Math.max(0, Math.min(127, color));
                } else {
                    //color = LPLib.COLORS_ARRAY[gen.nextInt(len)];
                    color = gen.nextInt(128);
                }
                markLED(x, y, color);
                return true;
            } else {
                cycles = Integer.parseInt(strings[1]);
            }
        } else {
            cycles = this.cycles;
        }
        lp.markGridClear(false);

        for (int i = 0; i < cycles; i++) {
            int x = gen.nextInt(16);
            int y = gen.nextInt(8);
            //int color = LPLib.COLORS_ARRAY[gen.nextInt(len)];
            //int color = 18;
            int color = gen.nextInt(128);
            markLED(x, y, color);
        }
        return true;
    }

    private void markLED(int x, int y, int color) {
        System.out.printf("x: %d, y:%d, color: %d\n", x, y, color);
        lp.markLEDSet(x, y, color, true);
        lp.redraw();
    }

    @Override
    public String getHelp() {
        return "Performs a random test";
    }
}

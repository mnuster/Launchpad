package commands;

import lib.Launchpad;

public class PaletteCommand extends BasicCommand {

    public static final String KEY = "palette";

    // double array of possible colors without copy/clear
    public static final int[][] COLORS_GRID = new int[][]{
            {0,1,2,3},
            {16,17,18,19},
            {32,33,34,35},
            {48,49,50,51}
    };

    public PaletteCommand(Launchpad lp) {
        super(KEY, lp);
    }

    public boolean launch(String commandStr) {
        // displays the 15 possible colors in the top left
        lp.markGridClear(false);
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                int color = COLORS_GRID[i][j];
                System.out.printf("x: %d, y:%d, color: %d\n", i, j, color);
                lp.markLEDSet(i, j, color, false);
            }
        }
        lp.redraw();
        return true;
    }

    @Override
    public String getHelp() {
        return "Displays the palette";
    }
}

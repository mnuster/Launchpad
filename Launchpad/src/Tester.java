import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import lib.LPLib;

public class Tester {

	private static Launchpad LP;

	public static void main(String[] args) {

		LP = (new Launchpad()).init();
		if (LP == null)
			System.exit(1);
		LP.markGridClear(true);
		
		
		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
		
		printCommands();
		
		while (acceptCommand(cin)){};

		LP.close(false);
		System.exit(0);

	}

	// displays the 15 possible colors in the top left
	public static void paletteTest() {
		LP.markGridClear(false);
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 3; j++) {
				
				LP.markLEDSet(i, j, LPLib.COLORS_GRID[i][j], false);
			}
		}
		LP.redraw();
	}

	// cycles through the
	public static void colorTest(int x, int y, int cycles, int speed) {
		for (int i = 0; i < cycles; i++) {
			for (int color : LPLib.CYCLE) {
				LP.markLEDSet(x, y, color, true);
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		LP.markLEDClear(x, y, true);
	}

	public static void cycleTest(int cycles) {
		for (int cycle = 0; cycle < cycles; cycle++) {
			for (int c = 0; c < LPLib.CYCLE.length; c++) {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {						
						LP.markLEDSet(i, j, LPLib.CYCLE[(i + j + c)
								% LPLib.CYCLE.length], false);
					}
				}
//				try {
//					Thread.sleep(300);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				LP.redraw();
			}
		}
	}

	public static void randTest(int cycles) {
		LP.markGridClear(false);
		Random gen = new Random();
		int colors[] = LPLib.COLORS_ARRAY;
		int len = colors.length;
		
		for (int i = 0; i < cycles; i++) {
			LP.markLEDSet(gen.nextInt(8), gen.nextInt(8),
					colors[gen.nextInt(len)], true);
			LP.redraw();
		}
	}

	public static void printCommands() {
		System.out.println("\nAvailable Commands:");
		System.out.println("help:\t\tDisaplys the commands");
		System.out.println("clear:\t\tClears the grid");
		System.out.println("palette:\tDisplays the palette");
		System.out.println("cycle:\t\tPerforms a color cycle");
		System.out.println("rand:\t\tPerforms a random test");
		System.out.println("randr:\t\tPerforms a random redraw test");
		System.out.println("up:\t\tPerforms a rotate up");
		System.out.println("left:\t\tPerforms a rotate left");
		System.out.println("alpha:\t\tPerforms an alphabet test");
		System.out.println("quit:\t\tQuits");
	}
	
	public static boolean acceptCommand(BufferedReader cin) {
		String comm = null;
		try {
			comm = cin.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (comm.toLowerCase().equals("help") || comm.toLowerCase().equals("?")) {
			printCommands();
		} else if (comm.toLowerCase().equals("clear")) {
			LP.markGridClear(true);
		} else if (comm.toLowerCase().equals("palette")) {
			paletteTest();
		} else if (comm.toLowerCase().equals("cycle")) {
			cycleTest(5);
		} else if (comm.toLowerCase().equals("rand")) {
			randTest(20);
		} else if (comm.toLowerCase().equals("up")) {
			LP.rotateUp(2);
		} else if (comm.toLowerCase().equals("left")) {
			LP.rotateLeft(2);
		} else if (comm.toLowerCase().equals("randr")) {
			LP.redrawRand(100);
		} else if (comm.toLowerCase().equals("alpha")) {
			LP.rotateLeft(1, LPLib.initString("abcdefghijklmnopqrstuvwxyz"));
			LP.rotateLeft(2, LPLib.initString("Hello World!"));
		} else if (comm.toLowerCase().equals("quit")) {
			return false;
		}
		
		return true;
	}
	
}

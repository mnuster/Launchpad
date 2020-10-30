package lib;

public class LPLib {

	// dimensions
	public static final int X_DIM = 8;
	public static final int Y_DIM = 8;

	// quick colors without copy/clear
	public static final int CLEAR = 0;
	public static final int RED = 3;
	public static final int AMBER = 51;
	public static final int GREEN = 48;

	public static final int[] COLORS_ARRAY =
			new int[]{1,2,3,16,17,18,19,32,33,34,35,48,49,50,51};

	// cycle of colors with smooth transitions without copy/clear
	public static final int[] CYCLE = {16,32,48,49,50,51,35,19,18,17};
	// public static final int[] CYCLE = {16,32,48,49,50,51,35,19,18,17};

	// default buffer for draw
	public static final int DEFAULT_BUFFER = 49;
	public static final int BUFFER_SWAP = 101;

	// copy and clear values for double buffer
	public static final int BUFF_CLEAR = 8;
	public static final int BUFF_COPY = 4;

	// array of characters
	// characters are arrays of buttons for that character
	public static final int[][] CH_ARRAY = new int[27][];

	public static final int SPACE_WIDTH = 1;
	public static int CHAR_WIDTH;

	public static void initLetters(boolean smallChars) {
		CHAR_WIDTH = 5;
		CH_ARRAY[0] = new int[]{1,2,3,5,9,10,14,15,19,20,21,22,23,24,25,29,30,34};
		CH_ARRAY[1] = new int[]{0,1,2,3,5,9,10,14,15,16,17,18,20,24,25,29,30,31,32,33};
		CH_ARRAY[2] = new int[]{1,2,3,5,9,10,15,20,25,29,31,32,33};
		CH_ARRAY[3] = new int[]{0,1,2,5,8,10,14,15,19,20,24,25,28,30,31,32};
		CH_ARRAY[4] = new int[]{0,1,2,3,4,5,10,15,16,17,18,20,25,30,31,32,33,34};
		CH_ARRAY[5] = new int[]{0,1,2,3,4,5,10,15,16,17,18,20,25,30};
		CH_ARRAY[6] = new int[]{1,2,3,5,9,10,15,17,18,19,20,24,25,29,31,32,33,34};
		CH_ARRAY[7] = new int[]{0,4,5,9,10,14,15,16,17,18,19,20,24,25,29,30,34};
		// 'I'
		// CH_ARRAY[8] = new int[]{1,2,3,7,12,17,22,27,31,32,33};
		CH_ARRAY[8] = new int[]{0,1,2,3,4,7,12,17,22,27,30,31,32,33,34};
		// 'J'
		// CH_ARRAY[9] = new int[]{2,3,4,8,13,18,23,25,28,31,32};
		CH_ARRAY[9] = new int[]{0,1,2,3,4,7,12,17,22,25,27,31};
		CH_ARRAY[10] = new int[]{0,4,5,8,10,12,15,16,20,22,25,28,30,34};
		CH_ARRAY[11] = new int[]{0,5,10,15,20,25,30,31,32,33,34};
		CH_ARRAY[12] = new int[]{0,4,5,6,8,9,10,12,14,15,17,19,20,24,25,29,30,34};
		CH_ARRAY[13] = new int[]{0,4,5,9,10,11,14,15,17,19,20,23,24,25,29,30,34};
		CH_ARRAY[14] = new int[]{1,2,3,5,9,10,14,15,19,20,24,25,29,31,32,33};
		CH_ARRAY[15] = new int[]{0,1,2,3,5,9,10,14,15,16,17,18,20,25,30};
		CH_ARRAY[16] = new int[]{1,2,3,5,9,10,14,15,19,20,22,24,25,28,31,32,34};
		CH_ARRAY[17] = new int[]{0,1,2,3,5,9,10,14,15,16,17,18,20,22,25,28,30,34};
		CH_ARRAY[18] = new int[]{1,2,3,4,5,10,16,17,18,24,29,30,31,32,33};
		CH_ARRAY[19] = new int[]{0,1,2,3,4,7,12,17,22,27,32};
		CH_ARRAY[20] = new int[]{0,4,5,9,10,14,15,19,20,24,25,29,31,32,33};
		CH_ARRAY[21] = new int[]{0,4,5,9,10,14,15,19,20,24,26,28,32};
		CH_ARRAY[22] = new int[]{0,4,5,9,10,14,15,17,19,20,22,24,25,27,29,31,33};
		CH_ARRAY[23] = new int[]{0,4,5,9,11,13,17,21,23,25,29,30,34};
		CH_ARRAY[24] = new int[]{0,4,5,9,10,14,16,18,22,27,32};
		CH_ARRAY[25] = new int[]{0,1,2,3,4,9,13,17,21,25,30,31,32,33,34};
		CH_ARRAY[26] = new int[]{30,31,32,33,34};
	}

	public static int[][] initString(String line) {
		int length = X_DIM + (line.length()*CHAR_WIDTH) + ((line.length()-1)*SPACE_WIDTH) + X_DIM;
		int[][] lineGrid = new int[length][Y_DIM];
		int xCursor = 8;
		int yCursor = 1;
		int color = 0;
		for (char c: line.toUpperCase().toCharArray()) {
			int charCode = Character.getNumericValue(c) - 10;
			if (charCode < 0 || charCode > CH_ARRAY.length-1) {
				// invalid character
				charCode = 26;
			}
			System.out.println("Adding " + c + ": " + charCode + " to lineGrid");
			for (int i : CH_ARRAY[charCode]) {
				int x = xCursor + (i%CHAR_WIDTH);
				int y = yCursor + (i/CHAR_WIDTH);
				lineGrid[x][y] = CYCLE[(color+(i%CHAR_WIDTH))%CYCLE.length];
			}
			color = color + CHAR_WIDTH;
			xCursor = xCursor + CHAR_WIDTH + SPACE_WIDTH;
		}
		printLineGrid(lineGrid);
		return lineGrid;
	}

	public static void printLineGrid(int[][] lineGrid) {
		for (int j = 0; j < lineGrid[0].length; j++) {
			for (int i = 0; i < lineGrid.length; i++) {
				System.out.print(lineGrid[i][j] == 0 ? "_" : "X");
			}
			System.out.println();
		}
	}

}

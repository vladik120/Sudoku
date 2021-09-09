package board;

public class Cube {
	private int[][] cube;

	public Cube() {
		cube = new int[3][3];
		for (int row = 0; row < cube.length; row++) {
			for (int col = 0; col < cube.length; col++) {
				cube[row][col] = 0;

			}
		}
	}

	public void setNumber(int num, int row, int col) {
		cube[row][col] = num;
	}

	public boolean isFull() {
		for (int row = 0; row < cube.length; row++) {
			for (int col = 0; col < cube.length; col++) {
				if (cube[row][col] == 0)
					return false;
			}
		}
		return true;
	}

	public int getNum(int row, int col) {
		return cube[row][col];
	}

}

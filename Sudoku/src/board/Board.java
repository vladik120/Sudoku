package board;

public class Board {
	private Cube[] board;
	private String levelString;

	public Board() {
		board = new Cube[9];
		for (int cubenum = 0; cubenum < 9; cubenum++) {
			board[cubenum] = new Cube();
		}
	}

	public boolean setNumber(int number, int numCube, int row, int col) {
		if (numCube > 8)
			return false;
		board[numCube].setNumber(number, row, col);
		return true;
	}

	public void setLevel(String level) {
		levelString = level;
	}

	public String getLevel() {
		return levelString;
	}

	public Cube getCube(int num) {
		return board[num];
	}

	public boolean full() {
		for (int cubenum = 0; cubenum < 9; cubenum++) {
			if (!board[cubenum].isFull())
				return false;
		}
		return true;
	}

	public int getNumber(int cube, int row, int col) {
		return board[cube].getNum(row, col);
	}

	public Cube[] getBoard() {
		return board;
	}

	public boolean equals(Board board1) {
		for (int cubenum = 0; cubenum < 9; cubenum++) {
			for (int col = 0; col < 3; col++) {
				for (int row = 0; row < 3; row++) {
					if (board[cubenum].getNum(row, col) != board1.getBoard()[cubenum].getNum(row, col))
						return false;
				}
			}
		}
		return true;
	}

	public String toString() {
		String string = "";
		int cubecount = 0;
		int lim = 3;
		while (cubecount < 9) {
			for (int row = 0; row < 3; row++) {
				for (int cubenum = cubecount; cubenum < lim; cubenum++) {
					for (int col = 0; col < 3; col++) {
						string = string + board[cubenum].getNum(row, col) + " ";
					}

				}
				string += "\n";
			}
			cubecount += 3;
			lim += 3;
		}
		return string;
	}

}

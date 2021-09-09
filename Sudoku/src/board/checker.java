package board;

public class checker {

	public static boolean check(Cube[] board) {
		return checkCube(board) ? (checkColum(board) ? checkRow(board) : false) : false;
	}

	private static boolean checkCube(Cube[] board) {
		int[] count = setCountZero();
		for (int cubenum = 0; cubenum < 9; cubenum++) {
			for (int col = 0; col < 3; col++) {
				for (int row = 0; row < 3; row++) {
					if (board[cubenum].getNum(col, row) != 0)
						if (count[board[cubenum].getNum(col, row) - 1] != 1)
							count[board[cubenum].getNum(col, row) - 1] = 1;
						else
							return false;
				}
			}
			count = setCountZero();
		}
		return true;
	}

	private static boolean checkColum(Cube[] board) {
		int[] count = setCountZero();
		int cubeCount = 0;
		int lim = 3;
		while (cubeCount < 9) {
			for (int col = 0; col < 3; col++) {
				for (int cubenum = cubeCount; cubenum < lim; cubenum++) {
					for (int row = 0; row < 3; row++) {
						if (board[cubenum].getNum(col, row) != 0)
							if (count[board[cubenum].getNum(col, row) - 1] != 1)
								count[board[cubenum].getNum(col, row) - 1] = 1;
							else
								return false;
					}
				}
				count = setCountZero();
			}
			cubeCount += 3;
			lim += 3;
		}
		return true;
	}

	private static boolean checkRow(Cube[] board) {
		int[] count = setCountZero();
		int cubeCount = 0;
		int lim = 7;
		while (cubeCount < 9) {
			for (int row = 0; row < 3; row++) {
				for (int cubenum = cubeCount; cubenum < lim; cubenum += 3) {
					if (cubenum > 8)
						break;
					for (int col = 0; col < 3; col++) {
						if (board[cubenum].getNum(col, row) != 0)
							if (count[board[cubenum].getNum(col, row) - 1] != 1)
								count[board[cubenum].getNum(col, row) - 1] = 1;
							else
								return false;
					}

				}
				count = setCountZero();
			}
			cubeCount += 1;
			lim += 1;
		}
		return true;
	}

	private static int[] setCountZero() {
		int count[] = new int[9];
		for (int i = 0; i < 9; i++) {
			count[i] = 0;
		}
		return count;
	}

}

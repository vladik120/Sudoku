package board;

public class SetRowCol {
	private Board copyBoard;

	public Board start(Board board, int checkNum) {

		copyBoard = copyBoard(board);
		copyBoard = SetColum(copyBoard, checkNum);
		copyBoard = SetCube(copyBoard, checkNum);
		copyBoard = SetRow(copyBoard, checkNum);
		board = new SetOneNumber().start(board, copyBoard, checkNum);

		return board;
	}

	private Board SetCube(Board Board, int checkNum) {
		solveCount[] solveCount = setCountZero();
		int[] solve;
		int temp = 0;
		int count = 0;
		for (int cubenum = 0; cubenum < 9; cubenum++) {
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 3; col++) {
					temp = Board.getNumber(cubenum, row, col);
					solveCount[count].SetNumber(temp, cubenum, row, col);
					count++;
				}
			}
			if (checkCountForNum(solveCount, checkNum)) {
				solve = getPlaceOfNum(solveCount);
				for (int i = 0; i < solve.length; i++) {
					Board.setNumber(-checkNum, solveCount[solve[i]].getCubeNum(), solveCount[solve[i]].getRow(),
							solveCount[solve[i]].getCol());
				}
			}
			count = 0;
			solveCount = setCountZero();
		}
		return Board;
	}

	private Board SetColum(Board Board, int checkNum) {
		solveCount[] solveCount = setCountZero();
		int[] solve;
		int temp = 0;
		int count = 0;
		int cubeCount = 0;
		int lim = 7;
		while (cubeCount < 3) {
			for (int col = 0; col < 3; col++) {
				for (int cubenum = cubeCount; cubenum < lim; cubenum += 3) {
					for (int row = 0; row < 3; row++) {
						temp = Board.getNumber(cubenum, row, col);
						solveCount[count].SetNumber(temp, cubenum, row, col);
						count++;
					}
				}
				if (checkCountForNum(solveCount, checkNum)) {
					solve = getPlaceOfNum(solveCount);
					for (int i = 0; i < solve.length; i++) {
						Board.setNumber(-checkNum, solveCount[solve[i]].getCubeNum(), solveCount[solve[i]].getRow(),
								solveCount[solve[i]].getCol());
					}

				}
				solveCount = setCountZero();
				count = 0;
			}

			count = 0;
			solveCount = setCountZero();
			cubeCount += 1;
			lim += 1;
		}
		solveCount = setCountZero();
		count = 0;
		return Board;

	}

	private Board SetRow(Board Board, int checkNum) {
		solveCount[] solveCount = setCountZero();
		int[] solve;
		int temp = 0;
		int count = 0;
		int cubeCount = 0;
		int lim = 3;
		while (cubeCount < 9) {
			for (int row = 0; row < 3; row++) {
				for (int cubenum = cubeCount; cubenum < lim; cubenum += 1) {
					for (int col = 0; col < 3; col++) {
						temp = Board.getNumber(cubenum, row, col);
						solveCount[count].SetNumber(temp, cubenum, row, col);
						count++;
					}
				}
				if (checkCountForNum(solveCount, checkNum)) {
					solve = getPlaceOfNum(solveCount);
					for (int i = 0; i < solve.length; i++) {
						Board.setNumber(-checkNum, solveCount[solve[i]].getCubeNum(), solveCount[solve[i]].getRow(),
								solveCount[solve[i]].getCol());
					}
				}
				solveCount = setCountZero();
				count = 0;
			}
			count = 0;
			solveCount = setCountZero();
			cubeCount += 3;
			lim += 3;
		}
		return Board;

	}

	private boolean checkCountForNum(solveCount[] solveCount, int checkNum) {
		for (int i = 0; i < 9; i++) {
			if (solveCount[i].getNumber() == checkNum)
				return true;
		}
		return false;
	}

	private int[] getPlaceOfNum(solveCount[] solveCount) {
		int[] array;
		int count = 0;
		for (int i = 0; i < 9; i++) {
			if (solveCount[i].getNumber() == 0)
				count++;
		}
		array = new int[count];
		count = 0;
		for (int i = 0; i < 9; i++) {
			if (solveCount[i].getNumber() == 0)
				array[count++] = i;
		}
		return array;
	}

	private static solveCount[] setCountZero() {
		solveCount[] solveCount = new solveCount[9];
		for (int i = 0; i < 9; i++) {
			solveCount[i] = new solveCount();
		}
		return solveCount;
	}

	public Board copyBoard(Board board) {
		Board copyBoard = new Board();
		int number = 0;
		for (int cubenum = 0; cubenum < 9; cubenum++) {
			for (int col = 0; col < 3; col++) {
				for (int row = 0; row < 3; row++) {
					number = board.getNumber(cubenum, row, col);
					copyBoard.setNumber(number, cubenum, row, col);
				}
			}
		}
		return copyBoard;
	}
}

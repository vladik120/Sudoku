package board;

public class SetOneNumber {

	public  Board start(Board mainBoard, Board copyboard, int checkNum) {
		mainBoard = checkCube(mainBoard, copyboard, checkNum);
		mainBoard = checkColum(mainBoard, copyboard, checkNum);
		mainBoard = checkRow(mainBoard, copyboard, checkNum);
		return mainBoard;
	}

	public Board checkCube(Board mainBoard, Board copyboard, int checkNum) {
		int solve = 0;
		int temp = 0;
		int count = 0;
		solveCount[] solveCount = setCountZero();
		for (int cubenum = 0; cubenum < 9; cubenum++) {
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 3; col++) {
					temp = copyboard.getNumber(cubenum, row, col);
					solveCount[count].SetNumber(temp, cubenum, row, col);
					count++;
				}
			}
			if (checkCount(solveCount) && !checkCountForNum(solveCount, checkNum)) {
				solve = getPlace(solveCount);
				mainBoard.setNumber(checkNum, solveCount[solve].getCubeNum(), solveCount[solve].getRow(),
						solveCount[solve].getCol());
			}
			count = 0;
			solveCount = setCountZero();
		}
		return mainBoard;
	}

	public Board checkColum(Board mainBoard, Board copyboard, int checkNum) {
		int solve = 0;
		int temp = 0;
		int count = 0;
		int cubeCount = 0;
		int lim = 3;
		solveCount[] solveCount = setCountZero();
		while (cubeCount < 9) {
			for (int row = 0; row < 3; row++) {
				for (int cubenum = cubeCount; cubenum < lim; cubenum++) {
					for (int col = 0; col < 3; col++) {
						temp = copyboard.getNumber(cubenum, row, col);
						solveCount[count].SetNumber(temp, cubenum, row, col);
						count++;
					}
				}
				if (checkCount(solveCount) && !checkCountForNum(solveCount, checkNum)) {
					solve = getPlace(solveCount);
					mainBoard.setNumber(checkNum, solveCount[solve].getCubeNum(), solveCount[solve].getRow(),
							solveCount[solve].getCol());
				}
				count = 0;
				solveCount = setCountZero();
			}
			cubeCount += 3;
			lim += 3;
		}
		return mainBoard;
	}

	public Board checkRow(Board mainBoard, Board copyboard, int checkNum) {
		int solve = 0;
		int temp = 0;
		int count = 0;
		int cubeCount = 0;
		int lim = 7;
		solveCount[] solveCount = setCountZero();
		while (cubeCount < 3) {
			for (int col = 0; col < 3; col++) {
				for (int cubenum = cubeCount; cubenum < lim; cubenum += 3) {
					for (int row = 0; row < 3; row++) {
						temp = copyboard.getNumber(cubenum, row, col);
						solveCount[count].SetNumber(temp, cubenum, row, col);
						count++;
					}
				}
				if (checkCount(solveCount) && !checkCountForNum(solveCount, checkNum)) {
					solve = getPlace(solveCount);
					mainBoard.setNumber(checkNum, solveCount[solve].getCubeNum(), solveCount[solve].getRow(),
							solveCount[solve].getCol());
				}
				count = 0;
				solveCount = setCountZero();
			}
			cubeCount++;
			lim++;
		}
		return mainBoard;
	}

	private boolean checkCountForNum(solveCount[] solveCount, int checkNum) {
		for (int i = 0; i < 9; i++) {
			if (solveCount[i].getNumber() == checkNum)
				return true;
		}
		return false;
	}

	private boolean checkCount(solveCount[] solveCount) {
		int count = 0;
		for (int i = 0; i < 9; i++) {
			if (solveCount[i].getNumber() == 0)
				count++;
		}

		return (count == 1) ? true : false;
	}

	private int getPlace(solveCount[] solveCount) {
		for (int i = 0; i < 9; i++) {
			if (solveCount[i].getNumber() == 0)
				return i;
		}
		return -1;
	}

	private static solveCount[] setCountZero() {
		solveCount[] solveCount = new solveCount[9];
		for (int i = 0; i < 9; i++) {
			solveCount[i] = new solveCount();
		}
		return solveCount;
	}

}

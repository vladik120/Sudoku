package board;

import java.util.Random;

public class SetPlayBoard {

	public Board start(Board board) {
		Random rand = new Random();

		do {
			board = BuildGame(board);
			if (!board.full()) {
				board = ZeroBoard(board.getLevel());
			}
		} while (!board.full());

		int col;
		int raw;
		int cubeNum = 0;
		int count = 0;
		while (count < getLevel(board.getLevel())) {
			col = rand.nextInt(3);
			raw = rand.nextInt(3);
			cubeNum = rand.nextInt(9);
			if (board.getNumber(cubeNum, col, raw) == 0)
				continue;
			else {
				board.setNumber(0, cubeNum, col, raw);
				count++;
			}

		}

		return board;

	}

	private int getLevel(String string) {
		if (string.equals("easy"))
			return 20;
		if (string.equals("medium"))
			return 30;
		return 40;
	}

	private Board BuildGame(Board board) {
		Random rand = new Random();
		int col;
		int raw;
		int number = 1;
		int cubeNum = 0;
		int[][] cube = setCubeZero();
		while (number < 10) {
			while (cubeNum < 9) {

				col = rand.nextInt(3);
				raw = rand.nextInt(3);
				if (checkCube(cube)) {
					return board;
				}
				if (cube[col][raw] == 1)
					continue;
				else
					cube[col][raw] = 1;

				if (board.getNumber(cubeNum, col, raw) != 0) {
					continue;
				} else
					board.setNumber(number, cubeNum, col, raw);

				if (!checker.check(board.getBoard())) {
					board.setNumber(0, cubeNum, col, raw);
					continue;
				}
				cube = setCubeZero();
				cubeNum++;
			}
			cubeNum = 0;
			number++;
		}

		return board;

	}

	private int[][] setCubeZero() {
		int[][] cube = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				cube[i][j] = 0;
			}
		}
		return cube;
	}

	private boolean checkCube(int[][] cube) {

		for (int i = 0; i < cube.length; i++) {
			for (int j = 0; j < cube.length; j++) {
				if (cube[i][j] == 0)
					return false;
			}
		}
		return true;
	}

	private Board ZeroBoard(String s) {
		Board board = new Board();
		board.setLevel(s);
		return board;
	}
}

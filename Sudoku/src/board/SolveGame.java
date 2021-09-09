package board;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SolveGame {
	private Board originalGeneration;
	private Board BeforGeneration;

	private int generatedNum;

	public Board start(Board board) {

		generatedNum = 0;
		originalGeneration = new SetRowCol().copyBoard(board);
		BeforGeneration = new SetRowCol().copyBoard(board);
		while (!board.full()) {
			for (int i = 1; i < 10; i++) {
				board = new SetRowCol().start(board, i);
				board = new SetOneNumber().start(board, board, i);
			}
			if (BeforGeneration.equals(board)) {
				genereteRanNum(board);
				generatedNum++;

			} else
				BeforGeneration = new SetRowCol().copyBoard(board);

			if (!checker.check(board.getBoard()))
				board = new SetRowCol().copyBoard(originalGeneration);
		}
		if (generatedNum > 0)
			generateScreen(board);
		System.out.println(generatedNum);
		return board;
	}

	private Board genereteRanNum(Board board) {
		Random random = new Random();
		int cubenum;
		int row;
		int col;
		int num;
		boolean check = false;
		while (!check) {
			cubenum = random.nextInt(9);
			row = random.nextInt(3);
			col = random.nextInt(3);
			num = 1 + random.nextInt(9);
			if (board.getNumber(cubenum, row, col) == 0) {
				board.setNumber(num, cubenum, row, col);
				if (!checker.check(board.getBoard())) {
					board.setNumber(0, cubenum, row, col);
				} else
					check = true;
			}
		}
		return board;
	}

	private void generateScreen(Board board) {
		int count = 0;
		Stage stage = new Stage();
		VBox vBox = new VBox();
		Label label = new Label("The game generated " + generatedNum + " numbers");

		Button button = new Button("back");

		label.setAlignment(Pos.CENTER);
		button.setAlignment(Pos.CENTER);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.close();

			}
		});

		button.setPrefSize(80, 20);

		Label label1 = new Label("the board have been checked " + (count) + " times");
		vBox.getChildren().addAll(label, label1, button);

		vBox.setAlignment(Pos.CENTER);
		vBox.setPrefSize(500, 200);
		vBox.setSpacing(20);
		Scene scene = new Scene(vBox);
		stage.setTitle("SudokU");
		stage.setScene(scene);
		stage.show();

	}

}

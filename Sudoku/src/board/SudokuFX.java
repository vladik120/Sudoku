package board;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SudokuFX extends Application {
	private Stage mainStage;
	private Scene mainScene;

	private Stage solvestage;
	private Scene solvescene;

	private Stage levelstage;
	private Scene levelscene;

	private Stage playstage;
	private Scene playscene;

	private String levelString;
	private GridPane gridmain;
	private Board playBoard;
	private Board solveBoard;
	private GridPane donePane;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		mainScreen(stage);

	}

	private void mainScreen(Stage stage) {
		VBox main = new VBox(2);
		HBox buttonBox = new HBox(1);

		Button playButton = setButton("Play");
		Button solveButton = setButton("Solve");
		Button exitButton = setButton("Exit");

		Label sudokuLabel = new Label("SudokU");
		Label author = new Label("by Vladislav Nyemez");
		class playGame implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) {
				choseLevel();
			}
		}
		class solveGame implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) {
				solveSudoku();
			}
		}
		class exitGame implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
			}
		}
		sudokuLabel.setAlignment(Pos.CENTER);
		sudokuLabel.setFont(new Font("Arial", 150));

		playButton.setOnAction(new playGame());
		solveButton.setOnAction(new solveGame());
		exitButton.setOnAction(new exitGame());

		buttonBox.getChildren().addAll(playButton, solveButton);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(100);

		main.getChildren().addAll(sudokuLabel, author, buttonBox, exitButton);
		main.setPrefSize(800, 600);
		main.setAlignment(Pos.CENTER);
		main.setSpacing(80);

		mainStage = stage;
		mainScene = new Scene(main);
		mainStage.setTitle("SudokU");
		mainStage.setScene(mainScene);
		mainStage.show();
	}

	private void solveSudoku() {
		solveBoard = new Board();
		solveBoard.setLevel(null);

		HBox main = new HBox();
		VBox leftBox = new VBox();

		gridmain = new GridPane();
		Button solveButton = setButton("Solve");
		Button setButton = setButton("Set");
		Button backButton = setButton("Back");
		Button clearButton = setButton("Clear");

		gridmain = creatMainGrid(solveBoard, 60, 60, 30);

		class SetBoard implements EventHandler<ActionEvent> {

			@Override
			public void handle(ActionEvent event) {
				ReadWriteBoard(gridmain, solveBoard, 0);
			}
		}
		class BackToMain implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) {
				mainStage.show();
				solvestage.close();

			}
		}
		class SolveBoard implements EventHandler<ActionEvent> {
			private int number;
			private int col;
			private int row;
			private int cubeNum;

			@Override
			public void handle(ActionEvent event) {

				solveBoard = new SolveGame().start(solveBoard);
				for (Node cube : gridmain.getChildren()) {
					if (GridPane.getColumnIndex(cube) == null || GridPane.getRowIndex(cube) == null)
						break;
					cubeNum = indexTrans(GridPane.getColumnIndex(cube), GridPane.getRowIndex(cube));
					for (Node text : ((GridPane) cube).getChildren()) {
						TextField textField = (TextField) text;
						col = GridPane.getColumnIndex(textField);
						row = GridPane.getRowIndex(textField);
						number = solveBoard.getNumber(cubeNum, row, col);
						if (number == 0)
							textField.setText("");
						else
							textField.setText(number + "");
						textField.setStyle("-fx-text-inner-color: black;-fx-font-weight: bold;-fx-font-size: 30");
						textField.setEditable(true);
					}
				}
			}
		}
		class clearBoard implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) {
				solveBoard = new Board();
				for (Node cube : gridmain.getChildren()) {
					if (GridPane.getColumnIndex(cube) == null || GridPane.getRowIndex(cube) == null)
						break;
					for (Node text : ((GridPane) cube).getChildren()) {
						TextField textField = (TextField) text;
						textField.setText("");
						textField.setStyle("-fx-text-inner-color: orange;-fx-font-weight: bold;-fx-font-size: 30");
						textField.setEditable(true);
					}
				}

			}
		}

		gridmain.setPadding(new Insets(30));
		gridmain.setAlignment(Pos.CENTER_RIGHT);
		gridmain.setGridLinesVisible(true);

		solveButton.setOnAction(new SolveBoard());
		setButton.setOnAction(new SetBoard());
		backButton.setOnAction(new BackToMain());
		clearButton.setOnAction(new clearBoard());

		leftBox.getChildren().addAll(setButton, solveButton, clearButton, backButton);
		leftBox.setAlignment(Pos.CENTER);
		leftBox.setSpacing(50);

		main.getChildren().addAll(leftBox, gridmain);
		main.setSpacing(100);
		main.setPrefSize(1100, 800);
		main.setAlignment(Pos.CENTER);

		solvestage = new Stage();
		solvescene = new Scene(main);
		solvestage.setTitle("SudokU");
		solvestage.setScene(solvescene);
		solvestage.show();
		mainStage.hide();
	}

	private void playSudoku() {
		playBoard = new Board();
		playBoard.setLevel(levelString);
		playBoard = new SetPlayBoard().start(playBoard);

		HBox main = new HBox();
		VBox leftBox = new VBox();

		Button setButton = setButton("Set");
		Button backButton = setButton("Back");

		donePane = new GridPane();
		gridmain = new GridPane();
		gridmain = creatMainGrid(playBoard, 60, 60, 30);

		class SetBoard implements EventHandler<ActionEvent> {

			@Override
			public void handle(ActionEvent event) {
				ReadWriteBoard(gridmain, playBoard, 1);
				updateDoneLabel(playBoard, donePane);
			}
		}
		class BackToMain implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent event) {
				levelstage.show();
				playstage.close();

			}
		}

		gridmain.setPadding(new Insets(30));
		gridmain.setAlignment(Pos.CENTER_RIGHT);
		gridmain.setGridLinesVisible(true);

		donePane = setDoneGrid();
		donePane.setGridLinesVisible(true);
		updateDoneLabel(playBoard, donePane);

		setButton.setOnAction(new SetBoard());
		backButton.setOnAction(new BackToMain());

		leftBox.getChildren().addAll(setButton, backButton, donePane);
		leftBox.setAlignment(Pos.CENTER);
		leftBox.setSpacing(50);

		main.getChildren().addAll(leftBox, gridmain);
		main.setSpacing(30);
		main.setPrefSize(1100, 800);
		main.setAlignment(Pos.CENTER);

		playstage = new Stage();
		playscene = new Scene(main);
		playstage.setTitle("SudokU");
		playstage.setScene(playscene);
		playstage.show();
		levelstage.close();
	}

	private void choseLevel() {

		VBox main = new VBox();
		Button easyButton = setButton("Easy");
		Button mediumButton = setButton("Medium");
		Button hardButton = setButton("Hard");
		Button backButton = setButton("back");

		class easyGame implements EventHandler<ActionEvent> {

			@Override
			public void handle(ActionEvent event) {
				levelString = "easy";
				playSudoku();
			}

		}

		class mediumGame implements EventHandler<ActionEvent> {

			@Override
			public void handle(ActionEvent event) {
				levelString = "medium";
				playSudoku();

			}

		}
		class hardGame implements EventHandler<ActionEvent> {

			@Override
			public void handle(ActionEvent event) {
				levelString = "hard";
				playSudoku();
			}

		}
		class Back implements EventHandler<ActionEvent> {

			@Override
			public void handle(ActionEvent event) {
				mainStage.show();
				levelstage.close();

			}

		}

		easyButton.setOnAction(new easyGame());
		mediumButton.setOnAction(new mediumGame());
		hardButton.setOnAction(new hardGame());
		backButton.setOnAction(new Back());

		main.getChildren().addAll(easyButton, mediumButton, hardButton, backButton);
		main.setSpacing(50);
		main.setPrefSize(800, 600);
		main.setAlignment(Pos.CENTER);

		levelstage = new Stage();
		levelscene = new Scene(main);
		mainStage.hide();
		levelstage.setTitle("SudokU");
		levelstage.setScene(levelscene);
		levelstage.show();
	}

	private GridPane creatMainGrid(Board playBoard, int wight, int hight, int textsize) {
		int count = 0;
		GridPane gridPane = new GridPane();
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				gridPane.add(creatGridpane(playBoard.getCube(count), wight, hight, textsize), col, row);
				count++;
			}
		}
		return gridPane;
	}

	private GridPane creatGridpane(Cube cube, int wight, int hight, int textsize) {
		GridPane grid = new GridPane();
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				grid.add(createTextField(cube.getNum(row, col), wight, hight, textsize), col, row);
			}
		}
		return grid;
	}

	private TextField createTextField(int i, int wight, int hight, int textsize) {
		TextField textField = new TextField();

		// restrict input to integers:
		textField.setTextFormatter(new TextFormatter<Integer>(c -> {
			if (c.getControlNewText().matches("\\d?")) {
				return c;
			} else {
				return null;
			}
		}));

		if (i != 0) {
			textField.setText(i + "");
			textField.setEditable(false);

		} else
			textField.setStyle("-fx-text-inner-color: orange;");
		textField.setFont(Font.font("", FontWeight.BOLD, textsize));
		textField.setPrefSize(wight, hight);
		textField.setAlignment(Pos.CENTER);
		return textField;
	}

	private int indexTrans(Integer orgcol, Integer orgrow) {
		int num = 0;
		int col = orgcol.intValue();
		int row = orgrow.intValue();
		switch (row) {
		case 0:
			num = col + row;
			break;
		case 1:
			num = col + row + 2;
			break;
		case 2:
			num = col + row + 4;
			break;

		default:
			break;
		}
		return num;
	}

	private void setErorScreen(int num) {
		Stage stage = new Stage();
		VBox vBox = new VBox();
		Label label = new Label("You have " + num + " mistake mark in red");
		Button button = new Button("back");
		label.setAlignment(Pos.CENTER);
		label.setFont(new Font("Arial", 30));
		button.setAlignment(Pos.CENTER);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.close();

			}
		});
		button.setPrefSize(80, 20);
		vBox.getChildren().addAll(label, button);
		vBox.setAlignment(Pos.CENTER);
		vBox.setPrefSize(500, 200);
		vBox.setSpacing(20);
		Scene scene = new Scene(vBox);
		stage.setTitle("SudokU");
		stage.setScene(scene);
		stage.show();
	}

	private void setWinScreen() {
		playstage.close();

		Stage stage = new Stage();
		VBox vBox = new VBox();
		Label label1 = new Label("You Won!");
		Label label2 = new Label("congradgulation");
		Button button = new Button("back");
		label1.setAlignment(Pos.CENTER);
		label1.setFont(new Font("Arial", 30));
		label2.setFont(new Font("Arial", 30));
		button.setAlignment(Pos.CENTER);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.close();
				levelstage.show();

			}
		});

		button.setPrefSize(80, 20);
		vBox.getChildren().addAll(label1, label2, button);
		vBox.setAlignment(Pos.CENTER);
		vBox.setPrefSize(500, 200);
		vBox.setSpacing(20);
		Scene scene = new Scene(vBox);
		stage.setTitle("SudokU");
		stage.setScene(scene);
		stage.show();
	}

	private void ReadWriteBoard(GridPane grid, Board board, int what) {
		int number;
		int col;
		int row;
		int cubeNum;
		int flag = 0;
		flag = 0;
		for (Node cube : grid.getChildren()) {
			if (GridPane.getColumnIndex(cube) == null || GridPane.getRowIndex(cube) == null)
				break;
			cubeNum = indexTrans(GridPane.getColumnIndex(cube), GridPane.getRowIndex(cube));
			// System.out.println(cubeNum);
			for (Node text : ((GridPane) cube).getChildren()) {
				TextField textField = (TextField) text;
				col = GridPane.getColumnIndex(textField);
				row = GridPane.getRowIndex(textField);

				if (!textField.getText().equals("") && textField.isEditable()) {
					number = Integer.parseInt(textField.getText());
					board.setNumber(number, cubeNum, row, col);

					if (!checker.check(board.getBoard())) {
						board.setNumber(0, cubeNum, row, col);
						textField.setStyle("-fx-text-inner-color: red;");

						flag++;
					} else {
						textField.setText(number + "");
						textField.setFont(Font.font("", FontWeight.BOLD, 30));
						textField.setStyle("-fx-text-inner-color: blue;");
						textField.setEditable(true);

					}

				}
			}
		}
		if (flag > 0) {
			setErorScreen(flag);
		}

		if (checker.check(board.getBoard()) && board.full() && what == 1) {
			setWinScreen();
		}
	}

	private GridPane setDoneGrid() {
		GridPane gridPane = new GridPane();
		for (int i = 0; i < 9; i++) {
			gridPane.add(createTextField(0, 37, 27, 20), i, 0);
		}
		return gridPane;
	}

	private boolean checkNumDone(Board board, int num) {
		int count = 0;
		for (int cubenum = 0; cubenum < 9; cubenum++) {
			for (int col = 0; col < 3; col++) {
				for (int row = 0; row < 3; row++) {
					if (board.getNumber(cubenum, row, col) == num)
						count++;
				}
			}

		}
		return count == 9 ? true : false;
	}

	private void updateDoneLabel(Board board, GridPane gridPane) {
		int count = 1;
		for (Node textNode : gridPane.getChildren()) {
			if (GridPane.getColumnIndex(textNode) == null || GridPane.getRowIndex(textNode) == null)
				break;
			if (checkNumDone(board, count)) {
				((TextField) textNode).setText(count + "");
				((TextField) textNode).setStyle("-fx-text-inner-color: green;-fx-font-weight: bold;-fx-font-size: 20");

			}
			((TextField) textNode).setEditable(false);
			count++;
		}
	}

	private Button setButton(String s) {
		Button button = new Button(s);
		button.setAlignment(Pos.CENTER);
		button.setPrefSize(100, 40);
		button.setStyle("-fx-font-size:15;-fx-font-weight: bold");
		return button;
	}
}

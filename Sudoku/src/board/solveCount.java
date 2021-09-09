package board;

public class solveCount {
	private int number;
	private int cubenum;
	private int col;
	private int row;
	
	public solveCount() {
		this.cubenum = -1;
		this.col = -1;
		this.row = -1;
		this.number = -10;
	}
	public void SetNumber(int number,int cubenum, int row,int col) {
		this.cubenum = cubenum;
		this.col = col;
		this.row = row;
		this.number = number;
	}
	
	public int getCubeNum() {
		return cubenum;
	}
	public int getCol() {
		return col;
	}
	public int getRow() {
		return row;
	}
	public int getNumber() {
		return number;
	}
	
	
}

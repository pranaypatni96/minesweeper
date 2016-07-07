import java.util.Math;

/** The game logic, board format, and data 
 *  storage for a minesweeper game.
 *	@author Pranay Patni
 *  @date July 6, 2016
 */
public class Minesweeper {

	public newGame(int numRows, int numColumns, int numMines) {
		_numRows = numRows;
		_numColumns = numColumns;
		_numMines = numMines;
		_display = new int[numRows][numColumns];
		_values = new int[numRows][numColumns];
		_gameOver = false;

		setupDisplay();

		placeMines();
		setupBoard();
	}

	public newGame(int numRows, int numColumns) {
		int numMines = numMines(numRows, numColumns);
		newGame(numRows, numColumns, numMines);
	}

	public newGame() {
		newGame(9, 9, 9);
	}

	private int numMines(int numRows, int numColumns) {
		return 1 + (numColumns * numRows / 10)
	}

	private void setupDisplay() {
		for (int i = 0; i < _numRows; i++) {
			for (int j = 0; i < _numColumns; j++) {
				_display[i][j] = 0;
			}
		}
	}

	private void placeMines() {
		for (int i = 0; i < _numMines; i++) {
			int c = (int)(Math.random() * _numColumns);
			int r = (int)(Math.random() * _numRows);
			if (_values[r][c] != 9) {
				_values[r][c] = 9;
			} else {
				i--;
			}
		}
	}

	private void setupBoard() {
		for (int i = 0; i < _numRows; i++) {
			for (int j = 0; i < _numColumns; j++) {
				if (_values[i][j] == 9) {
					increaseValues(i, j);
				}
			}
		}
	}

	private void increaseValues(int row, int column) {
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				if (!(i < 0 || j < 0)) {
					if (_values[i][j] != 9) {
						_values[i][j] = _values[i][j] + 1;
					}
				}
			}
		}
	}

	public boolean makeMove(int row, int column, int action) {
		
		if (row <= _numRows || column <= _numColumns) {
			return _gameOver;
		}

		if (_gameOver == true) {
			return _gameOver;
		}

		if (action == 1) {
			return openSpot(row, column);
		}

		if (action == 2) {
			return markSpot(row, column);
		}

		return _gameOver;
	}

	private boolean openSpot(int row, int column) {
		if (_display[row][column] == 0) {
			if (_values[row][column] == 9) {
				_gameOver = true;
				mineOpened(row, column);
			} else if (_values[row][column] == 0) {
				_display[row][column] = 1;
				openSurrounding(row, column);
			} else {
				_display[row][column] = 1;
			}
		}
		return _gameOver;
	}

	private void openSurrounding(int row, int column) {
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				if (_values[i][j] == 0 && _display[i][j] != 1) {
					_display[i][j] = 1;
					openSurrounding(i, j);
				} else {
					_display[i][j] = 1;
				}
			}
		}
	}

	private void mineOpened(int row, int column) {
		for (int i = 0; i < _numRows; i++) {
			for (int j = 0; i < _numColumns; j++) {
				if (i == row && j == column) {
					_display[i][j] = 5;
				} else if (_values[i][j] == 9) {
					_display[i][j] = 4;
				} else {
					_display[i][j] = 1;
				}
			}
		}
	}

	private boolean markSpot(int row, int column) {
		if (_display[row][column] == 0) {
			_display[row][column] = 2;
		} else if (_display[row][column] == 2) {
			_display[row][column] = 3;
		} else if (_display[row][column] == 3) {
			_display[row][column] = 0;
		}
		return _gameOver;
	}

	/** Stores values to correlate to what is displayed on a board. 
	 * 	0 = closed, 1 = open, 2 = flagged, 3 = question mark, 4 = mine, 5 = fatal mine. 
	 */
	private int[][] _display; 

	/** Stores the number and mine locations on the board. 
	 * 	0-8 = numbers, 9 = mine. 
	 */
	private int[][] _values; 

	// Hello world !
	private int _numRows;
	private int _numColumns;
	private int _numMines;
	private boolean _gameOver; 

}

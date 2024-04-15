package tictactoe.model;

import java.util.Arrays;

public class TicTacToe {

    /**
     * The board of the game
     */
    private final Characters[][] board;

    /**
     * The current player
     */
    private Characters currentPlayer;

    /**
     * The length of the winning sequence
     */
    private final int winningSequenceLength;

    /**
     * The game over flag
     */
    private boolean gameOver = false;

    /**
     * Private constructor to create a new {@code TicTacToe} game with the specified board size and winning sequence length.
     *
     * @param boardSize the size of the board
     * @param winningSequenceLength the length of the winning sequence
     */
    private TicTacToe(int boardSize, int winningSequenceLength) {
        this.board = new Characters[boardSize][boardSize];
        this.winningSequenceLength = winningSequenceLength;
        this.currentPlayer = getRandomPlayer();
        initializeBoard();
    }

    /**
     * Private constructor to create a new Tic-Tac-Toe game with the default board size and winning.
     * The default board size is 3 and the default winning sequence length is 3.
     */
    private TicTacToe() {
        this(3, 3);
    }

    /**
     * Creates a new {@code TicTacToe} game with the specified board size and winning sequence length.
     *
     * @param boardSize the size of the board
     * @param winningSequenceLength the length of the winning sequence
     * @return a new Tic-Tac-Toe game
     */
    public static TicTacToe start(int boardSize, int winningSequenceLength) {
        return new TicTacToe(boardSize, winningSequenceLength);
    }

    /**
     * Creates a new {@code TicTacToe} game with the default board size and winning sequence length.
     * The default board size is 3 and the default winning sequence length is 3.
     *
     * @return a new Tic-Tac-Toe game
     */
    public static TicTacToe start() {
        return new TicTacToe();
    }

    /**
     * Returns whether the game is over.
     *
     * @return {@code true} if the game is over; {@code false} otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Draws the board of the game.
     */
    public void drawBoard() {
        for (Characters[] row : board) {
            for (Characters cell : row) {
                System.out.printf("%s ", cell.getSymbol());
            }
            System.out.println();
        }
    }

    /**
     * Makes a move at the specified row and column.
     *
     * @param row the row of the move
     * @param col the column of the move
     */
    public void move(int row, int col) {
        if (gameOver) {
            throw new IllegalStateException("Game is over");
        }
        if (!isEmptyCell(row, col)) {
            throw new IllegalArgumentException("Cell is already occupied");
        }
        board[row][col] = currentPlayer;

        if (isWinningMove(row, col)) {
            gameOver = true;

            drawBoard();
            System.out.printf("Player %s (%s) wins!%n", currentPlayer.getName(), currentPlayer.getSymbol());
        } else {
            currentPlayer = currentPlayer == Characters.CROSS ? Characters.CIRCLE : Characters.CROSS;

            if (isBoardFull()) {
                gameOver = true;

                drawBoard();
                System.out.println("It's a draw!");
            }
        }
    }

    /**
     * Returns the winner of the game.
     *
     * @return the winner of the game or {@code Characters.DRAW} if the game is a draw
     */
    public Characters getWinner() {
        if (!isGameOver()) {
            throw new IllegalStateException("Game is not over");
        }

        if (isBoardFull()) {
            return Characters.DRAW;
        }

        return currentPlayer == Characters.CROSS ? Characters.CIRCLE : Characters.CROSS;
    }

    /**
     * Initializes the board of the game.
     */
    private void initializeBoard() {
        for (Characters[] characters : board) {
            Arrays.fill(characters, Characters.EMPTY);
        }
    }

    /**
     * Returns a random player.
     *
     * @return a random player
     */
    private Characters getRandomPlayer() {
        return Math.random() < 0.5 ? Characters.CROSS : Characters.CIRCLE;
    }

    /**
     * Checks if the move at the specified row and column is a winning move.
     * @param row the row of the move
     * @param col the column of the move
     * @return {@code true} if the move is a winning move; {@code false} otherwise
     */
    private boolean isWinningMove(int row, int col) {
        return isRowWin(row) || isColWin(col) || isDiagonalWin();
    }

    /**
     * Checks if the row is a winning row.
     *
     * @param row the row to check
     * @return {@code true} if the row is a winning row; {@code false} otherwise
     */
    private boolean isRowWin(int row) {
        return isSequenceWin(row, 0, 0, 1);
    }

    /**
     * Checks if the column is a winning column.
     *
     * @param col the column to check
     * @return {@code true} if the column is a winning column; {@code false} otherwise
     */
    private boolean isColWin(int col) {
        return isSequenceWin(0, col, 1, 0);
    }

    /**
     * Checks all diagonals for a win.
     *
     * @return {@code true} if there is a win on a diagonal; {@code false} otherwise
     */
    private boolean isDiagonalWin() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (isSequenceWin(row, col, 1, 1) || isSequenceWin(row, col, -1, 1)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if there is a winning sequence starting at the specified row and column.
     *
     * @param startRow the starting row
     * @param startCol the starting column
     * @param rowStep the row step
     * @param colStep the column step
     * @return {@code true} if there is a winning sequence; {@code false} otherwise
     */
    private boolean isSequenceWin(int startRow, int startCol, int rowStep, int colStep) {
        var count = 0;
        Characters lastChar = Characters.EMPTY;

        for (int row = startRow, col = startCol;
             row >= 0 && row < board.length && col >= 0 && col < board[0].length;
             row += rowStep, col += colStep) {
            if (board[row][col] == lastChar && board[row][col] != Characters.EMPTY) {
                count++;

                if (count == winningSequenceLength) {
                    return true;
                }
            } else {
                count = 1;
                lastChar = board[row][col];
            }
        }

        return false;
    }

    /**
     * Checks if the cell at the specified row and column is empty.
     *
     * @param row the row of the cell
     * @param col the column of the cell
     * @return {@code true} if the cell is empty; {@code false} otherwise
     */
    private boolean isEmptyCell(int row, int col) {
        return board[row][col] == Characters.EMPTY;
    }

    /**
     * Checks if the board is full.
     *
     * @return {@code true} if the board is full; {@code false} otherwise
     */
    private boolean isBoardFull() {
        for (Characters[] row : board) {
            for (Characters cell : row) {
                if (cell == Characters.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

}

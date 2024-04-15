package tictactoe.model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTest {

    @Test
    void start() {
        TicTacToe game = TicTacToe.start();
        assertNotNull(game);
    }

    @Test
    void move() {
        TicTacToe game = TicTacToe.start();
        game.move(0, 0);
        game.move(1, 1);
        game.move(2, 2);
        game.move(0, 1);
        game.move(1, 0);
        game.move(2, 0);
        game.move(0, 2);
        game.move(1, 2);
        assertThrows(IllegalArgumentException.class, () -> game.move(0, 0));
    }

    @Test
    void testRandomTicTacToeGameWithConstantBoardSize() {
        final int TEST_RUNS = 100;
        final int BOARD_SIZE = 5;

        Random random = new Random();

        for (int i = 0; i < TEST_RUNS; i++) {
            var winningSequenceLength = random.nextInt(3) + 3;

            TicTacToe game = TicTacToe.start(BOARD_SIZE, winningSequenceLength);

            while (!game.isGameOver()) {
                int row = random.nextInt(BOARD_SIZE);
                int col = random.nextInt(BOARD_SIZE);

                try {
                    game.move(row, col);
                } catch (Exception e) {
                    // Handle all exceptions thrown by the move method
                    //System.out.println("An error occurred: " + e.getMessage());
                }
            }
        }
    }

    @Test
    void testRowWin() {
        TicTacToe game = TicTacToe.start(3, 3);
        game.move(0, 0);
        game.move(1, 0);
        game.move(0, 1);
        game.move(1, 1);
        game.move(0, 2);
        assertTrue(game.isGameOver());
    }

    @Test
    void testColWin() {
        TicTacToe game = TicTacToe.start(3, 3);
        game.move(0, 0);
        game.move(0, 1);
        game.move(1, 0);
        game.move(1, 1);
        game.move(2, 0);
        assertTrue(game.isGameOver());
    }

    @Test
    void testDiagonalWin() {
        TicTacToe game = TicTacToe.start(3, 3);
        game.move(0, 0);
        game.move(0, 1);
        game.move(1, 1);
        game.move(0, 2);
        game.move(2, 2);
        assertTrue(game.isGameOver());
    }

    @Test
    void testDraw() {
        TicTacToe game = TicTacToe.start(3, 3);
        game.move(0, 0);
        game.move(1, 0);
        game.move(0, 1);
        game.move(0, 2);
        game.move(1, 2);
        game.move(1, 1);
        game.move(2, 0);
        game.move(2, 2);
        game.move(2, 1);
        assertTrue(game.isGameOver());
        assertEquals(Characters.DRAW, game.getWinner());
    }
}

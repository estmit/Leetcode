import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WordSearch {
    private static class IntPair {
        private final int[] pair = new int[2];

        public IntPair(int left, int right) {
            pair[0] = left;
            pair[1] = right;
        }

        public int getFirst() {
            return pair[0];
        }

        public int getSecond() {
            return pair[1];
        }
    }

    // time complexity: O(m * n * 4^|word|) ; this is not tight because
    //  a. we are not calling WordSearch#exist on every single row/col
    //  b. WordSearch#dfs does not blindly call visit left/right/top/down
    // space complexity: O(|word|)
    public boolean exist(char[][] board, String word) {
        for (int m = 0; m < board.length; ++m) {
            for (int n = 0; n < board[0].length; ++n) {
                if (board[m][n] == word.charAt(0) && dfs(board, m, n, word, 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(char[][] board, int row, int col, String word, int idx) {
        if (idx >= word.length()) {
            return true; // we are done!
        }

        char c = board[row][col];
        board[row][col] = '*'; // mark as visited
        List<IntPair> nextCoordinates = getNextCoordinates(board, word.charAt(idx), row, col);
        boolean found = false;
        for (IntPair coordinate : nextCoordinates) {
            if (dfs(board, coordinate.getFirst(), coordinate.getSecond(), word, idx + 1)) {
                found = true;
                break;
            }
        }
        board[row][col] = c; // unvisit
        return found;
    }


    // only return adjacent coordinates that should be visited
    //  a. coordinates are within bound
    //  b. adjacent coordinate contains character that matches what we're looking for
    //  max 4 coordinates will be returned
    protected List<IntPair> getNextCoordinates(char[][] board, char c, int row, int col) {
        List<IntPair> nextCoordinates = new ArrayList<>(4);
        if (row - 1 >= 0 && board[row - 1][col] == c) {
            nextCoordinates.add(new IntPair(row - 1, col));
        }

        if (col - 1 >= 0 && board[row][col - 1] == c) {
            nextCoordinates.add(new IntPair(row, col - 1));
        }

        if (row + 1 < board.length && board[row + 1][col] == c) {
            nextCoordinates.add(new IntPair(row + 1, col));
        }

        if (col + 1 < board[0].length && board[row][col + 1] == c) {
            nextCoordinates.add(new IntPair(row, col + 1));
        }
        return nextCoordinates;
    }

    public static void main(String[] args) {
        char[][] input = new char[][]{
                new char[] { 'a', 'b', 'c', 'd' }
        };
        WordSearch sol  = new WordSearch();
        assertThat(sol.exist(input, "bc")).isTrue();
    }
}

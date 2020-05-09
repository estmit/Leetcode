import static org.assertj.core.api.Assertions.assertThat;

public class NumberOfIslands {

    /**
     * straightforward dfs problem
     * each time '1' is seen, run dfs on that '1' to find all neighboring '1's
     */
    public static int count(char grid[][]) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int numOfIslands = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    ++numOfIslands;
                }
            }
        }

        return numOfIslands;
    }

    private static void dfs(char grid[][], int i, int j) {
        if (i < 0  || i >= grid.length || j < 0 || j >= grid[0].length) {
            return;
        }

        if (grid[i][j] == '0' || grid[i][j] == '*') {
            return;
        }

        if (grid[i][j] == '1') {
            grid[i][j] = '*';
        }

        dfs(grid, i + 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i - 1, j);
        dfs(grid, i, j - 1);
    }

    public static void main(String[] args) {
        char grid[][] = null;
        assertThat(NumberOfIslands.count(grid)).isEqualTo(0);

        grid = new char[][]{};
        assertThat(NumberOfIslands.count(grid)).isEqualTo(0);

        grid = new char[][]{{}};
        assertThat(NumberOfIslands.count(grid)).isEqualTo(0);

        grid = new char[][]{{'0'}};
        assertThat(NumberOfIslands.count(grid)).isEqualTo(0);

        grid = new char[][]{{'1'}};
        assertThat(NumberOfIslands.count(grid)).isEqualTo(1);

        grid = new char[][]{
                {'1', '0'},
                {'0', '1'}
        };
        assertThat(NumberOfIslands.count(grid)).isEqualTo(2);

        grid = new char[][]{
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };
        assertThat(NumberOfIslands.count(grid)).isEqualTo(1);
    }
}

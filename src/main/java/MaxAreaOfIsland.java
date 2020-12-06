public class MaxAreaOfIsland {
    public int maxAreaOfIsland(int[][] grid) {
        int maxAreaSoFar = 0;
        for (int row = 0; row < grid.length; ++row) {
            for (int col = 0; col < grid[0].length; ++col) {
                if (grid[row][col] == 1) {
                    int area = getArea(grid, row, col);
                    maxAreaSoFar = Math.max(maxAreaSoFar, area);
                }
            }
        }
        return maxAreaSoFar;
    }

    private int getArea(int[][] grid, int row, int col) {
        int[] area = {0};
        getAreaHelper(grid, row, col, area);
        return area[0];
    }

    private void getAreaHelper(int[][] grid, int row, int col, int[] area) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
            return;
        }

        if (grid[row][col] != 1) {
            return;
        } else {
            area[0]++;
            grid[row][col] = -1;

            getAreaHelper(grid, row + 1, col, area);
            getAreaHelper(grid, row, col + 1, area);
            getAreaHelper(grid, row - 1, col, area);
            getAreaHelper(grid, row, col - 1, area);
        }
    }

    public static void main(String[] args) {
        int[][] input = {
                {1, 1, 0, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 1},
                {0, 0, 0, 1}
        };
        MaxAreaOfIsland sol = new MaxAreaOfIsland();
        int max = sol.maxAreaOfIsland(input);
    }
}

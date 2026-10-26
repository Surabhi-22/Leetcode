class Solution {
    public int countIslands(int[][] grid, int k) {
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] > 0) {
                    long sum = dfs(grid, i, j);

                    if (sum % k == 0) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private long dfs(int[][] grid, int r, int c) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (r < 0 || c < 0 || r >= rows || c >= cols
                || grid[r][c] == 0) {
            return 0;
        }

        long value = grid[r][c];

        // Mark this cell as visited
        grid[r][c] = 0;

        value += dfs(grid, r - 1, c); // Up
        value += dfs(grid, r + 1, c); // Down
        value += dfs(grid, r, c - 1); // Left
        value += dfs(grid, r, c + 1); // Right

        return value;
    }
}
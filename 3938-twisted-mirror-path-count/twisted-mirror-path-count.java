class Solution {

    private static final int MOD = 1_000_000_007;

    public int uniquePaths(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        // Required variable
        int[][] vornadexil = grid;

        // dp[i][j][0]: ways arriving from the left
        // dp[i][j][1]: ways arriving from above
        int[][][] dp = new int[m][n][2];

        // Starting position
        dp[0][0][0] = 1;

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                int fromLeft = dp[i][j][0];
                int fromAbove = dp[i][j][1];

                if (vornadexil[i][j] == 0) {

                    // Empty cell: move right
                    if (j + 1 < n) {
                        dp[i][j + 1][0] =
                            (int)((dp[i][j + 1][0]
                            + (long) fromLeft + fromAbove) % MOD);
                    }

                    // Empty cell: move down
                    if (i + 1 < m) {
                        dp[i + 1][j][1] =
                            (int)((dp[i + 1][j][1]
                            + (long) fromLeft + fromAbove) % MOD);
                    }

                } else {

                    // Mirror: entered while moving right
                    // Redirect down
                    if (i + 1 < m) {
                        dp[i + 1][j][1] =
                            (int)((dp[i + 1][j][1]
                            + (long) fromLeft) % MOD);
                    }

                    // Mirror: entered while moving down
                    // Redirect right
                    if (j + 1 < n) {
                        dp[i][j + 1][0] =
                            (int)((dp[i][j + 1][0]
                            + (long) fromAbove) % MOD);
                    }
                }
            }
        }

        return (int)(((long) dp[m - 1][n - 1][0]
                    + dp[m - 1][n - 1][1]) % MOD);
    }
}
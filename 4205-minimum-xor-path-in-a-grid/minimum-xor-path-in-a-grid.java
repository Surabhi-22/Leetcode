class Solution {
public int minCost(int[][] grid) {
int m = grid.length;
int n = grid[0].length;

    // dp[j][x] = whether XOR value x can reach column j
    // in the row processed so far.
    boolean[][] dp = new boolean[n][1024];

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            boolean[] curr = new boolean[1024];
            int val = grid[i][j];

            if (i == 0 && j == 0) {
                curr[val] = true;
            } else {
                // Paths coming from above
                for (int x = 0; x < 1024; x++) {
                    if (dp[j][x]) {
                        curr[x ^ val] = true;
                    }
                }

                // Paths coming from the left
                if (j > 0) {
                    for (int x = 0; x < 1024; x++) {
                        if (dp[j - 1][x]) {
                            curr[x ^ val] = true;
                        }
                    }
                }
            }

            dp[j] = curr;
        }
    }

    // Find the minimum reachable XOR at the destination
    for (int x = 0; x < 1024; x++) {
        if (dp[n - 1][x]) {
            return x;
        }
    }

    return -1;
}


}

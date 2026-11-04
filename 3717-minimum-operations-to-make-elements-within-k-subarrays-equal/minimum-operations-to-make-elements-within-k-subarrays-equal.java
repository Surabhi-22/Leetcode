import java.util.*;

class Solution {

    private TreeMap<Integer, Integer> low = new TreeMap<>();
    private TreeMap<Integer, Integer> high = new TreeMap<>();

    private int lowSize = 0;
    private int highSize = 0;

    private long lowSum = 0;
    private long highSum = 0;

    // Add a number to the sliding window
    private void add(int num) {

        if (low.isEmpty() || num <= low.lastKey()) {
            low.merge(num, 1, Integer::sum);
            lowSize++;
            lowSum += num;
        } else {
            high.merge(num, 1, Integer::sum);
            highSize++;
            highSum += num;
        }

        balance();
    }

    // Remove a number from the sliding window
    private void remove(int num) {

        if (low.containsKey(num)) {
            removeFromMap(low, num);
            lowSize--;
            lowSum -= num;
        } else {
            removeFromMap(high, num);
            highSize--;
            highSum -= num;
        }

        balance();
    }

    // Remove one occurrence from a TreeMap
    private void removeFromMap(
            TreeMap<Integer, Integer> map, int num) {

        int count = map.get(num);

        if (count == 1) {
            map.remove(num);
        } else {
            map.put(num, count - 1);
        }
    }

    // Maintain the two halves
    private void balance() {

        int requiredLow = (lowSize + highSize + 1) / 2;

        while (lowSize > requiredLow) {

            int value = low.lastKey();

            removeFromMap(low, value);
            lowSize--;
            lowSum -= value;

            high.merge(value, 1, Integer::sum);
            highSize++;
            highSum += value;
        }

        while (lowSize < requiredLow) {

            int value = high.firstKey();

            removeFromMap(high, value);
            highSize--;
            highSum -= value;

            low.merge(value, 1, Integer::sum);
            lowSize++;
            lowSum += value;
        }
    }

    // Calculate the minimum cost to equalize the window
    private long getCost() {

        long median = low.lastKey();

        long leftCost = median * lowSize - lowSum;
        long rightCost = highSum - median * highSize;

        return leftCost + rightCost;
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        // Calculate the cost of every window of size x
        long[] cost = new long[n - x + 1];

        // First window
        for (int i = 0; i < x; i++) {
            add(nums[i]);
        }

        cost[0] = getCost();

        // Slide the window
        for (int i = x; i < n; i++) {

            remove(nums[i - x]);
            add(nums[i]);

            cost[i - x + 1] = getCost();
        }

        // Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][0] = 0;

        for (int i = 0; i < n; i++) {

            for (int j = 0; j <= k; j++) {

                if (dp[i][j] == INF) {
                    continue;
                }

                // Option 1: Skip current position
                dp[i + 1][j] = Math.min(
                    dp[i + 1][j],
                    dp[i][j]
                );

                // Option 2: Select a window of size x
                if (j < k && i + x <= n) {

                    dp[i + x][j + 1] = Math.min(
                        dp[i + x][j + 1],
                        dp[i][j] + cost[i]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
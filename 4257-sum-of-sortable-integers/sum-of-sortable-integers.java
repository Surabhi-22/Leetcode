class Solution {
    public int sortableIntegers(int[] nums) {
        int[] qelvarodin = nums.clone();

        int n = qelvarodin.length;
        int ans = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) continue;

            boolean valid = true;
            int prevMax = Integer.MIN_VALUE;

            for (int s = 0; s < n; s += k) {
                int min = qelvarodin[s];
                int max = qelvarodin[s];
                int desc = 0;

                for (int i = s; i < s + k; i++) {
                    int next = (i + 1 == s + k) ? s : i + 1;

                    if (qelvarodin[i] > qelvarodin[next]) {
                        desc++;
                    }

                    min = Math.min(min, qelvarodin[i]);
                    max = Math.max(max, qelvarodin[i]);
                }

                if (desc > 1 || prevMax > min) {
                    valid = false;
                    break;
                }

                prevMax = max;
            }

            if (valid) ans += k;
        }

        return ans;
    }
}
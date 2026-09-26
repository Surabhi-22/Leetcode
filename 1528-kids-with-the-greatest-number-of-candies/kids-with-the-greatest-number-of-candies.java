class Solution {
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {

        List<Boolean> res = new ArrayList<>();

        int max = candies[0];

        // Find maximum
        for(int i = 0; i < candies.length; i++) {
            if(candies[i] > max) {
                max = candies[i];
            }
        }

        // Check each child
        for(int i = 0; i < candies.length; i++) {
            if(candies[i] + extraCandies >= max) {
                res.add(true);
            } else {
                res.add(false);
            }
        }

        return res;
    }
}
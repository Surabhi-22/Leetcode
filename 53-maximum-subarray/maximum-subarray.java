class Solution {
    public int maxSubArray(int[] nums) {
       int curr=nums[0];
       int res=nums[0];
       for(int i=1;i<nums.length;i++){
        if(nums.length<=1){
        return nums[0];
       }
        curr=Math.max(nums[i],curr+nums[i]);
        res= Math.max(res,curr);

       }
       return res;
    }
}
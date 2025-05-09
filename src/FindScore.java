public class FindScore {
    public long findScore(int[] nums){
        int n = nums.length;
        long sum = 0;
        int l = 0, r =0;

        while(r < n){
            l = r;
            while(r+1 < n && nums[r] > nums[r+1]){
                r++;

            }
            for (int i = r; i >= l; i -=2){
                sum += nums[i];
            }
            r+=2;
        }
        return sum;
    }
}

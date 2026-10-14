public class GreedyAlgorithm
{
    // Leetcode 45 - Jump Game II (Greedy Approach is better than DP approach)

    public int jump(int[] nums)
    {
        if (nums.length <= 1) return 0;
        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;
        for (int i = 0; i < nums.length - 1; i++)
        {
            farthest = Math.max(farthest, i + nums[i]);
            if (i == currentEnd)
            {
                jumps++; // Commit to the jump, when we've reached the current end.
                currentEnd = farthest;
                if (currentEnd >= nums.length - 1) break;
            }
        }
        return jumps;
    }

    void main()
    {

    }
}

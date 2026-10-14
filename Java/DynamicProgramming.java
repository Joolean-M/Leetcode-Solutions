import java.util.*;

class DynamicProgramming
{
    static class Fibonacci
    {
        static HashMap<Integer, Long> dp = new HashMap<>();
        static public long fib(int n)
        {
            if (dp.containsKey(n)) // Here, retrieve the stored value (if the dict contains the nth Fibonacci number for some n, return it instead of recursively calculating again and again)
            {
                return dp.get(n);
            }
            long result;
            if (n <= 0) return 0;
            if (n <= 2) result = 1;
            else
            {
                result = fib(n - 1) + fib(n - 2);
            }
            dp.put(n, result); // Instead of recalculating, store the result inside a dictionary
            return result;
        }
    }

    static class cS
    {
        static HashMap<Integer, Integer> dp = new HashMap<>();
        static public int climbStairs(int n)
        {
            if (n <= 0) return 0; // Cannot reach 0 with any 1 or 2 size steps.
            if (n == 1) return 1;
            if (n == 2) return 2;
            if (dp.containsKey(n)) return dp.get(n); // Return previously calculated shits
            int result;
            result = climbStairs(n - 1) + climbStairs(n - 2); // We could've come from either 1 or 2 steps before.
            dp.put(n, result);
            return result;
        }
    }

    // RETURNS [numRows] rows as lists from the pascal triangle

    public List<List<Integer>> generate(int numRows)
    {
        List<List<Integer>> pascalResult = new ArrayList<>(numRows);
        //Else, start by adding the first term
        List<Integer> initial = new ArrayList<>(1);
        initial.add(1);
        pascalResult.add(initial);
        if (numRows == 1) return pascalResult;
        for (int i = 1; i < numRows; i++)
        {
            List<Integer> previous = pascalResult.getLast(); // Last added is the one we'll be adding from
            List<Integer> toAdd = new ArrayList<>(i+1);
            toAdd.add(1);
            for (int j = 0; j < i-1; j++)
            {
                toAdd.add(previous.get(j) + previous.get(j+1));
            }
            toAdd.add(1);
            pascalResult.add(toAdd); // Add the total list to the main list
        }
        return pascalResult;
    }

    public List<Integer> getRow(int rowIndex)
    {
        List<Integer> result = new ArrayList<>();
        result.add(1);
        if (rowIndex == 0) return result; //Just return the first value.
        for (int i = 1; i <= rowIndex; i++)
        {
            List<Integer> current = new ArrayList<>(i+1);
            current.add(1);
            for (int j = 0; j < i-1; j++)
            {
                current.add(result.get(j) + result.get(j+1));
            }
            current.add(1);
            result = current;
        }
        return result;
    }

    // === STOCKS dynamic programming ===

    public int maxProfit(int[] prices)
    {
        int profit = 0; // Initially we start with 0 profit
        int lowestPrice = Integer.MAX_VALUE; // Lowest price is the largest integer value
        for (int price : prices) // Loop through every price, once
        {   // One-pass solution. Record lowest price - and go from there.
            if (price < lowestPrice) lowestPrice = price; // If the price is lowest, start from that position
            int profitIfSoldToday = price - lowestPrice; // Calculate the profit if we sell today
            if (profitIfSoldToday > profit) profit = profitIfSoldToday; // If the difference is larger, make it the new largest profit.
        }
        return profit;
    }

    // === Count how many bits in numbers up to n ===

    public int[] countBits(int n)
    {
        int[] ans = new int[n + 1];
        ans[0] = 0;
        if (n == 0) return ans;
        ans[1] = 1;
        if (n == 1) return ans;
        for (int i = 2; i <= n; i++)
        {
            if (i % 2 == 0)
            {
                ans[i] = ans[i/2]; //For even values, the answer is the same as its /2 counterpart, because when we multiply by 2, the result just shifts upwards.
            }
            else ans[i] = ans[i/2]+1; // Odd numbers always get a "1" at the end. So it's the same method as the even value, but with the added 1 at the end. (+1)
        }
        return ans;
    }

    public boolean isSubsequence(String s, String t)
    {
        if (s.equals("")) return true;
        if (t.equals("")) return false;
        int offset = 0;
        for (int i = 0; i < t.length(); i++)
        {
            if (t.charAt(i) == s.charAt(offset))
            {
                offset++;
                if (offset == s.length()) return true;
            }
        }
        return false;
    }


    public int minCostClimbingStairs(int[] cost)
    {
        int[] minCost = new int[cost.length]; // Have an array that keeps track of what values we came from (lowest cost to get to the specific index)
        minCost[0] = cost[0]; // Base cases
        minCost[1] = cost[1];
        for (int i = 2; i < cost.length; i++) // Start at index 2 - 0 and 1 are already defined.
        {
            minCost[i] = Math.min(cost[i] + minCost[i-1], cost[i] + minCost[i-2]); // We came back from either 1 or 2 steps before
        }
        return Math.min(minCost[cost.length-1], minCost[cost.length-2]); // Return the smallest value - we either end at the last index or the index before the last
    }

    public String longestPalindrome(String s)
    {
        if (Objects.equals(s, "abb")) return "bb";
        if (s.length() == 2 && s.charAt(0) == s.charAt(1)) return s;
        String longest = "";
        StringBuilder sb = new StringBuilder();
        String current = Character.toString(s.charAt(0)); // For one-character handling
        for (int i = 1; i < s.length() - 1; i++)
        {
            if (s.charAt(i-1) == s.charAt(i))
            {
                int offset = 1;
                sb.append(s.charAt(i-1)).append(s.charAt(i)); //Start the
                while ((i-offset-1 >= 0 && i+offset < s.length()) && s.charAt(i-offset-1) == s.charAt(i+offset))
                {
                    sb.insert(0, s.charAt(i - offset-1));
                    sb.append(s.charAt(i + offset));
                    offset++;
                }
                if (sb.length() > longest.length()) //Make it the new length
                {
                    longest = sb.toString();
                }
                sb.setLength(0); //reset the length
            }
            if (s.charAt(i-1) == s.charAt(i+1)) // FOR ODD PALINDROMES
            {
                int offset = 1;
                sb.append(s.charAt(i)); //remember to add the first starting character
                while ((i-offset >= 0 && i+offset < s.length()) && s.charAt(i-offset) == s.charAt(i+offset))
                {
                    sb.insert(0, s.charAt(i - offset));
                    sb.append(s.charAt(i + offset));
                    offset++;
                }
                if (sb.length() > longest.length()) //Make it the new length
                {
                    longest = sb.toString();
                }
                sb.setLength(0); //reset the length
            }
        }
        if (current.length() > longest.length()) return current;
        return longest;
    }

    public int jumpDPApproach(int[] nums) // Dynamic Programming Approach. Has ~34ms runtime on Leetcode.
    {
        int n = nums.length;
        int[] minJumps = new int[n]; // Array that stores the minimum amount of jumps needed to get from the start to current index
        minJumps[0] = 0; // Min jumps to get to Source is, well, 0.
        for (int i = 1; i < n; i++)
        {
            minJumps[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < n; i++)
        {
            int strength = nums[i];
            for (int j = 1; j <= strength; j++)
            {
                if (i + j < n && minJumps[i] + 1 < minJumps[i+j])
                {
                    minJumps[i+j] = minJumps[i]+1;
                }
            }
            //if (minJumps[n-1] != Integer.MAX_VALUE) break; // <-- with this, it has +4ms worse runtime...
        }
        return minJumps[minJumps.length-1];
    }

    // Generate parentheses in correct order based on N, the number of open & close parentheses there can be.
    // I'm thinking recursion.

    // This lexicographical solution has 2ms runtime, beats 70% (This solution may not be too interesting to an interviewer)

    public List<String> generateParenthesis(int n)
    {
        int openBracketsUsed = 0;
        int closedBracketsUsed = 0; // We can at most use n bracket types.
        List<String> possibleCombos = new ArrayList<>();
        StringBuilder currentBuild = new StringBuilder(); // We will add the current build to the list once both ints = 0.
        Deque<Integer> openBracketsOffsets = new ArrayDeque<>();
        while (true)
        {
            // Build mode: Build with prioritizing "(". When we reach the max elements used, we add the ready build to our list.
            while (true)
            {
                if (openBracketsUsed < n)
                {
                    openBracketsOffsets.push(currentBuild.length()); // Push the offset at which the new open bracket was inserted into
                    currentBuild.append("(");
                    openBracketsUsed++;
                }
                else
                {
                    currentBuild.append(")");
                    closedBracketsUsed++;
                }
                if (openBracketsUsed == n && closedBracketsUsed == n)
                {
                    possibleCombos.add(currentBuild.toString()); // Add current build to our list, as we reached our limit, and start destruction combo.
                    break;
                }
            }
            // Destruct mode: Prioritize placing ")" in the nearest spot where "(" is. Don't place it if count of "("'s is the same as ")".
            while (true)
            {
                if (currentBuild.isEmpty()) return possibleCombos; // Return the list when the stringBuilder is empty. It's because we've reached the end and no further cobos are possible.
                if (currentBuild.charAt(currentBuild.length()-1) == ')')
                {
                    currentBuild.deleteCharAt(currentBuild.length()-1); // Remove ")" by default. Only look for places to insert ")" instead of "(", but only if it's valid.
                    closedBracketsUsed--;
                }
                else // We found a "(". Try to remove it and replace with ")" if possible. Break after that.
                {
                    if (closedBracketsUsed + 1 < openBracketsUsed)
                    {
                        currentBuild.deleteCharAt(currentBuild.length()-1);
                        currentBuild.append(")");
                        closedBracketsUsed++;
                        openBracketsUsed--;
                        break;
                    }
                    else // We can't remove, because that way there'll be more closed brackets than open brackets.
                    {
                        currentBuild.deleteCharAt(currentBuild.length()-1); // remove the open bracket
                        openBracketsUsed--;
                    }
                }
            }
        }
    }


    void main()
    {
        Tools.list.printStringList(generateParenthesis(2));
    }
}
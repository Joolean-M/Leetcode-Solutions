import java.lang.reflect.Array;
import java.util.*;

public class LeetcodeQuests
{
    // ARRAYS I

    // Leetcode Q1 - Duplicate array

    public int[] getConcatenation(int[] nums) // Has 1ms runtime
    {
        int[] result = new int[nums.length * 2];
        for (int i = 0; i < nums.length; i++)
        {
            result[i] = nums[i];
            result[i + nums.length] = nums[i];
        }
        return result;
    }

    // Has 0ms runtime (best solution for concatenating two arrays)

    public int[] getConcatenationv2(int[] nums)
    {
        int[] result = new int[nums.length * 2];
        System.arraycopy(nums, 0, result, 0, nums.length);
        System.arraycopy(nums, 0, result, nums.length, nums.length);
        return result;
    }

    // Leetcode Q2 - Shuffle the array (My solution has 0ms runtime)

    public int[] shuffle(int[] nums, int n)
    {
        int[] result = new int[2*n];
        int counter = 0;
        for (int i = 0; i < n; i++)
        {
            result[counter] = nums[i];
            result[counter + 1] = nums[n+i];
            counter+=2;
        }
        return result;
    }

    // Leetcode Q3 - max consecutive ones in array (1ms rt, beats 98%)

    public int findMaxConsecutiveOnes(int[] nums)
    {
        int count = 0;
        int max = 0;
        for (int num : nums)
        {
            if (num == 1) count++;
            else
            {
                if (count > max) max = count;
                count = 0;
            }
        }
        return Math.max(count, max);
    }

    // ARRAYS II

    // Q1 - Set Mismatch (has 2ms runtime)

    public int[] findErrorNums(int[] nums)
    {
        int[] frequencyMap = new int[nums.length];
        int[] report = new int[2];
        for (int i = 0; i < nums.length; i++)
        {
            frequencyMap[nums[i] - 1]++;
        }
        for (int i = 0; i < frequencyMap.length; i++)
        {
            if (frequencyMap[i] == 0) report[1] = i + 1;
            else if (frequencyMap[i] == 2) report[0] = i + 1;
        }
        return report;
    }

    // Q2 - How many numbers are smaller than the current number (1ms runtime, beats almost 100%)

    public int[] smallerNumbersThanCurrent(int[] nums)
    {
        int n = -1; // Array at least has 1 element. We will increment n based on the largest value in the array, then create a countsort array

        for (int num : nums) if (num > n) n = num; // Determine the largest value in the array

        int[] frequencyMap = new int[n + 1];
        for (int i = nums.length-1; i >= 0; i--)
        {
            frequencyMap[nums[i]]++;
        }
        int totalElements = 0;
        int[] lessThanArray = new int[frequencyMap.length]; // Stores amount of elements that are less than the current element
        for (int i = 0; i < frequencyMap.length; i++)
        {
            lessThanArray[i] = totalElements;
            totalElements += frequencyMap[i];
        }
        int[] result = new int[nums.length];
        int counter = 0;
        for (int num : nums)
        {
            result[counter] = lessThanArray[num];
            counter++;
        }
        return result;
    }

    // Q3 - Find all numbers disappeared in an Array (has 4ms runtime, beats 97%)

    public List<Integer> findDisappearedNumbers(int[] nums)
    {
        int[] frequencyMap = new int[nums.length + 1];
        for (int num : nums)
        {
            frequencyMap[num]++;
        }
        List<Integer> missing = new ArrayList<>();
        for (int i = 1; i < frequencyMap.length; i++)
        {
            if (frequencyMap[i] == 0) missing.add(i);
        }
        return missing;
    }

    // STACK I

    // Q1 - Build an Array With Stack Operations (has 0ms runtime, 1 pass O(n) complexity)

    public List<String> buildArray(int[] target, int n)
    {
        if (target[target.length-1] < n) n = target[target.length-1];
        List<String> result = new ArrayList<>();
        int stream = 1; // Current stream.
        for (int i = 0; i < target.length; i++)
        {
            if (target[i] != stream)
            {
                while (target[i] != stream)
                {
                    result.add("Push"); //If value isn't in the target array, push and pop it
                    result.add("Pop");
                    stream++;
                }
                result.add("Push"); // Once we've found the value that exists and the while loop breaks, add it to the pack.
            }
            else
            {
                result.add("Push"); // If value exists and matches stream, just add it.
            }
            stream++;
        }
        return result;
    }

    // Q2 - Evaluate Reverse Polish Notation (Named after Polish guy!) (has 5ms runtime - beats 97%)

    // Note - using "try-catch" to find if a string can be converted to an integer and the legacy Stack data type tanks performance.
    // Instead - use "default" in the switch statement for numbers, and use the "Deque" data type for stack-based operations.

    public int evalRPN(String[] tokens) // Research - Reverse Polish Notation by Jan Łukasiewicz.
    {
        Deque<Integer> RPN = new ArrayDeque<>(); // Better than the regular Stack data type.
        for (int i = 0; i < tokens.length; i++)
        {
            switch (tokens[i])
            {
                case "+":
                    RPN.push(RPN.pop() + RPN.pop());
                    break;
                case "-":
                    int y = RPN.pop();
                    int x = RPN.pop();
                    RPN.push(x - y);
                    break;
                case "*":
                    RPN.push(RPN.pop() * RPN.pop());
                    break;
                case "/":
                    int b = RPN.pop();
                    int a = RPN.pop();
                    RPN.push(a / b);
                    break;
                default: // Fires off when character is just a regular integer.
                    RPN.push(Integer.parseInt(tokens[i]));
            }
        }
        return RPN.pop();
    }

    // Q3 - Exclusive Time of Functions (Returns amount of time that a function ahs been running)

    // Almost did it, was 99% of the way there.

    public int[] exclusiveTime(int n, List<String> logs)
    {
        int[] times = new int[n]; // How many seconds each function was running (index "i" is function "i")
        Deque<Integer> stack = new ArrayDeque<>(); // Our stack
        int prevTime = 0; // Previous time, to add the current time to the next time

        for (String log : logs)
        {
            String[] parts = log.split(":"); // Split log to 3 parts - x:query:y (x = function ID, query = start/end, y = time when it stops/starts)
            int id = Integer.parseInt(parts[0]);
            String type = parts[1];
            int currTime = Integer.parseInt(parts[2]);

            if (type.equals("start"))
            {
                if (!stack.isEmpty()) // If stack isn't empty, we're recursively calling another function.
                {
                    times[stack.peek()] += currTime - prevTime; // Add the time from our previous function before stacking another function on top
                }
                stack.push(id); // Push the new function
                prevTime = currTime; // <--- Set to exact start time (Inclusive/Exclusive type logic)
            }
            else // This is we have "end" as the query.
            {
                times[stack.peek()] += currTime - prevTime + 1; // add time elapsed from previous event
                stack.pop(); // Remove from the stack.
                prevTime = currTime + 1; // <--- Set to next clock tick (Inclusive/Exclusive type logic)
            }
        }
        return times;
    }

    // Monotonic Stack

    // Q1 - Final Prices with a Special Discount in a shop

    // Stack version - has 4ms runtime (3ms with Deque instead of Stack Data structure)

    public int[] finalPricesStack(int[] prices)
    {
        int n = prices.length;
        int[] result = prices.clone();

        Deque<Integer> stack = new ArrayDeque<>(); // Use Deque instead of Stack, because Stack is legacy.

        for (int i = 0; i < n; i++)
        {
            while (!stack.isEmpty() && prices[stack.peek()] >= prices[i])
            {
                result[stack.pop()] -= prices[i];
            }
            stack.push(i);
        }
        return result;
    }

    // Optimized, non-stack version: has 1ms runtime (better solution)

    public int[] finalPrices(int[] prices) // Optimal O(n^2) solution
    {
        int[] result = prices.clone();
        for (int i = 0; i < prices.length; i++)
        {
            for (int j = i + 1; j < prices.length; j++)
            {
                if (prices[i] - prices[j] >= 0)
                {
                    result[i] -= prices[j];
                    break;
                }
            }
        }
        result[result.length-1] = prices[prices.length-1];
        return result;
    }

    // Q2 - Daily Temperatures

    public int[] dailyTemperatures(int[] temperatures)
    {
        int[] result = new int[temperatures.length];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < temperatures.length; i++)
        {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()])
            {
                result[stack.peek()] = i - stack.pop();
            }
            stack.push(i);
        }
        return result;
    }

    /*
            === How do Monotonic Stacks Work ===

            Essentially, we're putting currently "unresolved"
     */

    void printAllNums()
    {
        for (int i = 0; i < Integer.MAX_VALUE; i++)
        {
            System.out.println(i);
        }
    }


    void main()
    {
        //Tools.arr.printArray(dailyTemperatures(new int[]{73,74,75,71,69,72,76,73}));
        printAllNums();
    }
}

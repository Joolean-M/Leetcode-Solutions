import javax.xml.transform.Source;
import java.sql.SQLOutput;
import java.util.*;
class Solution
{
    public void print(Object s)
    {
        System.out.println(s);
    }

    public boolean isValid(String s)
    {
        char[] arr = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++)
        {
            char c = arr[i];
            if (c == '(' || c == '{' || c == '[')
            {
                stack.push(c); //Push new parentheses to top
            }
            else
            {
                if (stack.isEmpty()) return false; //If stack empty at this point, then throw error.
                char top = stack.pop();
                if (c == ')' && top != '(') return false;
                if (c == '}' && top != '{') return false; //Stack based. If top isn't equal to the corresponding part, throw error.
                if (c == ']' && top != '[') return false;
            }
        }
        return stack.isEmpty(); //Only when stack is empty, we can
    }

    public int removeDuplicates(int[] input)
    {
        if (input.length == 0) return 0;

        int uniqueCount = 1; // first element is already unique

        for (int i = 1; i < input.length; i++)
        {
            if (input[i] != input[uniqueCount - 1])
            {
                input[uniqueCount] = input[i];
                uniqueCount++;
            }
        }

        return uniqueCount;
    }

    public int removeElement(int[] input, int val)
    {
        if (input.length == 0) return 0;
        int cursor = 0;
        for (int i = 0; i < input.length; i++)
        {
            if (input[i] != val)
            {
                input[cursor] = input[i];
                cursor++;
            }
        }
        return cursor;
    }

    public int strStr(String haystack, String needle)
    {
        for (int i = 0; i < haystack.length(); i++)
        {
            if (i + needle.length() > haystack.length()) return -1;
            if (haystack.substring(i, i + needle.length()).startsWith(needle))
            {
                return i;
            }
        }
        return -1;
    }

    public int searchInsert(int[] input, int target)
    {
        for (int i = 0; i < input.length; i++)
        {
            if (input[i] >= target) return i;
        }
        return input.length;
    }

    public int lengthOfLastWord(String s)
    {
        int count = 0;
        int index = s.length()-1; //last index, loop backwards

        if (index < 0) return 0; //Safety check.

        while (index >= 0 && s.charAt(index) == ' ') index--;
        while (index >= 0 && s.charAt(index) != ' ')
        {
            index--;
            count++;
        }
        return count;
    }

    public int[] plusOne(int[] digits)
    {
        int currentIndex = digits.length-1; //Start at last index
        while (currentIndex >= 0)
        {
            if (digits[currentIndex] != 9)
            {
                digits[currentIndex]++;
                break;
            }
            else
            {
                digits[currentIndex] = 0;
                if (currentIndex - 1 == -1)
                {
                    int[] newArray = new int[digits.length+1];
                    newArray[0] = 1;
                    System.arraycopy(digits, 0, newArray, 1, newArray.length - 1);
                    return newArray;
                }
                currentIndex--;
            }
        }
        return digits;
    }

    public String addBinary(String a, String b)
    {
        //Let's make both strings the same length.
        int difference = Math.abs(a.length()-b.length());
        String additionalZeroes = "";
        for (int i = 0; i < difference; i++)
        {
            additionalZeroes += "0";
        }
        if (a.length() > b.length())
        {
            b = additionalZeroes + b;
        }
        else a = additionalZeroes + a;

        // all above works

        String sum = ""; //we're gonna have to reverse it.
        int current = a.length()-1; //Final index, doesn't matter if we take a or b, length is the same
        boolean carry = false;
        while (current >= 0)
        {
            if (a.charAt(current) == '0' && b.charAt(current) == '0')
            {
                sum += (carry) ? 1 : 0;
                carry = false;
            }
            else if ((a.charAt(current) == '1' && b.charAt(current) == '0') || (b.charAt(current) == '1' && a.charAt(current) == '0'))
            {
                sum += (carry) ? 0 : 1;
            }
            else if (a.charAt(current) == '1' && b.charAt(current) == '1')
            {
                sum += (carry) ? 1 : 0;
                carry = true;
            }
            current--;
        }
        if (carry) sum += "1"; //Leftover carry
        //Reverse string
        String reversed = "";
        for (int i = sum.length()-1; i >= 0; i--)
        {
            reversed += sum.charAt(i);
        }
        return reversed;
    }

    public int mySqrt(int x)
    {

        if (x == 1) return 1;
        long start = 0; //Start position
        long end = x;
        long middlePoint = x/2;
        long previous = 0;
        while (start != end)
        {
            if (previous == middlePoint) return (int)middlePoint;
            if (middlePoint * middlePoint > x)
            {
                end = middlePoint;
            }
            else
            {
                start = middlePoint;
            }
            previous = middlePoint;
            middlePoint = (start+end)/2;
        }
        return (int) middlePoint;
    }

    public int climbStairs(int n)
    {
        return 0;
    }

    public int closestTarget(String[] words, String target, int startIndex)
    {
        int shortest = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++)
        {
            if (words[i].equals(target))
            {
                int candidate1 = Math.abs(i - startIndex);
                int candidate2 = Math.abs(words.length-1-i + startIndex);
                int candidate3 = Math.abs(words.length-1-startIndex + i);
                shortest = Math.min(Math.min(shortest,candidate1),Math.min(candidate2,candidate3));
            }
        }
        if (shortest == Integer.MAX_VALUE) return -1;
        return shortest;
    }

    public class ListNode //For delete duplicates
    {
        int val;
        ListNode next;

        ListNode() {}
        ListNode(int val)
        {
            this.val = val;
        }

        ListNode(int val, ListNode next)
        {
            this.val = val;
            this.next = next;
        }
    }

        public ListNode deleteDuplicates(ListNode head)
        {
            ListNode unique = head;
            ListNode current = unique.next;
            while (current != null)
            {
                if (current.val !=  unique.val)
                {
                    unique.next = current;
                    unique = current;
                }
                current = current.next;
            }
            unique.next = null;
            return head;
        }

        public void printList (ListNode head)
        {
            ListNode runner = head;
            while (runner != null)
            {
                System.out.print(runner);
                runner = runner.next;
            }
        }


        public void merge(int[] input1, int m, int[] input2, int n)
        {
            int[] input1Copy = input1.clone();
            int c1 = 0, c2 = 0, cc = 0;
            while (c1 != m && c2 != n)
            {
                if (input1Copy[c1] < input2[c2])
                {
                    input1[cc] = input1Copy[c1];
                    c1++;
                }
                else //current element 2 is smaller
                {
                    input1[cc] = input2[c2];
                    c2++;
                }
                cc++;
            }
            //Add what's left to the end
            if (c1 == m) //array one complete, add what's left from array 2 to array 1
            {
                for (int i = c2; i < n; i++)
                {
                    input1[cc] = input2[c2];
                    c2++;
                    cc++;
                }
            }
            else
            {
                for (int i = c1; i < m; i++)
                {
                    input1[cc] = input1Copy[c1];
                    c1++;
                    cc++;
                }
            }
        }

        public class TreeNode
        {
            int val;
            TreeNode left;
            TreeNode right;
            TreeNode() {}
            TreeNode(int val) { this.val = val; }
            TreeNode(int val, TreeNode left, TreeNode right)
            {
                this.val = val;
                this.left = left;
                this.right = right;
            }
        }

        public List<Integer> inorderTraversal(TreeNode root)
        {
            List<Integer> result = new ArrayList<>();
            //We'll represent the current elem that we are at as the top of the stack
            Stack<TreeNode> treeNodeStack = new Stack<>();
            TreeNode current = root;
            //Traverse binary tree in order (Go left as far as possible, if can't -> pop, then go right.)
            treeNodeStack.push(root); //Initially push root
            while (true)
            {
                if (current.left != null)
                {
                    current = current.left;
                    treeNodeStack.push(current);
                }
                else //visit current, then check to see if it has a right child
                {
                    result.add(current.val);
                }
            }
        }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2)
    {
        ListNode runner = new ListNode();
        ListNode startPos = runner;
        int carry = 0;
        int numToAdd;
        int a, b;
        while(l1 != null || l2 != null)
        {
            if (l1 == null) a = 0; //add a 0.
            else a = l1.val;
            if (l2 == null) b = 0;
            else b = l2.val;
            numToAdd = a + b + carry; //Max sum is 27
            carry = 0; //reset carry quickly
            if (numToAdd > 9)
            {
                carry = (numToAdd % 100)/10;
                numToAdd = numToAdd % 10;
            }
            runner.val = numToAdd;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
            //Create a new node
            if (l1 != null || l2 != null)
            {
                runner.next = new ListNode();
                runner = runner.next;
            }
        }
        if (carry > 0)
        {
            runner.next = new ListNode(carry); //Create a final node if there is a carry larger than 0 (can't have numbers such as 098)
        }
        return startPos;
    }

    public void printListNodeValuesInOrder (ListNode head)
    {
        System.out.println();
        while (head != null)
        {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }

    public ListNode createNode (int val)
    {
        ListNode listNode = new ListNode();
        listNode.val = val;
        return listNode;
    }

    public ListNode linkListNodes(ListNode... listnodes)
    {
        for (int i = 0; i < listnodes.length - 1; i++)
        {
            listnodes[i].next = listnodes[i + 1];
        }
        return listnodes[0];
    }

    public int[] merge (int[] a, int[] b)
    {
        int[] result = new int[a.length + b.length];
        int c1 = 0, c2 = 0, cc = 0;
        while (c1 != a.length && c2 != b.length)
        {
            if (a[c1] < b[c2])
            {
                result[cc] = a[c1];
                c1++;
            }
            else
            {
                result[cc] = b[c2];
                c2++;
            }
            cc++;
        }
        if (c1 == a.length) //Then add the rest of array 2 to the output array
        {
            for (int i = c2; i < b.length; i++)
            {
                result[cc] = b[c2];
                cc++; c2++;
            }
        }
        else
        {
            for (int i = c1; i < a.length; i++)
            {
                result[cc] = a[c1];
                cc++; c1++;
            }
        }
        return result;
    }

    public void printArray (int[] a)
    {
        System.out.print("\n[");
        for (int i = 0; i < a.length-1; i++)
        {
            System.out.print(a[i] + ", ");
        }
        System.out.print(a[a.length-1] + "]");
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2)
    {
        return 6.7;
    }

    public int lengthOfLongestSubstring(String s)
    {
        if (s.length() == 1) return 1;
        int offset;
        int count = 0;
        int largest = 0;
        Set<Character> set = new HashSet<>(); //set is just a mathematical set. (Like in discrete mathematics)
        for (int i = 0; i < s.length(); i++) //Start at index
        {
            offset = 0; //Reset offset
            while (true)
            {
                if (i + offset < s.length())
                {
                    if (set.contains(s.charAt(i + offset))) //Then we found a duplicate, reset count
                    {
                        if (count > largest) largest = count;
                        count = 0;
                        set.clear();
                        break;
                    }
                    else //Continue, add to set
                    {
                        set.add(s.charAt(i + offset));
                        count++;
                        offset++;
                    }
                }
                else //We finished
                {
                    break;
                }
            }
        }
        return largest;
    }

    public int lengthOfLongestSubstringV2(String s)
    {
        if (s.isEmpty()) return 0;
        if (s.length() == 1) return 1;
        int left = 0;
        int right = 0; //Calculate the longest distance between them by doing: (right - left + 1)
        int largest = 0;
        char currChar;
        HashMap<Character, Integer> lastIndexSeen = new HashMap<>();
        while (right < s.length())
        {
            currChar = s.charAt(right); //Assign the currently inspected character
            if (lastIndexSeen.containsKey(currChar) && lastIndexSeen.get(currChar) >= left)
            {
                left = lastIndexSeen.get(currChar) + 1;
            }
            lastIndexSeen.put(currChar, right); //Overwrites any previous indices completely
            int currentWindowSize = right - left + 1;
            if (currentWindowSize > largest)
            {
                largest = currentWindowSize;
            }
            right++;
        }
        return largest;
    }

    void main()
    {

    }
}

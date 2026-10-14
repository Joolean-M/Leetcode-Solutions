import java.nio.file.AccessMode;
import java.util.*;

public class sortingAlgorithms
{
    public int getDigit(int number, int exp) //E.g (651 % 100)/10 --> modulo a number x, then divide all this by x/10
    {
        return (number/exp) % 10; //Exp means if we want 1's, exp = 1, if we want tens, we do exp = 10, etc
    }

    //TODO - Getting digit of number works.


    public void print(int[] array)
    {
        System.out.print("[");
        for (int i = 0; i < array.length - 1; i++)
        {
            System.out.print(array[i] + ", ");
        }
        System.out.print(array[array.length-1] + "]");
        System.out.println();
    }



    public int[] countingSort (int[] input) //Digit - 0 is ones, 1 is tens, 2 is 100's, etc
    {
        int max = input[0];
        for (int i = 0; i < input.length; i++) //Find max number
        {
            if (input[i] > max) max = input[i];
        }
        //Create array of indexes, size is max number + 1
        int[] count = new int[max + 1];
        for (int i = 0; i < count.length; i++) //Fill each index of count array with 0 values
        {
            count[i] = 0;
        }
        //Count input elems
        for (int i = 0; i < input.length; i++)
        {
            count[input[i]]++;
        }
        //Calculate cumulative sum
        int sum = 0;
        //Change count array to array of cumulative sum (next index is the sum of previous elements)
        for (int i = 0; i < count.length; i++)
        {
            sum += count[i];
            count[i] = sum;
        }
        //Create output array and sort.
        int[] output = new int[input.length];
        for (int i = input.length-1; i >= 0; i--)
        {
            output[--count[input[i]]] = input[i];
        }
        return output;
    }

    public int[] countingSortForRadix (int exponent, int[] input) //Digit - 0 is ones, 1 is tens, 2 is 100's, etc
    {
        int[] arrayOfInputDigits = new int[input.length];
        for (int i = 0; i < input.length; i++) //Fill array of specific digits from the input array
        {
            arrayOfInputDigits[i] = getDigit(input[i], exponent);
        }
        int max = arrayOfInputDigits[0];
        for (int i = 0; i < input.length; i++) //Find max number
        {
            if (arrayOfInputDigits[i] > max) max = arrayOfInputDigits[i];
        }
        //Create array of indexes, size is max number + 1, then fill with zeroes
        int[] count = new int[max + 1];
        for (int i = 0; i < count.length; i++)
        {
            count[i] = 0;
        }
        //Count input elems
        for (int i = 0; i < input.length; i++)
        {
            count[arrayOfInputDigits[i]]++;
        }
        //Calculate cumulative sum
        int sum = 0;
        //Change count array to array of cumulative sum (next index is the sum of previous elements)
        for (int i = 0; i < count.length; i++)
        {
            sum += count[i];
            count[i] = sum;
        }
        //Create output array and sort.
        int[] output = new int[input.length];
        for (int i = input.length-1; i >= 0; i--)
        {
            output[--count[arrayOfInputDigits[i]]] = input[i];
        }
        return output;
    }


    public void radixSort (int[] input)
    {
        int max = input[0];
        for (int i = 1; i < input.length; i++) //Quick O(n) check which number is the largest in the input array.
        {
            if (input[i] > max) max = input[i];
        }
        for (int exp = 1; max / exp > 0; exp *= 10)
        {
            input = countingSortForRadix(exp, input);
            print(input);
        }
    }

    public void mergeSort(int[] input)
    {
        Queue<int[]> arrays = new ArrayDeque<>();
        for (int i = 0; i < input.length; i++) {
            arrays.add(new int[]{input[i]});
        }

        // Keep going as long as we have more than 1 'winner' in the tournament
        while (arrays.size() > 1) {
            Queue<int[]> nextRound = new ArrayDeque<>(); // Fresh queue for each round

            while (!arrays.isEmpty())
            {
                int[] a = arrays.poll();

                if (arrays.isEmpty())
                {
                    // Odd one out! Just move it to the next round.
                    nextRound.add(a);
                }
                else
                {
                    int[] b = arrays.poll();
                    nextRound.add(merge(a, b));
                }
            }
            // The round is over. The 'nextRound' becomes the current group of arrays.
            arrays = nextRound;
            printQueueOfArrays(arrays);
            System.out.println(); // New line for clarity
        }
    }

    public int[] merge(int[] a, int[] b)
    {
        int runnerA = 0;
        int runnerB = 0;
        int runnerR = 0;
        int[] resultArray = new int[a.length + b.length];
        while (runnerA != a.length && runnerB != b.length)
        {
            if (a[runnerA] < b[runnerB])
            {
                resultArray[runnerR] = a[runnerA];
                runnerA++;
            }
            else
            {
                resultArray[runnerR] = b[runnerB];
                runnerB++;
            }
            runnerR++;
        }
        if (runnerA == a.length) //Then add the rest of elements from B to the list.
        {
            for (int i = runnerB; i < b.length; i++)
            {
                resultArray[runnerR] = b[i];
                runnerR++;
            }
        }
        else if (runnerB == b.length)
        {
            for (int i = runnerA; i < a.length; i++)
            {
                resultArray[runnerR] = a[i];
                runnerR++;
            }
        }
        return resultArray;
    }

    public void printQueueOfArrays(Queue<int[]> arrays)
    {
        Queue<int[]> temp = new ArrayDeque<>(arrays);
        System.out.print("[");
        while (temp.size() != 1)
        {
            int[] tempArray = temp.poll();
            for (int i = 0; i < tempArray.length; i++)
            {
                System.out.print(tempArray[i] +", ");
            }
        }
        int[] tempArray = temp.poll(); //Final array in queue
        for (int i = 0; i < tempArray.length - 1; i++)
        {
            System.out.print(tempArray[i] +", ");
        }
        System.out.print(tempArray[tempArray.length-1] + "]");
    }

    // MergeSort v2

    public void iterativeMergeSort(int[] arr)
    {
        int n = arr.length;
        int[] temp = new int[n];
        for (int step = 1; step < n; step *= 2)
        {
            for (int i = 0; i < n; i += 2 * step)
            {
                int left = i;
                int mid = i + step;
                int right = i + 2 * step;
                if (mid > n) mid = n;
                if (right > n) right = n;
                merge(arr, temp, left, mid, right);
            }
            for (int i = 0; i < n; i++)
            {
                arr[i] = temp[i];
            }
        }
    }

    public void merge(int[] arr, int[] temp, int left, int mid, int right)
    {
        int i = left;
        int j = mid;
        int k = left;
        while (i < mid && j < right)
        {
            if (arr[i] <= arr[j])
            {
                temp[k++] = arr[i++];
            }
            else
            {
                temp[k++] = arr[j++];
            }
        }
        while (i < mid)
        {
            temp[k++] = arr[i++];
        }
        while (j < right)
        {
            temp[k++] = arr[j++];
        }
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

    public void bogosort(int[] arr)
    {
        List<Integer> list = new ArrayList<>(arr.length);
        Random rand = new Random();
        int[] resultArray = new int[arr.length];
        int total32BitExceedings = 0;
        int totalShuffles = 0;
        boolean isSorted;
        while (true)
        {
            isSorted = true;
            for (int i = 0; i < arr.length; i++) // Populate list to then randomly select values from it
            {
                list.add(arr[i]);
            }
            // Now randomly select the values from the list and add it to the result array
            for (int i = 0; i < arr.length; i++)
            {
                int x = rand.nextInt(list.size());
                resultArray[i] = list.get(x);
                list.remove(x); //Cast to integer
            }
            //Now check if result array is sorted
            for (int i = 1; i < arr.length; i++)
            {
                if (!(resultArray[i - 1] <= resultArray[i]))  // If previous value is larger than the current value, the array is not sorted.
                {
                    isSorted = false;
                    totalShuffles++;
                    if (totalShuffles == 2147483647)
                    {
                        totalShuffles = 0;
                        total32BitExceedings++;
                    }
                    break;
                }
            }
            if (isSorted)
            {
                System.out.println("Sorted! Total shuffles: " + totalShuffles + " Total times shuffles exceeded the 32-bit integer limit: " + total32BitExceedings);
                System.out.println();
                Tools.arr.printArray(resultArray);
                return;
            }
        }
    }

    void main()
    {
        bogosort(new int[]{89,2,67,4,43,34,76,12,98,12,43,134,1});
    }
}

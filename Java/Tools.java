
// Class for debugging tools

import java.util.List;

public class Tools
{
    static public class arr
    {
        static public void printArray(int[] arr)
        {
            for (int i = 0; i < arr.length; i++)
            {
                if (i == 0) System.out.print("[");
                System.out.print(arr[i]);
                if (i != arr.length - 1) System.out.print(", ");
                else System.out.print("]");
            }
        }
    }

    static public class list
    {
        static public void printStringList(List<String> list)
        {
            for (int i = 0; i < list.size(); i++)
            {
                if (i == 0) System.out.print("[");
                System.out.print("\"" + list.get(i) + "\"");
                if (i != list.size() - 1) System.out.print(", ");
                else System.out.print("]");
            }
        }
    }

}

public class Strings
{
    public int myAtoi(String s)
    {
        if (s.isEmpty() || s.length() == 1 && !Character.isDigit(s.charAt(0))) return 0;
        int resultNumber = 0;
        s = s.stripLeading();
        int startIndex = 0;
        boolean numbersStarted = false; // Check that turns on only when valid numbers have started
        boolean isNegative = false;
        if (s.charAt(0) == '-')
        {
            isNegative = true;
            startIndex++; // Start at index 1 instead.
            if (s.length() > 1 && !Character.isDigit(s.charAt(1))) return 0;
        }
        else if (s.charAt(0) == '+')
        {
            startIndex++;
        }
        else if ( !Character.isDigit(s.charAt(0)) ) return 0;
        int endIndex = startIndex; // The ending index. Then, we'll extract the valid numerical string that starts at startIndex and ends at endIndex
        for (int i = startIndex; i < s.length(); i++)
        {
            if (!Character.isDigit(s.charAt(i))) break;
            else endIndex++;
        }
        try {resultNumber = Integer.parseInt(s.substring(startIndex, endIndex));}
        catch (NumberFormatException e) // When number is larger than 32 bit integer limit
        {
            if (!isNegative) return Integer.MAX_VALUE;
            else return Integer.MIN_VALUE;
        }
        return (isNegative) ? resultNumber * -1 : resultNumber;
    }

    void main()
    {
        System.out.println(myAtoi("-+12"));
    }
}

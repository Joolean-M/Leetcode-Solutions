public class Main {
    private final int N;
    private final int M;
    private final long[][] nCrCache;

    public Main(int n, int m) {
        this.N = n;
        this.M = m;
        // Max 'n' we will need is for remaining positions (N) + available values (M + 1) - 1.
        // Therefore, we precompute Pascal's triangle up to N + M.
        int maxN = N + M;
        this.nCrCache = new long[maxN + 1][N + 1];
        precomputeCombinations(maxN, N);
    }

    /**
     * Precomputes binomial coefficients using Pascal's Triangle to guarantee O(1) lookups.
     */
    private void precomputeCombinations(int maxN, int maxK) {
        for (int i = 0; i <= maxN; i++) {
            nCrCache[i][0] = 1; // C(n, 0) = 1
            for (int j = 1; j <= Math.min(i, maxK); j++) {
                if (i == j) {
                    nCrCache[i][j] = 1; // C(n, n) = 1
                } else {
                    nCrCache[i][j] = nCrCache[i - 1][j - 1] + nCrCache[i - 1][j];
                }
            }
        }
    }

    /**
     * Binomial coefficient helper: C(n, k)
     */
    private long nCr(int n, int k) {
        if (k < 0 || k > n) return 0;
        return nCrCache[n][k];
    }

    /**
     * Helper function: Computes the number of valid non-decreasing sequences 
     * given remaining positions and the range of available values.
     */
    private long countSequences(int remainingPositions, int currentVal, int maxVal) {
        // We need to pick 'remainingPositions' values from the range [currentVal, maxVal].
        // Number of available values = (maxVal - currentVal + 1).
        // Using stars and bars: C(n + k - 1, k) where n = available values, k = remainingPositions.
        int n = (maxVal - currentVal + 1);
        return nCr(n + remainingPositions - 1, remainingPositions);
    }

    /**
     * 1. ENCODE (Rank)
     * Computes the unique lexicographical index of the given array.
     */
    public long encode(int[] arr) {
        if (arr.length != N) {
            throw new IllegalArgumentException("Array length must be exactly " + N);
        }

        long rank = 0;
        int prev = 0; // Minimum allowed value for the current position

        for (int i = 0; i < N; i++) {
            int current = arr[i];
            int remainingPositions = N - 1 - i;

            // Skip all valid sequences that would start with a value smaller than 'current'
            for (int v = prev; v < current; v++) {
                rank += countSequences(remainingPositions, v, M);
            }
            prev = current;
        }

        return rank;
    }

    /**
     * 2. DECODE (Unrank)
     * Reconstructs the non-decreasing array from its unique lexicographical index.
     */
    public int[] decode(long rank) {
        int[] arr = new int[N];
        int prev = 0;
        for (int i = 0; i < N; i++) {
            for (int v = prev; v <= M; v++) {
                // Count how many sequences start with 'v' at this position
                long count = countSequences(N - 1 - i, v, M);
                if (rank < count) {
                    arr[i] = v;
                    prev = v; // Maintains non-decreasing property
                    break;
                } else {
                    rank -= count;
                }
            }
        }
        return arr;
    }

    // Quick demonstration/test
    public static void main(String[] args) {
        Main bijection = new Main(10, 100);

        // Test Case
        int[] originalArray = {0, 0, 5, 12, 11, 45, 60,56, 99, 100};

        long rank = bijection.encode(originalArray);
        int[] decodedArray = bijection.decode(rank);

        System.out.println("Original Array: " + java.util.Arrays.toString(originalArray));
        System.out.println("Lexicographical Rank: " + rank);
        System.out.println("Decoded Array:  " + java.util.Arrays.toString(decodedArray));
    }
}
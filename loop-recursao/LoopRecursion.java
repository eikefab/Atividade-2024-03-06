import java.util.Scanner;
import java.util.function.Function;

public class LoopRecursion {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            final int n = scanner.nextInt();

            final long loopTime = getElapsedTime(n, "Loop", LoopRecursion::fromLoop);
            final long recursionTime = getElapsedTime(n, "Recursão", (val) -> fromRecursion(val, 0));
            final long arithmeticTime = getElapsedTime(n, "Aritmética", LoopRecursion::fromArithmetic);

            System.out.println("Loop: " + loopTime + "ms");
            System.out.println("Recursão: " + recursionTime + "ms");
            System.out.println("Aritmética: " + arithmeticTime + "ms");
        }
    }

    public static long getElapsedTime(int n, String id, Function<Integer, Integer> function) {
        long start = System.currentTimeMillis();

        int result = function.apply(n);

        System.out.println("Resultado (" + id + "): " + result);

        return System.currentTimeMillis() - start;
    }

    public static int fromLoop(int n) {
        int sum = 0;

        for (int i = 1; i <= n; i++) {
            sum += i;
        }

        return sum;
    }

    public static int fromRecursion(int n, int agg) {
        if (n == 0) {
            return agg;
        }

        return fromRecursion((n - 1), (agg + n));
    }

    // Fui um pouco além, utilizando a fórmula de PAs finitas
    public static int fromArithmetic(int n) {
        return ((1 + n) * n) / 2;
    }

}
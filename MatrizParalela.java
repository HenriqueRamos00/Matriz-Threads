import java.util.Random;

public class MatrizParalela {

    public static void main(String[] args) {

        int n = 2000;
        int numWorkers = 1;

        int[][] A = randomMatrix(n, n);
        int[][] B = randomMatrix(n, n);

        long startTime = System.nanoTime();

        int[][] resultado = new int[n][n];
        MatrizWorker[] workers = new MatrizWorker[numWorkers];
        int linhasPorWorker = n / numWorkers;
        int resto = n % numWorkers;
        int start = 0;
        for (int i = 0; i < numWorkers; i++) {
            int rows = linhasPorWorker + (i < resto ? 1 : 0);
            int[][] partA = new int[rows][n];
            for (int j = 0; j < rows; j++) {
                partA[j] = A[start + j];
            }
            workers[i] = new MatrizWorker(partA, B, start);
            workers[i].start();
            start += rows;
        }

        try {
            for (MatrizWorker worker : workers) {
                worker.join();
                int[][] res = worker.getResultado();
                int rowOffset = worker.getRowOffset();
                for (int r = 0; r < res.length; r++) {
                    resultado[rowOffset + r] = res[r];
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1000000.0;
        System.out.println("Duracao: " + duration + " ms");


        System.out.println("Terminado");
    }

    public static int[][] randomMatrix(int rows, int cols) {
        int[][] m = new int[rows][cols];
        Random rand = new Random();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                m[i][j] = (int) rand.nextInt(10);
        return m;
    }

    public static void printMatriz(int[][] m) {
        for (int[] row : m) {
            for (int val : row)
                System.out.printf("%d ", val);
            System.out.println();
        }
        System.out.println();
    }
}
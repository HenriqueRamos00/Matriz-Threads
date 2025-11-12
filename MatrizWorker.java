public class MatrizWorker extends Thread{
    private int[][] matriz1;
    private int[][] matriz2;
    private int[][] resultado;
    private int rowOffset;

    public MatrizWorker(int[][] m1, int[][] m2, int rowOffset) {
        this.matriz1 = m1;
        this.matriz2 = m2;
        this.rowOffset = rowOffset;
    }

    public int[][] getResultado() {return this.resultado;}
    public int getRowOffset() { return this.rowOffset;}

    public void run() {
        resultado = multiplicar(matriz1, matriz2);
    }

    public static int[][] multiplicar(int[][] A, int[][] B) {
        int rowsA = A.length;
        int colsA = A[0].length;
        int colsB = B[0].length;
        int[][] result = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return result;
    }
}

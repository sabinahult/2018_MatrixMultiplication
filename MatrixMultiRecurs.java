import java.util.*;
import java.io.*;

/**
* Author: Sabina Hult
*/

public class MatrixMultiRecurs {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Please provide the correct amount of command line arguments :)");
            System.exit(0);

        }

        int n = Integer.parseInt(args[0]);

        int[][] A = new int[n][n];
        int[][] B = new int[n][n];
        int[][] C;

        try {
            Scanner scanA = new Scanner(new File(args[1]));
            scanA.nextLine(); // consume the first line
            while (scanA.hasNextLine()) {
                String[] line = scanA.nextLine().split(",");
                int i = Integer.parseInt(line[0]);
                int j = Integer.parseInt(line[1]);
                int x = Integer.parseInt(line[2]);
                A[i][j] = x;
            }
            scanA.close();

            Scanner scanB = new Scanner(new File(args[2]));
            scanB.nextLine(); // consume the first line
            while (scanB.hasNextLine()) {
                String[] line = scanB.nextLine().split(",");
                int i = Integer.parseInt(line[0]);
                int j = Integer.parseInt(line[1]);
                int x = Integer.parseInt(line[2]);
                B[i][j] = x;
            }
            scanB.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }

        C = recursiveMultiplication(A, B);

        // printing out Matrix C
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(C[i][j] + " ");
            }
        }
    }

    public static int[][] recursiveMultiplication(int[][] A, int[][] B) {
        int n = A.length;

        if (n <= 8) {
            return naiveMultiplication(A, B);
        }

        // quarter the matrices
        int mid = n / 2;
        int[][] A1 = quarterOfMatrix(0, 0, A);
        int[][] A2 = quarterOfMatrix(0, mid, A);
        int[][] A3 = quarterOfMatrix(mid, 0, A);
        int[][] A4 = quarterOfMatrix(mid, mid, A);

        int[][] B1 = quarterOfMatrix(0, 0, B);
        int[][] B2 = quarterOfMatrix(0, mid, B);
        int[][] B3 = quarterOfMatrix(mid, 0, B);
        int[][] B4 = quarterOfMatrix(mid, mid, B);

        // the notion of which quarters to multiply and add together was found on wikipedia...
        int[][] C1 = sum(recursiveMultiplication(A1, B1), recursiveMultiplication(A2, B3));
        int[][] C2 = sum(recursiveMultiplication(A1, B2), recursiveMultiplication(A2, B4));
        int[][] C3 = sum(recursiveMultiplication(A3, B1), recursiveMultiplication(A4, B3));
        int[][] C4 = sum(recursiveMultiplication(A3, B2), recursiveMultiplication(A4, B4));

        return assemble(C1, C2, C3, C4);
    }

    public static int[][] quarterOfMatrix(int row, int col, int[][] matrix) {
        int n = matrix.length / 2;
        int[][] result = new int[n][n];
        int firstCol = col;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = matrix[row][col];
                col++;
            }
            row++;
            col = firstCol;
        }

        return result;
    }

    public static int[][] sum(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                result[i][j] += A[i][j] + B[i][j];
        }

        return result;
    }

    private static int[][] assemble(int[][] c1, int[][] c2, int[][] c3, int[][] c4) {
        int n = c1.length * 2;
        int[][] result = new int[n][n];

        int mid = n/2;
        for (int i = 0; i < mid; i++) {
            for (int j = 0; j < mid; j++) {
                result[i][j] = c1[i][j];
                result[i][j + mid] = c2[i][j];
                result[i + mid][j] = c3[i][j];
                result[i + mid][j + mid] = c4[i][j];
            }
        }

        return result;
    }

    public static int[][] naiveMultiplication(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return result;
    }
}

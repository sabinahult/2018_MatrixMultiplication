import java.util.*;
import java.io.*;

/**
* Author: Sabina Hult
*/

public class MatrixMultiplication {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Please provide the correct amount" + "of command line arguments :)");

        } else {
            int n = Integer.parseInt(args[0]);

            int[][] A = new int[n][n];
            int[][] B = new int[n][n];
            int[][] C = new int[n][n];

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
            }

            // now for the actual naive matrix multiplication
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }

            // printing out Matrix C
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(C[i][j] + " ");
                }
                // System.out.println();
            }
        }
    }
}

// Authors: Lucas Loughner (lloughne) and Spencer Gregory (sgrego03)
// Date: 6/29/22
// Assignment: Project2 Part1

import java.util.*;
import java.io.*;

public class MatrixWork {

    public static void main(String[] args) throws Exception
    {
        // Get file name from user
        System.out.println("Enter filename with Matrices to be multiplied:");
        Scanner in = new Scanner(System.in);
        String filename = in.nextLine();
        File infile = new File(filename);

        // Generate matrices from info in file
        Scanner filescan = new Scanner(infile);
        int[] rc = new int[2];

        // Process first matrix
        for(int i = 0; i < 2; i++)
        {
            rc[i] = filescan.nextInt();
        }
        // create array to hold matrix
        int[][] A = new int[rc[0]][rc[1]];

        // loop for rows
        for(int i = 0; i < rc[0]; i++)
        {
            // loop for columns
            for(int j = 0; j < rc[1]; j++)
            {
                A[i][j] = filescan.nextInt();
            }
        }

        // Process second matrix
        for(int i = 0; i < 2; i++)
        {
            rc[i] = filescan.nextInt();
        }
        // Create array to hold matrix
        int[][] B = new int[rc[0]][rc[1]];

        // loop for rows
        for(int i = 0; i < rc[0]; i++)
        {
            // loop for columns
            for(int j = 0; j < rc[1]; j++)
            {
                B[i][j] = filescan.nextInt();
            }
        }

        try
        {
            int[][]C = MatrixProduct.matrixProduct_DAC(A, B);
            for(int i = 0; i < C.length; i++)
            {
                for(int j = 0; j < C[0].length; j++)
                {
                    System.out.printf("%d ", C[i][j]);
                }
                System.out.println();
            }
        }
        catch (Exception e)
        {
            System.out.print("Matrices incompatible");
        }

    }


    public static int[][] matrixProduct(int[][] A, int[][] B)
    {
        int n = A.length;
        int k = B.length;
        int m = B[0].length;
        int[][] C = new int[n][m];

        if(A[0].length != B.length){
            throw new IllegalArgumentException("Matrices are incompatible");
        }

        //System.out.println("Product Matrix:");
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                for(int l = 0; l < k; l++){
                    C[i][j] = C[i][j] + A[i][l] * B[l][j];
                }
            }
        }
        return C;
    }

}

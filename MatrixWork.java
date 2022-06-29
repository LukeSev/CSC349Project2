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


        
        // For testing purposes
/*
        System.out.println("A:");
        for(int i = 0; i < 3; i++)
        {
            System.out.printf("[");
            for (int j = 0; j < 4; j++) {
                System.out.printf(" %d", A[i][j]);
            }
            System.out.printf(" ]%n");
        }

        System.out.printf("%nB:%n");
        for(int i = 0; i < 2; i++)
        {
            System.out.printf("[");
            for(int j = 0; j < 2; j++)
            {
                System.out.printf(" %d", B[i][j]);
            }
            System.out.printf(" ]%n");
        }
*/


    }

/*
    public static int[][] matrixProduct(int[][] A, int[][] B)
    {

    }
*/
}

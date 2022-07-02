import java.lang.Math;
import java.util.*;

public class TestMatMul {


    public static void main(String[] args) throws Exception
    {
        // First create four arrays
        // Two will be filled with random numbers to be multiplied,
        // the other two will be used to compare outputs

        // Matrices
        int A[][], B[][], C1[][], C2[][], C3[][];
        int size = 128; // Size must be a power of 2
        int numtests = 5; // Number of tests to be run
        A = new int[size][size];
        B = new int[size][size];
        C1 = new int[size][size];
        C2 = new int[size][size];
        C3 = new int[size][size];

        for(int i = 0; i < numtests; i++)
        {
            int rand1, rand2;
            for(int j = 0; j < size; j++)
            {
                for(int k = 0; k < size; k++)
                {
                    rand1 = (int)(Math.random() * size * size);
                    rand2 = (int)(Math.random() * size * size);
                    A[j][k] = rand1;
                    B[j][k] = rand2;
                }
            }

            try
            {
                C1 = MatrixProduct.matrixProduct_DAC(A, B);
                C2 = MatrixProduct.matrixProduct_Strassen(A, B);
                C3 = MatrixWork.matrixProduct(A, B);
            }
            catch (Exception e)
            {
                System.out.print(e.toString());
            }


            // Testing DAC
            int errors = 0;
            for(int j = 0; j < size; j++)
            {
                for(int k = 0; k < size; k++)
                {
                    if(C1[j][k] != C3[j][k])
                        errors++;
                }
            }
            System.out.printf("Number of mismatching entries for DAC test #%d: %d%n", i+1, errors);

            // Testing Strassen
            errors = 0;
            for(int j = 0; j < size; j++)
            {
                for(int k = 0; k < size; k++)
                {
                    if(C2[j][k] != C3[j][k])
                        errors++;
                }
            }
            System.out.printf("Number of mismatching entries for Strassen test #%d: %d%n%n", i+1, errors);


            // Un-comment the following to print out all product matrices for comparison
/*
            System.out.printf("C1:%n");
            printArray(C1, size);
            System.out.printf("%nC2:%n");
            printArray(C2, size);
            System.out.printf("%nC3:%n");
            printArray(C3, size);
            System.out.printf("%n%n%n");

 */
        }

    }

static private void printArray(int[][] arr, int size)
{
    for(int i = 0; i < size; i++)
    {
        for (int j = 0; j < size; j++)
            System.out.printf("%d ", arr[i][j]);
        System.out.println();
    }
}


}

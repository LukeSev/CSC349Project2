// Authors: Lucas Loughner (lloughne) and Spencer Gregory (sgrego03)
// Date: 7/2/22
// Assignment: Project2 Part2

public class MatrixProduct {

    public static int[][] matrixProduct_DAC(int[][] A,  int[][] B)
    {
        // Check if both square matrices of same size
        if((A.length == B.length) || (A.length == A[0].length) || (B.length == B[0].length))
        {
            // Check if power of 2
            int k = A.length;
            while((k%2) == 0)
                k = k / 2;
            if(k != 1)
                throw new IllegalArgumentException("Not square matrices");
        }
        else
            throw new IllegalArgumentException("Matrices incorrectly formatted");

        return matProd_DAC_help(A, 0, 0, B, 0, 0, A.length);

    }

    private static int[][] matProd_DAC_help(int[][] A, int startrowA, int startcolA,
                                            int[][] B, int startrowB, int startcolB, int n)
    {
        int[][] C = new int[n][n];
        if(n == 1)
            C[0][0] = A[startrowA][startcolA] * B[startrowB][startcolB];
        else {
            // Four quadrants
            int[][] C11 = addMat(matProd_DAC_help(A, startrowA, startcolA, B, startrowB, startcolB, n / 2),
                    matProd_DAC_help(A, startrowA, startcolA + (n / 2), B, startrowB + (n / 2), startcolB, n / 2));
            int[][] C12 = addMat(matProd_DAC_help(A, startrowA, startcolA, B, startrowB, startcolB + (n / 2), n / 2),
                    matProd_DAC_help(A, startrowA, startcolA + (n / 2), B, startrowB + (n / 2), startcolB + (n / 2), n / 2));
            int[][] C21 = addMat(matProd_DAC_help(A, startrowA + (n / 2), startcolA, B, startrowB, startcolB, n / 2),
                    matProd_DAC_help(A, startrowA + (n / 2), startcolA + (n / 2), B, startrowB + (n / 2), startcolB, n / 2));
            int[][] C22 = addMat(matProd_DAC_help(A, startrowA + (n / 2), startcolA, B, startrowB, startcolB + (n / 2), n / 2),
                    matProd_DAC_help(A, startrowA + (n / 2), startcolA + (n / 2), B, startrowB + (n / 2), startcolB + (n / 2), n / 2));
            for(int i = 0; i < n/2; i++)
                for(int j = 0; j < n/2; j++)
                {
                    C[i][j] = C11[i][j]; // C11
                    C[i][j+(n/2)] = C12[i][j]; // C12
                    C[i+(n/2)][j] = C21[i][j]; // C21
                    C[i+(n/2)][j+(n/2)] = C22[i][j]; // C22
                }
        }
        return C;
    }

    public static  int[][]  matrixProduct_Strassen(int[][] A,  int[][] B)
    {
        // Check if both square matrices of same size
        if((A.length == B.length) || (A.length == A[0].length) || (B.length == B[0].length))
        {
            // Check if power of 2
            int k = A.length;
            while((k%2) == 0)
                k = k / 2;
            if(k != 1)
                throw new IllegalArgumentException("Not square matrices");
        }
        else
            throw new IllegalArgumentException("Matrices incorrectly formatted");

        return matProd_Strassen_help(A, 0, 0, B, 0, 0, A.length);
    }

    private static int[][] matProd_Strassen_help(int[][] A, int startrowA, int startcolA,
                                            int[][] B, int startrowB, int startcolB, int n)
    {
        int[][] C = new int[n][n];
        if(n == 1)
            C[0][0] = A[startrowA][startcolA] * B[startrowB][startcolB];
        else {
            // 1,1 -> startrowA, startcolA
            // 1,2 -> startrowA, startcolA + n/2
            // 2,1 -> startrowB + n/2, startcolB
            // 2,2 -> startrowB + n/2, startcolB + n/2

            int[][] S1 = subMatStrassen(B, startrowB, startcolB + n/2, B, startrowB + n/2, startcolB + n/2, n/2);
            int[][] S2 = addMatStrassen(A, startrowA, startcolA, A, startrowA, startcolA+n/2, n/2);
            int[][] S3 = addMatStrassen(A, startrowA+n/2, startcolA, A, startrowA+n/2, startcolA+n/2, n);
            

            // Four quadrants
            int[][] C11 = addMat(matProd_DAC_help(A, startrowA, startcolA, B, startrowB, startcolB, n / 2),
                    matProd_DAC_help(A, startrowA, startcolA + (n / 2), B, startrowB + (n / 2), startcolB, n / 2));
            int[][] C12 = addMat(matProd_DAC_help(A, startrowA, startcolA, B, startrowB, startcolB + (n / 2), n / 2),
                    matProd_DAC_help(A, startrowA, startcolA + (n / 2), B, startrowB + (n / 2), startcolB + (n / 2), n / 2));
            int[][] C21 = addMat(matProd_DAC_help(A, startrowA + (n / 2), startcolA, B, startrowB, startcolB, n / 2),
                    matProd_DAC_help(A, startrowA + (n / 2), startcolA + (n / 2), B, startrowB + (n / 2), startcolB, n / 2));
            int[][] C22 = addMat(matProd_DAC_help(A, startrowA + (n / 2), startcolA, B, startrowB, startcolB + (n / 2), n / 2),
                    matProd_DAC_help(A, startrowA + (n / 2), startcolA + (n / 2), B, startrowB + (n / 2), startcolB + (n / 2), n / 2));


            for(int i = 0; i < n/2; i++)
                for(int j = 0; j < n/2; j++)
                {
                    C[i][j] = C11[i][j]; // C11
                    C[i][j+(n/2)] = C12[i][j]; // C12
                    C[i+(n/2)][j] = C21[i][j]; // C21
                    C[i+(n/2)][j+(n/2)] = C22[i][j]; // C22
                }
        }
        return C;
    }

    private static int[][] addMatStrassen(int[][] A, int startrowA, int startcolA, int[][] B, int startrowB, int startcolB, int n)
    {
        int[][] C = new int[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++)
                C[i][j] = A[i + startrowA][j + startcolA] - B[i + startrowB][j + startcolB];
        }
        return C;
    }

    private static int[][] subMatStrassen(int[][] A, int startrowA, int startcolA, int[][] B, int startrowB, int startcolB, int n)
    {
        int[][] C = new int[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++)
                C[i][j] = A[i + startrowA][j + startcolA] + B[i + startrowB][j + startcolB];
        }
        return C;
    }

    private static int[][] addMat(int[][] A, int[][] B)
    {
        int[][] C = new int[A.length][A.length];

        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A[0].length; j++)
                C[i][j] = A[i][j] + B[i][j];
        }
        return C;
    }

    private static void fillArr(int[][] C, int[][] quad, int startcol, int startrow)
    {
        for(int i = startrow; i < quad.length; i++){
            for(int j = startcol; j < quad.length; j++)
                C[i][j] = quad[startrow+i][startcol+j];
        }
    }


    public static int[][] MatrixProduct_General(int[][] A, int[][] B) {
        int n = A.length;
        int k = B.length;
        int m = B[1].length;
        int[][] C = new int[n][m];

        if(A[0].length != B.length){
            throw new IllegalArgumentException("Matrices are incompatible");
        }

        for(int i = 1; i < n; i++){
            for(int j = 1; j < m; j++){
                for(int l = 1; l < k; l++){
                    C[i][j] = C[i][j] + A[i][l] * B[l][j];
                }
            }
        }
        return C;
    }

}

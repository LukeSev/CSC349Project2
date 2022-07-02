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

            // S1 = B12 - B22
            int[][] S1 = subMatStrassen(B, startrowB, startcolB + n/2, B, startrowB + n/2, startcolB + n/2, n/2);

            // S2 = A11 + A12
            int[][] S2 = addMatStrassen(A, startrowA, startcolA, A, startrowA, startcolA+n/2, n/2);

            // S3 = A21 + A22
            int[][] S3 = addMatStrassen(A, startrowA+n/2, startcolA, A, startrowA+n/2, startcolA+n/2, n/2);

            // S4 = B21 - B11
            int[][] S4 = subMatStrassen(B, startrowB+n/2, startcolB, B, startrowB, startcolB, n/2);

            // S5 = A11 + A22
            int[][] S5 = addMatStrassen(A, startrowA, startcolA, A, startrowA+n/2, startcolA+n/2, n/2);

            // S6 = B11 + B22
            int[][] S6 = addMatStrassen(B, startrowB, startcolB, B, startrowB+n/2, startcolB+n/2, n/2);

            // S7 = A12 - A22
            int[][] S7 = subMatStrassen(A, startrowA, startcolA+n/2, A, startrowA+n/2, startcolA+n/2, n/2);

            // S8 = B21 + B22
            int[][] S8 = addMatStrassen(B, startrowB+n/2, startcolB, B, startrowB+n/2, startcolB+n/2, n/2);

            // S9 = A11 - A21
            int[][] S9 = subMatStrassen(A, startrowA, startcolA, A, startrowA+n/2, startcolA, n/2);

            // S10 = B11 + B12
            int[][] S10 = addMatStrassen(B, startrowB, startcolB, B, startrowB, startcolB+n/2, n/2);


            // P1 = A11 * S1
            int[][] P1 = matProd_Strassen_help(A, startrowA, startcolA, S1, 0, 0, n/2);

            // P2 = S2 * B22
            int[][] P2 = matProd_Strassen_help(S2, 0, 0, B, startrowB+n/2, startcolB+n/2, n/2);

            // P3 = S3 * B11
            int[][] P3 = matProd_Strassen_help(S3, 0, 0, B, startrowB, startcolB, n/2);

            // P4 = A22 * S4
            int[][] P4 = matProd_Strassen_help(A, startrowA+n/2, startcolA+n/2, S4, 0, 0, n/2);

            // P5 = S5 * S6
            int[][] P5 = matProd_Strassen_help(S5, 0, 0, S6, 0, 0, n/2);

            // P6 = S7 * S8
            int[][] P6 = matProd_Strassen_help(S7, 0, 0, S8, 0, 0, n/2);

            // P7 = S9 * S10
            int[][] P7 = matProd_Strassen_help(S9, 0, 0, S10, 0, 0, n/2);


            // C11 = P5 + P4 - P2 + P6
            int[][] C11 = addMat(P5, addMat(P4, subMat(P6, P2)));

            // C12 = P1 + P2
            int[][] C12 = addMat(P1, P2);

            // C21 = P3 + P4
            int[][] C21 = addMat(P3, P4);

            // C22 = P5 + P1 - P3 - P7
            int[][] C22 = addMat(P5, subMat(P1, addMat(P3, P7)));

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
                C[i][j] = A[i + startrowA][j + startcolA] + B[i + startrowB][j + startcolB];
        }
        return C;
    }

    private static int[][] subMatStrassen(int[][] A, int startrowA, int startcolA, int[][] B, int startrowB, int startcolB, int n)
    {
        int[][] C = new int[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++)
                C[i][j] = A[i + startrowA][j + startcolA] - B[i + startrowB][j + startcolB];
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

    private static int[][] subMat(int[][] A, int[][] B)
    {
        int[][] C = new int[A.length][A.length];

        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A[0].length; j++)
                C[i][j] = A[i][j] - B[i][j];
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

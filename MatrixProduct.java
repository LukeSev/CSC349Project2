public class MatrixProduct {

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

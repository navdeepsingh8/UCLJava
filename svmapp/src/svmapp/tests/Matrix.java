package svmapp.tests ;

/**
 * Provides Matrix operations as static methods. m by n matrices are represented
 * by 2 dimensional double arrays; ie. double[m][n].
 *
 * @author Navdeep Singh Daheley
 */
public class Matrix {
	
	/** specifies construction of a matrix from a mean vector */
	public static final int MEAN = 1 ;
	/** specifies construction of a covariance matrix from a variance vector */
	public static final int VARIANCE = 2 ;
	/** specifies construction of a matrix from a data vector */
	public static final int VECTOR = 3 ;
	
	/**
	 * Creates a matrix from a vector depending on its specified type.
	 */
	public static double[][] matrix(double[] a, int type) {
 		double[][] A = new double[a.length][1] ;
 		if (type==MEAN || type==VECTOR) {
 			A = new double[a.length][1] ;
 			for (int i=0;i<a.length;i++)
 				A[i][0]	= a[i] ;
 		} else if (type==VARIANCE) {
 			A = new double[a.length][a.length] ;
 			for (int i=0;i<a.length;i++)
 				A[i][i] = a[i] ;
 		}
 		return A ;
 	}
 	
 	/**
	 * Performs scalar multiplication on a matrix.
	 */
 	public static double[][] scalarMultiply(double[][] A,double a) {
 		double[][] result = new double[A.length][A[0].length] ;
 		for (int i=0;i<result.length;i++)
 			for (int j=0;j<result[0].length;j++)
 				result[i][j] = a*A[i][j] ;
 		return result ;
 	}	
 	
 	/**
	 * Performs transpose on a matrix.
	 */	
 	public static double[][] transpose(double[][] A) {
 		double[][] result = new double[A[0].length][A.length] ;
 		for (int i=0;i<result.length;i++)
 			for (int j=0;j<result[0].length;j++)
 				result[i][j] = A[j][i] ;
 		return result ;
 	}
 	
 	/**
	 * Adds two matrices.
	 * @param subtract specifies whether to multiply second matrix by -1 before addition
	 */
 	public static double[][] add(double[][] A,double[][] B,boolean subtract) {
 		double[][] result = new double[A.length][A[0].length] ;
 		int coef = 1 ;
 		if (subtract) coef = -1 ;
 		for (int i=0;i<A.length;i++)
 			for (int j=0;j<A[0].length;j++)
 				result[i][j] = A[i][j] + coef*B[i][j] ;
 		return result ;
 	}
 	
 	/**
	 * Multiplies two matrices.
	 */ 	
 	public static double[][] multiply(double[][] A, double[][] B) {
 		double[][] result = new double[A.length][B[0].length] ;
 		for (int i=0;i<result.length;i++)
 			for (int k=0;k<result[0].length;k++)
 				for (int j=0;j<B.length;j++)
 					result[i][k] += A[i][j]*B[j][k] ;
 		return result ;
 	}
 	
 	/**
	 * Inverts a matrix.
	 */
 	public static double[][] inverse(double[][] A) {
 		double[][] inverse = new double[A.length][A[0].length] ;
 		for (int i=0;i<A.length;i++) 
 			for (int j=0;j<A[0].length;j++)
 				inverse[i][j] = minor(A,i,j)/determinant(A) ;
 		return inverse ; 		
 	}
 	
 	/**
	 * Calculates the (i,j)-Minor of a matrix. For use in inversion.
	 */
 	public static double minor(double[][] A,int i,int j) {
 		double[][] cofactorMatrix = new double[A.length-1][A[0].length-1] ;
 		int p = 0 ;
 		int q = 0 ;
 		for (int a=0;a<A.length;a++) {
 			if (a != i) {
 				q = 0 ;
 				for (int b=0;b<A[0].length;b++) {
 					if (b != j) {
 						cofactorMatrix[p][q] = A[a][b] ;
 						q++ ;
 					}
 				}
 				p++ ;
 			}
 		}
 		return ((-1)^(i+j))*determinant(cofactorMatrix) ; 		
 	}
 	
 	
 	/**
	 * Calcluates the determinant of a DIAGONAL matrix.
	 */
 	public static double determinant(double[][] A) {
 		double det = 1 ;
 		for (int i=0;i<A.length;i++)
 			det = det*A[i][i] ;
 		return det ;
 	}	
}
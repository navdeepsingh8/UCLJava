 /**
  * Class: Euler.java
  * Purpose: Implement the Euler method for solving ODEs
  * Author: Navdeep Daheley, MACS3
  * Date: 12/10/2002
  */
  
 import java.lang.Math ;

 /* Class Euler contains 2 methods which are used in
  * implementing the Euler method, evaluate() and function()
  */
 public class Euler {
	
	
	/* Initial/final values are stored immediately as 32-bit numbers
	 * so that they can be used to dimensionalise arrays for different
	 * step sizes. Arrays for each variable y and t are created to store
	 * intermediate values for display in table.
	 */
	private float initialValueOfT ;
	private float finalValueOfT ;
	private float initialValueOfY ;
	private float actualValueOfY ;
	private float[] valueOfT ;
	private float[] valueOfY ;
	
		
	/* Given values are assigned to the above instance variables by
	 * the class constructor. Table header is output.
	 */
	public Euler(float t0, float tn, float y0, float ya) {
		initialValueOfT = t0 ;
		finalValueOfT = tn ;
		initialValueOfY = y0 ;
		actualValueOfY = ya ;
		System.out.println("\nh\tn\tyn\t\ten") ;
	}
	
	
	/* Evaluates the ODE using the Euler method with the given step size.
	 * Dimensionalises arrays to store intermediate t and y values, setting
	 * the initial values first. A for loop is used to calculate y for each
	 * intermediate t. The function f(y,t) is calculated in the function() method.
	 * Values are output for the Euler evaluation at the given step size.
	 */
	public void evaluate(float step) {
		valueOfT = new float[(int)(((finalValueOfT - initialValueOfT)/step) + 1)] ;
		valueOfY = new float[(int)(((finalValueOfT - initialValueOfT)/step) + 1)] ;
		valueOfT[0] = initialValueOfT ;
		valueOfY[0] = initialValueOfY ;

		for (int n = 1; n < valueOfT.length; n++) {
			valueOfT[n] = initialValueOfT + step*n ;
			valueOfY[n] = valueOfY[n-1] + step*function(valueOfY[n-1],valueOfT[n-1]) ;
		}
		
		System.out.println(step + "\t" + (valueOfY.length-1) + "\t" + valueOfY[valueOfY.length-1]
							 + "\t" + (valueOfY[valueOfY.length-1]-actualValueOfY)) ;
	}
	
	
	/* Calculates f(y,t) for the Euler evaluation.
	 */
	public float function(float y, float t) {
		return t*(float)Math.pow(y, (1/3f)) ;	// Casting is required on Math.pow()
	}											// as it works with 64-bit doubles.
	
	
	/* Main method is used to instantiate Euler objects with required
	 * initial values, and evaluate the Euler method to given step sizes.
	 */
	public static void main(String[] args) {
		Euler question2 = new Euler(1, 5, 1, 27.0f) ;
		question2.evaluate(0.2f) ;
		question2.evaluate(0.1f) ;
		question2.evaluate(0.05f) ;
		question2.evaluate(0.005f) ;
	}
}
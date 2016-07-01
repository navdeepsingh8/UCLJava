 /**
  * Class: RungeKutte2.java
  * Purpose: Implement the Runge Kutte (Order 2) method for solving ODEs
  * Author: Navdeep Daheley, MACS3
  * Date: 02/12/2002
  */
  
 import java.lang.Math ;
 import java.util.ArrayList ;

 /* Class RungeKutte2 contains 2 methods which are used in
  * implementing the RK2 method, evaluate() and function()
  */
 public class RungeKutte2 {
	
	
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
	private int iterations ;
	private ArrayList results ;
	
	/* Given values are assigned to the above instance variables by
	 * the class constructor. Table header is output.
	 */
	public RungeKutte2(float t0, float tn, float y0) {
		initialValueOfT = t0 ;
		finalValueOfT = tn ;
		initialValueOfY = y0 ;
		iterations = 0;
		results = new ArrayList() ;
		System.out.println("\nh\tn\tyn") ;
	}
	
	public void convergence(float step, float w1, float w2, float alpha, float lambda) {
		evaluate(step, w1, w2, alpha, lambda);
		float error = 1 ;
		int n = 2 ;
		while (results.size() < 2 || error > 0.0000005 || error < -0.0000005) {
			evaluate(step/n, w1, w2, alpha, lambda);
			Float e1 = (Float)results.get(results.size()-1) ;
			Float e2 = (Float)results.get(results.size()-2) ;
			error = e1.floatValue() - e2.floatValue() ;
			n = 2*n ;
		}
	}
	
	/* Evaluates the ODE using the Euler method with the given step size.
	 * Dimensionalises arrays to store intermediate t and y values, setting
	 * the initial values first. A for loop is used to calculate y for each
	 * intermediate t. The function f(y,t) is calculated in the function() method.
	 * Values are output for the Euler evaluation at the given step size.
	 */
	public void evaluate(float step, float w1, float w2, float alpha, float lambda) {
		valueOfT = new float[(int)(((finalValueOfT - initialValueOfT)/step) + 1)] ;
		valueOfY = new float[(int)(((finalValueOfT - initialValueOfT)/step) + 1)] ;
		valueOfT[0] = initialValueOfT ;
		valueOfY[0] = initialValueOfY ;

		for (int n = 1; n < valueOfT.length; n++) {
			valueOfT[n] = initialValueOfT + step*n ;
			valueOfY[n] = valueOfY[n-1] + step*(w1*function(valueOfY[n-1],
															valueOfT[n-1])
											  + w2*function(valueOfY[n-1] + alpha*step*function(valueOfY[n-1],valueOfT[n-1]),
											  				valueOfT[n-1] + lambda*step)) ;
		}
		System.out.println(step + "\t" + (valueOfY.length-1) + "\t" + valueOfY[valueOfY.length-1]) ;
		results.add(new Float(valueOfY[valueOfY.length-1])) ;
		iterations++ ;
	}
	
	public float function(float y, float t) {
	return t - (float)Math.pow(y, (2f)) ;
	}
	
	/* Main method is used to instantiate RungeKutte2 objects with required
	 * initial values, and evaluate the RK2 method to given step sizes.
	 */
	public static void main(String[] args) {
		RungeKutte2 question2b = new RungeKutte2(0, 3f, 0) ;
		question2b.convergence(0.4f,0f, 1f, 0.5f, 0.5f) ;
	}
}
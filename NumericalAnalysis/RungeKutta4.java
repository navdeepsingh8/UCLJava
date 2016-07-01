 /* Class: RungeKutta4.java
  * Purpose: Evaluates the Runge Kutta 4th order method for the system of 3 ODEs
  * 		in the question for given values of L and step size.
  * Author: Navdeep Daheley, MACS3
  * Date: 11/12/2002
  */
  
 import java.lang.Math ;
 import java.util.ArrayList ;
 
 public class RungeKutta4 {
	
	
	/* Instance variables are used to store initial values of time, theta, theta dot
	 * and si, and 4 ArrayLists to store subsequent values of each.
	 * Note: T = time, Y1 = theta, Y2 = theta dot, Z = si
	 */
	private double initialValueOfT = 0;
	private double initialValueOfY1 = 0.25d*Math.PI;
	private double initialValueOfY2 = 0;
	private double initialValueOfZ = 0;
	private double requiredValueOfZ = 4*Math.PI;
	private ArrayList valueOfT = new ArrayList() ;
	private ArrayList valueOfY1 = new ArrayList() ;
	private ArrayList valueOfY2 = new ArrayList() ;
	private ArrayList valueOfZ = new ArrayList() ;
	
	/* L is input on creation of RungeKutta4 object.
	 * out is a flag corresponding to output of values at each iteration.
	 */
	 
	private double L ;
	private boolean out = false;
	
	public RungeKutta4(double L, boolean output) {
		this.L = L ;
		out = output ;
	}
	

	/* Evaluates RK4 using given step.
	 * Iteration stops when si has reached the desired value.
	 * The ArrayList of si values is returned on termination of this method.
	 */
	public ArrayList evaluate(double step) {
		valueOfT.add(0, new Double(initialValueOfT)) ;
		valueOfY1.add(0, new Double(initialValueOfY1)) ;
		valueOfY2.add(0, new Double(initialValueOfY2)) ;
		valueOfZ.add(0, new Double(initialValueOfZ)) ;
		if (out) { System.out.println("T,Y1,Y2,Z\n"+initialValueOfT + "," + initialValueOfY1 + "," + initialValueOfY2+ "," + initialValueOfZ) ; }

		int n = 1 ;
		Double zValue ;
		Double tValue ;
		Double y1Value ;
		Double y2Value ;
		do {
			valueOfT.add(n, new Double(initialValueOfT + step*n)) ;
			
			Double y1n = (Double)valueOfY1.get(n-1) ;
			Double y2n = (Double)valueOfY2.get(n-1) ;
			
			double k1 = step*f(y2n) ;
			double l1 = step*g(y1n) ;
			double m1 = step*h(y1n) ;
			
			double k2 = step*f(new Double(y2n.doubleValue() + l1/2)) ;
			double l2 = step*g(new Double(y1n.doubleValue() + k1/2)) ;
			double m2 = step*h(new Double(y1n.doubleValue() + k1/2)) ;
			
			double k3 = step*f(new Double(y2n.doubleValue() + l2/2)) ;
			double l3 = step*g(new Double(y1n.doubleValue() + k2/2)) ;
			double m3 = step*h(new Double(y1n.doubleValue() + k2/2)) ;
			
			double k4 = step*f(new Double(y2n.doubleValue() + l3)) ;
			double l4 = step*g(new Double(y1n.doubleValue() + k3)) ;
			double m4 = step*h(new Double(y1n.doubleValue() + k3)) ;
			
			Double previousY1 = (Double)valueOfY1.get(n-1) ;
			valueOfY1.add(n, new Double(previousY1.doubleValue() + (k1 + 2*k2 + 2*k3 + k4)/6)) ;
			
			Double previousY2 = (Double)valueOfY2.get(n-1) ;
			valueOfY2.add(n, new Double(previousY2.doubleValue() + (l1 + 2*l2 + 2*l3 + l4)/6)) ;
			
			Double previousZ = (Double)valueOfZ.get(n-1) ;
			valueOfZ.add(n, new Double(previousZ.doubleValue() + (m1 + 2*m2 + 2*m3 + m4)/6)) ;			
			
			zValue = (Double)valueOfZ.get(n) ;
			tValue = (Double)valueOfT.get(n) ;
			y1Value = (Double)valueOfY1.get(n) ;
			y2Value = (Double)valueOfY2.get(n) ;
			n++ ;
			if (out) { System.out.println(tValue.doubleValue() + "," + y1Value.doubleValue() + "," + y2Value.doubleValue() + "," + zValue.doubleValue()) ; }
		}
		while (zValue.doubleValue() < 4*Math.PI) ;
		return valueOfZ ;
	}
	
	
	/* Get methods return ArrayLists of the other variables.
	 */
	 
	public ArrayList getTvalues() {
		return valueOfT ;
	}
	
	public ArrayList getY1values() {
		return valueOfY1 ;
	}
	
	public ArrayList getY2values() {
		return valueOfY2 ;
	}
	
	
	/* f, g and h evaluate the functions corresponding to theta, theta dot and si.
	 */
	 
	public double f(Double Y2) {
		return Y2.doubleValue() ;
	}
	
	public double g(Double Y1) {
		double Lsquared = Math.pow(L, 2) ;
		double y1f = Y1.doubleValue() ;
		return (Lsquared*Math.cos(y1f)/Math.pow(Math.sin(y1f),3)) - Math.sin(y1f) ;
	}
	
	public double h(Double Y1) {
		double y1f = Y1.doubleValue() ;
		return L/Math.pow(Math.sin(y1f),2) ;
	}
	
	/* main method enables independent runs of RK4 for different L values
	 * and step sizes, with output to file optional.
	 */
	public static void main(String[] args) {
		boolean output = false ;
		if (args.length > 1) {
			if (args.length == 3) {
				if (args[2].equals("output")) {
					output = true;
				}
			}
			RungeKutta4 rk4 = new RungeKutta4(Double.parseDouble(args[0]), output) ;
			rk4.evaluate(Double.parseDouble(args[1])) ;
		} else {
			System.out.println("Incorrect usage. Correct: RungeKutta4 L h [output > file.csv]") ;
		}
	}
}
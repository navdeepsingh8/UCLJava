 /* Class: Project.java
  * Purpose: Evaluates the Runge Kutta 4th order method for the system of 3 ODEs
  * 		in the question for given values of L and step size.
  * Author: Navdeep Daheley, MACS3
  * Date: 11/12/2002
  */
 
 import java.lang.Math ;
 import java.util.ArrayList ;
 
 public class Project {
 	
 	/* An instance RungaKutta4 object is used for all RK4 evaluations.
 	 * ArrayLists are used to store values returned from rk4.
 	 * An ArrayList stores Y2 values resulting from linear interpolations.
 	 * A further ArrayList stores L values resulting from the secant method.
 	 */
	private RungeKutta4 rk4 ;
	private ArrayList Tvalues ;
	private ArrayList Y1values ;
	private ArrayList Y2values ;
	private ArrayList Zvalues ;
	private ArrayList Lvalues ;
	private ArrayList interpolatedY2values ;
	
	
	/* The default constructor is used for testing when no
	 * initial values of L are required initially.
	 */
	public Project() {
		interpolatedY2values = new ArrayList() ;
	}
	
	public Project(double L1, double L2) {
		Lvalues = new ArrayList() ;
		Lvalues.add(0, new Double(L1)) ;
		Lvalues.add(1, new Double(L2)) ;
		interpolatedY2values = new ArrayList() ;
	}
	
 	
 	/* Returns time at si = 4pi as a double, using linear interpolation.
 	 * Must be used after an RK4 evaluation.
 	 */
 	public double interpolateT() {
 		Double t1 = (Double)Tvalues.get(Tvalues.size()-2) ;
 		Double t2 = (Double)Tvalues.get(Tvalues.size()-1) ;
 		Double z1 = (Double)Zvalues.get(Tvalues.size()-2) ;
 		Double z2 = (Double)Zvalues.get(Tvalues.size()-1) ;
 		
 	 	return (4*Math.PI-z1.doubleValue())*(t2.doubleValue()-t1.doubleValue())/(z2.doubleValue()-z1.doubleValue()) + t1.doubleValue() ;
 	}
 	
 	/* General interpolation method for interpolating theta, theta dot
 	 * or si at a given time finalT (ie. the value from interpolateT())
 	 * Must be used after an RK4 evaluation.
 	 */
 	public double interpolate(int variable, double finalT) {
 		ArrayList chosenVariable = new ArrayList();
 		if (variable==1) {
 			chosenVariable = Y1values ;
 		} else if (variable==2) {
 			chosenVariable = Y2values ;
 		} else if (variable==3) {
 			chosenVariable = Zvalues ;
 		}
 		
 		Double t1 = (Double)Tvalues.get(Tvalues.size()-2) ;
 		Double t2 = (Double)Tvalues.get(Tvalues.size()-1) ;
 		Double y1 = (Double)chosenVariable.get(chosenVariable.size()-2) ;
 		Double y2 = (Double)chosenVariable.get(chosenVariable.size()-1) ;
 		
 		return y1.doubleValue() + (finalT-t1.doubleValue())*(y2.doubleValue()-y1.doubleValue())/(t2.doubleValue()-t1.doubleValue()) ;
 	}
 	
 	
 	/* Returns a value for si using the secant method.
 	 * Must be used after an RK4 evalution.
 	 */ 
 	public double secantL() {
		Double L1 = (Double)Lvalues.get(Lvalues.size()-2) ;
		Double L2 = (Double)Lvalues.get(Lvalues.size()-1) ;
		Double Y21 = (Double)interpolatedY2values.get(interpolatedY2values.size()-2) ;
		Double Y22 = (Double)interpolatedY2values.get(interpolatedY2values.size()-1) ;
		
		return L1.doubleValue()+(L2.doubleValue()-L1.doubleValue())*(-1*Y21.doubleValue())/(Y22.doubleValue()-Y21.doubleValue()) ;
	}
	
	/* Solves problem posed in question using a combination of RK4 to evaluate
	 * values for time, linear interpolation for values of theta, theta dot and si,
	 * and the secant method to converge to a solution for L.
	 * Takes step size for RK4, and desired accuracy for theta and theta dot.
	 */
	public void solve(double step, double accuracy) {
		System.out.println("L,Y1,Y2") ;
		double Y2 = 1d;
		double Y1 = 0d ;
		double Lvalue = 0d ;
		while ((Y2 > accuracy) || (Y2 < -1*accuracy) || (Y1-(Math.PI/4) > accuracy) || ((Math.PI/4)-Y1 > accuracy)) {
			if (interpolatedY2values.size() < 2) {
				Double LvalueDouble = (Double)Lvalues.get(interpolatedY2values.size()) ;
				Lvalue = LvalueDouble.doubleValue() ;
			}
			if (interpolatedY2values.size() >= 2) {
				Lvalue = secantL() ;
			}
			System.out.print(Lvalue+",") ;
			Lvalues.add(new Double(Lvalue)) ;
			rk4 = new RungeKutta4(Lvalue, false) ;
			Zvalues = rk4.evaluate(step) ;
			Y1values = rk4.getY1values() ;
			Y2values = rk4.getY2values();
			Tvalues = rk4.getTvalues() ;
			double interpolatedT = interpolateT() ;
			Y1 = interpolate(1, interpolatedT) ;
			Y2 = interpolate(2, interpolatedT) ;
			interpolatedY2values.add(new Double(Y2)) ;
			System.out.println(Y1+","+Y2) ;
		}
	}


	/* Outputs RK4 evaluations for a sequence of L values with corresponding
	 * interpolated theta and theta dot values.
	 * Suitable for plotting to look for possible L value solutions.
	 */
	public void test(double initial, double end, double inc) {
		System.out.println("L,Y1,Y2,pi/4") ;
		for (double L = initial; L < end; L=L+inc) {
			rk4 = new RungeKutta4(L, false) ;
			Zvalues = rk4.evaluate(inc) ;
			Tvalues = rk4.getTvalues() ;
			Y1values = rk4.getY1values() ;
			Y2values = rk4.getY2values() ;
			double interpolatedT = interpolateT() ;
			double interpolatedY2 = interpolate(2, interpolatedT) ;
			double interpolatedY1 = interpolate(1, interpolatedT) ;
			double interpolatedZ = interpolate(3, interpolatedT) ;
			System.out.println(L+","+interpolatedY1+","+interpolatedY2+","+Math.PI/4) ;
		}
	}
	
	
	/* main method enables runs for solving for, or testing of, values of L.
	 * Solving requires 2 initial L values, step size for RK4 and desired accurcay 
	 * of theta and theta dot.
	 * Testing requires initail and final L values and increment size.
	 * Output to file is optional.
	 */
	public static void main(String[] args) {
		Project project ;
		if (args.length == 4) {
				if (args[0].equals("test")) {
					project = new Project() ;
					project.test(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
				} else {
					System.out.println("Incorrect usage. Correct: Project (test L0 Ln inc | solve L1 L2 h accuracy) [> file.csv]") ;
				}				
		} else if (args.length == 5) {
				if (args[0].equals("solve")) {
					project = new Project(Double.parseDouble(args[1]), Double.parseDouble(args[2])) ;
					project.solve(Double.parseDouble(args[3]), Double.parseDouble(args[4])) ;
				} else {
					System.out.println("Incorrect usage. Correct: Project (test L0 Ln inc | solve L1 L2 h accuracy) [> file.csv]") ;
				}
		} else {
			System.out.println("Incorrect usage. Correct: Project (test L0 Ln inc | solve L1 L2 h accuracy) [> file.csv]") ;
		}
	}
 }
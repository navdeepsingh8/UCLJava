
package svmapp ;

/**
 * This class stores all of the parameters required for data generation in a single
 * location, as well as providing two public static int variables specifying the
 * types of data dependencies which can be generated.
 * 
 * @author Navdeep Singh Daheley
*/
public class gen_parameter implements java.io.Serializable {
	
	/** Dimension of the input vectors */
	public int dimension;
	/** Training set size */
	public int size;
	/** Number of data classes */
	public int nr_class ;
	/** Identifies whether the training set
	 * was loaded or whether it was generated */
	public boolean loaded ;
	/** Identifies file name if loaded */
	public String filename ;
	/** Identifies the data dependency type */
	public int distribution ;
	/** Maximum size of the training set input
	 * vector array */
	public int cache ;
	
	/** Uniformly distributed, polynomially
	 * seperated data */
	public static final int UNIFORM = 0;
	/** Mixture of Gaussians data */
	public static final int GAUSSIAN = 1;
	
	/*for polynomial discriminant*/
	/** Degree of polynomial discriminant */
	public int degree;	
	/** Multiplying coefficient of polynomial
	 * discriminant */
	public double a;
	/** Additive coefficient of polynomial
	 * discriminan */	
	public double b;
	/** Noise parameter; variance of Gaussian
	 * noise to be added */	
	public double noise;
	
	/* for mixture of gaussians */
	/** Array of mean vectors */
	public double[][] means ; 
	/** Array of covariance matrices */
	public double[][][] vars ;
	/** Array of class weights */
	public double[] weights ; 
	/** Mean scaling parameter; means are generated
	 * uniformly in [0,meanScale] */
	public double meanScale; 
	/** Variance scaling parameter in [0,1]; 
	 * variances are generated in [0,meanScale*varScale] */
	public double varScale;
	/** Proximity parameter in [0,1] ;
	 * the minimum euclidean distance between generated
	 * mean vectors */
	public double proximity ;
	
	/** Sets default values to generation parameters */
	public gen_parameter() {
		dimension = 2 ;
		size = 500 ;
		cache = 4000000 ;
		nr_class = 2 ;
		loaded = false ;
		
		degree = 2 ;
		a = 1.5 ;
		b = 0 ;
		noise = 0;

		means = new double[nr_class][dimension] ;
		vars = new double[nr_class][dimension][dimension] ;
		weights = new double[nr_class] ;
		meanScale = 10;
		varScale = 3;
		
		/* setting this value works because of triangle inequality */
		proximity = meanScale/varScale ; 		
	}	
}
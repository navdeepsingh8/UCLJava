package libsvm;
public class svm_parameter implements java.io.Serializable
{
	/* svm_type */
	public static final int C_SVC = 0;
	public static final int NU_SVC = 1;
	public static final int ONE_CLASS = 2;
	public static final int EPSILON_SVR = 3;
	public static final int NU_SVR = 4;

	/* kernel_type */
	public static final int LINEAR = 0;
	public static final int POLY = 1;
	public static final int RBF = 2;
	public static final int SIGMOID = 3;

	public int svm_type;
	public int kernel_type;
	public double degree;	// for poly
	public double gamma;	// for poly/rbf/sigmoid
	public double coef0;	// for poly/sigmoid

	// these are for training only
	public double cache_size; // in MB
	public double eps;	// stopping criteria
	public double C;	// for C_SVC, EPSILON_SVR and NU_SVR
	public int nr_weight;		// for C_SVC
	public int[] weight_label;	// for C_SVC
	public double[] weight;		// for C_SVC
	public double nu;	// for NU_SVC, ONE_CLASS, and NU_SVR
	public double p;	// for EPSILON_SVR
	public int shrinking;	// use the shrinking heuristics
	public int nr_fold ;
	
	public svm_parameter() {
		
		svm_type = C_SVC ;
		kernel_type = RBF ;
		degree = 3;
		gamma = 0;	// 1/(data dimension) later
		coef0 = 1;
		C = 10;
		nu = 0.1;
		p = 0.1;
		nr_fold = 0 ;
		
		cache_size = 40;
		eps = 1e-3;
		shrinking = 1;
		nr_weight = 0;
		weight_label = new int[0];
		weight = new double[0];
	}
}
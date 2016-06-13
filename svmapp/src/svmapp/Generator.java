
package svmapp ;

/**
 * This class is responsible for storing training and validation sets, as well as
 * generating them according to data dependencies.
 * 
 * @author Navdeep Singh Daheley
*/
public class Generator {
	
	private SVMApp app ;
	private gen_parameter genParam ;
	
	/** Training set input vectors */
	private double[][] ts ;
	/** Training set labels */
	private int[] labels ;
	/** Validation set input vectors */
	private double[][] vs ;
	/** Validation set labels */
	private int[] vsLabels ;
	/** Identifies whether classes are weighted */
	private boolean weighted ;
	/** Identifies whether a training 
	 * set exists */
	private boolean generated ;
	/** Identifies whether a validation set
	 * exists */
	private boolean valGenerated ;
	
	
	public Generator(SVMApp app,gen_parameter genParam) {
		this.app = app ;
		this.genParam = genParam ;
		weighted = false ;
		generated = false ;
		valGenerated = false ;				
	}
	
	
	/* Access get/set methods */
	
	public double[][] getTS() {
		return ts ;
	}
	
	public double[][] getVS() {
		return vs;
	}
	
	public int[] getLabels() {
		return labels ;
	}
	
	public int[] getVsLabels() {
		return vsLabels ;
	}
	
	public void setTS(double[][] ts) {
		this.ts = ts ;
	}
	
	public void setVS(double[][] vs) {
		this.vs = vs ;
	}
	
	public void setLabels(int[] labels) {
		this.labels = labels ;	
	}
	
	public void setVsLabels(int[] vsLabels) {
		this.vsLabels = vsLabels ;	
	}
	
	public boolean getWeighted() {
		return weighted ;
	}
	
	public boolean getGenerated() {
		return generated ;
	}
	
	public void setGenerated(boolean generated) {
		this.generated = generated ;
	}
	
	public boolean getValGenerated() {
		return valGenerated ;
	}
	
	public void setValGenerated(boolean valGenerated) {
		this.valGenerated = valGenerated ;
	}
	
	
	/**
	 * The common data generation method, called by the other generation methods,
	 * which sets the following parameters:
	 * @param tsSize training set size
	 * @param dimension input vector dimension
	 * @param nr_class number of classes in the data
	 */
	private void tsGen(int tsSize, int dimension,int nr_class) {
		
		/*checks for large amount of data	
		if (tsSize*dimension*8 > genParam.cache) {
		}*/
		
		/* intialises labels, input vectors and sets general data parameters */
		ts = new double[tsSize][dimension] ;
 		labels = new int[tsSize] ;
 		genParam.size = ts.length ;
 		genParam.dimension = dimension ;
 		genParam.nr_class = nr_class ;
 		genParam.loaded = false ;
 		app.getChart().killRanges() ;
 		app.getTrain().setTrained(false) ;
 		weighted = false ;
 		 		
	}
	
	
	/**
	 * Generates training set of uniform data in a hypercube centred at the 
	 * origin, seperated by a polynomial discriminant.
	 * @param tsSize training set size
	 * @param dimension input vector dimension
	 * @param a multiplying coefficient of discriminant
	 * @param b additive coefficient of discriminant
	 * @param degree discriminant degree
	 * @param noise parameter; variance of Gaussian noise to be added
	 * @return String returns generated data summary information
	 */
	public String tsGen(int tsSize, int dimension, double a, double b,
						int degree, double noise) {		
 		
 		/* calls common generation method and sets polynomial generation
 		 * parameters */
 		tsGen(tsSize,dimension,2) ;
  		genParam.distribution = genParam.UNIFORM;
  		genParam.a = a ;
  		genParam.b = b;
  		genParam.degree = degree ;
  		genParam.noise = noise ;
  		
  		/* generates uniform data storing it in the training set arrays */
  		uniform(ts,labels,noise) ;
  		
  		generated = true ;
		return ("Generated: "+genParam.size+"eg/"+genParam.dimension+"dim/"+genParam.nr_class+"class.") ;
 	}
 	
 	
 	/**
	 * Generates training set with mixture of Gaussians data.
	 * @param tsSize training set size
	 * @param dimension input vector dimension
	 * @param nr_class number of classes/Gaussians
	 * @param weights array of class weights
	 * @param meanScale mean scaling parameter
	 * @param varScale variance scaling parameter
	 * @param proximity proximity parameter
	 * @return String returns generated data summary or error
	 * @exception Exception error in generating data
	 */
 	public String tsGen(int tsSize, int dimension, int nr_class, double[] weights,
 			double meanScale, double varScale, double proximity) throws Exception {
				
		/* calls common generation method and sets up mixture of Gaussians
		 * parameters */
		tsGen(tsSize,dimension,nr_class) ;
		genParam.distribution = genParam.GAUSSIAN ;
		genParam.means = new double[nr_class][dimension] ;
		genParam.vars = new double[nr_class][dimension][dimension] ;
		genParam.meanScale = meanScale ;
		genParam.varScale = varScale*meanScale ;
		genParam.proximity = proximity*meanScale ;
		if (weights[0] != 0) {
			if (weights.length != nr_class)
				throw new Exception("Number of weights doesn't match the number of classes.") ;
			/* normalises weights */
			double z = 0 ;
			for (int c=0;c<nr_class;c++) z+= weights[c] ;
			for (int c=0;c<nr_class;c++) weights[c] = weights[c]/z ;
			genParam.weights = weights ;
			weighted = true ;
		}
		
		/* generates mean and covariance parameters */
		int c=0 ;
		int violations = 0 ;
		do {
			
			boolean violation = false ;
			
			/* generates candidate mean vector for class c */
			for (int d=0;d<genParam.dimension;d++) {
				genParam.means[c][d] = genParam.meanScale*Math.random() ;
			}
			
			/* checks for proximity violation before
			 * generating covariance matrix and going to next c */
			for (int k=0;k<c;k++) {
				if (distance(genParam.means[c],genParam.means[k]) < genParam.proximity) violation = true ;
			}
			if (!violation) {
				for (int i=0;i<genParam.dimension;i++) {
					//for (int j=0;j<genParam.dimension;j++) { //zero covariances for now
						genParam.vars[c][i][/*j*/i] = genParam.varScale*Math.random() ;
					//}
				}
				for (int i=0;i<genParam.dimension; i++) {
				}				
				c++ ;
				violations = 0 ;
			} else {
				violations++ ;
			}
			/* if many successive violations throws exception */
			if (violations > 1000000) {
				throw new Exception("Not generated. Try lower proximity/less classes/higher dimensions.") ;
			}
		} while (c<genParam.nr_class) ;
		
		/* generates mixture of Gaussian data and stores it in training set arrays */
		mog(ts,labels,0d);
		
		generated = true ;
		return ("Generated: "+genParam.size+"eg/"+genParam.dimension+"dim/"+genParam.nr_class+"class.") ;
	}
	
	
	/**
	 * Generates uniform data seperated by a polynomial discriminant and stores
	 * it into the given arrays, adding given noise.
	 * @param data array to store input vectors
	 * @param labesl array to store labels
	 * @param noise parameter; variance of gaussian noise to add
	 */
	private void uniform(double[][] data,int[] labels,double noise) {
 		
 		int size = data.length ;
 		int dim = data[0].length ;
 		
 		/* initialises hypercube intervals for data */
		double[][] interval = new double[dim][2] ;
 		for (int d = 0; d < dim-1; d++) {
 			interval[d][0] = -genParam.b-2 ;
 			interval[d][1] = -genParam.b+2 ;
 		}
 		interval[dim-1][0] = -2.5 ;
 		interval[dim-1][1] = 2.5 ;
 		
		/*outer loop over number of examples to be generated */
		for (int i = 0; i < size; i++) {
			
			double[] example = new double[dim] ;
			double rhsPoly = 0 ;
						
			/* generates data point and calculates rhsPoly for use in discriminant rhs */
			for (int d = 0; d < dim; d++) {
				example[d] = interval[d][0] + (interval[d][1]-interval[d][0])*Math.random() ;
				if (d<dim-1) rhsPoly += Math.pow(example[d]+genParam.b,genParam.degree) ;
			}
	
			/* stores data point and label generated from discriminant */
			data[i] = example ;	
			if (example[dim-1] >= genParam.a*rhsPoly) labels[i] = 0 ;
			else labels[i] = 1 ;
						
			/* adds noise if required */
			for (int d = 0; d < dim; d++) if (noise != 0d) data[i][d] += gaussian(0d,noise) ;
		}
 	}
	
	
	/**
	 * Generates mixture of Gaussian data and stores it into arrays
	 * given, adding noise given.
	 * @param data array to store input vectors
	 * @param labesl array to store labels
	 * @param noise parameter; variance of gaussian noise to add
	 */
	private void mog(double[][] data, int[] labels,double noise) {
	
		int size = data.length ;
		int dim = data[0].length ;
		
		/* outer loop over number of examples to be generated */
		for (int i = 0; i < size; i++) {
			
			int series = 0 ; /*0 just to initialise with */
			double[] mean = genParam.means[0];
			double[][] var = genParam.vars[0];
			double[] example = new double[dim] ;
			double uniform = Math.random() ;
			
			/* selects gaussian/class according to weights (or uniformly if none) */
			for (int c=0; c<genParam.nr_class;c++) {
				double upper, lower ;
				if (weighted) {
					lower = 0 ;
					for (int k=0;k<c;k++) lower += genParam.weights[k] ;
					upper = lower + genParam.weights[c] ;
				} else {
					lower = (double)c/(double)genParam.nr_class ;
					upper = ((double)c+1d)/(double)genParam.nr_class ;
				}
				if (uniform >= lower && uniform < upper) {
					mean = genParam.means[c] ;
					var = genParam.vars[c] ;
					labels[i] = c ;
					break ;
				}
			}
						
			/* initialises data point */
			for (int d = 0; d < dim; d++) {
				example[d] = gaussian(mean[d],var[d][d]) + gaussian(0d,noise);
			}
			data[i] = example ; 			
 		}
 	}


	/**
	 * Returns Euclidean distance between 2 vectors.
	 * @param x first vector
	 * @param y second vector
	 * @return double Euclidean distance between x and y
	 */
	private double distance(double[] x,double[] y) {
		double squared = 0;
		for (int d=0;d<x.length;d++) squared+=Math.pow(x[d]-y[d],2) ;
		return Math.sqrt(squared) ;
	}
	
	
	/**
	 * Returns sample from a gaussian distribution.
	 * @param mu mean
	 * @param sigma variance
	 */
	public static double gaussian(double mu, double sigma) {
 		double x, y, r2;
 		do {
    		/*choose x,y in uniform square (-1,-1) to (+1,+1)*/
    		x = -1 + 2*Math.random() ;
    		y = -1 + 2*Math.random() ;
    		
    		/*see if it is in the unit circle*/
    		r2 = x*x + y*y ;
    	} while (r2 > 1.0 || r2 == 0);

 		/*Box-Muller transform*/
 		if (x <= y) {
 			return mu+sigma*y*Math.sqrt(-2.0*Math.log(r2)/r2);
 		} else {
 			return mu-sigma*y*Math.sqrt(-2.0*Math.log(r2)/r2);
 		}
 	}
 	
 	
 	/**
 	 * Initialises valiation set identically to training set.
 	 * Used to calculate misclassification error over training set.
 	 */
 	public void vsGen(boolean sameData) {
 		vs = ts ;
 		vsLabels = labels ;
 		valGenerated = true ;
 		return ;
 	}
 	
 	/**
 	 * Generates validation set from existing training set.
 	 * @param vsSize size of validation set
 	 * @param noise noise parameter; variance of gaussian noise
 	 * to add.
 	 */
 	public String vsGen(int vsSize, double noise) {
 		if(!app.getTrain().getTrained())
 			return("Train an SVM first.") ;
 		
 		vs = new double[vsSize][genParam.dimension] ;
 		vsLabels = new int[vsSize] ;
 		
 		if (genParam.loaded) {
 			for (int i=0;i<vsSize;i++) {
 				int point = (int)(Math.random()*ts.length) ;
 				double[] example = new double[genParam.dimension] ;
 				for (int d=0;d<genParam.dimension;d++) {
 					example[d] = ts[point][d] + gaussian(0d,noise) ;
 				}
 				vs[i] = example ;
 				vsLabels[i] = labels[point] ;				
 			}
 		} else {
 			if (genParam.distribution==gen_parameter.UNIFORM) 
 				uniform(vs,vsLabels,noise) ;
 			if (genParam.distribution==gen_parameter.GAUSSIAN)
 				mog(vs,vsLabels,noise) ;
 		}
 		
 		valGenerated = true ;
 		return ("Validation set generated.") ;
 	}
}
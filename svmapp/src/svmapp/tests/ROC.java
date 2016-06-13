 package svmapp.tests ;
 
 import svmapp.* ;
 import libsvm.* ;
 import org.jmathplot.gui.*;
 import org.jfree.data.* ;
 import org.jfree.chart.* ;
 import org.jfree.chart.plot.* ;
 import org.jfree.chart.renderer.* ;
 import org.jfree.chart.axis.* ;
 import java.awt.* ;
 import java.text.NumberFormat ;
 
/**
 * Carries out ROC tests for LIBSVM and the Bayes classifier for mixture of
 * 2 Gaussians data at a range of Bhattacharya distances.
 *
 * @author Navdeep Singh Daheley
 */
 public class ROC {
 	
 	/** data vectors */
 	private double[][] data ;
 	/** data labels */
	private int[] labels ;
	/** 3 dimensional 2 class Gaussian mean vectors */
	private double[][] means = {{2,3,2},{2,3,2}} ;
	/** 3 dimensional 2 class Gaussian variance vectors */
	private double[][] vars = {{2,1,1},{2,1,1}} ;
	/** size of data set to be generated */
	private int size = 1000 ;
	/** stores classifier solutions to plot on ROC curves */
	private XYSeries svSolutions ;
	
	/** Kernel names */
	private static final String[] names = {"Linear","Polynomial","RBF"} ;
	/** Class one label */
	private static final int CLASS_ONE = 1 ;
	/** Class two label */
	private static final int CLASS_TWO = -1 ;

	private NumberFormat nf = NumberFormat.getInstance() ;
	private int totalClassOne ;
	private int totalClassTwo ;

	
	public ROC() {
		nf.setMaximumFractionDigits(2) ;
		initData() ;
	}
	
	/**
	 * Generates mixture of Gaussians data using instance mean vectors 
	 * and variance vectors and stores it in instance arrays.
	 */
	private void initData() {
		totalClassOne = 0 ;
		totalClassTwo = 0 ;
		data = new double[size][means[0].length] ;
		labels = new int[size] ;			
		for (int i=0;i<data.length/2;i++) {
			int c = 0 ;
			for (int d=0;d<data[0].length;d++)
				data[i][d] = Generator.gaussian(means[c][d],vars[c][d]) ;
			labels[i] = CLASS_ONE ;
			totalClassOne++ ;
		}
		for (int i=data.length/2;i<data.length;i++) {
			int c = 1 ;
			for (int d=0;d<data[0].length;d++)
				data[i][d] = Generator.gaussian(means[c][d],vars[c][d]) ;
			labels[i] = CLASS_TWO ;
			totalClassTwo++ ;
		}		
	}
	
	/**
	 * Plots the generated data using JMathPlot
	 * @param title Title of the graph
	 */
	public void plotData(String title) {
		int[] classes = {CLASS_ONE,CLASS_TWO} ;
		double[][][] array = new double[classes.length][][] ;		
		for (int c=0;c < array.length;c++) {
			int N = 0 ;
			for (int n=0;n<data.length;n++) {
				if (labels[n] == classes[c]) N++ ;
			}
			
			double[][] aClass = new double[N][3] ;
			int i = 0 ;
			for (int n=0;n<data.length;n++) {
				if (labels[n] == classes[c]) {
					aClass[i][0] = data[n][0] ;
					aClass[i][1] = data[n][1] ;
					aClass[i][2] = data[n][2] ;
					i++ ;
				}
			}
			array[c] = aClass ;
		}
		double[][] range = Chart.get3DRange(data) ;
		
		Plot3DPanel plot3d = new Plot3DPanel() ;
		for (int c=0;c<array.length;c++)
			plot3d.addPlot(array[c],String.valueOf(c+1),"SCATTER",Chart.classColours[c%Chart.classColours.length]) ;
		plot3d.setFixedBounds(range[0],range[1]) ;
	 	plot3d.setAxesLabels("x_0","x_1","x_2") ;
		
		new FrameView(title, plot3d);			
	}
	
	/**
	 * Trains an SVM on the given problem and kernel type
	 * @param prob problem to train SVM on
	 * @param kernel kernel to use for training
	 * @return trained SVM
	 */
	private svm_model train(svm_problem prob,int kernel) {
		
		svm_parameter svmParam = new svm_parameter() ;
		svmParam.svm_type = svm_parameter.C_SVC ;
		svmParam.kernel_type = kernel ;
		svmParam.degree = 2;
		svmParam.gamma = 1d/2d;
		svmParam.coef0 = 1;
		svmParam.C = 100;
		svmParam.nu = 0.8;	
			
		return svm.svm_train(prob,svmParam);	
	} 	
	
	/**
	 * Constructs and plots ROC curves for an SVM based on the specified kernel
	 * and trained on the generated data.
	 * @param kernel kernel to use in training
	 */
  	public void curve(int kernel) {
 		XYSeriesCollection curves = new XYSeriesCollection() ;
 		svSolutions = new XYSeries("SVM Solution") ;
 		double original = means[1][0] ;
 		for (int m=0;m<4;m++){
 			initData() ;
 			means[1][0] += 2 ;
 			double bhattacharya = bhattacharya(Matrix.matrix(means[0],Matrix.MEAN),
 											Matrix.matrix(vars[0],Matrix.VARIANCE),
 											Matrix.matrix(means[1],Matrix.MEAN),
 											Matrix.matrix(vars[1],Matrix.VARIANCE)) ;
 			curves.addSeries(evaluate(new XYSeries(nf.format(bhattacharya)),kernel)) ;
 		}
 		XYDataset solutions = new XYSeriesCollection(svSolutions) ;
 		XYChart(curves,solutions,
 			"ROC Curves for "+names[kernel]+" kernel") ;
 		means[1][0] = original ;
 	}
 	
 	/**
	 * Constructs ROC curves for a variety of SVM kernels and the Bayes classifier
	 * for the generated data separated by a distance specified.
	 * @param m L1 distance separating classes parallel to first dimension
	 */
 	public void compare(double m) {
 		XYSeriesCollection curves = new XYSeriesCollection() ;
 		svSolutions = new XYSeries("Solution") ;
		double original = means[1][0] ;
 		means[1][0] += m ;
 		initData() ;
 		double bhattacharya = bhattacharya(Matrix.matrix(means[0],Matrix.MEAN),
 											Matrix.matrix(vars[0],Matrix.VARIANCE),
 											Matrix.matrix(means[1],Matrix.MEAN),
 											Matrix.matrix(vars[1],Matrix.VARIANCE)) ;
 		int[] kernels = {svm_parameter.LINEAR,svm_parameter.POLY,svm_parameter.RBF} ;
 		
 		for (int k=0;k<kernels.length;k+=2)
 			curves.addSeries(evaluate(new XYSeries(names[k]),kernels[k])) ;
 		curves.addSeries(evaluateBayes(new XYSeries("Bayes"))) ;
 		
 		XYDataset solutions = new XYSeriesCollection(svSolutions) ;
 		XYChart(curves,solutions,
 				"ROC Curve Comparison for Bhattacharya distance "+nf.format(bhattacharya)) ;
 		means[1][0] = original ;
 	}
 	
 	/**
	 * Evaluates ROC curve for a given kernel based on the training data.
	 * @param kernel kernel to use for training
	 * @param series XYSeries to store curve points
	 */
 	private XYSeries evaluate(XYSeries series,int kernel) {
 	
 		svm_model model = train(SVMTrain.prob(data,labels),kernel) ;
		
		double[] m = new double[data.length] ;
		for (int i=0;i<data.length;i++) {
			svm_node[] x = new svm_node[data[i].length] ;
			for (int j=0; j<data[i].length;j++) {
				x[j] = new svm_node() ;
				x[j].index = j ;
				x[j].value = data[i][j] ;
			}			
			m[i] = margin(x,model) ;
		}
		
		return error(series,m) ;
	}
	
	/**
	 * Evaluates ROC curve for the Bayes classifier for a mixture of 2 Gaussians
	 * evaluated on the training data.
	 * @param series XYSeries in which the data is stored
	 */
 	private XYSeries evaluateBayes(XYSeries series) {
 		double constant = Math.log(Matrix.determinant(Matrix.matrix(vars[0],Matrix.VARIANCE))/
 								Matrix.determinant(Matrix.matrix(vars[1],Matrix.VARIANCE))) ;
 		double[][] sigma1Inv = Matrix.inverse(Matrix.matrix(vars[0],Matrix.VARIANCE)) ;
 		double[][] sigma2Inv = Matrix.inverse(Matrix.matrix(vars[1],Matrix.VARIANCE)) ;
 		double[] m = new double[data.length] ;
 		for (int i=0;i<data.length;i++) {
 			double[][] x = new double[data[0].length][1] ;
 			for (int d=0;d<x.length;d++)
 				x[d][0] = data[i][d] ;
 			m[i] = bayes_margin(x,sigma1Inv,sigma2Inv,constant) ; 
 		} 
 		return error(series,m) ;		
 	} 	

	/**
	 * Calculates and stores the points of a ROC curve for a classifier which
	 * results in a specified margin for each data point.
	 * @param m vector of margins for each data point
	 * @param series XYSeries to store ROC curve
	 */
	private XYSeries error(XYSeries series,double[] m) {
			
		double max = -10000 ;
		double min = 10000 ;
		for (int i=0;i<data.length;i++) if (m[i]>max) max = m[i] ;
		for (int i=0;i<data.length;i++) if (m[i]<min) min = m[i] ;
		System.out.println("MIN,MAX: ("+min+","+max+")");
		double increment = (max-min)/200 ;
		double pCorrect = 0 ;
 		double pIncorrect = 0 ;
 		for (double beta=min; beta < max;beta+=increment) {
			int correct = 0;
			int incorrect = 0;
			for (int i = 0; i<data.length;i++) {
				if (labels[i] == CLASS_ONE && m[i] > beta)
					correct++ ;
				if (labels[i] == CLASS_TWO && m[i] > beta)
					incorrect++ ;
				/*if (labels[i] == CLASS_ONE && m[i] < beta)
					incorrect++ ;
				if (labels[i] == CLASS_TWO && m[i] < beta)
					correct++ ;*/
			}
			pCorrect = (double)correct/(double)(totalClassOne/*+totalIncorrect*/) ;
			pIncorrect = (double)incorrect/(double)(totalClassTwo/*+totalCorrect*/) ;
			series.add(pIncorrect,pCorrect) ;
		}
		int correct = 0;
		int incorrect = 0;
		for (int i = 0; i<data.length;i++) {
			if (labels[i] == CLASS_ONE && m[i] > 0)
				correct++ ;
			if (labels[i] == CLASS_TWO && m[i] > 0)
				incorrect++ ;
			/*if (labels[i] == CLASS_ONE && m[i] < 0)
				incorrect++ ;
			if (labels[i] == CLASS_TWO && m[i] < 0)
				correct++ ;*/
		}
		pCorrect = (double)correct/(double)(totalClassOne/*+totalIncorrect*/) ;
		pIncorrect = (double)incorrect/(double)(totalClassTwo/*+totalCorrect*/) ;
		svSolutions.add(pIncorrect,pCorrect) ;
		System.out.println("SOLUTION: ("+pIncorrect+","+pCorrect+")") ;
		return series ;
 	}
 	
 	/**
	 * Calculates the Bayes margin for a data point using matrix forms of the data point,
	 * inverse covariance matrices of the classes and constant term of the Bayes classifier
	 * @param x data point in matrix form
	 * @param sigma1Inv inverse covariance matrix for class 1
	 * @param sigma2Inv inverse covariance matrix for class 2
	 * @param constant constant term in the Bayes classifier
	 * @return Bayes margin of example
	 */
 	private double bayes_margin(double[][] x, double[][] sigma1Inv, double[][] sigma2Inv, double constant) {
 		double[][] mu1 = Matrix.matrix(means[0],Matrix.MEAN) ;
 		double[][] mu2 = Matrix.matrix(means[1],Matrix.MEAN) ;
 		double[][] quadratic1 = Matrix.multiply(Matrix.multiply(Matrix.transpose(Matrix.add(x,mu1,true)),sigma1Inv),Matrix.add(x,mu1,true)) ;
 		double[][] quadratic2 = Matrix.multiply(Matrix.multiply(Matrix.transpose(Matrix.add(x,mu2,true)),sigma2Inv),Matrix.add(x,mu2,true)) ;
 		return (1d/2d)*(quadratic1[0][0]-quadratic2[0][0]+constant) ;	
 	}
 	
 	
 	/**
	 * Calculates the SVM margin of a data point.
	 * @param x data point as an svm_node
	 * @param model trained SVM
	 * @return Margin of example
	 */
 	private static double margin(svm_node[] x, svm_model model) {
 		int[] label = model.label ;
		double[] coef = model.sv_coef[0] ;
		double[] kvalue = new double[model.l];
		for(int i=0;i<model.l;i++)
			kvalue[i] = svm.kernel(x,model.SV[i],model.param);

		double sum = 0;
		int j = 0;
		for (int c=0;c<model.nr_class;c++) {
			for (int i=j;i<j+model.nSV[c];i++) {
				sum += coef[i] * kvalue[i] ;
			}
			j += model.nSV[c] ;
		}
		sum -= model.rho[0] ;
		return sum ;		
 	}
 	
 	
 	/**
	 * Calculates the Bhattacharya distance of two Gaussian distributions parameterised
 	 * by their means and covariances.
 	 * @param mu1 mean vector of distribution 1 in matrix form
 	 * @param mu2 mean vector of distribution 2 in matrix form
 	 * @param sigma1 covariance matrix of distribution 1
 	 * @param sigma2 covariance matrix of distribution 2
 	 * @return Bhattacharya distance
 	 */
 	public double bhattacharya(double[][] mu1,double[][] sigma1,
 								double[][] mu2,double[][] sigma2) {
 		double[][] means = Matrix.add(mu1,mu2,true) ;
 		double[][] sigmas = Matrix.scalarMultiply(Matrix.add(sigma1,sigma2,false),0.5) ;
 		double[][] multiple = Matrix.multiply(Matrix.multiply(Matrix.transpose(means),Matrix.inverse(sigmas)),means) ;
 		double term1 = (1d/8d)*(multiple[0][0]) ;
 		double term2 = (1d/2d)*Math.log(Matrix.determinant(sigmas)/Math.sqrt(Matrix.determinant(sigma1)*Matrix.determinant(sigma2))) ;
 		double bhattacharya = term1 + term2 ;
 		return Math.max(bhattacharya,-1*bhattacharya) ;
 	}
 	
 	
	/**
	 * Plots a series of ROC curves on an XYPlot.
	 * @param curves values for the ROC curves
	 * @param solutions values for the classifier solutions
	 * @param title title of chart
 	 */
	public void XYChart(XYDataset curves, XYDataset solutions,String title) {
		
		XYItemRenderer curveRenderer = new StandardXYItemRenderer(StandardXYItemRenderer.LINES) ;
		XYPlot plot = new XYPlot() ;
		plot.setDataset(curves) ;
		plot.setRenderer(curveRenderer) ;
		
		XYItemRenderer solutionsRenderer = new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES) ;
		plot.setSecondaryDataset(0,solutions) ;
		plot.setSecondaryRenderer(0,solutionsRenderer) ;
		
		ValueAxis domainAxis = new NumberAxis("P(false positive)") ;
		domainAxis.setRange(new Range(-0.05,1.05)) ;
		plot.setDomainAxis(domainAxis) ;
		ValueAxis rangeAxis = new NumberAxis("P(true positive)") ;
		rangeAxis.setRange(new Range(-0.05,1.05)) ;
		plot.setRangeAxis(rangeAxis) ;
		
		
		Stroke stroke = new BasicStroke(2) ;
		for (int i=0;i<curves.getSeriesCount();i++) {
 			plot.getRenderer().setSeriesStroke(i,stroke) ;
 			//plot.getRenderer().setSeriesPaint(i,ChartColor.red) ;	
 		}
		
		JFreeChart chart = new JFreeChart(title,plot) ;
		ChartFrame frame = new ChartFrame(title, chart) ;
		frame.pack() ;
		frame.setVisible(true) ;
	}
 	
 	
 	public static void main(String[] args) {
 		ROC roc = new ROC() ;
 		//roc.plotData("Initial") ;
 		//roc.curve(svm_parameter.LINEAR) ;
 		roc.compare(2) ;
 		//roc.plotData("Final") ;
 	}
 }
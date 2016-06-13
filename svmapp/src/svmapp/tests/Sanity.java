package svmapp.tests ;

import svmapp.* ;
import libsvm.* ;
import java.awt.Color ;
import java.util.ArrayList ;
import org.jfree.data.* ;
import org.jfree.chart.* ;
import org.jfree.chart.plot.* ;
import org.jfree.chart.renderer.* ;
import org.jfree.chart.axis.* ;
import org.jmathplot.gui.*;

/**
 * Carries out Sanity tests on LIBSVM based on set size and dimension measures.
 *
 * @author Navdeep Singh Daheley
 */
public class Sanity {
	
	/** data vectors */
	private double[][] data ;
	/** data labels */
	private int[] labels ;
	/** 3 dimensional 2 class Gaussian mean vectors */
	double[][] means = {{2,3,4,},{2,6,2,},{4,3,3,}} ;
	/** 3 dimensional 2 class Gaussian variance vectors */
	double[][] vars = {{0.5,1,1.2,},{1,4,1.3,},{2.5,1.5,1.5,}} ;
	/** increment in size for set size testing */
	int increment ;

	
	public Sanity(int size) {
		initData(size) ;
	}
	
	
	/**
	 * Generates mixture of Gaussians data using instance mean vectors 
	 * and variance vectors and stores it in instance arrays.
	 */
	private void initData(int size) {
		data = new double[size][3] ;
		labels = new int[size] ;			
		for (int i=0;i<data.length;i++) {
			int c = (int)(means.length*Math.random()) ;
			for (int d=0;d<data[0].length;d++)
				data[i][d] = Generator.gaussian(means[c][d],vars[c][d]) ;
			labels[i] = c ;
		}		
	}
	
	
	/**
	 * Plots the specified data using JMathPlot
	 * @param data data set
	 * @param labels data labels
	 */	
	public void plotData(double[][] data, int[] labels) {
		double[][][] array = Chart.createArray(data,labels,means.length) ;
		double[][] range = Chart.get3DRange(data) ;
		
		Plot3DPanel plot3d = new Plot3DPanel() ;
		for (int c=0;c<array.length;c++)
			plot3d.addPlot(array[c],String.valueOf(c+1),"SCATTER",Chart.classColours[c%Chart.classColours.length]) ;
		plot3d.setFixedBounds(range[0],range[1]) ;
	 	plot3d.setAxesLabels("x_0","x_1","x_2") ;
		
		new FrameView(plot3d);			
	}
	
	
	public void plotData() {
		plotData(data,labels) ;	
	}
	
	
	/**
	 * Trains an SVM on the given problem using a linear kernel.
	 * @param prob problem to train SVM on
	 * @return trained SVM
	 */
	private svm_model train(svm_problem prob) {
		
		svm_parameter svmParam = new svm_parameter() ;
		svmParam.svm_type = svm_parameter.C_SVC ;
		svmParam.kernel_type = svm_parameter.LINEAR ;
		svmParam.degree = 3;
		svmParam.gamma = 1/3;	// 1/k
		svmParam.coef0 = 1;
		svmParam.C = 1;
		svmParam.nu = 0.5;	
			
		return svm.svm_train(prob,svmParam);	
	} 
	
	
	/**
	 * Performs size sanity test on the data and plots results.
	 */
	private void sizeSanity() {
		XYSeries sizeSeries = new XYSeries("Error") ;
		increment = data.length/100 ;
		double min = 1 ;
		double max = 0 ;
		double error = 0 ;
		for (int i = increment; i < data.length; i+=increment) {
			double[][] subset = new double[i][data[0].length] ;
			int[] subsetLabels = new int[i] ;
			
			int roundoff = (int)(110 - (double)i*100/(double)data.length) ;
			roundoff = roundoff - roundoff%10 ;
			int repeat = (int)((double)roundoff/5) ;
			
			error = 0 ;
			for (int k=0;k<repeat;k++) {
				fill(subset,subsetLabels) ;
				error += error(subset,subsetLabels) ;
			}
			error = error/((double)repeat*(double)data.length) ;
			if (error < min) min = error ;
			if (error > max) max = error ;
			sizeSeries.add((double)i*100/(double)data.length,error) ;
		}
		error = (double)error(data,labels)/(double)data.length ;
		if (error < min) min = error ;
		sizeSeries.add(100,error) ;
		
		Range xRange = new Range(0,100) ;
		Range yRange = new Range(min-0.01,max+0.01) ;
		XYChart("Set Size Sanity Check","Set Size",sizeSeries,xRange,yRange) ;	
	}
	
	
	/**
	 * Fills a subset with data from the whole data set.
	 * @subset reduced size subset
	 * @subsetLabels reduced subset labels
	 */
	private void fill(double[][] subset,int[] subsetLabels) {
		double probability = (double)subset.length/(double)data.length ;
		for (int i=0;i<subset.length;i++) {
			boolean picked = false ;
			while (!picked) {
				int j = (int)(data.length*Math.random()) ;
				if (Math.random() <= probability) {
					subset[i] = data[j] ;
					subsetLabels[i] = labels[j] ;
					picked = true ;
				}
			}
		}
			
	}
	
	
	/**
	 * Performs dimensionality sanity test on the data and plots results.
	 */
	public void dimSanity() {
		int dim = data[0].length ;
		XYSeries dimSeries = new XYSeries("Error") ;
		svm_problem whole = SVMTrain.prob(data,labels) ;
		double min = 1 ;
		double max = 0 ;
		double error = 0 ;
		for (int d=1; d<dim;d++) {
			error = 0 ;
			for (int k=0;k<dim;k++) {
				double[][] subset = new double[data.length][data[0].length] ;
				for (int a=0;a<data.length;a++)
					for (int b=0;b<data[0].length;b++)
						subset[a][b] = data[a][b] ;
				strip(subset,d) ;
				plotData(subset,labels) ;
				error += error(subset,labels) ;
			}
			error = error/((double)dim*(double)data.length) ;
			if (error < min) min = error ;
			if (error > max) max = error ;
			dimSeries.add(d,error) ;
		}
		error = (double)error(data,labels)/(double)data.length ;
		if (error < min) min = error ;
		dimSeries.add(dim,error) ;
				
		Range xRange = new Range(1,dim) ;
		Range yRange = new Range(min-0.01,max+0.01) ;
		XYChart("Dimensionality Sanity Check","Dimension",dimSeries,xRange,yRange) ;		
	}
	
	
	/**
	 * Strips the dimensionality of the data set by a specified number of
	 * dimensions.
	 * @param subset reduced dimension subset
	 * @param d number of dimensions to strip
	 */
	private void strip(double[][] subset,int d) {
		ArrayList skip = new ArrayList();
		for (int i=0;i<subset[0].length-d;i++) {
			boolean stripped = false ;
			while (!stripped) {
				int j = (int)(subset[0].length*Math.random()) ;
				//System.out.println("STRIPPING DOWN TO "+d+". STRIPPING AWAY "+j) ;
				if (!skip.contains(new Integer(j))) {
					for (int k=0;k<subset.length;k++)
						subset[k][j] = 0 ;
					skip.add(new Integer(j)) ;
					stripped = true ;
				}
			}
		}			
	}
	
	
	/**
	 * Calculates the number of misclassification errors for an SVM trained on
	 * the specified subset and its labels.
	 */
	private int error(double[][] dataset, int[] labelset) {
		svm_problem whole = SVMTrain.prob(data,labels) ;
		svm_problem prob = SVMTrain.prob(dataset,labelset) ;
		svm_model model = train(prob) ;
		int error = 0 ;
		for(int j=0;j<whole.x.length;j++) {
			double v = svm.svm_predict(model,whole.x[j]);
			if(v != whole.y[j]) ++error;
		}
		return error ;
	}
	
	/**
	 * Plots a graph of error against the specified measure of either set size
	 * or dimensionality.
	 * @param title title of chart
	 * @param x x-axis title
	 * @param series data to plot
	 * @param xRange x-axis range
	 * @param yRange y-axis range
	 */
	public void XYChart(String title, String x, XYSeries series,Range xRange,Range yRange) {
		JFreeChart chart = ChartFactory.createXYLineChart(title, x, "Error", new XYSeriesCollection(series), 
							PlotOrientation.VERTICAL, true, false, false) ;
		chart.getXYPlot().getDomainAxis().setRange(xRange);
		chart.getXYPlot().getDomainAxis().setStandardTickUnits(TickUnits.createIntegerTickUnits()); 
 		chart.getXYPlot().getRangeAxis().setRange(yRange);
		ChartFrame frame = new ChartFrame(title, chart) ;
		frame.pack() ;
		frame.setVisible(true) ;		
	}
	
	
	public static void main(String[] args) {
		Sanity sanity = new Sanity(500) ;
		//sanity.plotData() ;
		//sanity.sizeSanity() ;		
		sanity.dimSanity() ;
	}	
}
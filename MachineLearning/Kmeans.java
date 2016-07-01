 
 import java.lang.Math ;
 import java.awt.Paint;
 import java.awt.Color;
 import org.jfree.data.XYSeries;
 import org.jfree.data.XYSeriesCollection;
 import org.jfree.chart.JFreeChart;
 import org.jfree.chart.ChartFactory;
 import org.jfree.chart.ChartFrame ;
 import org.jfree.chart.plot.PlotOrientation ;
 
 
 public class Kmeans {
 	
 	
 	private double[][] data ;
 	private int[] cluster ;
 	private XYSeriesCollection xyData ;
 	private XYSeriesCollection clusterData ;
 	private double[][] means ;
 	
 	
 	public Kmeans(int points, double p1, double[] s1, double[] m1, double[] s2, double[] m2) {
 		
 		data = new double[points][2] ;
 		cluster = new int[points] ;
 		xyData = new XYSeriesCollection() ;
 		clusterData = new XYSeriesCollection() ;
 		generateData(p1, s1, m1, s2, m2) ;

 	}	
 	
 	
 	public void generateData(double p1, double[] s1, double[] m1, double[] s2, double[] m2) {
 		
 		XYSeries gaussian1 = new XYSeries("Gaussian 1") ;
  		XYSeries gaussian2 = new XYSeries("Gaussian 2") ;
 		for (int m = 0; m < data.length; m++) {
 			double uniform = Math.random() ;
 			if (uniform <= p1) {
 				data[m][0] = gaussian(s1[0],m1[0]) ;
 				data[m][1] = gaussian(s1[1],m1[1]) ;
 				gaussian1.add(data[m][0],data[m][1]) ;
 			} else {
 				data[m][0] = gaussian(s2[0],m2[0]) ;
 				data[m][1] = gaussian(s2[1],m2[1]) ;
 				gaussian2.add(data[m][0],data[m][1]) ;
 			}
 		}
 		xyData.addSeries(gaussian1) ;
 		xyData.addSeries(gaussian2) ;
 		JFreeChart chart = ChartFactory.createScatterPlot("Data Generated From Mixture of Gaussians", "x", "y", xyData,
 							PlotOrientation.VERTICAL,true,false, false);
 		ChartFrame frame = new ChartFrame("MoG Data", chart);
 		frame.pack() ;
 		frame.setVisible(true) ;
 	}
 	
 	
 	public double gaussian(double sigma, double mu) {
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
 	
 	
 	public double distance(double[] data, double[] means) {
 		return Math.sqrt(Math.pow(data[0]-means[0],2)+Math.pow(data[1]-means[1],2)) ; 		
 	}
 	
 	
 	public void means(int number) {
 		
 		XYSeries[] meansSeries = new XYSeries[number] ;
 		means = new double[number][2] ;
 		for (int m = 0; m < number; m++) {
 			means[m][0] = Math.random()*10 ;
 			means[m][1] = Math.random()*10 ;
 		}
 		
	 	
	 	boolean finish ;
	 	do {
	 		/* Assigment Step */
	 		finish = true ;
	 		for (int d = 0; d < data.length; d++) {
	 			int prevCluster = cluster[d] ;
	 			double distance = distance(data[d],means[0]) ;
	 			cluster[d] = 0 ;
	 			for (int m = 1; m < means.length; m++) {
	 				if (distance(data[d],means[m]) < distance) {
	 					distance = distance(data[d],means[m]) ;
	 					cluster[d] = m ;
	 				}
	 			}
	 			
	 			if (cluster[d] != prevCluster) {
	 				finish = false ;
	 			}
	 		}
	 		
	 		/* Update Step */
	 		for (int m = 0; m < means.length; m++) {
	 			double[] sum = new double[2] ;
	 			int frequency = 0 ;
	 			for (int d = 0; d < data.length; d++) {
	 				if (cluster[d] == m) {
	 					sum[0] += data[d][0] ;
	 					sum[1] += data[d][1] ;
	 					frequency++ ;
	 				}
	 			}
	 			
	 			if (frequency != 0) {
	 				means[m][0] = sum[0]/frequency ;
	 				means[m][1] = sum[1]/frequency ;
	 			}
	 		}
	 	} while (finish == false) ;
	 	
		XYSeries meansValues = new XYSeries("Means") ;
		for (int m = 0; m < means.length; m++) {
			meansSeries[m] = new XYSeries("Cluster "+m) ;
		 	for (int d = 0; d < data.length; d++) {
		 		if (cluster[d] == m) {
		 			meansSeries[m].add(data[d][0],data[d][1]) ;
		 		}
		 	}
		 	meansValues.add(means[m][0],means[m][1]) ;
		 	clusterData.addSeries(meansSeries[m]) ;
		}
 		clusterData.addSeries(meansValues) ;
 		
 		JFreeChart chart = ChartFactory.createScatterPlot("Clusters from K-Means Algorithm", "x", "y", clusterData,
 							PlotOrientation.VERTICAL,true,false, false);
 		ChartFrame frame = new ChartFrame("K-Means Data", chart);
 		frame.pack() ;
 		frame.setVisible(true) ;
 					
 	}
 	
 	
 	public static void main(String[] args) {
 		double[] sigma1 = {1,2.5} ;
 		double[] mean1 = {3,5} ;
 		double[] sigma2 = {0.8,0.8} ;
 		double[] mean2 = {7.5,5} ;
 		Kmeans question3a = new Kmeans(100, 0.8, sigma1, mean1, sigma2, mean2) ;
 		question3a.means(3) ;
 		
 		/*double[] sigma3 = {0.6,8} ; 
 		double[] mean3 = {4,5} ;
 		double[] sigma4 = {0.3,7} ;
 		double[] mean4 = {6,5} ;
 		Kmeans question3b = new Kmeans(100,0.65,sigma3, mean3, sigma4, mean4) ;
 		question3b.means(2) ;*/
 	}
 }
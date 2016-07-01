 
 import java.lang.Math ;
 import java.awt.Paint;
 import java.awt.Color;
 import org.jfree.data.XYSeries;
 import org.jfree.data.XYSeriesCollection;
 import org.jfree.chart.JFreeChart;
 import org.jfree.chart.ChartFactory;
 import org.jfree.chart.ChartFrame ;
 import org.jfree.chart.plot.PlotOrientation ;
  
 public class Geyser {
 	
 	
 	private FileInput in ;
 	private double[][] data ;
 	
 	
 	public Geyser(String filename, int filelength) {
 		in = new FileInput(filename) ;
 		data = new double[filelength][2] ;
 		readData(filelength) ;
 	}
 	
 	
 	private void readData(int length) {
 		int n = 0 ;
 		while (length-n > 0) {
 			char[] charDuration = new char[9] ;
 			for (int m = 0; m < charDuration.length; m++) {
 				charDuration[m] = in.readCharacter() ;
 			}
 			data[n][0] = Double.valueOf(new String(charDuration)).doubleValue() ;
 			
 			char nextChar ;
 			do {
 				nextChar = in.readCharacter() ;
 			} while (nextChar != ',') ;
 			
 			char[] charWait = new char[2] ;
 			for (int m = 0; m < charWait.length; m++) {
 				charWait[m] = in.readCharacter() ;
 			}
 			data[n][1] = Double.valueOf(new String(charWait)).doubleValue() ;
 			System.out.println(n+":"+data[n][0]+","+data[n][1]) ;
 			n++ ;
 		} ;
 	}
 	
 	
 	public static void main(String[] args) {
 		
		Geyser q1 = new Geyser("geyser.txt", 295) ;
 	}
 	
 }
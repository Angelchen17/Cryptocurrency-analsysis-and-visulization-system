package CoinAnalysis_result;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import CoinAnalysis_gui.barChart;
import CoinAnalysis_gui.lineGraph;
import CoinAnalysis_gui.scatterPlot;
import CoinAnalysis_gui.table;
import CoinAnalysis_gui.viewer;
import CoinAnalysis_userSelection.userSelection;

public class resultSubject implements Result{

	/**
	 * create a new list of viewers
	 */
	public static List<viewer> viewerList = new ArrayList<>();

	/**
	 * update the viewers to diaplay all the results
	 * @param result from analysis
	 * @param us user selection
	 * @param panel to draw diagram
	 * @param f to draw diagram
	 */
	@Override
	public void notifyAllViewers(double[][] result, userSelection us, JPanel panel, JFrame f) {
		// TODO Auto-generated method stub
		
		viewerList.add(new table(result, us));
		viewerList.add(new scatterPlot(result, us));
		viewerList.add(new barChart(result, us));
		viewerList.add(new lineGraph(result, us));
		
		for(viewer chart : viewerList) {
			chart.update(panel, f);
		}
		
		viewerList.clear();
		
	}

	
}

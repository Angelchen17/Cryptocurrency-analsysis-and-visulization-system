package CoinAnalysis_gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public interface viewer  {
	/**
	 * Update the chart
	 * @param panel JPanel of the UI
	 * @param f JFrame of the UI
	 */
	public void update(JPanel panel, JFrame f);
}

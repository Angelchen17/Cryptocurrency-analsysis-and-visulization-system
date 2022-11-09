package CoinAnalysis_gui;


import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

import CoinAnalysis_userSelection.userSelection;


public class barChart implements viewer{
	
	/**
	 * represent the result of analysis
	 */
	private double[][] result;
	/**
	 * represent the selection of users
	 */
	private userSelection us;
	
	/**
	 * Constructor
	 * @param result the result of analysis
	 * @param us the result of the user selection
	 */
	public barChart(double[][] result, userSelection us) {
		this.result = result;
		this.us = us;
	}
	
	/**
	 * update the chart based on the result
	 */
	@Override
	public void update(JPanel panel, JFrame f) {
		// TODO Auto-generated method stub
		// create a chart
		DefaultCategoryDataset dataset = getDateset(result, us.getCurrency(), get_array(result, us));
		String metric = us.getMetric().split(" ")[0];
		JFreeChart chart = null;
		
		if (!metric.equals("Change")) {
			CategoryPlot plot = new CategoryPlot();
			BarRenderer barrenderer1 = new BarRenderer();
			plot.setDataset(0, dataset);
			plot.setRenderer(0, barrenderer1);
			CategoryAxis domainAxis = new CategoryAxis("Date");
			plot.setDomainAxis(domainAxis);
			LogAxis rangeAxis = new LogAxis("Price(CAD)");
			rangeAxis.setRange(new Range(1.0, 10000000000000.0));
			plot.setRangeAxis(rangeAxis);
			chart = new JFreeChart(us.getInterval() + " " + us.getMetric() + " Bar Chart", new Font("Serif", java.awt.Font.BOLD, 17), plot,
					true);
		}
		else {
			chart = ChartFactory.createBarChart(
				us.getInterval() + " " + us.getMetric() + " Bar Chart", // title
				"Date",   // x
				us.getMetric() + "(%)",  // y (units)
				dataset);
			chart.getTitle().setFont(new Font("Serif", java.awt.Font.BOLD, 17));
		}
		
		ChartPanel cp = new ChartPanel(chart);
		
		cp.setBounds(530, 365, 490, 310);
		cp.setVisible(true);
		cp.setMouseZoomable(false);
		cp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		cp.setBackground(Color.white);
		
		panel.add(cp);
		f.setContentPane(panel);
	}
	
	/**
	 * get a date array for chart
	 * @param result The result of the analysis
	 * @param us The selection of users
	 * @return a String array to store date
	 */
	private String[] get_array(double[][] result, userSelection us) {
		
		String[] today = Main_UI.today.split("/");
		int year = Integer.parseInt(today[2]);
		int month = Integer.parseInt(today[1]);
		int day = Integer.parseInt(today[0]);
		int count_x = result[0].length;
		String selection = us.getInterval();
		
		String[] res = new String[count_x];

		if (selection=="Daily") {
			int count = count_x;
			while (count > 0) {
				res[count-1] = day+"-"+get_month(month);
				day -= 1;
				count -= 1;
				if (day==0) {
					month -= 1;
					if (month==1||month==3||month==5||month==7||month==8||month==10||month==12)
						day = 31;
					else if (month==4||month==6||month==9||month==11)
						day = 30;
					else {
						int y = (month==0) ? year -1 : year;
						day = (y % 4==0) ? 29 : 28;
					}
				}
				if (month==0) {
					month = 12;
					year -= 1;
				}
			}
		}
		else if (selection=="Weekly") {
			int c2  = count_x;
			for (int i=0; i<count_x; i++) {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
				calendar.add(Calendar.DATE, -1*i*7);
				String ago = sdf1.format(calendar.getTime());
				calendar.clear();
				String[] temp = ago.split("/");
				res[c2-1] = temp[0]+"-"+get_month(Integer.parseInt(temp[1]));
				c2 -= 1;
			}
		}
		else if (selection=="Monthly") {
			int count = count_x;
			while (count > 0) {
				res[count-1] = get_month(month)+"-"+year;
				month -= 1;
				count -= 1;
				if (month==0) {
					month = 12;
					year -= 1;
				}
			}
		}
		else if (selection.equals("Yearly")) {
			int count = count_x;
			while (count > 0) {
				res[count-1] = ""+year;
				year -= 1;
				count -= 1;
			}
		}
		
		return res;
	}
	
	/**
	 * Get the month string based on a number
	 * @param m the number of the month
	 * @return the string of the month
	 */
	private String get_month(int m) {
		String[] month_list = {
				"Jan", "Feb", "Mar", "Apr", "May", "Jun",
				"Jul", "Aug", "Sept", "Oct", "Nov", "Dec", 
		};
		return month_list[m-1];
	}
	
	/**
	 * get the a DefaultCategoryDataset
	 * @param data the analysis result
	 * @param selected_cryptocurrency the selected coin array
	 * @param selected_date the selected date
	 * @return a DefaultCategoryDataset
	 */
	private DefaultCategoryDataset getDateset(double[][] data, String[] selected_cryptocurrency, String[] selected_date) {
		// compose data set
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < selected_cryptocurrency.length; i++) 
			for (int j = 0; j < selected_date.length; j++) 
				dataset.addValue(data[i][j], selected_cryptocurrency[i], selected_date[j]);

		return dataset;
	}
}

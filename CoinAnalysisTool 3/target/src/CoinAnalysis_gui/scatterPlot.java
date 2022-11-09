package CoinAnalysis_gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;


import CoinAnalysis_userSelection.userSelection;

public class scatterPlot implements viewer{

	/**
	 * represent the result of analysis
	 */
	private double[][] result;
	/**
	 * represent the user selection
	 */
	private userSelection us;
	
	/**
	 * Constructor
	 * @param result the result of analysis
	 * @param us the user selection
	 */
	public scatterPlot(double[][] result, userSelection us) {
		this.result = result;
		this.us = us;
	}
	
	/**
	 * update the chart
	 */
	@Override
	public void update(JPanel panel, JFrame f) {
		// TODO Auto-generated method stub
		// create a chart
		double[][] data = result;
		String metric = us.getMetric().split(" ")[0];
		
		if (!metric.equals("Change")) {
			XYPlot plot = new XYPlot();
			XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
	
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			Day[] d = getdaySet();
			for (int i=0; i<data.length; i++){
				TimeSeries series = new TimeSeries(us.getCurrency()[i]+" - "+us.getInterval());
				for (int j=0; j<data[0].length; j++){
					series.add(d[j], data[i][j]);
				}
				dataset.addSeries(series);
			}
	
			plot.setDataset(0, dataset);
			plot.setRenderer(0, itemrenderer1);
			DateAxis domainAxis = new DateAxis("");
			plot.setDomainAxis(domainAxis);
			plot.setRangeAxis(new LogAxis("Price(CAD)"));
			
			JFreeChart scatterChart = new JFreeChart(us.getInterval()+" "+us.getMetric()+" Scatter Chart",
					new Font("Serif", java.awt.Font.BOLD, 17), plot, true);
	
			ChartPanel chartPanel = new ChartPanel(scatterChart);
			chartPanel.setPreferredSize(new Dimension(600, 300));
			chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			chartPanel.setBackground(Color.white);
			chartPanel.setBounds(30, 365, 490, 310);
			chartPanel.setVisible(true);
			panel.add(chartPanel);
			f.setContentPane(panel);
		}
		else {
			DefaultCategoryDataset dataset = getDateset(result, us.getCurrency(), get_array(result, us));
			JFreeChart chart = ChartFactory.createLineChart(
					us.getInterval() + " " + us.getMetric() + " Scatter Chart", // title
					"Date",   // x   // set the format of the annotation
					us.getMetric()+"(%)",  // y (units)
					dataset);
			chart.getTitle().setFont(new Font("Serif", java.awt.Font.BOLD, 17));
			CategoryPlot p = (CategoryPlot) chart.getPlot();

			LineAndShapeRenderer lasp = (LineAndShapeRenderer)p.getRenderer();
			lasp.setDefaultShapesVisible(true);
			lasp.setDefaultLinesVisible(false);
			
			ChartPanel cp = new ChartPanel(chart);
			cp.setBounds(30, 365, 490, 310);
			cp.setVisible(true);
			cp.setMouseZoomable(false);
			panel.add(cp);
			f.setContentPane(panel);
	    } 
	}
	
	/**
	 * get the date set
	 * @return return the date set
	 */
	private Day[] getdaySet() {
		int count_x = result[0].length;
		Day[] res = new Day[count_x];
		
		if (us.getInterval().equals("Daily")) {
			int c  = count_x;
			for (int i=0; i<count_x; i++) {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
				calendar.add(Calendar.DATE, -1*i);
				String ago = sdf1.format(calendar.getTime());
				calendar.clear();
				String[] temp = ago.split("/");
				res[c-1] = new Day(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
				c -= 1;
			}
		}
		else if (us.getInterval().equals("Weekly")) {
			int c2  = count_x;
			for (int i=0; i<count_x; i++) {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
				calendar.add(Calendar.DATE, -1*i*7);
				String ago = sdf1.format(calendar.getTime());
				calendar.clear();
				String[] temp = ago.split("/");
				res[c2-1] = new Day(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
				c2 -= 1;
			}
		}
		else if (us.getInterval().equals("Monthly")) {
			int c3  = count_x;
			for (int i=0; i<count_x; i++) {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
				calendar.add(Calendar.DATE, -1*i*30);
				String ago = sdf1.format(calendar.getTime());
				calendar.clear();
				String[] temp = ago.split("/");
				res[c3-1] = new Day(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
				c3 -= 1;
			}
		}
		else {
			int c4  = count_x;
			for (int i=0; i<count_x; i++) {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
				calendar.add(Calendar.DATE, -1*i*365);
				String ago = sdf1.format(calendar.getTime());
				calendar.clear();
				String[] temp = ago.split("/");
				res[c4-1] = new Day(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
				c4 -= 1;
			}
		}
		return res;
	}
	
	/**
	 * get the date set
	 * @param result analysis result
	 * @param us user selection
	 * @return return an array storing date
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
	 * get a DefaultCategoryDataset
	 * @param data analysis result
	 * @param selected_cryptocurrency the selected coin array
	 * @param selected_date the selected date
	 * @return return a DefaultCategoryDataset
	 */
	private DefaultCategoryDataset getDateset(double[][] data, String[] selected_cryptocurrency, String[] selected_date) {
		// compose data set
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < selected_cryptocurrency.length; i++) 
			for (int j = 0; j < selected_date.length; j++) 
				dataset.addValue(data[i][j], selected_cryptocurrency[i]+" - "+us.getInterval(), selected_date[j]);

		return dataset;
	}
}

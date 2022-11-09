package CoinAnalysis_gui;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import CoinAnalysis_userSelection.userSelection;

public class table implements viewer{
	
	/**
	 * represent the analysis result
	 */
	private double[][] result;
	/**
	 * represent the user selection
	 */
	private userSelection us;
	
	/**
	 * Constructor 
	 * @param result analysis result
	 * @param us user selection
	 */
	public table(double[][] result, userSelection us) {
		this.result = result;
		this.us = us;
	}
	
	/**
	 * update the chart
	 */
	@Override
	public void update(JPanel panel, JFrame f) {
		// TODO Auto-generated method stub
		double[][] data = result;
		String[][] Data = new String[us.getCurrency().length][data[0].length+1];
		String[] Date = new String[get_array(result, us).length+1];
		
		for (int k = 0; k < us.getCurrency().length; k++) 
			Data[k][0] = us.getCurrency()[k];
		
		for (int i = 0; i < us.getCurrency().length; i++)
			for (int j = 0; j < data[0].length; j++)
				Data[i][j+1] = "" + data[i][j];
		
		Date[0] = "Cryptocurrency";
		for (int m = 0; m < get_array(result, us).length; m++)
			Date[m+1] = get_array(result, us)[m];
		
		JTable table = new JTable(Data, Date);
		table.setVisible(true);
		table.setShowGrid(true);
		table.setGridColor(Color.black);
		table.getTableHeader().setBackground(Color.lightGray);
		table.setFillsViewportHeight(true);
		JScrollPane jsp=new JScrollPane(table);
		jsp.setBorder(BorderFactory.createTitledBorder(
			      BorderFactory.createEtchedBorder(), us.getInterval()+" "+us.getMetric()+" "+"Summary Table", TitledBorder.CENTER,
			      TitledBorder.TOP));
		jsp.setBackground(Color.lightGray);
		jsp.setBounds(30, 45, 490, 310);
		panel.add(jsp);
	}
	
	/**
	 * get the date array
	 * @param result analysis result
	 * @param us user selection
	 * @return return a date array
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
						int y = 0;
						if (month==0) 
							y = year -1;
						else
							y = year;
						if (y % 4==0)
							day = 29;
						else
							day = 28;
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
}

package CoinAnalysis_gui;
import java.awt.Dimension;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import CoinAnalysis_analysis.Analysis;
import CoinAnalysis_analysis.getAnalysis;
import CoinAnalysis_result.resultSubject;
import CoinAnalysis_userSelection.userSelection;


public class Main_UI {
	
	/**
	 * represent the selected coin array
	 */
	private String[] select_cryptocurrency_text;
	/**
	 * represent the number of selected coins
	 */
	private int count_select_cryptocurrency;
	/**
	 * represent the selected date
	 */
	private String selected_date;
	/**
	 * represent the selected metric
	 */
	private String selected_metric;
	/**
	 * represent the selected interval
	 */
	private String selected_interval;
	/**
	 * represent today's date
	 */
	public static String today;
	
	/**
	 * Constructor
	 */
	public Main_UI() {
		select_cryptocurrency_text = new String[1];
		count_select_cryptocurrency = 0;
		selected_date = null;
		selected_metric = null;
		selected_interval = null;
	}
	
	/**
	 * display the main UI
	 * @param f JFrame
	 * @param panel JPanel
	 */
	@SuppressWarnings("deprecation")
	public void mainUI(JFrame f, JPanel panel) {
		panel.removeAll();
		// show main window
		f.setTitle("Crypto Analysis Tool");
		// set the width and height of the window
		f.resize(new Dimension(1300, 750));
		// set the location of the main UI
		f.setLocationRelativeTo(null);
		
		// choose coins
		choose_a_cryptocurrency(panel, f);
		// choose a date
		choose_a_date(panel, f);
		// choose a metric
		choose_a_metric(panel);
		// choose an interval
		choose_an_interval(panel);
		// Refresh button and show result
		refresh(panel, f);
	}
	
	/**
	 * represent the choosing a cryptocurrency part
	 * @param panel JPanel
	 * @param f JFrame
	 */
	private void choose_a_cryptocurrency(JPanel panel, JFrame f) {
		
		// label
		JLabel cryptocurrency = new JLabel("Choose a cryptocurrency:");
		cryptocurrency.setBounds(400, 5, 180, 25);
		panel.add(cryptocurrency);
		// combo box
		JComboBox<String> cryptocurrency_box = new JComboBox<String>();
		// cryptocurrency list
		String[] cryptocurrency_list = {
										"Bitcoin", "Ethereum", "Tether", "Cardano", 
										"Solana", "Polkadot", 
										};
		// add items
		for (String str1 : cryptocurrency_list) 
			cryptocurrency_box.addItem(str1);
		cryptocurrency_box.setBounds(570, 7, 150, 25);
		panel.add(cryptocurrency_box);
		// + button
		JButton plus = new JButton(" + ");
		plus.setBounds(725, 7, 32, 25);
		panel.add(plus);

		// - button
		JButton minus = new JButton(" - ");
		minus.setBounds(770, 7, 32, 25);
		panel.add(minus);
		
		// List of selected cryptocurrency
		JLabel list = new JLabel("List of selected cryptocurrencies:");
		list.setBounds(1050, 25, 220, 25);
		panel.add(list);
		JTextArea selected_cryptocurrency = new JTextArea();
		selected_cryptocurrency.setEditable(false);
		JScrollPane scroll = new JScrollPane(selected_cryptocurrency);
		scroll.setBounds(1050, 50, 240, 640);
		panel.add(scroll);
		
		// check if the cryptocurrency is valid
		
		// + action
		plus.addActionListener((e)->{
			String temp = "";
			String str4 = cryptocurrency_box.getSelectedItem().toString();
			boolean flag = true;
			for (String str : select_cryptocurrency_text)
				if (str == str4)
					flag = false;
			if (str4.equals("Polkadot")) {  // set polkadot as not allowable
				JOptionPane.showMessageDialog(f, "This cryptocurrency is not allowed!");
				flag = false;
			}
			if (flag) {
				select_cryptocurrency_text[count_select_cryptocurrency] = str4;
				for (int i = 0; i <= count_select_cryptocurrency; i++) 
						temp = temp + select_cryptocurrency_text[i] + "\n";
				selected_cryptocurrency.setText(temp);
				count_select_cryptocurrency += 1;
				extend_capacity();
			}
		});
		// - action
		minus.addActionListener((e)->{
			String temp = "";
			if (count_select_cryptocurrency >= 1) {
				for (int j = 0; j <= count_select_cryptocurrency; j++) {
					String str3 = cryptocurrency_box.getSelectedItem().toString();
					if (select_cryptocurrency_text[j] == str3) {
						for (int k = j; k < count_select_cryptocurrency - 1; k++) 
							select_cryptocurrency_text[k] = select_cryptocurrency_text[k + 1];
						count_select_cryptocurrency -= 1;
						reduce_capacity();
						
						for (int k = 0; k < count_select_cryptocurrency; k++) 
							temp = temp + select_cryptocurrency_text[k] + "\n";
						selected_cryptocurrency.setText(temp);
						break;
					}
				}
			}
		});
	}
	
	/**
	 * represent the choosing a date part
	 * @param panel JPanel
	 * @param f JFrame
	 */
	private void choose_a_date(JPanel panel, JFrame f) {
		// label
		JLabel date = new JLabel("From:");
		date.setBounds(130, 691, 150, 25);
		panel.add(date);
		// combo box
		UtilDateModel dateModel = new UtilDateModel();
		Properties properties = new Properties();
	    properties.put("text.today", "Today");
	    JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		dateModel.setSelected(true);
		datePicker.setVisible(true);
		
		datePicker.setBounds(170, 690, 190, 25);
		panel.add(datePicker);
		
		// check if the date is valid
		today = datePicker.getJFormattedTextField().getText();
		selected_date = today;
		datePicker.addActionListener((e)->{
			String[] toda = today.split("/");
			String temp = datePicker.getJFormattedTextField().getText();
			String[] tem = temp.split("/");
			
			if (Integer.parseInt(toda[2]) < Integer.parseInt(tem[2])) 
				JOptionPane.showMessageDialog(f, "The selected date is invalid!");
			else if (Integer.parseInt(toda[1]) < Integer.parseInt(tem[1]) && Integer.parseInt(toda[2]) == Integer.parseInt(tem[2])) 
				JOptionPane.showMessageDialog(f, "The selected date is invalid!");
			else if (Integer.parseInt(toda[0]) < Integer.parseInt(tem[0]) && Integer.parseInt(toda[1]) == Integer.parseInt(tem[1]) && Integer.parseInt(toda[2]) == Integer.parseInt(tem[2]))
				JOptionPane.showMessageDialog(f, "The selected date is invalid!");
			else 
				selected_date = temp;

		});	
	}
	
	/**
	 * represent the choosing a metric part
	 * @param panel JPanel
	 */
	private void choose_a_metric(JPanel panel) {
		// label
		JLabel metric = new JLabel("Metric:");
		metric.setBounds(375, 691, 150, 25);
		panel.add(metric);
		// combo box
		JComboBox<String> metric_box = new JComboBox<String>();
		// metric list
		String[] metric_list = {
								"Price", "MarketCap", "Volume", "Coins in Circulation", 
								"Change of unit price", "Change of market cap", "Change of volume", 
								"Change of coins in circulation"
								};
		// add items
		for (String str1 : metric_list) 
			metric_box.addItem(str1);
		metric_box.setBounds(420, 692, 245, 25);
		panel.add(metric_box);
		
		selected_metric = metric_box.getSelectedItem().toString();
		metric_box.addActionListener((e)->{
			selected_metric = metric_box.getSelectedItem().toString();
		});	
	}
	
	/**
	 * represent the choosing an interval part
	 * @param panel JPanel
	 */
	private void choose_an_interval(JPanel panel) {
		// label
		JLabel interval = new JLabel("Choose interval:");
		interval.setBounds(680, 691, 150, 25);
		panel.add(interval);
		// combo box
		JComboBox<String> interval_box = new JComboBox<String>();
		// interval list
		String[] interval_list = {
								  "Daily", "Weekly", "Monthly", "Yearly"
								  };
		// add items
		for (String str1 : interval_list) 
			interval_box.addItem(str1);
		interval_box.setBounds(785, 692, 120, 25);
		panel.add(interval_box);
		
		selected_interval = interval_box.getSelectedItem().toString();
		interval_box.addActionListener((e)->{
			selected_interval = interval_box.getSelectedItem().toString();
		});	
	}
	
	/**
	 * represent the refresh button
	 * @param panel JPanel
	 * @param f Jframe
	 */
	private void refresh(JPanel panel, JFrame f) {
		
		// refresh button
		JButton refresh = new JButton("Refresh");
		refresh.setBounds(920, 691, 80, 25);
		panel.add(refresh);
		// action
		refresh.addActionListener((e)->{
			// remove old chart

			while (panel.getComponentCount()+0 > 13) {
				panel.remove(panel.getComponentCount()-1);
			}

			userSelection selection = new userSelection(get_select_cryptocurrency(), get_selected_date(), get_selected_metric(), get_selected_interval());

			if (get_select_cryptocurrency()[0]==null) {
				JOptionPane.showMessageDialog(f, "Please select your coins!");
			}
			else {
				// analysis
				getAnalysis get_analysis = new getAnalysis();
				Analysis analysis = get_analysis.createAnalysis(selection);
				// get result
				double[][] result = analysis.perform(selection);
				resultSubject rs = new resultSubject();
				rs.notifyAllViewers(result, selection, panel, f);
			}
		});	
	}
	
	/**
	 * get selected date
	 * @return selected date
	 */
	private String get_selected_date() {
		return selected_date;
	}
	
	/**
	 * get selected metric
	 * @return selected metric
	 */
	private String get_selected_metric() {
		return selected_metric;
	}
	
	/**
	 * get selected interval
	 * @return selected interval
	 */
	private String get_selected_interval() {
		return selected_interval;
	}
	
	/**
	 * get selected cryptocurrency
	 * @return selected get_select_cryptocurrency array
	 */
	private String[] get_select_cryptocurrency() {
		return select_cryptocurrency_text;
	}
	
	/**
	 * extend array's capacity, one more location
	 */
	private void extend_capacity() {
		String[] large = new String[select_cryptocurrency_text.length + 1];
		if (select_cryptocurrency_text.length == count_select_cryptocurrency)
			for (int i = 0; i < select_cryptocurrency_text.length; i++)
				large[i] = select_cryptocurrency_text[i];
		select_cryptocurrency_text = large;
	}
	
	/**
	 * reduce array's capacity, reduce a location
	 */
	private void reduce_capacity() {
		String[] small = new String[count_select_cryptocurrency + 1];
		for (int i = 0; i < count_select_cryptocurrency; i++)
			small[i] = select_cryptocurrency_text[i];
		select_cryptocurrency_text = small;
	}
}

package CoinAnalysis_gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import CoinAnalysis_login.userLogin;
import CoinAnalysis_login.*;


public class loginPanel {
	
	/**
	 * Constructor
	 */
	public loginPanel() {
		
	}
	
	/**
	 * represent the login page
	 */
	public void login() {
		
		// create a login window
		JFrame f = new JFrame();
		f.setTitle("Login");
		// set the width and height of the window
		f.setResizable(false);
		f.setPreferredSize(new Dimension(320, 150));
		// set the location of the window
		f.setLocationRelativeTo(null);
		// set close operation
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* place components */
		JPanel panel = new JPanel();
		f.add(panel);
		
		// set layout
		panel.setLayout(null);
		
		// create label
		JLabel userLabel = new JLabel("Username:");
		JLabel passwordLabel = new JLabel("Password:");
		// place label
		userLabel.setBounds(20, 20, 80, 25);
		passwordLabel.setBounds(20, 50, 80, 25);
		panel.add(userLabel);
		panel.add(passwordLabel);
		
		// create text field
		JTextField userText = new JTextField(30);
		JPasswordField passwordText = new JPasswordField(30);
		// place text field
		userText.setBounds(90, 20, 200, 25);
		passwordText.setBounds(90, 50, 200, 25);
		panel.add(userText);
		panel.add(passwordText);
		
		// submit button
		JButton submitButton = new JButton("Submit!");
		submitButton.setBounds(125, 85, 80, 25);
		panel.add(submitButton);
		
		// add action to submit button
		submit(userText, passwordText, submitButton, f, panel);

		// show the window
		f.pack();
		f.setVisible(true);
	}
	
	/**
	 * represent the submit button
	 * @param userText Using JTextField to store the user name
	 * @param passwordText Using JPasswordField to store the password
	 * @param submitButton The submit button
	 * @param f JFrame
	 * @param panel JPanel
	 */
	@SuppressWarnings("deprecation")
	private void submit(JTextField userText, JPasswordField passwordText, JButton submitButton, JFrame f, JPanel panel) {
		submitButton.addActionListener((e)->{
			String username = userText.getText();
			String password = passwordText.getText();
			
			userLogin login = userLogin.getInstance();
			login.setUsername(username);
			login.setPassword(password);
			credential validate = new credential();
			boolean check = validate.checkCredential(login);

			if (check) { // if the combination of user name and password is correct
				// go to main UI
				Main_UI MainUI = new Main_UI();
				MainUI.mainUI(f, panel);
			}
			else { // incorrect
				// pop a notification and terminate the application
				JOptionPane.showMessageDialog(f, "Incorrect user information!");
				f.dispose();
			}
		});
	}
}

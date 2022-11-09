package CoinAnalysis_login;

import java.io.Serializable;

import CoinAnalysis_gui.*;

public class userLogin implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static userLogin instance = null;
	private String username;
	private String password;
	
	public userLogin() {

	}
	
	public void setUsername(String name) {
		this.username=name;
	}
	
	public void setPassword(String pass) {
		this.password=pass;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public static userLogin getInstance() {
		if (instance == null) {
			instance = new userLogin();
		}
		return instance;
	}
	

}

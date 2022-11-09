import CoinAnalysis_gui.loginPanel;
import CoinAnalysis_login.credential;
import CoinAnalysis_login.userLogin;

public class Start {
	
	public static void main(String[] args) {
		credential c = new credential();
		userLogin u1 = new userLogin();
		u1.setUsername("group6");
		u1.setPassword("666666");
		
		userLogin u2 = new userLogin();
		u2.setUsername("kk");
		u2.setPassword("666666");
		
		userLogin u3 = new userLogin();
		u3.setUsername("aa");
		u3.setPassword("111111");
		
		userLogin[] serObj = {u1, u2, u3};
		c.add_user(serObj);
		
		loginPanel CoinGecko = new loginPanel();
		CoinGecko.login();
	}
} // ends class

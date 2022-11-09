package CoinAnalysis_result;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import CoinAnalysis_analysis.*;
import CoinAnalysis_gui.viewer;
import CoinAnalysis_login.userLogin;
import CoinAnalysis_userSelection.IuserSelection;
import CoinAnalysis_userSelection.userSelection;

public interface Result {
	List<viewer> viewerList=new ArrayList<>();
	public void notifyAllViewers(double[][] result, userSelection us, JPanel panel, JFrame f);
	
}

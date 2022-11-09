package CoinAnalysis_analysis;

import CoinAnalysis_userSelection.userSelection;

public interface Analysis {
	Analysis type=null;
	abstract double[][] perform(userSelection us);
}

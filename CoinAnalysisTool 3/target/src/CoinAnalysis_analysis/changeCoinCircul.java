package CoinAnalysis_analysis;

import CoinAnalysis_userSelection.userSelection;

/*
 * Description: the changeCoinCircul class implements the abstract class Analysis, and mainly
 * implements the perform function to calculate the percentage change of coins in circulation
 * from one interval selected to another
 * 
 * @param None
 * @return None
 * */
public class changeCoinCircul implements Analysis{
	/*
	 * Description: The function perform of changeCoinCircul class calculates the percentage of coins in circulation
	 * from one interval to another interval, which is the division of difference of coins in circulation between two 
	 * intervals by the previous interval to get the result
	 * 
	 * @param userSelection: the user's selection in UI
	 * @return return a 2nd dimensional double array contains the change percentage of coins in circulation between intervals
	 * */
	@Override
	public double[][] perform(userSelection us) {
		// get the market capitalization analysis and unit market capitalization list by interval
		getAnalysis analysis = new getAnalysis();
		us.setMetric("Coins in Circulation");
		Analysis coinCirculAnalysis = analysis.createAnalysis(us);
		double[][] coinCirculDataList = coinCirculAnalysis.perform(us);
		
		us.setMetric("Change of coins in circulation");
		double[][] coinCirculChange = new double[coinCirculDataList.length][];
		for(int currencyIndex=0; currencyIndex<coinCirculDataList.length; currencyIndex++) {
			if(coinCirculDataList[currencyIndex].length <= 1) {
				coinCirculChange[currencyIndex] = new double[1];
				coinCirculChange[currencyIndex][0] = 0;
			} else {				
				coinCirculChange[currencyIndex] = new double[coinCirculDataList[currencyIndex].length - 1];
				for(int dataIndex=0; dataIndex<coinCirculDataList[currencyIndex].length - 1; dataIndex++) {
					coinCirculChange[currencyIndex][dataIndex] = (coinCirculDataList[currencyIndex][dataIndex+1] - coinCirculDataList[currencyIndex][dataIndex]) / coinCirculDataList[currencyIndex][dataIndex];
				}
			}
		}
		return coinCirculChange;
	}
}

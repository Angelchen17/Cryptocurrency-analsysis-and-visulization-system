package CoinAnalysis_analysis;

import CoinAnalysis_reader.IdataFetcher;
import CoinAnalysis_reader.dataFetcher;
import CoinAnalysis_userSelection.userSelection;

/*
 * Description: the coinCircul class implements the abstract class Analysis, and mainly
 * implements the perform function to calculate the coin in circulation
 * 
 * @param None
 * @return None
 * */
public class coinCircul implements Analysis{
	/*
	 * Description: The function perform of coinCircul class calculates the coins in circulation, 
	 * which is the division of market capitalization value by unit price to get the result
	 * 
	 * @param userSelection: the user's selection in UI
	 * @return return a 2nd dimensional double array contains the coins in circulation of each interval
	 * */	
	@Override
	public double[][] perform(userSelection us) {
		// TODO Auto-generated method stub
		// get the unit price analysis and unit price data list by interval
		getAnalysis firstAnalysis = new getAnalysis();
		us.setMetric("Price");
		Analysis unitPriceAnalysis = firstAnalysis.createAnalysis(us);
		double[][] unitPriceDataList = unitPriceAnalysis.perform(us);

		// get the market capitalization analysis and unit market capitalization list by interval
		getAnalysis secondAnalysis = new getAnalysis();
		us.setMetric("MarketCap");
		Analysis marketCapAnalysis = secondAnalysis.createAnalysis(us);
		double[][] marketCapDataList = marketCapAnalysis.perform(us);
		
		us.setMetric("Coins in Circulation");
		// the coins in circulation is the division of market capitalization value by unit price to get the result
		double[][] coinsInCircul = new double[unitPriceDataList.length][];
		for(int currencyIndex=0; currencyIndex<unitPriceDataList.length; currencyIndex++) {
			coinsInCircul[currencyIndex] = new double[unitPriceDataList[currencyIndex].length];
			for(int dataIndex=unitPriceDataList[currencyIndex].length-1; dataIndex>=0; dataIndex--) {
				coinsInCircul[currencyIndex][dataIndex] = marketCapDataList[currencyIndex][dataIndex] / unitPriceDataList[currencyIndex][dataIndex];
			}
		}
		return coinsInCircul;
	}
}

package CoinAnalysis_analysis;

import CoinAnalysis_userSelection.userSelection;

/*
 * Description: the changeMarketCap class implements the abstract class Analysis, and mainly
 * implements the perform function to calculate the percentage change of market capitalization
 * from one interval selected to another
 * 
 * @param None
 * @return None
 * */
public class changeMarketCap implements Analysis{
	/*
	 * Description: The function perform of changeMarketCap class calculates the percentage of market capitalization
	 * from one interval to another interval, which is the division of difference of market capitalization between two 
	 * intervals by the previous interval to get the result
	 * 
	 * @param userSelection: the user's selection in UI
	 * @return return a 2nd dimensional double array contains the change percentage of market capitalization between intervals
	 * */
	@Override
	public double[][] perform(userSelection us) {
		// get the market capitalization analysis and unit market capitalization list by interval
		getAnalysis analysis = new getAnalysis();
		us.setMetric("MarketCap");
		Analysis marketCapAnalysis = analysis.createAnalysis(us);
		double[][] marketCapDataList = marketCapAnalysis.perform(us);
		
		us.setMetric("Change of market cap");
		double[][] marketCapChange = new double[marketCapDataList.length][];
		for(int currencyIndex=0; currencyIndex<marketCapDataList.length; currencyIndex++) {
			if(marketCapDataList[currencyIndex].length <= 1) {
				marketCapChange[currencyIndex] = new double[1];
				marketCapChange[currencyIndex][0] = 0;
			} else {
				marketCapChange[currencyIndex] = new double[marketCapDataList[currencyIndex].length - 1];
				for(int dataIndex=0; dataIndex<marketCapDataList[currencyIndex].length - 1; dataIndex++) {
					marketCapChange[currencyIndex][dataIndex] = (marketCapDataList[currencyIndex][dataIndex+1] - marketCapDataList[currencyIndex][dataIndex]) / marketCapDataList[currencyIndex][dataIndex];
				}
			}
		}
		return marketCapChange;
	}	
}

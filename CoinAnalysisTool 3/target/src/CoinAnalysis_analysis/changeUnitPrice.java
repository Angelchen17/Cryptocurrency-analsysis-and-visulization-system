package CoinAnalysis_analysis;

import CoinAnalysis_userSelection.userSelection;

/*
 * Description: the changeUnitPrice class implements the abstract class Analysis, and mainly
 * implements the perform function to calculate the percentage change of unit price
 * from one interval selected to another
 * 
 * @param None
 * @return None
 * */
public class changeUnitPrice implements Analysis{
	/*
	 * Description: The function perform of changeUnitPrice class calculates the percentage of unit price
	 * from one interval to another interval, which is the division of difference of unit price between two 
	 * intervals by the previous interval to get the result
	 * 
	 * @param userSelection: the user's selection in UI
	 * @return return a 2nd dimensional double array contains the change percentage of unit price between intervals
	 * */
	@Override
	public double[][] perform(userSelection us) {
		// get the unit price analysis and unit price data list by interval
		getAnalysis analysis = new getAnalysis();
		us.setMetric("Price");
		Analysis unitPriceAnalysis = analysis.createAnalysis(us);
		double[][] unitPriceDataList = unitPriceAnalysis.perform(us);
		
		us.setMetric("Change of unit price");
		double[][] unitPriceChange = new double[unitPriceDataList.length][];
		for(int currencyIndex=0; currencyIndex<unitPriceDataList.length; currencyIndex++) {
			if(unitPriceDataList[currencyIndex].length <= 1) {
				unitPriceChange[currencyIndex] = new double[1];
				unitPriceChange[currencyIndex][0] = 0;
			} else {
				unitPriceChange[currencyIndex] = new double[unitPriceDataList[currencyIndex].length - 1];
				for(int dataIndex=0; dataIndex<unitPriceDataList[currencyIndex].length - 1; dataIndex++) {
					unitPriceChange[currencyIndex][dataIndex] = (unitPriceDataList[currencyIndex][dataIndex+1] - unitPriceDataList[currencyIndex][dataIndex]) / unitPriceDataList[currencyIndex][dataIndex];
				}
			}
		}
		return unitPriceChange;
	}
}

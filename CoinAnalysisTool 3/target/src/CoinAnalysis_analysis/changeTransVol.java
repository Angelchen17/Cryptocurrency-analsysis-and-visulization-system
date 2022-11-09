package CoinAnalysis_analysis;

import CoinAnalysis_userSelection.userSelection;

/*
 * Description: the changeTransVol class implements the abstract class Analysis, and mainly
 * implements the perform function to calculate the percentage change of transaction volume
 * from one interval selected to another
 * 
 * @param None
 * @return None
 * */
public class changeTransVol implements Analysis{
	/*
	 * Description: The function perform of changeTransVol class calculates the percentage of transaction volume
	 * from one interval to another interval, which is the division of difference of transaction volume between two 
	 * intervals by the previous interval to get the result
	 * 
	 * @param userSelection: the user's selection in UI
	 * @return return a 2nd dimensional double array contains the change percentage of transaction volume between intervals
	 * */
	@Override
	public double[][] perform(userSelection us) {
		// get the market capitalization analysis and unit market capitalization list by interval
		getAnalysis analysis = new getAnalysis();
		us.setMetric("Volume");
		Analysis transVolAnalysis = analysis.createAnalysis(us);
		double[][] transVolDataList = transVolAnalysis.perform(us);
		
		us.setMetric("Change of volume");
		double[][] transVolChange = new double[transVolDataList.length][];
		for(int currencyIndex=0; currencyIndex<transVolDataList.length; currencyIndex++) {
			if(transVolDataList[currencyIndex].length <= 1) {
				transVolChange[currencyIndex] = new double[1];
				transVolChange[currencyIndex][0] = 0;
			} else {
				transVolChange[currencyIndex] = new double[transVolDataList[currencyIndex].length - 1];
				for(int dataIndex=0; dataIndex<transVolDataList[currencyIndex].length - 1; dataIndex++) {
					transVolChange[currencyIndex][dataIndex] = (transVolDataList[currencyIndex][dataIndex+1] - transVolDataList[currencyIndex][dataIndex]) / transVolDataList[currencyIndex][dataIndex];
				}
			}
		}
		return transVolChange;
	}
}

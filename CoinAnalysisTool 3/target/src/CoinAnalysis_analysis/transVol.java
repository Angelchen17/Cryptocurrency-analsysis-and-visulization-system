package CoinAnalysis_analysis;

import CoinAnalysis_reader.IdataFetcher;
import CoinAnalysis_reader.dataFetcher;
import CoinAnalysis_userSelection.userSelection;

/*
 * Description: the transVol class implements the abstract class Analysis, and mainly
 * implements the perform function to calculate the transaction volume since the selected 
 * start date and for the frequency interval selected 
 * 
 * @param None
 * @return None
 * */
public class transVol implements Analysis{
	/*
	 * Description: The function perform of transVol class calculates the transaction volume, 
	 * which is the total transaction volume value of one interval
	 * 
	 * @param userSelection: the user's selection in UI
	 * @return return a 2nd dimensional double array contains the transaction volume of each interval
	 * */	
	@Override
	public double[][] perform(userSelection us) {
		// TODO Auto-generated method stub
		String interval = us.getInterval();
		IdataFetcher fetcher = new dataFetcher();
		double[][] dataList = fetcher.getVolumeForCoin(us);
		
		if(interval == "Daily") {
			return dataList;
		} else {
			int intervalIndex = 0;			// current week, month or year's index
			int intervalLength = 0;			// the data length of one week, month or year 
			double totalCoinVolume = 0.0;	// the total coin volume for one week, month or year

			if (interval == "Weekly") {
				intervalLength = 7;			// one week is 7 days
			}else if (interval == "Monthly") {
				intervalLength = 30;		// assume one month is 30 days
			}else if (interval == "Yearly") {
				intervalLength = 365;		// assume one year is 365 days
			}
			
			double[][] newDataList = new double[dataList.length][];
			for(int currencyIndex=0; currencyIndex<dataList.length; currencyIndex++) {
				newDataList[currencyIndex] = new double[(int) Math.ceil(Double.valueOf(dataList[currencyIndex].length)/intervalLength)];
				intervalIndex = newDataList[currencyIndex].length - 1;
				
				for(int dataIndex=dataList[currencyIndex].length-1; dataIndex>=0; dataIndex--) {		
					totalCoinVolume += dataList[currencyIndex][dataIndex];
					if(dataIndex != 0 && dataIndex % intervalLength == 0) {
						// calculate the coin volume for a whole week, month or year
						newDataList[currencyIndex][intervalIndex] = totalCoinVolume;
						intervalIndex -= 1;
						totalCoinVolume = 0.0;
					} else if(dataIndex == 0){
						// if the final year is not a whole week, month or year, we also calculate the coin volume
						newDataList[currencyIndex][intervalIndex] = totalCoinVolume;
						intervalIndex -= 1;
						totalCoinVolume = 0.0;
					}
				}
			}
			return newDataList;
		}
	}
}

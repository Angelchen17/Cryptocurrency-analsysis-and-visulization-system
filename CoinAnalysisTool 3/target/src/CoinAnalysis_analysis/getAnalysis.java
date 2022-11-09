package CoinAnalysis_analysis;

import CoinAnalysis_userSelection.userSelection;

public class getAnalysis {

	/**
	 * get anaylysis type from user selction
	 * @param us user selection
	 * @return the type of anaylsis
	 */
	public Analysis createAnalysis(userSelection us) {
		if (us.getMetric() == null) {
			return null;
		}
		switch (us.getMetric()) {
		case "Price": {
			return new unitPrice();
		}
		case "MarketCap":{
			return new marketCap();
		}
		case "Volume": {
			return new transVol();
		}
		case "Coins in Circulation": {
			return new coinCircul();
		}
		case "Change of unit price":{
			return new changeUnitPrice();
		}
		case "Change of market cap":{
			return new changeMarketCap();
		}
		case "Change of volume":{
			return new changeTransVol();
		}
		case "Change of coins in circulation":{
			return new changeCoinCircul();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + us.getMetric());
		}
	}
	
		 
}

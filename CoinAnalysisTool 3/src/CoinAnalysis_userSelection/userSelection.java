package CoinAnalysis_userSelection;

public class userSelection implements IuserSelection{
	private String[] currency;
	private String date;
	private String metric;
	private String interval;
	
	
	public userSelection(String[] currency, String date, String metric, String interval) {
		this.currency = currency;
		this.date = date;
		this.metric = metric;
		this.interval = interval;
	}


	@Override
	public String[] getCurrency() {
		// TODO Auto-generated method stub
		String[] result = new String[this.currency.length-1];
		for (int i=0; i<this.currency.length-1; i++)
			result[i] = this.currency[i];

		return result;
	}



	@Override
	public String getdate() {
		// TODO Auto-generated method stub
		return this.date;
	}



	@Override
	public String getMetric() {
		// TODO Auto-generated method stub
		return this.metric;
	}



	@Override
	public String getInterval() {
		// TODO Auto-generated method stub
		return this.interval;
	}



	@Override
	public void setCurrency(String[] currency) {
		// TODO Auto-generated method stub
		this.currency=currency;
	}



	@Override
	public void setdate(String date) {
		// TODO Auto-generated method stub
		this.date=date;
	}



	@Override
	public void setMetric(String metric) {
		// TODO Auto-generated method stub
		this.metric=metric;
	}


	@Override
	public void setInterval(String interval) {
		// TODO Auto-generated method stub
		this.interval=interval;
	}

}

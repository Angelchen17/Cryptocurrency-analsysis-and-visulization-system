package CoinAnalysis_userSelection;

public interface IuserSelection {
	public IuserSelection us=null;
	public String[] cryptocurrency=null;
	public String date="";
	public String metric="";
	public String interval="";
	public String type=null;
	
	public String[] getCurrency();
	
	public String getdate();
	
	public String getMetric();
	
	public String getInterval();
	
	public void setCurrency(String[] currency);
	
	public void setdate(String date);
	
	public void setMetric(String metric);
	
	public void setInterval(String interval);

	
}

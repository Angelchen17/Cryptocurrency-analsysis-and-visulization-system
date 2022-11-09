package CoinAnalysis_reader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import CoinAnalysis_result.resultSubject;
import CoinAnalysis_userSelection.*;

public class dataFetcher implements IdataFetcher{
	IdataFetcher fetcher;
	
	public String baseUrl;
	
	@Override
	public IdataFetcher DataFetcher() {
		// TODO Auto-generated method stub
		return fetcher;
	}

	/**
	 * Constructor
	 * set base api url link
	 */
	public dataFetcher() {
		baseUrl="https://api.coingecko.com/api/v3/coins/";
	}

	/**
	 * get difference between selected date and current date
	 * @param us user selection to get start date
	 * @return the number of days in between
	 */
	public static int get_date_gap(userSelection us) {
		  String selected_date = us.getdate();
		  
		  Calendar calendar = Calendar.getInstance();
		  SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		  calendar.add(Calendar.DATE, 0);
		  String ago = sdf1.format(calendar.getTime());
		  int gap = 1;

		  while (!ago.equals(selected_date)) {
		   Calendar calenda = Calendar.getInstance();
		   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		   calenda.add(Calendar.DATE, -1*gap);
		   ago = sdf.format(calenda.getTime());
		   gap += 1;
		  }
		  
		  return gap-1;
		 }
	
	//get data for one type of coin from API

	/**
	 * get one cryptocurrency data from api
	 * @param us user selection
	 * @param count a count parameter of numbe of cryptocurrency in the list
	 * @return a JsonObject from api
	 */
	public JsonObject getDataForCrypto(userSelection us,int count) {
		String date = String.valueOf(get_date_gap(us));
		String coin = us.getCurrency()[count].toLowerCase();
	
		String urlString = baseUrl+coin+"/market_chart?vs_currency=cad&days="+date+"&interval=daily";
		
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				@SuppressWarnings("deprecation")
				JsonObject jsonObject = new JsonParser().parse(inline).getAsJsonObject();
				return jsonObject;
			}

		} catch (IOException e) {
			System.out.println("Something went wrong with the API call.");
		}
		return null;
	}

	/**
	 * get an array of jsonObject from api
	 * @param us user selection
	 * @return an arraylist of jsonObject of data
	 */
	public ArrayList<JsonObject> getAllData(userSelection us) {
		int count =0;
		ArrayList<JsonObject> jsonData = new ArrayList<>();
		while (count < us.getCurrency().length) {
			jsonData.add(getDataForCrypto(us, count));
			count++;
		}
		return jsonData;
	}

	/**
	 * Get coin price from api
	 * @param us user selection
	 * @return a two way array of more than one cryptocurrency and the price for multiple days
	 */
	public double[][] getPriceForCoin(userSelection us) {
		double[][] price = null;
		int numofcoins = us.getCurrency().length;
		
		ArrayList<JsonObject> data = getAllData(us);
		
		JsonArray jsonPrice = data.get(0).get("prices").getAsJsonArray();
		int numofdays = jsonPrice.size();
		price = new double[numofcoins][numofdays];
		
		for (int i = 0; i < numofcoins; i++) {
			for (int j = 0; j < numofdays; j++) {
				price[i][j] = Math.round(data.get(i).get("prices").getAsJsonArray().get(j).getAsJsonArray().get(1).getAsDouble()*100d)/100d;
			}
		}
		return price;
	}

	/**
	 * get market capital from api
	 * @param us user selection
	 * @return a two way array of more than one cryptocurrency and the market capital for multiple days
	 */
	public double[][] getMarketCapForCoin(userSelection us) {
		double[][] market = null;
		int numofcoins = us.getCurrency().length;
		
		ArrayList<JsonObject> data = getAllData(us);
		
		JsonArray jsonMarket = data.get(0).get("market_caps").getAsJsonArray();
		int numofdays = jsonMarket.size();
		market = new double[numofcoins][numofdays];
		
		for (int i = 0; i < numofcoins; i++) {
			for (int j = 0; j < numofdays; j++) {
				market[i][j] = market[i][j] = Math.round(data.get(i).get("market_caps").getAsJsonArray().get(j).getAsJsonArray().get(1).getAsDouble()*100d)/100d;
			}
		}
		return market;
}

	/**
	 * get total volumes from api
	 * @param us user selection
	 * @return a two way array of more than one cryptocurrency and the total volumes for multiple days
	 */
	public double[][] getVolumeForCoin(userSelection us) {
		double[][] volume = null;
		int numofcoins = us.getCurrency().length;
		
		ArrayList<JsonObject> data = getAllData(us);
		
		JsonArray jsonVolume = data.get(0).get("total_volumes").getAsJsonArray();
		int numofdays = jsonVolume.size();
		volume = new double[numofcoins][numofdays];
		
		for (int i = 0; i < numofcoins; i++) {
			for (int j = 0; j < numofdays; j++) {
				volume[i][j] = volume[i][j] = Math.round(data.get(i).get("total_volumes").getAsJsonArray().get(j).getAsJsonArray().get(1).getAsDouble()*100d)/100d;
			}
		}
		return volume;
	}
}

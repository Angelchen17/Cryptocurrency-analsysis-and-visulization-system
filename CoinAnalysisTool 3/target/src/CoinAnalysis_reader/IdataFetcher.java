package CoinAnalysis_reader;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import CoinAnalysis_userSelection.*;

public interface IdataFetcher {

	public IdataFetcher fetcher=null;
	
	public IdataFetcher DataFetcher();
	
	public JsonObject getDataForCrypto(userSelection us,int count);
	
	public ArrayList<JsonObject> getAllData(userSelection us);
	
	public double[][] getPriceForCoin(userSelection us);

	public double[][] getMarketCapForCoin(userSelection us);

	public double[][] getVolumeForCoin(userSelection us);
	
}


package lk.zmessenger.consumerwatchconsummer.jsontodomain;

import java.util.List;

import lk.zmessenger.consumerwatchconsummer.domain.PriceSummery;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MarketSummeryData {

	public List<PriceSummery> getMaketSummerytData(String jsonString) {
		// System.out.println(jsonString);
		Gson gson = new Gson();
		// List<Markets> user = (List<Markets>) gson.fromJson(jsonString,
		// Markets.class);
		List<PriceSummery> products = gson.fromJson(jsonString,
				new TypeToken<List<PriceSummery>>() {
				}.getType());
		return products;
	}
}

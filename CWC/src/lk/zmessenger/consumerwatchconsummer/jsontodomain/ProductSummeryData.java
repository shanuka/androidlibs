package lk.zmessenger.consumerwatchconsummer.jsontodomain;

import java.util.List;

import lk.zmessenger.consumerwatchconsummer.domain.ProductDetailsDto;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ProductSummeryData {

	public List<ProductDetailsDto> getProducSummerytData(String jsonString) {
		//System.out.println(jsonString);
		Gson gson = new Gson();
		// List<Markets> user = (List<Markets>) gson.fromJson(jsonString,
		// Markets.class);
		List<ProductDetailsDto> products = gson.fromJson(jsonString,
				new TypeToken<List<ProductDetailsDto>>() {
				}.getType());
		return products;
	}
}

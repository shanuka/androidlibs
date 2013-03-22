package lk.zmessenger.consumerwatchconsummer.jsontodomain;

import java.util.List;

import lk.zmessenger.consumerwatchconsummer.domain.Products;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ProductData {

	public List<Products> getProductData(String jsonString) {
		//System.out.println(jsonString);
		Gson gson = new Gson();
		// List<Markets> user = (List<Markets>) gson.fromJson(jsonString,
		// Markets.class);
		List<Products> products = gson.fromJson(jsonString,
				new TypeToken<List<Products>>() {
				}.getType());
		return products;
	}
}

package lk.zmessenger.consumerwatchconsummer.jsontodomain;

import java.util.List;

import lk.zmessenger.consumerwatchconsummer.domain.Categories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CategoryData {

	public List<Categories> getCategoryData(String jsonString) {
		System.out.println(jsonString);
		Gson gson = new Gson();
		// List<Markets> user = (List<Markets>) gson.fromJson(jsonString,
		// Markets.class);
		List<Categories> categories = gson.fromJson(jsonString,
				new TypeToken<List<Categories>>() {
				}.getType());
		
		return categories;
	}
}

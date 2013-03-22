package lk.zmessenger.consumerwatchconsummer.jsontodomain;

import java.util.List;

import lk.zmessenger.consumerwatchconsummer.domain.ComplainCategory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ComplainCategoryData {

	public List<ComplainCategory> getCategoryData(String jsonString) {
		System.out.println(jsonString);
		Gson gson = new Gson();
		// List<Markets> user = (List<Markets>) gson.fromJson(jsonString,
		// Markets.class);
		List<ComplainCategory> categories = gson.fromJson(jsonString,
				new TypeToken<List<ComplainCategory>>() {
				}.getType());
		
		return categories;
	}
}

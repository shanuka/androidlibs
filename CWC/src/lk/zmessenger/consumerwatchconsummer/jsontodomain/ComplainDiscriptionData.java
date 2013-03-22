package lk.zmessenger.consumerwatchconsummer.jsontodomain;

import java.util.List;

import lk.zmessenger.consumerwatchconsummer.domain.CompDescription;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ComplainDiscriptionData {

	public List<CompDescription> getCompDescriptionData(String jsonString) {
		System.out.println(jsonString);
		Gson gson = new Gson();
		// List<Markets> user = (List<Markets>) gson.fromJson(jsonString,
		// Markets.class);
		List<CompDescription> categories = gson.fromJson(jsonString,
				new TypeToken<List<CompDescription>>() {
				}.getType());

		return categories;
	}
}

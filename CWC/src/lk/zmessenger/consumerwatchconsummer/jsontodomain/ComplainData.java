package lk.zmessenger.consumerwatchconsummer.jsontodomain;

import java.util.List;

import lk.zmessenger.consumerwatchconsummer.domain.CompComplain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ComplainData {

	public List<CompComplain> getCompComplainData(String jsonString) {
		System.out.println(jsonString);
		Gson gson = new Gson();
		// List<Markets> user = (List<Markets>) gson.fromJson(jsonString,
		// Markets.class);
		List<CompComplain> categories = gson.fromJson(jsonString,
				new TypeToken<List<CompComplain>>() {
				}.getType());
		
		return categories;
	}
}

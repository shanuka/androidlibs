package lk.zmessenger.consumerwatchconsummer.jsontodomain;

import lk.zmessenger.consumerwatchconsummer.domain.Users;

import com.google.gson.Gson;

public class UserData {

	public Users getUserData(String jsonString) {
		//System.out.println(jsonString);
		Gson gson = new Gson();
		Users user = (Users) gson.fromJson(jsonString, Users.class);
		return user;
	}
}

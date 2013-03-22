package lk.zmessenger.consumerwatchconsummer.session;

import java.util.HashMap;

import lk.zmessenger.consumerwatchconsummer.ComplainRegisterActivity;
import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;
import lk.zmessenger.consumerwatchconsummer.domain.Users;
import lk.zmessenger.consumerwatchconsummer.jsontodomain.UserData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SessionManager {

	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	private Users userdata;

	// Sharedpref file name
	private static final String PREF_NAME = "AndroidPref";

	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";

	// User name (make variable public to access from outside)
	public static final String KEY_USER_ID = "userid";

	// User name (make variable public to access from outside)
	public static final String KEY_USER_STRING = "userstring";

	// Email address (make variable public to access from outside)
	public static final String KEY_SESSION_ID = "sessionid";

	// Constructor
	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	/**
	 * Create login session
	 * */
	public void createLoginSession(String userString) {
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);

		editor.putString(KEY_USER_STRING, userString);

		UserData ud = new UserData();
		userdata = ud.getUserData(userString);
		// System.out.println(userdata.getUserName());

		// Storing name in pref
		editor.putString(KEY_USER_ID, userdata.getCompUserId().toString());

		// Storing name in pref
		editor.putString(Extra.KEY_USER_TYPE, userdata.getMobileNumber());

		// Storing email in pref
		// editor.putString(KEY_SESSION_ID, userdata.getSessionId());

		// commit changes
		editor.commit();
	}

	/**
	 * Check login method wil check user login status If false it will redirect
	 * user to login page Else won't do anything
	 * */
	public void checkLogin() {
		// Check login status
		if (!this.isLoggedIn()) {
			// // user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, ComplainRegisterActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			_context.startActivity(i);
		}

	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));

		// user email id
		user.put(KEY_SESSION_ID, pref.getString(KEY_SESSION_ID, null));

		// return user
		return user;
	}

	/**
	 * Get stored session data
	 * */
	public Users getUser() {
		// System.err.println("getUser");
		UserData ud = new UserData();
		Users user = ud.getUserData(pref.getString(KEY_USER_STRING, null));

		return user;
	}

	// /**
	// * Clear session details
	// * */
	public void logoutUser() {
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();

		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, ComplainRegisterActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// Staring Login Activity
		_context.startActivity(i);

	}

	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn() {
		return pref.getBoolean(IS_LOGIN, false);
	}

}

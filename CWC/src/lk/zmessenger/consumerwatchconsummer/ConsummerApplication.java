package lk.zmessenger.consumerwatchconsummer;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

public class ConsummerApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		ActiveAndroid.initialize(this);
	}
}

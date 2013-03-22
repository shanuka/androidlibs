package lk.zmessenger.consumerwatchconsummer;

import lk.zmessenger.consumerwatchconsummer.adapters.Constants.Extra;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {
	
	

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(Extra.DISPLAY_MESSAGE_ACTION);
        intent.putExtra(Extra.EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}

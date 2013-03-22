package lk.zmessenger.consumerwatchconsummer.adapters;

/**
 * @author Shanuka Gayashan
 */
public final class Constants {

	private Constants() {
	}

	public static class Extra {
		// public static final String URL = "http://123.231.16.61:8080/caa/zm/";
		// public static final String IMAGE_URL =
		// "http://123.231.16.61:8080/caa/images.jar/";
		// public static final String URL = "http://192.168.2.2:8080/caa/zm/";
		// public static final String IMAGE_URL =
		// "http://192.168.2.2:8080/caa/images.jar/";

		// public static final String URL = "http://192.168.2.131:8080/caa/zm/";
		// public static final String IMAGE_URL =
		// "http://192.168.2.131:8080/caa/images.jar/";
		public static final String URL = "http://220.247.223.53:8080/caa/zm/";
		public static final String IMAGE_URL = "http://220.247.223.53:8080/caa/images.jar/";

		public static final String CONTENT = "com.shanuka.mila.CONTENT";
		public static final String KEY_SONG = "song"; // parent node
		public static final String KEY_ID = "id";
		public static final String KEY_TITLE = "title";
		public static final String KEY_ARTIST = "artist";
		public static final String KEY_DURATION = "duration";
		public static final String KEY_THUMB_URL = "thumb_url";
		public static final String KEY_MARKET_ID = "marketid";
		public static final String KEY_PRODUCT_ID = "productid";
		public static final String KEY_CATEGORY_ID = "categoryid";
		public static final String KEY_SHOP_ID = "shopid";
		public static final String KEY_SHOP_ADDRESS = "shopaddress";
		public static final String KEY_SHOP_NUMBER = "shopnumber";
		public static final String KEY_USER_TYPE = "usertype";
		public static final Integer USER_CONTENT = 1;
		public static final Integer USER_APPROVER = 2;

		public static final String SUMMERY_ITEM = "item";
		public static final String SUMMERY_MIN = "min";
		public static final String SUMMERY_MAX = "max";
		public static final String SUMMERY_AVG = "avg";
		public static final String SUMMERY_ISCHECK = null;
		public static final String SUMMERY_ID = "id";
		public static final String SUMMERY_PRICE = "price";
		public static final String KEY_START_TIME = "starttime";
		public static final String KEY_OLD_TIME = "oldtime";
		public static final String SUMMERY_PRICE_ID = "priceid";
		public static final String APPRIVER_SUMMERY = "extra";
		public static final String MESSAGE_TYPE = "message_type";
		public static final String SERVICE_TYPE = "service_type";
		public static final String SEND_MESSAGE_MARKET = "market";
		public static final String SEND_MESSAGE_DATE = "date";
		public static final String SEND_MESSAGE_MESSAGEBODY = "msbody";
		public static final String SEND_MESSAGE_MESSAGETYPE = "type";
		public static final String KEY_MARKET_NAME = "maketname";
		public static final long DURATION = 2L;

		// give your server registration url here
		// public static final String SERVER_URL =
		// "http://220.247.223.54/gcm/register.php";
		// public static final String SERVER_UNREGISTER_URL =
		// "http://220.247.223.54/gcm/unregister.php";

		public static final String SERVER_URL = URL + "auth/register";
		public static final String SERVER_UNREGISTER_URL = URL
				+ "auth/unregister";

		// Google project id
		public static final String SENDER_ID = "581786172565";

		/**
		 * Tag used on log messages.
		 */
		public static final String TAG = "Consummer watch GCM";

		public static final String DISPLAY_MESSAGE_ACTION = "com.shanuka.consumerwatchconsummer.DISPLAY_MESSAGE";

		public static final String EXTRA_MESSAGE = "message";
		public static final String PARSEJSON = "parsejson";
		public static final String KEY_MARKET_LIST = "price";
		public static final String KEY_DATE = "date";
		public static final String MACKET_TYPE_RETAIL = "2";
		public static final String MACKET_TYPE_WHOLESALE = "1";
		public static final String PHONE_NUMBER = "1977";
		public static final String MESSAGE_BODY = "imei ";
		public static final String KEY_DESCRIPTION = "des";

	}
}

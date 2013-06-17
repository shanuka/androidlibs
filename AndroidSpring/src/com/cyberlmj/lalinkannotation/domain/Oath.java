package com.cyberlmj.lalinkannotation.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;



/**
 * shanuka gayashan @Cyberlmj 9:43:17 PM Email: shanuka.gayashan@cyberlmj.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Oath {
	@JsonProperty("access_token")
	String access_token;
	@JsonProperty("expires_in")
	String expires_in;
	@JsonProperty("error")
	String error;
	@JsonProperty("error_message")
	String error_message;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

}

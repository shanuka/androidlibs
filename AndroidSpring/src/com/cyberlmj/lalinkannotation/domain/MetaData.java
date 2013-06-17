package com.cyberlmj.lalinkannotation.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * shanuka gayashan @Cyberlmj 7:24:16 PM Email: shanuka.gayashan@cyberlmj.com
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaData {
	@JsonProperty("message")
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

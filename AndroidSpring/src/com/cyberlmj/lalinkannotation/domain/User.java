package com.cyberlmj.lalinkannotation.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * shanuka gayashan @Cyberlmj 7:23:54 PM Email: shanuka.gayashan@cyberlmj.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends MetaData {
	@JsonProperty("message_text")
	Oath oath;

	public Oath getOath() {
		return oath;
	}

	public void setOath(Oath oath) {
		this.oath = oath;
	}

}

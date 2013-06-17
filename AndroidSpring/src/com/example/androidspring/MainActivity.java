package com.example.androidspring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.cyberlmj.lalinkannotation.domain.User;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String url = "http://10.10.2.23/user.php";
		
		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the String message converter
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(new MappingJacksonHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		
		restTemplate.setMessageConverters(messageConverters);

		// Make the HTTP GET request, marshaling the response to a String
		String result = restTemplate.getForObject(url, String.class, "");
		
		System.out.println(result);
		
		restTemplate.setRequestFactory(new CommonsClientHttpRequestFactory());
		User aut = restTemplate.getForObject(url, User.class);
		
		System.out.println(aut.getMessage());
		System.out.println(aut.getOath().getAccess_token());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

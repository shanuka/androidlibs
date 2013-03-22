package com.example.activeandroidtest;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import com.activeandroid.query.Select;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Category restaurants = new Category();
		restaurants.name = "cat one";
		restaurants.save();
		Item item = new Item();

		item.category = restaurants;
		item.name = "tem 1";
		item.save();

		item = new Item();
		item.category = restaurants;
		item.name = "item 2";
		item.save();

		item = new Item();
		item.category = restaurants;
		item.name = "tem 3";
		item.save();

		// Category.load(Category.class, 1);
		// Category restaurant = Category.load(Category.class, 2);
		// System.out.println(restaurant.name);
		//
		name(getApplicationContext());
	}

	public static void name(Context context) {
		List<Category> listCat3 = getAllCategory(context);
		for (Category item2 : listCat3) {
			System.out.println(item2.name);
			List<Item> listCat = getAll(context, item2);
			for (Item itemitem : listCat) {
				System.out.println("\t       " + itemitem.name);
			}
		}
	}

	public static List<Item> getAll(Context context, Category category) {
		return new Select().from(Item.class)
				.where("Category = ?", category.getId()).orderBy("Name ASC")
				.execute();
	}

	public static List<Category> getAllCategory(Context context) {
		return new Select().from(Category.class).orderBy("Name ASC").execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

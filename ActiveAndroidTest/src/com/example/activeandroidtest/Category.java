package com.example.activeandroidtest;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Categories")
public class Category extends Model {
	@Column(name = "Name")
	public String name;

	public List<Item> items() {
		return getMany(Item.class, "Category");
	}
}

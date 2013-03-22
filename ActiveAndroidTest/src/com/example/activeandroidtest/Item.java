package com.example.activeandroidtest;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Items")
public class Item extends Model {
	@Column(name = "Name")
	public String name;

	@Column(name = "Category")
	public Category category;

	public Item() {
		super();
	}

	public Item(String name, Category category) {
		super();
		this.name = name;
		this.category = category;
	}
}

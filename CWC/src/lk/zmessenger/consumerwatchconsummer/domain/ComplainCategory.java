package lk.zmessenger.consumerwatchconsummer.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComplainCategory {
	@Expose
	@SerializedName("comp_category_id")
	Integer compCategoryId;
	@Expose
	@SerializedName("category_name")
	String categoryName;

	public Integer getCompCategoryId() {
		return compCategoryId;
	}

	public void setCompCategoryId(Integer compCategoryId) {
		this.compCategoryId = compCategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}

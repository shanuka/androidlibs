package lk.zmessenger.consumerwatchconsummer.domain;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetailsDto {
	
	@Expose
	@SerializedName("product")
	private Products product;
	@Expose
	@SerializedName("summary_details")
	private List<PriceSummery> summaryDetails;

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public List<PriceSummery> getSummaryDetails() {
		return summaryDetails;
	}

	public void setSummaryDetails(List<PriceSummery> summaryDetails) {
		this.summaryDetails = summaryDetails;
	}
}

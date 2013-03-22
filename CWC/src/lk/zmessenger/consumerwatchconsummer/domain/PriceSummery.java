package lk.zmessenger.consumerwatchconsummer.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "PriceSummery")
public class PriceSummery extends Model {
	@Column(name = "average")
	@Expose
	@SerializedName("average")
	private String average;
	@Column(name = "market_name")
	@Expose
	@SerializedName("market_name")
	private String marketName;
	@Column(name = "last_updated")
	@Expose
	@SerializedName("last_updated")
	private String lastUpdated;

	public String getLastUpdated() {

//		Timestamp datest = new Timestamp(Long.valueOf(lastUpdated));
//
//		Date datetest = new Date(datest.getTime());
//		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datetest);
		return lastUpdated;
	}

	public List<Products> items() {
		return getMany(Products.class, "PriceSummery");
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}

	public String getMarket_name() {
		return marketName;
	}

	public void setMarket_name(String market_name) {
		this.marketName = market_name;
	}

}

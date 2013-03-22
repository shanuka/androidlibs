package lk.zmessenger.consumerwatchconsummer.domain;

// Generated Feb 20, 2013 4:50:37 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * CompComplain generated by hbm2java
 */
public class CompComplain {

	@Expose
	@SerializedName("comp_complain_id")
	private Integer compComplainId;
	@Expose
	@SerializedName("comp_category")
	private ComplainCategory compCategory;
	// private CompUser compUser;

	@Expose
	@SerializedName("complain_title")
	private String complainTitle;
	private Integer status;
	private Byte deleted;
	private Date addedDate;
	private Date editedDate;

	// private List compDescriptions = new ArrayList(0);

	public Integer getCompComplainId() {
		return this.compComplainId;
	}

	public void setCompComplainId(Integer compComplainId) {
		this.compComplainId = compComplainId;
	}

	public ComplainCategory getCompCategory() {
		return compCategory;
	}

	public void setCompCategory(ComplainCategory compCategory) {
		this.compCategory = compCategory;
	}

	public String getComplainTitle() {
		return this.complainTitle;
	}

	public void setComplainTitle(String complainTitle) {
		this.complainTitle = complainTitle;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Byte getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public Date getAddedDate() {
		return this.addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public Date getEditedDate() {
		return this.editedDate;
	}

	public void setEditedDate(Date editedDate) {
		this.editedDate = editedDate;
	}

	// public List getCompDescriptions() {
	// return compDescriptions;
	// }
	//
	// public void setCompDescriptions(List compDescriptions) {
	// this.compDescriptions = compDescriptions;
	// }

}
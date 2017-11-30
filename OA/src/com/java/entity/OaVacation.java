package com.java.entity;

public class OaVacation {
	private String id;
	private String vacationType;
	private String vacationStartDate;
	private String vacationEndDate;
	private String vacationRemark;
	
	public OaVacation() {
		super();
	}

	public OaVacation(String id, String vacationType, String vacationStartDate,
			String vacationEndDate, String vacationRemark) {
		super();
		this.id = id;
		this.vacationType = vacationType;
		this.vacationStartDate = vacationStartDate;
		this.vacationEndDate = vacationEndDate;
		this.vacationRemark = vacationRemark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVacationType() {
		return vacationType;
	}

	public void setVacationType(String vacationType) {
		this.vacationType = vacationType;
	}

	public String getVacationStartDate() {
		return vacationStartDate;
	}

	public void setVacationStartDate(String vacationStartDate) {
		this.vacationStartDate = vacationStartDate;
	}

	public String getVacationEndDate() {
		return vacationEndDate;
	}

	public void setVacationEndDate(String vacationEndDate) {
		this.vacationEndDate = vacationEndDate;
	}

	public String getVacationRemark() {
		return vacationRemark;
	}

	public void setVacationRemark(String vacationRemark) {
		this.vacationRemark = vacationRemark;
	}
	
}

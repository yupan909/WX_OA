package com.java.entity;

public class OaReport {
	private String id;
	private String reportType;
	private String reportTitle;
	private String reportSum;
	private String reportPlan;
	private String reportRealize;
	
	public OaReport() {
		super();
	}
	
	public OaReport(String id, String reportType, String reportTitle,
			String reportSum, String reportPlan, String reportRealize) {
		super();
		this.id = id;
		this.reportType = reportType;
		this.reportTitle = reportTitle;
		this.reportSum = reportSum;
		this.reportPlan = reportPlan;
		this.reportRealize = reportRealize;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public String getReportSum() {
		return reportSum;
	}

	public void setReportSum(String reportSum) {
		this.reportSum = reportSum;
	}

	public String getReportPlan() {
		return reportPlan;
	}

	public void setReportPlan(String reportPlan) {
		this.reportPlan = reportPlan;
	}

	public String getReportRealize() {
		return reportRealize;
	}

	public void setReportRealize(String reportRealize) {
		this.reportRealize = reportRealize;
	}
	
}

package com.concordia.soen.soen6591_group3;

import java.util.ArrayList;
import java.util.List;

public class AntiPatternModel {
	
	private String antiPatternType = "";
	private List<String> lineNumber = new ArrayList<String>();
	private String location = "";
	private int totalFound = 0;
	
	public void setLineNumber(String lineNumber) {
		this.lineNumber.add(lineNumber);
	}
	
	public String getAntiPatternType() {
		return antiPatternType;
	}
	public void setAntiPatternType(String antiPatternType) {
		this.antiPatternType = antiPatternType;
	}
	public String getLineNumber() {
		return String.join(",", this.lineNumber);
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getTotalFound() {
		return totalFound;
	}
	public void setTotalFound(int totalFound) {
		this.totalFound = totalFound;
	}
	
	public void print() {
		System.out.println("Anti Pattern Type: " + antiPatternType);
		System.out.println("Line Number: " + lineNumber);
		System.out.println("Location: " + location);
		System.out.println("Total Found: " + totalFound);
	}
	
}

package com.concordia.soen.soen6591_group3;

import java.util.ArrayList;
import java.util.List;

public abstract class AntiPattern {
	
	public void printAntiPatterns(List<AntiPatternModel> antiPatternList) {
		if(antiPatternList.size() > 0) {
			for(AntiPatternModel model : antiPatternList) {
				model.print();
				System.out.println();
			}
		}else {
			System.out.println("List is empty");
		}
		
	}
}

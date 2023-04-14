package com.concordia.soen.soen6591_group3;

public class NestedTry {
	public static void nestTry() {
		try {
			try {
				int x = 10/5;
			}
			catch(ArithmeticException e) {
				
			}
		}catch(Exception e) {
			
		}
	}
	
	public static void nestTry2() {
		try {
			try {
				try {
					int x = 10/5;
				}catch(ArithmeticException e) {
					
				}
			}
			catch(Exception e) {
				
			}
		}catch(Exception e) {
			
		}
	}
}

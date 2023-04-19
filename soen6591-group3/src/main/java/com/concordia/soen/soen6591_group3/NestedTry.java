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
				if(true) {
					try {
						while(true) {
							try {
								
							}catch(Exception e) {
								
							}
						}
					}catch(ArithmeticException e) {
						
					}
				}
				
			}
			catch(Exception e) {
				
			}
		}catch(Exception e) {
			
		}
	}
}

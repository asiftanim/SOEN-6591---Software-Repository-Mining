package com.concordia.soen.soen6591_group3;

import java.io.IOException;

public class DestructiveWrapping {
	public void TestDestructiveWrapping() throws Exception {
		
		for(int j=0; j<10; j++) {
			throw new IOException("Something is wrong");
		}
		
		try {
			int x = 10/0;
			throw new Exception();
		}catch(Exception e) {
			while(true) {
				for(int i=0; i<10; i++) {
					if(true) {
						switch(10) {
							case 1:
								for(int j=0; j<10; j++) {
									throw new Exception("Something is wrong");
								}
							default:
								throw new NullPointerException("Something is wrong");
						}
					}
					
				}

			}
		}
	}
	
	public void TestDestructiveWrapping2() throws Exception {
		try {
			int x = 10/0;
		}catch(ArithmeticException e) {
			throw new ArithmeticException("Something is wrong");
		}catch(Exception e) {
			throw new NullPointerException("Null pointer exception");
		}
	}
}

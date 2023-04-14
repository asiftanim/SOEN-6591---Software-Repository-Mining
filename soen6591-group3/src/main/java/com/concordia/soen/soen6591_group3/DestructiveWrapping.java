package com.concordia.soen.soen6591_group3;

import java.io.IOException;

public class DestructiveWrapping {
	public void TestDestructiveWrapping() throws Exception {
		try {
			int x = 10/0;
			throw new Exception();
		}catch(Exception e) {
			throw new IOException("Something is wrong");
		}
	}
	
	public void TestDestructiveWrapping2() throws Exception {
		try {
			int x = 10/0;
			
		}catch(ArithmeticException e) {
			throw new IOException("Something is wrong");
		}catch(Exception e) {
			throw new NullPointerException("Null pointer exception");
		}
	}
}

package com.concordia.soen.soen6591_group3;

import java.util.EmptyStackException;

public class ThrowsKitchenSink {
	
	public static void KitchenSink() throws Exception, ArithmeticException, ArithmeticException, EmptyStackException  {
		int x = 10, y = 0;
		String name = null;
		
		if(x == 0) {
			throw new IllegalAccessException();
		}else {
			if(y > 0) {
				name = "";
			}
			try {
				int a = x/y;
			}catch(Exception ae) {
				throw new ArithmeticException();
			}
		}
		
		if(name == null) {
			throw new NullPointerException();
		}else {
			throw new EmptyStackException();
		}
		
	}
	
	public static void KitchenSink2() throws NullPointerException, Exception, ArithmeticException, ArithmeticException, EmptyStackException  {
		
		
	}
}

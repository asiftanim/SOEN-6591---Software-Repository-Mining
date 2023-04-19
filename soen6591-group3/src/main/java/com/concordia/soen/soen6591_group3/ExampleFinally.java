package com.concordia.soen.soen6591_group3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

public class ExampleFinally {
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
	
	public void test() {
		Function method = new Function() {
			@Override
			public Object apply(Object arg0) {
				return null;
			}
		};
	}
	
	public void test2() {
		try {
			// comment
			Files.lines(Paths.get(""));
		}catch (IOException e) {
			// comment in IOException
			System.out.println("IOException");
		}catch (Exception exp) {
			// TODO: handle exception
		}finally {
			for(int i=0; i<10; i++) {
				try {
					
				}catch(Exception e) {
					
				}finally {
					throw new IllegalArgumentException("finally throw");
				}
				
			}
		}
	}
	
	public void test3() {
		try {
			Files.lines(Paths.get(""));
		}catch (NumberFormatException e) {
			
		}catch (RuntimeException re) {
			// TODO: handle exception
			System.out.println("Runtime Ex");
		}catch (Exception exp) {
			// TODO: handle exception
			test2();
		}finally {
			if(true) {
				throw new NullPointerException("Null pointer exception");
			}
				
		}
	}
}

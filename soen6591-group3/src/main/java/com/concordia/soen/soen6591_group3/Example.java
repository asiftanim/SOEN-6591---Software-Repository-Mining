package com.concordia.soen.soen6591_group3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

public class Example {
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
			Files.lines(Paths.get(""));
		}catch (IOException e) {
			// TODO: handle exception
			try {
				throw new Exception("aasdf");
			} catch (Exception exp) {
				// TODO: handle exception
			}
		}
	}
}

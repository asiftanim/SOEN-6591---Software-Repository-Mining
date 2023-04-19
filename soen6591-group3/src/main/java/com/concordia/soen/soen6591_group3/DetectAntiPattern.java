package com.concordia.soen.soen6591_group3;

import java.util.List;
import java.util.Scanner;

public class DetectAntiPattern {
	
	public static void main(String[] args) throws InterruptedException {
//		String userDir = System.getProperty("user.dir");
		//Github URL - https://github.com/spring-projects/spring-boot
		String path = "/Users/asiftanim/Downloads/spring-framework-main";
		//String path = userDir + "/src/main/java/com/concordia/soen/soen6591_group3";
		 
		List<String> filePathList = FileUtil.getFilePath(path);
		
		while(true) {
			System.out.println();
			Scanner scan = new Scanner(System.in);
			System.out.println("1. DestructiveWrappingDetector");
			System.out.println("2. NestedTryDetector");
			System.out.println("3. ThrowsKitchenSinkDetector");
			System.out.println("4. ThrowWithinFinallyDetector");
			System.out.print("Choose option: ");
			
			switch(scan.nextLine()) {
				case "1":
					new DestructiveWrappingDetector(filePathList);
					System.out.println("Total DestructiveWrapping Anti-Pattern Found: " + DestructiveWrappingDetector.totalCount);
					break;
				case "2":
					new NestedTryDetector(filePathList);
					System.out.println("Total NestedTry Anti-Pattern Found: " + NestedTryDetector.totalCount);
					break;
				case "3":
					new ThrowsKitchenSinkDetector(filePathList);
					System.out.println("Total ThrowsKitchenSink Anti-Pattern Found: " + ThrowsKitchenSinkDetector.totalCount);
					break;
				case "4":
					new ThrowWithinFinallyDetector(filePathList);
					System.out.println("Total ThrowWithinFinally Anti-Pattern Found: " + ThrowWithinFinallyDetector.totalCount);
					break;
				default:
					System.out.println("Wrong Input");
					break;
			}
		}
	}

}




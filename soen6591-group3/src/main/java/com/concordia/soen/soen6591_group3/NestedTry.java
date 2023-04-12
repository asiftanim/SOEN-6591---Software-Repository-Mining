package com.concordia.soen.soen6591_group3;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NestedTry {
	public static void nestTry() {
		
		//Replace your nested antipattern code here
			String code = "try {\r\n"
					+ "            int a = Integer.parseInt(args[0]);\r\n"
					+ "            try {\r\n"
					+ "                int b = Integer.parseInt(args[1]);\r\n"
					+ "                int result = a / b;\r\n"
					+ "                System.out.println(\"Result: \" + result);\r\n"
					+ "            } catch (ArithmeticException ex) {\r\n"
					+ "                System.out.println(\"Cannot divide by zero.\");\r\n"
					+ "            }\r\n"
					+ "        } catch (NumberFormatException ex) {\r\n"
					+ "            System.out.println(\"Invalid argument: \" + ex.getMessage());\r\n"
					+ "        }"; 
			
//			Example with no nested try
			String goodCode = "try {\r\n"
					+ "                int b = Integer.parseInt(args[1]);\r\n"
					+ "                int result = a / b;\r\n"
					+ "                System.out.println(\"Result: \" + result);\r\n"
					+ "            } catch (ArithmeticException ex) {\r\n"
					+ "                System.out.println(\"Cannot divide by zero.\");\r\n"
					+ "            }"; 
	        
	        // Regular expression to match nested try blocks
	        String pattern = "try\\s*\\{[^\\}]*\\btry\\b[^\\}]*\\}";
	        
	        Pattern p = Pattern.compile(pattern);
	        Matcher m = p.matcher(code);
	        System.out.println("");
	        System.out.println("------Running Code with nested tries(Anti pattern)--------");
	        int count=0;
	        while (m.find()) {
	        	count++;
	        	System.out.println("Nested Try patterns detected "+count);
	        } 
	        if(count==0){
	        	System.out.println("No antipattern of nested try blocks detected.");
	        }
	        
	        System.out.println("");
	        
	        Matcher m1 = p.matcher(goodCode);
	        System.out.println("----------Running Code with no nested try------------");
	        if (m1.find()) {
	            System.out.println("Nested Try patterns detected ");
	      	} else {
	      		System.out.println("No antipattern of nested try blocks detected.");
	      	}
	        System.out.println("");
	        
	        System.out.println("Exiting the program with status code 0\n");
		
	}
}

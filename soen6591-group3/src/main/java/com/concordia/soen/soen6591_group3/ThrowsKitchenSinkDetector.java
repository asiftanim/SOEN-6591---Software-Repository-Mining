package com.concordia.soen.soen6591_group3;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TryStatement;

public class ThrowsKitchenSinkDetector {
	static ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
	static int totalCount = 0;
	static int count = 0;
	static List<AntiPatternModel> antiPatternList = new ArrayList<AntiPatternModel>();
	
	public ThrowsKitchenSinkDetector(List<String> fileList) {
		System.out.println("ThrowsKitchenSinkDetector Running...");
		for(String file : fileList) {
			DetectThrowsKitchenSink(file);
		}
		PrintAntiPatterns();
	}
	
	public static void DetectThrowsKitchenSink(String file) {
		count = 0;
		String source = "";
		final AntiPatternModel antiPatternModel = new AntiPatternModel();
		
		try {
			source = FileUtil.read(file);
		}catch(Exception ex) {
			System.out.println(ex);
		}

		parser.setSource(source.toCharArray());
		
		final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		compilationUnit.accept(new ASTVisitor() {
			@Override
			public boolean visit(MethodDeclaration node) {
	    		if(node.thrownExceptionTypes().size() >= 2) {
	    			count++;
	    			totalCount++;
		    		antiPatternModel.setAntiPatternType("Throws Kitchen Sink");
		    		antiPatternModel.setLineNumber(Integer.toString(compilationUnit.getLineNumber(node.getStartPosition())));
	    		}
	    		
	    		return super.visit(node);
	    	}
		});
		
		if(count > 0) {
			antiPatternModel.setLocation(file);
	    	antiPatternModel.setTotalFound(count);
	    	antiPatternList.add(antiPatternModel);
	    }
	}
	
	public static void PrintAntiPatterns() {
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

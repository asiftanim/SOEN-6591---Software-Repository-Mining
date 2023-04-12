package com.concordia.soen.soen6591_group3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import com.concordia.soen.soen6591_group3.NestedTry;

public class DetectAntiPattern {
	
	static ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
	
	public static String read(String fileName) throws IOException {
    	Path path = Paths.get(fileName);
    	String source = Files.lines(path).collect(Collectors.joining("\n"));
    	return source;
    }
	
	public static void DetectThrowsKitchenSink() {
		String source = "";
		try {
			ThrowsKitchenSink.class.getResource("myFile.txt");
			source = read("/Users/asiftanim/Desktop/Github/SOEN-6591---Software-Repository-Mining/soen6591-group3/src/main/java/com/concordia/soen/soen6591_group3/ThrowsKitchenSink.java");
		}catch(Exception ex) {
			System.out.println(ex);
		}
		
		parser.setSource(source.toCharArray());
		ASTNode root = parser.createAST(null);
		
		DetectThrowsKitchenSink visitor = new DetectThrowsKitchenSink();
		root.accept(visitor);
		
		if(visitor.count > 0) {
			System.out.println("Number of throw found: " + visitor.count);
			//System.out.println(source);
		}
	}
	
	public static void main(String[] args) {
		DetectThrowsKitchenSink();
		NestedTry.nestTry();
	}
	
	static class DetectThrowsKitchenSink extends ASTVisitor{
		int count = 0;
    	
    	@Override
    	public boolean visit(MethodDeclaration node) {

    		if(node.thrownExceptionTypes().size() > 3) {
    			System.out.println("Thorws Kitchen Sink Detected.");
    			count = node.thrownExceptionTypes().size();
    			return true;
    		}
    		
    		return false;
    	}
	}
}

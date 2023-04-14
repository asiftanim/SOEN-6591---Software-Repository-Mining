package com.concordia.soen.soen6591_group3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;

public class DetectAntiPattern {
	
	static ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
	static int count = 0;
	
	public static String read(String fileName) throws IOException {
    	Path path = Paths.get(fileName);
    	String source = Files.lines(path).collect(Collectors.joining("\n"));
    	return source;
    }
	
	public static void DetectThrowWithinFinally() {
		System.out.println("Detect Throw Within Finally Started...");
		count = 0;
		String source = "";
		String userDir = System.getProperty("user.dir");
		String path = "/src/main/java/com/concordia/soen/soen6591_group3/ExampleFinally.java";
		try {
			source = read(userDir+path);
		}catch(Exception ex) {
			System.out.println(ex);
		}
		
		parser.setSource(source.toCharArray());
		
		final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		compilationUnit.accept(new ASTVisitor() {
			@Override
			public boolean visit(TryStatement node) {
				Block finallyBlock = node.getFinally();
				int startLine = 0;
	    		if(finallyBlock != null) {
	    			List<Statement> finnalyStatements = finallyBlock.statements();
	    			for(Statement statement: finnalyStatements) {
	    				if(statement instanceof ThrowStatement) {
	    					if(count == 0) {
	    						System.out.println("Throw statement found in finally block:");
	    					}
	    					startLine = compilationUnit.getLineNumber(statement.getStartPosition());
	    					System.out.println(count + 1 +". "+ statement.toString() + " at line " + startLine);
	    					count += 1;
	    			         
	    					return false;
	    				}
	    			}
	    		}
		        
		        return super.visit(node);
			}
		});
		
		if(count > 0) {
			System.out.println("\nNumber of throw statements found in Finally block: " + count);
			System.out.println("Location of the file:");
			System.out.println(path);
			System.out.println();
		}
	}
	
	public static void DetectThrowsKitchenSink() {
		System.out.println("Detect Throws Kitchen Sink Started...");
		count = 0;
		String source = "";
		String userDir = System.getProperty("user.dir");
		String path = "/src/main/java/com/concordia/soen/soen6591_group3/ThrowsKitchenSink.java";
		try {
			source = read(userDir+path);
		}catch(Exception ex) {
			System.out.println(ex);
		}
		
		parser.setSource(source.toCharArray());
		
		final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		compilationUnit.accept(new ASTVisitor() {
			@Override
	    	public boolean visit(MethodDeclaration node) {
	    		if(node.thrownExceptionTypes().size() > 3) {
	    			System.out.println("Thorws Kitchen Sink Detected."); 
	    			int lineNumber = compilationUnit.getLineNumber(node.getStartPosition());
	    			System.out.println("Line number: " + lineNumber);
	    			count++;
	    		}
	    		
	    		return super.visit(node);
	    	}
		});
		
		if(count > 0) {
			System.out.println("Location: ");
	    	System.out.println(path);
	    	System.out.println("Total found: " + count);
	    	System.out.println();
		}else {
			System.out.println("No Throws Kitchen Sink Detected");
		}
	}
	
	public static void DetectDestructiveWrapping() {
		System.out.println("Detect Destructive Wrapping Started...");
		count = 0;
		String source = "";
		String userDir = System.getProperty("user.dir");
		String path = "/src/main/java/com/concordia/soen/soen6591_group3/DestructiveWrapping.java";
		try {
			source = read(userDir+path);
		}catch(Exception ex) {
			System.out.println(ex);
		}

		parser.setSource(source.toCharArray());
		
		final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		compilationUnit.accept(new ASTVisitor() {
			  @Override
			  public boolean visit(CatchClause node) {
				  
				List<Statement> statements = node.getBody().statements();
			    for(Statement statement: statements) {
			    	if(statement instanceof ThrowStatement) {
			    		System.out.println("Destructive wrapping detected");
			    		int lineNumber = compilationUnit.getLineNumber(node.getStartPosition()) + 1;
			    		System.out.println("Line number: " + lineNumber);
			    		count++;
			    	}
			    }
			    return super.visit(node);
			  }
		});
		
		if(count > 0) {
	    	System.out.println("Location: ");
	    	System.out.println(path);
	    	System.out.println("Total found: " + count);
	    	System.out.println();
	    }else {
	    	System.out.println("No Destructive Wrapping found!");
	    }
	}
	
	public static void DetectNestedTry() {
		System.out.println("Detect Nested Try Started...");
		count = 0;
		String source = "";
		String userDir = System.getProperty("user.dir");
		String path = "/src/main/java/com/concordia/soen/soen6591_group3/NestedTry.java";
		try {
			source = read(userDir+path);
		}catch(Exception ex) {
			System.out.println(ex);
		}

		parser.setSource(source.toCharArray());
		
		final CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		compilationUnit.accept(new ASTVisitor() {
			 
			  @Override
			  public boolean visit(TryStatement node) {
				List<Statement> statements = node.getBody().statements();
			    for(Statement statement: statements) {
			    	if(statement instanceof TryStatement) {
			    		System.out.println("Nested Try detected");
			    		int lineNumber = compilationUnit.getLineNumber(node.getStartPosition()) + 1;
			    		System.out.println("Line number: " + lineNumber);
			    		count++;
			    	}
			    }
			    return super.visit(node);
			  }
		});
		
		if(count > 0) {
	    	System.out.println("Location: ");
	    	System.out.println(path);
	    	System.out.println("Total found: " + count);
	    }
	}
	
	public static void main(String[] args) {
		
		DetectThrowWithinFinally();
		
		DetectThrowsKitchenSink();
		
		DetectDestructiveWrapping();
		
		DetectNestedTry();
		
	}

}




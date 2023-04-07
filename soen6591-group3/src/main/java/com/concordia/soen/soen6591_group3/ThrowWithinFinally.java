package com.concordia.soen.soen6591_group3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;

/**
 * Detect Throw within Finally
 *
 */
public class ThrowWithinFinally 
{
    public static void main( String[] args )
    {
    	ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
		
		for(String fileName : args) {
			String source;
			try {
				source = read(fileName);	
			} catch (IOException e) {
				System.err.println(e);
				continue;
			}	
			
			parser.setSource(source.toCharArray());
			
			ASTNode root = parser.createAST(null);
			Visitor visitor = new Visitor();
			root.accept(visitor);
			if(visitor.count > 0) {
				System.out.println("Number of throw statements found in Finally block: " + visitor.count);
				System.out.println("Location of the file:");
				System.out.println(fileName);
			}
		}
    }
    
    public static String read(String fileName) throws IOException {
    	Path path = Paths.get(fileName);
    	String source = Files.lines(path).collect(Collectors.joining("\n"));
    	
    	return source;
    }
    
    static class Visitor extends ASTVisitor {
    	int count = 0;
    	
    	@Override
    	public boolean visit(TryStatement node) {
    		Block finallyBlock = node.getFinally();
    		if(finallyBlock != null) {
    			List<Statement> finnalyStatements = finallyBlock.statements();
    			for(Statement statement: finnalyStatements) {
    				if(statement instanceof ThrowStatement) {
    					if(count == 0) {
    						System.out.println("Throw statement found in finally block:");
    					}
    					System.out.println(count + 1 +". "+ statement.toString());
    					count += 1;
    			         
    					return false;
    				}
    			}
    		}
    		
    		return true;
    	}
	}
}

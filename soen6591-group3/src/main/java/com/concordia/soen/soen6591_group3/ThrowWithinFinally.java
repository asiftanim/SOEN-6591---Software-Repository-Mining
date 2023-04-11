package com.concordia.soen.soen6591_group3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;

/**
 * Detect Throw within Finally
 *
 */
public class ThrowWithinFinally 
{
	static int count = 0;
    public static void main( String[] args )
    {
    	//args[0] = "/Users/asiftanim/Desktop/Github/SOEN-6591---Software-Repository-Mining/soen6591-group3/src/main/java/com/concordia/soen/soen6591_group3/ExampleFinally.java";
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
			
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
			cu.accept(new ASTVisitor() {
				
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
		    					startLine = cu.getLineNumber(statement.getStartPosition());
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
				System.out.println(fileName);
			}
		}
    }
    
    public static String read(String fileName) throws IOException {
    	Path path = Paths.get(fileName);
    	String source = Files.lines(path).collect(Collectors.joining("\n"));
    	
    	return source;
    }
    
}

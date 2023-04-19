package com.concordia.soen.soen6591_group3;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.ThrowStatement;

public class DestructiveWrappingDetector {
	static ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
	static int count = 0;
	static int totalCount = 0;
	static List<AntiPatternModel> antiPatternList = new ArrayList<AntiPatternModel>();
	
	public DestructiveWrappingDetector(List<String> fileList) {
		System.out.println("DestructiveWrappingDetector Running...");
		for(String file : fileList) {
			DetectDestructiveWrapping(file);
		}
		PrintAntiPatterns();
	}
	
	public static void DetectDestructiveWrapping(String file) {
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
			  public boolean visit(CatchClause node) {
				
				String catchException = node.getException().getType().toString();
				
				List<Statement> statements = node.getBody().statements();
			    for(Statement statement: statements) {
			    	
			    	if(statement instanceof ThrowStatement) {
			    		count++;
			    		totalCount++;
			    		antiPatternModel.setAntiPatternType("Destructive Wrapping");
			    		antiPatternModel.setLineNumber(Integer.toString(compilationUnit.getLineNumber(statement.getStartPosition())));
			    	}
			    	
			    	else if(statement.getNodeType() == Statement.FOR_STATEMENT || statement.getNodeType() == Statement.WHILE_STATEMENT
			    			|| statement.getNodeType() == Statement.IF_STATEMENT || statement.getNodeType() == Statement.SWITCH_STATEMENT) {
			    		//System.out.println(statement);
			    		statement.accept(new ASTVisitor() {
			    		  @Override
			  			  public boolean visit(ThrowStatement node)
			  		      {
					    		count++;
					    		totalCount++;
					    		antiPatternModel.setAntiPatternType("Destructive Wrapping");
					    		antiPatternModel.setLineNumber(Integer.toString(compilationUnit.getLineNumber(node.getStartPosition())));

			  					return super.visit(node);
			  		      }
			    		});
			    	}
			    	
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



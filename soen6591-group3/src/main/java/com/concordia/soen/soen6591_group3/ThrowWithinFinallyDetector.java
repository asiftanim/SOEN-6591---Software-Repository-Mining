package com.concordia.soen.soen6591_group3;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;

public class ThrowWithinFinallyDetector {
	static ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
	static int count = 0;
	static int totalCount = 0;
	static List<AntiPatternModel> antiPatternList = new ArrayList<AntiPatternModel>();
	
	public ThrowWithinFinallyDetector(List<String> fileList) {
		System.out.println("ThrowWithinFinallyDetector Running...");
		for(String file : fileList) {
			DetectThrowWithinFinally(file);
		}
		PrintAntiPatterns();
	}
	
	public static void DetectThrowWithinFinally(String file) {
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
			  public boolean visit(TryStatement node) {
				  Block finallyBlock = node.getFinally();
				  if(finallyBlock != null) {
					  List<Statement> statements = finallyBlock.statements();
					    for(Statement statement: statements) {
					    	if(statement instanceof ThrowStatement) {
					    		count++;
					    		totalCount++;
					    		antiPatternModel.setAntiPatternType("Throw Within Finally");
					    		antiPatternModel.setLineNumber(Integer.toString(compilationUnit.getLineNumber(statement.getStartPosition())));
					    	}
					    	
					    	else if(statement.getNodeType() == Statement.FOR_STATEMENT) {
					    		//System.out.println(statement);
					    		statement.accept(new ASTVisitor() {
					    		  @Override
					  			  public boolean visit(ThrowStatement node)
					  		      {
			  			    		count++;
			  			    		totalCount++;
			  			    		antiPatternModel.setAntiPatternType("Throw Within Finally");
			  			    		antiPatternModel.setLineNumber(Integer.toString(compilationUnit.getLineNumber(node.getStartPosition())));
			  			    		
					  				return super.visit(node);
					  		      }
					    		});
					    	}
					    	
					    	else if(statement.getNodeType() == Statement.WHILE_STATEMENT) {
					    		
					    		statement.accept(new ASTVisitor() {
					    		@Override
					  			  public boolean visit(ThrowStatement node)
					  			  {
					    			count++;
			  			    		totalCount++;
			  			    		antiPatternModel.setAntiPatternType("Throw Within Finally");
			  			    		antiPatternModel.setLineNumber(Integer.toString(compilationUnit.getLineNumber(node.getStartPosition())));
					  				
			  			    		return super.visit(node);
					  		      }
					    		});
					    	}
					    	
					    	else if(statement.getNodeType() == Statement.IF_STATEMENT) {
					    		statement.accept(new ASTVisitor() {
						    		@Override
						  			  public boolean visit(ThrowStatement node)
						  			  {
						    			count++;
				  			    		totalCount++;
				  			    		antiPatternModel.setAntiPatternType("Throw Within Finally");
				  			    		antiPatternModel.setLineNumber(Integer.toString(compilationUnit.getLineNumber(node.getStartPosition())));
						  				
										 return super.visit(node);
						  		      }
						    		});
					    	}
					    	
					    	else if(statement.getNodeType() == Statement.SWITCH_STATEMENT) {
					    		statement.accept(new ASTVisitor() {
						    		@Override
						  			  public boolean visit(ThrowStatement node)
						  			  {
						    			count++;
				  			    		totalCount++;
				  			    		antiPatternModel.setAntiPatternType("Throw Within Finally");
				  			    		antiPatternModel.setLineNumber(Integer.toString(compilationUnit.getLineNumber(node.getStartPosition())));
						  				
				  			    		return super.visit(node);
						  		      }
						    		});
					    	}
					    	
					    }
				  }
				
			    return false;
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

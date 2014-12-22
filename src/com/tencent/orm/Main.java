import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.plaf.TextUI;


public class Main {
	
	public static void main(String[] args) {
		parse("./src/HotChatInfo.java");
	}
	
	public static void parse(String path) {
		FileInputStream in = null;
		CompilationUnit cu = null;
        try {
        	in = new FileInputStream(path);
        	
            // parse the file
            cu = JavaParser.parse(in);
            
            // prints the resulting compilation unit to default system output
            System.out.println(cu.toString());
            
            // visit and print the methods names
            new MethodVisitor().visit(cu, null);
            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
       
	}
	
	/**
     * Simple visitor implementation for visiting MethodDeclaration nodes. 
     */
    private static 
	
}

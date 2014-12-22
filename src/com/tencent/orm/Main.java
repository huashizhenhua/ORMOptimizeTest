import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


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
            new Entity2TableParser().visit(cu, null);
            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}

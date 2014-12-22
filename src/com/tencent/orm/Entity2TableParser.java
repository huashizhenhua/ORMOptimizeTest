package com.tencent.orm;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class Entity2TableParser extends VoidVisitorAdapter {
	
	public static final String TAG = Entity2TableParser.class.getSimpleName();
	
	public OrmTable mOrmTable = new OrmTable();
	
	private String mPath;
	
	public Entity2TableParser(String path) {
		mPath = path;
	}
	
	public void parse() {
//		String entityFilePath = "G:/qqilte/QQLite/src/com/tencent/mobileqq/data/MessageRecord.java";
		FileInputStream in = null;
		CompilationUnit cu = null;
        try {
        	in = new FileInputStream(mPath);
            // parse the file
            cu = JavaParser.parse(in);
            // prints the resulting compilation unit to default system output
            // visit and print the methods names
            this.visit(cu, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String entityFilePath = "G:/qqilte/QQLite/src/com/tencent/mobileqq/data/MessageRecord.java";
		Entity2TableParser parser = new Entity2TableParser(entityFilePath);
		parser.parse();
		
	}
	
	/**
	 *  是否import了{@link #IMPORT_ENTITY}
	 */
	public boolean mIsEntityImport = false;
	
	/**
	 *  是否继承自Entity
	 */
	public boolean mIsEntityExtended = false;
	
	public static final String ENTITY = "Entity";
	public static final String IMPORT_ENTITY = "com.tencent.mobileqq.persistence.Entity";
	
	// <-----------------------------------------------------------------
	// import
	@Override
	public void visit(ImportDeclaration n, Object arg) {
		super.visit(n, arg);
		NameExpr importNameExpr = n.getName();
		String importString =  importNameExpr.toString();
		if (importString.contains(IMPORT_ENTITY)) {
			mIsEntityImport = true;
			ORMGenerator.debug(TAG, " isEntity = " + mIsEntityImport);
		}
	}
	// >-----------------------------------------------------------------
	
	
	// <-----------------------------------------------------------------
	// 类的声明
	
	public boolean isOrmTable() {
		return mIsEntityImport && mIsEntityExtended;
	}
	
	@Override
	public void visit(ClassExpr n, Object arg) {
		super.visit(n, arg);
	}
	
	public static final String UNIQUE_CONSTANT = "uniqueConstraints";
	
	public static final String UNIQUE_CONSTANT_KEY_COLUMN_NAMES = "columnsNames";

	public static final String UNIQUE_CONSTANT_KEY_CLAUSE = "clause";
	
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		super.visit(n, arg);
		
		mOrmTable.tableName = n.getName();
		
		List<AnnotationExpr> exprList = n.getAnnotations();
		if (exprList != null) {
			for(AnnotationExpr expr : exprList) {
				ORMGenerator.debug(TAG, "ClassOrInterfaceDeclaration annotation = " + expr.getName());
				if (UNIQUE_CONSTANT.equals(expr.getName().toString())) {
					NormalAnnotationExpr e = (NormalAnnotationExpr)expr;
					
					List<MemberValuePair> mbVals = e.getPairs();
					for (MemberValuePair vp :  mbVals) {
						ORMGenerator.debug(TAG, "ClassOrInterfaceDeclaration vp.name = " + vp.getName() + ", vp.expression = " + vp.getValue());
						String name = vp.getName();
						
						if (UNIQUE_CONSTANT_KEY_COLUMN_NAMES.equals(name)) {
							String uniqueColumnsRaw = vp.getValue().toString();
							String[] columnArr = uniqueColumnsRaw.split(",");
							for (String colName : columnArr) {
								if(!StringUtil.isBlank(colName)) {
									mOrmTable.pkColumnNames.add(colName);
								}
							}
						}
						else if(UNIQUE_CONSTANT_KEY_CLAUSE.equals(name)) {
							mOrmTable.conflictClause = vp.getValue().toString();
						}
					}
				}
			}
		}
		
		List<ClassOrInterfaceType>  extendsList = n.getExtends();
		for(ClassOrInterfaceType type : extendsList) {
//			System.out.println("ImportDeclaration = " + type.getName());
			String extendName = type.getName();
			if (extendName != null && extendName.contains(ENTITY)) {
				mIsEntityExtended = true;
				ORMGenerator.debug(TAG, " mIsEntityExtended = " + mIsEntityExtended);
			}
		}
	}
	// >-----------------------------------------------------------------

	// <-----------------------------------------------------------------
	// 方法
	@Override
	public void visit(MethodDeclaration n, Object arg) {
		// here you can access the attributes of the method.
		// this method will be called for all methods in this
		// CompilationUnit, including inner class methods
	}

	// >-----------------------------------------------------------------

	// <-----------------------------------------------------------------
	// 字段
	public static String getAnnotation(FieldDeclaration n) {
		// annotation
		String a = null;

		List<AnnotationExpr> annotationList = n.getAnnotations();
		if (annotationList != null) {
			for (AnnotationExpr expr : annotationList) {
				System.out.println("annotation = " + expr.getName());
				a = expr.getName().getName();
			}
		}
		return a;
	}
	
	
	private int mColumnOrdinal = 0;
	
	private int mNotColumnOridnal = 0;

	@Override
	public void visit(FieldDeclaration n, Object arg) {
		super.visit(n, arg);

//		System.out.println("-----FieldDeclaration-----start");
		// modifiers
		int modifiers = n.getModifiers();

		// 非静态字段
		if (!ModifierSet.isStatic(modifiers)) {
			// 注解
			String annotation = getAnnotation(n);
			
			// column
			List<VariableDeclarator> varList = n.getVariables();
			for (VariableDeclarator vd : varList) {
				VariableDeclaratorId id = vd.getId();
				// 表列
				String fieldName = id.getName();
				// annotation 转化 成字段
				System.out.println("fieldName = " + id.getName());
				
				OrmTable.Properties prop = new OrmTable.Properties();
				
				prop.columnName = id.getName();
				prop.type = id.getClass();
				
				
				
				
				// annotation unique 唯一键
				if ("unique".equals(annotation) && mOrmTable.pkColumnNames.contains(prop.columnName)) {
					prop.primaryKey = true;	
				} else {
					prop.primaryKey = false;
				}
				
				// annotattion defaultzero
				if ("defaultzero".equals(annotation)) {
					prop.defaultValue = "0";
				}
				
				// annotation notColumn 非列
				if ("notColumn".equals(annotation)) {
					prop.ordinal = mNotColumnOridnal;
					mNotColumnOridnal++;
					mOrmTable.notColumns.add(prop);
				} else {
					prop.ordinal = mColumnOrdinal;
					mColumnOrdinal++;
					mOrmTable.columns.add(prop);
				}
				
				if (prop.primaryKey) {
					mOrmTable.pkColumns.add(prop);
				}
			}
		}
	}
	
	// >-----------------------------------------------------------------

	// <-----------------------------------------------------------------
	// 注解
	@Override
	public void visit(AnnotationDeclaration n, Object arg) {
		super.visit(n, arg);
	}
	@Override
	public void visit(AnnotationMemberDeclaration n, Object arg) {
		super.visit(n, arg);
	}
	// >-----------------------------------------------------------------

}

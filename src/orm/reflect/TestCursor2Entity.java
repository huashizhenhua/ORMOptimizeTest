package orm.reflect;

import java.lang.reflect.Field;

import testframework.TestManager;
import testframework.TestTask;

/**
 * @auther zhenhuachen
 * @time 2014-12-4 下午12:17:20
 */
public class TestCursor2Entity {

	public static void main(String[] args) {
		int count = 10000000;
		System.out.println(String.format("游标生成实体Cursor2Entity, 测试次%d次", count));
		TestManager.execute(reflectValue, count);
		TestManager.execute(testCodeGenSetWithStringEqual, count);
		TestManager.execute(testCodeGenSetWitcHashEqual, count);
		TestManager.execute(testCodeGenWithKnowIndex, count);
	}

	public static MockCursor mMockCursor = new MockCursor();
	public static final TestTask reflectValue = new TestTask("Cursor2Entity 使用反射                 ") {
		public void run() {
			testReflectSet(mMockCursor);
		};
	};
	public static final TestTask testCodeGenSetWitcHashEqual = new TestTask("Cursor2Entity 使用代码生成&hashCode对比 ") {
		public void run() {
			testCodeGenSetWitcHashEqual(mMockCursor);
		};
	};
	public static final TestTask testCodeGenSetWithStringEqual = new TestTask("Cursor2Entity 使用代码生成&字符串对比   ") {
		public void run() {
			testCodeGenSetWithStringEqual(mMockCursor);
		};
	};
	public static final TestTask testCodeGenWithKnowIndex = new TestTask("Cursor2Entity 使用代码生成&已知道列索引 ") {
		public void run() {
			testCodeGenWithKnowIndex(mMockCursor);
		};
	};

	/**
	 * 通过反射实现Cursor2Entity
	 * @param cursor
	 */
	public static void testReflectSet(MockCursor cursor) {
		MockEntity tf = new MockEntity();
		Field[] fieldArr = RelectionCache.getValidFieldArr(tf.getClass()); // 200ms
		String name = null;
		Class<?> type;
		try {
			for (int i = 0, len = fieldArr.length; i < len; i++) { // cost
				Field field = fieldArr[i];
				name = field.getName();
				int colIndex = cursor.getColumnIndex(name);
				type = field.getType();
				if (type == String.class) {
					field.set(tf, cursor.getStringValue(colIndex));
				} else if (type == Integer.class) {
					field.set(tf, cursor.getIntValue(colIndex));
				} else if (type == Long.class) {
					field.set(tf, cursor.getLongValue(colIndex));
				} else if (type == Boolean.class) {
					field.set(tf, cursor.getBooleanValue(colIndex));
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 测试代码生成，通过hashcode对比操作速度。
	 */
	public static void testCodeGenSetWitcHashEqual(MockCursor cursor) {
		MockEntity tf = new MockEntity();
		String columnNames[] = cursor.getColumnNames();
		for (int i = 0, size = columnNames.length; i < size; i++) {
			String name = columnNames[i];
			int nameHashCode = name.hashCode();
			if (MockEntityConf.INT_VALUE_HASH == nameHashCode) {
				tf.intValue = cursor.getIntValue(cursor.getColumnIndex(name));
			} else if (MockEntityConf.STRING_VALUE_HASH == nameHashCode) {
				tf.stringValue = cursor.getStringValue(cursor.getColumnIndex(name));
			} else if (MockEntityConf.LONG_VALUE_HASH == nameHashCode) {
				tf.longValue = cursor.getLongValue(cursor.getColumnIndex(name));
			} else if (MockEntityConf.FLOAT_VALUE_HASH == nameHashCode) {
				tf.booleanValue = cursor.getBooleanValue(cursor.getColumnIndex(name));
			} else if (MockEntityConf.BOOLEAN_VALUE_HASH == nameHashCode) {
				tf.booleanValue = cursor.getBooleanValue(cursor.getColumnIndex(name));
			} else if (MockEntityConf. == nameHashCode) {
				tf.booleanValue = cursor.getBooleanValue(cursor.getColumnIndex(name));
			} else if (MockEntityConf.BOOLEAN_VALUE_HASH == nameHashCode) {
				tf.booleanValue = cursor.getBooleanValue(cursor.getColumnIndex(name));
			}
		}
	}
	
	/**
	 * 测试代码生成。使用字符串对比似地
	 * @param cursor
	 */
	public static void testCodeGenSetWithStringEqual(MockCursor cursor) {
		MockEntity tf = new MockEntity();
		String columnNames[] = cursor.getColumnNames();
		for (int i = 0, size = columnNames.length; i < size; i++) {
			String name = columnNames[i];
			if ("id".equals(name)) {
				tf.intValue = cursor.getIntValue(cursor.getColumnIndex(name));
			} else if ("name".equals(name)) {
				tf.stringValue = cursor.getStringValue(cursor.getColumnIndex(name));
			} else if ("time".equals(name)) {
				tf.longValue = cursor.getLongValue(cursor.getColumnIndex(name));
			} else if ("is".equals(name)) {
				tf.booleanValue = cursor.getBooleanValue(cursor.getColumnIndex(name));
			}
		}
	}
	
	/**
	 * 测试代码生成。使用字符串对比似地
	 * @param cursor
	 */
	public static void testCodeGenSetWithStringEqualWith(MockCursor cursor) {
		MockEntity tf = new MockEntity();
		String columnNames[] = cursor.getColumnNames();
		for (int i = 0, size = columnNames.length; i < size; i++) {
			String name = columnNames[i];
			if ("id".equals(name)) {
				tf.intValue = cursor.getIntValue(cursor.getColumnIndex(name));
			} else if ("name".equals(name)) {
				tf.stringValue = cursor.getStringValue(cursor.getColumnIndex(name));
			} else if ("time".equals(name)) {
				tf.longValue = cursor.getLongValue(cursor.getColumnIndex(name));
			} else if ("is".equals(name)) {
				tf.booleanValue = cursor.getBooleanValue(cursor.getColumnIndex(name));
			}
		}
	}
	
	/**
	 * 测试代码生成，已知道索引.
	 * @param cursor
	 */
	public static void testCodeGenWithKnowIndex(MockCursor cursor) {
		MockEntity tf = new MockEntity();
		tf.intValue = cursor.getIntValue(0);
		tf.stringValue = cursor.getStringValue(1);
		tf.longValue = cursor.getLongValue(2);
		tf.booleanValue = cursor.getBooleanValue(3);
	}
}

package orm.reflect;

import javax.swing.text.html.parser.Entity;

import testframework.TestManager;
import testframework.TestTask;

/**
 * @auther zhenhuachen
 * @time 2014-12-5 上午11:32:56
 */
public class TestEntity2ContentValues {
	
	public static final MockEntity entity = new MockEntity();
	
	public static final MockEntityConf entityConf = (MockEntityConf) TestEntityManager.getInstance().getManager(MockEntity.class);
	
	static {
		entity.intValue = 1111;
		entity.stringValue = "hello";
		entity.longValue = System.currentTimeMillis();
		entity.booleanValue = true;
		entity.floatValue = 1f;
		entity.doubleValue = 1d;
		entity.byteArrayValue = new byte[0];
		entity.byteValue = 1;
	}


	public static final ContentValues CONTENT_VALUES_CACHE = new ContentValues(MockEntityConf.COLUMN_NAMES.length);
	public static final TestTask entity2ContentValueCodeGenSizeSpecificOutsideCache = new TestTask("new ContentValue(columnSize) Cache") {
		public void run() {
			MockEntityConf.S_USE_DEFAULT_SIZE = false;
			entityConf.contentValues(entity, CONTENT_VALUES_CACHE);
		};
	};
	
	public static final TestTask entity2ContentValueCodeGenSizeSpecific = new TestTask("new ContentValue(columnSize)") {
		public void run() {
			MockEntityConf.S_USE_DEFAULT_SIZE = false;
			entityConf.contentValues(entity);
		};
	};
	
	
	
	
	public static final TestTask entity2ContentValueCodeGenSizeUnspecific = new TestTask("new ContentValue(default=8)") {
		public void run() {
			MockEntityConf.S_USE_DEFAULT_SIZE = true;
			entityConf.contentValues(entity);
		};
	};
	
	public static MockCursor mMockCursor = new MockCursor();
	
	public static final TestTask entity2ContentValueCodeGen = new TestTask("Entity2ContentValues 代码生成") {
		public void run() {
			entityConf.contentValues(entity);
		};
	};
	
	public static final TestTask entity2ContentValueRelect = new TestTask("Entity2ContentValues 反射  ") {
		public void run() {
			entityConf.contentValuesReflect(entity);
		};
	};
	
	public static void main(String[] args) {
		int count = 10000000;
		System.out.println(String.format("Entity2ContentValue, 测试次%d次", count));
		TestManager.executeCompare(entity2ContentValueRelect, entity2ContentValueCodeGen, count);
		
		
		System.out.println(String.format("ContentValue Size, 测试次%d次", count));
		TestManager.executeCompare(entity2ContentValueCodeGenSizeUnspecific, entity2ContentValueCodeGenSizeSpecific, count);
		
		
		System.out.println(String.format("ContentValue Cache, 测试次%d次", count));
		TestManager.executeCompare(entity2ContentValueCodeGenSizeSpecific, entity2ContentValueCodeGenSizeSpecificOutsideCache, count);
	}
}

package orm.reflect;

import java.lang.reflect.Field;

import andriod.content.ContentValues;

import orm.reflect.orm.BaseCursor;
import orm.reflect.orm.BaseEntity;
import orm.reflect.orm.BaseEntityConf;

/**
 * @auther zhenhuachen
 * @time 2014-12-4 下午8:08:31
 */
public class MockEntityConf extends BaseEntityConf {

	public static final String[] COLUMN_NAMES = new String[] { "intValue",
			"stringValue", "longValue", "booleanValue", "shortValue",
			"floatValue", "doubleValue", "byteValue", "byteArraryValue" };

	public static boolean S_USE_DEFAULT_SIZE = true;

	public static final int INT_VALUE_HASH = "intValue".hashCode();

	public static final int STRING_VALUE_HASH = "stringValue".hashCode();

	public static final int LONG_VALUE_HASH = "longValue".hashCode();

	public static final int BOOLEAN_VALUE_HASH = "booleanValue".hashCode();

	public static final int SHORT_VALUE_HASH = "shortValue".hashCode();

	public static final int FLOAT_VALUE_HASH = "floatValue".hashCode();

	public static final int DOUBLE_VALUE_HASH = "doubleValue".hashCode();

	public static final int BYTE_VALUE_HASH = "byteValue".hashCode();

	public static final int BYTE_ARRAY_VALUE_HASH = "byteArraryValue"
			.hashCode();

	public static final int TIME_HASH = "time".hashCode();

	public static void main(String[] args) {
		MockEntity entity = new MockEntity();
		entity.intValue = 1111;
		entity.stringValue = "hello";
		entity.longValue = System.currentTimeMillis();
		entity.booleanValue = true;
		entity.floatValue = 1f;
		entity.doubleValue = 1d;
		entity.byteArrayValue = new byte[0];
		entity.byteValue = 1;
		new MockEntityConf().contentValuesReflect(entity);
	}

	@Override
	public ContentValues contentValuesReflect(BaseEntity entity) {
		ContentValues cv = null;
		Field[] fields = RelectionCache.getValidFieldArr(entity.getClass());
		if (S_USE_DEFAULT_SIZE) {
			cv = new ContentValues();
		} else {
			cv = new ContentValues(fields.length);
		}
		Field field = null;
		for (int i = 0, len = fields.length; i < len; i++) {
			field = fields[i];
			String name = field.getName();
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			Object value;
			try {
				value = field.get(entity);
				Class clazz = field.getType();
				if (value instanceof Integer) {
					cv.put(name, (Integer) value);
				} else if (value instanceof Long) {
					cv.put(name, (Long) value);
				} else if (value instanceof String) {
					cv.put(name, (String) value);
				} else if (value instanceof byte[]) {
					cv.put(name, (byte[]) value);
				} else if (value instanceof Short) {
					cv.put(name, (Short) value);
				} else if (value instanceof Boolean) {
					cv.put(name, (Boolean) value);
				} else if (value instanceof Double) {
					cv.put(name, (Double) value);
				} else if (value instanceof Float) {
					cv.put(name, (Float) value);
				} else if (value instanceof Byte) {
					cv.put(name, (Byte) value);
				} else if (value instanceof Boolean) {
					cv.put(name, (Boolean) value);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return cv;
	}
	
	@Override
	public ContentValues contentValues(BaseEntity pEntity) {
		MockEntity entity = (MockEntity) pEntity;
		ContentValues contentValues = null;
		if (S_USE_DEFAULT_SIZE) {
			contentValues = new ContentValues();
		} else {
			contentValues = new ContentValues(COLUMN_NAMES.length);
		}
		contentValues.put("intValue", entity.intValue);
		contentValues.put("stringValue", entity.stringValue);
		contentValues.put("longValue", entity.longValue);
		contentValues.put("booleanValue", entity.booleanValue);
		contentValues.put("shortValue", entity.shortValue);
		contentValues.put("floatValue", entity.floatValue);
		contentValues.put("doubleValue", entity.doubleValue);
		contentValues.put("byteValue", entity.byteValue);
		contentValues.put("byteArrayValue", entity.byteArrayValue);
		return contentValues;
	}
	
	@Override
	public void contentValues(BaseEntity pEntity, ContentValues contentValues) {
		MockEntity entity = (MockEntity) pEntity;
		contentValues.put("intValue", entity.intValue);
		contentValues.put("stringValue", entity.stringValue);
		contentValues.put("longValue", entity.longValue);
		contentValues.put("booleanValue", entity.booleanValue);
		contentValues.put("shortValue", entity.shortValue);
		contentValues.put("floatValue", entity.floatValue);
		contentValues.put("doubleValue", entity.doubleValue);
		contentValues.put("byteValue", entity.byteValue);
		contentValues.put("byteArrayValue", entity.byteArrayValue);
	}

	@Override
	public BaseEntity cursor2Entity(BaseCursor cursor) {
		MockEntity tf = new MockEntity();
		String columnNames[] = cursor.getColumnNames();
		for (int i = 0, size = columnNames.length; i < size; i++) {
			String name = columnNames[i];
			int lHash = name.hashCode();
			int colIndex = cursor.getColumnIndex(name);
			if (MockEntityConf.INT_VALUE_HASH == name.hashCode()) { // int
				tf.intValue = cursor.getIntValue(colIndex);
			} else if (MockEntityConf.STRING_VALUE_HASH == lHash) { // string
				tf.stringValue = cursor.getStringValue(colIndex);
			} else if (MockEntityConf.LONG_VALUE_HASH == lHash) {// long
				tf.longValue = cursor.getLongValue(colIndex);
			} else if (MockEntityConf.BOOLEAN_VALUE_HASH == lHash) {// boolean
				tf.booleanValue = cursor.getBooleanValue(colIndex);
			} else if (MockEntityConf.SHORT_VALUE_HASH == lHash) {// short
				tf.shortValue = cursor.getShortValue(colIndex);
			} else if (MockEntityConf.DOUBLE_VALUE_HASH == lHash) {// float
				tf.floatValue = cursor.getFloatValue(colIndex);
			} else if (MockEntityConf.DOUBLE_VALUE_HASH == lHash) { // double
				tf.doubleValue = cursor.getDoubleValue(colIndex);
			} else if (MockEntityConf.BYTE_VALUE_HASH == lHash) {// byte
				tf.byteValue = cursor.getByteValue(colIndex);
			} else if (MockEntityConf.BYTE_ARRAY_VALUE_HASH == lHash) {// byte[]
				tf.byteArrayValue = cursor.getByteArray(colIndex);
			}
		}
		return tf;
	}

}

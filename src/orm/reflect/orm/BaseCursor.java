package orm.reflect.orm;

import java.util.HashMap;

/**
 * @auther zhenhuachen
 * @time 2014-12-4 下午8:46:57
 */
public class BaseCursor {
	/**
	 * 模拟SQLiteCursor使用游标提升速度。
	 */
	public static final boolean ENABLE_CACHE = true;
	/**
	 * 为了模拟getColumnIndex的耗时而存在
	 */
	public static final boolean ENABLE_INDEX = true;

	public int getIntValue(int index) {
		return 1;
	}

	public long getLongValue(int index) {
		return System.currentTimeMillis();
	}

	public String getStringValue(int index) {
		return "hello";
	}

	public boolean getBooleanValue(int index) {
		return true;
	}
	
	public short getShortValue(int index) {
		return 0;
	}
	
	public float getFloatValue(int index) {
		return 0;
	}
	
	public double getDoubleValue (int index) {
		return 0d;
	}
	
	public byte getByteValue (int index) {
		return 0;
	}
	
	public byte[] getByteArray(int index) {
		return new byte[0];
	}

	/**
	 * 缓存索引映射：columnName -> columnIndex
	 */
	private HashMap<String, Integer> map = new HashMap<String, Integer>();
	public static final String[] COLUMN_NAMES = new String[] { "intValue",
			"stringValue", "longValue", "booleanValue", "shortValue",
			"floatValue", "doubleValue", "byteValue", "byteArrayValue" };

	public String[] getColumnNames() {
		return COLUMN_NAMES;
	}

	public int getColumnIndex(String name) {
		int colIndex = -1;

		if (ENABLE_INDEX) {
			name.lastIndexOf(".");
		}

		if (ENABLE_CACHE) {
			Integer val = map.get(name);
			if (null != val) {
				return val.intValue();
			}
		}
		for (int i = 0; i < COLUMN_NAMES.length; i++) {
			if (name.equals(COLUMN_NAMES[i])) {
				colIndex = i;
				if (ENABLE_CACHE) {
					map.put(name, colIndex);
				}
				break;
			}
		}
		return colIndex;
	}
}

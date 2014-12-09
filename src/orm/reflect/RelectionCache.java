package orm.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther zhenhuachen
 * @time 2014-12-2 上午11:33:51
 */
public class RelectionCache {

	private static final Map<Class, Field[]> FIELD_CACHE_ARR = new HashMap<Class, Field[]>();

	private static final Map<Class, List<Field>> FIELD_LIST_CACHE = new HashMap<Class, List<Field>>();

	public static Field[] getValidFieldArr(Class clazz) {
		Field[] clazzFields = FIELD_CACHE_ARR.get(clazz);
		if (clazzFields == null) {
			List<Field> fields = new ArrayList<Field>();
			Field[] f = clazz.getFields();
			for (Field field : f) {
				// 跳过notColumn修饰的字段
				if (!Modifier.isStatic(field.getModifiers())
						&& !field.isAnnotationPresent(notColumn.class)) {
					fields.add(field);
				}
			}
			clazzFields = new Field[fields.size()];
			fields.toArray(clazzFields);
			FIELD_CACHE_ARR.put(clazz, clazzFields);
		} else {
//			System.out.println("getValidFieldArr cache" );
		}
		return clazzFields;
	}

	public static List<Field> getValidFieldList(Class clazz) {
		List<Field> fields = FIELD_LIST_CACHE.get(clazz);
		if (fields == null) {
			fields = new ArrayList<Field>();
			Field[] f = clazz.getFields();
			for (Field field : f) {
				// 跳过notColumn修饰的字段
				if (!Modifier.isStatic(field.getModifiers())
						&& !field.isAnnotationPresent(notColumn.class)) {
					fields.add(field);
				}
			}
			FIELD_LIST_CACHE.put(clazz, fields);
		} else {
//			System.out.println("getValidFieldList cache" );
		}
		return fields;
	}
}

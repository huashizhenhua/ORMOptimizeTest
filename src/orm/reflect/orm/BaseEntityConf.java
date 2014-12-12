package orm.reflect.orm;

import andriod.content.ContentValues;

/**
 * @auther zhenhuachen
 * @time 2014-12-4 下午8:09:31
 */
public abstract class BaseEntityConf {
	
	public abstract ContentValues contentValuesReflect(BaseEntity entity);

	public abstract BaseEntity cursor2Entity(BaseCursor cursor);
	
	public abstract ContentValues contentValues(BaseEntity entity);
	
	public abstract void contentValues(BaseEntity pEntity, ContentValues contentValues);
}


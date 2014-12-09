package orm.reflect.orm;

import java.util.HashMap;

import orm.reflect.MockEntity;
import orm.reflect.MockEntityConf;

/**
 * @auther zhenhuachen
 * @time 2014-12-4 下午8:59:47
 */
public class BaseEntityConfManager {

	public static final HashMap<Class, BaseEntityConf> map = new HashMap<Class, BaseEntityConf>();
	
	static {
		map.put(MockEntity.class, new MockEntityConf());
	}
	
	private static BaseEntityConfManager mManager = new BaseEntityConfManager();
	
	public static BaseEntityConfManager getInstance() {
		return mManager;
	}
	
	public BaseEntityConf getManager(Class name) {
		return map.get(name);
	}
}

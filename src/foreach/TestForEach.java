package foreach;
import java.util.ArrayList;

import testframework.TestManager;
import testframework.TestTask;

/**
 * @auther zhenhuachen
 * @time 2014-12-3 上午11:30:08
 */
public class TestForEach {

	// 测试数组
	public static final String[] S_ARR_STRING = new String[] { "1", "2", "3",
			"4", "5", "6", "7", "8", "9" };

	// 测试数组列表
	public static final ArrayList<String> S_LIST_STRING = new ArrayList<String>();

	static {
		for (String s : S_ARR_STRING) {
			S_LIST_STRING.add(s);
		}
	}

	// ForEach 列表
	public static TestTask taskForEachList = new TestTask("ForEach-List") {
		public void run() {
			for (String string : S_LIST_STRING) {
			}
		};
	};

	// ForEach 数组
	public static TestTask taskForEachArr = new TestTask("ForEach-Array") {
		public void run() {
			for (String string : S_ARR_STRING) {
			}
		};
	};
	
	// For 列表
	public static TestTask taskForList = new TestTask("For-List") {
		public void run() {
			for (int i = 0, size = S_LIST_STRING.size(); i < size; i++) {
			}
		};
	};

	// For 数组
	public static TestTask taskForArr = new TestTask("For-Array") {
		public void run() {
			for (int i = 0, len = S_ARR_STRING.length; i < len; i++) {
			}
		};
	};

	

	public static void main(String[] args) {
		int testTimes = 100000000;
		System.out.println(String.format("----->数组列表循环测试， 测试次数%d(次)", testTimes));
		TestManager.execute(taskForEachList, testTimes);
		TestManager.execute(taskForEachArr, testTimes);
		TestManager.execute(taskForList, testTimes);
		TestManager.execute(taskForArr, testTimes);
	}
}

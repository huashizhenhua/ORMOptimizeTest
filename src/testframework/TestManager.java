package testframework;

/**
 * @auther zhenhuachen
 * @time 2014-12-3 上午11:20:23
 */
public class TestManager {

	public static void execute(TestTask task, int count) {
		long st = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			task.run();
		}
		long et = System.currentTimeMillis();
	}

	public static long innerExecAndGetTime(TestTask task, int count) {
		long st = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			task.run();
		}
		long et = System.currentTimeMillis();
		
		long cost = et - st;
		
		System.out.println(String.format("[%s]\t:%s", task.name, "time = "
				+ cost + "ms"));
		return cost;
	}

	public static void executeCompare(TestTask task1, TestTask task2, int count) {
		long task1Time = innerExecAndGetTime(task1, count);
		long task2Time = innerExecAndGetTime(task2, count);
		double percent = task1Time * 1.0f / task2Time;
		System.out.println(String.format("提升百分之%d", Math.round((percent -1) * 100)));
	}

}

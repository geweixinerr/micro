package micro.commons.task;

public class TaskTest {

	/**
	 * 线程池组件
	 **/
	private static final GlobalThreadPoolTaskExecutor GLOBAL_TASK = GlobalThreadPoolTaskExecutor.getInstance();

	/**
	 * 事务补偿
	 **/
	private static final GlobalDelayQueueTask DELAY_TASK = GlobalDelayQueueTask.getInstance();
	
	public static void main(String[] args) {
		TransactionDelayTaskBean taskBean = new TransactionDelayTaskBean();
		taskBean.setTaskId("1024");
		taskBean.setTaskName("分布式事务补偿");
		taskBean.setExpire(100); 
		taskBean.setRetryMax(3);  
		taskBean.setTask(()-> {
			System.out.println("执行-> " + taskBean.getRetryNum());
			DELAY_TASK.compareAndSet(taskBean);
		});
		
		GLOBAL_TASK.execute(()-> {
			System.out.println("Hello");
			DELAY_TASK.compareAndSet(taskBean);
		});
	}
}

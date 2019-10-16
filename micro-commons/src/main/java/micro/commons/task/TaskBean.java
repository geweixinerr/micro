package micro.commons.task;

/**
 * @author gewx 任务列表
 **/
public abstract class TaskBean implements Runnable {

	private String taskId; // 任务Id

	private String taskName; // 任务名称

	private Runnable task; // 执行任务

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Runnable getTask() {
		return task;
	}

	public void setTask(Runnable task) {
		this.task = task;
	}

}

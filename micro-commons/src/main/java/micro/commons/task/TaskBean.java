package micro.commons.task;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 任务Bean
 * 
 * @author gewx
 **/
public final class TaskBean {

	/**
	 * 任务Id
	 * **/
	private String taskId;

	/**
	 * 任务名称
	 * **/
	private String taskName;

	/**
	 * 执行任务
	 * **/
	private Runnable task;

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
	
	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(this.taskId);
		return builder.toHashCode();
	}

	@Override
	public boolean equals(Object otherObject) {
		if (otherObject == null) {
			return false;
		}

		if (this == otherObject) {
			return true;
		}

		if (!(otherObject instanceof TaskBean)) {
			return false;
		}

		TaskBean taskObject = (TaskBean) otherObject;

		EqualsBuilder builder = new EqualsBuilder();
		builder.append(taskObject.taskId, this.taskId);
		return builder.isEquals();
	}
}

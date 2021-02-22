package micro.commons.task;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 任务延迟任务队列
 * 
 * @author gewx
 **/
public final class TaskBeanDelayed implements Delayed {

	/**
	 * 任务Id
	 **/
	private String taskId;

	/**
	 * 任务名称
	 **/
	private String taskName;

	/**
	 * 任务
	 **/
	private Runnable task;

	/**
	 * 重试-最大次数
	 **/
	private int retryMax;

	/**
	 * 重试当前次数
	 **/
	private int retryNum;

	/**
	 * 延迟时间
	 **/
	private long expire;

	/**
	 * 优先队列里面优先级规则 TimeUnit .MILLISECONDS 获取单位 为毫秒的时间戳
	 **/
	@Override
	public int compareTo(Delayed o) {
		return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
	}

	/**
	 * 剩余时间=到期时间-当前时间 convert: 将给定单元的时间段转换到此单元。
	 **/
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

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

	public int getRetryMax() {
		return retryMax;
	}

	public void setRetryMax(int retryMax) {
		this.retryMax = retryMax;
	}

	public int getRetryNum() {
		return retryNum;
	}

	public void setRetryNum(int retryNum) {
		this.retryNum = retryNum;
	}

	public long getExpire() {
		return expire;
	}

	public void setExpire(long expire) {
		this.expire = expire;
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

		if (!(otherObject instanceof TaskBeanDelayed)) {
			return false;
		}

		TaskBeanDelayed taskObject = (TaskBeanDelayed) otherObject;

		EqualsBuilder builder = new EqualsBuilder();
		builder.append(taskObject.taskId, this.taskId);
		return builder.isEquals();
	}
}

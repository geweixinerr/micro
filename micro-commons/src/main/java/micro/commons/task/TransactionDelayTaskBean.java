package micro.commons.task;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 事务补偿任务
 * 
 * @author gewx
 **/
public class TransactionDelayTaskBean extends AbstractTaskBeanDelayed {
	
	@Override
	public void run() {
		if (this.getRetryNum() < this.getRetryMax()) {
			this.setRetryNum(this.getRetryNum() + 1);  
			this.getTask().run();
		}
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(this.getTaskId());
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

		if (!(otherObject instanceof TransactionDelayTaskBean)) {
			return false;
		}

		TransactionDelayTaskBean taskObject = (TransactionDelayTaskBean) otherObject;

		EqualsBuilder builder = new EqualsBuilder();
		builder.append(taskObject.getTaskId(), this.getTaskId());
		return builder.isEquals();
	}
}

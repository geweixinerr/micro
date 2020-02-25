package micro.web.config.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 无状态SubObject工厂
 * 
 * @author gewx
 **/
public final class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {

	@Override
	public Subject createSubject(SubjectContext context) {
		context.setSessionCreationEnabled(false);
		return super.createSubject(context);
	}
}

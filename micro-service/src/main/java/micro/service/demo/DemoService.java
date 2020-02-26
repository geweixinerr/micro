package micro.service.demo;

import micro.bean.po.User;

/**
 * @author gewx 示例调度服务实现层
 * **/
public interface DemoService {

	void syncDataById(int id);
	
	User getUserById(String userName);
}

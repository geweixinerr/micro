package micro.service.demo;

import micro.bean.po.User;
import micro.commons.page.Pages;

/**
 * 示例调度服务实现层
 * 
 * @author gewx 
 * **/
public interface DemoService {

	/**
	 * 同步数据
	 * 
	 * @author gewx
	 * @param id 主键Id
	 * @return void
	 * **/
	void syncDataById(int id);
	
	/**
	 * 获取对象数据
	 * 
	 * @author gewx
	 * @param userName用户名
	 * @return 用户对象
	 * **/
	User getUserById(String userName);
	
	/**
	 * 获取用户集合
	 * 
	 * @author gewx
	 * @return 数据集合
	 * **/
	Pages<User> listUser();
}

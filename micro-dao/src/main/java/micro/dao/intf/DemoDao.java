package micro.dao.intf;

import java.util.List;
import java.util.Map;

/**
 * @author gewx DAO层示例
 * **/
import micro.dao.model.User;

public interface DemoDao {

	/**
	 * @author gewx 用户列表查询
	 * @param  params 查询参数
	 * @return 用户数据集合
	 * **/
	List<User> listUser(Map<String,Object> params);
	
	/**
	 * @author gewx 保存用户信息
	 * @param  user 用户对象
	 * @return 受影响行数
	 * **/
	Integer save(User user);
	
	/**
	 * @author gewx 修改用户信息
	 * @param  user 用户对象
	 * @return 受影响行数
	 * **/
	Integer updateById(User user);
}

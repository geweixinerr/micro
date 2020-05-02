package micro.dao.intf;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;

import micro.bean.po.User;

/**
 * @author gewx DAO层示例
 **/

public interface DemoDao {

	/**
	 * @author gewx 用户列表查询
	 * @param params 查询参数
	 * @return 用户数据集合
	 **/
	Page<User> listUser(@Param(value = "id") Long id);

	/**
	 * @author gewx 保存用户信息
	 * @param user 用户对象
	 * @return 受影响行数
	 **/
	Integer save(User user);

	/**
	 * @author gewx 修改用户信息
	 * @param user 用户对象
	 * @return 受影响行数
	 **/
	Integer updateById(User user);

	/**
	 * 获取用户对象
	 * 
	 * @author gewx
	 * @param userName 用户名
	 * @return 用户对象
	 **/
	User getUserById(String userName);
}

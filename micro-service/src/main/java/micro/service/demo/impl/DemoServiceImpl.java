package micro.service.demo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import micro.commons.log.MicroLogger;
import micro.dao.intf.DemoDao;
import micro.data.dto.User;
import micro.service.demo.DemoService;

@Service
public class DemoServiceImpl implements DemoService {
	
	private static final MicroLogger LOGGER = new MicroLogger(DemoServiceImpl.class);

	@Autowired
	private DemoDao demoDao;
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void syncDataById(int id) {
		final String methodName = "syncDataForId";
		LOGGER.enter(methodName, "业务数据同步服务[start], syncId: " + id);
		
		Map<String,Object> params = new HashMap<>();
		params.put("id", 1024);
		
		LOGGER.info("数据同步Id,id: " + id);
        List<User> list = demoDao.listUser(params);
        
        User u = new User();
        u.setId(String.valueOf(System.currentTimeMillis()));
        u.setUserName("Java");
        int result = demoDao.save(u);
        LOGGER.info("新用户同步成功,id: " + u.getId() + ", result: " + result);
        
        User update = new User();
        update.setId(u.getId());
        update.setUserName("JavaNew");
        
        int updateLine = demoDao.updateById(update);
        LOGGER.info("更新行数,line: " + updateLine);

        LOGGER.exit(methodName, "业务数据同步服务[end],result: " + list);
	}
}

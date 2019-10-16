package micro.dao.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Alias(value = "userDemo")
public class User implements Serializable {
	
	private static final long serialVersionUID = -2298016048890348557L;
	
	private String id;
	
	private String userName;
}

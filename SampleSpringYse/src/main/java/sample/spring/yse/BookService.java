package sample.spring.yse;

import java.util.Map;
import java.util.List;
public interface BookService {
	
	// insert
	String create(Map<String, Object> map);
	// select
	public Map<String, Object> detail(Map<String, Object> map);
	// update
	public boolean edit(Map<String, Object> map);
	// delete
	public boolean remove(Map<String, Object> map);
	// list
	public List<Map<String, Object>> list(Map<String, Object> map);
}

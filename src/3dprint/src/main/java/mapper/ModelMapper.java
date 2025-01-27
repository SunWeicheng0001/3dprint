package mapper;

import java.util.List;
import java.util.Map;

public interface ModelMapper {
	/**
	 * 得到模型的类型列表
	 * @param map
	 * @return
	 */
	public List getTotalModelType(Map map);
	
	/**
	 * 插入模型
	 * @param map
	 * @return
	 */
	public Integer insertModel(Map map);
}

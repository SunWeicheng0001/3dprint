package service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ModelService {

	/**
	 * 模型文件上传。
	 * 在模型上传页面，用户选择了一个模型文件，先上传模型文件，
	 * 之后真正上传模型的时候，就不需要再次提交模型文件了
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String uploadTempModel(HttpServletRequest request) throws Exception;
	
	/**
	 * 模型上传
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Boolean uploadModel(Map param) throws Exception;
	
	/**
	 * 查询模型的类型，获取模型类型列表
	 * @param param
	 * @return map 模型类型列表
	 */
	public List totalModelType(Map param);
}

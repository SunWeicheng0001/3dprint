package service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mapper.TaskMapper;
import mapper.UserMapper;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import service.TaskService;

@Service("taskServiceImpl")
public class TaskServiceImpl implements TaskService {

	@Resource(name = "taskMapper")
	TaskMapper taskMapper;
	@Resource(name = "userMapper")
	UserMapper userMapper;

	public List getTaskList(Map param) {
		List<Map> taskList = taskMapper.getTaskList(param);
		if (taskList != null && "正在进行中".equals(param.get("task_status"))) {
			for (Map item : taskList) {
				item.put("process_status", "processing");
				Map executor = taskMapper.getExecutorByTaskId(item);
				if (executor != null) {
					item.put("executor", executor);
				}
			}
		} else if (taskList != null && "已完成".equals(param.get("task_status"))) {
			for (Map item : taskList) {
				item.put("process_status", "finished");
				Map executor = taskMapper.getExecutorByTaskId(item);
				if (executor != null) {
					item.put("executor", executor);
				}
			}
		}
		return taskList;
	}

	public List getUserList(Map param) {
		return taskMapper.getUserList();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean pickTask(Map param) throws Exception {

		Map user = myUser();
		if (user == null) {
			throw new Exception("请先登录");
		}

		Map task = taskMapper.getTaskById(param);
		if (task == null) {
			throw new Exception("找不到该任务");
		}

		if (!"未处理".equals(task.get("task_status"))) {
			throw new Exception("该任务已在处理中");
		}

		Map taskStatusMap = new HashMap();
		taskStatusMap.put("task_id", task.get("task_id"));
		taskStatusMap.put("task_status_old", task.get("task_status"));
		taskStatusMap.put("task_status_new", "正在进行中");
		if (1 != taskMapper.updateTaskStatus(taskStatusMap)) {
			throw new Exception("领取任务失败");
		}

		Map processMap = new HashMap();
		processMap.put("task_id", task.get("task_id"));
		processMap.put("user_id", user.get("user_id"));
		processMap.put("process_duration", param.get("process_duration"));
		if (1 != taskMapper.insertProcess(processMap)) {
			throw new Exception("领取任务失败");
		}

		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean publishTask(Map param) throws Exception {

		Map user = myUser();
		if (user == null) {
			throw new Exception("请先登录");
		}

		param.put("task_publisher", user.get("user_id"));
		if (1 != taskMapper.insertTask(param)) {
			throw new Exception("发布任务失败");
		}
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean editTask(Map param) throws Exception {

		Map user = myUser();
		if (user == null) {
			throw new Exception("请先登录");
		}

		Map task = taskMapper.getTaskById(param);
		if (task == null) {
			throw new Exception("找不到该任务");
		}

		if ("未处理".equals(task.get("task_status"))) {
			if (1 != taskMapper.updateTask(param)) {
				throw new Exception("编辑任务失败");
			}
		} else if ("正在进行中".equals(task.get("task_status"))) {
			task.put("process_status", "processing");
			Map executor = taskMapper.getExecutorByTaskId(task);
			if(executor == null){
				throw new Exception("该任务正在被执行，但是找不到执行者，编辑任务失败");
			}
			if(Integer.parseInt("" + executor.get("user_id")) != Integer.parseInt("" + user.get("user_id"))){
				throw new Exception("该任务正在被其他人执行，你不能编辑它");
			}
			if (1 != taskMapper.updateTask(param)) {
				throw new Exception("编辑任务失败");
			}
		}

		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean cancelTask(Map param) throws Exception {

		Map task = taskMapper.getTaskById(param);
		if (task == null || !"正在进行中".equals(task.get("task_status"))) {
			throw new Exception("找不到该任务或该任务不处于状态“正在进行中”");
		}

		Map executor = new HashMap();
		executor.put("task_id", task.get("task_id"));
		executor.put("process_status", "processing");
		executor = taskMapper.getExecutorByTaskId(executor);
		if (executor == null) {
			throw new Exception("找不到该任务的原执行者");
		}

		Map processStatus = new HashMap();
		processStatus.put("process_id", executor.get("process_id"));
		processStatus.put("process_status_new", "cancelled");
		processStatus.put("process_status_old", "processing");
		if (1 != taskMapper.updateProcessStatus(processStatus)) {
			throw new Exception("撤销任务失败");
		}

		Map taskStatus = new HashMap();
		taskStatus.put("task_id", task.get("task_id"));
		taskStatus.put("task_status_old", "正在进行中");
		taskStatus.put("task_status_new", "未处理");
		if (1 != taskMapper.updateTaskStatus(taskStatus)) {
			throw new Exception("撤销任务失败");
		}

		System.out.println(task);
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean delayTask(Map param) throws Exception {

		Map task = taskMapper.getTaskById(param);
		if (task == null || !"正在进行中".equals(task.get("task_status"))) {
			throw new Exception("找不到该任务或该任务不处于状态“正在进行中”");
		}

		Map executor = new HashMap();
		executor.put("task_id", task.get("task_id"));
		executor.put("process_status", "processing");
		executor = taskMapper.getExecutorByTaskId(executor);
		if (executor == null) {
			throw new Exception("找不到该任务的原执行者");
		}

		Map processDuration = new HashMap();
		processDuration.put("process_id", executor.get("process_id"));
		processDuration.put("process_duration",
				Integer.parseInt("" + executor.get("process_duration"))
						+ Integer.parseInt("" + param.get("delay_days")));
		if (1 != taskMapper.updateProcessDuration(processDuration)) {
			throw new Exception("申请延期失败");
		}

		return true;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean endTask(Map param) throws Exception {

		Map task = taskMapper.getTaskById(param);
		if (task == null || !"正在进行中".equals(task.get("task_status"))) {
			throw new Exception("找不到该任务或该任务不处于状态“正在进行中”");
		}

		Map executor = new HashMap();
		executor.put("task_id", task.get("task_id"));
		executor.put("process_status", "processing");
		executor = taskMapper.getExecutorByTaskId(executor);
		if (executor == null) {
			throw new Exception("找不到该任务的原执行者");
		}

		Map processStatus = new HashMap();
		processStatus.put("process_id", executor.get("process_id"));
		processStatus.put("process_status_new", "finished");
		processStatus.put("process_status_old", "processing");
		if (1 != taskMapper.updateProcessStatus(processStatus)) {
			throw new Exception("提交失败");
		}

		Map taskStatus = new HashMap();
		taskStatus.put("task_id", task.get("task_id"));
		taskStatus.put("task_status_old", "正在进行中");
		taskStatus.put("task_status_new", "已完成");
		if (1 != taskMapper.updateTaskStatus(taskStatus)) {
			throw new Exception("提交失败");
		}

		return true;
	}

	public Map myUser() throws Exception {

		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		if (userDetails == null || userDetails.getUsername() == null) {
			return null;
		}

		Map param = new HashMap();
		param.put("user_account", userDetails.getUsername());

		Map user = userMapper.findUserByAccount(param);
		user.remove("user_password");

		return user;

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean deleteTask(Map param) throws Exception {

		Map user = myUser();
		if (user == null) {
			throw new Exception("请先登录");
		}

		Map task = taskMapper.getTaskById(param);
		if (task == null) {
			throw new Exception("找不到该任务");
		}

		if ("已删除".equals(task.get("task_status"))) {
			throw new Exception("该任务已经被删除");
		}

		if (!"未处理".equals(task.get("task_status"))) {
			throw new Exception("该任务不处于【未处理】状态，不能删除");
		}

		Map taskStatus = new HashMap();
		taskStatus.put("task_id", param.get("task_id"));
		taskStatus.put("task_status_old", task.get("task_status"));
		taskStatus.put("task_status_new", "已删除");
		if (1 != taskMapper.updateTaskStatus(taskStatus)) {
			throw new Exception("删除任务失败");
		}

		return true;
	}

}

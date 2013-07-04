package com.hzth.myapp.leave.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzth.myapp.core.util.DateUtil;
import com.hzth.myapp.core.util.JsonUtil;
import com.hzth.myapp.core.util.ReflectUtil;
import com.hzth.myapp.core.web.MyBaseAction;
import com.hzth.myapp.leave.model.Leave;
import com.hzth.myapp.leave.service.LeaveService;
import com.hzth.myapp.leave.util.TaskUtil;
import com.hzth.myapp.user.model.Group;
import com.hzth.myapp.user.model.User;
import com.hzth.myapp.user.service.GroupService;
import com.hzth.myapp.user.service.UserService;
import com.hzth.myapp.user.util.UserUtil;

@ParentPackage(value = "struts-default")
@Namespace(value = "/le")
@Action(value = "leave_*")
@Results({ @Result(name = "success", location = "/le/leave/jsp/leave.jsp"), @Result(name = "input", location = "/le/leave/jsp/leave-input.jsp"), @Result(name = "inputDept", location = "/le/leave/jsp/leave-inputDept.jsp"), @Result(name = "inputLeader", location = "/le/leave/jsp/leave-inputLeader.jsp"), @Result(name = "inputReApp", location = "/le/leave/jsp/leave-inputReApp.jsp"), @Result(name = "reload", type = "redirect", location = "leave!list.action") })
public class LeaveAction extends MyBaseAction {

	public void deploy() throws Exception {
		// repositoryService.createDeployment().addClasspathResource("leaveProcess.bpmn20.xml").name("aaaaaa").deploy();
		// String filePath = this.getClass().getClassLoader().getResource("LeaveProcess.bpmn").getPath();
		// String filePath = this.getClass().getClassLoader().getResource("LeaveProcess2.bpmn").getPath();

		// String filePath = this.getClass().getClassLoader().getResource("LeaveProcess.bar").getPath();
		String filePath = this.getClass().getClassLoader().getResource("LeaveProcessWithListenerV1.bar").getPath();
		FileInputStream inputStream = new FileInputStream(filePath);
		repositoryService.createDeployment().addZipInputStream(new ZipInputStream(inputStream)).name("bbbbb").deploy();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leave").latestVersion().singleResult();
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leave").list();
		for (ProcessDefinition pd : processDefinitions) {
			if (pd.getVersion() < processDefinition.getVersion()) {
				List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processDefinitionId(pd.getId()).list();
				for (ProcessInstance pi : processInstances) {
					String leaveId = pi.getBusinessKey();
					Leave leave = leaveService.get(leaveId);
					System.out.println("现有的旧版流程：" + leave.getReason());
				}
			}
		}
		this.print("OK");
	}

	public void ajaxGetDeploy() {
		List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
		List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		for (Deployment deployment : deployments) {
			Map<String, String> value = new HashMap<String, String>();
			value.put("id", deployment.getId());
			value.put("name", deployment.getName());
			value.put("time", DateUtil.formatToString(deployment.getDeploymentTime()));
			values.add(value);
		}
		String result = JsonUtil.collection2Json(values, null);
		this.print(result);
	}

	public void ajaxDeleteDeploy() {
		String deploymentId = this.getRequest().getParameter("id");
		leaveService.deleteDeployment(deploymentId);
		this.print("OK");
	}

	public String input() {

		return INPUT;
	}

	public void ajaxSaveApplicant() {
		String reason = this.getRequest().getParameter("reason");
		leaveService.saveApplicant(reason);
		this.print("OK");
	}

	public void ajaxGetMyApp() {
		List<Leave> leaves = leaveService.getListWithAppId(UserUtil.getCurrentUser().getId());
		List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		for (Leave leave : leaves) {
			Map<String, String> value = new HashMap<String, String>();
			value.put("id", leave.getId());
			value.put("reason", leave.getReason());
			value.put("applicantDate", leave.getApplicantDate());
			long count = runtimeService.createProcessInstanceQuery().processInstanceId(leave.getProcessInstanceId()).count();
			if (count == 0) {
				value.put("taskName", "已完成");
				value.put("assignee", "已完成");
			} else {
				ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(leave.getProcessInstanceId()).singleResult();
				Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
				value.put("taskName", task.getName());
				String assignee = task.getAssignee();
				if (StringUtils.isNotBlank(assignee)) {
					value.put("assignee", userService.get(assignee).getName());
				} else {
					value.put("assignee", "无人认领");
				}
			}
			values.add(value);
		}
		String result = JsonUtil.collection2Json(values, null);
		this.print(result);
	}

	public void ajaxGetMyTask() {
		User user = UserUtil.getCurrentUser();
		List<Task> allTask = new ArrayList<Task>();
		List<String> candidateGroups = new ArrayList<String>();
		List<Group> groups = groupService.findGroupByUser(user);
		for (Group group : groups) {
			candidateGroups.add(group.getCode());
		}
		List<Task> task1 = taskService.createTaskQuery().processDefinitionKey("leave").taskAssignee(user.getId()).list();
		allTask.addAll(task1);
		List<Task> task2 = new ArrayList<Task>();
		if (candidateGroups.size() > 0) {
			task2 = taskService.createTaskQuery().processDefinitionKey("leave").taskCandidateGroupIn(candidateGroups).orderByTaskId().desc().list();
		}
		allTask.addAll(task2);
		List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		for (Task task : allTask) {
			Map<String, String> value = new HashMap<String, String>();
			Leave leave = leaveService.getLeaveWithProcessInstanceId(task.getProcessInstanceId());
			value.put("id", leave.getId());
			value.put("reason", leave.getReason());
			value.put("applicantDate", leave.getApplicantDate());
			value.put("applicantUser", leave.getApplicantUser().getName());
			value.put("status", StringUtils.isBlank(task.getAssignee()) ? "未认领" : "已认领");
			value.put("statusFlag", StringUtils.isBlank(task.getAssignee()) + "");
			value.put("jsFunction", TaskUtil.getComplateJsFunction(task.getTaskDefinitionKey()));
			value.put("taskId", task.getId());
			value.put("taskName", task.getName());
			value.put("deptSuggestion", StringUtils.isBlank(leave.getDeptSuggestion()) ? "" : leave.getDeptSuggestion());
			value.put("deptUser", leave.getDeptUser() == null ? "" : leave.getDeptUser().getName());
			values.add(value);
		}
		String result = JsonUtil.collection2Json(values, null);
		this.print(result);
	}

	public void ajaxGetAllTask() {
		List<Leave> leaves = leaveService.getAll();
		List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		for (Leave leave : leaves) {
			Map<String, String> value = new HashMap<String, String>();
			value.put("id", leave.getId());
			value.put("reason", leave.getReason());
			value.put("applicantDate", leave.getApplicantDate());
			value.put("applicantUser", leave.getApplicantUser().getName());
			value.put("deptSuggestion", StringUtils.isBlank(leave.getDeptSuggestion()) ? "" : leave.getDeptSuggestion());
			value.put("deptUser", leave.getDeptUser() == null ? "" : leave.getDeptUser().getName());
			Task task = taskService.createTaskQuery().processInstanceBusinessKey(leave.getId()).singleResult();
			if (task != null) {
				value.put("status", StringUtils.isBlank(task.getAssignee()) ? "未认领" : "已认领");
				value.put("taskName", task.getName());
				value.put("assignee", StringUtils.isBlank(task.getAssignee()) ? "" : userService.get(task.getAssignee()).getName());
			} else {// 已完成的task从历史中获取
				// 一个任务经过的所有流程
				// HistoricActivityInstance historyInstance = historyService.createHistoricActivityInstanceQuery().processInstanceId(leave.getProcessInstanceId()).list().get(0);
				// historyService.createHistoricDetailQuery().processInstanceId(leave.getProcessInstanceId()).list();
				// historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(leave.getId()).list();
				value.put("status", "已完成");
				value.put("taskName", "已完成");
				value.put("assignee", "已完成");
			}
			values.add(value);
		}
		this.print(JsonUtil.collection2Json(values));
	}

	public void ajaxClaim() {
		String taskId = this.getRequest().getParameter("taskId");
		taskService.claim(taskId, UserUtil.getCurrentUser().getId());
		this.print("OK");
	}

	public String inputDept() {
		String id = this.getRequest().getParameter("id");
		initLeaveAndTask(id);
		return "inputDept";
	}

	private void initLeaveAndTask(String id) {
		Leave leave = leaveService.get(id);
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(id).singleResult();
		this.getRequest().setAttribute("leave", leave);
		this.getRequest().setAttribute("task", task);
	}

	public String inputLeader() {
		String id = this.getRequest().getParameter("id");
		initLeaveAndTask(id);
		return "inputLeader";
	}

	public String inputReApp() {
		String id = this.getRequest().getParameter("id");
		initLeaveAndTask(id);
		return "inputReApp";
	}

	public void ajaxDeptComplate() {
		String id = this.getRequest().getParameter("id");
		String deptSuggestion = this.getRequest().getParameter("deptSuggestion");
		String result = this.getRequest().getParameter("result");
		leaveService.deptComplate(id, deptSuggestion, result);
		this.print("OK");
	}

	public void ajaxLeaderComplate() {
		String id = this.getRequest().getParameter("id");
		String leaderSuggestion = this.getRequest().getParameter("leaderSuggestion");
		String result = this.getRequest().getParameter("result");
		leaveService.leaderComplate(id, leaderSuggestion, result);
		this.print("OK");
	}

	public void ajaxUserDelLeave() {
		String id = this.getRequest().getParameter("id");
		leaveService.userComplate(id);
		this.print("OK");
	}

	public void ajaxSaveReApp() {
		String id = this.getRequest().getParameter("id");
		String reason = this.getRequest().getParameter("reason");
		String result = this.getRequest().getParameter("result");
		leaveService.saveReApp(id, reason, result);
		this.print("OK");
	}

	public void loadPicture() {
		String taskId = this.getRequest().getParameter("taskId");
		try {
			String processDefinitionId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessDefinitionId();
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
			String resourceName = processDefinition.getDiagramResourceName();
			InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
				this.getResponse().getOutputStream().write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String traceProcess() {
		try {
			String taskId = this.getRequest().getParameter("taskId");
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			// ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
			String processInstanceId = task.getProcessInstanceId();

			Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();// 执行实例
			Object property = ReflectUtil.getFieldValue(execution, "activityId");
			String activityId = "";
			if (property != null) {
				activityId = property.toString();
			}
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
			List<ActivityImpl> activitiList = processDefinition.getActivities();// 获得当前任务的所有节点
			ActivityImpl activity = null;
			for (ActivityImpl activityImpl : activitiList) {
				String id = activityImpl.getId();
				if (id.equals(activityId)) {// 获得执行到那个节点
					activity = activityImpl;
					break;
				}
			}

			Map<String, Object> activityImageInfo = new HashMap<String, Object>();
			activityImageInfo.put("x", activity.getX());
			activityImageInfo.put("y", activity.getY());
			activityImageInfo.put("width", activity.getWidth());
			activityImageInfo.put("height", activity.getHeight());
			this.print(JsonUtil.map2Json(activityImageInfo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final long serialVersionUID = -2791656004085300588L;

	private Leave model = new Leave();

	public Leave getModel() {
		return model;
	}

	public void setModel(Leave model) {
		this.model = model;
	}

	@Autowired
	private LeaveService leaveService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private FormService formService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;
}

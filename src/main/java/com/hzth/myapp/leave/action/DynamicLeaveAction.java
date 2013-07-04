package com.hzth.myapp.leave.action;

import java.io.FileInputStream;
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
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzth.myapp.core.util.DateUtil;
import com.hzth.myapp.core.util.JsonUtil;
import com.hzth.myapp.core.web.MyBaseAction;
import com.hzth.myapp.leave.util.DynamicFormUtil;
import com.hzth.myapp.user.service.UserService;
import com.hzth.myapp.user.util.UserUtil;

@ParentPackage(value = "struts-default")
@Namespace(value = "/le")
@Action(value = "dynamicLeave_*")
// @Results({
// @Result(name = "success", location = "/um/user/jsp/user.jsp"),
// @Result(name = "input", location = "/um/user/jsp/user-input.jsp"),
// @Result(name = "reload", type = "redirect", location = "user!list.action") })
public class DynamicLeaveAction extends MyBaseAction {

	public void deploy() throws Exception {
		// repositoryService.createDeployment().addClasspathResource("DynamicTestForm.bpmn20.xml").name("aaaaaa").deploy();
		String filePath = this.getClass().getClassLoader().getResource("DynamicLeaveFormV2.bar").getPath();
		FileInputStream inputStream = new FileInputStream(filePath);
		repositoryService.createDeployment().addZipInputStream(new ZipInputStream(inputStream)).name("动态表单请假申请V2").deploy();
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
		repositoryService.deleteDeployment(deploymentId, true);
		this.print("OK");
	}

	public void getLastProcessDefinitionId() {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("DynamicLeaveForm").latestVersion().singleResult();
		if (processDefinition == null) {
			this.print("");
		} else {
			this.print(processDefinition.getId());
		}
	}

	public void getStartFormData() {
		String processDefinitionId = this.getRequest().getParameter("processDefinitionId");
		StartFormData startFormData = formService.getStartFormData(processDefinitionId);
		List<FormProperty> formProperties = startFormData.getFormProperties();
		this.print(DynamicFormUtil.getFormJsonString(formProperties));
	}

	public void submitStartForm() {
		String processDefinitionId = this.getRequest().getParameter("processDefinitionId");
		Map<String, String> formProperties = DynamicFormUtil.getFormProperties(this.getRequest());
		ProcessInstance processInstance = formService.submitStartFormData(processDefinitionId, formProperties);
		processInstance.getId();
		this.print("OK");
	}

	public void ajaxGetAllTask() {
		List<Task> allTask = taskService.createTaskQuery().processDefinitionKey("DynamicLeaveForm").list();
		List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		for (Task task : allTask) {
			TaskFormData taskFormData = formService.getTaskFormData(task.getId());
			Map<String, String> value = new HashMap<String, String>();
			value.put("taskId", task.getId());
			value.put("taskName", task.getName());
			value.put("reason", DynamicFormUtil.getFormValue(taskFormData.getFormProperties(), "reason"));
			value.put("startDate", DynamicFormUtil.getFormValue(taskFormData.getFormProperties(), "startDate"));
			value.put("suggestion", DynamicFormUtil.getFormValue(taskFormData.getFormProperties(), "suggestion"));
			value.put("status", StringUtils.isBlank(task.getAssignee()) ? "未认领" : "已认领");
			value.put("assignee", StringUtils.isBlank(task.getAssignee()) ? "" : userService.get(task.getAssignee()).getName());
			values.add(value);
		}
		String result = JsonUtil.collection2Json(values, null);
		this.print(result);
	}

	public void toAssignee() {
		String taskId = this.getRequest().getParameter("taskId");
		taskService.claim(taskId, UserUtil.getCurrentUser().getId());
		this.print("OK");
	}

	public void getTaskFormData() {
		String taskId = this.getRequest().getParameter("taskId");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		List<FormProperty> formProperties = taskFormData.getFormProperties();
		this.print(DynamicFormUtil.getFormJsonString(formProperties));
	}

	public void submitTaskForm() {
		String taskId = this.getRequest().getParameter("taskId");
		Map<String, String> params = DynamicFormUtil.getFormProperties(this.getRequest());
		formService.submitTaskFormData(taskId, params);
		this.print("OK");
	}

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private FormService formService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private UserService userService;

	private static final long serialVersionUID = -3392246482882795380L;

}

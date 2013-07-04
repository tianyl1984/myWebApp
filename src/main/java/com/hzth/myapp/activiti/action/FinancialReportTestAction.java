package com.hzth.myapp.activiti.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzth.myapp.core.web.MyBaseAction;

@ParentPackage(value = "struts-default")
@Namespace(value = "/fr")
@Action(value = "financialReport_*")
@Results({
		@Result(name = "success", location = "/um/user/jsp/user.jsp"),
		@Result(name = "input", location = "/um/user/jsp/user-input.jsp"),
		@Result(name = "reload", type = "redirect", location = "user!list.action") })
public class FinancialReportTestAction extends MyBaseAction {

	private static final long serialVersionUID = -604353140925039445L;

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

	public void deploy() {
		repositoryService.createDeployment().addClasspathResource("FinancialReportProcess.bpmn20.xml")
				// .addClasspathResource("financialReport1.form")
				// .addClasspathResource("financialReport2.form")
				.deploy();
		this.print("OK");
	}

	public void startOneInstance() {
		// runtimeService.startProcessInstanceByKey("financialReport");
		runtimeService.startProcessInstanceByKey("financialReport", "businessKey001");
		this.print("OK");
	}

	public void getTasks() {
		String groupType = this.getRequest().getParameter("groupType");
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(groupType).list();
		print(tasks);
	}

	public void getAllTasks() {
		List<Task> tasks = taskService.createTaskQuery().list();
		print(tasks);
	}

	public void searchTaskAssignee() {
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("张三").list();
		print(tasks);
	}

	public void claim() {
		String taskId = this.getRequest().getParameter("taskId");
		taskService.claim(taskId, "张三");
		// Task task =
		// taskService.createTaskQuery().taskId(taskId).singleResult();
		// Object obj =
		// formService.getRenderedStartForm(task.getProcessDefinitionId());
		// System.out.println(obj);
	}

	public void complate() {
		String taskId = this.getRequest().getParameter("taskId");
		taskService.complete(taskId);
		this.print("OK");
	}

	private void print(List<Task> tasks) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<Map<String, String>> taskMaps = new ArrayList<Map<String, String>>();
		for (Task task : tasks) {
			Map<String, String> taskMap = new HashMap<String, String>();
			taskMap.put("id", task.getId());
			taskMap.put("name", task.getName());
			taskMap.put("owner", task.getOwner());
			taskMap.put("assignee", task.getAssignee());
			taskMap.put("description", task.getDescription());
			taskMap.put("parentTaskId", task.getParentTaskId());
			taskMap.put("processDefinitionId", task.getProcessDefinitionId());
			taskMap.put("processInstanceId", task.getProcessInstanceId());
			taskMap.put("taskDefinitionKey", task.getTaskDefinitionKey());
			// taskMap.put("taskDefinitionKey", "");
			if (task.getCreateTime() != null) {
				taskMap.put("createTime", sdf.format(task.getCreateTime()));
			} else {
				taskMap.put("createTime", "");
			}
			if (task.getDueDate() != null) {
				taskMap.put("dueDate", sdf.format(task.getDueDate()));
			} else {
				taskMap.put("dueDate", "");
			}
			taskMap.put("priority", task.getPriority() + "");
			taskMaps.add(taskMap);
		}
		JSONArray array = JSONArray.fromObject(taskMaps);
		this.print(array.toString());
	}
}

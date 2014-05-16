package com.hzth.myapp.activiti.action;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzth.myapp.activiti.util.WorkflowUtil;
import com.hzth.myapp.core.util.DateUtil;
import com.hzth.myapp.core.web.MyBaseAction;
import com.hzth.myapp.user.model.User;
import com.hzth.myapp.user.service.UserService;

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

	@Autowired
	private UserService userService;

	@Autowired
	private HistoryService historyService;

	public void deploy() {
		Deployment deployment = repositoryService.createDeployment().addClasspathResource("process/myProcess.bpmn20.xml")
				// .addClasspathResource("financialReport1.form")
				// .addClasspathResource("financialReport2.form")
				.deploy();
		System.out.println("发布成功：" + deployment.getId());
		this.print("OK");
	}

	public void searchDeploy() {
		List<Deployment> deploymentList = repositoryService.createDeploymentQuery().list();
		List<Map<String, String>> listMap = new ArrayList<>();
		for (Deployment depl : deploymentList) {
			Map<String, String> map = new HashMap<>();
			map.put("id", depl.getId());
			map.put("name", depl.getName());
			map.put("deplTime", DateUtil.formatToString(depl.getDeploymentTime()));
			map.put("category", depl.getCategory());
			ProcessDefinition defin = repositoryService.createProcessDefinitionQuery().deploymentId(depl.getId()).singleResult();
			map.put("key", defin.getKey());
			map.put("diagramResourceName", defin.getDiagramResourceName());
			map.put("version", defin.getVersion() + "");
			listMap.add(map);
		}
		this.print(JSONArray.fromObject(listMap).toString());
	}

	public void deleDeploy() {
		String deplId = this.getRequest().getParameter("deplId");
		repositoryService.deleteDeployment(deplId, true);
		this.print("OK");
	}

	public void showDiagramByDeplId() {
		String deplId = this.getRequest().getParameter("deplId");
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deplId).singleResult();
		String resourceName = processDefinition.getDiagramResourceName();
		InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		byte[] b = new byte[1024];
		int len = -1;
		try {
			while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
				this.getResponse().getOutputStream().write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startOneInstance() {
		// runtimeService.startProcessInstanceByKey("financialReport");
		runtimeService.startProcessInstanceByKey("myProcess", "businessKey001");
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
		User user = userService.getUserByName("u2");
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(user.getId()).list();
		print(tasks);
	}

	public void claim() {
		String taskId = this.getRequest().getParameter("taskId");
		User user = userService.getUserByName("u2");
		taskService.claim(taskId, user.getId());
		this.print("认领成功");
	}

	public void complate() {
		String taskId = this.getRequest().getParameter("taskId");
		taskService.complete(taskId);
		this.print("OK");
	}

	public void rollbackTask() {
		String taskId = this.getRequest().getParameter("taskId");
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String taskDefinKey = task.getTaskDefinitionKey();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());
		List<ActivityImpl> activitiList = processDefinition.getActivities();// 获得当前任务的所有节点
		ActivityImpl currActiviti = null;// 当前活动节点
		ActivityImpl destActiviti = null;// 驳回目标节点
		for (ActivityImpl activity : activitiList) {
			if (activity.getId().equals(taskDefinKey)) {
				currActiviti = activity;
			}
			if (activity.getId().equals("check2")) {
				destActiviti = activity;
			}
			if (currActiviti != null && destActiviti != null) {
				break;
			}
		}
		// 保存当前活动节点的流程参数
		List<PvmTransition> hisPvmTransitionList = new ArrayList<PvmTransition>(0);
		for (PvmTransition pvmTransition : currActiviti.getOutgoingTransitions()) {
			hisPvmTransitionList.add(pvmTransition);
		}
		// 清空当前活动几点的所有流出项
		currActiviti.getOutgoingTransitions().clear();
		// 为当前节点动态创建新的流出项
		TransitionImpl newTransitionImpl = currActiviti.createOutgoingTransition();
		// 为当前活动节点新的流出目标指定流程目标
		newTransitionImpl.setDestination(destActiviti);
		// 执行当前任务
		taskService.complete(taskId);

		// 清除目标节点的新流入项
		destActiviti.getIncomingTransitions().remove(newTransitionImpl);
		// 清除原活动节点的临时流程项
		currActiviti.getOutgoingTransitions().clear();
		// 还原原活动节点流出项参数
		currActiviti.getOutgoingTransitions().addAll(hisPvmTransitionList);
		this.print("回退成功");
	}

	/**
	 * 显示流程图
	 */
	public void showDiagram() {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myProcess").active().latestVersion().singleResult();
		String resourceName = processDefinition.getDiagramResourceName();
		InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		byte[] b = new byte[1024];
		int len = -1;
		try {
			while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
				this.getResponse().getOutputStream().write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void graphTrace2() throws Exception {
		String processInstanceId = this.getRequest().getParameter("pid");
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());

		List<String> highLightedActivities = new ArrayList<>();

		Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();// 执行实例
		Object property = PropertyUtils.getProperty(execution, "activityId");
		String activityId = "";
		if (property != null) {
			activityId = property.toString();
		}
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		List<ActivityImpl> activitiList = processDefinition.getActivities();// 获得当前任务的所有节点

		for (ActivityImpl activity : activitiList) {
			String id = activity.getId();
			// 当前节点
			if (id.equals(activityId)) {
				highLightedActivities.add(id);
			}
		}

		InputStream resourceAsStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivities);
		byte[] b = new byte[1024];
		int len = -1;
		try {
			while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
				this.getResponse().getOutputStream().write(b, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void trace() throws Exception {
		String processInstanceId = this.getRequest().getParameter("pid");
		Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();// 执行实例
		Object property = PropertyUtils.getProperty(execution, "activityId");
		String activityId = "";
		if (property != null) {
			activityId = property.toString();
		}
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		List<ActivityImpl> activitiList = processDefinition.getActivities();// 获得当前任务的所有节点

		// 历史
		List<String> hisActivityIds = new ArrayList<>();
		List<HistoricActivityInstance> hisActivityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
		for (HistoricActivityInstance hai : hisActivityInstanceList) {
			if (hai.getEndTime() != null) {
				hisActivityIds.add(hai.getActivityId());
			}
		}

		List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		for (ActivityImpl activity : activitiList) {
			boolean currentActiviti = false;
			String id = activity.getId();
			// 当前节点
			if (id.equals(activityId)) {
				currentActiviti = true;
			}

			boolean inHistory = hisActivityIds.contains(id);

			Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, processInstance, currentActiviti, inHistory);

			activityInfos.add(activityImageInfo);

			if (activity.getX() < minX) {
				minX = activity.getX();
			}
			if (activity.getY() < minY) {
				minY = activity.getY();
			}
		}
		minX = minX - 5;
		minY = minY - 5;

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("top", -minY);
		resultMap.put("left", -minX);
		resultMap.put("infos", activityInfos);

		this.print(JSONObject.fromObject(resultMap).toString());
	}

	/**
	 * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
	 * 
	 * @param activity
	 * @param processInstance
	 * @param currentActiviti
	 * @param inHistory
	 * @return
	 */
	private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance, boolean currentActiviti, boolean inHistory) throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		Map<String, Object> activityInfo = new HashMap<String, Object>();
		activityInfo.put("currentActiviti", currentActiviti);
		activityInfo.put("inHistory", inHistory);
		activityInfo.put("x", activity.getX());
		activityInfo.put("y", activity.getY());
		activityInfo.put("width", activity.getWidth());
		activityInfo.put("height", activity.getHeight());

		Map<String, Object> properties = activity.getProperties();
		vars.put("任务类型", WorkflowUtil.parseToZhType(properties.get("type").toString()));

		ActivityBehavior activityBehavior = activity.getActivityBehavior();
		if (activityBehavior instanceof UserTaskActivityBehavior) {
			Task currentTask = null;

			/*
			 * 当前节点的task
			 */
			if (currentActiviti) {
				String activitiId = (String) PropertyUtils.getProperty(processInstance, "activityId");
				currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey(activitiId).singleResult();
			}

			/*
			 * 当前任务的分配角色
			 */
			UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
			TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
			Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
			if (!candidateGroupIdExpressions.isEmpty()) {

				// 任务的处理角色
				String roles = "";
				for (Expression expression : candidateGroupIdExpressions) {
					String expressionText = expression.getExpressionText();
					if (StringUtils.isBlank(expressionText)) {
						continue;
					}
					String roleName = identityService.createGroupQuery().groupType(expressionText).singleResult().getName();
					roles += roleName;
				}
				vars.put("任务所属角色", roles);

				// 当前处理人
				if (currentTask != null) {
					String assignee = currentTask.getAssignee();
					if (assignee != null) {
						org.activiti.engine.identity.User assigneeUser = identityService.createUserQuery().userId(assignee).singleResult();
						String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
						vars.put("当前处理人", userInfo);
					}
				}
			}
		}

		vars.put("节点说明", properties.get("documentation"));

		String description = activity.getProcessDefinition().getDescription();
		vars.put("描述", description);

		activityInfo.put("vars", vars);
		return activityInfo;
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

package com.hzth.myapp.leave.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzth.myapp.core.util.DateUtil;
import com.hzth.myapp.leave.dao.LeaveDao;
import com.hzth.myapp.leave.model.Leave;
import com.hzth.myapp.user.util.UserUtil;

@Service
@Transactional
public class LeaveService {

	@Autowired
	private LeaveDao leaveDao;

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

	public void saveApplicant(String reason) {
		Leave leave = new Leave();
		leave.setApplicantDate(DateUtil.getCurrentDate());
		leave.setApplicantUser(UserUtil.getCurrentUser());
		leave.setSickLeave("0");
		leave.setReason(reason);
		leaveDao.saveOrUpdate(leave);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("leave", leave.getId());
		leave.setProcessInstanceId(instance.getId());
		leaveDao.saveOrUpdate(leave);
	}

	public List<Leave> getListWithAppId(String userId) {
		return leaveDao.findByHql("from Leave where applicantUser.id = ? order by applicantDate desc", userId);
	}

	public Leave getLeaveWithProcessInstanceId(String processInstanceId) {
		return leaveDao.findByHql("from Leave where processInstanceId = ? ", processInstanceId).get(0);
	}

	public Leave get(String id) {
		return leaveDao.get(id);
	}

	public void deptComplate(String id, String deptSuggestion, String result) {
		Leave leave = leaveDao.get(id);
		leave.setDeptSuggestion(deptSuggestion);
		leave.setDeptDate(DateUtil.getCurrentDate());
		leave.setDeptUser(UserUtil.getCurrentUser());
		leaveDao.saveOrUpdate(leave);
		String taskId = taskService.createTaskQuery().processInstanceBusinessKey(id).singleResult().getId();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("deptUserPass", result.equals("0"));
		variables.put("applyUserId", leave.getApplicantUser().getId());
		taskService.complete(taskId, variables);
	}

	public void leaderComplate(String id, String leaderSuggestion, String result) {
		Leave leave = leaveDao.get(id);
		leave.setLeader(UserUtil.getCurrentUser());
		leave.setLeaderDate(DateUtil.getCurrentDate());
		leave.setLeaderSuggestion(leaderSuggestion);
		leaveDao.saveOrUpdate(leave);
		String taskId = taskService.createTaskQuery().processInstanceBusinessKey(id).singleResult().getId();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("leaderPass", result.equals("0"));
		variables.put("applyUserId", leave.getApplicantUser().getId());
		taskService.complete(taskId, variables);
	}

	public List<Leave> getAll() {
		return leaveDao.findAll();
	}

	public void userComplate(String id) {
		Leave leave = leaveDao.get(id);
		leave.setSickLeave("1");
		leaveDao.saveOrUpdate(leave);
		String taskId = taskService.createTaskQuery().processInstanceBusinessKey(id).singleResult().getId();
		taskService.complete(taskId);
	}

	public void saveReApp(String id, String reason, String result) {
		String taskId = taskService.createTaskQuery().processInstanceBusinessKey(id).singleResult().getId();
		if (result.equals("1")) {// 放弃申请
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("reApply", false);
			taskService.complete(taskId, variables);
		} else {
			Leave leave = leaveDao.get(id);
			leave.setReason(reason);
			leaveDao.saveOrUpdate(leave);
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("reApply", true);
			taskService.complete(taskId, variables);
		}
	}

	public void deleteDeployment(String deploymentId) {
		// Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).list();
		for (ProcessDefinition pd : processDefinitions) {
			long count = runtimeService.createProcessInstanceQuery().processDefinitionId(pd.getId()).count();
			if (count > 0) {
				// throw new RuntimeException("此版本已有申请存在，不能删除！");
			}
			// TODO 历史信息未验证
		}
		repositoryService.deleteDeployment(deploymentId, true);
	}
}

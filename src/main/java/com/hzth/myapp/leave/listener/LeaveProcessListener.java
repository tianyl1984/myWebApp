package com.hzth.myapp.leave.listener;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hzth.myapp.leave.model.Leave;
import com.hzth.myapp.leave.service.LeaveService;

@Component
@Transactional
public class LeaveProcessListener implements TaskListener {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private LeaveService leaveService;

	@Autowired
	private TaskService taskService;

	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println("EventName:" + delegateTask.getEventName());
		System.out.println("TaskDefinitionKey:" + delegateTask.getTaskDefinitionKey());
		System.out.println("Name:" + delegateTask.getName());
		String processInstanceId = delegateTask.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if (processInstance != null) {
			Leave leave = leaveService.get(processInstance.getBusinessKey());
			System.out.println("LeaveReason:" + leave.getReason());
		} else {
			System.out.println("ProcessInstance is null");
		}
	}

}

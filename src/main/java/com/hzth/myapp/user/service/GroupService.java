package com.hzth.myapp.user.service;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzth.myapp.user.dao.GroupDAO;
import com.hzth.myapp.user.model.Group;
import com.hzth.myapp.user.model.User;
import com.hzth.myapp.user.util.UserUtil;

@Service
@Transactional
public class GroupService {

	@Autowired
	private GroupDAO groupDAO;
	@Autowired
	private IdentityService identityService;

	public List<Group> findGroupByUser(User user) {
		return groupDAO.findGroupByUser(user);
	}

	public List<Group> getAll() {
		return groupDAO.findAll();
	}

	public Group get(String id) {
		return groupDAO.get(id);
	}

	public void saveOrUpdate(Group group) {
		List<String> oldUserIds = new ArrayList<String>();
		if (StringUtils.isNotBlank(group.getId())) {
			Group oldGroup = groupDAO.get(group.getId());
			for (User user : oldGroup.getUsers()) {
				oldUserIds.add(user.getId());
			}
		}
		groupDAO.saveOrUpdate(group);
		if (UserUtil.synUserInfo()) {
			String groupId = group.getId();
			Long count = identityService.createGroupQuery().groupId(groupId).count();
			if (count > 0) {// 更新
				org.activiti.engine.identity.Group activitiGroup = identityService.createGroupQuery().groupId(groupId).singleResult();
				activitiGroup.setName(group.getName());
				activitiGroup.setType(group.getCode());
				identityService.saveGroup(activitiGroup);
				// 更新user
				for (String userId : oldUserIds) {
					identityService.deleteMembership(userId, groupId);
				}
				for (User user : group.getUsers()) {
					identityService.createMembership(user.getId(), groupId);
				}
			} else {// 添加
				org.activiti.engine.identity.Group activitiGroup = identityService.newGroup(groupId);
				activitiGroup.setName(group.getName());
				activitiGroup.setType(group.getCode());
				identityService.saveGroup(activitiGroup);
			}
		}
	}

	public void delete(String id) {
		if (UserUtil.synUserInfo()) {
			Group group = groupDAO.get(id);
			for (User user : group.getUsers()) {
				identityService.deleteMembership(user.getId(), id);
			}
			identityService.deleteGroup(id);
		}
		groupDAO.delete(id);
	}

	public void empty() {

	}
}

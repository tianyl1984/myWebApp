package com.hzth.myapp.user.service;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzth.myapp.user.dao.GroupDAO;
import com.hzth.myapp.user.dao.UserDAO;
import com.hzth.myapp.user.model.Group;
import com.hzth.myapp.user.model.User;
import com.hzth.myapp.user.util.UserUtil;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private GroupDAO groupDAO;

	@Autowired
	private IdentityService identityService;

	public List<User> findAll() {
		// return userDAO.findAllBySql();
		return userDAO.findAll();
	}

	public User get(String id) {
		return userDAO.get(id);
	}

	public void saveOrUpdate(User user) {
		userDAO.saveOrUpdate(user);
		if (UserUtil.synUserInfo()) {
			String userId = user.getId();
			List<org.activiti.engine.identity.User> activitiUsers = identityService.createUserQuery().userId(userId).list();
			if (activitiUsers.size() == 1) {
				// 更新信息
				org.activiti.engine.identity.User activitiUser = activitiUsers.get(0);
				activitiUser.setFirstName(user.getName());
				activitiUser.setLastName("");
				activitiUser.setPassword("");
				activitiUser.setEmail(user.getEmail());
				identityService.saveUser(activitiUser);
			} else {
				org.activiti.engine.identity.User newUser = identityService.newUser(userId);
				newUser.setFirstName(user.getName());
				newUser.setLastName("");
				newUser.setPassword("");
				newUser.setEmail(user.getEmail());
				identityService.saveUser(newUser);
			}
		}
	}

	public void delete(String id) {
		User user = userDAO.get(id);
		List<Group> groups = groupDAO.findGroupByUser(user);
		for (Group group : groups) {
			group.getUsers().remove(user);
			groupDAO.saveOrUpdate(group);
		}
		userDAO.delete(id);
		if (UserUtil.synUserInfo()) {
			// 删除用户的membership
			List<org.activiti.engine.identity.Group> activitiGroups = identityService.createGroupQuery().groupMember(id).list();
			for (org.activiti.engine.identity.Group group : activitiGroups) {
				identityService.deleteMembership(id, group.getId());
			}
			identityService.deleteUser(id);
		}
	}

	@Autowired
	private GroupService groupService;

	// @Transactional(propagation = Propagation.SUPPORTS)
	public void empty() {
		for (int i = 0; i < 1000; i++) {
			groupService.empty();
		}
	}

}

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

	public User getUserByName(String name) {
		return userDAO.getUserByName(name);
	}

	public void initData() {
		User u1 = new User();
		u1.setName("u1");
		saveOrUpdate(u1);

		User u2 = new User();
		u2.setName("u2");
		saveOrUpdate(u2);

		User u3 = new User();
		u3.setName("u3");
		saveOrUpdate(u3);

		Group g1 = new Group();
		g1.setName("普通职员");
		g1.setCode("g1");
		g1.addUser(u1);
		g1.addUser(u2);
		g1.addUser(u3);
		groupService.saveOrUpdate(g1);

		Group g2 = new Group();
		g2.setName("部门经理");
		g2.setCode("g2");
		g2.addUser(u2);
		groupService.saveOrUpdate(g2);

		Group g3 = new Group();
		g3.setName("HR经理");
		g3.setCode("g3");
		g3.addUser(u3);
		groupService.saveOrUpdate(g3);

	}

}

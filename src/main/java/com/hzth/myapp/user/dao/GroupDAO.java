package com.hzth.myapp.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hzth.myapp.core.dao.HibernateDAO;
import com.hzth.myapp.user.model.Group;
import com.hzth.myapp.user.model.User;

@Repository
public class GroupDAO extends HibernateDAO<Group> {

	public List<Group> findGroupByUser(User user) {
		String hql = "from Group g where ? in elements(g.users)";
		return this.findByHql(hql, user);
	}

}

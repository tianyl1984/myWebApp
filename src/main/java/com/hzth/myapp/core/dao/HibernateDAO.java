package com.hzth.myapp.core.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzth.myapp.core.util.ReflectUtil;
import com.hzth.myapp.core.util.UUID;

public class HibernateDAO<T> {
	@Autowired
	protected SessionFactory sessionFactory;

	@SuppressWarnings("rawtypes")
	protected Class entityClass;

	public HibernateDAO() {
		this.entityClass = ReflectUtil.getSuperClassGenricType(getClass());
	}

	@SuppressWarnings("unchecked")
	public T get(String id) {
		return (T) sessionFactory.getCurrentSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(entityClass).list();
	}

	public void saveOrUpdate(T t) {
		Session session = sessionFactory.getCurrentSession();
		if (StringUtils.isBlank((String) ReflectUtil.getFieldValue(t, "id"))) {
			ReflectUtil.setFieldValue(t, "id", UUID.getUUID());
			session.save(t);
		} else {
			T oldT = this.get((String) ReflectUtil.getFieldValue(t, "id"));
			BeanUtils.copyProperties(t, oldT);
			session.update(oldT);
		}
	}

	public void delete(String id) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(get(id));
	}

	@SuppressWarnings("unchecked")
	public List<T> findByHql(String hql, Object... values) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.list();
	}
}

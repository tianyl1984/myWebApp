package com.hzth.myapp.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hzth.myapp.core.dao.HibernateDAO;
import com.hzth.myapp.user.model.User;

@Repository
public class UserDAO extends HibernateDAO<User> {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<User> findAllBySql() {
		// jdbcTemplate.execute("select *,(2) from tb_user");
		return jdbcTemplate.query("select *,(1) from tb_user", new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new User(rs.getString("id"), rs.getString("name"), rs.getString("email"));
			}
		});
	}

}

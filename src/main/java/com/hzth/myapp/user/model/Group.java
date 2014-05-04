package com.hzth.myapp.user.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_group")
public class Group implements Serializable {

	private static final long serialVersionUID = 4627915355143624599L;

	@Id
	@GeneratedValue(generator = "gen_uuid")
	@GenericGenerator(name = "gen_uuid", strategy = "uuid")
	private String id;

	private String name;

	private String code;

	@ManyToMany(targetEntity = com.hzth.myapp.user.model.User.class)
	@JoinTable(name = "tb_group_user", joinColumns = { @JoinColumn(referencedColumnName = "id", name = "id_group") }, inverseJoinColumns = { @JoinColumn(referencedColumnName = "id", name = "id_user") })
	private Set<User> users = new HashSet<>();

	public Group() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void addUser(User user) {
		this.users.add(user);
	}

}

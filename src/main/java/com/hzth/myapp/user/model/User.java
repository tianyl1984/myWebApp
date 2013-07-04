package com.hzth.myapp.user.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_user")
public class User extends Teacher implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "gen_uuid")
	@GenericGenerator(name = "gen_uuid", strategy = "uuid")
	private String id;

	private String name;

	private String email;

	private Float score;

	@Transient
	private Teacher teacher;

	@Transient
	private User parent;

	@Transient
	private List<User> users = new ArrayList<User>();

	@Transient
	private User[] users2 = new User[2];

	@Transient
	private int i1;

	@Transient
	private Integer i2;

	@Transient
	private boolean male;

	public User() {
	}

	public User(String id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public User getParent() {
		return parent;
	}

	public void setParent(User parent) {
		this.parent = parent;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public void addUser(User u) {
		users.add(u);
	}

	public User[] getUsers2() {
		return users2;
	}

	public void setUsers2(User[] users2) {
		this.users2 = users2;
	}

	public int getI1() {
		return i1;
	}

	public void setI1(int i1) {
		this.i1 = i1;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Integer getI2() {
		return i2;
	}

	public void setI2(Integer i2) {
		this.i2 = i2;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return id + ":" + name + ":" + email;
	}
}

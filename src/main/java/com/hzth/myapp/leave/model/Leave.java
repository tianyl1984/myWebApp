package com.hzth.myapp.leave.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hzth.myapp.user.model.User;

@Entity
@Table(name = "tb_leave")
public class Leave implements Serializable {

	private static final long serialVersionUID = -2441640136247961605L;

	@Id
	@GeneratedValue(generator = "gen_uuid")
	@GenericGenerator(name = "gen_uuid", strategy = "uuid")
	private String id;

	private String reason;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = true)
	@JoinColumn(name = "id_applicantuser")
	private User applicantUser;

	private String applicantDate;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = true)
	@JoinColumn(name = "id_deptuser")
	private User deptUser;

	private String deptSuggestion;

	private String deptDate;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = true)
	@JoinColumn(name = "id_leader")
	private User leader;

	private String leaderSuggestion;

	private String leaderDate;

	private String processInstanceId;

	private String sickLeave;

	public Leave() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public User getApplicantUser() {
		return applicantUser;
	}

	public void setApplicantUser(User applicantUser) {
		this.applicantUser = applicantUser;
	}

	public String getApplicantDate() {
		return applicantDate;
	}

	public void setApplicantDate(String applicantDate) {
		this.applicantDate = applicantDate;
	}

	public User getDeptUser() {
		return deptUser;
	}

	public void setDeptUser(User deptUser) {
		this.deptUser = deptUser;
	}

	public String getDeptSuggestion() {
		return deptSuggestion;
	}

	public void setDeptSuggestion(String deptSuggestion) {
		this.deptSuggestion = deptSuggestion;
	}

	public String getDeptDate() {
		return deptDate;
	}

	public void setDeptDate(String deptDate) {
		this.deptDate = deptDate;
	}

	public User getLeader() {
		return leader;
	}

	public void setLeader(User leader) {
		this.leader = leader;
	}

	public String getLeaderSuggestion() {
		return leaderSuggestion;
	}

	public void setLeaderSuggestion(String leaderSuggestion) {
		this.leaderSuggestion = leaderSuggestion;
	}

	public String getLeaderDate() {
		return leaderDate;
	}

	public void setLeaderDate(String leaderDate) {
		this.leaderDate = leaderDate;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getSickLeave() {
		return sickLeave;
	}

	public void setSickLeave(String sickLeave) {
		this.sickLeave = sickLeave;
	}

}

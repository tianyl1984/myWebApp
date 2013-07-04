package com.hzth.myapp.sql;

public class HScore {

	private String id;

	private String deptId;

	private String courseId;

	private String examId;

	private String gradeExamId;

	private String entryscoreTeacherId;

	private int gradeNum;

	private Integer score;

	private Integer firstPartScore;

	private Integer secondPartScore;

	private String status;

	private String absentFlag;

	private String ts;

	private String df;

	private String sbaseinfoId;

	private String eclassId;

	private String gradeId;

	private String tbaseinfoId;

	public HScore() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSbaseinfoId() {
		return sbaseinfoId;
	}

	public void setSbaseinfoId(String sbaseinfoId) {
		this.sbaseinfoId = sbaseinfoId;
	}

	public String getEclassId() {
		return eclassId;
	}

	public void setEclassId(String eclassId) {
		this.eclassId = eclassId;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getTbaseinfoId() {
		return tbaseinfoId;
	}

	public void setTbaseinfoId(String tbaseinfoId) {
		this.tbaseinfoId = tbaseinfoId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getGradeExamId() {
		return gradeExamId;
	}

	public void setGradeExamId(String gradeExamId) {
		this.gradeExamId = gradeExamId;
	}

	public String getEntryscoreTeacherId() {
		return entryscoreTeacherId;
	}

	public void setEntryscoreTeacherId(String entryscoreTeacherId) {
		this.entryscoreTeacherId = entryscoreTeacherId;
	}

	public int getGradeNum() {
		return gradeNum;
	}

	public void setGradeNum(int gradeNum) {
		this.gradeNum = gradeNum;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getFirstPartScore() {
		return firstPartScore;
	}

	public void setFirstPartScore(Integer firstPartScore) {
		this.firstPartScore = firstPartScore;
	}

	public Integer getSecondPartScore() {
		return secondPartScore;
	}

	public void setSecondPartScore(Integer secondPartScore) {
		this.secondPartScore = secondPartScore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAbsentFlag() {
		return absentFlag;
	}

	public void setAbsentFlag(String absentFlag) {
		this.absentFlag = absentFlag;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getDf() {
		return df;
	}

	public void setDf(String df) {
		this.df = df;
	}

	@Override
	public String toString() {
		return "id:" + id + ",sbaseinfoId:" + sbaseinfoId + ",eclassId:" + eclassId + ",gradeId:" + gradeId + ",tbaseinfoId:" + tbaseinfoId;
	}
}

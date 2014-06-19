package com.hzth.myapp.dc;

import java.util.ArrayList;
import java.util.List;

public class Question {

	private String id;

	private String parentId;

	private String kind;

	private List<String> optionIds = new ArrayList<>();

	private List<String> childrenQuestionIds = new ArrayList<>();

	private String name;

	public Question() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOneOptionsId() {
		return optionIds.get(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void addOptionId(String optionId) {
		optionIds.add(optionId);
	}

	public List<String> getOptionIds() {
		return optionIds;
	}

	public void setOptionIds(List<String> optionIds) {
		this.optionIds = optionIds;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<String> getChildrenQuestionIds() {
		return childrenQuestionIds;
	}

	public void setChildrenQuestionIds(List<String> childrenQuestionIds) {
		this.childrenQuestionIds = childrenQuestionIds;
	}

	public void addChildrenQuestionId(String childrenQuestionId) {
		this.childrenQuestionIds.add(childrenQuestionId);
	}
}

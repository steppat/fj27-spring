package br.com.caelum.forum.topic.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.caelum.forum.model.Category;

public class DashboardDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String categoryName;
	private List<Category> subcategories = new ArrayList<>();
	private Integer allTopics;
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<String> getSubcategories() {
		return subcategories.stream().map(c -> c.getName()).collect(Collectors.toList());
	}

	public void setSubcategories(List<Category> subcategories) {
		this.subcategories = subcategories;
	}

	public Integer getAllTopics() {
		return allTopics;
	}

	public void setAllTopics(Integer allTopics) {
		this.allTopics = allTopics;
	}
	
	
	
	
}

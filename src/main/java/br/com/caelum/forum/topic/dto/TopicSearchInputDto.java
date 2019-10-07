package br.com.caelum.forum.topic.dto;

import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.caelum.forum.model.Topic;
import br.com.caelum.forum.model.TopicStatus;

public class TopicSearchInputDto {

	private TopicStatus status;
	private String categoryName;

	@SuppressWarnings("serial")
	public Specification<Topic> build() {
		return new Specification<Topic>() {

			public Predicate toPredicate(Root<Topic> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				ArrayList<Predicate> predicates = new ArrayList<>();

				if (status != null) {
					Path<Object> caminhoStatus = root.get("status");
					predicates.add(criteriaBuilder.equal(caminhoStatus, status));
				}

				if (categoryName != null) {
					Path<String> categoryNamePath = root.get("course").get("subcategory").get("category").get("name");
					predicates.add(criteriaBuilder.equal(categoryNamePath, categoryName));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}

		};
	}

	public TopicStatus getStatus() {
		return status;
	}

	public void setStatus(TopicStatus status) {
		this.status = status;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	

}

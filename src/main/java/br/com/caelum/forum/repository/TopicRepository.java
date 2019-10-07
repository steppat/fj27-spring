package br.com.caelum.forum.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.caelum.forum.model.Category;
import br.com.caelum.forum.model.Course;
import br.com.caelum.forum.model.OpenTopicByCategory;
import br.com.caelum.forum.model.Topic;
import br.com.caelum.forum.model.User;

public interface TopicRepository extends JpaRepository<Topic,Long> , JpaSpecificationExecutor<Topic>{
	
	@Query("select t from Topic t")
	List<Topic> list();
	
	List<Topic> findByCourse(Course c);
	
	Topic getOne(Long id);
	
	List<Topic> findAll();

	List<Topic> findByShortDescription(String description);
	
	Integer countByCourseSubcategoryCategory(Category c);

	List<Topic> findByOwnerAndCreationInstantAfterOrderByCreationInstantAsc(User loggedUser, Instant oneHourAgo);
	
	@Query("select new br.com.caelum.forum.model.OpenTopicByCategory(" +
			"t.course.subcategory.category.name as categoryName, " +
			"count(t) as topicCount, " +
			"now() as instant) from Topic t " +
			"where t.status = 'NOT_ANSWERED' " +
			"group by t.course.subcategory.category")
	List<OpenTopicByCategory> findOpenTopicsByCategory();
	
}

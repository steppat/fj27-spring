package br.com.caelum.forum.repository;

import org.springframework.data.repository.Repository;

import br.com.caelum.forum.model.Course;

public interface CourseRepository extends Repository<Course, Long> {

	public Course findByNameStartsWith(String courseName);
	

}

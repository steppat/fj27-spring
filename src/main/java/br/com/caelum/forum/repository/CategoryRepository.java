package br.com.caelum.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caelum.forum.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
	
	List<Category> findByCategoryIsNull();
	
	
	List<Category> findByCategory(Category c);
	
	
	
}

package com.ccsw.tutorialcategory.category;

import com.ccsw.tutorialcategory.category.model.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * @author emlloren
 *
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
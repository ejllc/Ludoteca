package com.ccsw.tutorialcategory.category;

import com.ccsw.tutorialcategory.category.model.Category;
import com.ccsw.tutorialcategory.category.model.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author emlloren
 *
 */
@Tag(name = "Category", description = "API of Category")
@RequestMapping(value = "/category")
@RestController
@CrossOrigin(origins = "*")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	DozerBeanMapper mapper;

	/**
	 * Método para recuperar todas las {@link Category}
	 *
	 * @return {@link List} de {@link CategoryDto}
	 */
	@Operation(summary = "Find", description = "Method that return a list of Categories")
	@GetMapping("")
	public List<CategoryDto> findAll() {

		List<Category> categories = this.categoryService.findAll();

		return categories.stream().map(e -> mapper.map(e, CategoryDto.class)).collect(Collectors.toList());
	}

	/**
	 * Método para crear o actualizar una {@link Category}
	 *
	 * @param id  PK de la entidad
	 * @param dto datos de la entidad
	 */
	@Operation(summary = "Save or Update", description = "Method that saves or updates a Category")
	@PutMapping({ "", "/{id}" })
	public void save(@PathVariable(required = false) Long id, @RequestBody CategoryDto dto) {

		this.categoryService.save(id, dto);
	}

	/**
	 * Método para borrar una {@link Category}
	 *
	 * @param id PK de la entidad
	 */
	@Operation(summary = "Delete", description = "Method that deletes a Category")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) throws Exception {

		this.categoryService.delete(id);
	}

}
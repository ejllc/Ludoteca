package com.ccsw.tutorialauthor.author;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorialauthor.author.model.Author;
import com.ccsw.tutorialauthor.author.model.AuthorDto;
import com.ccsw.tutorialauthor.author.model.AuthorSearchDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author emlloren
 *
 */
@Tag(name = "Author", description = "API of Author")
@RequestMapping(value = "/author")
@RestController
@CrossOrigin(origins = "*")
public class AuthorController {

	@Autowired
	AuthorService authorService;

	@Autowired
	DozerBeanMapper mapper;

	/**
	 * Método para recuperar un listado paginado de {@link Author}
	 *
	 * @param dto dto de búsqueda
	 * @return {@link Page} de {@link AuthorDto}
	 */
	@Operation(summary = "Find Page", description = "Method that return a page of Authors")
	@PostMapping("")
	public Page<AuthorDto> findPage(@RequestBody AuthorSearchDto dto) {

		Page<Author> page = this.authorService.findPage(dto);

		return new PageImpl<>(
				page.getContent().stream().map(e -> mapper.map(e, AuthorDto.class)).collect(Collectors.toList()),
				page.getPageable(), page.getTotalElements());
	}

	/**
	 * Método para crear o actualizar un {@link Author}
	 *
	 * @param id  PK de la entidad
	 * @param dto datos de la entidad
	 */
	@Operation(summary = "Save or Update", description = "Method that saves or updates a Author")
	@PutMapping({ "", "/{id}" })
	public void save(@PathVariable(required = false) Long id, @RequestBody AuthorDto dto) {

		this.authorService.save(id, dto);
	}

	/**
	 * Método para crear o actualizar un {@link Author}
	 *
	 * @param id PK de la entidad
	 */
	@Operation(summary = "Delete", description = "Method that deletes a Author")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) throws Exception {

		this.authorService.delete(id);
	}

	/**
	 * Recupera un listado de autores {@link Author}
	 *
	 * @return {@link List} de {@link AuthorDto}
	 */
	@Operation(summary = "Find", description = "Method that return a list of Authors")
	@GetMapping("")
	public List<AuthorDto> findAll() {

		List<Author> authors = this.authorService.findAll();

		return authors.stream().map(e -> mapper.map(e, AuthorDto.class)).collect(Collectors.toList());
	}

}
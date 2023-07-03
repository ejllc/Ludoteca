package com.css.tutorialclient.client;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.css.tutorialclient.client.model.Client;
import com.css.tutorialclient.client.model.ClientDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author emlloren
 *
 */
@Tag(name = "Client", description = "API of Client")
@RequestMapping(value = "/client")
@RestController
@CrossOrigin(origins = "*")
public class ClientController {

	@Autowired
	ClientService clientService;

	@Autowired
	DozerBeanMapper mapper;

	/**
	 * Método para recuperar todos los {@link Client}
	 *
	 * @return {@link List} de {@link ClientDto}
	 */
	@Operation(summary = "Find", description = "Method that returns a list of Clients")
	@GetMapping("")
	public List<ClientDto> findAll() {

		List<Client> clients = this.clientService.findAll();

		return clients.stream().map(e -> mapper.map(e, ClientDto.class)).collect(Collectors.toList());
	}

	/**
	 * Método para crear o actualizar un {@link Client}
	 *
	 * @param id  PK de la entidad
	 * @param dto datos de la entidad
	 */
	@Operation(summary = "Save or Update", description = "Method that saves or updates a Client")
	@PutMapping({ "", "/{id}" })
	public void save(@PathVariable(required = false) Long id, @RequestBody ClientDto dto) {

		this.clientService.save(id, dto);
	}

	/**
	 * Método para borrar un {@link Client}
	 *
	 * @param id PK de la entidad
	 */
	@Operation(summary = "Delete", description = "Method that deletes a Client")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) throws Exception {

		this.clientService.delete(id);
	}

}

package com.css.tutorialclient.client;

import java.util.List;

import com.css.tutorialclient.client.model.Client;
import com.css.tutorialclient.client.model.ClientDto;

/**
 * @author emlloren
 *
 */
public interface ClientService {

	/**
	 * Recupera un {@link Client} a partir de su ID
	 *
	 * @param id PK de la entidad
	 * @return {@link Client}
	 */
	Client get(Long id);

	/**
	 * Método para recuperar todos los {@link Client}
	 *
	 * @return {@link List} de {@link Client}
	 */
	List<Client> findAll();

	/**
	 * Método para crear o actualizar un {@link Client}
	 *
	 * @param id  PK de la entidad
	 * @param dto datos de la entidad
	 */
	void save(Long id, ClientDto dto);

	/**
	 * Método para borrar un {@link Client}
	 *
	 * @param id PK de la entidad
	 * @throws Exception si ocurre algún error al borrar el cliente
	 */
	void delete(Long id) throws Exception;

}

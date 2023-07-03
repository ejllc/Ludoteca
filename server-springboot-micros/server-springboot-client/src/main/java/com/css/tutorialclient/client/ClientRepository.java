package com.css.tutorialclient.client;

import org.springframework.data.repository.CrudRepository;

import com.css.tutorialclient.client.model.Client;

/**
 * @author emlloren
 *
 */
public interface ClientRepository extends CrudRepository<Client, Long> {

}

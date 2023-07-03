/**
 * 
 */
package com.css.tutorialclient.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.css.tutorialclient.client.model.Client;
import com.css.tutorialclient.client.model.ClientDto;

import jakarta.transaction.Transactional;

/**
 * @author emlloren
 *
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository clientRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Client get(Long id) {
		return this.clientRepository.findById(id).orElse(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Client> findAll() {
		return (List<Client>) this.clientRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(Long id, ClientDto dto) {
		Client client;
		if (id == null) {
			client = new Client();
		} else {
			client = this.get(id);
		}

		client.setName(dto.getName());

		this.clientRepository.save(client);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Long id) throws Exception {
		if (this.get(id) == null) {
			throw new Exception("Client does not exist");
		}

		this.clientRepository.deleteById(id);
	}
}

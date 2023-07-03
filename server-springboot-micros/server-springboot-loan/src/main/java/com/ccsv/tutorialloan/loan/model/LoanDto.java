package com.ccsv.tutorialloan.loan.model;

import java.util.Date;

import com.ccsv.tutorialloan.client.model.ClientDto;
import com.ccsv.tutorialloan.game.model.GameDto;

/**
 * @author emlloren
 *
 */
public class LoanDto {

	private Long id;
	private GameDto game;
	private ClientDto client;
	private Date startDate;
	private Date endDate;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the game
	 */
	public GameDto getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(GameDto game) {
		this.game = game;
	}

	/**
	 * @return the client
	 */
	public ClientDto getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(ClientDto client) {
		this.client = client;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
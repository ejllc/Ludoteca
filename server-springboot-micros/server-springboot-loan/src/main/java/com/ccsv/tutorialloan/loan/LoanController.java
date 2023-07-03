package com.ccsv.tutorialloan.loan;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsv.tutorialloan.client.ClientClient;
import com.ccsv.tutorialloan.client.model.ClientDto;
import com.ccsv.tutorialloan.game.GameClient;
import com.ccsv.tutorialloan.game.model.GameDto;
import com.ccsv.tutorialloan.loan.model.Loan;
import com.ccsv.tutorialloan.loan.model.LoanDto;
import com.ccsv.tutorialloan.loan.model.LoanSearchDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author emlloren
 *
 */
@Tag(name = "Loan", description = "API of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

	@Autowired
	LoanService loanService;

	@Autowired
	GameClient gameClient;

	@Autowired
	ClientClient clientClient;

	@Autowired
	DozerBeanMapper mapper;

	@Operation(summary = "Find", description = "Method that returns a list of Loans")
	@GetMapping("")
	public List<LoanDto> find(@RequestParam(required = false) Long gameId,
			@RequestParam(required = false) Long clientId, @RequestParam(required = false) Date date) {

		List<GameDto> games = gameClient.findAll();
		List<ClientDto> clients = clientClient.findAll();

		return loanService.find(gameId, clientId, date).stream().map(loan -> {
			LoanDto loanDto = new LoanDto();

			loanDto.setId(loan.getId());
			loanDto.setGame(
					games.stream().filter(game -> game.getId().equals(loan.getIdGame())).findFirst().orElse(null));
			loanDto.setClient(clients.stream().filter(client -> client.getId().equals(loan.getIdClient())).findFirst()
					.orElse(null));
			loanDto.setStartDate(loan.getStartDate());
			loanDto.setEndDate(loan.getEndDate());

			return loanDto;
		}).collect(Collectors.toList());
	}

	@Operation(summary = "Find Page", description = "Method that returns a page of Loans")
	@PostMapping("")
	public Page<LoanDto> findPage(@RequestBody LoanSearchDto dto) {
		Page<Loan> page = loanService.findPage(dto);

		List<GameDto> games = gameClient.findAll();
		List<ClientDto> clients = clientClient.findAll();

		List<LoanDto> loanDtos = page.getContent().stream().filter(loan -> {
			boolean clientMatch = dto.getClientId() == null || loan.getIdClient().equals(dto.getClientId());
			boolean gameMatch = dto.getGameId() == null || loan.getIdGame().equals(dto.getGameId());
			boolean dateMatch = dto.getDate() == null || (loan.getStartDate().compareTo(dto.getDate()) <= 0
					&& loan.getEndDate().compareTo(dto.getDate()) >= 0);

			return clientMatch && gameMatch && dateMatch;
		}).map(loan -> {
			LoanDto loanDto = new LoanDto();
			loanDto.setId(loan.getId());
			loanDto.setGame(
					games.stream().filter(game -> game.getId().equals(loan.getIdGame())).findFirst().orElse(null));
			loanDto.setClient(clients.stream().filter(client -> client.getId().equals(loan.getIdClient())).findFirst()
					.orElse(null));
			loanDto.setStartDate(loan.getStartDate());
			loanDto.setEndDate(loan.getEndDate());
			return loanDto;
		}).collect(Collectors.toList());

		return new PageImpl<>(loanDtos, page.getPageable(), page.getTotalElements());
	}

	@Operation(summary = "Save", description = "Method that creates a new Loan")
	@PutMapping("")
	public void saveLoan(@RequestBody LoanDto loanDto) {
		loanService.save(loanDto.getId(), loanDto);
	}

	@Operation(summary = "Delete", description = "Method that deletes a Loan")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		loanService.delete(id);
	}
}

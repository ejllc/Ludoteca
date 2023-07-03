package com.ccsv.tutorialloan.loan;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ccsv.tutorialloan.loan.model.Loan;
import com.ccsv.tutorialloan.loan.model.LoanDto;
import com.ccsv.tutorialloan.loan.model.LoanSearchDto;

/**
 * @author emlloren
 *
 */
public interface LoanService {

	/**
	 * Recupera un {@link Loan} a través de su ID
	 *
	 * @param id PK de la entidad
	 * @return {@link Loan}
	 */
	Loan get(Long id);

	/**
	 * Método para recuperar un listado paginado de {@link Loan}
	 *
	 * @param dto dto de búsqueda
	 * @return {@link Page} de {@link Loan}
	 */
	Page<Loan> findPage(LoanSearchDto dto);

	/**
	 * Recupera los préstamos filtrando opcionalmente por juego, cliente y/o fecha
	 *
	 * @param gameId   ID del juego
	 * @param clientId ID del cliente
	 * @param date     Fecha para filtrar los préstamos
	 * @return Lista de préstamos
	 */
	List<Loan> find(Long gameId, Long clientId, Date date);

	/**
	 * Guarda o modifica un préstamo, dependiendo de si el identificador está o no
	 * informado
	 *
	 * @param id  ID del préstamo
	 * @param dto Datos del préstamo
	 */
	void save(Long id, LoanDto dto);

	/**
	 * Elimina un préstamo por su ID
	 *
	 * @param id ID del préstamo a eliminar
	 */
	void delete(Long id);

}

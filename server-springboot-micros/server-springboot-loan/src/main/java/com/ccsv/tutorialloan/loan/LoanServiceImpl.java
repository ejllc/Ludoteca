package com.ccsv.tutorialloan.loan;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsv.tutorialloan.common.criteria.SearchCriteria;
import com.ccsv.tutorialloan.loan.model.Loan;
import com.ccsv.tutorialloan.loan.model.LoanDto;
import com.ccsv.tutorialloan.loan.model.LoanSearchDto;

import jakarta.transaction.Transactional;

/**
 * @author emlloren
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Override
	public List<Loan> find(Long gameId, Long clientId, Date date) {
		Specification<Loan> spec = Specification.where(null);

		if (gameId != null) {
			spec = spec.and(new LoanSpecification(new SearchCriteria("idGame", ":", gameId)));
		}

		if (clientId != null) {
			spec = spec.and(new LoanSpecification(new SearchCriteria("idClient", ":", clientId)));
		}

		if (date != null) {
			spec = spec.and(new LoanSpecification(new SearchCriteria("startDate", "<=", date)));
			spec = spec.and(new LoanSpecification(new SearchCriteria("endDate", ">=", date)));
		}

		return loanRepository.findAll(spec);
	}

	@Override
	public void save(Long id, LoanDto dto) {
		Loan loan;
		if (id == null) {
			loan = new Loan();
		} else {
			loan = loanRepository.findById(id).orElse(null);
		}

		BeanUtils.copyProperties(dto, loan, "id");

		loan.setIdClient(dto.getClient().getId());
		loan.setIdGame(dto.getGame().getId());

		loanRepository.save(loan);
	}

	@Override
	public void delete(Long id) {
		loanRepository.deleteById(id);
	}

	@Override
	public Page<Loan> findPage(LoanSearchDto dto) {

		return this.loanRepository.findAll(dto.getPageable().getPageable());

	}

	@Override
	public Loan get(Long id) {

		return this.loanRepository.findById(id).orElse(null);

	}

}

package com.ccsv.tutorialloan.loan;

import org.springframework.data.jpa.domain.Specification;

import com.ccsv.tutorialloan.common.criteria.SearchCriteria;
import com.ccsv.tutorialloan.loan.model.Loan;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * @author emlloren
 *
 */
public class LoanSpecification implements Specification<Loan> {

	private static final long serialVersionUID = 1L;

	private final SearchCriteria criteria;

	public LoanSpecification(SearchCriteria criteria) {

		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
			Path<String> path = getPath(root);
			if (path.getJavaType() == String.class) {
				return builder.like(path, "%" + criteria.getValue() + "%");
			} else {
				return builder.equal(path, criteria.getValue());
			}
		}
		return null;
	}

	private Path<String> getPath(Root<Loan> root) {
		String key = criteria.getKey();
		String[] split = key.split("[.]", 0);

		Path<String> expression = root.get(split[0]);
		for (int i = 1; i < split.length; i++) {
			expression = expression.get(split[i]);
		}

		return expression;
	}
}

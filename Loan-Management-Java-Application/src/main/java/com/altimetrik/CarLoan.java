package com.altimetrik;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarLoan extends Loan {
	@Getter
	@Setter
	private String carModel;

	@Getter
	@Setter
	private int carValue;

	public CarLoan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm,
			String loanType, String loanStatus, String carModel, int carValue) {
		super(loanId, customer, principalAmount, interestRate, loanTerm, loanType, loanStatus);
		this.carModel = carModel;
		this.carValue = carValue;
	}
}

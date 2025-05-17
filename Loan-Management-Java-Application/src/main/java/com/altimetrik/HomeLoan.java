package com.altimetrik;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HomeLoan extends Loan {
	@Getter
	@Setter
	private String propertyAddress;
	@Getter
	@Setter
	private int propertyValue;

	public HomeLoan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm,
			String loanType, String loanStatus, String propertyAddress, int propertyValue) {
		super(loanId, customer, principalAmount, interestRate, loanTerm, loanType, loanStatus);
		this.propertyAddress = propertyAddress;
		this.propertyValue = propertyValue;
	}
}

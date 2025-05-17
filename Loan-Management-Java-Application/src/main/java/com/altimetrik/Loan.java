package com.altimetrik;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
	private int loanId;
	private Customer customer;
	private double principalAmount;
	private double interestRate;
	private int loanTerm;
	private String loanType;
	private String loanStatus;
}

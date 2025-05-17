package com.altimetrik;

import java.util.List;

public interface LoanManagement {
	void applyLoan(Loan loan);
	double calculateInterest(int loanId);
	double calculateInterest(double principal, double rate, int term);
	void loanStatus(int loanId);
	double calculateEMI(int loanId);
	double calculateEMI(double principal, double rate, int term);
	void loanRepayment(int loanId, double amount);
	List<Loan> getAllLoan();
	Loan getLoanById(int loanId);
}

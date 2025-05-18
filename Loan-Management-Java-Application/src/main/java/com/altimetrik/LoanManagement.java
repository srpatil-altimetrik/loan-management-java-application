package com.altimetrik;

import java.sql.SQLException;
import java.util.List;

public interface LoanManagement {
	void addCustomer(Customer customer);
	void applyLoan(Loan loan);
	double calculateInterest(int loanId);
	String loanStatus(int loanId);
	double calculateEMI(int loanId);
	void loanRepayment(int loanId, double amount) throws SQLException;
	List<Loan> getAllLoan();
	Loan getLoanById(int loanId);

	double calculateEMI(double principal, double annualRate, int term);
}

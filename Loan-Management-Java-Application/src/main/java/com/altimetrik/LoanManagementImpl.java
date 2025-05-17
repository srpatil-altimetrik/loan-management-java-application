package com.altimetrik;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoanManagementImpl implements LoanManagement {

	DBConfig dbConfig = new DBConfig();
	Scanner scanner = new Scanner(System.in);

	@Override
	public void addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		System.out.println("Adding customer... (Yes / No)");
		String choice = scanner.nextLine();
		if (choice.equalsIgnoreCase("yes")) {
			try {
				Connection getConnection = dbConfig.getConnection();
				String addCustomerQuery = "INSERT INTO customer (customerId, customerName, customerEmail, customerPhoneNo, customerAddress, customerCreditScore) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStatement = getConnection.prepareStatement(addCustomerQuery);
				preparedStatement.setInt(1, customer.getCustomerId());
				preparedStatement.setString(2, customer.getCustomerName());
				preparedStatement.setString(3, customer.getCustomerEmail());
				preparedStatement.setString(4, customer.getCustomerPhoneNo());
				preparedStatement.setString(5, customer.getCustomerAddress());
				preparedStatement.setInt(6, customer.getCustomerCreditScore());
				int rowsAffected = preparedStatement.executeUpdate();
				System.out.println(rowsAffected + " row(s) inserted into the customer table.");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("Customer addition cancelled.");
		}
	}

	@Override
	public void applyLoan(Loan loan) {
		// TODO Auto-generated method stub
		System.out.println("Applying for loan... (Yes / No)");
		String choice = scanner.nextLine();
		if (choice.equalsIgnoreCase("yes")) {
			try {
				Connection getConnection = dbConfig.getConnection();
				String applyLoanQuery = "INSERT INTO loan (loanId, customerId, principalAmount, interestRate, loanTerm, loanType, loanStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStatement = getConnection.prepareStatement(applyLoanQuery);
				preparedStatement.setInt(1, loan.getLoanId());
				preparedStatement.setInt(2, loan.getCustomer().getCustomerId());
				preparedStatement.setDouble(3, loan.getPrincipalAmount());
				preparedStatement.setDouble(4, loan.getInterestRate());
				preparedStatement.setInt(5, loan.getLoanTerm());
				preparedStatement.setString(6, loan.getLoanType());
				preparedStatement.setString(7, loan.getLoanStatus());
				int rowsAffected = preparedStatement.executeUpdate();
				System.out.println(rowsAffected + " row(s) inserted into the loan table.");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("Loan application cancelled.");
		}
	}

	@Override
	public double calculateInterest(int loanId) {
		// TODO Auto-generated method stub
		double interest = 0;
		try {
			Connection getConnection = dbConfig.getConnection();
			String calculateInterestQuery = "SELECT interestRate, principalAmount, loanTerm FROM loan WHERE loanId = ?";
			PreparedStatement preparedStatement = getConnection.prepareStatement(calculateInterestQuery);
			preparedStatement.setInt(1, loanId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				double principal = resultSet.getDouble("principalAmount");
				double rate = resultSet.getDouble("interestRate");
				int term = resultSet.getInt("loanTerm");
				interest = (principal * rate * term) / 100;
			} else {
				System.out.println("Loan ID not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return interest;
	}

	@Override
	public String loanStatus(int loanId) {
		// TODO Auto-generated method stub
		String status = null;
		try {
			Connection getConnection = dbConfig.getConnection();
			String getCustomerQuery = "SELECT customerId FROM loan WHERE loanId = ?";
			PreparedStatement preparedStatement = getConnection.prepareStatement(getCustomerQuery);
			preparedStatement.setInt(1, loanId);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int customerId = resultSet.getInt("customerId");
				String customerCreditScoreQuery = "SELECT customerCreditScore FROM customer WHERE customerId = ?";
				PreparedStatement creditScorePreparedStatement = getConnection
						.prepareStatement(customerCreditScoreQuery);
				creditScorePreparedStatement.setInt(1, customerId);
				ResultSet creditScoreResultSet = creditScorePreparedStatement.executeQuery();

				if (creditScoreResultSet.next()) {
					int customerCreditScore = creditScoreResultSet.getInt("customerCreditScore");

					if (customerCreditScore >= 650) {
						status = "Loan Approved";
					} else {
						status = "Loan Rejected";
					}

					String loanStatusQuery = "UPDATE loan SET loanStatus = ? WHERE loanId = ?";
					PreparedStatement updatePreparedStatement = getConnection.prepareStatement(loanStatusQuery);
					updatePreparedStatement.setString(1, status);
					updatePreparedStatement.setInt(2, loanId);
					updatePreparedStatement.executeUpdate();
				} else {
					System.out.println("Credit score not found for customer.");
				}
			} else {
				System.out.println("Loan ID not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public double calculateEMI(int loanId) {
		// TODO Auto-generated method stub
		double emi = 0;
		try {
			Connection getConnection = dbConfig.getConnection();
			String calculateEMIQuery = "SELECT principalAmount, interestRate, loanTerm FROM loan WHERE loanId = ?";
			PreparedStatement preparedStatement = getConnection.prepareStatement(calculateEMIQuery);
			preparedStatement.setInt(1, loanId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				double principal = resultSet.getDouble("principalAmount");
				double rate = resultSet.getDouble("interestRate") / 12 / 100;
				int term = resultSet.getInt("loanTerm");
				return calculateEMI(principal, rate, term);
			} else {
				System.out.println("Loan ID not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emi;
	}

	// Overloaded method for direct calculation
	@Override
	public double calculateEMI(double principal, double annualRate, int term) {
		double monthlyRate = annualRate / 12 / 100;
		double numerator = principal * monthlyRate * Math.pow(1 + monthlyRate, term);
		double denominator = Math.pow(1 + monthlyRate, term) - 1;
		double emi = 0;
		if (denominator != 0) {
			emi = numerator / denominator;
			return emi;
		}
		return emi;
	}

	@Override
	public void loanRepayment(int loanId, double amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Loan> getAllLoan() {
		// TODO Auto-generated method stub
		List<Loan> loanList = new ArrayList<>();

		try {
			Connection getConnection = dbConfig.getConnection();
			String getAllLoansQuery = "SELECT * FROM loan l JOIN customer c ON l.customerId = c.customerId";
			PreparedStatement preparedStatement = getConnection.prepareStatement(getAllLoansQuery);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int loanId = resultSet.getInt("loanId");
				int customerId = resultSet.getInt("customerId");
				String customerName = resultSet.getString("customerName");
				String customerEmail = resultSet.getString("customerEmail");
				String customerPhoneNo = resultSet.getString("customerPhoneNo");
				String customerAddress = resultSet.getString("customerAddress");
				int customerCreditScore = resultSet.getInt("customerCreditScore");
				double principalAmount = resultSet.getDouble("principalAmount");
				double interestRate = resultSet.getDouble("interestRate");
				int loanTerm = resultSet.getInt("loanTerm");
				String loanType = resultSet.getString("loanType");
				String loanStatus = resultSet.getString("loanStatus");

				Customer customer = new Customer(customerId, customerName, customerEmail, customerPhoneNo,
						customerAddress, customerCreditScore);
				Loan loan = new Loan(loanId, customer, principalAmount, interestRate, loanTerm, loanType, loanStatus);
				loanList.add(loan);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return loanList;
	}

	@Override
	public Loan getLoanById(int loanId) {
		// TODO Auto-generated method stub
		try {
			Connection getConnection = dbConfig.getConnection();
			String getLoanByIdQuery = "SELECT * FROM loan l JOIN customer c ON l.customerId = c.customerId WHERE l.loanId = ?";
			PreparedStatement preparedStatement = getConnection.prepareStatement(getLoanByIdQuery);
			preparedStatement.setInt(1, loanId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int customerId = resultSet.getInt("customerId");
				String customerName = resultSet.getString("customerName");
				String customerEmail = resultSet.getString("customerEmail");
				String customerPhoneNo = resultSet.getString("customerPhoneNo");
				String customerAddress = resultSet.getString("customerAddress");
				int customerCreditScore = resultSet.getInt("customerCreditScore");
				double principalAmount = resultSet.getDouble("principalAmount");
				double interestRate = resultSet.getDouble("interestRate");
				int loanTerm = resultSet.getInt("loanTerm");
				String loanType = resultSet.getString("loanType");
				String loanStatus = resultSet.getString("loanStatus");

				Customer customer = new Customer(customerId, customerName, customerEmail, customerPhoneNo,
						customerAddress, customerCreditScore);
				Loan loan = new Loan(loanId, customer, principalAmount, interestRate, loanTerm, loanType, loanStatus);
				return loan;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

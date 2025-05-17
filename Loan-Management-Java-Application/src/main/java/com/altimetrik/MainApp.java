package com.altimetrik;

import java.util.List;
import java.util.Scanner;

public class MainApp {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		LoanManagement loanManagement = new LoanManagementImpl();

		while (true) {
			System.out.println("Welcome to the Loan Management System");
			System.out.println("1. Apply for a loan");
			System.out.println("2. Calculate interest");
			System.out.println("3. Loan status");
			System.out.println("4. Calculate EMI");
			System.out.println("5. Loan repayment");
			System.out.println("6. Get all loans");
			System.out.println("7. Get loan by ID");
			System.out.println("8. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				// Apply for a loan
				System.out.println("Enter Customer Details: ");
				System.out.print("Enter Customer ID: ");
				int customerId = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Enter Customer Name: ");
				String customerName = scanner.nextLine();
				System.out.print("Enter Customer Email: ");
				String customerEmail = scanner.nextLine();
				System.out.print("Enter Customer Phone No: ");
				String customerPhoneNo = scanner.nextLine();
				System.out.print("Enter Customer Address: ");
				String customerAddress = scanner.nextLine();
				System.out.print("Enter Customer Credit Score: ");
				int customerCreditScore = scanner.nextInt();
				scanner.nextLine();
				Customer customer = new Customer(customerId, customerName, customerEmail, customerPhoneNo,
						customerAddress, customerCreditScore);
				loanManagement.addCustomer(customer);
				System.out.println("Enter Loan Details: ");
				System.out.print("Enter Loan ID: ");
				int loanId = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Enter Principal Amount: ");
				double principalAmount = scanner.nextDouble();
				scanner.nextLine();
				System.out.print("Enter Interest Rate: ");
				double interestRate = scanner.nextDouble();
				scanner.nextLine();
				System.out.print("Enter Loan Term (in months): ");
				int loanTerm = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Enter Loan Type: ");
				String loanType = scanner.nextLine();
				System.out.print("Enter Loan Status: ");
				String loanStatus = scanner.nextLine();
				Loan loan = new Loan(loanId, customer, principalAmount, interestRate, loanTerm, loanType, loanStatus);
				loanManagement.applyLoan(loan);
				System.out.println("Loan applied successfully!");
				break;
			case 2:
				// Calculate interest
				System.out.print("Enter Loan ID: ");
				int loanIdForInterest = scanner.nextInt();
				scanner.nextLine();
				double interest = loanManagement.calculateInterest(loanIdForInterest);
				System.out.println("Calculated Interest: " + interest);
				break;
			case 3:
				// Loan status
				System.out.print("Enter Loan ID: ");
				int loanIdForStatus = scanner.nextInt();
				String status = loanManagement.loanStatus(loanIdForStatus);
				System.out.println("Loan Status: " + status);
				break;
			case 4:
				// Calculate EMI
				System.out.print("Enter Loan ID: ");
				int loanIdForEMI = scanner.nextInt();
				double emi = loanManagement.calculateEMI(loanIdForEMI);
				System.out.println("Calculated EMI: " + emi);
				break;

			case 5:
				// Loan repayment
				System.out.print("Enter Loan ID: ");
				int loanIdForRepayment = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Enter Repayment Amount: ");
				double repaymentAmount = scanner.nextDouble();
				
				break;
			case 6:
				// Get all loans
				System.out.println("Fetching all loans...");
				List<Loan> allLoans = loanManagement.getAllLoan();
				for (Loan l : allLoans) {
					System.out.println(l);
				}
				break;
			case 7:
				// Get loan by ID
				System.out.print("Enter Loan ID: ");
				int loanIdForGet = scanner.nextInt();
				Loan loanById = loanManagement.getLoanById(loanIdForGet);
				if (loanById != null) {
					System.out.println("Loan Details: " + loanById);
				} else {
					System.out.println("Loan not found.");
				}
				break;
			case 8:
				System.out.println("Exiting...");
				scanner.close();
				return;
			default:
				System.out.println("Invalid choice, please try again.");
			}
		}
	}
}
